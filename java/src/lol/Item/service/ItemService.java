package lol.Item.service;
import lol.Item.entity.ItemClass;

import java.sql.SQLException;

public interface ItemService {
    // ID查询物品信息的业务方法
    String getItemInfo(String item_ID) throws SQLException;
    // ID删除物品的业务方法
    String deleteItem(String item_ID) throws SQLException;
    // 添加物品的业务方法
    String addItem(ItemClass Item) throws SQLException;
    // 修改物品的业务方法
    String updateItem(ItemClass Item) throws SQLException;
}
