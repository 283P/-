package lol.item.dao;

import lol.DButil.DButil;
import lol.item.entity.itemclass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class itemDaoImplTest {
    private itemDaoImpl dao;
    private Connection testConn;
    private itemclass testItem;

    @BeforeEach
    public void setUp() throws SQLException {
        dao = new itemDaoImpl();
        testConn = DButil.getConnction();

        // 清空测试数据
        testConn.prepareStatement("DELETE FROM item").executeUpdate();

        // 创建测试物品
        testItem = new itemclass("008", "无尽之刃", "武器", "3400");
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
        itemclass newItem = new itemclass("002", "饮血剑", "武器", "3500");
        dao.insert(newItem);

        itemclass retrieved = dao.select("002");
        assertNotNull(retrieved, "插入后应能查询到数据");
        assertEquals("饮血剑", retrieved.getItem_name(), "物品名称应匹配");
    }

    @Test
    public void testUpdate() {
        testItem.setItem_cost("3600");
        dao.update(testItem);

        itemclass updated = dao.select("008");
        assertEquals("3600", updated.getItem_cost(), "价格应更新为3600");
    }

    @Test
    public void testDelete() {
        dao.delete(testItem);
        assertNull(dao.select("008"), "删除后查询应返回null");
    }

    @Test
    public void testSelect() {
        itemclass result = dao.select("008");
        assertNotNull(result, "应能查询到测试数据");
        assertEquals("无尽之刃", result.getItem_name(), "物品名称应匹配");
    }

    @Test
    public void testSelectAll() throws SQLException {
        dao.insert(new itemclass("002", "灭世者的死亡之帽", "法术", "3600"));

        List<itemclass> items = dao.selectAll();
        assertNotNull(items, "查询结果不应为null");
        assertEquals(2, items.size(), "应包含2条记录");
    }

    @Test
    public void testSelectNonExistent() {
        assertNull(dao.select("999"), "查询不存在的ID应返回null");
    }

    @Test
    public void testInsertDuplicate() {
        // 确保测试数据存在
        assertNotNull(dao.select("008"), "测试数据应存在");

        // 创建重复ID对象
        itemclass duplicate = new itemclass("008", "重复物品", "武器", "1000");

        // 验证插入会抛出异常
        SQLException exception = assertThrows(SQLException.class, () -> {
            dao.insert(duplicate);
        });

        // 可选：验证异常信息
        assertTrue(exception.getMessage().contains("Duplicate entry"),
                  "异常信息应包含主键重复提示");
    }
}
