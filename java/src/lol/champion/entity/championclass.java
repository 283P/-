package lol.champion.entity;

public class championclass {
    private String champion_ID;
    private String champion_name;
    private String role;
    private String attacktype;
    private String skill_intro;
 
    public String getChampion_ID() {
        return champion_ID;
    }

    public void setChampion_ID(String champion_ID) {
        this.champion_ID = champion_ID;
    }

    public String getChampion_name() {
        return champion_name;
    }

    public void setChampion_name(String champion_name) {
        this.champion_name = champion_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public championclass() {
    }

    public championclass(String champion_ID, String champion_name, String role, String attacktype, String skill_intro) {
        this.champion_ID = champion_ID;
        this.champion_name = champion_name;
        this.role = role;
        this.attacktype = attacktype;
        this.skill_intro = skill_intro;
    }

    public String getAttacktype() {
        return attacktype;
    }

    public void setAttacktype(String attacktype) {
        this.attacktype = attacktype;
    }

    public String getSkill_intro() {
        return skill_intro;
    }

    public void setSkill_intro(String skill_intro) {
        this.skill_intro = skill_intro;
    }

    @Override
    public String toString() {
        return "championclass [champion_ID=" + champion_ID + ", champion_name=" + champion_name + "]";
    }
}
