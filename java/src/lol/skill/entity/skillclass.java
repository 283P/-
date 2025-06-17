package lol.skill.entity;

public class skillclass {
    private String skill_ID;
    private String champion_ID;
    private String skill_name;
    private String skill_type;
    private String cooldown;
    private String skill_effect;
    public String getSkill_ID() {
        return skill_ID;
    }
    public void setSkill_ID(String skill_ID) {
        this.skill_ID = skill_ID;
    }
    public String getChampion_ID() {
        return champion_ID;
    }
    public void setChampion_ID(String champion_ID) {
        this.champion_ID = champion_ID;
    }
    public String getSkill_name() {
        return skill_name;
    }
    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }
    public String getSkill_type() {
        return skill_type;
    }
    public void setSkill_type(String skill_type) {
        this.skill_type = skill_type;
    }
    public String getCooldown() {
        return cooldown;
    }
    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
    }
    public String getSkill_effect() {
        return skill_effect;
    }
    public void setSkill_effect(String skill_effect) {
        this.skill_effect = skill_effect;
    }
    public skillclass(String skill_ID, String champion_ID, String skill_name, String skill_type, String cooldown,
            String skill_effect) {
        this.skill_ID = skill_ID;
        this.champion_ID = champion_ID;
        this.skill_name = skill_name;
        this.skill_type = skill_type;
        this.cooldown = cooldown;
        this.skill_effect = skill_effect;
    }
    @Override
    public String toString() {
        return "skillclass [skill_ID=" + skill_ID + ", champion_ID=" + champion_ID + ", skill_name=" + skill_name
                + ", skill_type=" + skill_type + ", cooldown=" + cooldown + ", skill_effect=" + skill_effect
                + ", getSkill_ID()=" + getSkill_ID() + ", getChampion_ID()=" + getChampion_ID() + ", getSkill_name()="
                + getSkill_name() + ", getSkill_type()=" + getSkill_type() + ", getCooldown()=" + getCooldown()
                + ", getSkill_effect()=" + getSkill_effect() + "]";
    }
    




}
