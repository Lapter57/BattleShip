package com.lapter57.db.repository;

import com.lapter57.db.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CommonRepository<Game> {

    Optional<Game> findById(Long id);
}
