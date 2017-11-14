package graphics;

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
}
