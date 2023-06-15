package com.sas4ta.second.service;

import com.sas4ta.second.data.ChessMove;
import com.sas4ta.second.data.ChessMoveFen;
import com.sas4ta.second.data.ChessMoveFenRepository;
import com.sas4ta.second.data.ChessMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USEREXT')")
    public ChessMoveFen getFenDataByFen(String fen) {
        return chessMoveFenRepository.findFirstByFen(fen);
    }

}