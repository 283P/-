package lol.matchrecord.dao;

import lol.matchrecord.entity.matchrecordclass;

import java.util.List;

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

    List<matchrecordclass> select(String username);

    void delete(String match_ID);
}
