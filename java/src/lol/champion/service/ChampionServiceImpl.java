package lol.champion.service;

import lol.champion.dao.championDaoImpl;
import lol.champion.entity.championclass;

public class ChampionServiceImpl implements ChampionService {
    private  championDaoImpl Champion=new championDaoImpl();


    @Override
    public String getChampionInfo(String championId) {
        return Champion.select(championId).toString();

    }

    @Override
    public String deleteChampion(championclass championclass) {
        return Champion.delete(championclass)
        ;
    }
}

