package com.lapter57.graphics.loading;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Loading {
    public static void loadIMG(HashMap<String, Image> map_img, ArrayList<ImageView> ships_img) {
        try (InputStream ot = Files.newInputStream(Paths.get("src/main/resources/tiles/Ordinary_Tile_2.png"));
             InputStream us = Files.newInputStream(Paths.get("src/main/resources/tiles/Unbroken_Ship_2.png"));
             InputStream ft = Files.newInputStream(Paths.get("src/main/resources/tiles/Focus_Tile.png"));
             InputStream dt = Files.newInputStream(Paths.get("src/main/resources/tiles/Dead_Tile_2.png"));
             InputStream ds = Files.newInputStream(Paths.get("src/main/resources/tiles/Destroyed_Ship_3.png"));
             InputStream hs = Files.newInputStream(Paths.get("src/main/resources/tiles/Hurt_Ship.png"));
             InputStream nb = Files.newInputStream(Paths.get("src/main/resources/legend/Num_Board.png"));
             InputStream lb = Files.newInputStream(Paths.get("src/main/resources/legend/Let_Board.png"));
             InputStream ss = Files.newInputStream(Paths.get("src/main/resources/tiles/Surviving_Ships.png"));
             InputStream car_h = Files.newInputStream(Paths.get("src/main/resources/ships/Car_hor.png"));
             InputStream car_v = Files.newInputStream(Paths.get("src/main/resources/ships/Car_ver.png"));
             InputStream des_h = Files.newInputStream(Paths.get("src/main/resources/ships/Des_hor.png"));
             InputStream des_v = Files.newInputStream(Paths.get("src/main/resources/ships/Des_ver.png"));
             InputStream sub_h = Files.newInputStream(Paths.get("src/main/resources/ships/Sub_hor.png"));
             InputStream sub_v = Files.newInputStream(Paths.get("src/main/resources/ships/Sub_ver.png"));
             InputStream frig = Files.newInputStream(Paths.get("src/main/resources/ships/Frigate.png"));
             InputStream gr_p = Files.newInputStream(Paths.get("src/main/resources/pointer/Green_Pointer.png"));
             InputStream red_p = Files.newInputStream(Paths.get("src/main/resources/pointer/Red_Pointer.png"));
             InputStream expl = Files.newInputStream(Paths.get("src/main/resources/animation/Explosion.png"));
             InputStream an_p = Files.newInputStream(Paths.get("src/main/resources/animation/pointerAnimation.png"))) {
            map_img.put("ot", new Image(ot));
            map_img.put("dt", new Image(dt));
            map_img.put("ft", new Image(ft));
            map_img.put("us", new Image(us));
            map_img.put("hs", new Image(hs));
            map_img.put("ds", new Image(ds));
            map_img.put("nb", new Image(nb));
            map_img.put("lb", new Image(lb));
            map_img.put("ss", new Image(ss));
            map_img.put("gp", new Image(gr_p));
            map_img.put("rp", new Image(red_p));
            map_img.put("expl", new Image(expl));
            map_img.put("an_p", new Image(an_p));

            Image ch = new Image(car_h);
            Image cv = new Image(car_v);
            Image sh = new Image(sub_h);
            Image sv = new Image(sub_v);
            Image dh = new Image(des_h);
            Image dv = new Image(des_v);
            Image fr = new Image(frig);

            ships_img.add(new ImageView(ch));
            ships_img.add(new ImageView(sh));
            ships_img.add(new ImageView(sh));
            ships_img.add(new ImageView(dh));
            ships_img.add(new ImageView(dh));
            ships_img.add(new ImageView(dh));

            ships_img.add(new ImageView(cv));
            ships_img.add(new ImageView(sv));
            ships_img.add(new ImageView(sv));
            ships_img.add(new ImageView(dv));
            ships_img.add(new ImageView(dv));
            ships_img.add(new ImageView(dv));

            ships_img.add(new ImageView(fr));
            ships_img.add(new ImageView(fr));
            ships_img.add(new ImageView(fr));
            ships_img.add(new ImageView(fr));
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }
    }

    public static void loadPaneOfLocOfShips(StackPane[][] water, HashMap<String, Image> map_img, ArrayList<ImageView> ships_img, GridPane board, Pane fleet_h, Pane fleet_v, StackPane all_fleet) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                water[i][j] = new StackPane();
                ImageView img_ot = new ImageView(map_img.get("ot"));
                ImageView img_us = new ImageView(map_img.get("us"));
                water[i][j].getChildren().add(img_us);
                water[i][j].getChildren().add(img_ot);
                board.add(water[i][j], j, i);
            }
        board.setTranslateX(150);
        board.setTranslateY(70);

        Rectangle fl_h = new Rectangle(450, 450, Color.valueOf("#EFF0F1"));
        fl_h.setOpacity(0.5);
        fleet_h.getChildren().add(fl_h);
        Rectangle fl_v = new Rectangle(450, 450, Color.valueOf("#EFF0F1"));
        fl_v.setOpacity(0.5);
        fleet_v.getChildren().add(fl_v);
        fleet_v.setVisible(false);

        fleet_h.getChildren().add(ships_img.get(0));
        ships_img.get(0).setLayoutX(225);
        ships_img.get(0).setLayoutY(45);
        int i;
        int shift = 270;
        for (i = 1; i < 3; i++) {
            fleet_h.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(shift);
            ships_img.get(i).setLayoutY(135);
            shift -= 180;
        }

        shift = 315;
        for (; i < 6; i++) {
            fleet_h.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(shift);
            ships_img.get(i).setLayoutY(225);
            shift -= 135;
        }
        shift = 360;
        for (i = 12; i < 16; i++) {
            fleet_h.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(shift);
            ships_img.get(i).setLayoutY(315);
            shift -= 90;
        }

        fleet_v.getChildren().add(ships_img.get(6));
        ships_img.get(6).setLayoutX(45);
        ships_img.get(6).setLayoutY(225);
        shift = 270;
        for (i = 7; i < 9; i++) {
            fleet_v.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(135);
            ships_img.get(i).setLayoutY(shift);
            shift -= 180;
        }

        shift = 315;
        for (; i < 12; i++) {
            fleet_v.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(225);
            ships_img.get(i).setLayoutY(shift);
            shift -= 135;
        }
        all_fleet.getChildren().addAll(fleet_v, fleet_h);
        all_fleet.setMaxWidth(450);
        all_fleet.setMinHeight(450);
    }
}
