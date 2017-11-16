package logics;

import graphics.BattlePane;
import graphics.BattlePreparePane;
import graphics.Graphic;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import logics.coord.Coord;
import logics.players.ComputerPlayer;
import logics.players.HumanPlayer;
import logics.players.Player;
import statistics.PlayerStats;


public class Game {
    private Boolean first_click_auto = true;
    private PlayerStats playerStats = new PlayerStats();
    private BattlePreparePane preparePane = new BattlePreparePane();
    private Graphic graphic;
    public enum Level{EASY,NORMAL,HARD,HUMAN}

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

    public void humanVsComputer(Pane gameHvsC) {

        HumanPlayer hp = new HumanPlayer();
        hp.setLevel(Level.NORMAL);
        prepareForBattle(hp, gameHvsC);

        ComputerPlayer cp = new ComputerPlayer();

        preparePane.createLevelBottoms(gameHvsC,hp,cp);
        preparePane.createReadyBottom(1100, 615, gameHvsC);

        preparePane.getReadyBottom().setOnMouseClicked(event -> {

            if (hp.getField().getFleet().size() == 10) {
                if(first_click_auto) {
                    hp.getField().initEmptyTiles();
                }

                gameHvsC.setVisible(false);
                BattlePane battlePane = new BattlePane(graphic, this, hp, cp);
                battlePane.createPlayingFields(hp, 150, 115, graphic);
                battlePane.createPlayingFields(cp, 750, 115, graphic);

                hp.checkName();
                hp.showLocationOfShips();

                cp.setPlaceShipRand();
                cp.setEnemyField(hp.getField());
                hp.setEnemyField(cp.getField());
                battlePane.useFocusTilesOnField(cp);
                cp.getGraphicField().getBoard().setOnMouseClicked(event1 -> {
                    Node node = event1.getPickResult().getIntersectedNode();
                    boolean found = false;
                    Coord coord = cp.searchPressedImg(node);
                    if(coord.row < 10){
                        found = true;
                    }
                    if (found) {
                        if (!hp.yourTurn(this, cp, coord)) {
                            hp.addShot();
                            graphic.getPointer().getChildren().get(1).setVisible(false);
                            graphic.getPointer().getChildren().get(0).setVisible(true);
                            cp.yourTurn(hp);
                            graphic.getPointer().getChildren().get(0).setVisible(false);
                            graphic.getPointer().getChildren().get(1).setVisible(true);
                            if (hp.gameOver()) {
                                cp.printShipLocation();
                            }
                        }
                        else{
                            hp.addShot();
                            if (cp.gameOver()) {
                                hp.printShipLocation();
                                cp.getGraphicField().getBoard().setDisable(true);
                                playerStats.updateStats(hp);
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

            if(first_click_auto) {
                hp1.getField().initEmptyTiles();
            }

            first_click_auto = true;

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
                        if (first_click_auto) {
                            hp2.getField().initEmptyTiles();
                        }
                        gameHvH2.setVisible(false);
                        BattlePane battlePane = new BattlePane(graphic, this, hp1, hp2);
                        battlePane.createPlayingFields(hp1, 150, 115, graphic);
                        battlePane.createPlayingFields(hp2, 750, 115, graphic);

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

                                if (!hp1.yourTurn(this, hp2, coord)) {
                                    hp1.addShot();
                                    graphic.getPointer().getChildren().get(1).setVisible(false);
                                    graphic.getPointer().getChildren().get(0).setVisible(true);

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

                                            if (!hp2.yourTurn(this, hp1, coord2)) {
                                                hp2.addShot();
                                                graphic.getPointer().getChildren().get(0).setVisible(false);
                                                graphic.getPointer().getChildren().get(1).setVisible(true);
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
}
