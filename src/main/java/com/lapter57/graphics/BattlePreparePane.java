package com.lapter57.graphics;

import com.lapter57.graphics.loading.Loading;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.lapter57.logics.Game;
import com.lapter57.logics.Ship;
import com.lapter57.logics.coord.Coord;
import com.lapter57.logics.players.ComputerPlayer;
import com.lapter57.logics.players.HumanPlayer;
import com.lapter57.logics.players.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BattlePreparePane {
    private ArrayList<ImageView> ships_img = new ArrayList<>();
    private StackPane all_fleet = new StackPane();
    private Pane fleet_h = new Pane();
    private Pane fleet_v = new Pane();
    private StackPane[][] water = new StackPane[10][10];
    private int cur_size_ship;
    private char cur_direction;
    static ArrayList<Rectangle> focusTiles = new ArrayList<>();
    {
        for(int i = 0; i < 4; i++){
            Rectangle rec = new Rectangle(45,45, Color.LIGHTGREEN);
            focusTiles.add(rec);
        }
    }
    private GridPane board = new GridPane();{
        board.setPrefSize(10, 10);
    }

    private Graphic.MenuItem autoChoice = new Graphic.MenuItem("AUTO", 400, 50);
    private Graphic.MenuItem clear = new Graphic.MenuItem("CLEAR", 400, 50);
    private Graphic.MenuItem quit = new Graphic.MenuItem("QUIT", 90, 90);
    private Graphic.MenuItem rot90 = new Graphic.MenuItem("Rot90", 90, 90);
    private Graphic.MenuItem ready = new Graphic.MenuItem("BATTLE!", 100, 70);
    private Graphic.MenuItem next = new Graphic.MenuItem("NEXT PLAYER", 400, 50);


    public BattlePreparePane(){
        Loading.loadIMG(Graphic.map_img, ships_img);
        Loading.loadPaneOfLocOfShips(water, Graphic.map_img, ships_img, board, fleet_h, fleet_v, all_fleet);
    }

    public void clearWater(){
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                water[i][j].getChildren().get(1).setVisible(true);
    }

    public void repairShips(){
        for (ImageView img : ships_img) {
            img.setVisible(true);
        }
    }

    public void createReadyBottom(int x, int y, Pane gameArea){
        ready.setTranslateX(x);
        ready.setTranslateY(y);
        gameArea.getChildren().add(ready);
    }

    public Graphic.MenuItem getReadyBottom() {
        return ready;
    }

    public void createNextBottom(int x, int y, Pane gameArea){
        next.setTranslateX(x);
        next.setTranslateY(y);
        gameArea.getChildren().add(next);
    }

    public Graphic.MenuItem getNextBottom() {
        return next;
    }



    public void prepare(HumanPlayer hp, Pane gameArea, Game game){

        clearWater();
        repairShips();

        gameArea.getChildren().addAll(board, all_fleet);
        all_fleet.setTranslateY(70);
        all_fleet.setTranslateX(750);
        createBottoms(hp,gameArea,game);

        ImageView numB = new ImageView(Graphic.map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX(105);
        numB.setTranslateY(70);

        ImageView letB = new ImageView(Graphic.map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX(150);
        letB.setTranslateY(25);

        gameArea.getChildren().addAll(numB,letB);



        customizeDragAndDrop(hp);
    }

    private void createBottoms(Player hp, Pane gameArea, Game game){
        autoChoice.setOnMouseClicked(event -> {
            game.clearBoard(hp);
            hp.setPlaceShipRand();
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (hp.getField().getGrid()[i][j].getLinkShip() != null) {
                        water[i][j].getChildren().get(1).setVisible(false);
                    }
                }
            for (ImageView img : ships_img) {
                img.setVisible(false);
            }

        });

        clear.setOnMouseClicked(event -> {
            game.clearBoard(hp);
        });

        quit.setTranslateX(0);
        quit.setTranslateY(630);
        gameArea.getChildren().add(quit);
        quit.setOnMouseClicked(event -> {
            game.clearBoard(hp);
            gameArea.setVisible(false);
            game.getGraphic().removePane(gameArea);
            game.getGraphic().getMainStPain().menuBox.setVisible(true);
            game.getGraphic().getMainStPain().title.setVisible(true);
        });

        VBox deployMenu = new VBox(
                autoChoice, clear
        );
        deployMenu.setSpacing(15);
        deployMenu.setTranslateX(175);
        deployMenu.setTranslateY(550);
        gameArea.getChildren().add(deployMenu);

        gameArea.getChildren().add(rot90);
        rot90.setTranslateX(660);
        rot90.setTranslateY(70);
        rot90.setOnMouseClicked(event -> {
            if (fleet_h.isVisible()) {
                fleet_h.setVisible(false);
                fleet_v.setVisible(true);
            } else {
                fleet_h.setVisible(true);
                fleet_v.setVisible(false);
            }
        });
    }

    public void changeQuitBottom(Game game, Pane[] panes, Player...players){
        quit.setOnMouseClicked(event1 -> {
            for (Player pl: players) {
                game.clearBoard(pl);
            }
            for (Pane p: panes) {
                game.getGraphic().removePane(p);
            }
            game.getGraphic().getMainStPain().menuBox.setVisible(true);
            game.getGraphic().getMainStPain().title.setVisible(true);
            ready.changeSize(100, 70);
        });
    }

    public void createLevelBottoms(Pane gameArea, HumanPlayer hp, ComputerPlayer cp){
        Graphic.MenuItem easy = new Graphic.MenuItem("EASY", 100, 70);
        easy.setTranslateX(770);
        easy.setTranslateY(615);
        Graphic.MenuItem normal = new Graphic.MenuItem("NORMAL", 100, 70);
        normal.bg.setFill(Color.valueOf("#A93927"));
        normal.text.setFill(Color.WHITE);
        normal.setDisable(true);
        normal.setTranslateX(880);
        normal.setTranslateY(615);
        Graphic.MenuItem hard = new Graphic.MenuItem("HARD", 100, 70);
        hard.setTranslateX(990);
        hard.setTranslateY(615);

        gameArea.getChildren().addAll(easy, normal, hard);
        easy.setOnMouseClicked(event -> {
            cp.getLevel().delete(0, cp.getLevel().length());
            cp.getLevel().append("easy");
            hp.setLevel(Game.Level.EASY);

            easy.setDisable(true);
            easy.bg.setFill(Color.valueOf("#A93927"));
            easy.text.setFill(Color.WHITE);

            normal.setDisable(false);
            normal.bg.setFill(Color.BLACK);
            normal.text.setFill(Color.DARKGRAY);

            hard.setDisable(false);
            hard.bg.setFill(Color.BLACK);
            hard.text.setFill(Color.DARKGRAY);
        });

        normal.setOnMouseClicked(event -> {
            cp.getLevel().delete(0, cp.getLevel().length());
            cp.getLevel().append("normal");
            hp.setLevel(Game.Level.NORMAL);

            normal.bg.setFill(Color.valueOf("#A93927"));
            normal.text.setFill(Color.WHITE);
            normal.setDisable(true);

            easy.setDisable(false);
            easy.bg.setFill(Color.BLACK);
            easy.text.setFill(Color.DARKGRAY);

            hard.setDisable(false);
            hard.bg.setFill(Color.BLACK);
            hard.text.setFill(Color.DARKGRAY);
        });

        hard.setOnMouseClicked(event -> {
            cp.getLevel().delete(0, cp.getLevel().length());
            cp.getLevel().append("hard");
            hp.setLevel(Game.Level.HARD);

            hard.bg.setFill(Color.valueOf("#A93927"));
            hard.text.setFill(Color.WHITE);
            hard.setDisable(true);

            easy.setDisable(false);
            easy.bg.setFill(Color.BLACK);
            easy.text.setFill(Color.DARKGRAY);

            normal.setDisable(false);
            normal.bg.setFill(Color.BLACK);
            normal.text.setFill(Color.DARKGRAY);
        });
    }

    private void customizeDragAndDrop(HumanPlayer hp){
        board.setOnDragExited(event -> {
            for(int k = 0; k < 4; k++) {
                focusTiles.get(k).setVisible(false);
            }
        });
        board.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage()) {
                event.acceptTransferModes(TransferMode.COPY);
                Node node = event.getPickResult().getIntersectedNode();
                int row = 0;
                int col = 0;
                boolean f = false;
                while (row < 10 && !f) {
                    col = 0;
                    while (col < 10 && !f) {
                        if (node == water[row][col].getChildren().get(1))
                            f = true;
                        else
                        if(water[row][col].getChildren().size() == 3 && node == water[row][col].getChildren().get(2) && node != focusTiles.get(0))
                            f = true;
                        else
                            col++;
                    }
                    if (!f)
                        row++;
                }
                if (f) {
                    Coord crd1 = new Coord(row, col);
                    Coord crd2 = new Coord((cur_direction == 'h') ? row : row + cur_size_ship - 1,
                                           (cur_direction == 'v') ? col : col + cur_size_ship - 1);
                    if (hp.checkCollision(crd1, crd2)) {
                        int shift;
                        if (cur_direction == 'h') {
                            shift = col + cur_size_ship;
                            for (int j = col; j < shift; j++) {
                                if(water[row][j].getChildren().size() != 3) {
                                    water[row][j].getChildren().add(focusTiles.get(j - col));
                                }
                                water[row][j].getChildren().get(2).setVisible(true);

                            }
                        } else {
                            shift = row + cur_size_ship;
                            for (int i = row; i < shift; i++) {
                                if(water[i][col].getChildren().size() != 3) {
                                    water[i][col].getChildren().add(focusTiles.get(i - row));
                                }
                                water[i][col].getChildren().get(2).setVisible(true);
                            }
                        }
                    }
                    else{
                        for(int k = 0; k < 4; k++) {
                            focusTiles.get(k).setVisible(false);
                        }
                    }
                }
            }
            event.consume();
        });

        board.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage() && hp.getEstabShip() != 10) {
                Node node = event.getPickResult().getIntersectedNode();
                int row = 0;
                int col = 0;
                boolean f = false;
                while (row < 10 && !f) {
                    col = 0;
                    while (col < 10 && !f) {
                        if (water[row][col].getChildren().size() == 3 && node == water[row][col].getChildren().get(2))
                            f = true;
                        else
                            col++;
                    }
                    if (!f)
                        row++;
                }
                if (f) {
                    Coord crd1 = new Coord(row, col);
                    Coord crd2 = new Coord((cur_direction == 'h') ? row : row + cur_size_ship - 1,
                                           (cur_direction == 'v') ? col : col + cur_size_ship - 1);

                    if (cur_size_ship > 1) {
                        Ship ship = new Ship(cur_size_ship, crd1, crd2, hp.getField());
                        ship.linkTilesWithDeck(hp.getField());
                        ship.linkTilesWithHalo(hp.getField());
                        hp.getField().getFleet().add(ship);
                    } else {
                        Ship ship = new Ship(cur_size_ship, crd1, hp.getField());
                        ship.linkTilesWithDeck(hp.getField());
                        ship.linkTilesWithHalo(hp.getField());
                        hp.getField().getFleet().add(ship);
                    }
                    hp.addEstabShip();
                    int shift;
                    for (int k = 0; k < cur_size_ship; k++) {
                        focusTiles.get(k).setVisible(false);
                    }
                    if (cur_direction == 'h') {
                        shift = col + cur_size_ship;
                        for (int j = col; j < shift; j++) {
                            water[row][j].getChildren().get(1).setVisible(false);
                            water[row][j].getChildren().get(0).setVisible(true);
                        }
                    } else {
                        shift = row + cur_size_ship;
                        for (int i = row; i < shift; i++) {
                            water[i][col].getChildren().get(1).setVisible(false);
                            water[i][col].getChildren().get(0).setVisible(true);
                        }
                    }
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
            }
        });

        for (ImageView ship : ships_img) {
            ship.setOnDragDetected(event -> {
                int i = ships_img.indexOf(ship);
                switch (i) {
                    case 0:
                    case 6:
                        cur_size_ship = 4;
                        break;
                    case 1:
                    case 2:
                    case 7:
                    case 8:
                        cur_size_ship = 3;
                        break;
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        cur_size_ship = 1;
                        break;
                    default:
                        cur_size_ship = 2;

                }
                if (i < 6 || (i > 11 && i < 16))
                    cur_direction = 'h';
                else
                    cur_direction = 'v';

                Dragboard db = ship.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                db.setDragView(ship.getImage(), 20, 20);
                content.putImage(ship.getImage());
                db.setContent(content);
                event.consume();
            });

            ship.setOnDragDone(event -> {
                TransferMode modeUsed = event.getTransferMode();
                if (modeUsed == TransferMode.COPY) {
                    if (cur_size_ship == 1)
                        ship.setVisible(false);
                    else {
                        int i = ships_img.indexOf(ship);
                        switch (i) {
                            case 0:
                            case 6:
                                ships_img.get(0).setVisible(false);
                                ships_img.get(6).setVisible(false);
                                break;
                            case 1:
                            case 7:
                                ships_img.get(1).setVisible(false);
                                ships_img.get(7).setVisible(false);
                                break;
                            case 2:
                            case 8:
                                ships_img.get(2).setVisible(false);
                                ships_img.get(8).setVisible(false);
                                break;
                            case 3:
                            case 9:
                                ships_img.get(3).setVisible(false);
                                ships_img.get(9).setVisible(false);
                                break;
                            case 4:
                            case 10:
                                ships_img.get(4).setVisible(false);
                                ships_img.get(10).setVisible(false);
                                break;
                            case 5:
                            case 11:
                                ships_img.get(5).setVisible(false);
                                ships_img.get(11).setVisible(false);
                                break;
                        }
                    }
                }
            });
        }
    }

}
