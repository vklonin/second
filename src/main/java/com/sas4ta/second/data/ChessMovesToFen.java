package com.sas4ta.second.data;

import chesspresso.position.*;
import chesspresso.move.*;
import com.fasterxml.jackson.databind.*;

import java.util.*;
import java.util.stream.Collectors;

public class ChessMovesToFen {

    static Map<Integer, String> numberToLetterMap = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<ChessMoveFen> parser(String data) {

        List<ChessMoveFen> fenData = null;
        try {
            Map<String, Object> chessData = objectMapper.readValue(data, Map.class);

            fenData = generateFenData(chessData);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return fenData;
    }

    private static List<ChessMoveFen> generateFenData(Map<String, Object> data) {
        Position position = new Position(Position.createInitialPosition());
        List<ChessMoveFen> result = new ArrayList<>();

        parseMove(data, position, result);

        return result;
    }

    private static int coordToSqi(String coord) {
        if (coord.isEmpty()) {
            return -1; // Возвращаем -1 в случае отсутствия позиции
        }
        int file = coord.charAt(0) - 'a';
        int rank = '8' - coord.charAt(1);
        return rank * 8 + file;
    }

    private static void parseMove(Map<String, Object> moveData, Position position, List<ChessMoveFen> result) {

        numberToLetterMap.put(5, "P");
        numberToLetterMap.put(1, "N");
        numberToLetterMap.put(2, "B");
        numberToLetterMap.put(4, "R");
        numberToLetterMap.put(3, "Q");
        numberToLetterMap.put(6, "K");

        String moveStringInitial = (String) moveData.get("m");
        String moveString = moveStringInitial.replace("+","");
        if (!moveString.isEmpty()) {
            try {


                String pieceSymbol = moveString.substring(0, 1);

                short[] allMoves = position.getAllMoves();
                Short[] wrappedMoves = new Short[allMoves.length];
                for (int i = 0; i < allMoves.length; i++) {
                    wrappedMoves[i] = Short.valueOf(allMoves[i]);
                }

                Short moveShort = -1;

                try {

                    if (moveString.length() == 2) {

                        moveShort = Arrays.stream(wrappedMoves).filter(e -> {
                            var move = Move.getString(e);
                            return move.substring(move.length() - 2).equals(moveString);
                        }).findAny().get();

                    } else if (moveString.length() >= 3 && Character.isUpperCase(pieceSymbol.charAt(0))) {
                        List<Short> collectedMoves = Arrays.stream(wrappedMoves).filter(e -> {
                            var move = Move.getString(e);
                            return move.substring(move.length() - 2).equals(moveString.substring(moveString.length() - 2));
                        }).collect(Collectors.toList());
                        if (collectedMoves.size() > 1) {

                            moveShort = collectedMoves.stream().filter(e -> numberToLetterMap.get(position.getPiece(Move.getFromSqi(e))).equals(pieceSymbol)).findAny().get();

                        } else {
                            moveShort = collectedMoves.get(0);
                        }

                    } else if (moveString.length() >= 3 && moveString.contains("x")) {
                        List<Short> collectedMoves = Arrays.stream(wrappedMoves).filter(e -> {
                            var move = Move.getString(e);
                            return move.substring(move.length() - 2).equals(moveString.substring(2));
                        }).collect(Collectors.toList());
                        if (collectedMoves.size() > 1) {
                            moveShort = collectedMoves.stream().filter(e -> numberToLetterMap.get(position.getPiece(Move.getFromSqi(e))).equals(pieceSymbol)).findAny().get();
                        } else {
                            moveShort = collectedMoves.get(0);
                        }
                    } else {
                        moveShort = Arrays.stream(wrappedMoves).filter(e -> {
                            var move = Move.getString(e);
                            return move.equals(moveString.replace("-", ""));
                        }).findAny().get();
                    }
                } catch (Exception e) {
                    return;
                }


                position.doMove(moveShort);

            } catch (IllegalMoveException e) {
                System.err.println("Illegal move: " + moveString);
                System.exit(1);
            }
        }

        Map<String, Object> moveInfo = (Map<String, Object>) moveData.get("e");

        if (moveInfo == null) {

            String bestMove = (String) moveData.get("m");
            result.add(new ChessMoveFen(position.getFEN(),new MoveInfo(bestMove, 0, 0)));

        } else {

            String bestMove = (String) moveData.get("m");
            double score = ((Number) moveInfo.get("v")).doubleValue();
            double depth = ((Number) moveInfo.get("d")).doubleValue();


            result.add(new ChessMoveFen(position.getFEN(), new MoveInfo(bestMove, score, depth)));
        }

        List<Map<String, Object>> nextMovesData = (List<Map<String, Object>>) moveData.get("s");
        for (Map<String, Object> nextMoveData : nextMovesData) {
            parseMove(nextMoveData, new Position(position), result);
        }
    }


}
