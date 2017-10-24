package Logics;
import Logics.coord.Coord;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
  HashMap<String,Image> map_img = new HashMap<>();
  ArrayList<ImageView> ships_img = new ArrayList<>();
  Boolean clearBut = false;
  GridPane board = new GridPane();
    {board.setPrefSize(10, 10);}
  StackPane all_fleet = new StackPane();
  Pane fleet_h = new Pane();
  Pane fleet_v = new Pane();
  StackPane[][] water = new StackPane[10][10];
  private byte cur_sizeS;
  private char cur_dir;
  Boolean first_click_auto = true;
  StackPane pointer = new StackPane();
    TextField namePlayer = new TextField();

    public Game() {
        try(InputStream ot = Files.newInputStream(Paths.get("res/images/Ordinary_Tile_2.png"));
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
            map_img.put("ot",new Image(ot));
            map_img.put("dt",new Image(dt));
            map_img.put("ft",new Image(ft));
            map_img.put("us",new Image(us));
            map_img.put("hs",new Image(hs));
            map_img.put("ds",new Image(ds));
            map_img.put("nb",new Image(nb));
            map_img.put("lb",new Image(lb));
            map_img.put("gp",new Image(gr_p));
            map_img.put("rp",new Image(red_p));

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
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++) {
                water[i][j] = new StackPane();
                ImageView img_ot = new ImageView(map_img.get("ot"));
//              ImageView img_ft = new ImageView(map_img.get("ft"));
                ImageView img_us = new ImageView(map_img.get("us"));
                water[i][j].getChildren().add(img_us);
//              water[i][j].getChildren().add(img_ft);
                water[i][j].getChildren().add(img_ot);
                board.add(water[i][j],j,i);
            }
        board.setTranslateX(150);
        board.setTranslateY(70);

        Rectangle fl_h = new Rectangle(450,450, Color.valueOf("#EFF0F1"));
        fl_h.setOpacity(0.5);
        fleet_h.getChildren().add(fl_h);
        Rectangle fl_v = new Rectangle(450,450, Color.valueOf("#EFF0F1"));
        fl_v.setOpacity(0.5);
        fleet_v.getChildren().add(fl_v);
        fleet_v.setVisible(false);

        fleet_h.getChildren().add(ships_img.get(0));
        ships_img.get(0).setLayoutX(225);
        ships_img.get(0).setLayoutY(45);
        int i;
        int shift = 270;
        for(i = 1; i < 3; i++){
            fleet_h.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(shift);
            ships_img.get(i).setLayoutY(135);
            shift -= 180;
        }

        shift = 315;
        for(; i < 6; i++){
            fleet_h.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(shift);
            ships_img.get(i).setLayoutY(225);
            shift -= 135;
        }
        shift = 360;
        for(i = 12;i < 16; i++){
            fleet_h.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(shift);
            ships_img.get(i).setLayoutY(315);
            shift -= 90;
        }

        fleet_v.getChildren().add(ships_img.get(6));
        ships_img.get(6).setLayoutX(45);
        ships_img.get(6).setLayoutY(225);
        shift = 270;
        for(i = 7; i < 9; i++){
            fleet_v.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(135);
            ships_img.get(i).setLayoutY(shift);
            shift -= 180;
        }

        shift = 315;
        for(; i < 12; i++){
            fleet_v.getChildren().add(ships_img.get(i));
            ships_img.get(i).setLayoutX(225);
            ships_img.get(i).setLayoutY(shift);
            shift -= 135;
        }
        all_fleet.getChildren().addAll(fleet_v,fleet_h);
        all_fleet.setMaxWidth(450);
        all_fleet.setMinHeight(450);
    }


    // Three game modes
   /* public void computerVscomputer() {
        ArrayDeque<Player> queue = new ArrayDeque<>(2);
        ComputerPlayer c1 = new ComputerPlayer("Computer_1");
        queue.addLast(c1);
        ComputerPlayer c2 = new ComputerPlayer("Computer_2");
        queue.addLast(c2);

        c1.setPlaceShipRand();
        c2.setPlaceShipRand();
        c2.setEnemyField(c1.getRefField());
        c1.setEnemyField(c2.getRefField());

        Player curP;
        byte id = 0;
        while (!c1.gameOver() && !c2.gameOver()) {
            curP = queue.pop();
            curP.yourTurn(this, queue.getFirst(), id);
            id = (id == 0) ? id++ : id--;
            queue.addLast(curP);
        }

        //system("CLS");
        outputFields(c1, c2);
        //COORD coordConsole = { 46, 6 };
        //SetConsoleCursorPosition(hConsole_, coordConsole);
        if (c1.gameOver()) //!!!!! Вывод
            System.out.println("***Computer_2 WIN***");
        else
            System.out.println("***Computer_1 WIN***");
        if (id == 0)
            printShipLocation((byte) 69, (byte) 14, c2);
        else
            printShipLocation((byte) 21, (byte) 14, c1);
    }

    public void humanVshuman() {
        ArrayDeque<Player> queue = new ArrayDeque<>(2);
        byte n;
        Scanner scanner = new Scanner(System.in);

        COORD coordConsole = { 40, 0 };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        String inp = "Player_1! Do you want to enter your name? (1-yes, 0-no): ";
        System.out.println(inp);
        try {
            n = scanner.nextByte();
            if (n > 1) n = inputValid(inp);
        } catch (NumberFormatException e) {
            n = inputValid(inp);
        }
        String name1;
        HumanPlayer p1 = new HumanPlayer("Player_1");
        if (n == 1) {
           *//*coordConsole = { 40, 1 };
            SetConsoleCursorPosition(hConsole_, coordConsole);*//*
            System.out.println("Enter your name: ");
            name1 = scanner.nextLine();
            p1.name = name1;
        }

       coordConsole = { 40, (n == 1) ? 2 : 1 };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        inp = "Do you want to put the ships yourself? (1-yes, 0-no): ";
        System.out.println(inp);
        try {
            n = scanner.nextByte();
            if (n > 1) n = inputValid(inp);
        } catch (NumberFormatException e) {
            n = inputValid(inp);
        }
        if (n == 1)
            p1.setPlaceShip(this);
        else
            p1.setPlaceShipRand();
        // system("CLS");

       *//*coordConsole = { 40, 0 };
        SetConsoleCursorPosition(hConsole_, coordConsole);*//*
        inp = "Player_2! Do you want to enter your name? (1-yes, 0-no): ";
        System.out.println(inp);
        try {
            n = scanner.nextByte();
            if (n > 1) n = inputValid(inp);
        } catch (NumberFormatException e) {
            n = inputValid(inp);
        }
        String name2;
        HumanPlayer p2 = new HumanPlayer("Player_2");


        if (n == 1) {
          coordConsole = { 40, 1 };
            SetConsoleCursorPosition(hConsole_, coordConsole);
            System.out.println("Enter your name: ");
            name2 = scanner.nextLine();
            p2.name = name2;
        }

       coordConsole = { 40, (n == 1) ? 2 : 1 };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        inp = "Do you want to put the ships yourself? (1-yes, 0-no): ";
        try {
            n = scanner.nextByte();
            if (n > 1) n = inputValid(inp);
        } catch (NumberFormatException e) {
            n = inputValid(inp);
        }
        if (n == 1)
            p2.setPlaceShip(this);
        else
            p2.setPlaceShipRand();

        queue.addLast(p1);
        queue.addLast(p2);
        p2.setEnemyField(p1.getRefField());
        p1.setEnemyField(p2.getRefField());

        Player curP;
        byte id = 0;
        while (!p1.gameOver() && !p2.gameOver()) {
            curP = queue.pop();
            curP.yourTurn(this, queue.getFirst(), id);
            id = (id == 0) ? id++ : id--;
            queue.addLast(curP);
        }
        //system("CLS");

        outputFields(p1, p2);
        coordConsole = { 46, 6 };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        if (p1.gameOver())
            System.out.println("***" + p2.getName() + " WIN***");
        else
            System.out.println("***" + p1.getName() + " WIN***");
        if (id == 0)
            printShipLocation((byte) 69, (byte) 14, p2); //цифры не нужны так как это старвй ввывод в консоли
        else
            printShipLocation((byte) 21, (byte) 14, p1);//цифры не нужны так как это старвй ввывод в консоли
    }
*/
    //private void prepareShips(){



    //}

    private void clearBoard(Player pl){
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                water[i][j].getChildren().get(1).setVisible(true);

        for(ImageView img: ships_img){
            img.setVisible(true);
        }
            pl.field.clearField();
            pl.ships.clear();

    }

    private void prepareForBattle(HumanPlayer hp, Pane gameHvH, MainStPain msp) {
        gameHvH.getChildren().addAll(board, all_fleet);
        all_fleet.setTranslateY(70);
        all_fleet.setTranslateX(750);

        namePlayer.setPromptText("Enter your nickname");
        namePlayer.setFocusTraversable(false);
        namePlayer.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 26));
        namePlayer.setPrefSize(400, 50);
        StackPane st = new StackPane(namePlayer);
        st.setTranslateX(800);
        st.setTranslateY(550);

        gameHvH.getChildren().add(st);
        Main.MenuItem autoChoice = new Main.MenuItem("AUTO",400,50);
        autoChoice.setOnMouseClicked(event -> {
            if(false == first_click_auto) {
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
        Main.MenuItem clear = new Main.MenuItem("CLEAR",400,50);
        clear.setOnMouseClicked(event -> {
            clearBoard(hp);
        });
        VBox deployMenu = new VBox(
                autoChoice,clear
        );

        Main.MenuItem bck = new Main.MenuItem("BACK",90,90);
        bck.setTranslateX(0);
        bck.setTranslateY(630);
        gameHvH.getChildren().add(bck);
        bck.setOnMouseClicked(event -> {
            clearBoard(hp);
            gameHvH.setVisible(false);
            msp.menuBox.setVisible(true);
            msp.title.setVisible(true);
        });

        deployMenu.setSpacing(15);
        deployMenu.setTranslateX(175);
        deployMenu.setTranslateY(550);
        gameHvH.getChildren().add(deployMenu);

        ImageView numB = new ImageView(map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX(105);
        numB.setTranslateY(70);
        gameHvH.getChildren().add(numB);
        ImageView letB = new ImageView(map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX(150);
        letB.setTranslateY(25);
        gameHvH.getChildren().add(letB);

        Main.MenuItem rot90 = new Main.MenuItem("Rot90",90,90);
        gameHvH.getChildren().add(rot90);
        rot90.setTranslateX(660);
        rot90.setTranslateY(70);
        rot90.setOnMouseClicked(event -> {
            if(fleet_h.isVisible()){
                fleet_h.setVisible(false);
                fleet_v.setVisible(true);
            }
            else{
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
                    Coord crd1 = new Coord(row,col);
                    Coord crd2 = new Coord((cur_dir == 'h')?row:(byte)(row+ cur_sizeS - 1),(cur_dir == 'v')?col:(byte)(col +cur_sizeS-1));

                    if(hp.checkCollision(crd1,crd2)){
                        if (cur_sizeS > 1) {
                            Ship ship = new Ship(cur_sizeS, crd1, crd2, hp.field);
                            ship.linkTilesWithDeck(hp.field);
                            ship.linkTilesWithHalo(hp.field);
                            hp.field.fleet.add(ship);
                        }
                        else {
                            Ship ship = new Ship(cur_sizeS, crd1, hp.field);
                            ship.linkTilesWithDeck(hp.field);
                            ship.linkTilesWithHalo(hp.field);
                            hp.field.fleet.add(ship);
                        }
                        hp.estabShip++;
                        int shift;
                        if(cur_dir == 'h'){
                            shift = col + cur_sizeS;
                            for(int j = col; j < shift; j++){
                                water[row][j].getChildren().get(1).setVisible(false);
                            }
                        }
                        else {
                            shift = row + cur_sizeS;
                            for(int i = row; i < shift; i++){
                                water[i][col].getChildren().get(1).setVisible(false);
                            }
                        }
                        event.setDropCompleted(true);
                    }
                    else {
                        event.setDropCompleted(false);
                    }

                } else {
                    event.setDropCompleted(false);
                }
            }
        });

        for(ImageView ship: ships_img) {
            ship.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int i = 0;
                    while(ships_img.get(i) != ship ) {
                        i++;
                    }
                    switch (i){
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
                    if(i < 6 || (i > 11 && i < 16))
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
                        if(cur_sizeS == 1)
                            ship.setVisible(false);
                        else{
                            int i = 0;
                            while(ships_img.get(i) != ship ) {
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

    private void createPlayingfields(Player pl, MainStPain msp, Pane playingFields, int b_x, int b_y){

        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++) {
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
                pl.board.add(pl.water[i][j],j,i);
            }
        pl.board.setTranslateX(b_x);
        pl.board.setTranslateY(b_y);
        playingFields.getChildren().add(pl.board);

        ImageView numB = new ImageView(map_img.get("nb"));
        numB.setFitHeight(450);
        numB.setFitWidth(45);
        numB.setTranslateX((b_x == 150)?105:705+495);
        numB.setTranslateY(115);
        playingFields.getChildren().add(numB);
        ImageView letB = new ImageView(map_img.get("lb"));
        letB.setFitHeight(45);
        letB.setFitWidth(450);
        letB.setTranslateX((b_x == 150)?150:750);
        letB.setTranslateY(70);
        playingFields.getChildren().add(letB);


        Rectangle sa = new Rectangle(360,135, Color.valueOf("#EFF0F1"));
        sa.setOpacity(0.5);
        sa.setArcHeight(20);
        sa.setArcWidth(20);
        pl.stat_area.setLayoutX((b_x == 150)?195:795);
        pl.stat_area.setLayoutY(565);
        pl.stat_area.setAlignment(Pos.CENTER);
        pl.stat_area.getChildren().addAll(sa, pl.cur_stat);
        pl.cur_stat.setTranslateY(17);

        Rectangle na = new Rectangle(225,45, Color.valueOf("#EFF0F1"));
        na.setOpacity(0.5);
        na.setArcHeight(20);
        na.setArcWidth(20);
        pl.name_area.setLayoutX((b_x == 150)?150:750);
        pl.name_area.setLayoutY(20);
        Text name = new Text(pl.name.toString());
        name.setFill(Color.valueOf("#455760"));
        name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,30));
        pl.name_area.getChildren().addAll(na,name);
        name.setTranslateX(-25);
        playingFields.getChildren().addAll(pl.stat_area, pl.name_area);


    }

    void createPointer(Pane playingFields){
        ImageView rp = new ImageView(map_img.get("rp"));
        ImageView gp = new ImageView(map_img.get("gp"));
        pointer.getChildren().addAll(rp,gp);
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
    }

    void printShipLocation(Player pl){
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(pl.field.grid[i][j].getLinkShip() != null && pl.field.grid[i][j].getState() != 'x'){
                    pl.water[i][j].getChildren().get(4).setVisible(false);
                    pl.water[i][j].getChildren().get(3).setVisible(false);
                    pl.water[i][j].getChildren().get(2).setVisible(false);
                    pl.water[i][j].getChildren().get(1).setVisible(false);
                }
            }
        }
    }

    public void humanVscomputer(Pane gameHvH, MainStPain msp){
        HumanPlayer hp = new HumanPlayer();
        prepareForBattle(hp,gameHvH, msp);
        Main.MenuItem ready = new Main.MenuItem("BATTLE!",400,50);
        ready.setTranslateX(800);
        ready.setTranslateY(615);
        gameHvH.getChildren().add(ready);

        Pane playingFields = new Pane();
        playingFields.setVisible(true);
        playingFields.setMaxHeight(720);
        playingFields.setMaxWidth(1280);

        ready.setOnMouseClicked(event -> {
            if(hp.field.fleet.size() == 10) {
                if(!namePlayer.getText().isEmpty()) {
                    hp.name.delete(0, hp.name.length());
                    hp.name.append(namePlayer.getText());
                    System.out.println(hp.name.toString());
                    if(hp.name.length() > 15){
                        hp.name.delete(15, hp.name.length());
                    }
                }
                ComputerPlayer cp = new ComputerPlayer();
                cp.setPlaceShipRand();
                gameHvH.setVisible(false);

                cp.setEnemyField(hp.getRefField());
                hp.setEnemyField(cp.getRefField());

                msp.getChildren().add(playingFields);
                createPointer(playingFields);
                createPlayingfields(hp, msp, playingFields, 150, 115);
                createPlayingfields(cp, msp, playingFields, 750, 115);

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
                                if(f == true) {
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

        //ArrayDeque<Player> queue = new ArrayDeque<>(2);
        /*ComputerPlayer c1 = new ComputerPlayer("Computer");
        queue.addLast(p1);
        queue.addLast(c1);
        c1.setPlaceShipRand();
        c1.setEnemyField(p1.getRefField());
        p1.setEnemyField(c1.getRefField());

        Player curP;
        byte id = 0;
        while (!p1.gameOver() && !c1.gameOver()) {
            curP = queue.pop();
            curP.yourTurn(this, queue.getFirst(), id);
            id = (id == 0) ? id++ : id--;
            queue.addLast(curP);
        }
*/
        /*COORD coordConsole = { 40, 0 };
        SetConsoleCursorPosition(hConsole_, coordConsole);*/
        /*String inp = "Do you want to enter your name? (1-yes, 0-no): ";
        System.out.println(inp);
        try {
            n = scanner.nextByte();
            if(n > 1) n = inputValid(inp);
        }catch (NumberFormatException e){
            n = inputValid(inp);
        }
        String name;
        HumanPlayer p1 = new HumanPlayer();
        if (n == 1) {

            System.out.println("Enter your name: ");
            name = scanner.nextLine();
            p1.name = name;
        }

       *//* coordConsole = { 40, (n == 1) ? 2 : 1 };
        SetConsoleCursorPosition(hConsole_, coordConsole);*//*
        inp = "Do you want to put the ships yourself? (1-yes, 0-no): ";
        System.out.println(inp);
        try {
            n = scanner.nextByte();
            if(n > 1) n = inputValid(inp);
        }catch (NumberFormatException e){
            n = inputValid(inp);
        }
        if(n==1)
            p1.setPlaceShip(this);
        else
            p1.setPlaceShipRand();*/

       /* ComputerPlayer c1 = new ComputerPlayer("Computer");
        queue.addLast(p1);
        queue.addLast(c1);
        c1.setPlaceShipRand();
        c1.setEnemyField(p1.getRefField());
        p1.setEnemyField(c1.getRefField());

        Player curP;
        byte id = 0;
        while (!p1.gameOver() && !c1.gameOver()) {
            curP = queue.pop();
            curP.yourTurn(this, queue.getFirst(), id);
            id = (id == 0) ? id++ : id--;
            queue.addLast(curP);
        }
        // system("CLS");

        outputFields(p1, c1);
      *//*  coordConsole = { 48, 6 };
        SetConsoleCursorPosition(hConsole_, coordConsole);*//*
        if (p1.gameOver())
            System.out.println("***You LOSE***");
        else
            System.out.println("***You WIN***");
        if (id == 0)
            printShipLocation((byte) 69, (byte) 14, c1); //цифры не нужны так как это старвй ввывод в консоли
        else
            printShipLocation((byte) 21, (byte) 14, p1);//цифры не нужны так как это старвй ввывод в консоли*/
}
    /*// Methods associated with the visual part(work in the console)
    public void outputFields(Player p1, Player p2){
        system("CLS");
        COORD coordConsole = { 28, 0 };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        SetConsoleTextAttribute(hConsole_, 11);
        cout << p1->getName();
        coordConsole = { 76, 0 };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        cout << p2->getName();
        print(21, 1, p1);
        print(69, 1, p2);
        printStats(5, 1, p1);
        printStats(96, 1, p2);
    }
    public void print(byte col, byte row, Player player){
        COORD coordConsole = { col, row };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        SetConsoleTextAttribute(hConsole_, 15);
        unsigned char i, j;
        i = j = 0;
        unsigned char k = 'A';
        cout << "  ";
        for (int i = 0; i < 10; i++)
            cout << k++ << ' ';
        cout << endl;
        for (auto && el1 : player->field_.grid_) {
            coordConsole = { col, row + i + 1 };
            SetConsoleCursorPosition(hConsole_, coordConsole);
            SetConsoleTextAttribute(hConsole_, 15);
            cout << int(i) << '|';
            for (auto && el2 : el1) {
                switch (el2->getState())
                {
                    case 'x': {
                        SetConsoleTextAttribute(hConsole_, 11); //4 red
                        cout << 'x';
                        SetConsoleTextAttribute(hConsole_, 15);
                        cout << '|';
                        break;
                    }
                    case '*': {
                        SetConsoleTextAttribute(hConsole_, 15);
                        cout << '*' << '|';
                        break;
                    }
                    default: {
                        SetConsoleTextAttribute(hConsole_, 15);
                        cout << ' ' << '|';
                    }
                }

                j++;
            }
            cout << endl;
            j = 0;
            i++;
        }
    }
    public void printShipLocation(byte col, byte row, Player player){
        COORD coordConsole = { col, row };
        SetConsoleCursorPosition(hConsole_, coordConsole);
        SetConsoleTextAttribute(hConsole_, 15);
        unsigned char i, j;
        i = j = 0;
        unsigned char k = 'A';
        cout << "  ";
        for (int i = 0; i < 10; i++)
            cout << k++ << ' ';
        cout << endl;
        for (auto && el1 : player->field_.grid_) {
            coordConsole = { col, row + i + 1 };
            SetConsoleCursorPosition(hConsole_, coordConsole);
            SetConsoleTextAttribute(hConsole_, 15);
            cout << int(i) << '|';
            for (auto && el2 : el1) {
                if (el2 != nullptr && el2->getLinkShip() != nullptr) {
                    SetConsoleTextAttribute(hConsole_, 4);
                    cout << 'x';
                    SetConsoleTextAttribute(hConsole_, 15);
                    cout << '|';
                }
                else {
                    SetConsoleTextAttribute(hConsole_, 15);
                    cout << ' ' << '|';
                }
                j++;
            }
            cout << endl;
            j = 0;
            i++;
        }
    }
    public void printStats(byte col, byte row, Player player){
        COORD coordConsole;
        for (unsigned char i = 4; i > 0; i--) {
            coordConsole = { col, row };
            row++;
            SetConsoleCursorPosition(hConsole_, coordConsole);
            switch (i) {
                case 4:
                    cout << "CARRIER: " << (int)player->ships_[i-1];
                    break;
                case 3:
                    cout << "SUBMARINE: " << (int)player->ships_[i-1];
                    break;
                case 2:
                    cout << "DESTROYER: " << (int)player->ships_[i-1];
                    break;
                case 1:
                    cout << "FRIGATE: " << (int)player->ships_[i-1];
            }
        }
    }

    // Input validation
    byte inputValid(final String inp){
        //COORD coordConsole;
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        byte r = 0;
        do {
            byte n;
          *//* system("CLS");
            coordConsole = { 30, 0};
            SetConsoleCursorPosition(hConsole_, coordConsole);*//*
            System.out.println("Incorrect data entered!");
            n = scanner.nextByte();
            if (n == 1 || n == 0) {
                check = false;
                r = n;
            }
        } while(check);
        return r;
    }
}*/


/* namePlayer.focusedProperty().addListener((obs,oldValue,newValue) ->{
            if(newValue){
                System.out.println("in");
            }else{
                System.out.println("out");
                st.requestFocus();
            }

        });*/
       /* ChangeListener<Boolean> focusLossListener = (observable, wasFocused, isFocused) -> {
            if (!isFocused) {
                st.requestFocus();
            }
        };
        namePlayer.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                namePlayer.focusedProperty().removeListener(focusLossListener);
                if(newValue){
                  namePlayer.focusedProperty().addListener(focusLossListener);
                    namePlayer.requestFocus();
                }
            }
        });*/