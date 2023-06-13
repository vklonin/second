package com.sas4ta.second.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChessMoveFenRepository extends MongoRepository<ChessMoveFen, String> {
    ChessMoveFen findFirstByFen(String fen);
}