package Logics.coord;

public class Coord {
    public byte row = 0;
    public byte col = 0;
    public Coord(){}
    public Coord(byte row, byte col){
        this.row = row;
        this.col = col;
    }
    public Coord(Coord crd){
        row = crd.row;
        col = crd.col;
    }
}
