package lol.player.entity;

public class playerclass {
    String player_ID;
    String champion_ID;
    String player_rank;
    String player_name;

    public playerclass() {

    }

    public String getPlayer_ID() {
        return player_ID;
    }
    public void setPlayer_ID(String player_ID) {
        this.player_ID = player_ID;
    }
    public String getChampion_ID() {
        return champion_ID;
    }
    public void setChampion_ID(String champion_ID) {
        this.champion_ID = champion_ID;
    }
    public String getPlayer_rank() {
        return player_rank;
    }
    public void setPlayer_rank(String player_rank) {
        this.player_rank = player_rank;
    }
    public String getPlayer_name() {
        return player_name;
    }
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }
    public playerclass(String player_ID, String champion_ID, String player_rank, String player_name) {
        this.player_ID = player_ID;
        this.champion_ID = champion_ID;
        this.player_rank = player_rank;
        this.player_name = player_name;
    }
    @Override
    public String toString() {
        return "player_class [player_ID=" + player_ID + ", champion_ID=" + champion_ID + ", player_rank=" + player_rank
                + ", player_name=" + player_name + ", getPlayer_ID()=" + getPlayer_ID() + ", getChampion_ID()="
                + getChampion_ID() + ", getPlayer_rank()=" + getPlayer_rank() + ", getPlayer_name()=" + getPlayer_name()
                + "]";
    }
    
    

}
