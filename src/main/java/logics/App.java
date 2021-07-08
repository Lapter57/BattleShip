package logics;

import graphics.Graphic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}


