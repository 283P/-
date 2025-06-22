package lol.player.service;

import lol.player.dao.playerdaoImpl;
import lol.player.entity.playerclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class playerServerImpl implements playerServer {
    private playerdaoImpl dao = new playerdaoImpl();

    @Override
    public Map<String, Object> getplayerInfo(String playerId) {
        playerclass player = dao.select(playerId);
        if (player == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("player_name", player.getPlayer_name());
        result.put("player_id", player.getPlayer_ID());
        result.put("rank", player.getPlayer_rank());
        // 添加其他需要的字段...
        return result;
    }

    @Override
    public String deleteplayer(String playerId) {
        return "";
    }

    @Override
    public String addPlayer(String playerId, String playerName, String rank) {
        try {
            playerclass newPlayer = new playerclass();
            newPlayer.setPlayer_ID(playerId);
            newPlayer.setPlayer_name(playerName);
            newPlayer.setPlayer_rank(rank);
            dao.insert(newPlayer);
            return "玩家添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加玩家失败: " + e.getMessage());
        }
    }

    @Override
    public String updatePlayer(String playerId, String newName, String newRank) {
        try {
            playerclass existingPlayer = dao.select(playerId);
            if (existingPlayer == null) {
                return "玩家不存在";
            }
            existingPlayer.setPlayer_name(newName);
            existingPlayer.setPlayer_rank(newRank);
            dao.update(existingPlayer);
            return "玩家信息更新成功";
        } catch (Exception e) {
            throw new RuntimeException("更新玩家失败: " + e.getMessage());
        }
    }

    @Override
    public String deletePlayer(String playerId) {
        try {
            int result = dao.delete(playerId);
            return result > 0 ? "玩家删除成功" : "玩家不存在";
        } catch (Exception e) {
            throw new RuntimeException("删除玩家失败: " + e.getMessage());
        }
    }

    @Override
    public String getPlayerRank(String playerId) {
        try {
            playerclass player = dao.select(playerId);
            // 添加空值检查和默认值
            if (player == null) {
                System.err.println("玩家不存在: " + playerId);
                return "未找到玩家";
            }

            // 添加字段存在性检查
            String rank = player.getPlayer_rank();
            if (rank == null || rank.isEmpty()) {
                return "未定级";
            }
            return rank;
        } catch (Exception e) {
            // 添加详细错误日志
            System.err.println("段位查询失败 [玩家ID:" + playerId + "]");
            e.printStackTrace();
            return "查询错误"; // 保持与前端兼容
        }
    }
    @Override
    public String addplayer(playerclass player) {
        return this.addPlayer(player.getPlayer_ID(), player.getPlayer_name(), player.getPlayer_rank());
    }

    @Override
    public String updateplayer(playerclass player) {
        return this.updatePlayer(player.getPlayer_ID(), player.getPlayer_name(), player.getPlayer_rank());
    }

    @Override
    public List<Map<String, Object>> getAllPlayers() {
        try {
            List<playerclass> players = dao.selectAll();
            List<Map<String, Object>> result = new ArrayList<>();

            for (playerclass player : players) {
                Map<String, Object> data = new HashMap<>();
                data.put("player_id", player.getPlayer_ID());
                data.put("player_name", player.getPlayer_name());
                data.put("rank", player.getPlayer_rank());
                result.add(data);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("获取全部玩家失败: " + e.getMessage());
        }
    }

}

