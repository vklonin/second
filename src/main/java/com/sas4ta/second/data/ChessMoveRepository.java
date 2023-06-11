package com.sas4ta.second.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChessMoveRepository extends MongoRepository<ChessMove, String> {
}