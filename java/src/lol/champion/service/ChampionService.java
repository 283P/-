package lol.champion.service;

import lol.champion.entity.championclass;

public interface ChampionService {

    String getChampionInfo(String championId);
    String deleteChampion(championclass championId);

}
