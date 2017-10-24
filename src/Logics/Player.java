package Logics;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import Logics.coord.Coord;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public abstract class Player {
    protected GridPane board = new GridPane(); {board.setPrefSize(10, 10);}
    protected StackPane[][] water = new StackPane[10][10];
    protected StackPane name_area = new StackPane();
    protected StackPane stat_area = new StackPane();
    {
        stat_area.setMinHeight(135);
        stat_area.setMaxWidth(360);
    }
    protected Text cur_stat;
    protected Field enemyField = new Field();
    protected Field field = new Field();
    protected Byte estabShip = 0;
    protected StringBuilder name;
    protected Ship foundShip = null;
    ArrayList<Byte> ships = new ArrayList<>(4);

  /*protected enum TypeShip{
        FRIGATE(1), DESTROYER(2), SUBMARINE(3), CARRIER(4);
        private int id;

        TypeShip(int id){
            this.id = id;
        }
         public void setId(int id){this.id = id;}



public static String getClassName() {
            return TypeShip.class.getName();
        }

    }*/


    public Player(StringBuilder name){
        this.name = name;
        byte j = 4;
        for(int i = 0; i < 4; i++)
            ships.add(j--);
        String str = "CARRIER: " + ships.get(3) + "\n" + "SUBMARINE: " + ships.get(2) + "\n" + "DESTROYED: " + ships.get(1) + "\n" + "FRIGATE: " + ships.get(0) + "\n";
        cur_stat = new Text(str);
        cur_stat.setFill(Color.valueOf("#455760"));
        cur_stat.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,30));
    }

    public void updateCurStat(){
        String str = "CARRIER: " + ships.get(3) + "\n" + "SUBMARINE: " + ships.get(2) + "\n" + "DESTROYED: " + ships.get(1) + "\n" +  "FRIGATE: " + ships.get(0) + "\n";
        cur_stat.setText(str);
    }
    // Checking the correctness of coordinates input
    public boolean checkC(final byte check){ return(check >=0 && check < 10); }
    public boolean checkC(final byte check1, final byte check2){
        return ( check1 >=0 && check1 < 10 && check2 >=0 && check2 < 10);
    }

    // Checking that the tile has not yet been shot
    //public boolean checkEmptyTile(final Coord coord){ return field.grid[coord.row][coord.col].getState() != ' '; }

    // The player did not lose until the number of ships afloat became zero
    public boolean gameOver(){ return field.numShipAfloat == 0; }

    // Abstract method for implementing the player's turn


    // Communication with the enemy field
    public void setEnemyField(Field field) { enemyField = field; }

    // Destruction of the number of ships of a certain type
    public void destroy(final byte sizeShip) {
        Byte temp = ships.get(sizeShip - 1);
        ships.set(sizeShip - 1, --temp);
    }
    // Getting a reference to the playing field
    Field getRefField() { return field; }

    // Getting the player's name
    public StringBuilder getName() { return name; }

    //-------------------------------------------------------------------------------------

    // Checking the correct location of ships ( not a Frigate (size == 1) )
    public boolean checkCollision(final Coord coord1, final Coord coord2) {
        byte checkStart, checkEnd;
        char disription;

        if (coord1.row == coord2.row) {
            disription = 'h';
            if (coord1.col < coord2.col) {
                checkStart = (byte)(coord1.col - 1);
                checkEnd = (byte)(coord2.col + 1);
            }
            else {
                checkStart = (byte)(coord2.col - 1);
                checkEnd = (byte)(coord1.col + 1);
            }
        }
        else {
            disription = 'v';
            if (coord1.row < coord2.row) {
                checkStart = (byte)(coord1.row - 1);
                checkEnd = (byte)(coord2.row + 1);
            }
            else {
                checkStart = (byte)(coord2.row - 1);
                checkEnd = (byte)(coord1.row + 1);
            }
        }

        if(checkC(coord1.col,coord1.row) && checkC(coord2.col,coord2.row)) {
            boolean check = true;
            byte i = checkStart;
            while (check && i <= checkEnd) {
                if (checkC(i)) {
                    if (disription == 'h') {
                        if (field.grid[coord1.row][i] != null && field.grid[coord1.row][i].getLinkShip() != null)
                            check = false;
                        if (check && checkC((byte) (coord1.row + 1)) && field.grid[coord1.row + 1][i] != null && field.grid[coord1.row + 1][i].getLinkShip() != null)
                            check = false;
                        if (check && checkC((byte) (coord1.row - 1)) && field.grid[coord1.row - 1][i] != null && field.grid[coord1.row - 1][i].getLinkShip() != null)
                            check = false;
                    } else {
                        if (field.grid[i][coord1.col] != null && field.grid[i][coord1.col].getLinkShip() != null)
                            check = false;
                        if (check && checkC((byte) (coord1.col + 1)) && field.grid[i][coord1.col + 1] != null && field.grid[i][coord1.col + 1].getLinkShip() != null)
                            check = false;
                        if (check && checkC((byte) (coord1.col - 1)) && field.grid[i][coord1.col - 1] != null && field.grid[i][coord1.col - 1].getLinkShip() != null)
                            check = false;
                    }
                } // if (checkC(i))
                i++;
            } // while (check && i <= checkEnd)
            return check;
        }
        return false;
    }

    //-------------------------------------------------------------------------------------

    // Checking the correct location of ships ( Frigate (size == 1) )
    boolean checkCollision(final Coord coord) {
        boolean check = true;
        byte i = (byte)(coord.col - 1);
        while (check && i <= coord.col + 1) {
            if (checkC(i)) {
                if (field.grid[coord.row][i] != null && field.grid[coord.row][i].getLinkShip() != null) check = false;
                if (check && checkC((byte)(coord.row + 1)) && field.grid[coord.row + 1][i] != null && field.grid[coord.row + 1][i].getLinkShip() != null) check = false;
                if (check && checkC((byte)(coord.row - 1)) && field.grid[coord.row - 1][i] != null && field.grid[coord.row - 1][i].getLinkShip() != null) check = false;
            }
            i++;
        }
        return check;
    }

    //-------------------------------------------------------------------------------------

    // Method that implements a random arrangement of ships on the playing field
    public void setPlaceShipRand() {
        byte row, col, shift, way = 0, direction = 0;
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
                        direction = (random.nextInt(2) == 0) ? (byte)1 : (byte)-1;
                        way = (random.nextInt(2) == 0) ? (byte)1 : (byte)-1;
                        shift = (direction == 1) ?  col : row;
                        if (!checkC((byte)(shift + way * (i - 1)))) way *= -1;
                    }

                    switch (i) {
                        case 1: {
                            coor1.row = row;
                            coor1.col = col;
                            OK = checkCollision(coor1);
                            break;
                        }
                        default:
                            if (direction == 1) {
                                coor1.row = row;
                                coor2.row = row;
                                coor1.col = col;
                                coor2.col = (byte)(col + way *(i - 1));
                                OK = checkCollision(coor1, coor2);
                            }
                            else {
                                coor1.row = row;
                                coor2.row = (byte)(row + way * (i - 1));
                                coor1.col = col;
                                coor2.col = col;
                                OK = checkCollision(coor1, coor2);
                            }
                    } // switch (i)
                } while (!OK);


                if (i > 1) {
                    ship = new Ship(i, new Coord(coor1),new Coord(coor2), field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.fleet.add(ship);
                }
                else {
                    ship = new Ship(i, new Coord(coor1), field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.fleet.add(ship);
                }
            } // for (unsigned char j = 1; j <= (5 - i); j++)
        field.initEmptyTiles();
    }
}
