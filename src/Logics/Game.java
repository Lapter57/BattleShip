package Logics;
import Logics.coord.Coord;
import Logics.players.ComputerPlayer;
import Logics.players.HumanPlayer;
import Logics.players.Player;
import graphics.BattlePreparePane;
import graphics.Graphic;
import graphics.MainStPain;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import statistics.PlayerStats;


import java.util.ArrayList;

public class Game {
    private Boolean first_click_auto = true;
    private PlayerStats playerStats = new PlayerStats();
    private BattlePreparePane preparePane = new BattlePreparePane();
    private Graphic graphic;

    Game(Graphic graphic) {
        graphic.createStatsPane(playerStats);
        this.graphic = graphic;
    }

    public void clearBoard(Player player) {
        preparePane.clearWater();
        preparePane.repairShips();
        player.clearField();
    }

    public void setFirstClickAuto(Boolean fca) {
        first_click_auto = fca;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    private void prepareForBattle(HumanPlayer hp, Pane gameArea) {
        preparePane.prepare(hp, gameArea, this);
        hp.fillNameArea(gameArea);
    }
    public void createPlayingFields(Player pl, Pane playingFields, int b_x, int b_y) {

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                pl.getGraphicField().getWater()[i][j] = new StackPane();
                ImageView img_ot = new ImageView(Graphic.map_img.get("ot"));
                ImageView img_us = new ImageView(Graphic.map_img.get("us"));
                ImageView img_hs = new ImageView(Graphic.map_img.get("hs"));
                ImageView img_dt = new ImageView(Graphic.map_img.get("dt"));
                ImageView img_ds = new ImageView(Graphic.map_img.get("ds"));
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_us);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_ds);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_hs);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_dt);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_ot);
                pl.getGraphicField().getBoard().add(pl.getGraphicField().getWater()[i][j], j, i);
            }
        pl.getGraphicField().getBoard().setTranslateX(b_x);
        pl.getGraphicField().getBoard().setTranslateY(b_y);
        playingFields.getChildren().add(pl.getGraphicField().getBoard());

        ImageView numB = new ImageView(Graphic.map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX((b_x == 150) ? 105 : 705 + 495);
        numB.setTranslateY(115);
        playingFields.getChildren().add(numB);
        ImageView letB = new ImageView(Graphic.map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX((b_x == 150) ? 150 : 750);
        letB.setTranslateY(70);
        playingFields.getChildren().add(letB);


        pl.customizeCurrentStats(playingFields, b_x);
        pl.customizeNameArea(playingFields, b_x);



    }

    public void humanVsComputer(Pane gameHvC, MainStPain msp) {
        HumanPlayer hp = new HumanPlayer();
        hp.setLevel(1);
        prepareForBattle(hp, gameHvC);
      
        // Stop Refactoring
        ComputerPlayer cp = new ComputerPlayer();
        easy.setOnMouseClicked(event -> {
            cp.level.delete(0, cp.level.length());
            cp.level.append("easy");
            hp.setLevel(0);

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
            cp.level.delete(0, cp.level.length());
            cp.level.append("normal");
            hp.setLevel(1);

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
            cp.level.delete(0, cp.level.length());
            cp.level.append("hard");
            hp.setLevel(2);

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

        ready.setOnMouseClicked(event -> {
            if (hp.field.fleet.size() == 10) {
                if(first_click_auto) {
                    hp.field.initEmptyTiles();
                }

                Pane playingFields = new Pane();
                playingFields.setMaxHeight(720);
                playingFields.setMaxWidth(1280);
                msp.getChildren().add(playingFields);
                playingFields.setVisible(true);

                hp.checkName();
                cp.setPlaceShipRand();
                gameHvC.setVisible(false);
                cp.setEnemyField(hp.getRefField());
                hp.setEnemyField(cp.getRefField());
                createPointer(playingFields, msp, hp, cp);
                createPlayingFields(hp, playingFields, 150, 115);
                createPlayingFields(cp, playingFields, 750, 115);
                showLocationOfShips(hp);
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        int row = i;
                        int col = j;
                        cp.water[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                focusTiles.get(0).setVisible(true);
                                if (cp.water[row][col].getChildren().get(4).isVisible() && !cp.water[row][col].getChildren().contains(focusTiles.get(0)))
                                    cp.water[row][col].getChildren().add(focusTiles.get(0));
                                else
                                    focusTiles.get(0).setVisible(false);
                            }
                        });
                        cp.water[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                focusTiles.get(0).setVisible(false);
                            }
                        });
                    }
                }
                cp.board.setOnMouseClicked(event1 -> {
                    boolean f = false;
                    byte row;
                    byte col;
                    Coord coord;
                    Node node = event1.getPickResult().getIntersectedNode();
                    row = 0;
                    col = 0;
                    while (row < 10 && !f) {
                        col = 0;
                        while (col < 10 && !f) {
                            if (cp.water[row][col].getChildren().size() == 6 && node == cp.water[row][col].getChildren().get(5)) {
                                focusTiles.get(0).setVisible(false);
                                f = true;
                            }
                            else
                                col++;
                        }
                        if (!f)
                            row++;
                    }
                    if (f) {
                        coord = new Coord(row, col);
                        if (!hp.yourTurn(this, cp, coord)) {
                            pointer.getChildren().get(1).setVisible(false);
                            pointer.getChildren().get(0).setVisible(true);
                            cp.yourTurn(hp);
                            pointer.getChildren().get(0).setVisible(false);
                            pointer.getChildren().get(1).setVisible(true);
                            if (hp.gameOver()) {
                                printShipLocation(cp);
                                cp.board.setDisable(true);
                            }
                        }
                        else{
                            hp.addShot();
                            if (cp.gameOver()) {
                                printShipLocation(hp);
                                cp.board.setDisable(true);
                                playerStats.updateStats(hp);
                            }
                        }
                    }
                });
            }

        });

    }

    public void humanVsHuman(Pane gameHvH, MainStPain msp) {
        HumanPlayer hp1 = new HumanPlayer();
        hp1.setLevel(3);
        prepareForBattle(hp1, gameHvH, msp);
        Main.MenuItem next = new Main.MenuItem("NEXT PLAYER", 400, 50);
        next.setTranslateX(800);
        next.setTranslateY(615);
        gameHvH.getChildren().add(next);

        Pane gameHvH2 = new Pane();
        next.setOnMouseClicked(event -> {
            if(first_click_auto) {
                hp1.field.initEmptyTiles();
            }
            first_click_auto = true;
            if (hp1.field.fleet.size() == 10) {
                gameHvH.setVisible(false);
                for (int i = 0; i < 10; i++)
                    for (int j = 0; j < 10; j++)
                        water[i][j].getChildren().get(1).setVisible(true);
                for (ImageView img : ships_img) {
                    img.setVisible(true);
                }
                gameHvH2.setMaxHeight(720);
                gameHvH2.setMaxWidth(1280);
                msp.getChildren().add(gameHvH2);

                HumanPlayer hp2 = new HumanPlayer();
                hp2.setLevel(3);
                prepareForBattle(hp2, gameHvH2, msp);
                Main.MenuItem ready = new Main.MenuItem("BATTLE!", 400, 50);
                ready.setTranslateX(800);
                ready.setTranslateY(615);
                gameHvH2.getChildren().add(ready);

                Main.MenuItem bck = new Main.MenuItem("QUIT", 90, 90);
                bck.setTranslateX(0);
                bck.setTranslateY(630);
                bck.setOpacity(0.5);
                gameHvH2.getChildren().add(bck);

                bck.setOnMouseClicked(event1 -> {
                    clearBoard(hp1);
                    clearBoard(hp2);
                    removePane(gameHvH);
                    removePane(gameHvH2);
                    msp.menuBox.setVisible(true);
                    msp.title.setVisible(true);
                });


                ready.setOnMouseClicked(event2 -> {
                    if (hp2.field.fleet.size() == 10) {
                        if (first_click_auto) {
                            hp2.field.initEmptyTiles();
                        }
                        Pane playingFields = new Pane();
                        playingFields.setMaxHeight(720);
                        playingFields.setMaxWidth(1280);
                        msp.getChildren().add(playingFields);
                        playingFields.setVisible(true);
                        hp1.checkName();
                        hp2.checkName();
                        gameHvH2.setVisible(false);
                        hp1.setEnemyField(hp2.getRefField());
                        hp2.setEnemyField(hp1.getRefField());
                        createPointer(playingFields, msp, hp1, hp2);
                        createPlayingFields(hp1, playingFields, 150, 115);
                        createPlayingFields(hp2, playingFields, 750, 115);
                        hp2.board.setDisable(false);
                        hp1.board.setDisable(true);

                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                int row = i;
                                int col = j;
                                hp1.water[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(true);
                                        if (hp1.water[row][col].getChildren().get(4).isVisible() && !hp1.water[row][col].getChildren().contains(focusTiles.get(0)))
                                            hp1.water[row][col].getChildren().add(focusTiles.get(0));
                                        else
                                            focusTiles.get(0).setVisible(false);
                                    }
                                });
                                hp1.water[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(false);
                                    }
                                });

                                hp2.water[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(true);
                                        if (hp2.water[row][col].getChildren().get(4).isVisible() && !hp2.water[row][col].getChildren().contains(focusTiles.get(0)))
                                            hp2.water[row][col].getChildren().add(focusTiles.get(0));
                                        else
                                            focusTiles.get(0).setVisible(false);
                                    }
                                });
                                hp2.water[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(false);
                                    }
                                });
                            }
                        }

                        hp2.board.setOnMouseClicked(event1 -> {
                            boolean f = false;
                            byte row;
                            byte col;
                            Coord coord;
                            Node node = event1.getPickResult().getIntersectedNode();
                            row = 0;
                            col = 0;
                            while (row < 10 && !f) {
                                col = 0;
                                while (col < 10 && !f) {
                                    if (hp2.water[row][col].getChildren().size() == 6 && node == hp2.water[row][col].getChildren().get(5)) {
                                        focusTiles.get(0).setVisible(false);
                                        f = true;
                                    } else
                                        col++;
                                }
                                if (!f)
                                    row++;
                            }
                            if (f) {
                                coord = new Coord(row, col);

                                if (!hp1.yourTurn(this, hp2, coord)) {
                                    pointer.getChildren().get(1).setVisible(false);
                                    pointer.getChildren().get(0).setVisible(true);

                                    hp1.board.setDisable(false);
                                    hp2.board.setDisable(true);
                                    hp1.board.setOnMouseClicked(event3 -> {
                                        boolean f2 = false;
                                        byte r;
                                        byte c;
                                        Coord crd;
                                        Node nde = event3.getPickResult().getIntersectedNode();
                                        r = 0;
                                        c = 0;
                                        while (r < 10 && !f2) {
                                            c = 0;
                                            while (c < 10 && !f2) {
                                                if (hp1.water[r][c].getChildren().size() == 6 && nde == hp1.water[r][c].getChildren().get(5)) {
                                                    focusTiles.get(0).setVisible(false);
                                                    f2 = true;
                                                } else
                                                    c++;
                                            }
                                            if (!f2)
                                                r++;
                                        }
                                        if (f2) {
                                            crd = new Coord(r, c);

                                            if (!hp2.yourTurn(this, hp1, crd)) {
                                                pointer.getChildren().get(0).setVisible(false);
                                                pointer.getChildren().get(1).setVisible(true);
                                                hp2.board.setDisable(false);
                                                hp1.board.setDisable(true);
                                            } else {
                                                hp2.addShot();
                                                if (hp1.gameOver()) {
                                                    printShipLocation(hp2);
                                                    hp1.board.setDisable(true);
                                                    hp2.board.setDisable(true);
                                                    playerStats.updateStats(hp2);
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    hp1.addShot();
                                    if (hp2.gameOver()) {
                                        printShipLocation(hp1);
                                        hp1.board.setDisable(true);
                                        hp2.board.setDisable(true);
                                        playerStats.updateStats(hp1);
                                    }
                                }
                            }
                        });
                    }

                });

            }

        });
    }
}
