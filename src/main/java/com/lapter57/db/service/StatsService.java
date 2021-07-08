package com.lapter57.db.service;

import com.lapter57.db.model.Stats;
import com.lapter57.db.repository.StatsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatsService extends AbstractService<Stats, StatsRepository> {

    public StatsService(final StatsRepository repository) {
        super(repository);
    }

    @Override
    public Stats save(final Stats stats) {
        return repository.saveAndFlush(stats);
    }

    public Optional<Stats> findById(final Long id) {
        return repository.findById(id);
    }

    public List<Stats> findTop10() {
        return repository.findTop10ByOrderByScoreAsc();
    }
}
