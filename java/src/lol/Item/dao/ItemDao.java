package lol.Item.dao;

import lol.Item.entity.ItemClass;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    /** 插入 */
    public void insert(ItemClass ItemClass) throws SQLException;
    /** 更新 */
    public void update(ItemClass ItemClass) throws SQLException;
    /** 删除 */
    public void delete(String item_ID) throws SQLException;
    /** 查询 */
    public ItemClass select(String item_ID) throws SQLException;
    /** 查询所有 */
    public List<ItemClass> selectAll() throws SQLException;
}
