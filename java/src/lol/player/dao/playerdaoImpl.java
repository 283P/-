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
        String sql="insert into player(player_ID,champion_ID,player_rank,player_name) values(?,?,?,?)";
        try {
            conn = DButil.getConnction();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getPlayer_ID());
            pstmt.setString(2, player.getChampion_ID());
            pstmt.setString(3, player.getPlayer_rank());
            pstmt.setString(4, player.getPlayer_name());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入玩家数据失败", e); // 添加异常抛出
        } finally {
            DButil.close(conn); // 确保关闭所有资源
        }
    }
    // 更新方法中的拼写错误修复
    @Override
    /*更新数据*/
    public int update(playerclass player) {
        String sql="update player set champion_ID=?,player_rank=?,player_name=? where player_ID=?"; // 修正plyaer_name拼写
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player.getChampion_ID());
            pstmt.setString(2, player.getPlayer_rank());
            pstmt.setString(3, player.getPlayer_name());
            pstmt.setString(4, player.getPlayer_ID());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn);
        }
        return 0;
    }
    @Override
    /*删除数据*/
    public int delete(String player_ID) {
        String sql="delete from player where player_ID=?";
        try {
            conn=DButil.getConnction();
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
            conn = DButil.getConnction();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player_ID);
            rs = pstmt.executeQuery();
            if(rs.next()){
                playerclass player = new playerclass();
                player.setPlayer_ID(rs.getString("player_ID"));
                player.setChampion_ID(rs.getString("champion_ID"));
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
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            List<playerclass> playerclasses=new ArrayList<playerclass>();
            while(rs.next()){
                playerclass playerclass=new playerclass();
                playerclass.setPlayer_ID(rs.getString("player_ID"));
                playerclass.setChampion_ID(rs.getString("champion_ID"));
                playerclass.setPlayer_rank(rs.getString("player_rank"));
                playerclass.setPlayer_name(rs.getString("plyaer_name"));
                playerclasses.add(playerclass);
            }
            return playerclasses;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn);
        }
        return null;
    }
}

