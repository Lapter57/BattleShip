package com.lapter57.db.repository;

import com.lapter57.db.model.Player;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CommonRepository<Player> {

    Optional<Player> findById(Long id);

    Optional<Player> findByNickname(String nickname);
}
