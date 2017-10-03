import coord.Coord;
public class HumanPlayer extends Player {
    public HumanPlayer(String name){
        super(name);
    }
    // Checking that the distance between the tiles is equal to the size of the ship
    public boolean checkSize(final byte size, final Coord coord1, final Coord coord2){
        if (coord1.col - coord2.col != 0)
            return (size == (coord1.col - coord2.col + 1) || size ==((coord2.col - coord1.col + 1)));
        else
            return (size == (coord1.row - coord2.row + 1) || size == ((coord2.row - coord1.row + 1)));
    }

    // Manual placement of ships on the playing field
    public void setPlaceShip(Game game){

    }

    // Method for implementing the player's turn
    public void yourTurn(Game game, Player rival, byte id){

    }

    // Overloaded assignment operator to change the player's name
    //void operator=(final String name) { name.this = name; }

    // Method for getting coordinates for a shot
    Coord getCoord();
}
