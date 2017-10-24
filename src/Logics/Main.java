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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        MainStPain msp = new MainStPain();


        Scene scene = new Scene(msp);
        msp.newGame.setOnMouseClicked(event->msp.menuBox.setSubMenu(msp.newGameMenu));
        msp.exitGame.setOnMouseClicked(event-> System.exit(0));
        msp.bck.setOnMouseClicked(event-> {
            msp.menuBox.setSubMenu(msp.mainMenu);
            msp.title.setVisible(true);
        });
        Game game = new Game();
        msp.hvC.setOnMouseClicked(event -> {
            Pane gameHvH = new Pane();
            gameHvH.setMaxHeight(720);
            gameHvH.setMaxWidth(1280);
            msp.getChildren().add(gameHvH);

            gameHvH.setVisible(true);
            msp.menuBox.setVisible(false);
            msp.title.setVisible(false);
            game.humanVscomputer(gameHvH,msp);
        });
        msp.hvH.setOnMouseClicked(event -> {
            Pane gameHvC = new Pane();
            gameHvC.setMaxHeight(720);
            gameHvC.setMaxWidth(1280);
            msp.getChildren().add(gameHvC);

            gameHvC.setVisible(true);
            msp.menuBox.setVisible(false);
            msp.title.setVisible(false);
            game.humanVsHuman(gameHvC,msp);
        });

        msp.menuBox.setVisible(true);
        msp.title.setVisible(true);
        /*scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                FadeTransition ft1 = new FadeTransition(Duration.seconds(1),menuBox);
                FadeTransition ft2 = new FadeTransition(Duration.seconds(1),title);
                if (!menuBox.isVisible()) {
                    ft1.setFromValue(0);
                    ft1.setToValue(1);
                    ft2.setFromValue(0);
                    ft2.setToValue(1);
                    ft1.play();
                    ft2.play();
                    menuBox.setVisible(true);
                    title.setVisible(true);
                }
                else{
                    ft1.setFromValue(1);
                    ft1.setToValue(0);
                    ft2.setFromValue(1);
                    ft2.setToValue(0);
                    ft1.setOnFinished(evt ->
                        menuBox.setVisible(false)
                    );
                    ft2.setOnFinished(evt -> title.setVisible(false));
                    ft2.play();
                    ft1.play();

                }
            }
        });*/
        primaryStage.setTitle("BattleShip");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
  }



    static class MenuItem extends StackPane{
        public  MenuItem(String name,int x,int y){
            LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.valueOf("#A93927")),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.valueOf("#A93927")),
            });
            Rectangle bg = new Rectangle(x,y);
            bg.setOpacity(0.5);
            bg.setArcHeight(20);
            bg.setArcWidth(20);
            Text text = new Text(name);
            text.setFill(Color.DARKGRAY);
            text.setFont(Font.font("Tw Cen MT Condensed",FontWeight.SEMI_BOLD,26));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGRAY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.valueOf("#A93927"));
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


