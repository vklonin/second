package com.sas4ta.second.service;

import com.sas4ta.second.data.ChessMove;
import com.sas4ta.second.data.ChessMoveFen;
import com.sas4ta.second.data.ChessMoveFenRepository;
import com.sas4ta.second.data.ChessMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FenDataService {

    private final ChessMoveRepository chessMoveRepository;
    private final ChessMoveFenRepository chessMoveFenRepository;

    @Autowired
    public FenDataService(ChessMoveRepository chessMoveRepository, ChessMoveFenRepository chessMoveFenRepository) {
        this.chessMoveRepository = chessMoveRepository;
        this.chessMoveFenRepository = chessMoveFenRepository;
    }

    public List<ChessMove> getAllData() {
        return chessMoveRepository.findAll();
    }

    public List<ChessMoveFen> getAllFenData() {
        return chessMoveFenRepository.findAll();
    }

    public ChessMoveFen getFenDataByFen(String fen) {
        return chessMoveFenRepository.findFirstByFen(fen);
    }

//    public ChessMove getFenDataByFen(String fen) {
//        // TODO: Implement FEN data fetching based on FEN string
//        // This could require more complex MongoDB query or even processing after fetching the data.
//        // We need more details about how the FEN string maps to the stored data.
//    }
}