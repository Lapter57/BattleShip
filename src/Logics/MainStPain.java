package Logics;

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
    Main.Title title = new Main.Title("B A T T L E S H I P");
    {
        title.setTranslateX(-20);
        title.setTranslateY(-200);
    }
    Main.MenuItem newGame = new Main.MenuItem("NEW GAME", 400, 50);
    Main.MenuItem options = new Main.MenuItem("STATISTICS", 400, 50);
    Main.MenuItem exitGame = new Main.MenuItem("EXIT", 400, 50);
    Main.SubMenu mainMenu = new Main.SubMenu(
            newGame, options, exitGame
    );
    Main.MenuItem hvC = new Main.MenuItem("     HUMAN     VS     COMPUTER", 400, 50);
    Main.MenuItem hvH = new Main.MenuItem("HUMAN     VS     HUMAN", 400, 50);
    Main.MenuItem bck = new Main.MenuItem("BACK", 400, 50);
    Main.SubMenu newGameMenu = new Main.SubMenu(
            hvC, hvH, bck
    );
    Main.MenuBox menuBox = new Main.MenuBox(mainMenu);

    MainStPain() {
        setMaxWidth(1280);
        setMinHeight(720);
        try (InputStream is = Files.newInputStream(Paths.get("res/images/BattleShip.jpg"))) {
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
    }
}
