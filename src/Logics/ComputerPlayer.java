package Logics;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.Collections;

import Logics.coord.Coord;

public class ComputerPlayer extends Player {
    private ArrayList<Coord> coordForStep = new ArrayList<>();
    private ArrayList<Coord> coordForToShell = new ArrayList<>();
    private ArrayDeque<Coord> queue = new ArrayDeque<>(); // можно ли поменять?
    private Byte sizeShip = 4;
    private boolean firstHit = true;

    Coord firstDestTile;

    // Constructor and destructor
    public ComputerPlayer(String name){
        super(name);
    }
    public ComputerPlayer(){
        super("Computer");
    }

    // Method that generates coordinates for a shot based on a diagonal strategy
    public void updateCoord(final byte search){
        byte minD = 0, maxD = 0, step = 0;

        switch (search){ // Enum problem
            case 4: {
                minD = 3;
                maxD = 15;
                step = 4;
                break;
            }
            case 3: {
                minD = 1;
                maxD = 17;
                step = 4;
                break;
            }
            case 1: {
                minD = 0;
                maxD = 18;
                step = 2;
            }
        }

        byte num_tiles_diag;
        byte row;
        Coord coord = new Coord();

        for (byte diag = minD; diag <= maxD; diag += step) {
            if (diag <= 9)  num_tiles_diag = (byte)(diag + 1); else num_tiles_diag = (byte)(9 - diag % 10);
            for (byte tile_num = 0; tile_num < num_tiles_diag; ++tile_num) {
                if (diag <= 9) row = tile_num; else  row =(byte) (tile_num + diag - 9);
                if (enemyField.grid[row][diag - row].getState() == ' ') {
                    coord.col = row;
                    coord.row = (byte)(diag - row);
                    coordForStep.add(coord);
                } // if (enemyField_->grid_[row][diag - row]->getState() == ' ')
            } // for (unsigned char tile_num = 0; tile_num < num_tiles_diag; ++tile_num)
        } // for (unsigned char diag = minD; diag <= maxD; diag += step)

        Collections.shuffle(coordForStep);
        //random_shuffle(coordForStep_.begin(), coordForStep_.end(), myrandom);
    }

    // Method for implementing the player's turn
    public void yourTurn(Game game, Player rival, byte id){
      /*  HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
        COORD coordConsole;*/
        Coord coord = new Coord();
        Coord tempcrd = new Coord();
        byte offset = 1;
        boolean check = true;
        boolean hit = true;

        do {
            /*if(id == 0)
                game.outputFields(this, rival);
            else
                game.outputFields(rival, this);*/

           /* if (id == 0) {
                coordConsole = { 28, 13 };
                SetConsoleCursorPosition(hConsole, coordConsole);
            }
            else {
                coordConsole = { 76, 13 };
                SetConsoleCursorPosition(hConsole, coordConsole);
            }*/

            System.out.print("Your turn");
            //cout << "Your turn";
            //Sleep(1000);!!!!!!
           /* coordConsole = { 0, 14 };
            SetConsoleCursorPosition(hConsole, coordConsole);*/

            if (!queue.isEmpty())
                coord = queue.pop();
            else
                if(coordForToShell.isEmpty()) coord = getCoord(); else coord = coordForToShell.get(0);

            if (enemyField.grid[coord.row][coord.col].getLinkShip() == null) {
                enemyField.grid[coord.row][coord.col].setState('*');
                if(!coordForToShell.isEmpty())
                    coordForToShell.remove(coordForToShell.get(0));
                hit = false;
                if (queue.isEmpty() && !check) {
                    offset *= (-3);
                    if (coord.row == firstDestTile.row)
                        firstDestTile.col += offset;
                    else
                        firstDestTile.row += offset;
                    queue.addLast(firstDestTile); // enqueue!!!!!!
                } // if (queue_.empty() && !check)
            } // if (enemyField_->grid_[coord.first][coord.second]->getLinkShip() == nullptr)
		else {
                enemyField.grid[coord.row][coord.col].setState('x');

                if(foundShip == null)
                    foundShip = enemyField.grid[coord.row][coord.col].getLinkShip();
                foundShip.setHit();

                if (!foundShip.stateОk()) {
                    enemyField.shipDestroy();
                    foundShip.setStateHalo();
                    rival.destroy(foundShip.getSizeShip());
                    System.out.print("***" + name + " destroyed ");
                    //cout  <<"***"<< name_ << " destroyed ";
                    switch (foundShip.getSizeShip()) { // решить проблему с enum, как вариант отказать от него
                        case 4:
                            System.out.println("CARRIER ");
                            //cout << "CARRIER ";
                            break;
                        case 3:
                            System.out.println("SUBMARINE ");
                            //cout << "SUBMARINE ";
                            break;
                        case 2:
                            System.out.println("DESTROYER ");
                            //cout << "DESTROYER ";
                            break;
                        case 1:
                            System.out.println("FRIGATE ");
                            //cout << "FRIGATE ";
                    }
                    System.out.println("of " + rival.getName() + "***");
                    //cout << "of " << rival->getName() <<"***";
                    foundShip = null;
                    firstHit = true;
                    check = true;
                    if (!queue.isEmpty()) queue.clear();
                    if (!coordForToShell.isEmpty())
                        coordForToShell.clear();
                    //Sleep(1000);!!!!!!!!!!!!!!!!!!!!!!!!
                } // if (!foundShip_->stateОk())
                else {
                    if (firstHit) {
                        tempcrd.col = coord.col;
                        for (int i = 0; i < 2; i++) {
                            tempcrd.row = (byte)(coord.row + offset);
                            if (checkC(tempcrd.row) && enemyField.grid[tempcrd.row][tempcrd.col].getState() == ' ')
                            coordForToShell.add(tempcrd);
                            offset *= -1;
                        } // for (int i = 0; i < 2; i++)

                        tempcrd.row = coord.row;
                        for (int i = 0; i < 2; i++) {
                            tempcrd.col = (byte)(coord.col + offset);
                            if (checkC(tempcrd.col) && enemyField.grid[tempcrd.row][tempcrd.col].getState() == ' ')
                            coordForToShell.add(tempcrd);
                            offset *= -1;
                        } // for (int i = 0; i < 2; i++)

                        firstDestTile = coord;
                        Collections.shuffle(coordForToShell);
                        //random_shuffle(coordForToShell_.begin(), coordForToShell_.end(), myrandom);
                        firstHit = false;
                    } // if (firstHit_)
                    else {
                        if (!coordForToShell.isEmpty())
                            coordForToShell.clear();

                        if (queue.isEmpty()){
                            if (coord.row == firstDestTile.row) {
                                offset = (coord.col < firstDestTile.col) ?  (byte)-1 :(byte) 1;
                                if (check) {
                                    coord.col += offset;
                                    if (checkC(coord.col) && enemyField.grid[coord.row][coord.col].getState() == ' ')
                                    queue.addLast(coord);//enqueue
                                    offset *= -1;
                                    tempcrd = firstDestTile;
                                    tempcrd.col += offset;
                                    if (checkC(tempcrd.col) && enemyField.grid[tempcrd.row][tempcrd.col].getState() == ' ')
                                    queue.addLast(tempcrd);//enqueue
                                    check = false;
                                }
                                else {
                                    coord.col += offset;
                                    queue.addLast(coord);//enqueue
                                }
                            }
                            else {
                                offset = (coord.row < firstDestTile.row) ? (byte) -1 : (byte)1;
                                if (check) {
                                    coord.row += offset;
                                    if (checkC(coord.row) && enemyField.grid[coord.row][coord.col].getState() == ' ')
                                    queue.addLast(coord);
                                    offset *= -1;
                                    tempcrd = firstDestTile;
                                    tempcrd.row += offset;
                                    if (checkC(tempcrd.row) && enemyField.grid[tempcrd.row][tempcrd.col].getState() == ' ')
                                    queue.addLast(tempcrd);
                                    check = false;
                                }
                                else {
                                    coord.row += offset;
                                    queue.addLast(coord);
                                }
                            } // if (coord.first == firstDestTile_.first) ELSE
                        } // if (queue_.empty())
                    } // if (firstHit_) ELSE
                } // if (!foundShip_->stateОk()) ELSE
            } // if (enemyField_->grid_[coord.first][coord.second]->getLinkShip() == nullptr) ELSE
        } while (hit && enemyField.numShipAfloat != 0);
    }

    // Method for getting coordinates for a shot
    public Coord getCoord(){
        while (coordForStep.isEmpty() || enemyField.grid[coordForStep.get(0).row][coordForStep.get(0).col].getState() != ' ') {
            if (coordForStep.isEmpty()) {
                updateCoord(sizeShip);
                sizeShip = (sizeShip == 4) ?  (byte)3 : (byte) 1;
            }
            else
                coordForStep.remove(coordForStep.get(0));
        }
        Coord coord = coordForStep.get(0);
        coordForStep.remove(coordForStep.get(0));
        return coord;
    }
}
