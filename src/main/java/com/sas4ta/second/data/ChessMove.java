package com.sas4ta.second.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@Document(collection = "chess_moves")
public class ChessMove {

//    @Id
//    private String id;

    private String m;
    private int n;
    private String c;
    private String t;
    private Evaluation e;
    private List<ChessMove> s;

}
