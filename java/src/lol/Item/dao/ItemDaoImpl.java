package lol.Item.dao;

import lol.DButil.DButil;
import lol.Item.entity.ItemClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 假设修正后的接口名为 ItemDao
public class ItemDaoImpl implements ItemDao {
    // 移除未使用的字段
    // private ItemClass item;

    @Override
    /*插入数据*/
    public void insert(ItemClass itemClass) throws SQLException {
        String sql = "insert into item values(?,?,?,?)";
        // 使用 try-with-resources 自动关闭资源
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemClass.getItem_ID());
            pstmt.setString(2, itemClass.getItem_name());
            pstmt.setString(3, itemClass.getItem_type());
            pstmt.setString(4, itemClass.getItem_cost());
            pstmt.executeUpdate();
        }
    }

    @Override
    /*更新数据*/
    public void update(ItemClass itemClass) throws SQLException {
        String sql = "update item set item_name=?,item_type=?,item_cost=? where item_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemClass.getItem_name());
            pstmt.setString(2, itemClass.getItem_type());
            pstmt.setString(3, itemClass.getItem_cost());
            pstmt.setString(4, itemClass.getItem_ID());
            pstmt.executeUpdate();
        }
    }

    @Override
    /*删除数据*/
    public void delete(String itemId) throws SQLException {
        String sql = "delete from item where item_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public ItemClass select(String itemId) throws SQLException {
        String sql = "select * from item where item_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemId);  // 先设置参数
            try (ResultSet rs = pstmt.executeQuery()) {  // 再执行查询
                if (rs.next()) {
                    return new ItemClass(
                            rs.getString("item_ID"),
                            rs.getString("item_name"),
                            rs.getString("item_type"),
                            rs.getString("item_cost"));
                }
            }
        }
        return null;
    }

    @Override
    /*查询所有数据*/
    public List<ItemClass> selectAll() throws SQLException {
        String sql = "select * from item";
        List<ItemClass> itemClassList = new ArrayList<>();
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                itemClassList.add(new ItemClass(
                        rs.getString("item_ID"),
                        rs.getString("item_name"),
                        rs.getString("item_type"),
                        rs.getString("item_cost")));
            }
        }
        return itemClassList;
    }
}


