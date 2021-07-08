package com.lapter57.db.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player extends AbstractEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String nickname;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stats> stats;

    public static Player from(final String nickname) {
        final var player = new Player();
        player.setNickname(nickname);
        return player;
    }
}
