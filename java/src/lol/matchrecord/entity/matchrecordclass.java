package lol.matchrecord.entity;

public class matchrecordclass {
    String match_ID;
    String match_result;
    String match_goldearned;
    public String getMatch_ID() {
        return match_ID;
    }
    public void setMatch_ID(String match_ID) {
        this.match_ID = match_ID;
    }
    public String getMatch_result() {
        return match_result;
    }
    public void setMatch_result(String match_result) {
        this.match_result = match_result;
    }
    public String getMatch_goldearned() {
        return match_goldearned;
    }
    public void setMatch_goldearned(String match_goldearned) {
        this.match_goldearned = match_goldearned;
    }
    public matchrecordclass(String match_ID, String match_result, String match_goldearned) {
        this.match_ID = match_ID;
        this.match_result = match_result;
        this.match_goldearned = match_goldearned;
    }
    @Override
    public String toString() {
        return "matchrecordclass [match_ID=" + match_ID + ", match_result=" + match_result + ", match_goldearnd="
                + match_goldearned + ", getMatch_ID()=" + getMatch_ID() + ", getMatch_result()=" + getMatch_result()
                + ", getMatch_goldearnd()=" + getMatch_goldearned() + "]";
    }
    

}
