package lol.item.dao;

import lol.item.entity.itemclass;

import java.sql.SQLException;
import java.util.List;

public interface itemdao {
    /** 插入 */
    public void insert(itemclass itemclass) throws SQLException;
    /** 更新 */
    public void update(itemclass itemclass);
    /** 删除 */
    public void delete(itemclass itemclass);
    /** 查询 */
    public itemclass select(String item_ID);
    /** 查询所有 */
    public List<itemclass> selectAll() throws SQLException;
}
