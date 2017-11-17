package logics;

import javafx.scene.layout.StackPane;
import logics.coord.Coord;

import java.util.ArrayList;

public class Ship {
    private int sizeShip = 0;
    private int hits = 0;
    private ArrayList<Tile> deck = new ArrayList<>();
    private ArrayList<Tile> halo = new ArrayList<>();

    public Ship(int size, Coord coord1, Coord coord2, Field field){
        sizeShip = size;
        if (coord1.row == coord2.row) {
            int colMin, colMax;
            if (coord1.col < coord2.col) {
                colMin = coord1.col;
                colMax = coord2.col;
            }
            else {
                colMin = coord2.col;
                colMax = coord1.col;
            }
            for (int i = colMin; i <= colMax; i++) {
                Tile tile = new Tile(coord1.row, i);
                deck.add(tile);
            }
            fillHalo(coord1.row, colMin - 1, size, 'h', field);
        }
        else {
            int rowMin, rowMax;
            if (coord1.row < coord2.row) {
                rowMin = coord1.row;
                rowMax = coord2.row;
            }
            else {
                rowMin = coord2.row;
                rowMax = coord1.row;
            }
            for (int i = rowMin; i <= rowMax; i++) {
                Tile tile = new Tile(i, coord1.col);
                deck.add(tile);
            }
            fillHalo(rowMin - 1, coord1.col, size, 'v', field);
        }
    }

    public Ship(int size, Coord coord, Field field){
        sizeShip = size;
        Tile tile = new Tile(coord.row, coord.col);
        deck.add(tile);
        fillHalo(coord.row - 1, coord.col, size, 'v', field);
    }

    public ArrayList<Tile> getDeck() {
        return deck;
    }

    public boolean destroyed(){
        return (sizeShip == hits);
    }

    public void setHit(){
        ++hits;
    }

    public int getSizeShip(){
        return sizeShip;
    }

    private void fillHalo(int row, int col, int size, char direction, Field field){
        int travel_width;
        int travel_length;
        if (direction == 'h') {
            travel_width = row;
            travel_length = col;
        }
        else {
            travel_width = col;
            travel_length = row;
        }

        int max_path = size * 2 + 6;
        for (int i = 1; i <= (max_path); i++) {
            if (checkCoord(travel_width) && checkCoord(travel_length)) {
                if (((direction == 'h') && field.getGrid()[travel_width][travel_length] == null) ||
                        ((direction == 'v') && field.getGrid()[travel_length][travel_width] == null)) {
                    Tile tile = new Tile();
                    if (direction == 'h') {
                        tile.setRow(travel_width);
                        tile.setCol(travel_length);
                    } else {
                        tile.setRow(travel_length);
                        tile.setCol(travel_width);
                    }
                    halo.add(tile);
                } else if (direction == 'h')
                    halo.add(field.getGrid()[travel_width][travel_length]);
                else
                    halo.add(field.getGrid()[travel_length][travel_width]);
            }

            if (i == 1 || i == max_path)
                --travel_width;
            else if ((i == max_path - (size + 2)) || (i == max_path - (size + 3)))
                ++travel_width;
            else if (i < max_path - (size + 3))
                ++travel_length;
            else
                --travel_length;
        }
    }

    private boolean checkCoord(final int check){ return(check >= 0 && check < 10); }

    public void linkTilesWithDeck(Field field) {
        for (Tile aDeck : deck) {
            aDeck.linkShip(this);
            field.addTile(aDeck);
        }
    }

    public void linkTilesWithHalo(Field field) {
        for (Tile aHalo : halo)
            if (field.getGrid()[aHalo.getRow()][aHalo.getCol()] == null)
                field.addTile(aHalo);
    }

    public void setStateHalo(StackPane[][] water) {
        for (Tile aHalo : halo)
            if (aHalo.getState() != '*') {
                aHalo.setState('*');
                water[aHalo.getRow()][aHalo.getCol()].getChildren().get(4).setVisible(false);
            }
    }


}
