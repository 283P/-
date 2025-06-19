package lol.rune.dao;

import lol.rune.entity.runeclass;
import org.junit.jupiter.api.*;

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
    void testDelete() {
        int result = dao.delete("test001");
        assertEquals(1, result);
        
        assertNull(dao.select("test001"));
    }

    @AfterAll
    void cleanup() {
        // 清理测试数据
        dao.delete("test001");
    }
}
