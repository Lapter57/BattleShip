package com.lapter57.db.service;

import com.lapter57.db.model.Game;
import com.lapter57.db.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService extends AbstractService<Game, GameRepository> {

    public GameService(final GameRepository repository) {
        super(repository);
    }

    @Override
    public Game save(final Game game) {
        return repository.saveAndFlush(game);
    }

    public Optional<Game> findById(final Long id) {
        return repository.findById(id);
    }
}
