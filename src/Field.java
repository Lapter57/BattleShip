import java.util.ArrayList;

public class Field {
    Tile[][] grid = new Tile[10][10];
    ArrayList<Ship> fleet;
    Byte numShipAfloat = 10;

    public Field() {
            for (Tile[] el1 : grid)
            for (Tile el2 :el1){
                el2 = null;
            }
    }

    //-------------------------------------------------------------------------------------

    // Method of filling the playing field
    public void initEmptyTiles() {
        Tile tile;
        byte i, j;
        i = j = 0;
        for (Tile[] el1 : grid) {
            for (Tile el2 : el1) {
                if (el2 == null) {
                    tile = new Tile(i, j);
                    el2 = tile;
                }
                j++;
            }
            j = 0;
            i++;
        }
    }

    // Methods of filling the playing field
    public void addTile(Tile tile) { grid[(tile.getRow())][tile.getCol()] = tile; }

    // Method of reducing the number of ships afloat
    public void shipDestroy() { --numShipAfloat; }
}
