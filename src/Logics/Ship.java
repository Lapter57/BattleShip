package Logics;

import Logics.coord.Coord;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Ship {
    private Byte sizeShip = 0;
    private Byte hits = 0;
    private ArrayList<Tile> deck = new ArrayList<>();
    ArrayList<Tile> halo = new ArrayList<>();

    public Ship(){}
    /*public Ship(Byte size){
        sizeShip = size;
        for(byte i = 0; i < size; i++){
            Tile tile = new Tile();
            deck.add(tile);
        }
    }*/
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

    //-------------------------------------------------------------------------------------

    public Ship(Byte size, Coord coord, Field field){
        sizeShip = size;
        Tile tile = new Tile(coord.row, coord.col);
        deck.add(tile);
        fillHalo((byte)(coord.row - 1), coord.col, size, 'v', field);
    }

//-------------------------------------------------------------------------------------

    // Creation of a halo of a ship
    public void fillHalo(byte row, byte col, byte size, char direction, Field field){
        byte trav_width;
        byte trav_length;
        if (direction == 'h') {
            trav_width = row;
            trav_length = col;
        }
        else {
            trav_width = col;
            trav_length = row;
        }

        byte max_path = (byte)(size * 2 + 6);
        for (int i = 1; i <= (max_path); i++) {
            if (checkCoord(trav_width) && checkCoord(trav_length)) {
                if (((direction == 'h') && field.grid[trav_width][trav_length] == null) || ((direction == 'v') && field.grid[trav_length][trav_width] == null)) {
                    Tile tile = new Tile();
                    if (direction == 'h') {
                        tile.setRow(trav_width);
                        tile.setCol(trav_length);
                    }
                    else {
                        tile.setRow(trav_length);
                        tile.setCol(trav_width);
                    }
                    halo.add(tile);
                } // if (((direction == 'h') && pField->grid_[trav_width][trav_length] == nullptr) || ((direction == 'v') && pField->grid_[trav_length][trav_width] == nullptr))
                else if (direction == 'h')
                    halo.add(field.grid[trav_width][trav_length]);
                else
                    halo.add(field.grid[trav_length][trav_width]);
            } // if (checkCoord(trav_width) && checkCoord(trav_length))

            if (i == 1 || i == max_path)
                --trav_width;
            else if ((i == max_path - (size + 2)) || (i == max_path - (size + 3)))
                ++trav_width;
            else if(i < max_path - (size + 3))
                ++trav_length;
            else
                --trav_length;
        } // for (int i = 1; i <= (max_path); i++)
    }

    //-------------------------------------------------------------------------------------

    // Communication with the game field
    public void linkTilesWithDeck(Field field) {
        for(int i = 0; i < deck.size(); i++) {
            deck.get(i).linkShip(this);
            field.addTile( deck.get(i));
        }
    }

    //-------------------------------------------------------------------------------------

    // Communication with the game field
    public void linkTilesWithHalo(Field field) {
        for(int i = 0; i < halo.size(); i++)
            if(field.grid[halo.get(i).getRow()][halo.get(i).getCol()] == null)
                field.addTile(halo.get(i));
    }

    // Checking the coordinates for belonging to the range of the playing field
    public boolean checkCoord(final byte check){ return(check >= 0 && check < 10); }

    // Ship condition check
    public boolean stateОk(){ return (sizeShip != hits); }

    // Halo filling when destroying the ship
    public void setStateHalo(StackPane[][] water) {
        for (int i = 0; i < halo.size(); i++)
            if (halo.get(i).getState() != '*') {
                halo.get(i).setState('*');
                water[halo.get(i).getRow()][halo.get(i).getCol()].getChildren().get(3).setVisible(false);
            }
    }

    // Increase in ship hits
    public void setHit(){ ++hits; }

    // Getting the size of the ship
    byte getSizeShip(){ return sizeShip; }

}
