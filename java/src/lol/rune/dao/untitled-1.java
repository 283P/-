package lol.rune.dao;

import lol.rune.entity.runeclass;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RunedaoImplTest {
    private runedao dao;
    private runeclass testRune;

    @BeforeAll
    void setup() {
        dao = new runedaoImpl(); // 需要实现具体类
        testRune = new runeclass();
        testRune.setRune_ID("test001");
        testRune.setRune_name("测试符文");
        testRune.setRune_type("攻击");
        testRune.setRune_effect("+10攻击力");
        testRune.setRune_tier("1");
    }

    @Test
    @Order(1)
    void testInsert() {
        int result = dao.insert(testRune);
        assertEquals(1, result);
    }

    @Test
    @Order(2)
    void testSelectById() {
        runeclass rune = dao.select("test001");
        assertNotNull(rune);
        assertEquals("测试符文", rune.getRune_name());
    }

    @Test
    @Order(3)
    void testUpdate() {
        testRune.setRune_effect("+15攻击力");
        int result = dao.update(testRune);
        assertEquals(1, result);
        
        runeclass updated = dao.select("test001");
        assertEquals("+15攻击力", updated.getRune_effect());
    }

    @Test
    @Order(4)
    void testSelectAll() {
        List<runeclass> runes = dao.select();
        assertNotNull(runes);
        assertFalse(runes.isEmpty());
    }

    @Test
    @Order(5)
    void testDelete() throws SQLException {
        // 确保测试数据存在，不存在则插入
        if(dao.select("test001") == null) {
            dao.insert(testRune);
        }

        // 验证初始数据存在
        assertNotNull(dao.select("test001"), "删除前测试数据必须存在");

        // 执行删除
        int result = dao.delete("test001");
        assertEquals(1, result, "应成功删除1条记录");

        // 验证删除结果
        assertNull(dao.select("test001"), "删除后查询应返回null");

        // 验证重复删除
        assertEquals(0, dao.delete("test001"), "重复删除应返回0");
    }

    @AfterAll
    void cleanup() throws SQLException {
        // 清理测试数据
        dao.delete("test001");
    }
}
