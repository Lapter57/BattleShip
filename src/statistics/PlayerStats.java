package statistics;

import Logics.Game;
import Logics.players.HumanPlayer;
import javafx.scene.control.TableView;

import java.sql.*;

public class PlayerStats {

    private static final String URL = "jdbc:mysql://localhost:3306/player_stats?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    Connection connection;
    private Statement statement;
    private TableStats table = new TableStats();

    public PlayerStats(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            table.fillList(statement);
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void updateStats(HumanPlayer hp){
        try {
            String hpName = hp.getName();
            double hpScore = (double)(hp.getNumShipAfloat() + hp.getNumWinShots())/2;
            Game.Level hpLevel = hp.getLevel();
            int hlevel = 1;
            String strLevel = null;
            switch (hpLevel){
                case EASY:
                    strLevel = new String("Easy");
                    hlevel = 0;
                    break;
                case NORMAL:
                    strLevel = new String("Normal");
                    hlevel = 1;
                    break;
                case HARD:
                    strLevel = new String("Hard");
                    hlevel = 2;
                    break;
                case HUMAN:
                    strLevel = new String("Human");
                    hlevel = 3;
                    break;
            }
            int cntRow = 0;
            ResultSet resultSet = statement.executeQuery("select count(*) from stats");
            while(resultSet.next()){
                cntRow = resultSet.getInt(1);
            }

            if(cntRow < 2){

                String str = "insert into stats(nickname, score, level, datetime) values(?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(str);
                preparedStatement.setString(1, hpName);
                preparedStatement.setDouble(2, hpScore);
                preparedStatement.setString(3, strLevel);
                long millis=System.currentTimeMillis();
                preparedStatement.setDate(4,new Date(millis));
                preparedStatement.executeUpdate();
                table.fillList(statement);
            }
            else{
                resultSet = statement.executeQuery("select * from stats order by score asc");
                boolean addPlayer = false;
                while(resultSet.next() && !addPlayer){
                    int level = 0;
                    switch (resultSet.getString("level")){
                        case "Easy":
                            level = 0;
                            break;
                        case "Normal":
                            level = 1;
                            break;
                        case "Hard":
                            level = 2;
                            break;
                        case "Human":
                            level = 3;
                            break;
                    }
                    if(resultSet.getDouble("score") > hpScore && level <= hlevel || resultSet.getDouble("score") >= hpScore && level < hlevel){
                        String str = "update stats set nickname = ?, score = ?, level = ?, datetime = ? where id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(str);
                        preparedStatement.setInt(5, resultSet.getInt("id"));
                        preparedStatement.setString(1, hpName);
                        preparedStatement.setDouble(2, hpScore);
                        preparedStatement.setString(3, strLevel);
                        long millis=System.currentTimeMillis();
                        preparedStatement.setDate(4,new Date(millis));
                        preparedStatement.executeUpdate();
                        addPlayer = true;
                    }
                }
                if(addPlayer){
                    table.fillList(statement);
                }
            }
        }
        catch (SQLException e){
                e.printStackTrace();
        }
    }

    public TableView<PlayerAccount> getTable(){
        return table.getTable();
    }
}
