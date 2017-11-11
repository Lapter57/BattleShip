package Logics;
import Logics.coord.Coord;
import loading.Loading;

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
import statistics.PlayerStats;


import java.util.ArrayList;
import java.util.HashMap;

class Game {
    private HashMap<String, Image> map_img = new HashMap<>();
    private ArrayList<ImageView> ships_img = new ArrayList<>();
    private GridPane board = new GridPane();
    private StackPane all_fleet = new StackPane();
    private Pane fleet_h = new Pane();
    private Pane fleet_v = new Pane();
    private StackPane[][] water = new StackPane[10][10];
    private byte cur_sizeS;
    private char cur_dir;
    private Boolean first_click_auto = true;
    private StackPane pointer = new StackPane();
    private ArrayList<Rectangle> focusTiles = new ArrayList<>();
    private PlayerStats playerStats;

    Game(PlayerStats playerStats) {
        this.playerStats = playerStats;
        board.setPrefSize(10, 10);
        for(int i = 0; i < 4; i++){
            Rectangle rec = new Rectangle(45,45,Color.LIGHTGREEN);
            focusTiles.add(rec);
        }
        Loading.loadIMG(map_img, ships_img);
        Loading.loadPaneOfLocOfShips(water, map_img, ships_img, board, fleet_h, fleet_v, all_fleet);
    }

    private void clearBoard(Player pl) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                water[i][j].getChildren().get(1).setVisible(true);

        for (ImageView img : ships_img) {
            img.setVisible(true);
        }
        pl.field.clearField();
        pl.estabShip = 0;

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
            clearBoard(hp);
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
            first_click_auto = true;
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

        board.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                for(int k = 0; k < 4; k++) {
                    focusTiles.get(k).setVisible(false);
                }
            }
        });
        board.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    event.acceptTransferModes(TransferMode.COPY);
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
                                if(water[row][col].getChildren().size() == 3 && node == water[row][col].getChildren().get(2) && node != focusTiles.get(0))
                                    f = true;
                            else
                                col++;
                        }
                        if (!f)
                            row++;
                    }
                    if (f) {
                        Coord crd1 = new Coord(row, col);
                        Coord crd2 = new Coord((cur_dir == 'h') ? row : (byte) (row + cur_sizeS - 1), (cur_dir == 'v') ? col : (byte) (col + cur_sizeS - 1));
                        if (hp.checkCollision(crd1, crd2)) {
                            int shift;
                            if (cur_dir == 'h') {
                                shift = col + cur_sizeS;
                                for (int j = col; j < shift; j++) {
                                    if(water[row][j].getChildren().size() != 3) {
                                        water[row][j].getChildren().add(focusTiles.get(j - col));
                                    }
                                    water[row][j].getChildren().get(2).setVisible(true);

                                }
                            } else {
                                shift = row + cur_sizeS;
                                for (int i = row; i < shift; i++) {
                                    if(water[i][col].getChildren().size() != 3) {
                                        water[i][col].getChildren().add(focusTiles.get(i - row));
                                    }
                                    water[i][col].getChildren().get(2).setVisible(true);
                                }
                            }
                        }
                        else{
                            for(int k = 0; k < 4; k++) {
                                focusTiles.get(k).setVisible(false);
                            }
                        }
                    }
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
                            if (water[row][col].getChildren().size() == 3 && node == water[row][col].getChildren().get(2))
                                f = true;
                            else
                                col++;
                        }
                        if (!f)
                            row++;
                    }
                    if (f) {
                        Coord crd1 = new Coord(row, col);
                        Coord crd2 = new Coord((cur_dir == 'h') ? row : (byte) (row + cur_sizeS - 1), (cur_dir == 'v') ? col : (byte) (col + cur_sizeS - 1));

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
                        for (int k = 0; k < cur_sizeS; k++) {
                            focusTiles.get(k).setVisible(false);
                        }
                        if (cur_dir == 'h') {
                            shift = col + cur_sizeS;
                            for (int j = col; j < shift; j++) {
                                water[row][j].getChildren().get(1).setVisible(false);
                                water[row][j].getChildren().get(0).setVisible(true);
                            }
                        } else {
                            shift = row + cur_sizeS;
                            for (int i = row; i < shift; i++) {
                                water[i][col].getChildren().get(1).setVisible(false);
                                water[i][col].getChildren().get(0).setVisible(true);
                            }
                        }
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
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
                    Dragboard db = ship.startDragAndDrop(TransferMode.COPY);
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
                    if (modeUsed == TransferMode.COPY) {
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
        if(pointer.getChildren().size() == 0) {
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
            pointer.getChildren().get(0).setVisible(false);
            pointer.getChildren().get(1).setVisible(true);
        }
        else{
            playingFields.getChildren().add(pointer);
            pointer.getChildren().get(0).setVisible(false);
            pointer.getChildren().get(1).setVisible(true);
        }

        Main.MenuItem bck = new Main.MenuItem("BACK", 90, 90);
        bck.setTranslateX(0);
        bck.setTranslateY(630);
        playingFields.getChildren().add(bck);
        bck.setOnMouseClicked(event -> {
            clearBoard(p1);
            clearBoard(p2);
            removePane(playingFields);
            msp.menuBox.setVisible(true);
            msp.title.setVisible(true);
            first_click_auto = true;
        });
    }

    private void removePane(Pane pane) {
        pane.getChildren().clear();
        pane.setVisible(false);
        pane = null;
    }

    private void printShipLocation(Player pl) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (pl.field.grid[i][j].getLinkShip() != null && pl.field.grid[i][j].getState() != 'x') {
                    ImageView img_ss = new ImageView(map_img.get("ss"));
                    pl.water[i][j].getChildren().add(img_ss);
                }
            }
        }
    }

    private void showLocationOfShips(HumanPlayer hp){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(hp.field.grid[i][j].getLinkShip() != null ) {
                    hp.water[i][j].getChildren().get(0).setVisible(true);
                    hp.water[i][j].getChildren().get(1).setVisible(false);
                    hp.water[i][j].getChildren().get(2).setVisible(false);
                    hp.water[i][j].getChildren().get(3).setVisible(false);
                    hp.water[i][j].getChildren().get(4).setVisible(false);
                }

            }
        }
    }

    void humanVsComputer(Pane gameHvC, MainStPain msp) {
        HumanPlayer hp = new HumanPlayer();
        hp.setLevel(1);
        prepareForBattle(hp, gameHvC, msp);
        Main.MenuItem ready = new Main.MenuItem("BATTLE!", 100, 70);
        ready.setTranslateX(1100);
        ready.setTranslateY(615);
        Main.MenuItem easy = new Main.MenuItem("EASY", 100, 70);
        easy.setTranslateX(770);
        easy.setTranslateY(615);
        Main.MenuItem normal = new Main.MenuItem("NORMAL", 100, 70);
        normal.bg.setFill(Color.valueOf("#A93927"));
        normal.text.setFill(Color.WHITE);
        normal.setDisable(true);
        normal.setTranslateX(880);
        normal.setTranslateY(615);
        Main.MenuItem hard = new Main.MenuItem("HARD", 100, 70);
        hard.setTranslateX(990);
        hard.setTranslateY(615);

        gameHvC.getChildren().addAll(ready, easy, normal, hard);

        ComputerPlayer cp = new ComputerPlayer();
        easy.setOnMouseClicked(event -> {
            cp.level.delete(0, cp.level.length());
            cp.level.append("easy");
            hp.setLevel(0);

            easy.setDisable(true);
            easy.bg.setFill(Color.valueOf("#A93927"));
            easy.text.setFill(Color.WHITE);

            normal.setDisable(false);
            normal.bg.setFill(Color.BLACK);
            normal.text.setFill(Color.DARKGRAY);

            hard.setDisable(false);
            hard.bg.setFill(Color.BLACK);
            hard.text.setFill(Color.DARKGRAY);
        });

        normal.setOnMouseClicked(event -> {
            cp.level.delete(0, cp.level.length());
            cp.level.append("normal");
            hp.setLevel(1);

            normal.bg.setFill(Color.valueOf("#A93927"));
            normal.text.setFill(Color.WHITE);
            normal.setDisable(true);

            easy.setDisable(false);
            easy.bg.setFill(Color.BLACK);
            easy.text.setFill(Color.DARKGRAY);

            hard.setDisable(false);
            hard.bg.setFill(Color.BLACK);
            hard.text.setFill(Color.DARKGRAY);
        });

        hard.setOnMouseClicked(event -> {
            cp.level.delete(0, cp.level.length());
            cp.level.append("hard");
            hp.setLevel(2);

            hard.bg.setFill(Color.valueOf("#A93927"));
            hard.text.setFill(Color.WHITE);
            hard.setDisable(true);

            easy.setDisable(false);
            easy.bg.setFill(Color.BLACK);
            easy.text.setFill(Color.DARKGRAY);

            normal.setDisable(false);
            normal.bg.setFill(Color.BLACK);
            normal.text.setFill(Color.DARKGRAY);
        });

        ready.setOnMouseClicked(event -> {
            if (hp.field.fleet.size() == 10) {
                if(first_click_auto) {
                    hp.field.initEmptyTiles();
                }

                Pane playingFields = new Pane();
                playingFields.setMaxHeight(720);
                playingFields.setMaxWidth(1280);
                msp.getChildren().add(playingFields);
                playingFields.setVisible(true);

                hp.checkName();
                cp.setPlaceShipRand();
                gameHvC.setVisible(false);
                cp.setEnemyField(hp.getRefField());
                hp.setEnemyField(cp.getRefField());
                createPointer(playingFields, msp, hp, cp);
                createPlayingFields(hp, playingFields, 150, 115);
                createPlayingFields(cp, playingFields, 750, 115);
                showLocationOfShips(hp);
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        int row = i;
                        int col = j;
                        cp.water[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                focusTiles.get(0).setVisible(true);
                                if (cp.water[row][col].getChildren().get(4).isVisible() && !cp.water[row][col].getChildren().contains(focusTiles.get(0)))
                                    cp.water[row][col].getChildren().add(focusTiles.get(0));
                                else
                                    focusTiles.get(0).setVisible(false);
                            }
                        });
                        cp.water[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                focusTiles.get(0).setVisible(false);
                            }
                        });
                    }
                }
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
                            if (cp.water[row][col].getChildren().size() == 6 && node == cp.water[row][col].getChildren().get(5)) {
                                focusTiles.get(0).setVisible(false);
                                f = true;
                            }
                            else
                                col++;
                        }
                        if (!f)
                            row++;
                    }
                    if (f) {
                        coord = new Coord(row, col);
                        if (!hp.yourTurn(this, cp, coord)) {
                            pointer.getChildren().get(1).setVisible(false);
                            pointer.getChildren().get(0).setVisible(true);
                            cp.yourTurn(hp);
                            pointer.getChildren().get(0).setVisible(false);
                            pointer.getChildren().get(1).setVisible(true);
                            if (hp.gameOver()) {
                                printShipLocation(cp);
                                cp.board.setDisable(true);
                            }
                        }
                        else{
                            hp.addShot();
                            if (cp.gameOver()) {
                                printShipLocation(hp);
                                cp.board.setDisable(true);
                                playerStats.updateStats(hp);
                            }
                        }
                    }
                });
            }

        });

    }

    void humanVsHuman(Pane gameHvH, MainStPain msp) {
        HumanPlayer hp1 = new HumanPlayer();
        hp1.setLevel(3);
        prepareForBattle(hp1, gameHvH, msp);
        Main.MenuItem next = new Main.MenuItem("NEXT PLAYER", 400, 50);
        next.setTranslateX(800);
        next.setTranslateY(615);
        gameHvH.getChildren().add(next);

        Pane gameHvH2 = new Pane();
        next.setOnMouseClicked(event -> {
            if(first_click_auto) {
                hp1.field.initEmptyTiles();
            }
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
                msp.getChildren().add(gameHvH2);

                HumanPlayer hp2 = new HumanPlayer();
                hp2.setLevel(3);
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
                    removePane(gameHvH);
                    removePane(gameHvH2);
                    msp.menuBox.setVisible(true);
                    msp.title.setVisible(true);
                });


                ready.setOnMouseClicked(event2 -> {
                    if (hp2.field.fleet.size() == 10) {
                        if (first_click_auto) {
                            hp2.field.initEmptyTiles();
                        }
                        Pane playingFields = new Pane();
                        playingFields.setMaxHeight(720);
                        playingFields.setMaxWidth(1280);
                        msp.getChildren().add(playingFields);
                        playingFields.setVisible(true);
                        hp1.checkName();
                        hp2.checkName();
                        gameHvH2.setVisible(false);
                        hp1.setEnemyField(hp2.getRefField());
                        hp2.setEnemyField(hp1.getRefField());
                        createPointer(playingFields, msp, hp1, hp2);
                        createPlayingFields(hp1, playingFields, 150, 115);
                        createPlayingFields(hp2, playingFields, 750, 115);
                        hp2.board.setDisable(false);
                        hp1.board.setDisable(true);

                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                int row = i;
                                int col = j;
                                hp1.water[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(true);
                                        if (hp1.water[row][col].getChildren().get(4).isVisible() && !hp1.water[row][col].getChildren().contains(focusTiles.get(0)))
                                            hp1.water[row][col].getChildren().add(focusTiles.get(0));
                                        else
                                            focusTiles.get(0).setVisible(false);
                                    }
                                });
                                hp1.water[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(false);
                                    }
                                });

                                hp2.water[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(true);
                                        if (hp2.water[row][col].getChildren().get(4).isVisible() && !hp2.water[row][col].getChildren().contains(focusTiles.get(0)))
                                            hp2.water[row][col].getChildren().add(focusTiles.get(0));
                                        else
                                            focusTiles.get(0).setVisible(false);
                                    }
                                });
                                hp2.water[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        focusTiles.get(0).setVisible(false);
                                    }
                                });
                            }
                        }

                        hp2.board.setOnMouseClicked(event1 -> {
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
                                    if (hp2.water[row][col].getChildren().size() == 6 && node == hp2.water[row][col].getChildren().get(5)) {
                                        focusTiles.get(0).setVisible(false);
                                        f = true;
                                    } else
                                        col++;
                                }
                                if (!f)
                                    row++;
                            }
                            if (f) {
                                coord = new Coord(row, col);

                                if (!hp1.yourTurn(this, hp2, coord)) {
                                    pointer.getChildren().get(1).setVisible(false);
                                    pointer.getChildren().get(0).setVisible(true);

                                    hp1.board.setDisable(false);
                                    hp2.board.setDisable(true);
                                    hp1.board.setOnMouseClicked(event3 -> {
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
                                                if (hp1.water[r][c].getChildren().size() == 6 && nde == hp1.water[r][c].getChildren().get(5)) {
                                                    focusTiles.get(0).setVisible(false);
                                                    f2 = true;
                                                } else
                                                    c++;
                                            }
                                            if (!f2)
                                                r++;
                                        }
                                        if (f2) {
                                            crd = new Coord(r, c);

                                            if (!hp2.yourTurn(this, hp1, crd)) {
                                                pointer.getChildren().get(0).setVisible(false);
                                                pointer.getChildren().get(1).setVisible(true);
                                                hp2.board.setDisable(false);
                                                hp1.board.setDisable(true);
                                            } else {
                                                hp2.addShot();
                                                if (hp1.gameOver()) {
                                                    printShipLocation(hp2);
                                                    hp1.board.setDisable(true);
                                                    hp2.board.setDisable(true);
                                                    playerStats.updateStats(hp2);
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    hp1.addShot();
                                    if (hp2.gameOver()) {
                                        printShipLocation(hp1);
                                        hp1.board.setDisable(true);
                                        hp2.board.setDisable(true);
                                        playerStats.updateStats(hp1);
                                    }
                                }
                            }
                        });
                    }

                });

            }

        });
    }
}
