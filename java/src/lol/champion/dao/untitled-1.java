package lol.champion.dao;

import lol.DButil.DButil;
import lol.champion.entity.championclass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class championDaoImplTest {
    private championDaoImpl dao;
    private championclass testChampion;
    private Connection testConn;

    @BeforeEach
    void setUp() throws SQLException {
        dao = new championDaoImpl();
        testConn = DButil.getConnection();

        // 先删除引用表的记录
        testConn.prepareStatement("DELETE FROM player WHERE champion_ID IS NOT NULL").executeUpdate();
        // 再清空主表
        testConn.prepareStatement("DELETE FROM champion").executeUpdate();
        
        // 创建测试数据
        testChampion = new championclass();
        testChampion.setChampion_ID("001");
        testChampion.setChampion_name("Ashe");
        testChampion.setRole("Marksman");
        testChampion.setAttacktype("Ranged");
        testChampion.setSkill_intro("Frost Arrow");
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (testConn != null) {
            testConn.close();
        }
    }

    @Test
    void testInsert() {
        // 测试插入
        dao.insert(testChampion);
        
        // 验证插入结果
        championclass retrieved = dao.select("001");
        assertNotNull(retrieved);
        assertEquals("Ashe", retrieved.getChampion_name());
    }

    @Test
    void testInsertDuplicateId() {
        // 第一次插入应该成功
        dao.insert(testChampion);
        
        // 尝试插入相同ID的记录
        championclass duplicate = new championclass();
        duplicate.setChampion_ID("001");
        duplicate.setChampion_name("Duplicate");
        
        // 应该不会抛出异常，但后续查询应该还是原始数据
        dao.insert(duplicate);
        championclass retrieved = dao.select("001");
        assertEquals("Ashe", retrieved.getChampion_name());
    }

    @Test
    void testUpdate() {
        // 先插入数据
        dao.insert(testChampion);
        
        // 修改数据
        testChampion.setChampion_name("Ashe Updated");
        dao.update(testChampion);
        
        // 验证更新
        championclass updated = dao.select("001");
        assertEquals("Ashe Updated", updated.getChampion_name());
    }

    @Test
    void testUpdateNonExisting() {
        // 尝试更新不存在的记录
        championclass nonExist = new championclass();
        nonExist.setChampion_ID("999");
        
        // 应该不会抛出异常
        dao.update(nonExist);
        assertNull(dao.select("999"));
    }

    @Test
    void testDelete() {
        // 先插入数据
        dao.insert(testChampion);
        
        // 删除数据
        dao.delete(testChampion);
        
        // 验证删除
        assertNull(dao.select("001"));
    }

    @Test
    void testDeleteNonExisting() {
        // 尝试删除不存在的记录
        championclass nonExist = new championclass();
        nonExist.setChampion_ID("999");
        
        // 应该不会抛出异常
        dao.delete(nonExist);
    }

    @Test
    void testSelect() {
        // 先插入数据
        dao.insert(testChampion);
        
        // 查询数据
        championclass result = dao.select("001");
        
        // 验证查询结果
        assertNotNull(result);
        assertEquals("Ashe", result.getChampion_name());
    }

    @Test
    void testSelectNonExisting() {
        // 查询不存在的数据
        assertNull(dao.select("999"));
    }

    @Test
    void testSelectAll() {
        // 插入多条测试数据
        dao.insert(testChampion);
        
        championclass testChampion2 = new championclass();
        testChampion2.setChampion_ID("002");
        testChampion2.setChampion_name("Annie");
        dao.insert(testChampion2);
        
        // 查询所有
        List<championclass> results = dao.selectAll();
        
        // 验证结果
        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(c -> "Ashe".equals(c.getChampion_name())));
        assertTrue(results.stream().anyMatch(c -> "Annie".equals(c.getChampion_name())));
    }

    @Test
    void testSelectAllEmpty() {
        // 空表查询
        List<championclass> results = dao.selectAll();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
