package statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logics.players.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class TableStats{
    private TableView<PlayerAccount> table = new TableView<>();
    private ObservableList<PlayerAccount> list = FXCollections.observableArrayList();

    TableStats(){
        TableColumn<PlayerAccount, String> nickNameCol = new TableColumn<>("Nickname");
        nickNameCol.setPrefWidth(200);
        nickNameCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        nickNameCol.setStyle("-fx-font-size: 18");

        TableColumn<PlayerAccount, Double> scoreCol = new TableColumn<>("Score");
        scoreCol.setPrefWidth(200);
        scoreCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        scoreCol.setStyle("-fx-font-size: 18");

        TableColumn<PlayerAccount, String> levelCol = new TableColumn<>("Level");
        levelCol.setPrefWidth(200);
        levelCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        levelCol.setStyle("-fx-font-size: 18");

        TableColumn<PlayerAccount, String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(200);
        dateCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        dateCol.setStyle("-fx-font-size: 18");

        nickNameCol.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(nickNameCol, scoreCol, levelCol, dateCol);

        table.setMaxWidth(1000);
        table.setMaxHeight(700);
        table.setTranslateX(250);
        table.setTranslateY(150);
        table.setStyle("-fx-selection-bar: gray");
    }

    void fillList(Statement statement){
        if(!list.isEmpty()){
            list.removeAll();
            table.getItems().clear();
        }
        try {
            ResultSet resultSet = statement.executeQuery("select * from stats order by score asc");
            Set<PlayerAccount> hset = new HashSet<>();

            while(resultSet.next()){
                PlayerAccount plAccount = new PlayerAccount(resultSet.getString("nickname"), resultSet.getDouble("score"), resultSet.getString("level"), resultSet.getDate("datetime").toString());
                hset.add(plAccount);
                //sortPlayer.add(plAccount);
            }
            Set<PlayerAccount> sortPlayer = new TreeSet<>(hset);
            list.addAll(sortPlayer);
            if(!table.getItems().contains(list)){
                table.setItems(list);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    TableView<PlayerAccount> getTable() {
        return table;
    }
}


