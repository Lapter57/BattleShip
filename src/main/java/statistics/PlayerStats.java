package statistics;

import javafx.scene.control.TableView;
import logics.Game;
import logics.players.HumanPlayer;

import java.sql.*;
import java.util.Objects;

public class PlayerStats {

    private static final String INIT_SCRIPT_PATH = Objects.requireNonNull(PlayerStats.class.getClassLoader().getResource("sql/init.sql")).toString();
    private static final String URL = String.format("jdbc:h2:~/battleship;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE;INIT=RUNSCRIPT FROM '%s'", INIT_SCRIPT_PATH);
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private Connection connection;
    private Statement statement;
    private TableStats table = new TableStats();

    public PlayerStats(){
        try {
            Class.forName("org.h2.Driver");
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
            double hpScore = (double)(10 - hp.getNumShipAfloat() + hp.getNumWinShots())/2;
            Game.Level hpLevel = hp.getLevel();
            int hlevel = 1;
            String strLevel = null;
            switch (hpLevel){
                case EASY:
                    strLevel = "Easy";
                    hlevel = 0;
                    break;
                case NORMAL:
                    strLevel = "Normal";
                    hlevel = 1;
                    break;
                case HARD:
                    strLevel = "Hard";
                    hlevel = 2;
                    break;
                case HUMAN:
                    strLevel = "Human";
                    hlevel = 3;
                    break;
            }
            int cntRow = 0;
            ResultSet resultSet = statement.executeQuery("select count(*) from stats");
            while(resultSet.next()){
                cntRow = resultSet.getInt(1);
            }

            if(cntRow < 11){

                String str = "insert into stats(nickname, score, level, game_date) values(?,?,?,?)";
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
                    int level = getNumLevel(resultSet.getString("level"));
                    if(resultSet.getDouble("score") > hpScore && level <= hlevel || resultSet.getDouble("score") >= hpScore && level < hlevel){
                        String str = "update stats set nickname = ?, score = ?, level = ?, game_date = ? where id = ?";
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

    static int getNumLevel(String level){
        switch (level){
            case "Easy":
                return 0;
            case "Normal":
                return 1;
            case "Hard":
                return 2;
        }
        return 3;
    }
}
