package logics;

public class Tile {
    private int row = 0;
    private int col = 0;
    private char state = ' ';
    private Ship typeShip = null;

    Tile(){}
    Tile(int row, int col){
        this.row = row;
        this.col = col;

    }
    public void setRow(final int row){
        this.row = row;
    }
    public int getRow(){
        return row;
    }
    void setCol(final int col){
        this.col = col;
    }
    public int getCol(){
        return col;
    }

    public void setState(final char state){
        this.state = state;
    }
    public Character getState(){
        return state;
    }

    void linkShip(Ship ship){
        typeShip = ship;
    }

    public Ship getLinkShip(){
        return typeShip;
    }



}
