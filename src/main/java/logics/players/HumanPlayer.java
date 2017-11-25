package logics.players;
import graphics.Graphic;

import logics.Game;
import logics.coord.Coord;


public class HumanPlayer extends Player {
    private byte numWinShots = 0;
    private Game.Level level = Game.Level.NORMAL ;

    public HumanPlayer(){
        super(new StringBuilder("NO_NAME"));
    }

    public Game.Level getLevel() {
        return level;
    }

    public void setLevel(Game.Level level){
        this.level = level;
    }

    public void addShot(){
        numWinShots++;
    }

    public int getNumWinShots(){
        return numWinShots;
    }

    public void checkName(){

        if (!this.getNamePlayer().getText().isEmpty()) {
            this.name.delete(0, this.name.length());
            this.name.append(this.getNamePlayer().getText());
            if (this.name.length() > 15) {
                this.name.delete(15, this.name.length());
            }
        }

    }

    public void showLocationOfShips(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(field.getGrid()[i][j].getLinkShip() != null ) {
                    graphicField.getWater()[i][j].getChildren().get(0).setVisible(true);
                    graphicField.getWater()[i][j].getChildren().get(1).setVisible(false);
                    graphicField.getWater()[i][j].getChildren().get(2).setVisible(false);
                    graphicField.getWater()[i][j].getChildren().get(3).setVisible(false);
                    graphicField.getWater()[i][j].getChildren().get(4).setVisible(false);
                }

            }
        }
    }

    public boolean yourTurn(Player rival, Coord coord){
        boolean hit = true;
        if (enemyField.getGrid()[coord.row][coord.col].getState() == ' ') {
            if (enemyField.getGrid()[coord.row][coord.col].getLinkShip() == null) {
                enemyField.getGrid()[coord.row][coord.col].setState('*');
                Graphic.animation.sounWater();
                rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(4).setVisible(false);
                hit = false;
            }
            else {
                enemyField.getGrid()[coord.row][coord.col].setState('x');
                rival.graphicField.getWater()[coord.row][coord.col].getChildren().add(Graphic.animation.getImageExpl());
                Graphic.animation.playExplosive();
                rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(4).setVisible(false);
                rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(3).setVisible(false);
                foundShip = enemyField.getGrid()[coord.row][coord.col].getLinkShip();
                foundShip.setHit();
                if (foundShip.destroyed()) {
                    for(int i = 0; i < foundShip.getDeck().size(); i++){
                        int row = foundShip.getDeck().get(i).getRow();
                        int col = foundShip.getDeck().get(i).getCol();
                        rival.graphicField.getWater()[row][col].getChildren().get(2).setVisible(false);
                    }
                    enemyField.shipDestroy();
                    foundShip.setStateHalo(rival.graphicField.getWater());
                    rival.destroy(foundShip.getSizeShip());
                    rival.updateCurStat();
                }
                foundShip = null;
            }
        }
        return  hit;
    }
}
