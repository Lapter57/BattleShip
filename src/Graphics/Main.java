package Graphics;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
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
import javafx.util.Duration;

import javafx.stage.StageStyle;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
      Pane root = new Pane();
      try(InputStream is = Files.newInputStream(Paths.get("res/images/BattleShip.jpg"))) {
          ImageView img = new ImageView(new Image(is));
          img.setFitHeight(720);
          img.setFitWidth(1280);
          root.getChildren().add(img);
      }
      catch (IOException e){
          System.out.println("Couldn't load image");
      }
        Title title = new Title("B A T T L E S H I P");
        title.setTranslateX(410);
        title.setTranslateY(100);
        MenuItem newGame = new MenuItem("NEW GAME");
        MenuItem options = new MenuItem("STATISTICS");
        MenuItem exitGame = new MenuItem("EXIT");
        SubMenu mainMenu = new SubMenu(
                newGame,options,exitGame
        );
        MenuItem hvC = new MenuItem("HUMAN VS COMPUTER");
        MenuItem hvH = new MenuItem("HUMAN VS HUMAN");
        MenuItem bck = new MenuItem("BACK");
        SubMenu newGameMenu = new SubMenu(
                hvC,hvH,bck
        );
        MenuBox menuBox = new MenuBox(mainMenu);

        newGame.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));
        exitGame.setOnMouseClicked(event-> System.exit(0));
        bck.setOnMouseClicked(event-> menuBox.setSubMenu(mainMenu));
       /* hvC.setOnMouseClicked(event -> {
            SubMenu gameHvC = new SubMenu();
            title.setVisible(false);
            menuBox.setSubMenu(gameHvC);
            Game game = new Game();
            game.humanVscomputer();
        });*/
        hvH.setOnMouseClicked(event -> {
           /* SubMenu gameHvH = new SubMenu();
            title.setVisible(false);
            menuBox.setSubMenu(gameHvH);*/
            /*Game game = new Game();
            game.humanVshuman();*/
        });
        root.getChildren().addAll(menuBox, title);

        Scene scene = new Scene(root);
        menuBox.setVisible(true);
        title.setVisible(true);
        scene.setOnKeyPressed(event -> {
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
        });
        primaryStage.setTitle("BattleShip");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
  }



    private static class MenuItem extends StackPane{
        public  MenuItem(String name){
            LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.valueOf("#A93927")),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.valueOf("#A93927")),
            });
            Rectangle bg = new Rectangle(400,50);
            bg.setOpacity(0.5);

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

    private static class Title extends StackPane{
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

    private static class MenuBox extends Pane{
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;
            setVisible(false);
            Rectangle bg = new Rectangle(1280,720,Color.LIGHTBLUE);
            bg.setOpacity(0.3);
            getChildren().addAll(bg, subMenu);
        }
        public void setSubMenu(SubMenu subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }

    }

    private static class SubMenu extends VBox{
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


