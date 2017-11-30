package statistics;

import java.util.Objects;

public class PlayerAccount implements Comparable<PlayerAccount> {

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

    @Override
    public int hashCode() {
        return Objects.hash(score, level);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if (!(obj instanceof PlayerAccount)) return false;
        PlayerAccount pa = (PlayerAccount) obj;
        return (Objects.equals(nickName, pa.nickName) && Objects.equals(score, pa.score) &&
                Objects.equals(level, pa.level) && Objects.equals(date, pa.date));
    }

    @Override
    public int compareTo(PlayerAccount pa) {
        int level1 = PlayerStats.getNumLevel(level);
        int level2 = PlayerStats.getNumLevel(pa.level);
        if(score > pa.score && level1 <=level2 || score >= pa.score && level1 < level2 )
            return 1;
        else if(Objects.equals(score, pa.score) && level1 == level2)
            return 0;
        else
            return -1;
    }

}
