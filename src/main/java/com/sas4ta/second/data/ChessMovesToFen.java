package com.sas4ta.second.data;

import chesspresso.*;
import chesspresso.position.*;
import chesspresso.move.*;
import com.fasterxml.jackson.databind.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ChessMovesToFen {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void parser(String data) {
        try {
            String jsonInput = data;
            Map<String, Object> chessData = objectMapper.readValue(jsonInput, Map.class);

            List<Object[]> fenData = generateFenData(chessData);
            System.out.println(objectMapper.writeValueAsString(fenData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Object[]> generateFenData(Map<String, Object> data) {
        Position position = new Position(Position.createInitialPosition());
        List<Object[]> result = new ArrayList<>();

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

    private static void parseMove(Map<String, Object> moveData, Position position, List<Object[]> result) {
        String moveString = (String) moveData.get("m");
        if (!moveString.isEmpty()) {
            try {

                String source;
                String destination;
                char pieceSymbol = moveString.charAt(0);
                Boolean isCapture = false;

                if (moveString.length() == 2) {
                    source = "";
                    destination = moveString;
                } else if (moveString.length() == 3 && Character.isUpperCase(pieceSymbol)) {
                    destination = moveString.substring(1, 3);
                    source = "";
                } else if (moveString.length() >= 3 && moveString.contains("x")) {
                        // Ход с взятием
                        source = "";//moveString.substring(0, moveString.indexOf('x'));
                        destination = moveString.substring(moveString.indexOf('x') + 1);
                        isCapture = true;
                } else {
                    source = moveString.substring(0, 2);
                    destination = moveString.substring(2);
                }

                var move = Move.getRegularMove(coordToSqi(source),
                        coordToSqi(destination),
                        isCapture);
                position.doMove(move);


            } catch (IllegalMoveException e) {
                System.err.println("Illegal move: " + moveString);
                System.exit(1);
            }
        }

        Map<String, Object> moveInfo = (Map<String, Object>) moveData.get("e");

        if (moveInfo == null) {

            String bestMove = (String) moveData.get("m");
            result.add(new Object[]{position.getFEN(), new MoveInfo(bestMove, 0, 0)});

        } else {

            String bestMove = (String) moveData.get("m");
            double score = ((Number) moveInfo.get("v")).doubleValue();
            double depth = ((Number) moveInfo.get("d")).doubleValue();

            result.add(new Object[]{position.getFEN(), new MoveInfo(bestMove, score, depth)});
        }



        List<Map<String, Object>> nextMovesData = (List<Map<String, Object>>) moveData.get("s");
        for (Map<String, Object> nextMoveData : nextMovesData) {
            parseMove(nextMoveData, new Position(position), result);
        }
    }

    @Getter
    @Setter
    static class MoveInfo {
        String bestMove;
        double score;
        double depth;

        MoveInfo(String bestMove, double score, double depth) {
            this.bestMove = bestMove;
            this.score = score;
            this.depth = depth;
        }
    }
}
