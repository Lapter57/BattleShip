package Logics;

import java.util.ArrayList;

public class Field {
    Tile[][] grid = new Tile[10][10];
    ArrayList<Ship> fleet = new ArrayList<>();
    /*{
        for(byte i = 4; i > 0; i--){
            for(byte j = 1; j <= 5 - i; j++ ){
                fleet.add(new Ship(i));
            }
        }
    }*/
    Byte numShipAfloat = 10;

    public Field() {
        for (Tile[] el1 : grid) {
            for (Tile el2 : el1) {
                el2 = null;
            }
        }
    }

    //-------------------------------------------------------------------------------------

    // Method of filling the playing field
    public void initEmptyTiles() {
       // Tile tile;
        byte i = 0, j = 0;
        for (Tile[] el1 : grid) {
            for (Tile el2 : el1) {
                if (el2 == null) {
                    addTile(new Tile(i, j));
                }
                j++;
            }
            j = 0;
            i++;
        }
    }

    public void clearField(){
        for(int i = 0; i < 10;i++)
            for(int j = 0; j < 10; j++)
                grid[i][j] = null;
        fleet.clear();
    }

    // Methods of filling the playing field
    public void addTile(Tile tile) { grid[(tile.getRow())][tile.getCol()] = tile; }

    // Method of reducing the number of ships afloat
    public void shipDestroy() { --numShipAfloat; }
}
