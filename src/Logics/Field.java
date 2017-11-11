package Logics;

import java.util.ArrayList;

public class Field {
    Tile[][] grid = new Tile[10][10];
    ArrayList<Ship> fleet = new ArrayList<>();
    Byte numShipAfloat = 10;

    public enum TypeShip{

        FRIGATE(1),
        DESTROYER(2),
        SUBMARINE(3),
        CARRIER(4);


        private int size;
        TypeShip(int size){
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public Field() {
        for (Tile[] el1 : grid) {
            for (Tile el2 : el1) {
                el2 = null;
            }
        }
    }

    public void initEmptyTiles() {
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

    public void addTile(Tile tile) {
        grid[(tile.getRow())][tile.getCol()] = tile;
    }

    public void shipDestroy() {
        --numShipAfloat;
    }
}
