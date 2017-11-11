package Logics;

import Logics.coord.Coord;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.TextField;
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
    protected Text cur_stat;
    protected Field enemyField = new Field();
    protected Field field = new Field();
    protected Byte estabShip = 0;
    protected StringBuilder name;
    protected Ship foundShip = null;
    protected ArrayList<Byte> ships = new ArrayList<>(4);
    protected TextField namePlayer = new TextField();


    Player(StringBuilder name){
        this.name = name;
        byte j = 4;
        for(int i = 0; i < 4; i++)
            ships.add(j--);
        String str = "CARRIER: " + ships.get(3) + "\n" + "SUBMARINE: " + ships.get(2) + "\n" + "DESTROYED: " + ships.get(1) + "\n" + "FRIGATE: " + ships.get(0) + "\n";
        cur_stat = new Text(str);
        cur_stat.setFill(Color.valueOf("#455760"));
        cur_stat.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,30));

        stat_area.setMinHeight(135);
        stat_area.setMaxWidth(360);
    }

    void updateCurStat(){
        String str = "CARRIER: " + ships.get(3) + "\n" + "SUBMARINE: " + ships.get(2) + "\n" + "DESTROYED: " + ships.get(1) + "\n" +  "FRIGATE: " + ships.get(0) + "\n";
        cur_stat.setText(str);
    }

    boolean gameOver(){
        return field.numShipAfloat == 0;
    }

    public byte getNumShipAfloat(){
        return field.numShipAfloat;
    }

    void setEnemyField(Field field) {
        enemyField = field;
    }

    public String getName() {
        return name.toString();
    }

    Field getRefField() {
        return field;
    }

    void destroy(final byte sizeShip) {
        Byte temp = ships.get(sizeShip - 1);
        ships.set(sizeShip - 1, --temp);
    }

    private boolean checkCoord(final Coord ... coords){
        boolean check = true;
        for(Coord crd: coords){
            check = crd.row >=0 && crd.row < 10 && crd.col >=0 && crd.col < 10;
            if(!check)
                break;
        }
        return check;
    }

    private boolean nearShip(byte row, byte col){
        return checkCoord(new Coord(row,col)) && field.grid[row][col] != null && field.grid[row][col].getLinkShip() != null;
    }

    boolean checkCollision(final Coord coord1, final Coord coord2) {
        byte checkStart, checkEnd;
        char direction;

        if (coord1.row == coord2.row) {
            direction = 'h';
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
            direction = 'v';
            if (coord1.row < coord2.row) {
                checkStart = (byte)(coord1.row - 1);
                checkEnd = (byte)(coord2.row + 1);
            }
            else {
                checkStart = (byte)(coord2.row - 1);
                checkEnd = (byte)(coord1.row + 1);
            }
        }
        boolean check = false;
        if(checkCoord(coord1,coord2)) {
            check = true;
            byte i = (checkStart >= 0 && checkStart < 10) ? checkStart : ++checkStart;
            checkEnd = (checkEnd >= 0 && checkEnd < 10) ? checkEnd : --checkEnd;
            while (check && i <= checkEnd) {
                if (direction == 'h') {
                    if (nearShip(coord1.row, i) || nearShip((byte) (coord1.row + 1), i) || nearShip((byte) (coord1.row - 1), i))
                        check = false;
                } else if (nearShip(i, coord1.col) || nearShip(i, (byte) (coord1.col + 1)) || nearShip(i, (byte) (coord1.col - 1)))
                    check = false;
                i++;
            }
        }
        return check;
    }

    void setPlaceShipRand() {
        byte row, col, shift, stern,  way = 0, direction = 0;
        Coord crd1 = new Coord(), crd2 = new Coord();
        boolean established;
        Ship ship;

        for (byte i = 4; i > 0; i--)
            for (byte j = 1; j <= (5 - i); j++) {
                do {
                    row = (byte) new Random().nextInt(10);
                    col = (byte) new Random().nextInt(10);
                    if (i > 1) {
                        direction = (new Random().nextInt(2) == 0) ? (byte)1 : (byte)-1;
                        way = (new Random().nextInt(2) == 0) ? (byte)1 : (byte)-1;
                        shift = (direction == 1) ?  col : row;
                        stern = (byte) ( shift + way * (i - 1) );
                        if (!(stern >= 0 && stern < 10))
                            way *= -1;
                    }

                    if (direction == 1) {
                        crd1.row = row;
                        crd2.row = row;
                        crd1.col = col;
                        crd2.col = (byte)(col + way *(i - 1));
                        established = checkCollision(crd1, crd2);
                    }
                    else {
                        crd1.row = row;
                        crd2.row = (byte)(row + way * (i - 1));
                        crd1.col = col;
                        crd2.col = col;
                        established = checkCollision(crd1, crd2);
                    }
                } while (!established);


                if (i > 1) {
                    ship = new Ship(i, new Coord(crd1),new Coord(crd2), field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.fleet.add(ship);
                }
                else {
                    ship = new Ship(i, new Coord(crd1), field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.fleet.add(ship);
                }
            }
        field.initEmptyTiles();
    }
}
