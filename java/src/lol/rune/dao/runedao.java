package lol.rune.dao;

import lol.rune.entity.runeclass;

import java.util.List;

public interface runedao {
    /**
     * 插入
     * @param rune
     * @return
     */
    public int insert(runeclass rune);
    /**
     * 更新
     * @param rune
     * @return
     */
    public int update(runeclass rune);
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
    public runeclass select(String rune_ID);
    /**
     * 查询所有
     * @return
     */
    public List<runeclass> select();

}
