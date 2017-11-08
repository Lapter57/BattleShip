package statistics;

import Logics.HumanPlayer;

import java.sql.*;
import java.util.Date;

public class PlayerStats {

    private static final String URL = "jdbc:mysql://localhost:3306/player_stats?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Connection connection;
    private Statement statement;

    public PlayerStats(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            statement = connection.createStatement();
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void updateStats(HumanPlayer hp){
        try {
            String hpName = hp.getName();
            double hpScore = (double)(hp.getNumShipAfloat() + hp.getNumWinShots())/2;
            Date newDate = new Date();

            int cntRow = 0;
            ResultSet resultSet = statement.executeQuery("select count(*) from stats");
            while(resultSet.next()){
                cntRow = resultSet.getInt(1);
            }

            if(cntRow < 11){
                statement.execute("insert into stats(nickname, score, datetime) values(hpName,hpScore,newDate)");
            }
            else{
                resultSet = statement.executeQuery("select * from stats order by score desc");
                boolean addPlayer = false;
                while(resultSet.next() && !addPlayer){
                    if(resultSet.getDouble("score") < hpScore){
                        statement.executeUpdate("update stats set nickname=hpName,score = hpScore, datetime=newDate");
                        addPlayer = true;
                    }
                }
            }
            resultSet = statement.executeQuery("select * from stats order by score desc");

        }
        catch (SQLException e){
                e.printStackTrace();
        }
    }
}
