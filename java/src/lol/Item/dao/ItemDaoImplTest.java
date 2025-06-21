package lol.Item.dao;

import lol.DButil.DButil;
import lol.Item.entity.ItemClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDaoImplTest {
    private ItemDaoImpl dao;
    private Connection testConn;
    private ItemClass testItem;
    private String testItem_ID = "008";

    @BeforeEach
    public void setUp() throws SQLException {
        dao = new ItemDaoImpl();
        testConn = DButil.getConnection();

        // 清空测试数据
        testConn.prepareStatement("DELETE FROM Item").executeUpdate();

        // 创建测试物品
        testItem = new ItemClass("008", "无尽之刃", "武器", "3400");
        dao.insert(testItem);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (testConn != null) {
            testConn.close();
        }
    }

    @Test
    public void testInsert() throws SQLException {
        // 确保所有字段都有值
        ItemClass newItem = new ItemClass("002", "饮血剑", "武器", "3500");
        dao.insert(newItem);

        ItemClass retrieved = dao.select("002");
        assertNotNull(retrieved, "插入后应能查询到数据");
    }

    @Test
    public void testUpdate() throws SQLException {
        testItem.setItem_cost("3600");
        dao.update(testItem);

        ItemClass updated = dao.select("008");
        assertEquals("3600", updated.getItem_cost(), "价格应更新为3600");
    }

    @Test
    public void testDelete() throws SQLException {
        dao.delete(testItem_ID);  // 直接传入 itemId
        assertNull(dao.select(testItem_ID), "删除后查询应返回null");
    }

    @Test
    public void testSelect() throws SQLException {
        ItemClass result = dao.select("008");
        assertNotNull(result, "应能查询到测试数据");
        assertEquals("无尽之刃", result.getItem_name(), "物品名称应匹配");
    }

    @Test
    public void testSelectAll() throws SQLException {
        dao.insert(new ItemClass("002", "灭世者的死亡之帽", "法术", "3600"));

        List<ItemClass> Items = dao.selectAll();
        assertNotNull(Items, "查询结果不应为null");
        assertEquals(2, Items.size(), "应包含2条记录");
    }

    @Test
    public void testSelectNonExistent() throws SQLException {
        assertNull(dao.select("999"), "查询不存在的ID应返回null");
    }

    @Test
    public void testInsertDuplicate() throws SQLException {
        // 确保测试数据存在
        assertNotNull(dao.select("008"), "测试数据应存在");

        // 创建重复ID对象
        ItemClass duplicate = new ItemClass("008", "重复物品", "武器", "1000");

        // 验证插入会抛出异常
        SQLException exception = assertThrows(SQLException.class, () -> {
            dao.insert(duplicate);
        });

        // 可选：验证异常信息
        assertTrue(exception.getMessage().contains("Duplicate entry"),
                  "异常信息应包含主键重复提示");
    }
}
