package com.lapter57.statistics;

import com.lapter57.db.model.Stats;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
class TableStats{
    private TableView<Stats> table = new TableView<>();
    private ObservableList<Stats> list = FXCollections.observableArrayList();

    TableStats() {
        TableColumn<Stats, String> nickNameCol = new TableColumn<>("Nickname");
        nickNameCol.setPrefWidth(200);
        nickNameCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        nickNameCol.setStyle("-fx-font-size: 18");

        TableColumn<Stats, Double> scoreCol = new TableColumn<>("Score");
        scoreCol.setPrefWidth(200);
        scoreCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        scoreCol.setStyle("-fx-font-size: 18");

        TableColumn<Stats, String> levelCol = new TableColumn<>("Level");
        levelCol.setPrefWidth(200);
        levelCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        levelCol.setStyle("-fx-font-size: 18");

        TableColumn<Stats, String> dateCol = new TableColumn<>("Date");
        dateCol.setPrefWidth(200);
        dateCol.minWidthProperty().bind(nickNameCol.prefWidthProperty());
        dateCol.setStyle("-fx-font-size: 18");

        nickNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPlayer().getNickname()));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        levelCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGame().getLevel().toString()));
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(DateTimeFormatter.ISO_LOCAL_DATE.format(cell.getValue().getGame().getDate().toLocalDateTime())));

        table.getColumns().addAll(nickNameCol, scoreCol, levelCol, dateCol);

        table.setMaxWidth(1000);
        table.setMaxHeight(700);
        table.setTranslateX(250);
        table.setTranslateY(150);
        table.setStyle("-fx-selection-bar: gray");
    }

    void fillList(final List<Stats> stats){
        if (!list.isEmpty()) {
            list.removeAll();
            table.getItems().clear();
        }
        list.addAll(stats);
        if (!table.getItems().contains(list)) {
            table.setItems(list);
        }
    }

    TableView<Stats> getTable() {
        return table;
    }

}


