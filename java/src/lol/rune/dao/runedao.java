package lol.rune.dao;

import lol.rune.entity.rune;

public interface runedao {
    /**
     * 插入
     * @param rune
     * @return
     */
    public int insert(rune rune);
    /**
     * 更新
     * @param rune
     * @return
     */
    public int update(rune rune);
    /**
     * 删除
     * @param rune_ID
     * @return
     */
    public int delete(String rune_ID);
    /**
     * 查询
     * @param rune_ID
     * @return
     */
    public rune select(String rune_ID);
    /**
     * 查询所有
     * @return
     */
    public List<rune> select();
    /**
     * 删除所有
     * @return
     */
    public int deleteAll();
}
