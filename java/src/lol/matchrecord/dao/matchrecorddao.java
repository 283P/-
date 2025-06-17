package lol.matchrecord.dao;

import java.util.List;

import lol.matchrecord.entity.matchrecordclass;

public interface matchrecorddao {
    /**
     * 插入数据
     * @param matchrecordclass
     */
    public void insert(matchrecordclass matchrecordclass);
    /**
     * 更新数据
     * @param matchrecordclass
     */
    public void update(matchrecordclass matchrecordclass);
    /**
     * 删除数据
     * @param matchrecordclass
     */
    public void delete(matchrecordclass matchrecordclass);
    /**
     * 查询所有数据
     * @return
     */
    public List<matchrecordclass> select();
    /**
     * 根据ID查询数据
     * @param match_ID
     * @return
     */
    public matchrecordclass select(String match_ID);
}
