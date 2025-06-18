package lol.skill.dao;

import lol.skill.entity.skillclass;

import java.util.List;

public interface skilldao {
    /**
     * 插入
     * @param skillclass
     * @return
     */
    public int insert(skillclass skillclass);
    /**
     * 更新
     * @param skillclass
     * @return
     */
    public int update(skillclass skillclass);
    /**
     * 删除
     * @param skill_ID
     * @return
     */
    public int delete(String skill_ID);
    /**
     * 查询
     * @param skill_ID
     * @return
     */
    public skillclass select(String skill_ID);
    /**
     * 查询所有
     * @return
     */
    public List<skillclass> select();
    /**
     * 删除所有
     * @return
     */
    public int deleteAll();
}
