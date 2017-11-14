package statistics;

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
            Integer hpLevel = hp.getLevel();
            String strLevel = null;
            switch (hpLevel){
                case 0:
                    strLevel = new String("Easy");
                    break;
                case 1:
                    strLevel = new String("Normal");
                    break;
                case 2:
                    strLevel = new String("Hard");
                    break;
                case 3:
                    strLevel = new String("Human");
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
                    int lev = 0;
                    switch (resultSet.getString("level")){
                        case "Easy":
                            lev = 0;
                            break;
                        case "Normal":
                            lev = 1;
                            break;
                        case "Hard":
                            lev = 2;
                            break;
                        case "Human":
                            lev = 3;
                            break;
                    }
                    if(resultSet.getDouble("score") > hpScore && lev <= hpLevel || resultSet.getDouble("score") >= hpScore && lev < hpLevel){
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
