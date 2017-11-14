package graphics;

import Logics.Main;
import Logics.players.Player;
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
import statistics.PlayerStats;
import Logics.Game;

import java.util.HashMap;

public class Graphic {
    private MainStPain mainStPain = new MainStPain();
    public static HashMap<String, Image> map_img = new HashMap<>();
    public MainStPain getMainStPain() {
        return mainStPain;
    }
    private StackPane pointer = new StackPane();

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

    public void createPointer(Pane playingFields, Player p1, Player p2, Game game) {
        if(pointer.getChildren().size() == 0) {
            ImageView rp = new ImageView(Graphic.map_img.get("rp"));
            ImageView gp = new ImageView(Graphic.map_img.get("gp"));
            pointer.getChildren().addAll(rp, gp);
            playingFields.getChildren().add(pointer);
            pointer.setMaxWidth(150);
            pointer.setMaxHeight(225);
            pointer.setLayoutX(600);
            pointer.setLayoutY(295);
            rp.setTranslateX(25);
            rp.setTranslateY(-20);
            gp.setTranslateX(45);
            gp.setTranslateY(-20);
            pointer.getChildren().get(0).setVisible(false);
            pointer.getChildren().get(1).setVisible(true);
        }
        else{
            playingFields.getChildren().add(pointer);
            pointer.getChildren().get(0).setVisible(false);
            pointer.getChildren().get(1).setVisible(true);
        }

        MenuItem bck = new MenuItem("BACK", 90, 90);
        bck.setTranslateX(0);
        bck.setTranslateY(630);
        playingFields.getChildren().add(bck);
        bck.setOnMouseClicked(event -> {
            game.clearBoard(p1);
            game.clearBoard(p2);
            removePane(playingFields);
            mainStPain.menuBox.setVisible(true);
            mainStPain.title.setVisible(true);
            game.setFirstClickAuto(true);
        });
    }

    public void removePane(Pane pane) {
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
            game.humanVsComputer(gameHvC,mainStPain);
        });

        mainStPain.playWithHuman.setOnMouseClicked(event -> {
            Pane gameHvH = new Pane();
            gameHvH.setMaxHeight(720);
            gameHvH.setMaxWidth(1280);
            mainStPain.getChildren().add(gameHvH);

            gameHvH.setVisible(true);
            mainStPain.menuBox.setVisible(false);
            mainStPain.title.setVisible(false);
            game.humanVsHuman(gameHvH,mainStPain);
        });

        mainStPain.menuBox.setVisible(true);
        mainStPain.title.setVisible(true);

    }

    public static class MenuItem extends StackPane {
        Rectangle bg;
        Text text;
        public  MenuItem(String name,int x,int y){
            LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.valueOf("#A93927")),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.valueOf("#A93927")),
            });
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



    public static class Title extends StackPane{
        public Title(String name){
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

    public static class MenuBox extends Pane {
        static VBox subMenu;
        public MenuBox(VBox subMenu){
            MenuBox.subMenu = subMenu;
            setVisible(false);
            getChildren().addAll(subMenu);
        }
        public void setSubMenu(VBox subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }

    }

    public static class SubMenu extends VBox{
        public SubMenu(MenuItem...items){
            setSpacing(15);
            setTranslateY(250);
            setTranslateX(420);
            for(MenuItem item : items){
                getChildren().addAll(item);
            }

        }
    }



}
