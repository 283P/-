package lol.skill.service;

import lol.skill.entity.skillclass;

public interface skillServer {
    String insert(skillclass skillclass);
    String update(skillclass skillclass);
    String delete(String skill_ID);
    String select(String skill_ID);
}
