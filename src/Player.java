import java.util.ArrayList;
import java.util.Random;
import coord.Coord;

public abstract class Player {
    protected Field enemyField;
    protected Field field;
    protected Ship foundShip = null;
    protected String name = "NO_NAME";
    ArrayList<Byte> ships = new ArrayList<>(4);
    protected enum TypeShip{
        FRIGATE(1), DESTROYER(2), SUBMARINE(3), CARRIER(4);
        private int id;

        TypeShip(int id){
            this.id = id;
        }

        /*public static String getClassName() {
            return TypeShip.class.getName();
        }*/
    }

    public Player(String name){
        this.name = name;
        byte j = 4;
        for(int i = 0; i < 4; i++)
            ships.add(j--);
        /*for (Byte el: ships)
            el = j--;*/ // проверить работает или нет
    }

    // Checking the correctness of coordinates input
    public boolean checkC(final byte check){ return((check >= '0' && check <= '9') || (check >= 'A' && check <= 'J') || (check >= 'a' && check <= 'j') || ((check >=  0 && check <= 9))); }
    public boolean checkC(final byte check1, final byte check2){
        return ( ((check1 >= '0' && check1 <= '9') && ((check2 >= 'A' && check2 <= 'J') || (check2 >= 'a' && check2 <= 'j'))) || ((check2 >= '0' && check2 <= '9') && ((check1 >= 'A' && check1 <= 'J') || (check1 >= 'a' && check1 <= 'j'))) );
    }

    // Checking that the tile has not yet been shot
    public boolean checkEmptyTile(final Coord coord){ return field.grid[coord.col][coord.row].getState() != ' '; }

    // The player did not lose until the number of ships afloat became zero
    public boolean gameOver(){ return field.numShipAfloat == 0; }

    // Abstract method for implementing the player's turn

    public abstract void yourTurn(Game game, Player rival, byte id);

    // Abstract method for getting coordinates for a shot
    public abstract Coord getCoord();

    // Communication with the enemy field
    public void setEnemyField(Field field) { enemyField = field; }

    // Destruction of the number of ships of a certain type
    public void destroy(final byte sizeShip) {
        Byte temp = ships.get(sizeShip - 1);
        ships.set(sizeShip - 1, --temp);
    }
    // Getting a pointer to the playing field
    Field getPointField() { return field; }

    // Getting the player's name
    public String getName() { return name; }

    //-------------------------------------------------------------------------------------

    // Checking the correct location of ships ( not a Frigate (size == 1) )
    public boolean checkCollision(final Coord coord1, final Coord coord2) {
        byte checkStart, checkEnd;
        char disription;

        if (coord1.col == coord2.col) {
            disription = 'h';
            if (coord1.row < coord2.row) {
                checkStart = (byte)(coord1.row - 1);
                checkEnd = (byte)(coord2.row + 1);
            }
            else {
                checkStart = (byte)(coord2.row - 1);
                checkEnd = (byte)(coord1.row + 1);
            }
        }
        else {
            disription = 'v';
            if (coord1.col < coord2.col) {
                checkStart = (byte)(coord1.col - 1);
                checkEnd = (byte)(coord2.col + 1);
            }
            else {
                checkStart = (byte)(coord2.col - 1);
                checkEnd = (byte)(coord1.col + 1);
            }
        }

        boolean check = true;
        byte i = checkStart;
        while (check && i <= checkEnd) {
            if (checkC(i)) {
                if (disription == 'h') {
                    if (field.grid[coord1.col][i] != null && field.grid[coord1.col][i].getLinkShip() != null) check = false;
                    if (check && checkC((byte)(coord1.col + 1)) && field.grid[coord1.col + 1][i] != null && field.grid[coord1.col + 1][i].getLinkShip() != null) check = false;
                    if (check && checkC((byte)(coord1.col - 1)) && field.grid[coord1.col - 1][i] != null && field.grid[coord1.col - 1][i].getLinkShip() != null) check = false;
                }
                else {
                    if (field.grid[i][coord1.row] != null && field.grid[i][coord1.row].getLinkShip() != null) check = false;
                    if (check && checkC((byte)(coord1.row + 1)) && field.grid[i][coord1.row + 1] != null && field.grid[i][coord1.row + 1].getLinkShip() != null) check = false;
                    if (check && checkC((byte)(coord1.row - 1)) && field.grid[i][coord1.row - 1] != null && field.grid[i][coord1.row - 1].getLinkShip() != null) check = false;
                }
            } // if (checkC(i))
            i++;
        } // while (check && i <= checkEnd)
        return check;
    }

    //-------------------------------------------------------------------------------------

    // Checking the correct location of ships ( Frigate (size == 1) )
    boolean checkCollision(final Coord coord) {
        boolean check = true;
        byte i = (byte)(coord.row - 1);
        while (check && i <= coord.row + 1) {
            if (checkC(i)) {
                if (field.grid[coord.col][i] != null && field.grid[coord.col][i].getLinkShip() != null) check = false;
                if (check && checkC((byte)(coord.col + 1)) && field.grid[coord.col + 1][i] != null && field.grid[coord.col + 1][i].getLinkShip() != null) check = false;
                if (check && checkC((byte)(coord.col - 1)) && field.grid[coord.col - 1][i] != null && field.grid[coord.col - 1][i].getLinkShip() != null) check = false;
            }
            i++;
        }
        return check;
    }

    //-------------------------------------------------------------------------------------

    // Method that implements a random arrangement of ships on the playing field
    public void setPlaceShipRand() {
        byte row, col, way, shift;
        char direction;
        Coord coor1 = new Coord(), coor2 = new Coord();
        boolean OK = false;
        Ship ship;
        final Random random = new Random();

        for (byte i = 4; i > 0; i--)
            for (byte j = 1; j <= (5 - i); j++) {
                do {
                    row = (byte) random.nextInt(10);
                    col = (byte) random.nextInt(10);
                    if (i > 1) {
                        direction = (random.nextInt(2) == 0) ? (char)1 : (char)-1;
                        way = (random.nextInt(2) == 0) ? (byte)1 : (byte)-1;
                        shift = (direction == 1) ?  col : row;
                        if (!checkC((byte)(shift + way * (i - 1)))) way *= -1;
                    }

                    switch (i) {
                        case 1: {
                            coor1.col = row;
                            coor1.row = col;
                            OK = checkCollision(coor1);
                            break;
                        }
                        default:
                            if (direction == 1) {
                                coor1.col = row;
                                coor2.col = row;
                                coor1.row = col;
                                coor2.row = (byte)(col + way *(i - 1));
                                OK = checkCollision(coor1, coor2);
                            }
                            else {
                                coor1.col = row;
                                coor2.col = (byte)(row + way * (i - 1));
                                coor1.row = col;
                                coor2.row = col;
                                OK = checkCollision(coor1, coor2);
                            }
                    } // switch (i)
                } while (!OK);


                if (i > 1) {
                    ship = new Ship(i, coor1, coor2, field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.fleet.add(ship);
                }
                else {
                    ship = new Ship(i, coor1, field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.fleet.add(ship);
                }
            } // for (unsigned char j = 1; j <= (5 - i); j++)
        field.initEmptyTiles();
    }
}