package Logics;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import statistics.PlayerStats;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        MainStPain msp = new MainStPain();


        Scene scene = new Scene(msp);
        msp.newGame.setOnMouseClicked(event->msp.menuBox.setSubMenu(msp.newGameMenu));
        msp.exitGame.setOnMouseClicked(event-> System.exit(0));
        Pane statsPane = new Pane();
        PlayerStats playerStats = new PlayerStats();
        statsPane.getChildren().add(playerStats.getTable());


        msp.getChildren().add(statsPane);
        Main.MenuItem back = new Main.MenuItem("BACK", 100, 70);
        back.setTranslateX(10);
        back.setTranslateY(640);
        statsPane.getChildren().add(back);
        statsPane.setVisible(false);
        back.setOnMouseClicked(event -> {
            statsPane.setVisible(false);
            msp.menuBox.setVisible(true);
            msp.title.setVisible(true);
        });
        msp.statistics.setOnMouseClicked(event -> {
            statsPane.setVisible(true);
            msp.menuBox.setVisible(false);
            msp.title.setVisible(false);
        });
        msp.bck.setOnMouseClicked(event-> {
            msp.menuBox.setSubMenu(msp.mainMenu);
            msp.title.setVisible(true);
        });
        Game game = new Game(playerStats);
        msp.hvC.setOnMouseClicked(event -> {
            Pane gameHvC = new Pane();
            gameHvC.setMaxHeight(720);
            gameHvC.setMaxWidth(1280);
            msp.getChildren().add(gameHvC);

            gameHvC.setVisible(true);
            msp.menuBox.setVisible(false);
            msp.title.setVisible(false);
            game.humanVsComputer(gameHvC,msp);
        });
        msp.hvH.setOnMouseClicked(event -> {
            Pane gameHvH = new Pane();
            gameHvH.setMaxHeight(720);
            gameHvH.setMaxWidth(1280);
            msp.getChildren().add(gameHvH);

            gameHvH.setVisible(true);
            msp.menuBox.setVisible(false);
            msp.title.setVisible(false);
            game.humanVsHuman(gameHvH,msp);
        });

        msp.menuBox.setVisible(true);
        msp.title.setVisible(true);
        primaryStage.setTitle("BattleShip");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
  }



    static class MenuItem extends StackPane{
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
            text.setFont(Font.font("Tw Cen MT Condensed",FontWeight.SEMI_BOLD,26));

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

    static class MenuBox extends Pane{
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

    static class SubMenu extends VBox{
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


