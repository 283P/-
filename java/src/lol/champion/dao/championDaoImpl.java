package lol.champion.dao;

import lol.DButil.DButil;
import lol.champion.entity.championclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}

