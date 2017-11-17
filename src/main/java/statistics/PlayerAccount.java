package statistics;


public class PlayerAccount {

    private String nickName;
    private Double score;
    private String level;
    private String date;

    PlayerAccount(String nickName, Double score, String level, String date){
        this.nickName = nickName;
        this.score = score;
        this.level = level;
        this.date = date;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score){
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}
