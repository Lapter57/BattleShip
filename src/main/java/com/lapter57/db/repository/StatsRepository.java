package com.lapter57.db.repository;

import com.lapter57.db.model.Stats;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepository extends CommonRepository<Stats> {

    Optional<Stats> findById(Long id);

    List<Stats> findTop10ByOrderByScoreAsc();
}
