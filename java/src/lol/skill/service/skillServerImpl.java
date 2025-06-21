package lol.skill.service;

import lol.skill.dao.skilldaoImpl;
import lol.skill.entity.skillclass;

public class skillServerImpl implements skillServer {
    private skilldaoImpl dao = new skilldaoImpl();

    @Override
    public String insert(skillclass skill) {
        dao.insert(skill);
        return "技能添加成功";
    }

    @Override
    public String update(skillclass skill) {
        dao.update(skill);
        return "技能更新成功";
    }

    @Override
    public String delete(String skill_ID) {
        dao.delete(skill_ID);
        return "技能删除成功";
    }

    @Override
    public String select(String skill_ID) {
        return dao.select(skill_ID).toString();
    }
}
