package statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableStats{
    private TableView<PlayerAccount> table = new TableView<>();
    private ObservableList<PlayerAccount> list = FXCollections.observableArrayList();

    public TableStats(){
        TableColumn<PlayerAccount, String> nickNameCol = new TableColumn<>("Nickname");
        nickNameCol.setPrefWidth(200);
        nickNameCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        nickNameCol.setStyle("-fx-font-size: 18");
        //nickNameCol.setStyle("-fx-alignment: CENTER");

        TableColumn<PlayerAccount, Double> scoreCol = new TableColumn<>("Score");
        scoreCol.setPrefWidth(200);
        scoreCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        scoreCol.setStyle("-fx-font-size: 18");
        //scoreCol.setStyle("-fx-alignment: CENTER");

        TableColumn<PlayerAccount, String> levelCol = new TableColumn<>("Level");
        levelCol.setPrefWidth(200);
        levelCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        levelCol.setStyle("-fx-font-size: 18");
        //levelCol.setStyle("-fx-alignment: CENTER");

        TableColumn<PlayerAccount, String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(200);
        dateCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        dateCol.setStyle("-fx-font-size: 18");
        //dateCol.setStyle("-fx-alignment: CENTER");

        nickNameCol.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(nickNameCol, scoreCol, levelCol, dateCol);

        table.setMaxWidth(1000);
        table.setMaxHeight(700);
        table.setTranslateX(250);
        table.setTranslateY(150);
    }

    public void fillList(Statement statement){
        if(!list.isEmpty()){
            list.removeAll();
            table.getItems().clear();
        }
        try {
            ResultSet resultSet = statement.executeQuery("select * from stats order by score asc");
            while(resultSet.next()){
                PlayerAccount plAccount = new PlayerAccount(resultSet.getString("nickname"), resultSet.getDouble("score"), resultSet.getString("level"), resultSet.getDate("datetime").toString());
                list.add(plAccount);
            }
            if(!table.getItems().contains(list)){
                table.setItems(list);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public TableView<PlayerAccount> getTable() {
        return table;
    }
}


