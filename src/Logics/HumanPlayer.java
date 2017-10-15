package Logics;
import Logics.coord.Coord;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(){super("NO_NAME");}
    public HumanPlayer(String name){ super(name); }
    // Checking that the distance between the tiles is equal to the size of the ship
    public boolean checkSize(final byte size, final Coord coord1, final Coord coord2){
        if (coord1.row - coord2.row != 0)
            return (size == (coord1.row - coord2.row + 1) || size ==((coord2.row - coord1.row + 1)));
        else
            return (size == (coord1.col - coord2.col + 1) || size == ((coord2.col - coord1.col + 1)));
    }

    // Manual placement of ships on the playing field
    public void setPlaceShip(Game game){
       /* system("CLS");
        HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
        COORD coordConsole;*/

        Coord bow, stern = new Coord();
        Ship ship;
        String bowInp = "Enter the coordinates of the BOW of the ";
        String sternInp = "Enter the coordinates of the STERN of the ";
        String typeShip = new String();
        //TypeShip type;

        for(int i = 4; i > 0; i--)
        for (int j = 1; j <= 5 - i; j++) {
 /*coordConsole = { 53, 0 };
            SetConsoleCursorPosition(hConsole, coordConsole);
            SetConsoleTextAttribute(hConsole, 11);*/

            System.out.print(name); //cout << name_;
           // game.printShipLocation((byte)46,(byte) 1, this);// числа не нужны
/*coordConsole = { 0, 13 };
            SetConsoleCursorPosition(hConsole, coordConsole);*/

            System.out.print(bowInp);//cout << bowInp;

           // type.setId(i); //решить проблему в enum!!!!!!!!!!!
            switch (i)
            {
                case 4: {
                    System.out.print("CARRIER(size = 4): ");//cout << "CARRIER(size = 4): ";
                    typeShip = "CARRIER(size = 4): ";
                    break;
                }
                case 3: {
                    System.out.print("SUBMARINE(size = 3): ");//cout << "SUBMARINE(size = 3): ";
                    typeShip = "SUBMARINE(size = 3): ";
                    break;
                }
                case 2: {
                    System.out.print("DESTROYER(size = 2): ");//cout << "DESTROYER(size = 2): ";
                    typeShip = "DESTROYER(size = 2): ";
                    break;
                }
                case 1: {
                    System.out.print("FRIGATE(size = 1): ");//cout << "FRIGATE(size = 1): ";
                    typeShip = "FRIGATE(size = 1): ";
                }
            }

            bow = getCoord();
            if (i > 1) {
                System.out.print(sternInp);//cout << sternInp;
                System.out.print(typeShip);//cout << typeShip;
                stern = getCoord();
            }
            if (i > 1) {
                while (((bow.row != stern.row) && (bow.col != stern.col)) || !checkSize((byte)i, bow, stern) || !checkCollision(bow, stern)) {
                    if ((bow.row == stern.row) || (bow.col == stern.col))
                        if (checkSize((byte)i, bow, stern))
                            System.out.println("When placing ships, they can not touch each other with sides and corners. Please enter coordinates again: ");
                    //cout << "When placing ships, they can not touch each other with sides and corners. Please enter coordinates again: " << endl;
                        else
                            System.out.println("The range of coordinates chosen is not the same as the size of the ship. Please enter them again: ");
                            //cout << "The range of coordinates chosen is not the same as the size of the ship. Please enter them again: " << endl;
                    else
                        System.out.println("Coordinates are incorrect. Please enter them again: " );
                        //cout << "Coordinates are incorrect. Please enter them again: " << endl;
                    System.out.print(bowInp + typeShip);//cout << bowInp << typeShip;
                    bow = getCoord();
                    System.out.println(sternInp + typeShip);//cout << sternInp << typeShip;
                    stern = getCoord();
                } // while (((bow.first != stern.first) && (bow.second != stern.second)) || !checkSize(i, bow, stern) || !checkCollision(bow, stern))
            } // if (i > 1)
            else
                while (!checkCollision(bow)) {
                    System.out.println("When placing ships, they can not touch each other with sides and corners. Please enter coordinates again: ");
                //cout << "When placing ships, they can not touch each other with sides and corners. Please enter coordinates again: " << endl;
                    System.out.println(bowInp + typeShip);
                    //cout << bowInp << typeShip;
                    bow = getCoord();
                }

            if (i > 1) {
                ship = new Ship((byte)i, bow, stern, field);
                ship.linkTilesWithDeck(field);
                ship.linkTilesWithHalo(field);
                field.fleet.add(ship);
            }
            else {
                ship = new Ship((byte)i, bow, field);
                ship.linkTilesWithDeck(field);
                ship.linkTilesWithHalo(field);
                field.fleet.add(ship);
            }
            //system("CLS");
        } // for (unsigned char j = 1; j <= 5 - i; j++)
        //game.printShipLocation((byte)46, (byte)1, this); //числа не нужны
        //system("CLS");
        field.initEmptyTiles();
    }

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
