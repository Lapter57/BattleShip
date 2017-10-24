package Logics;
import Logics.coord.Coord;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

class Game {
    private HashMap<String, Image> map_img = new HashMap<>();
    private ArrayList<ImageView> ships_img = new ArrayList<>();
    private GridPane board = new GridPane();

    {
        board.setPrefSize(10, 10);
    }

    private StackPane all_fleet = new StackPane();
    private Pane fleet_h = new Pane();
    private Pane fleet_v = new Pane();
    private StackPane[][] water = new StackPane[10][10];
    private byte cur_sizeS;
    private char cur_dir;
    private Boolean first_click_auto = true;
    private StackPane pointer = new StackPane();

    Game() {
        try (InputStream ot = Files.newInputStream(Paths.get("res/images/Ordinary_Tile_2.png"));
             InputStream us = Files.newInputStream(Paths.get("res/images/Unbroken_Ship_2.png"));
             InputStream ft = Files.newInputStream(Paths.get("res/images/Focus_Tile.png"));
             InputStream dt = Files.newInputStream(Paths.get("res/images/Dead_Tile_2.png"));
             InputStream ds = Files.newInputStream(Paths.get("res/images/Destroyed_Ship_3.png"));
             InputStream hs = Files.newInputStream(Paths.get("res/images/Hurt_Ship.png"));
             InputStream nb = Files.newInputStream(Paths.get("res/images/Num_Board.png"));
             InputStream lb = Files.newInputStream(Paths.get("res/images/Let_Board.png"));
             InputStream car_h = Files.newInputStream(Paths.get("res/images/ships/Car_hor.png"));
             InputStream car_v = Files.newInputStream(Paths.get("res/images/ships/Car_ver.png"));
             InputStream des_h = Files.newInputStream(Paths.get("res/images/ships/Des_hor.png"));
             InputStream des_v = Files.newInputStream(Paths.get("res/images/ships/Des_ver.png"));
             InputStream sub_h = Files.newInputStream(Paths.get("res/images/ships/Sub_hor.png"));
             InputStream sub_v = Files.newInputStream(Paths.get("res/images/ships/Sub_ver.png"));
             InputStream frig = Files.newInputStream(Paths.get("res/images/ships/Frigate.png"));
             InputStream gr_p = Files.newInputStream(Paths.get("res/images/Green_Pointer.png"));
             InputStream red_p = Files.newInputStream(Paths.get("res/images/Red_Pointer.png"))) {
            map_img.put("ot", new Image(ot));
            map_img.put("dt", new Image(dt));
            map_img.put("ft", new Image(ft));
            map_img.put("us", new Image(us));
            map_img.put("hs", new Image(hs));
            map_img.put("ds", new Image(ds));
            map_img.put("nb", new Image(nb));
            map_img.put("lb", new Image(lb));
            map_img.put("gp", new Image(gr_p));
            map_img.put("rp", new Image(red_p));

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
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                water[i][j] = new StackPane();
                ImageView img_ot = new ImageView(map_img.get("ot"));
//              ImageView img_ft = new ImageView(map_img.get("ft"));
                ImageView img_us = new ImageView(map_img.get("us"));
                water[i][j].getChildren().add(img_us);
//              water[i][j].getChildren().add(img_ft);
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

    private void clearBoard(Player pl) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                water[i][j].getChildren().get(1).setVisible(true);

        for (ImageView img : ships_img) {
            img.setVisible(true);
        }
        pl.field.clearField();

    }

    private void prepareForBattle(HumanPlayer hp, Pane gameArea, MainStPain msp) {
        gameArea.getChildren().addAll(board, all_fleet);
        all_fleet.setTranslateY(70);
        all_fleet.setTranslateX(750);

        hp.namePlayer.setPromptText("Enter your nickname");
        hp.namePlayer.setFocusTraversable(false);
        hp.namePlayer.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 26));
        hp.namePlayer.setPrefSize(400, 50);
        StackPane st = new StackPane(hp.namePlayer);
        st.setTranslateX(800);
        st.setTranslateY(550);

        gameArea.getChildren().add(st);
        Main.MenuItem autoChoice = new Main.MenuItem("AUTO", 400, 50);
        autoChoice.setOnMouseClicked(event -> {
            if (false == first_click_auto) {
                clearBoard(hp);
            }
            hp.setPlaceShipRand();
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (hp.field.grid[i][j].getLinkShip() != null) {
                        water[i][j].getChildren().get(1).setVisible(false);
                    }
                }
            for (ImageView img : ships_img) {
                img.setVisible(false);
            }
            first_click_auto = false;

        });
        Main.MenuItem clear = new Main.MenuItem("CLEAR", 400, 50);
        clear.setOnMouseClicked(event -> {
            clearBoard(hp);
        });
        VBox deployMenu = new VBox(
                autoChoice, clear
        );

        Main.MenuItem bck = new Main.MenuItem("QUIT", 90, 90);
        bck.setTranslateX(0);
        bck.setTranslateY(630);
        gameArea.getChildren().add(bck);
        bck.setOnMouseClicked(event -> {
            clearBoard(hp);
            gameArea.setVisible(false);
            removePane(gameArea);
            msp.menuBox.setVisible(true);
            msp.title.setVisible(true);
        });

        deployMenu.setSpacing(15);
        deployMenu.setTranslateX(175);
        deployMenu.setTranslateY(550);
        gameArea.getChildren().add(deployMenu);

        ImageView numB = new ImageView(map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX(105);
        numB.setTranslateY(70);
        gameArea.getChildren().add(numB);
        ImageView letB = new ImageView(map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX(150);
        letB.setTranslateY(25);
        gameArea.getChildren().add(letB);

        Main.MenuItem rot90 = new Main.MenuItem("Rot90", 90, 90);
        gameArea.getChildren().add(rot90);
        rot90.setTranslateX(660);
        rot90.setTranslateY(70);
        rot90.setOnMouseClicked(event -> {
            if (fleet_h.isVisible()) {
                fleet_h.setVisible(false);
                fleet_v.setVisible(true);
            } else {
                fleet_h.setVisible(true);
                fleet_v.setVisible(false);
            }
        });
        board.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });

        board.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasImage() && hp.estabShip != 10) {
                    Node node = event.getPickResult().getIntersectedNode();
                    byte row = 0;
                    byte col = 0;
                    boolean f = false;
                    while (row < 10 && !f) {
                        col = 0;
                        while (col < 10 && !f) {
                            if (node == water[row][col].getChildren().get(1))
                                f = true;
                            else
                                col++;
                        }
                        if (!f)
                            row++;
                    }
                    Coord crd1 = new Coord(row, col);
                    Coord crd2 = new Coord((cur_dir == 'h') ? row : (byte) (row + cur_sizeS - 1), (cur_dir == 'v') ? col : (byte) (col + cur_sizeS - 1));

                    if (hp.checkCollision(crd1, crd2)) {
                        if (cur_sizeS > 1) {
                            Ship ship = new Ship(cur_sizeS, crd1, crd2, hp.field);
                            ship.linkTilesWithDeck(hp.field);
                            ship.linkTilesWithHalo(hp.field);
                            hp.field.fleet.add(ship);
                        } else {
                            Ship ship = new Ship(cur_sizeS, crd1, hp.field);
                            ship.linkTilesWithDeck(hp.field);
                            ship.linkTilesWithHalo(hp.field);
                            hp.field.fleet.add(ship);
                        }
                        hp.estabShip++;
                        int shift;
                        if (cur_dir == 'h') {
                            shift = col + cur_sizeS;
                            for (int j = col; j < shift; j++) {
                                water[row][j].getChildren().get(1).setVisible(false);
                            }
                        } else {
                            shift = row + cur_sizeS;
                            for (int i = row; i < shift; i++) {
                                water[i][col].getChildren().get(1).setVisible(false);
                            }
                        }
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }

                } else {
                    event.setDropCompleted(false);
                }
            }
        });

        for (ImageView ship : ships_img) {
            ship.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int i = 0;
                    while (ships_img.get(i) != ship) {
                        i++;
                    }
                    switch (i) {
                        case 0:
                        case 6:
                            cur_sizeS = 4;
                            break;
                        case 1:
                        case 2:
                        case 7:
                        case 8:
                            cur_sizeS = 3;
                            break;
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                            cur_sizeS = 1;
                            break;
                        default:
                            cur_sizeS = 2;

                    }
                    if (i < 6 || (i > 11 && i < 16))
                        cur_dir = 'h';
                    else
                        cur_dir = 'v';
                    Dragboard db = ship.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    db.setDragView(ship.getImage(), 20, 20);
                    content.putImage(ship.getImage());
                    db.setContent(content);
                    event.consume();
                }
            });
            ship.setOnDragDone(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    TransferMode modeUsed = event.getTransferMode();
                    if (modeUsed == TransferMode.MOVE) {
                        if (cur_sizeS == 1)
                            ship.setVisible(false);
                        else {
                            int i = 0;
                            while (ships_img.get(i) != ship) {
                                i++;
                            }
                            switch (i) {
                                case 0:
                                case 6:
                                    ships_img.get(0).setVisible(false);
                                    ships_img.get(6).setVisible(false);
                                    break;
                                case 1:
                                case 7:
                                    ships_img.get(1).setVisible(false);
                                    ships_img.get(7).setVisible(false);
                                    break;
                                case 2:
                                case 8:
                                    ships_img.get(2).setVisible(false);
                                    ships_img.get(8).setVisible(false);
                                    break;
                                case 3:
                                case 9:
                                    ships_img.get(3).setVisible(false);
                                    ships_img.get(9).setVisible(false);
                                    break;
                                case 4:
                                case 10:
                                    ships_img.get(4).setVisible(false);
                                    ships_img.get(10).setVisible(false);
                                    break;
                                case 5:
                                case 11:
                                    ships_img.get(5).setVisible(false);
                                    ships_img.get(11).setVisible(false);
                                    break;
                            }
                        }
                        //all_fleet.getChildren().remove(1);
                    }
                }
            });
        }
    }

    private void createPlayingFields(Player pl, Pane playingFields, int b_x, int b_y) {

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                pl.water[i][j] = new StackPane();
                ImageView img_ot = new ImageView(map_img.get("ot"));
                ImageView img_us = new ImageView(map_img.get("us"));
                ImageView img_hs = new ImageView(map_img.get("hs"));
                ImageView img_dt = new ImageView(map_img.get("dt"));
                ImageView img_ds = new ImageView(map_img.get("ds"));
                pl.water[i][j].getChildren().add(img_us);
                pl.water[i][j].getChildren().add(img_ds);
                pl.water[i][j].getChildren().add(img_hs);
                pl.water[i][j].getChildren().add(img_dt);
                pl.water[i][j].getChildren().add(img_ot);
                pl.board.add(pl.water[i][j], j, i);
            }
        pl.board.setTranslateX(b_x);
        pl.board.setTranslateY(b_y);
        playingFields.getChildren().add(pl.board);

        ImageView numB = new ImageView(map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX((b_x == 150) ? 105 : 705 + 495);
        numB.setTranslateY(115);
        playingFields.getChildren().add(numB);
        ImageView letB = new ImageView(map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX((b_x == 150) ? 150 : 750);
        letB.setTranslateY(70);
        playingFields.getChildren().add(letB);


        Rectangle sa = new Rectangle(360, 135, Color.valueOf("#EFF0F1"));
        sa.setOpacity(0.5);
        sa.setArcHeight(20);
        sa.setArcWidth(20);
        pl.stat_area.setLayoutX((b_x == 150) ? 195 : 795);
        pl.stat_area.setLayoutY(565);
        pl.stat_area.setAlignment(Pos.CENTER);
        pl.stat_area.getChildren().addAll(sa, pl.cur_stat);
        pl.cur_stat.setTranslateY(17);

        Rectangle na = new Rectangle(225, 45, Color.valueOf("#EFF0F1"));
        na.setOpacity(0.5);
        na.setArcHeight(20);
        na.setArcWidth(20);
        pl.name_area.setLayoutX((b_x == 150) ? 150 : 750);
        pl.name_area.setLayoutY(20);
        Text name = new Text(pl.name.toString());
        name.setFill(Color.valueOf("#455760"));
        name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
        pl.name_area.getChildren().addAll(na, name);
        name.setTranslateX(-25);
        playingFields.getChildren().addAll(pl.stat_area, pl.name_area);


    }

    private void createPointer(Pane playingFields, MainStPain msp, Player p1, Player p2) {
        ImageView rp = new ImageView(map_img.get("rp"));
        ImageView gp = new ImageView(map_img.get("gp"));
        pointer.getChildren().addAll(rp, gp);
        playingFields.getChildren().add(pointer);
        pointer.setMaxWidth(150);
        pointer.setMaxHeight(225);
        pointer.setLayoutX(600);
        pointer.setLayoutY(295);
        rp.setTranslateX(25);
        rp.setTranslateY(-20);
        gp.setTranslateX(45);
        gp.setTranslateY(-20);
        rp.setVisible(false);

        Main.MenuItem bck = new Main.MenuItem("BACK", 90, 90);
        bck.setTranslateX(0);
        bck.setTranslateY(630);
        playingFields.getChildren().add(bck);
        bck.setOnMouseClicked(event -> {
            clearBoard(p1);
            clearBoard(p2);
            playingFields.setVisible(false);
            removePane(playingFields);
            msp.menuBox.setVisible(true);
            msp.title.setVisible(true);
        });
    }

    private void removePane(Pane pane) {
        pane.getChildren().clear();
        pane = null;
    }

    private void printShipLocation(Player pl) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (pl.field.grid[i][j].getLinkShip() != null && pl.field.grid[i][j].getState() != 'x') {
                    pl.water[i][j].getChildren().get(4).setVisible(false);
                    pl.water[i][j].getChildren().get(3).setVisible(false);
                    pl.water[i][j].getChildren().get(2).setVisible(false);
                    pl.water[i][j].getChildren().get(1).setVisible(false);
                }
            }
        }
    }

    void humanVscomputer(Pane gameHvC, MainStPain msp) {
        HumanPlayer hp = new HumanPlayer();
        prepareForBattle(hp, gameHvC, msp);
        Main.MenuItem ready = new Main.MenuItem("BATTLE!", 400, 50);
        ready.setTranslateX(800);
        ready.setTranslateY(615);
        gameHvC.getChildren().add(ready);


        ready.setOnMouseClicked(event -> {
            Pane playingFields = new Pane();
            playingFields.setMaxHeight(720);
            playingFields.setMaxWidth(1280);
            msp.getChildren().add(playingFields);
            playingFields.setVisible(true);

            if (hp.field.fleet.size() == 10) {
                if (!hp.namePlayer.getText().isEmpty()) {
                    hp.name.delete(0, hp.name.length());
                    hp.name.append(hp.namePlayer.getText());
                    if (hp.name.length() > 15) {
                        hp.name.delete(15, hp.name.length());
                    }
                }
                ComputerPlayer cp = new ComputerPlayer();
                cp.setPlaceShipRand();
                gameHvC.setVisible(false);

                cp.setEnemyField(hp.getRefField());
                hp.setEnemyField(cp.getRefField());

                createPointer(playingFields, msp, hp, cp);
                createPlayingFields(hp, playingFields, 150, 115);
                createPlayingFields(cp, playingFields, 750, 115);

                cp.board.setOnMouseClicked(event1 -> {
                    boolean f = false;
                    byte row;
                    byte col;
                    Coord coord;
                    Node node = event1.getPickResult().getIntersectedNode();
                    row = 0;
                    col = 0;
                    while (row < 10 && !f) {
                        col = 0;
                        while (col < 10 && !f) {
                            if (node == cp.water[row][col].getChildren().get(4))
                                f = true;
                            else
                                col++;
                        }
                        if (!f)
                            row++;
                    }
                    if (f == true) {
                        coord = new Coord(row, col);
                        if (false == hp.yourTurn(this, cp, coord)) {
                            pointer.getChildren().get(1).setVisible(false);
                            pointer.getChildren().get(0).setVisible(true);
                            if (cp.gameOver()) {

                            } else {
                                           /* PauseTransition pause = new PauseTransition(Duration.millis(400));
                                            pause.play();
                                            pause.setOnFinished(event2 -> {*/
                                cp.yourTurn(this, hp);
                                //  });
                                pointer.getChildren().get(0).setVisible(false);
                                pointer.getChildren().get(1).setVisible(true);
                            }
                            if (hp.gameOver()) {
                                printShipLocation(cp);
                            }
                        }
                    }
                });

            }
        });

    }

    void humanVsHuman(Pane gameHvH, MainStPain msp) {
        HumanPlayer hp1 = new HumanPlayer();

        prepareForBattle(hp1, gameHvH, msp);
        Main.MenuItem next = new Main.MenuItem("NEXT PLAYER", 400, 50);
        next.setTranslateX(800);
        next.setTranslateY(615);
        gameHvH.getChildren().add(next);

        Pane gameHvH2 = new Pane();
        next.setOnMouseClicked(event -> {
            first_click_auto = true;
            if (hp1.field.fleet.size() == 10) {
                gameHvH.setVisible(false);
                for (int i = 0; i < 10; i++)
                    for (int j = 0; j < 10; j++)
                        water[i][j].getChildren().get(1).setVisible(true);
                for (ImageView img : ships_img) {
                    img.setVisible(true);
                }
                gameHvH2.setMaxHeight(720);
                gameHvH2.setMaxWidth(1280);
                if (false == msp.getChildren().contains(gameHvH2)) {
                    gameHvH2.getChildren().clear();
                    gameHvH2.setVisible(true);
                    msp.getChildren().add(gameHvH2);
                }
                HumanPlayer hp2 = new HumanPlayer();
                prepareForBattle(hp2, gameHvH2, msp);
                Main.MenuItem ready = new Main.MenuItem("BATTLE!", 400, 50);
                ready.setTranslateX(800);
                ready.setTranslateY(615);
                gameHvH2.getChildren().add(ready);

                Main.MenuItem bck = new Main.MenuItem("QUIT", 90, 90);
                bck.setTranslateX(0);
                bck.setTranslateY(630);
                bck.setOpacity(0.5);
                gameHvH2.getChildren().add(bck);

                bck.setOnMouseClicked(event1 -> {
                    clearBoard(hp1);
                    clearBoard(hp2);
                    gameHvH.setVisible(false);
                    removePane(gameHvH);
                    gameHvH2.setVisible(false);
                    removePane(gameHvH2);
                    msp.menuBox.setVisible(true);
                    msp.title.setVisible(true);
                });


                ready.setOnMouseClicked(event2 -> {
                    Pane playingFields = new Pane();
                    playingFields.setMaxHeight(720);
                    playingFields.setMaxWidth(1280);
                    msp.getChildren().add(playingFields);
                    playingFields.setVisible(true);
                    if (hp1.field.fleet.size() == 10) {
                        if (!hp1.namePlayer.getText().isEmpty()) {
                            hp1.name.delete(0, hp1.name.length());
                            hp1.name.append(hp1.namePlayer.getText());
                            if (hp1.name.length() > 15) {
                                hp1.name.delete(15, hp1.name.length());
                            }
                        }
                    }
                    if (hp2.field.fleet.size() == 10) {
                        if (!hp2.namePlayer.getText().isEmpty()) {
                            hp2.name.delete(0, hp2.name.length());
                            hp2.name.append(hp2.namePlayer.getText());
                            if (hp2.name.length() > 15) {
                                hp2.name.delete(15, hp2.name.length());
                            }
                        }
                    }

                    gameHvH2.setVisible(false);

                    hp1.setEnemyField(hp2.getRefField());
                    hp2.setEnemyField(hp1.getRefField());

                    createPointer(playingFields, msp, hp1, hp2);
                    createPlayingFields(hp1, playingFields, 150, 115);
                    createPlayingFields(hp2, playingFields, 750, 115);
                   /* Rectangle invis = new Rectangle(450,450);
                    invis.setOpacity(0);
                    invis.setVisible(false);
                    playingFields.getChildren().add(invis);*/


                        hp2.board.setOnMouseClicked(event1 -> {
                            /*invis.setTranslateX(150);
                            invis.setTranslateY(115);*/
                            boolean f = false;
                            byte row;
                            byte col;
                            Coord coord;
                            Node node = event1.getPickResult().getIntersectedNode();
                            row = 0;
                            col = 0;
                            while (row < 10 && !f) {
                                col = 0;
                                while (col < 10 && !f) {
                                    if (node == hp2.water[row][col].getChildren().get(4))
                                        f = true;
                                    else
                                        col++;
                                }
                                if (!f)
                                    row++;
                            }
                            if (f == true) {
                                coord = new Coord(row, col);

                                if (false == hp1.yourTurn(this, hp2, coord)) {
                                    pointer.getChildren().get(1).setVisible(false);
                                    pointer.getChildren().get(0).setVisible(true);
                                    if (hp2.gameOver()) {
                                        printShipLocation(hp1);
                                    }
                                    else{
                                        hp1.board.setOnMouseClicked(event3 -> {
                                         /*   invis.setLayoutX(750);
                                            invis.setLayoutY(115);*/
                                            boolean f2 = false;
                                            byte r;
                                            byte c;
                                            Coord crd;
                                            Node nde = event3.getPickResult().getIntersectedNode();
                                            r = 0;
                                            c = 0;
                                            while (r < 10 && !f2) {
                                                c = 0;
                                                while (c < 10 && !f2) {
                                                    if (nde == hp1.water[r][c].getChildren().get(4))
                                                        f2 = true;
                                                    else
                                                        c++;
                                                }
                                                if (!f2)
                                                    r++;
                                            }
                                            if (f2 == true) {
                                                crd = new Coord(r, c);

                                                if (false == hp2.yourTurn(this, hp1, crd)) {
                                                    pointer.getChildren().get(0).setVisible(false);
                                                    pointer.getChildren().get(1).setVisible(true);
                                                    if (hp1.gameOver()) {
                                                        printShipLocation(hp2);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });


                });

            }

        });
    }
}
