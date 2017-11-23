package logics.players;


import graphics.Graphic;
import javafx.scene.Group;
import logics.Field;
import logics.coord.Coord;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class ComputerPlayer extends Player {
    private ArrayList<Coord> coordForNewShot = new ArrayList<>();
    private ArrayList<Coord> coordForToShell = new ArrayList<>();
    private ArrayList<Coord> randomSteps = new ArrayList<>();
    private ArrayDeque<Coord> tilesAfterSecondHit = new ArrayDeque<>();
    private int sizeShip = 4;
    private boolean firstHit = true;
    private int shotsByAlgorithm = 0;
    private Coord firstFoundTileOfShip;
    private StringBuilder level = new StringBuilder("normal");

    public ComputerPlayer() {
        super(new StringBuilder("Computer"));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                randomSteps.add(new Coord(i, j));
            }
        }
        Collections.shuffle(randomSteps);
    }

    public StringBuilder getLevel() {
        return level;
    }

    private void updateCoord(final int search) {

        int min_diag = 0, max_diag = 0, step = 0;

        Field.TypeShip typeShip;
        if (search == 1)
            typeShip = Field.TypeShip.FRIGATE;
        else
            typeShip = (search == 3) ? Field.TypeShip.SUBMARINE : Field.TypeShip.CARRIER;

        switch (typeShip) {
            case CARRIER: {
                min_diag = 3;
                max_diag = 15;
                step = 4;
                break;
            }
            case SUBMARINE: {
                min_diag = 1;
                max_diag = 17;
                step = 4;
                break;
            }
            case FRIGATE: {
                min_diag = 0;
                max_diag = 18;
                step = 2;
            }
        }

        int num_tiles_diag;
        int row;

        for (int diag = min_diag; diag <= max_diag; diag += step) {
            num_tiles_diag = (diag <= 9) ? diag + 1 : 9 - diag % 10;
            for (int tile_num = 0; tile_num < num_tiles_diag; ++tile_num) {
                row = (diag <= 9) ? tile_num : tile_num + diag - 9;
                if (enemyField.getGrid()[row][diag - row].getState() == ' ') {
                    Coord coord = new Coord();
                    coord.row = row;
                    coord.col = diag - row;
                    coordForNewShot.add(coord);
                }
            }
        }

        Collections.shuffle(coordForNewShot);
    }

    private boolean checkCoord(int crd){
        return crd >=0 && crd < 10;
    }

    private void chooseCoordForShot(){
        while (coordForNewShot.isEmpty() || enemyField.getGrid()[coordForNewShot.get(0).row][coordForNewShot.get(0).col].getState() != ' ') {
            if (coordForNewShot.isEmpty()) {
                updateCoord(sizeShip);
                sizeShip = (sizeShip == 4) ? 3 : 1;
            } else
                coordForNewShot.remove(0);
        }
    }

    private Coord getCoord(){
        Coord coord = new Coord();
        switch (level.toString()){
            case "hard":
                if(shotsByAlgorithm < 2) {
                    chooseCoordForShot();
                    coord.row = coordForNewShot.get(0).row;
                    coord.col = coordForNewShot.get(0).col;
                    coordForNewShot.remove(0);
                    shotsByAlgorithm++;
                }
                else{
                    while (enemyField.getGrid()[randomSteps.get(0).row][randomSteps.get(0).col].getState() != ' '){
                        randomSteps.remove(0);
                    }
                    coord.row = randomSteps.get(0).row;
                    coord.col = randomSteps.get(0).col;
                    Collections.shuffle(randomSteps);
                    shotsByAlgorithm = 0;
                }
                return coord;
            case "normal":
                chooseCoordForShot();
                coord.row = coordForNewShot.get(0).row;
                coord.col = coordForNewShot.get(0).col;
                coordForNewShot.remove(0);
                return coord;
        }

        while (enemyField.getGrid()[randomSteps.get(0).row][randomSteps.get(0).col].getState() != ' '){
            randomSteps.remove(0);
        }
        coord.row = randomSteps.get(0).row;
        coord.col = randomSteps.get(0).col;
        Collections.shuffle(randomSteps);
        return coord;
    }


    public void yourTurn(Player rival) {
        Coord coord;
        Coord tempCrd = new Coord();
        int offset = 1;
        boolean lastShotOnCarrier = false;
        boolean hit = true;

        do {
            if (!tilesAfterSecondHit.isEmpty())
                coord = tilesAfterSecondHit.pop();
            else if (coordForToShell.isEmpty())
                coord = getCoord();
            else
                coord = coordForToShell.get(0);

            if (enemyField.getGrid()[coord.row][coord.col].getLinkShip() == null) {
                enemyField.getGrid()[coord.row][coord.col].setState('*');
                rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(4).setVisible(false);
                rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(3).setVisible(true);
                if (!coordForToShell.isEmpty())
                    coordForToShell.remove(0);
                hit = false;
                if (tilesAfterSecondHit.isEmpty() && lastShotOnCarrier) {
                    offset *= (-3);
                    if (coord.row == firstFoundTileOfShip.row)
                        firstFoundTileOfShip.col += offset;
                    else
                        firstFoundTileOfShip.row += offset;
                    tilesAfterSecondHit.addLast(firstFoundTileOfShip);
                }
                continue;
            }

            enemyField.getGrid()[coord.row][coord.col].setState('x');
            rival.graphicField.getWater()[coord.row][coord.col].getChildren().add(Graphic.animation.getImageExpl());
            Graphic.animation.playExplosive();
            rival.graphicField.getWater()[coord.row][coord.col].getChildren().remove(5);
            rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(4).setVisible(false);
            rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(3).setVisible(false);
            rival.graphicField.getWater()[coord.row][coord.col].getChildren().get(2).setVisible(true);
            if (foundShip == null)
                foundShip = enemyField.getGrid()[coord.row][coord.col].getLinkShip();
            foundShip.setHit();

            if (foundShip.destroyed()) {
                for (int i = 0; i < foundShip.getDeck().size(); i++) {
                    int row = foundShip.getDeck().get(i).getRow();
                    int col = foundShip.getDeck().get(i).getCol();
                    rival.graphicField.getWater()[row][col].getChildren().get(2).setVisible(false);
                    rival.graphicField.getWater()[row][col].getChildren().get(1).setVisible(true);
                }
                enemyField.shipDestroy();
                foundShip.setStateHalo(rival.graphicField.getWater());
                rival.destroy(foundShip.getSizeShip());
                rival.updateCurStat();
                foundShip = null;
                firstHit = true;
                lastShotOnCarrier = false;
                if (!tilesAfterSecondHit.isEmpty()) tilesAfterSecondHit.clear();
                if (!coordForToShell.isEmpty())
                    coordForToShell.clear();
                continue;
            }

            if (firstHit) {
                tempCrd.col = coord.col;
                for (int i = 0; i < 2; i++) {
                    tempCrd.row = coord.row + offset;
                    if (checkCoord(tempCrd.row) && enemyField.getGrid()[tempCrd.row][tempCrd.col].getState() == ' ')
                        coordForToShell.add(new Coord(tempCrd));
                    offset *= -1;
                }

                tempCrd.row = coord.row;
                for (int i = 0; i < 2; i++) {
                    tempCrd.col = coord.col + offset;
                    if (checkCoord(tempCrd.col) && enemyField.getGrid()[tempCrd.row][tempCrd.col].getState() == ' ')
                        coordForToShell.add(new Coord(tempCrd));
                    offset *= -1;
                }

                firstFoundTileOfShip = coord;
                Collections.shuffle(coordForToShell);
                firstHit = false;
                continue;
            }

            if (!coordForToShell.isEmpty())
                coordForToShell.clear();

            if (tilesAfterSecondHit.isEmpty()) {
                if (coord.row == firstFoundTileOfShip.row) {
                    offset = (coord.col < firstFoundTileOfShip.col) ? -1 : 1;
                    if (!lastShotOnCarrier) {
                        coord.col += offset;
                        if (checkCoord(coord.col) && enemyField.getGrid()[coord.row][coord.col].getState() == ' ')
                            tilesAfterSecondHit.addLast(new Coord(coord));
                        offset *= -1;
                        tempCrd.row = firstFoundTileOfShip.row;
                        tempCrd.col = firstFoundTileOfShip.col;
                        tempCrd.col += offset;
                        if (checkCoord(tempCrd.col) && enemyField.getGrid()[tempCrd.row][tempCrd.col].getState() == ' ')
                            tilesAfterSecondHit.addLast(new Coord(tempCrd));
                        lastShotOnCarrier = true;
                    }
                    else {
                        coord.col += offset;
                        tilesAfterSecondHit.addLast(new Coord(coord));
                    }
                    continue;
                }
                offset = (coord.row < firstFoundTileOfShip.row) ? -1 : 1;
                if (!lastShotOnCarrier) {
                    coord.row += offset;
                    if (checkCoord(coord.row) && enemyField.getGrid()[coord.row][coord.col].getState() == ' ')
                        tilesAfterSecondHit.addLast(new Coord(coord));
                    offset *= -1;
                    tempCrd.row = firstFoundTileOfShip.row;
                    tempCrd.col = firstFoundTileOfShip.col;
                    tempCrd.row += offset;
                    if (checkCoord(tempCrd.row) && enemyField.getGrid()[tempCrd.row][tempCrd.col].getState() == ' ')
                        tilesAfterSecondHit.addLast(new Coord(tempCrd));
                    lastShotOnCarrier = true;
                } else {
                    coord.row += offset;
                    tilesAfterSecondHit.addLast(new Coord(coord));
                }
            }
        } while (hit && enemyField.getNumShipAfloat() != 0);
    }
}
