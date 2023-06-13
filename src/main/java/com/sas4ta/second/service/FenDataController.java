package com.sas4ta.second.service;

import com.sas4ta.second.data.ChessMove;
import com.sas4ta.second.data.ChessMoveFen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FenDataController {

    private final FenDataService fenDataService;

    @Autowired
    public FenDataController(FenDataService fenDataService) {
        this.fenDataService = fenDataService;
    }

    @GetMapping("/base")
    public List<ChessMove> getAllData() {
        return fenDataService.getAllData();
    }

    @GetMapping("/fenbase")
    public List<ChessMoveFen> getAllFenData() {
        return fenDataService.getAllFenData();
    }

    @GetMapping("/fendata")
    public ChessMoveFen getFenData(@RequestParam String fen) {
        return fenDataService.getFenDataByFen(fen);
    }
}