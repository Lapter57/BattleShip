package graphics;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import logics.Game;
import logics.players.Player;

public class BattlePane {
    private Pane playingFields = new Pane();

    public BattlePane(Graphic graphic, Game game, Player...players){
        playingFields.setMaxHeight(720);
        playingFields.setMaxWidth(1280);
        playingFields.setVisible(true);

        graphic.createPointer(playingFields);
        createQuitBottom(game, players);
    }

    public void createPlayingFields(Player pl,int b_x, int b_y, Graphic graphic) {

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                pl.getGraphicField().getWater()[i][j] = new StackPane();
                ImageView img_ot = new ImageView(Graphic.map_img.get("ot"));
                ImageView img_us = new ImageView(Graphic.map_img.get("us"));
                ImageView img_hs = new ImageView(Graphic.map_img.get("hs"));
                ImageView img_dt = new ImageView(Graphic.map_img.get("dt"));
                ImageView img_ds = new ImageView(Graphic.map_img.get("ds"));
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_us);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_ds);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_hs);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_dt);
                pl.getGraphicField().getWater()[i][j].getChildren().add(img_ot);
                pl.getGraphicField().getBoard().add(pl.getGraphicField().getWater()[i][j], j, i);
            }
        pl.getGraphicField().getBoard().setTranslateX(b_x);
        pl.getGraphicField().getBoard().setTranslateY(b_y);
        playingFields.getChildren().add(pl.getGraphicField().getBoard());

        ImageView numB = new ImageView(Graphic.map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX((b_x == 150) ? 105 : 705 + 495);
        numB.setTranslateY(115);
        playingFields.getChildren().add(numB);
        ImageView letB = new ImageView(Graphic.map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX((b_x == 150) ? 150 : 750);
        letB.setTranslateY(70);
        playingFields.getChildren().add(letB);


        pl.customizeCurrentStats(playingFields, b_x);
        pl.customizeNameArea(playingFields, b_x);
        if(!graphic.getMainStPain().getChildren().contains(playingFields))
            graphic.getMainStPain().getChildren().add(playingFields);
    }

    private void createQuitBottom(Game game, Player... players){
        Graphic.MenuItem bck = new Graphic.MenuItem("QUIT", 90, 90);
        bck.setTranslateX(0);
        bck.setTranslateY(630);
        playingFields.getChildren().add(bck);
        bck.setOnMouseClicked(event -> {
            for (Player pl: players) {
                game.clearBoard(pl);
            }
            game.getGraphic().removePane(playingFields);
            game.getGraphic().getMainStPain().menuBox.setVisible(true);
            game.getGraphic().getMainStPain().title.setVisible(true);
            game.getPreparePane().getReadyBottom().changeSize(100, 70);
        });
    }

    public void useFocusTilesOnField(Player...players) {

            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    int row = i;
                    int col = j;
                    for (Player pl: players) {
                        pl.getGraphicField().getWater()[i][j].setOnMouseEntered(event -> {
                            BattlePreparePane.focusTiles.get(0).setVisible(true);
                            if (pl.getGraphicField().getWater()[row][col].getChildren().get(4).isVisible() &&
                                    !pl.getGraphicField().getWater()[row][col].getChildren().contains(BattlePreparePane.focusTiles.get(0)))
                                pl.getGraphicField().getWater()[row][col].getChildren().add(BattlePreparePane.focusTiles.get(0));
                            else
                                BattlePreparePane.focusTiles.get(0).setVisible(false);
                        });

                        pl.getGraphicField().getWater()[i][j].setOnMouseExited(event ->
                                BattlePreparePane.focusTiles.get(0).setVisible(false));
                    }
                }
            }

    }
}
