package lol.rune.dao;

import java.sql.Connection;
import java.util.List;

import lol.rune.entity.rune;

public class runedaoImpl implements runedao {
    private Connection conn;
    @Override
    public int insert(rune rune) {
        String sql = "insert into rune(rune_ID, rune_name, rune_type, rune_price, rune_description) values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rune.getRune_ID());
            ps.setString(2, rune.getRune_name());
            ps.setString(3, rune.getRune_type());
            ps.setInt(4, rune.getRune_price());
            ps.setString(5, rune.getRune_description());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int update(rune rune) {
        String sql = "update rune set rune_name=?, rune_type=?, rune_price=?, rune_description=? where rune_ID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rune.getRune_name());
            ps.setString(2, rune.getRune_type());
            ps.setInt(3, rune.getRune_price());
            ps.setString(4, rune.getRune_description());
            ps.setString(5, rune.getRune_ID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int delete(String rune_ID) {
        String sql = "delete from rune where rune_ID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rune_ID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public rune select(String rune_ID) {
        String sql = "select * from rune where rune_ID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rune_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rune rune = new rune();
                rune.setRune_ID(rs.getString("rune_ID"));
                rune.setRune_name(rs.getString("rune_name"));
                rune.setRune_type(rs.getString("rune_type"));
                rune.setRune_price(rs.getInt("rune_price"));
                rune.setRune_description(rs.getString("rune_description"));
                return rune;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<rune> select() {
        String sql = "select * from rune";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<rune> runeList = new ArrayList<rune>();
            while (rs.next()) {
                rune rune = new rune();
                rune.setRune_ID(rs.getString("rune_ID"));
                rune.setRune_name(rs.getString("rune_name"));
                rune.setRune_type(rs.getString("rune_type"));
                rune.setRune_price(rs.getInt("rune_price"));
                rune.setRune_description(rs.getString("rune_description"));
                runeList.add(rune);
            }
            return runeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
