package logics;

public class Tile {
    private Byte row = null;
    private Byte col = null;
    private Character state = ' ';
    Ship typeShip = null;

    public Tile(){}
    public Tile(Byte row, Byte col){
        this.row = row;
        this.col = col;

    }
    public void setRow(final Byte row){
        this.row = row;
    }
    public Byte getRow(){
        return row;
    }
    public void setCol(final Byte col){
        this.col = col;
    }
    public Byte getCol(){
        return col;
    }

    public void setState(final Character state){
        this.state = state;
    }
    public Character getState(){
        return state;
    }

    public void linkShip(Ship ship){
        typeShip = ship;
    }

    public Ship getLinkShip(){
        return typeShip;
    }



}
