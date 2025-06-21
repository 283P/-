package lol.rune.dao;

import lol.DButil.DButil;
import lol.rune.entity.runeclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class runedaoImpl implements runedao {
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Connection conn = DButil.getConnection();

    @Override
    public int insert(runeclass rune) {
        String sql="insert into rune(rune_ID, rune_type, rune_tier, rune_effect, rune_name) values(?,?,?,?,?)";
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rune.getRune_ID());
            ps.setString(2, rune.getRune_name());
            ps.setString(3, rune.getRune_type());
            ps.setString(4, rune.getRune_effect());
            ps.setString(5, rune.getRune_tier());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("插入失败", e);
        }
    }



    @Override
    /*更新数据*/
    public int update(runeclass rune) {
        String sql = "update rune set rune_name=?, rune_type=?, rune_effect=?, rune_tier=? where rune_ID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rune.getRune_name());
            ps.setString(2, rune.getRune_type());
            ps.setString(3, rune.getRune_effect());
            ps.setString(4, rune.getRune_tier());
            ps.setString(5, rune.getRune_ID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int delete(String rune_ID) throws SQLException {
        String sql = "delete from rune where rune_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rune_ID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? affectedRows : 0;
        }
    }
    @Override
    /*id查询数据*/
    public runeclass select(String rune_ID) {
        String sql = "select * from rune where rune_ID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rune_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                runeclass rune = new runeclass();
                rune.setRune_ID(rs.getString("rune_ID"));
                rune.setRune_name(rs.getString("rune_name"));
                rune.setRune_type(rs.getString("rune_type"));
                rune.setRune_effect(rs.getString("rune_effect"));
                rune.setRune_tier(rs.getString("rune_tier"));
                return rune;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    /*查询所有数据*/
    public List<runeclass> select() {
        String sql = "select * from rune";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<runeclass> runeList = new ArrayList<runeclass>();
            while (rs.next()) {
                runeclass rune = new runeclass();
                rune.setRune_ID(rs.getString("rune_ID"));
                rune.setRune_name(rs.getString("rune_name"));
                rune.setRune_type(rs.getString("rune_type"));
                rune.setRune_effect(rs.getString("rune_effect"));
                rune.setRune_tier(rs.getString("rune_tier"));
                runeList.add(rune);
            }
            return runeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
