package lol.matchrecord.dao;

import lol.DButil.DButil;
import lol.matchrecord.entity.matchrecordclass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class matchrecorddaoImplTest {
    private matchrecorddaoImpl dao;
    private Connection testConn;
    private matchrecordclass testRecord;

    @BeforeEach
    public void setUp() throws SQLException {
        dao = new matchrecorddaoImpl();
        testConn = DButil.getConnection();

        // 清空测试数据
        testConn.prepareStatement("DELETE FROM matchrecord").executeUpdate();

        // 创建测试记录(根据实际数据库字段调整)
        testRecord = new matchrecordclass("001", "胜利", "10000"); // 三个参数均为String类型
        dao.insert(testRecord);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (testConn != null) {
            testConn.close();
        }
    }

    @Test
    public void testInsert() {
        matchrecordclass newRecord = new matchrecordclass("002", "失败", "8000"); // 全部使用String参数
        dao.insert(newRecord);

        matchrecordclass retrieved = dao.select("002");
        assertNotNull(retrieved, "插入后应能查询到数据");
        assertEquals("失败", retrieved.getMatch_result(), "比赛结果应匹配"); // 断言String类型值
    }

    @Test
    public void testUpdate() {
        testRecord.setMatch_goldearned("12000"); // 设置String类型值
        dao.update(testRecord);

        matchrecordclass updated = dao.select("001");
        assertEquals("12000", updated.getMatch_goldearned(), "金币应更新为12000"); // 比较String值
    }

    @Test
    public void testDeleteByObject() {
        dao.delete(testRecord);
        assertNull(dao.select("001"), "删除后查询应返回null");
    }

    @Test
    public void testDeleteById() {
        dao.delete("001");
        assertNull(dao.select("001"), "删除后查询应返回null");
    }

    @Test
    public void testSelectById() {
        matchrecordclass result = dao.select("001");
        assertNotNull(result, "应能查询到测试数据");
    }

    @Test
    public void testSelectAll() {
        // 验证初始记录
        List<matchrecordclass> initialRecords = dao.select();
        assertNotNull(initialRecords, "初始查询不应为null");
        assertEquals(1, initialRecords.size(), "初始应包含1条记录");

        // 插入第二条记录
        matchrecordclass newRecord = new matchrecordclass("002", "胜利", "9000");
        dao.insert(newRecord);

        // 查询所有记录
        List<matchrecordclass> records = dao.select();
        assertNotNull(records, "查询结果不应为null");
        assertEquals(2, records.size(), "应包含2条记录");
    }

    @Test
    public void testSelectNonExistent() {
        assertNull(dao.select("999"), "查询不存在的ID应返回null");
    }
}
