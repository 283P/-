package lol.rune.entity;

public class runeclass {
    private String rune_ID;
    private String rune_type;
    private String rune_tier;
    private String rune_effect;
    private String rune_name;

    public runeclass() {

    }

    public String getRune_ID() {
        return rune_ID;
    }
    public void setRune_ID(String rune_ID) {
        this.rune_ID = rune_ID;
    }
    public String getRune_type() {
        return rune_type;
    }
    public void setRune_type(String rune_type) {
        this.rune_type = rune_type;
    }
    public String getRune_tier() {
        return rune_tier;
    }
    public void setRune_tier(String rune_tier) {
        this.rune_tier = rune_tier;
    }
    public String  getRune_effect() {
        return rune_effect;
    }
    public void setRune_effect(String rune_effect) {
        this.rune_effect = rune_effect;
    }
    public String getRune_name() {
        return rune_name;
    }
    public void setRune_name(String rune_name) {
        this.rune_name = rune_name;
    }
    public runeclass(String rune_ID, String rune_type, String rune_tier, String rune_effect, String rune_name) {
        this.rune_ID = rune_ID;
        this.rune_type = rune_type;
        this.rune_tier = rune_tier;
        this.rune_effect = rune_effect;
        this.rune_name = rune_name;
    }
    @Override
    public String toString() {
        return "runeclass [rune_ID=" + rune_ID + ", rune_type=" + rune_type + ", rune_tier=" + rune_tier
                + ", rune_effect=" + rune_effect + ", rune_name=" + rune_name + ", getRune_ID()=" + getRune_ID()
                + ", getRune_type()=" + getRune_type() + ", getRune_tier()=" + getRune_tier() + ", getRune_effect()="
                + getRune_effect() + ", getRune_name()=" + getRune_name() + "]";
    }
    

}
