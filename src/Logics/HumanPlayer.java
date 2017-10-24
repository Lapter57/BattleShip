package Logics;
import Logics.coord.Coord;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(){super(new StringBuilder("NO_NAME"));}
    public HumanPlayer(StringBuilder name){ super(name); }

    // Method for implementing the player's turn
    public boolean yourTurn(Game game, Player rival, Coord coord){
                    boolean hit = true;
                        if (enemyField.grid[coord.row][coord.col].getState() == ' ') {
                            if (enemyField.grid[coord.row][coord.col].getLinkShip() == null) {
                                enemyField.grid[coord.row][coord.col].setState('*');
                                rival.water[coord.row][coord.col].getChildren().get(3).setVisible(false);
                                hit = false;
                            }
                            else {
                                enemyField.grid[coord.row][coord.col].setState('x');
                                rival.water[coord.row][coord.col].getChildren().get(3).setVisible(false);
                                rival.water[coord.row][coord.col].getChildren().get(2).setVisible(false);
                                if (foundShip == null)
                                    foundShip = enemyField.grid[coord.row][coord.col].getLinkShip();
                                foundShip.setHit();

                                if (false == foundShip.stateОk()) {
                                    enemyField.shipDestroy();
                                    foundShip.setStateHalo(rival.water);
                                    //Check correct
                                    /*System.out.println();
                                    for(int i = 0; i < 10; i++) {
                                        for (int j = 0; j < 10; j++) {
                                            System.out.print(rival.field.grid[i][j].getState());
                                        }
                                        System.out.println();
                                    }*/
                                    rival.destroy(foundShip.getSizeShip());
                                    rival.updateCurStat();
                                    foundShip= null;
                                }
                                /*PauseTransition pause = new PauseTransition(Duration.seconds(1));
                                pause.play();*/
                            }
                        }
                        return  hit;
    }
}
