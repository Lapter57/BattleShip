package graphics;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainStPain extends StackPane{
    public Graphic.Title title = new Graphic.Title("B A T T L E S H I P");
    {
        title.setTranslateX(-20);
        title.setTranslateY(-200);
    }
    Graphic.MenuItem newGame = new Graphic.MenuItem("NEW GAME", 400, 50);
    Graphic.MenuItem statistics = new Graphic.MenuItem("STATISTICS", 400, 50);
    Graphic.MenuItem exitGame = new Graphic.MenuItem("EXIT", 400, 50);
    Graphic.SubMenu mainMenu = new Graphic.SubMenu(
            newGame, statistics, exitGame
    );
    Graphic.MenuItem playWithComputer = new Graphic.MenuItem("     HUMAN     VS     COMPUTER", 400, 50);
    Graphic.MenuItem playWithHuman = new Graphic.MenuItem("HUMAN     VS     HUMAN", 400, 50);
    Graphic.MenuItem bck = new Graphic.MenuItem("BACK", 400, 50);
    Graphic.SubMenu newGameMenu = new Graphic.SubMenu(
            playWithComputer, playWithHuman, bck
    );


    public Graphic.MenuBox menuBox = new Graphic.MenuBox(mainMenu);

    MainStPain() {
        setMaxWidth(1280);
        setMinHeight(720);
        try (InputStream is = Files.newInputStream(Paths.get("src/main/resources/background/BattleShip.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitHeight(720);
            img.setFitWidth(1280);
            getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Rectangle bg = new Rectangle(1280, 720, Color.LIGHTBLUE);
        bg.setOpacity(0.3);
        getChildren().addAll(bg, title, menuBox);

        newGame.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));
        exitGame.setOnMouseClicked(event-> System.exit(0));

    }
}
