package lol.champion.dao;

import lol.DButil.DButil;
import lol.champion.entity.championclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class championDaoImpl implements championinter {

    @Override
    public void insert(championclass championclass) {
        // 先检查 champion_ID 是否存在
        if (isChampionIdExists(championclass.getChampion_ID())) {
            throw new RuntimeException("英雄 ID 重复，插入失败");
        }

        String sql="insert into champion values(?,?,?,?,?)";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, championclass.getChampion_ID());
            pstmt.setString(2, championclass.getChampion_name());
            pstmt.setString(3, championclass.getRole());
            pstmt.setString(4, championclass.getAttacktype());
            pstmt.setString(5, championclass.getSkill_intro());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("插入英雄数据失败", e);
        }
    }

    private boolean isChampionIdExists(String championId) {
        String sql = "select 1 from champion where champion_ID = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, championId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("检查英雄 ID 是否存在失败", e);
        }
    }

    @Override
    public void update(championclass championclass) {
        String sql="update champion set champion_name=?,role=?,attacktype=?,skill_intro=? where champion_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, championclass.getChampion_name());
            pstmt.setString(2, championclass.getRole());
            pstmt.setString(3, championclass.getAttacktype());
            pstmt.setString(4, championclass.getSkill_intro());
            pstmt.setString(5, championclass.getChampion_ID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("更新英雄数据失败", e);
        }
    }

    @Override
    public String delete(championclass championclass) {
        String sql="delete from champion where champion_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, championclass.getChampion_ID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("删除英雄数据失败", e);
        }
        return sql;
    }

    @Override
    public championclass select(String champion_ID) {
        String sql="select * from champion where champion_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, champion_ID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()){
                    championclass championclass=new championclass();
                    championclass.setChampion_ID(rs.getString("champion_ID"));
                    championclass.setChampion_name(rs.getString("champion_name"));
                    championclass.setRole(rs.getString("role"));
                    championclass.setAttacktype(rs.getString("attacktype"));
                    championclass.setSkill_intro(rs.getString("skill_intro"));
                    return championclass;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询英雄数据失败", e);
        }
        return null;
    }

    @Override
    public List<championclass> selectAll() {
        String sql="select * from champion";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            List<championclass> championclassList=new ArrayList<>();
            while(rs.next()){
                championclass championclass=new championclass();
                championclass.setChampion_ID(rs.getString("champion_ID"));
                championclass.setChampion_name(rs.getString("champion_name"));
                championclass.setRole(rs.getString("role"));
                championclass.setAttacktype(rs.getString("attacktype"));
                championclass.setSkill_intro(rs.getString("skill_intro"));
                championclassList.add(championclass);
            }
            return championclassList;
        } catch (SQLException e) {
            throw new RuntimeException("查询所有英雄数据失败", e);
        }
    }
    @Override
    public List<Map<String, Object>> selectChampionStats(String playerId) {
        String sql = "SELECT c.champion_name, " +
                "COUNT(*) as games_played, " +
                "SUM(CASE WHEN mr.match_result = '胜利' THEN 1 ELSE 0 END) as wins " + // 确保匹配中文"胜利"
                "FROM matchrecord mr " +
                "JOIN player_match pm ON mr.match_ID = pm.match_ID " +
                "JOIN champion c ON pm.champion_ID = c.champion_ID " +
                "WHERE pm.player_ID = ? " +
                "GROUP BY c.champion_name";

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> stat = new HashMap<>();
                stat.put("champion_name", rs.getString("champion_name"));
                stat.put("games_played", rs.getInt("games_played"));

                int wins = rs.getInt("wins");
                int games = rs.getInt("games_played");
                // 添加调试输出
                System.out.println("DEBUG - 英雄:" + stat.get("champion_name")
                    + " 场次:" + games + " 胜场:" + wins);

                // 计算胜率并保留4位小数
                stat.put("win_rate", games > 0 ?
                    Double.parseDouble(String.format("%.4f", (double)wins/games)) : 0.0);

                result.add(stat);
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取英雄统计数据失败", e);
        }

        return result;
    }
}

