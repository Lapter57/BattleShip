package graphics;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logics.players.Player;

public class CurrentStats {
    Player player;
    StackPane stat_area = new StackPane();
    Text cur_stat;
    public CurrentStats(Player player){
        String str = "CARRIER: " + player.getShips().get(3) + "\n" +
                     "SUBMARINE: " + player.getShips().get(2) + "\n" +
                     "DESTROYED: " + player.getShips().get(1) + "\n" +
                     "FRIGATE: " + player.getShips().get(0) + "\n";
        cur_stat = new Text(str);
        cur_stat.setFill(Color.valueOf("#455760"));
        cur_stat.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,30));

        stat_area.setMinHeight(135);
        stat_area.setMaxWidth(360);
        this.player = player;
    }

    public void update(){
        String str = "CARRIER: " + player.getShips().get(3) + "\n" + "SUBMARINE: " +  player.getShips().get(2) + "\n" + "DESTROYED: " +  player.getShips().get(1) + "\n" +  "FRIGATE: " +  player.getShips().get(0) + "\n";
        cur_stat.setText(str);
    }

    public void customize(Pane playingFields, int b_x){
        Rectangle sa = new Rectangle(360, 135, Color.valueOf("#EFF0F1"));
        sa.setOpacity(0.5);
        sa.setArcHeight(20);
        sa.setArcWidth(20);

        stat_area.setLayoutX((b_x == 150) ? 195 : 795);
        stat_area.setLayoutY(565);
        stat_area.setAlignment(Pos.CENTER);
        stat_area.getChildren().addAll(sa, cur_stat);
        cur_stat.setTranslateY(17);
        playingFields.getChildren().add(stat_area);
    }
}
