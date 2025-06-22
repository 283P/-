package lol.champion.service;

import lol.champion.entity.championclass;

import java.util.List;
import java.util.Map;

public interface ChampionService {

    String getChampionInfo(String championId);
    String deleteChampion(championclass championId);

    String getTopChampion(String username);

    List<Map<String, Object>> getChampionStats(String username);
}
