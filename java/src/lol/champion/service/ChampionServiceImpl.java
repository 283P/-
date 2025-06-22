package lol.champion.service;

import lol.champion.dao.championDaoImpl;
import lol.champion.entity.championclass;

import java.util.List;
import java.util.Map;

public class ChampionServiceImpl implements ChampionService {
    private championDaoImpl Champion = new championDaoImpl();

    @Override
    public String getChampionInfo(String championId) {
        championclass champion = Champion.select(championId);
        return champion != null ? champion.toString() : "英雄不存在";
    }

    @Override
    public String deleteChampion(championclass championclass) {
        return Champion.delete(championclass);
    }

    @Override
    public String getTopChampion(String username) {
        List<Map<String, Object>> stats = Champion.selectChampionStats(username);
        if (stats.isEmpty()) {
            return "无数据";
        }

        // 找出使用次数最多的英雄
        Map<String, Object> topChampion = stats.stream()
            .max((a, b) -> Integer.compare(
                (int)a.get("games_played"),
                (int)b.get("games_played")))
            .get();

        return (String) topChampion.get("champion_name");
    }

    @Override
    public List<Map<String, Object>> getChampionStats(String username) {
        List<Map<String, Object>> stats = Champion.selectChampionStats(username);

        // 添加服务层验证
        stats.forEach(stat -> {
            double winRate = (double) stat.get("win_rate");
            System.out.println("服务层验证 - 英雄:" + stat.get("champion_name")
                + " 胜率:" + winRate);
        });

        return stats;
    }

}

