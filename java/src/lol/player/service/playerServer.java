package lol.player.service;

import lol.player.entity.playerclass;

public interface playerServer {
    String getplayerInfo(String playerId);
    String deleteplayer(String playerId);
    String addplayer(playerclass player);
    String updateplayer(playerclass player);
}
