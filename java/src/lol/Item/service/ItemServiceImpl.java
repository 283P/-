package lol.Item.service;

import lol.Item.dao.ItemDaoImpl;
import lol.Item.entity.ItemClass;

import java.sql.SQLException;

public class ItemServiceImpl implements ItemService {
    private  ItemDaoImpl ItemDao=new ItemDaoImpl();// 移除 abstract

    @Override
    public String getItemInfo(String item_ID) throws SQLException {
        ItemClass item = ItemDao.select(item_ID);
        return item != null ? item.toString() : "未找到该物品";
    }

    @Override
    public String deleteItem(String item_ID) throws SQLException {
        ItemDao.delete(item_ID);  // 直接传入 item_ID
        return "删除成功";
    }
    @Override
    public String addItem(ItemClass Item) throws SQLException {  // 添加异常声明
        ItemDao.insert(Item);
        return "已插入";
    }

    @Override
    public String updateItem(ItemClass Item) throws SQLException {  // 添加异常声明
        ItemDao.update(Item);
        return "更新成功";
    }
}