package lol.matchrecord.dao;

import lol.DButil.DButil;
import lol.matchrecord.entity.matchrecordclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class matchrecorddaoImpl implements matchrecorddao{
   private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;   
    @Override
    public List<matchrecordclass> select() {
        return null;
    }
    @Override
    public matchrecordclass select(String match_ID) {
        String sql="select * from matchrecord where match_ID=?";
        try {
            conn= DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, match_ID);
            rs=pstmt.executeQuery();
            if(rs.next()){
                return new matchrecordclass(rs.getString("match_ID"),rs.getString("match_name"),rs.getString("match_type"),rs.getString("match_cost"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };
    @Override
    public void insert(matchrecordclass matchrecordclass) {
        String sql="insert into matchrecord(match_ID,match_name,match_type,match_cost) values(?,?,?,?)";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, matchrecordclass.getMatch_ID());
            pstmt.setString(2, matchrecordclass.getMatch_result());
            pstmt.setString(3, matchrecordclass.getMatch_goldearnd());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    @Override
    public void update(matchrecordclass matchrecordclass) {
        String sql="update matchrecord set match_name=?,match_type=?,match_cost=? where match_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, matchrecordclass.getMatch_ID());
            pstmt.setString(2, matchrecordclass.getMatch_result());
            pstmt.setString(3, matchrecordclass.getMatch_goldearnd());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    @Override
    public void delete(matchrecordclass matchrecordclass) {
        String sql="delete from matchrecord where match_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, matchrecordclass.getMatch_ID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    @Override
    public void delete(String match_ID) {
        String sql="delete from matchrecord where match_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, match_ID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
