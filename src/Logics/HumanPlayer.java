package Logics;
import Logics.coord.Coord;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(){super(new StringBuilder("NO_NAME"));}
    public HumanPlayer(StringBuilder name){ super(name); }

    // Method for implementing the player's turn
    public void yourTurn(Game game, Player rival, byte id){
/*HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
        COORD coordConsole;*/

        Coord coord;
        boolean hit = true;

        do {
           /* if(id == 0)
                game.outputFields(this, rival);
            else
                game.outputFields(rival, this);*/

/*if (id == 0) {
                coordConsole = { 28, 13 };
                SetConsoleCursorPosition(hConsole, coordConsole);
            }
            else {
                coordConsole = { 76, 13 };
                SetConsoleCursorPosition(hConsole, coordConsole);
            }*/


            System.out.print("Your turn");//cout << "Your turn";
/*coordConsole = { 0, 14 };
            SetConsoleCursorPosition(hConsole, coordConsole);*/

            System.out.println("Now is shoot of " + this.getName() + ": ");
            //cout << "Now is shoot of " << this->getName() <<": ";
            coord = getCoord();

            if (enemyField.grid[coord.row][coord.col].getState() == ' ') {
                if (enemyField.grid[coord.row][coord.col].getLinkShip() == null) {
                    enemyField.grid[coord.row][coord.col].setState('*');
                    hit = false;
                }
			else {
                    enemyField.grid[coord.row][coord.col].setState('x');
                    if (foundShip == null)
                        foundShip = enemyField.grid[coord.row][coord.col].getLinkShip();
                    foundShip.setHit();

                    if (!foundShip.stateОk()) {
                        enemyField.shipDestroy();
                        foundShip.setStateHalo();
                        rival.destroy(foundShip.getSizeShip());
                        System.out.println("*** " + name + " destroed ");//cout <<"*** " << name_ << " destroed ";

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

                        foundShip= null;
                        System.out.println("of " + rival.getName() + "***");
                        //cout << "of " << rival->getName() << "***";
                    } // if (!foundShip_->stateОk())
                    else System.out.println("*** " + name + " hit the ship " + "of " + rival.getName() + "***");//cout << "*** " << name_ << " hit the ship " << "of " << rival->getName() << "***";
                    //Sleep(1000);!!!!!!!!!!!!!!!
                } // if (enemyField_->grid_[coord.first][coord.second]->getLinkShip() == nullptr) ELSE
            } // if (enemyField_->grid_[coord.first][coord.second]->getState() == ' ')
		else {
                System.out.println("You already shot this place. Please enter coordinates again:");
                // cout << "You already shot this place. Please enter coordinates again: ";
                //Sleep(1000);!!!!!!!!!!!!!!!!!
            }
        } while (hit && enemyField.numShipAfloat != 0);
    }

    // Overloaded assignment operator to change the player's name
    //void operator=(final String name) { name.this = name; }


    private static void swap(byte a, byte b){
        byte temp = a;
        a = b;
        b = temp;
    }
    // Method for getting coordinates for a shot!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public Coord getCoord(){
        Coord coord = new Coord();
        Scanner scanner = new Scanner(System.in);
        coord.row = scanner.nextByte();
        coord.col = scanner.nextByte();

        while (!(checkC(coord.row) && checkC(coord.col) && checkC(coord.row, coord.col) )) {
            System.out.println("Coordinates are incorrect. Please enter them again: ");
            coord.row = scanner.nextByte();
            coord.col = scanner.nextByte();
        }

        //Unicode!!!!!!!!!!

        if (coord.row >= 'A' && coord.row <= 'J') swap(coord.row, coord.col);
        if (coord.row >= 'a' && coord.row <= 'j') {
            coord.row -= 32;
            swap(coord.row, coord.col);
        }
        if(coord.row >= 'a' && coord.row <= 'j') coord.row -= 32;


        coord.col -= '0';
        coord.row -= 'A';
        return coord;
    };
}
