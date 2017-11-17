package logics.players;

import graphics.CurrentStats;
import graphics.Graphic;
import graphics.GraphicField;
import graphics.NameArea;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logics.Field;
import logics.Ship;
import logics.coord.Coord;

import java.util.ArrayList;
import java.util.Random;


public abstract class Player {
    protected GraphicField graphicField = new GraphicField();
    protected NameArea nameArea;
    protected CurrentStats currentStats;
    protected Field enemyField = new Field();
    protected Field field = new Field();
    protected int estabShip = 0;
    protected StringBuilder name;
    protected Ship foundShip = null;
    protected ArrayList<Integer> ships = new ArrayList<>(4);


    Player(StringBuilder name){
        this.name = name;
        int j = 4;
        for(int i = 0; i < 4; i++)
            ships.add(j--);
        currentStats = new CurrentStats(this);
        nameArea = new NameArea(this);
    }

    public ArrayList<Integer> getShips() {
        return ships;
    }

    void updateCurStat(){
        currentStats.update();
    }

    public void customizeCurrentStats(Pane playingFields, int b_x){
        currentStats.customize(playingFields, b_x);
    }

    public void customizeNameArea(Pane playingFields, int b_x){
        nameArea.customize(playingFields, b_x);
    }

    public boolean gameOver(){
        return field.getNumShipAfloat() == 0;
    }

    public int getNumShipAfloat(){
        return field.getNumShipAfloat();
    }

    public void setEnemyField(Field field) {
        enemyField = field;
    }

    public String getName() {
        return name.toString();
    }

    TextField getNamePlayer(){
        return nameArea.getNamePlayer();
    }

    public Field getField() {
        return field;
    }

    public int getEstabShip() {
        return estabShip;
    }

    public void addEstabShip(){
        estabShip++;
    }

    public GraphicField getGraphicField() {
        return graphicField;
    }

    public Coord searchPressedImg(Node node){
        return graphicField.searchPressedImg(node);
    }

    void destroy(final int sizeShip) {
        int temp = ships.get(sizeShip - 1);
        ships.set(sizeShip - 1, --temp);
    }

    public void clearField(){
        field.clearField();
        estabShip = 0;
    }

    public void fillNameArea(Pane gameArea){
        nameArea.fillNameArea(gameArea);
    }

    public void printShipLocation() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field.getGrid()[i][j].getLinkShip() != null && field.getGrid()[i][j].getState() != 'x') {
                    ImageView img_ss = new ImageView(Graphic.map_img.get("ss"));
                    graphicField.getWater()[i][j].getChildren().add(img_ss);
                }
            }
        }

        graphicField.getBoard().setDisable(true);
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

    private boolean nearShip(int row, int col){
        return checkCoord(new Coord(row,col)) && field.getGrid()[row][col] != null && field.getGrid()[row][col].getLinkShip() != null;
    }

    public boolean checkCollision(final Coord coord1, final Coord coord2) {
        int checkStart, checkEnd;
        char direction;

        if (coord1.row == coord2.row) {
            direction = 'h';
            if (coord1.col < coord2.col) {
                checkStart = coord1.col - 1;
                checkEnd = coord2.col + 1;
            }
            else {
                checkStart = coord2.col - 1;
                checkEnd = coord1.col + 1;
            }
        }
        else {
            direction = 'v';
            if (coord1.row < coord2.row) {
                checkStart = coord1.row - 1;
                checkEnd = coord2.row + 1;
            }
            else {
                checkStart = coord2.row - 1;
                checkEnd = coord1.row + 1;
            }
        }
        boolean check = false;
        if(checkCoord(coord1,coord2)) {
            check = true;
            int i = (checkStart >= 0 && checkStart < 10) ? checkStart : ++checkStart;
            checkEnd = (checkEnd >= 0 && checkEnd < 10) ? checkEnd : --checkEnd;
            while (check && i <= checkEnd) {
                if (direction == 'h') {
                    if (nearShip(coord1.row, i) || nearShip(coord1.row + 1, i) || nearShip(coord1.row - 1, i))
                        check = false;
                } else if (nearShip(i, coord1.col) || nearShip(i, coord1.col + 1) || nearShip(i, coord1.col - 1))
                    check = false;
                i++;
            }
        }
        return check;
    }

    public void setPlaceShipRand() {
        int row, col, shift, stern,  way = 0, direction = 0;
        Coord crd1 = new Coord(), crd2 = new Coord();
        boolean established;
        Ship ship;

        for (int i = 4; i > 0; i--)
            for (int j = 1; j <= (5 - i); j++) {
                do {
                    row = new Random().nextInt(10);
                    col = new Random().nextInt(10);
                    if (i > 1) {
                        direction = (new Random().nextInt(2) == 0) ? 1 : -1;
                        way = (new Random().nextInt(2) == 0) ? 1 : -1;
                        shift = (direction == 1) ?  col : row;
                        stern =  shift + way * (i - 1);
                        if (!(stern >= 0 && stern < 10))
                            way *= -1;
                    }

                    if (direction == 1) {
                        crd1.row = row;
                        crd2.row = row;
                        crd1.col = col;
                        crd2.col = col + way *(i - 1);
                        established = checkCollision(crd1, crd2);
                    }
                    else {
                        crd1.row = row;
                        crd2.row = row + way * (i - 1);
                        crd1.col = col;
                        crd2.col = col;
                        established = checkCollision(crd1, crd2);
                    }
                } while (!established);


                if (i > 1) {
                    ship = new Ship(i, new Coord(crd1),new Coord(crd2), field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.getFleet().add(ship);
                }
                else {
                    ship = new Ship(i, new Coord(crd1), field);
                    ship.linkTilesWithDeck(field);
                    ship.linkTilesWithHalo(field);
                    field.getFleet().add(ship);
                }
            }
        field.initEmptyTiles();
    }
}
