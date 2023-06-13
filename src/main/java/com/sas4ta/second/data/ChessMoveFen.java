package com.sas4ta.second.data;

import lombok.Value;

@Value
public class ChessMoveFen {
    private String fen;
    private MoveInfo moveInfo;
}
