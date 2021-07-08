package com.lapter57.db.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "stats")
public class Stats extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "score", nullable = false)
    private Double score;

    public static Stats of(final Game game,
                           final Player player,
                           final Double score) {
        final var stats = new Stats();
        stats.setGame(game);
        stats.setPlayer(player);
        stats.setScore(score);
        return stats;
    }
}
