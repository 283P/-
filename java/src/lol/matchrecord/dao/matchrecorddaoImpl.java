package lol.matchrecord.dao;

import lol.DButil.DButil;
import lol.matchrecord.entity.matchrecordclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class matchrecorddaoImpl implements matchrecorddao{
   private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;   
    @Override
    /*查询所有数据*/
    public List<matchrecordclass> select() {
        String sql="select * from matchrecord";
        List<matchrecordclass> list=new ArrayList<matchrecordclass>();
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();

            // 处理结果集
            while(rs.next()) {
                list.add(new matchrecordclass(
                    rs.getString("match_ID"),
                    rs.getString("match_result"),
                    rs.getString("match_goldearned")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }  ;
    @Override
    /*根据ID查询数据*/
    public matchrecordclass select(String match_ID) {
        String sql="select * from matchrecord where match_ID=?";
        try {
            conn= DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, match_ID);
            rs=pstmt.executeQuery();
            if(rs.next()){
                return new matchrecordclass(rs.getString("match_ID"),rs.getString("match_result"),rs.getString("match_goldearned"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };
    @Override
    /*插入数据*/
    public void insert(matchrecordclass matchrecordclass) {
        String sql="insert into matchrecord(match_ID,match_result,match_goldearned) values(?,?,?)";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, matchrecordclass.getMatch_ID());
            pstmt.setString(2, matchrecordclass.getMatch_result());
            pstmt.setString(3, matchrecordclass.getMatch_goldearned());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    /*更新数据*/
    public void update(matchrecordclass matchrecordclass) {
        String sql="update matchrecord set match_result=?,match_goldearned=? where match_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            // 修正参数顺序：1.result 2.gold 3.ID
            pstmt.setString(1, matchrecordclass.getMatch_result());
            pstmt.setString(2, matchrecordclass.getMatch_goldearned());
            pstmt.setString(3, matchrecordclass.getMatch_ID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    /*删除数据*/
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
    /*根据ID删除数据*/
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
