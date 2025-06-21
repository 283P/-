package lol.player.dao;

import lol.DButil.DButil;
import lol.player.entity.playerclass;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerDaoTest {
    private playerdao dao;
    private playerclass testPlayer;

    @BeforeAll
    void setup() throws SQLException {
        dao = new playerdaoImpl();

        // 使用原生JDBC插入champion测试数据
        Connection conn = DButil.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO champion(champion_ID, champion_name) VALUES(?, ?)");
            pstmt.setString(1, "testChamp001");
            pstmt.setString(2, "Test Champion");
            pstmt.executeUpdate();
        } finally {
            DButil.close(conn);
        }

        // 准备player测试数据
        testPlayer = new playerclass();
        testPlayer.setPlayer_ID("test001");
        testPlayer.setChampion_ID("testChamp001");
        testPlayer.setPlayer_rank("Gold");
        testPlayer.setPlayer_name("TestPlayer");
    }

    @AfterAll
    void cleanup() throws SQLException {
        // 清理player测试数据
        dao.delete("test001");

        // 清理champion测试数据
        Connection conn = DButil.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM champion WHERE champion_ID = ?");
            pstmt.setString(1, "testChamp001");
            pstmt.executeUpdate();
        } finally {
            DButil.close(conn);
        }
    }

    @Test
    @Order(1)
    void testInsert() {
        var player = new playerclass();
        player.setPlayer_ID("test001");
        player.setChampion_ID("1");
        player.setPlayer_rank("Gold");
        player.setPlayer_name("TestPlayer");
        int result = dao.insert(testPlayer);
        assertEquals(1, result);
    }

    @Test
    @Order(2)
    void testSelect() {
        playerclass player = dao.select("test001");
        assertNotNull(player);
        assertEquals("TestPlayer", player.getPlayer_name());
    }

    @Test
    @Order(3)
    void testUpdate() {
        testPlayer.setPlayer_rank("Platinum");
        int result = dao.update(testPlayer);
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    void testDelete() {
        // 先确保数据存在
        playerclass player = new playerclass();
        player.setPlayer_ID("test001");
        player.setChampion_ID("testChamp001");
        player.setPlayer_rank("Gold");

        // 验证数据确实被删除
        assertNull(dao.select("test001"));
        player.setPlayer_name("TestPlayer");
        dao.insert(player);

        // 再执行删除
        int result = dao.delete("test001");
        assertEquals(1, result);
    }


}