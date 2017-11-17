package logics.coord;

public class Coord {
    public int row = 0;
    public int col = 0;
    public Coord(){}
    public Coord(int row, int col){
        this.row = row;
        this.col = col;
    }
    public Coord(Coord crd){
        row = crd.row;
        col = crd.col;
    }
}
