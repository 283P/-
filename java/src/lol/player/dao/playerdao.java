package lol.player.dao;

import java.util.List;

import lol.player.entity.playerclass;

public interface playerdao {
    /**
     * 插入
     * @param player
     * @return
     */
    public int insert(playerclass player);
    /**
     * 更新
     * @param player
     * @return
     */
    public int update(playerclass player);
    /**
     * 删除
     * @param player_ID
     * @return
     */
    public int delete(String player_ID);
    /**
     * 查询
     * @param player_ID
     * @return
     */
    public playerclass select(String player_ID);
    /**
     * 查询所有
     * @return
     */
    public List<playerclass> select();
}
