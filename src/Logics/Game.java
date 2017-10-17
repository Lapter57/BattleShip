package Logics;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Game {
    // Constructor and destructor
    public Game() {//hConsole_ = GetStdHandle(STD_OUTPUT_HANDLE);}
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
    public void humanVscomputer(Pane root, Main.MenuBox menuBox, Scene scene) {
        HumanPlayer p1 = new HumanPlayer();
        TextField namePlayer = new TextField();
        namePlayer.setPromptText("Enter your nickname");
        namePlayer.setFocusTraversable(false);
        namePlayer.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,26));
        namePlayer.setPrefSize(400,50);
        StackPane st = new StackPane(namePlayer);
        st.setTranslateX(800);
        st.setTranslateY(550);
        namePlayer.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                if(!namePlayer.getText().isEmpty())
                    p1.name = namePlayer.getText();
                st.requestFocus();
            }
        });

        root.getChildren().add(st);
        Main.MenuItem autoChoice = new Main.MenuItem("AUTO");
        Main.MenuItem clear = new Main.MenuItem("CLEAR");
        Main.MenuItem ready = new Main.MenuItem("BATTLE!");
        ready.setTranslateX(800);
        ready.setTranslateY(615);
        root.getChildren().add(ready);
        VBox deployMenu = new VBox(
                autoChoice,clear
        );
        deployMenu.setSpacing(15);
        deployMenu.setTranslateX(175);
        deployMenu.setTranslateY(550);
        menuBox.setSubMenu(deployMenu);

        GridPane board = new GridPane();
        GridPane shipsToBeBoard = new GridPane();
        ImageView[][] water = new ImageView[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++) {
                try(InputStream is = Files.newInputStream(Paths.get("res/images/Ordinary_Tile_2.png"))) {
                    water[i][j] = new ImageView(new Image(is));
                    water[i][j].setPreserveRatio(true);
                    water[i][j].setFitHeight(45);
                    water[i][j].setFitWidth(45);
                    board.add(water[i][j],i,j);
                }
                catch (IOException e) {
                    System.out.println("Couldn't load image");
                }
        }
        board.setTranslateX(150);
        board.setTranslateY(70);

        try(InputStream is = Files.newInputStream(Paths.get("res/images/Num_Board.png"))) {
            ImageView numB = new ImageView(new Image(is));
            numB.setFitHeight(450);
            numB.setFitWidth(45);
            numB.setTranslateX(105);
            numB.setTranslateY(70);
            root.getChildren().add(numB);
        }
        catch (IOException e){
            System.out.println("Couldn't load image");
        }
        try(InputStream is = Files.newInputStream(Paths.get("res/images/Let_Board.png"))) {
            ImageView letB = new ImageView(new Image(is));
            letB.setFitHeight(45);
            letB.setFitWidth(450);
            letB.setTranslateX(150);
            letB.setTranslateY(25);
            root.getChildren().add(letB);
        }
        catch (IOException e){
            System.out.println("Couldn't load image");
        }
        root.getChildren().add(board);
        ArrayDeque<Player> queue = new ArrayDeque<>(2);
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