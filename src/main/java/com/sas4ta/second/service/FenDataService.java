package com.sas4ta.second.service;

import com.sas4ta.second.data.ChessMove;
import com.sas4ta.second.data.ChessMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FenDataService {

    private final ChessMoveRepository chessMoveRepository;

    @Autowired
    public FenDataService(ChessMoveRepository chessMoveRepository) {
        this.chessMoveRepository = chessMoveRepository;
    }

    public List<ChessMove> getAllData() {
        return chessMoveRepository.findAll();
    }

//    public ChessMove getFenDataByFen(String fen) {
//        // TODO: Implement FEN data fetching based on FEN string
//        // This could require more complex MongoDB query or even processing after fetching the data.
//        // We need more details about how the FEN string maps to the stored data.
//    }
}