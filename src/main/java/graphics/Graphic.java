package graphics;

import graphics.animation.Animation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logics.Game;
import statistics.PlayerStats;

import java.util.HashMap;

public class Graphic {
    private MainStPain mainStPain = new MainStPain();
    public static HashMap<String, Image> map_img = new HashMap<>();
    private StackPane pointer = new StackPane();
    public static Animation animation = new Animation();
    public MainStPain getMainStPain() {
        return mainStPain;
    }

    public StackPane getPointer() {
        return pointer;
    }

    public void createStatsPane(PlayerStats playerStats){
        Pane statsPane = new Pane();
        statsPane.getChildren().add(playerStats.getTable());
        mainStPain.getChildren().add(statsPane);

        Graphic.MenuItem back = new Graphic.MenuItem("BACK", 100, 70);
        back.setTranslateX(10);
        back.setTranslateY(640);
        statsPane.getChildren().add(back);
        statsPane.setVisible(false);

        back.setOnMouseClicked(event -> {
            statsPane.setVisible(false);
            mainStPain.menuBox.setVisible(true);
            mainStPain.title.setVisible(true);
        });
        mainStPain.statistics.setOnMouseClicked(event -> {
            statsPane.setVisible(true);
            mainStPain.menuBox.setVisible(false);
            mainStPain.title.setVisible(false);
        });
        mainStPain.bck.setOnMouseClicked(event-> {
            mainStPain.menuBox.setSubMenu(mainStPain.mainMenu);
            mainStPain.title.setVisible(true);
        });
    }

    void createPointer(Pane playingFields) {
        if(pointer.getChildren().size() == 0) {
            playingFields.getChildren().add(pointer);
            pointer.setMaxWidth(150);
            pointer.setMaxHeight(225);
            pointer.setLayoutX(600);
            pointer.setLayoutY(295);
        }
        else{
            if(!playingFields.getChildren().contains(pointer))
                playingFields.getChildren().add(pointer);
        }
    }



    void removePane(Pane pane) {
        pane.getChildren().clear();
        pane.setVisible(false);
        pane = null;
    }

    public void customizeGameMods(Game game){

        mainStPain.playWithComputer.setOnMouseClicked(event -> {
            Pane gameHvC = new Pane();
            gameHvC.setMaxHeight(720);
            gameHvC.setMaxWidth(1280);
            mainStPain.getChildren().add(gameHvC);

            gameHvC.setVisible(true);
            mainStPain.menuBox.setVisible(false);
            mainStPain.title.setVisible(false);
            game.humanVsComputer(gameHvC);
        });

        mainStPain.playWithHuman.setOnMouseClicked(event -> {
            Pane gameHvH = new Pane();
            gameHvH.setMaxHeight(720);
            gameHvH.setMaxWidth(1280);
            mainStPain.getChildren().add(gameHvH);

            gameHvH.setVisible(true);
            mainStPain.menuBox.setVisible(false);
            mainStPain.title.setVisible(false);
            game.humanVsHuman(gameHvH);
        });

        mainStPain.menuBox.setVisible(true);
        mainStPain.title.setVisible(true);

    }

    public static class MenuItem extends StackPane {
        Rectangle bg;
        Text text;

        public void changeSize(int x, int y){
            bg.setWidth(x);
            bg.setHeight(y);
        }
        MenuItem(String name, int x, int y){
            LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, new Stop(0, Color.valueOf("#A93927")),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.valueOf("#A93927")));
            bg = new Rectangle(x,y);
            bg.setOpacity(0.5);
            bg.setArcHeight(20);
            bg.setArcWidth(20);
            text = new Text(name);
            text.setFill(Color.DARKGRAY);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,26));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                if(!isDisable()) {
                    bg.setFill(Color.BLACK);
                    text.setFill(Color.DARKGRAY);
                }
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.valueOf("#A93927"));
                text.setFill(Color.WHITE);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });


        }
    }

    static class Title extends StackPane{
        Title(String name){
            setVisible(false);
            Rectangle bg = new Rectangle(420,100);
            bg.setStroke(Color.valueOf("#0D1C25"));
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.valueOf("#0D1C25"));
            text.setFont(Font.font("Tw Cen MT Condensed",FontWeight.SEMI_BOLD,80));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
        }
    }

    static class MenuBox extends Pane {
        static VBox subMenu;
        MenuBox(VBox subMenu){
            MenuBox.subMenu = subMenu;
            setVisible(false);
            getChildren().addAll(subMenu);
        }
        void setSubMenu(VBox subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }

    }

    static class SubMenu extends VBox{
        SubMenu(MenuItem... items){
            setSpacing(15);
            setTranslateY(250);
            setTranslateX(420);
            for(MenuItem item : items){
                getChildren().addAll(item);
            }

        }
    }



}
