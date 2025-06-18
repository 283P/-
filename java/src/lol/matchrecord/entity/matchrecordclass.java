package lol.matchrecord.entity;

public class matchrecordclass {
    String match_ID;
    String match_result;
    String match_goldearnd;
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
    public String getMatch_goldearnd() {
        return match_goldearnd;
    }
    public void setMatch_goldearnd(String match_goldearnd) {
        this.match_goldearnd = match_goldearnd;
    }
    public matchrecordclass(String match_ID, String match_result, String match_goldearnd, String matchCost) {
        this.match_ID = match_ID;
        this.match_result = match_result;
        this.match_goldearnd = match_goldearnd;
    }
    @Override
    public String toString() {
        return "matchrecordclass [match_ID=" + match_ID + ", match_result=" + match_result + ", match_goldearnd="
                + match_goldearnd + ", getMatch_ID()=" + getMatch_ID() + ", getMatch_result()=" + getMatch_result()
                + ", getMatch_goldearnd()=" + getMatch_goldearnd() + "]";
    }
    

}
