package lol.skill.dao;

import lol.skill.entity.skillclass;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SkillDaoImplTest {
    private skilldao dao;
    private skillclass testSkill;

    @BeforeAll
    void setup() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lol", "root", "114514");
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM skill");
            stmt.execute("DELETE FROM champion");
        }

        // 插入测试数据
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lol", "root", "114514");
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO champion(champion_ID, champion_name) VALUES(?, ?)")) {
            ps.setString(1, "champ001");
            ps.setString(2, "测试英雄");
            ps.executeUpdate();
        }


        // 初始化skill数据
        dao = new skilldaoImpl();
        testSkill = new skillclass();
        testSkill.setSkill_ID("test001");
        testSkill.setChampion_ID("champ001");
        testSkill.setSkill_name("测试技能");
        testSkill.setSkill_type("主动");
        testSkill.setCooldown("10");
        testSkill.setSkill_effect("造成100点伤害");
        dao.insert(testSkill);
    }

    @Test
    @Order(1)
    void testInsert() {
        // 先清理可能存在的测试数据
        dao.delete("test001");
        
        int result = dao.insert(testSkill);
        assertEquals(1, result);
    }

    @Test
    @Order(2)
    void testSelectById() {
        skillclass skill = dao.select("test001");
        assertNotNull(skill);
        assertEquals("测试技能", skill.getSkill_name());
    }

    @Test
    @Order(3)
    void testUpdate() {
        testSkill.setSkill_effect("造成150点伤害");
        int result = dao.update(testSkill);
        assertEquals(1, result);
        
        skillclass updated = dao.select("test001");
        assertEquals("造成150点伤害", updated.getSkill_effect());
    }

    @Test
    @Order(4)
    void testSelectAll() {
        List<skillclass> skills = dao.select();
        assertNotNull(skills);
        assertFalse(skills.isEmpty());
    }

    @Test
    @Order(5)
    void testDelete() {

            // 先确保数据存在
            dao.insert(testSkill);

            int result = dao.delete("test001");
            assertEquals(1, result);

            assertNull(dao.select("test001"));


    }

    @AfterAll
    void cleanup() {
        // 确保清理测试数据
        dao.delete("test001");
    }
}
