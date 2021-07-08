package com.lapter57.db.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game extends AbstractEntity {

    @Column(nullable = false, length = 100)
    private com.lapter57.logics.Game.Level level;

    @Column(nullable = false)
    private Timestamp date;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stats> stats;

    public static Game from(final com.lapter57.logics.Game.Level level) {
        final var game = new Game();
        game.setLevel(level);
        game.setDate(new Timestamp(System.currentTimeMillis()));
        return game;
    }
}
