package Logics;

import graphics.Graphic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import statistics.PlayerStats;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        Graphic graphic = new Graphic();
        Scene scene = new Scene(graphic.getMainStPain());

        Game game = new Game(graphic);
        graphic.customizeGameMods(game);

        primaryStage.setTitle("BattleShip");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
  }

}


