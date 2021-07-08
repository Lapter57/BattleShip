package com.lapter57;

import com.lapter57.JavaFXApplication.StageReadyEvent;
import com.lapter57.graphics.Graphic;
import com.lapter57.logics.Game;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final Game game;

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        final var stage = stageReadyEvent.getStage();

        game.customizeGameMods();
        Scene scene = new Scene(game.getGraphic().getMainStPain());

        stage.setTitle("BattleShip");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }
}