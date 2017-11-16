package graphics;

import logics.coord.Coord;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GraphicField {
    private GridPane board = new GridPane(); {
        board.setPrefSize(10, 10);
    }
    private StackPane[][] water = new StackPane[10][10];

    public GridPane getBoard() {
        return board;
    }

    public StackPane[][] getWater() {
        return water;
    }


    public Coord searchPressedImg(Node node) {
        byte row = 0;
        byte col = 0;
        boolean found = false;
        while (row < 10 && !found) {
            col = 0;
            while (col < 10 && !found) {
                if (water[row][col].getChildren().size() == 6 && node == water[row][col].getChildren().get(5)) {
                    BattlePreparePane.focusTiles.get(0).setVisible(false);
                    found = true;
                }
                else
                    col++;
            }
            if (!found)
                row++;
        }
        return new Coord(row, col);
    }
}
