package com.lapter57.logics;

import com.lapter57.graphics.BattlePane;
import com.lapter57.graphics.BattlePreparePane;
import com.lapter57.graphics.Graphic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import com.lapter57.logics.coord.Coord;
import com.lapter57.logics.players.ComputerPlayer;
import com.lapter57.logics.players.HumanPlayer;
import com.lapter57.logics.players.Player;
import com.lapter57.statistics.PlayerStats;
import org.springframework.stereotype.Component;

@Component
public class Game {
    private final PlayerStats playerStats;
    private final BattlePreparePane preparePane;
    private final Graphic graphic;
    private boolean turnOfComp = false;
    private boolean gameOver = false;
    private Timeline fxTimer;

    public enum Level {
        EASY(0),
        NORMAL(1),
        HARD(2),
        HUMAN(3);

        private final int id;

        Level(final int id) {
            this.id = id;
        }

        public static Level valueOf(final int id) {
            return switch (id) {
                case 0 -> EASY;
                case 1 -> NORMAL;
                case 2 -> HARD;
                case 3 -> HUMAN;
                default -> throw new IllegalArgumentException("Unexpected value: " + id);
            };
        }
    }

    public Game(final Graphic graphic,
                final PlayerStats playerStats,
                final BattlePreparePane preparePane) {
        graphic.createStatsPane(playerStats);
        this.graphic = graphic;
        this.preparePane = preparePane;
        this.playerStats = playerStats;
    }

    public void setTurnOfComp(boolean turnOfComp) {
        this.turnOfComp = turnOfComp;
    }

    public BattlePreparePane getPreparePane() {
        return preparePane;
    }

    public void clearBoard(Player player) {
        preparePane.clearWater();
        preparePane.repairShips();
        player.clearField();
    }

    public Graphic getGraphic() {
        return graphic;
    }

    private void prepareForBattle(HumanPlayer hp, Pane gameArea) {
        preparePane.prepare(hp, gameArea, this);
        hp.fillNameArea(gameArea);
    }

    public void humanVsComputer(Pane gameHvsC) {

        HumanPlayer hp = new HumanPlayer();
        hp.setLevel(Level.NORMAL);
        prepareForBattle(hp, gameHvsC);

        ComputerPlayer cp = new ComputerPlayer();

        preparePane.createLevelBottoms(gameHvsC,hp,cp);
        preparePane.createReadyBottom(1100, 615, gameHvsC);

        preparePane.getReadyBottom().setOnMouseClicked(event -> {

            if (hp.getField().getFleet().size() == 10) {
                hp.getField().initEmptyTiles();
                gameHvsC.setVisible(false);
                hp.checkName();
                BattlePane battlePane = new BattlePane(graphic, this, hp, cp);
                battlePane.createPlayingFields(hp, 150, 115, graphic);
                battlePane.createPlayingFields(cp, 750, 115, graphic);

                graphic.getPointer().getChildren().clear();
                ImageView gp = new ImageView(Graphic.map_img.get("gp"));
                graphic.getPointer().getChildren().add(gp);
                gp.setTranslateX(45);
                gp.setTranslateY(-20);
                hp.showLocationOfShips();

                cp.setPlaceShipRand();
                cp.setEnemyField(hp.getField());
                hp.setEnemyField(cp.getField());
                battlePane.useFocusTilesOnField(cp);
                gameOver = false;
                if(fxTimer != null)
                    fxTimer.stop();
                fxTimer = new Timeline(new KeyFrame(Duration.millis(600), event2 -> {
                    if(!gameOver) {
                        if (turnOfComp) {
                            if (!hp.gameOver()) {
                                cp.yourTurn(hp, this);
                            } else {
                                cp.printShipLocation();
                                gameOver = true;
                                turnOfComp = false;
                            }
                        } else {
                            cp.getGraphicField().getBoard().setDisable(false);
                            if (!graphic.greenPoint) {
                                graphic.getPointer().getChildren().clear();
                                graphic.getPointer().getChildren().add(Graphic.animation.getImagePointers(175, 1));
                                graphic.getPointer().getChildren().get(0).setTranslateX(30);
                                graphic.getPointer().getChildren().get(0).setTranslateY(-30);
                                Graphic.animation.playPointer();
                                graphic.greenPoint = true;
                            }
                        }
                    }
                }));
                fxTimer.setCycleCount(Timeline.INDEFINITE);
                fxTimer.play();

                cp.getGraphicField().getBoard().setOnMouseClicked(event1 -> {
                    Node node = event1.getPickResult().getIntersectedNode();
                    boolean found = false;
                    Coord coord = cp.searchPressedImg(node);
                    if(coord.row < 10){
                        found = true;
                    }
                    if (found) {
                        if (!hp.yourTurn(cp, coord, this)) {
                            hp.addShot();
                            cp.getGraphicField().getBoard().setDisable(true);

                            graphic.getPointer().getChildren().clear();
                            graphic.getPointer().getChildren().add(Graphic.animation.getImagePointers(0, 1));
                            graphic.getPointer().getChildren().get(0).setTranslateX(25);
                            graphic.getPointer().getChildren().get(0).setTranslateY(-30);
                            Graphic.animation.playPointer();
                            graphic.greenPoint = false;

                        }
                        else{
                            hp.addShot();
                            if (cp.gameOver()) {
                                hp.printShipLocation();
                                cp.getGraphicField().getBoard().setDisable(true);
                                playerStats.updateStats(hp);
                                gameOver = true;
                                turnOfComp = false;
                            }
                        }
                    }
                });
            }

        });

    }

    public void humanVsHuman(Pane gameHvH) {
        HumanPlayer hp1 = new HumanPlayer();
        hp1.setLevel(Level.HUMAN);
        prepareForBattle(hp1, gameHvH);
        preparePane.createNextBottom(800, 615, gameHvH);

        Pane gameHvH2 = new Pane();
        preparePane.getNextBottom().setOnMouseClicked(event -> {
            hp1.getField().initEmptyTiles();
            if (hp1.getField().getFleet().size() == 10) {
                preparePane.getReadyBottom().changeSize(400, 50);
                gameHvH.setVisible(false);
                gameHvH2.setMaxHeight(720);
                gameHvH2.setMaxWidth(1280);
                graphic.getMainStPain().getChildren().add(gameHvH2);

                HumanPlayer hp2 = new HumanPlayer();
                hp2.setLevel(Level.HUMAN);
                prepareForBattle(hp2, gameHvH2);
                preparePane.createReadyBottom(800, 615, gameHvH2);
                preparePane.changeQuitBottom(this, new Pane[]{gameHvH, gameHvH2}, hp1, hp2);

                preparePane.getReadyBottom().setOnMouseClicked(event2 -> {
                    if (hp2.getField().getFleet().size() == 10) {
                        hp2.getField().initEmptyTiles();
                        gameHvH2.setVisible(false);
                        BattlePane battlePane = new BattlePane(graphic, this, hp1, hp2);
                        battlePane.createPlayingFields(hp1, 150, 115, graphic);
                        battlePane.createPlayingFields(hp2, 750, 115, graphic);

                        graphic.getPointer().getChildren().clear();
                        ImageView gp = new ImageView(Graphic.map_img.get("gp"));
                        graphic.getPointer().getChildren().add(gp);
                        gp.setTranslateX(45);
                        gp.setTranslateY(-20);

                        hp1.checkName();
                        hp2.checkName();
                        hp1.setEnemyField(hp2.getField());
                        hp2.setEnemyField(hp1.getField());
                        battlePane.useFocusTilesOnField(hp1, hp2);

                        hp2.getGraphicField().getBoard().setDisable(false);
                        hp1.getGraphicField().getBoard().setDisable(true);



                        hp2.getGraphicField().getBoard().setOnMouseClicked(event1 -> {
                            Node node = event1.getPickResult().getIntersectedNode();
                            boolean found = false;
                            Coord coord = hp2.searchPressedImg(node);
                            if(coord.row < 10){
                                found = true;
                            }

                            if (found) {

                                if (!hp1.yourTurn(hp2, coord, this)) {
                                    hp1.addShot();

                                    graphic.getPointer().getChildren().clear();
                                    graphic.getPointer().getChildren().add(Graphic.animation.getImagePointers(0, 1));
                                    graphic.getPointer().getChildren().get(0).setTranslateX(25);
                                    graphic.getPointer().getChildren().get(0).setTranslateY(-30);
                                    Graphic.animation.playPointer();

                                    hp1.getGraphicField().getBoard().setDisable(false);
                                    hp2.getGraphicField().getBoard().setDisable(true);

                                    hp1.getGraphicField().getBoard().setOnMouseClicked(event3 -> {
                                        Node node2 = event1.getPickResult().getIntersectedNode();
                                        boolean found2 = false;
                                        Coord coord2 = hp1.searchPressedImg(node2);
                                        if(coord2.row < 10){
                                            found2 = true;
                                        }

                                        if (found2) {

                                            if (!hp2.yourTurn(hp1, coord2, this)) {
                                                hp2.addShot();
                                                graphic.getPointer().getChildren().clear();
                                                graphic.getPointer().getChildren().add(Graphic.animation.getImagePointers(175, 1));
                                                graphic.getPointer().getChildren().get(0).setTranslateX(30);
                                                graphic.getPointer().getChildren().get(0).setTranslateY(-30);
                                                Graphic.animation.playPointer();

                                                hp2.getGraphicField().getBoard().setDisable(false);
                                                hp1.getGraphicField().getBoard().setDisable(true);
                                            } else {
                                                hp2.addShot();
                                                if (hp1.gameOver()) {
                                                    hp2.printShipLocation();
                                                    hp1.getGraphicField().getBoard().setDisable(true);
                                                    playerStats.updateStats(hp2);
                                                }
                                            }
                                        }
                                    });

                                } else {
                                    hp1.addShot();
                                    if (hp2.gameOver()) {
                                        hp1.printShipLocation();
                                        hp2.getGraphicField().getBoard().setDisable(true);
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

    public void customizeGameMods() {
        graphic.customizeGameMods(this);
    }
}
