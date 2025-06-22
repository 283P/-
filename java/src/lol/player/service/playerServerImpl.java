package lol.player.service;

import lol.player.dao.playerdaoImpl;
import lol.player.entity.playerclass;

import java.util.HashMap;
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
        dao.delete(playerId);
        return "删除玩家成功";
    }

    @Override
    public String addplayer(playerclass player) {
        dao.insert(player);
        return "添加玩家成功";
    }

    @Override
    public String updateplayer(playerclass player) {
        dao.update(player);
        return "更新玩家信息成功";
    }

    @Override
    public String getPlayerRank(String username) {
        playerclass player = dao.select(username); // 假设select方法可以通过username查询玩家
        return player != null ? player.getPlayer_rank() : "未定级";
    }
}