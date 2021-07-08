package com.lapter57.db.service;

import com.lapter57.db.model.Player;
import com.lapter57.db.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService extends AbstractService<Player, PlayerRepository> {

    public PlayerService(final PlayerRepository repository) {
        super(repository);
    }

    @Override
    public Player save(final Player player) {
        return repository.saveAndFlush(player);
    }

    public Optional<Player> findById(final Long id) {
        return repository.findById(id);
    }

    public Optional<Player> findNickname(final String nickname) {
        return repository.findByNickname(nickname);
    }
}
