package Logics;

import Logics.coord.Coord;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Ship {
    private Byte sizeShip = 0;
    private Byte hits = 0;
    ArrayList<Tile> deck = new ArrayList<>();
    ArrayList<Tile> halo = new ArrayList<>();

    public Ship(Byte size, Coord coord1, Coord coord2, Field field){
        sizeShip = size;
        if (coord1.row == coord2.row) {
            byte colMin, colMax;
            if (coord1.col < coord2.col) {
                colMin = coord1.col;
                colMax = coord2.col;
            }
            else {
                colMin = coord2.col;
                colMax = coord1.col;
            }
            for (byte i = colMin; i <= colMax; i++) {
                Tile tile = new Tile(coord1.row, i);
                deck.add(tile);
            }
            fillHalo(coord1.row, (byte)(colMin - 1), size, 'h', field);
        }
        else {
            byte rowMin, rowMax;
            if (coord1.row < coord2.row) {
                rowMin = coord1.row;
                rowMax = coord2.row;
            }
            else {
                rowMin = coord2.row;
                rowMax = coord1.row;
            }
            for (byte i = rowMin; i <= rowMax; i++) {
                Tile tile = new Tile(i, coord1.col);
                deck.add(tile);
            }
            fillHalo((byte)(rowMin - 1), coord1.col, size, 'v', field);
        }
    }

    public Ship(Byte size, Coord coord, Field field){
        sizeShip = size;
        Tile tile = new Tile(coord.row, coord.col);
        deck.add(tile);
        fillHalo((byte)(coord.row - 1), coord.col, size, 'v', field);
    }



    public void fillHalo(byte row, byte col, byte size, char direction, Field field){
        byte travel_width;
        byte travel_length;
        if (direction == 'h') {
            travel_width = row;
            travel_length = col;
        }
        else {
            travel_width = col;
            travel_length = row;
        }

        byte max_path = (byte)(size * 2 + 6);
        for (int i = 1; i <= (max_path); i++) {
            if (checkCoord(travel_width) && checkCoord(travel_length)) {
                if (((direction == 'h') && field.grid[travel_width][travel_length] == null) ||
                        ((direction == 'v') && field.grid[travel_length][travel_width] == null)) {
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
                    halo.add(field.grid[travel_width][travel_length]);
                else
                    halo.add(field.grid[travel_length][travel_width]);
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

    public boolean checkCoord(final byte check){ return(check >= 0 && check < 10); }

    public void linkTilesWithDeck(Field field) {
        for(int i = 0; i < deck.size(); i++) {
            deck.get(i).linkShip(this);
            field.addTile( deck.get(i));
        }
    }


    public void linkTilesWithHalo(Field field) {
        for(int i = 0; i < halo.size(); i++)
            if(field.grid[halo.get(i).getRow()][halo.get(i).getCol()] == null)
                field.addTile(halo.get(i));
    }

    public void setStateHalo(StackPane[][] water) {
        for (int i = 0; i < halo.size(); i++)
            if (halo.get(i).getState() != '*') {
                halo.get(i).setState('*');
                water[halo.get(i).getRow()][halo.get(i).getCol()].getChildren().get(4).setVisible(false); // можно ли улучшить
            }
    }

    public boolean destroyed(){
        return (sizeShip == hits);
    }


    public void setHit(){
        ++hits;
    }


    byte getSizeShip(){
        return sizeShip;
    }

}
