package com.lapter57.graphics;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.lapter57.logics.players.Player;

public class NameArea{
    private Player player;
    private StackPane name_area = new StackPane();
    private TextField namePlayer = new TextField();

    public NameArea(Player player){
        this.player = player;
    }

    public void fillNameArea(Pane gameArea){
        namePlayer.setPromptText("Enter your nickname");
        namePlayer.setFocusTraversable(false);
        namePlayer.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 26));
        namePlayer.setPrefSize(400, 50);
        StackPane st = new StackPane(namePlayer);
        st.setTranslateX(800);
        st.setTranslateY(550);

        gameArea.getChildren().add(st);
    }

    public TextField getNamePlayer() {
        return namePlayer;
    }

    public void customize(Pane playingFields, int b_x){
        Rectangle na = new Rectangle(225, 45, Color.valueOf("#EFF0F1"));
        na.setOpacity(0.5);
        na.setArcHeight(20);
        na.setArcWidth(20);

        name_area.setLayoutX((b_x == 150) ? 150 : 750);
        name_area.setLayoutY(20);
        Text name = new Text(player.getName());
        name.setFill(Color.valueOf("#455760"));
        name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
        name_area.getChildren().addAll(na, name);
        name.setTranslateX(-25);

        playingFields.getChildren().add(name_area);
    }
}
