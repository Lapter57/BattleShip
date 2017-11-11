package Logics;
import Logics.coord.Coord;


public class HumanPlayer extends Player {
    private byte numWinShots = 0;
    private Integer level = 1;

    public HumanPlayer(){
        super(new StringBuilder("NO_NAME"));
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level){
        this.level = level;
    }

    public void addShot(){
        numWinShots++;
    }

    public byte getNumWinShots(){
        return numWinShots;
    }

    public void checkName(){

        if (!this.namePlayer.getText().isEmpty()) {
            this.name.delete(0, this.name.length());
            this.name.append(this.namePlayer.getText());
            if (this.name.length() > 15) {
                this.name.delete(15, this.name.length());
            }
        }

    }

    public boolean yourTurn(Game game, Player rival, Coord coord){
        boolean hit = true;
        if (enemyField.grid[coord.row][coord.col].getState() == ' ') {
            if (enemyField.grid[coord.row][coord.col].getLinkShip() == null) {
                enemyField.grid[coord.row][coord.col].setState('*');
                rival.water[coord.row][coord.col].getChildren().get(4).setVisible(false);
                hit = false;
            }
            else {
                enemyField.grid[coord.row][coord.col].setState('x');
                rival.water[coord.row][coord.col].getChildren().get(4).setVisible(false);
                rival.water[coord.row][coord.col].getChildren().get(3).setVisible(false);
                foundShip = enemyField.grid[coord.row][coord.col].getLinkShip();
                foundShip.setHit();
                if (foundShip.destroyed()) {
                    for(int i = 0; i < foundShip.deck.size(); i++){
                        byte row = foundShip.deck.get(i).getRow();
                        byte col = foundShip.deck.get(i).getCol();
                        rival.water[row][col].getChildren().get(2).setVisible(false);
                    }
                    enemyField.shipDestroy();
                    foundShip.setStateHalo(rival.water);
                    rival.destroy(foundShip.getSizeShip());
                    rival.updateCurStat();
                }
                foundShip = null;
            }
        }
        return  hit;
    }
}
