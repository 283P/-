package lol.player.dao;

import lol.DButil.DButil;
import lol.player.entity.playerclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class playerdaoImpl implements playerdao {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    @Override
    /*插入数据*/
    public int insert(playerclass player) {
        // 修正SQL语句（移除champion_ID字段）
        String sql="insert into player(player_ID,player_rank,player_name) values(?,?,?)";
        try {
            conn = DButil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getPlayer_ID());
            pstmt.setString(2, player.getPlayer_rank());
            pstmt.setString(3, player.getPlayer_name());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("插入玩家数据失败: " + e.getMessage(), e);
        } finally {
            DButil.close(conn);
        }
    }
    // 更新方法中的拼写错误修复
    @Override
    /*更新数据*/
    public int update(playerclass player) {
        // 修正字段顺序和设置参数
        String sql="update player set player_name=?,player_rank=? where player_ID=?";
        try {
            conn=DButil.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player.getPlayer_name());
            pstmt.setString(2, player.getPlayer_rank());
            pstmt.setString(3, player.getPlayer_ID());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("更新玩家失败: " + e.getMessage(), e);
        } finally {
            DButil.close(conn);
        }
    }
    @Override
    /*删除数据*/
    public int delete(String player_ID) {
        String sql="delete from player where player_ID=?";
        try {
            conn=DButil.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player_ID);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除玩家数据失败", e); // 添加异常抛出
        } finally {
            DButil.close(conn); // 确保关闭所有资源
        }
    }
    // select方法中也需修正player_name拼写
    @Override
    /*id查询数据*/
    public playerclass select(String player_ID) {
        String sql="select * from player where player_ID=?";
        try {
            conn = DButil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player_ID);
            rs = pstmt.executeQuery();
            if(rs.next()){
                playerclass player = new playerclass();
                player.setPlayer_ID(rs.getString("player_ID"));
                player.setPlayer_rank(rs.getString("player_rank"));
                player.setPlayer_name(rs.getString("player_name"));
                return player;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询玩家数据失败", e);
        } finally {
            DButil.close(conn); // 确保关闭所有资源
        }
        return null;
    }

    @Override
    /*查询所有数据*/
    public List<playerclass> select() {
        String sql="select * from player";
        ResultSet rs = null; // 显式声明ResultSet
        try {
            conn=DButil.getConnection();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            List<playerclass> players=new ArrayList<>();
            while(rs.next()){
                playerclass player=new playerclass();
                player.setPlayer_ID(rs.getString("player_ID"));
                // 修正拼写错误字段
                player.setPlayer_name(rs.getString("player_name"));
                player.setPlayer_rank(rs.getString("player_rank"));
                players.add(player);
            }
            return players;
        } catch (Exception e) {
            throw new RuntimeException("查询所有玩家失败: " + e.getMessage(), e);
        } finally {
            // 关闭所有资源
            try { if(rs != null) rs.close(); } catch (Exception e) {}
            DButil.close(conn);
        }
    }
}

