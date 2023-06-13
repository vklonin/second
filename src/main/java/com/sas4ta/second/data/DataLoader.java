package com.sas4ta.second.data;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;


@Service
@Log4j2
public class DataLoader {

    private final ChessMoveRepository chessMoveRepository;
    private final ChessMoveFenRepository chessMoveFenRepository;
    private final ObjectMapper mapper;

    public DataLoader(ChessMoveRepository chessMoveRepository, ObjectMapper mapper, ChessMoveFenRepository chessMoveFenRepository) {
        this.chessMoveRepository = chessMoveRepository;
        this.mapper = mapper;
        this.chessMoveFenRepository = chessMoveFenRepository;
    }

    public void loadChessMovesFromJson() {
        log.info("loading started");
        try {
            chessMoveRepository.deleteAll();
            InputStream inputStream = TypeReference.class.getResourceAsStream("/base.json");

            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            var myString =  scanner.hasNext() ? scanner.next() : "";

            ChessMove chessMove = mapper.readValue(myString, ChessMove.class);
            chessMoveRepository.save(chessMove);

            List<ChessMove> moves = chessMoveRepository.findAll();
            ChessMove chessMoveOne = moves.get(0);

            List<ChessMoveFen> dataToStore = ChessMovesToFen.parser(mapper.writeValueAsString(chessMoveOne));

            chessMoveFenRepository.saveAll(dataToStore);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
