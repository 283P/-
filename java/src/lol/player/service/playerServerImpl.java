package lol.player.service;

import lol.player.dao.playerdaoImpl;
import lol.player.entity.playerclass;

public class playerServerImpl implements playerServer {
    private playerdaoImpl dao = new playerdaoImpl();

    @Override
    public String getplayerInfo(String playerId) {
        return dao.select(playerId).toString();
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
}