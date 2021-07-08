package com.lapter57.statistics;

import com.lapter57.db.model.Player;
import com.lapter57.db.model.Stats;
import com.lapter57.db.service.GameService;
import com.lapter57.db.service.PlayerService;
import com.lapter57.db.service.StatsService;
import com.lapter57.logics.players.HumanPlayer;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class PlayerStats {

    private final TableStats table;
    private final StatsService statsService;
    private final PlayerService playerService;
    private final GameService gameService;

    public PlayerStats(final TableStats table,
                       final StatsService statsService,
                       final PlayerService playerService,
                       final GameService gameService) {
        this.table = table;
        this.statsService = statsService;
        this.playerService = playerService;
        this.gameService = gameService;
        table.fillList(statsService.findTop10());
    }

    public void updateStats(HumanPlayer hp){
        double hpScore = (double)(10 - hp.getNumShipAfloat() + hp.getNumWinShots())/2;
        final var player = playerService.findNickname(hp.getName())
                .orElse(Player.from(hp.getName()));
        if (player.getId() == null) {
            playerService.save(player);
        }
        final var game = com.lapter57.db.model.Game.from(hp.getLevel());
        gameService.save(game);

        final var stats = Stats.of(game, player, hpScore);
        statsService.save(stats);
        table.fillList(statsService.findTop10());
    }

    public TableView<Stats> getTable(){
        return table.getTable();
    }
}
