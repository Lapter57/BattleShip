package statistics;

import Logics.HumanPlayer;

import java.sql.*;
import java.util.Date;

public class PlayerStats {

    private static final String URL = "jdbc:mysql://localhost:3306/player_stats";
    private static final String USERNAME = "Lapter";
    private static final String PASSWORD = "root";
    private Connection connection;
    private Statement statement;

    public PlayerStats(){
        try {
            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            statement = connection.createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    void updateStats(HumanPlayer hp){
        try {
            ResultSet resultSet = statement.executeQuery("select * from stats");
            String hpName = hp.getName();
            Double hpScore;
            Date newDate = new Date();
            if (!resultSet.isBeforeFirst()){
                statement.execute("insert into stats(nickname, score, datetime) values(hpName,hpScore,newDate)");
            }
            else{
                while(resultSet.next()){
                    if(resultSet.getDouble("score") < hpScore){
                        statement.executeUpdate("update stats set nickname=hpName,score = hpScore, datetime=newDate");
                        // сместить игроков нижних вниз
                    }
                }
            }
        }
        catch (SQLException e){
                e.printStackTrace();
        }
    }
}
