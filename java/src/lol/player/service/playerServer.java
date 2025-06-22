package lol.player.service;

import lol.player.entity.playerclass;

import java.util.Map;

public interface playerServer {
    Map<String, Object> getplayerInfo(String playerId); // 修改返回值类型
    String deleteplayer(String playerId);
    String addplayer(playerclass player);
    String updateplayer(playerclass player);

    String getPlayerRank(String username);
}
