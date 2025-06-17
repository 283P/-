package lol.player.dao;

import java.util.List;

import lol.player.entity.playerclass;

public class playerdaoImpl implements playerdao {
    @Override
    public int insert(playerclass player) {
        String sql="insert into player(player_ID,champion_ID,player_rank,plyaer_name) values(?,?,?,?)";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player.getPlayer_ID());
            pstmt.setString(2, player.getChampion_ID());
            pstmt.setString(3, player.getPlayer_rank());
            pstmt.setString(4, player.getPlyaer_name());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn, pstmt, null);
        }
        return 0;
    }
    @Override
    public int update(playerclass player) {
        String sql="update player set champion_ID=?,player_rank=?,plyaer_name=? where player_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player.getChampion_ID());
            pstmt.setString(2, player.getPlayer_rank());
            pstmt.setString(3, player.getPlyaer_name());
            pstmt.setString(4, player.getPlayer_ID());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn, pstmt, null);
        }
        return 0;
    }
    @Override
    public int delete(String player_ID) {
        String sql="delete from player where player_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player_ID);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn, pstmt, null);
        }
        return 0;
    }
    @Override
    public playerclass select(String player_ID) {
        String sql="select * from player where player_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, player_ID);
            rs=pstmt.executeQuery();
            if(rs.next()){
                playerclass playerclass=new playerclass();
                playerclass.setPlayer_ID(rs.getString("player_ID"));
                playerclass.setChampion_ID(rs.getString("champion_ID"));
                playerclass.setPlayer_rank(rs.getString("player_rank"));
                playerclass.setPlyaer_name(rs.getString("plyaer_name"));
                return playerclass;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn, pstmt, rs);
        }
        return null;
    }
    @Override
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
                playerclass.setPlyaer_name(rs.getString("plyaer_name"));
                playerclasses.add(playerclass);
            }
            return playerclasses;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.close(conn, pstmt, rs);
        }
        return null;
    }
}
