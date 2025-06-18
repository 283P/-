package lol.champion.dao;

import lol.champion.entity.championclass;

import java.util.List;

public interface championinter {
    /** 插入 */
    public void insert(championclass championclass);
    /** 更新 */
    public void update(championclass championclass);
    /** 删除 */
    public void delete(championclass championclass);
    /** 查询 */
    public championclass select(String champion_ID);
    /** 查询所有 */
    public List<championclass> selectAll();
}
