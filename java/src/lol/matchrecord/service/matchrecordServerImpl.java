package lol.matchrecord.service;

import lol.matchrecord.dao.matchrecorddaoImpl;
import lol.matchrecord.entity.matchrecordclass;

public class matchrecordServerImpl implements matchrecordServer {
    private matchrecorddaoImpl dao = new matchrecorddaoImpl();

    @Override
    public String getMatchRecord(String puuid) {
        return dao.select(puuid).toString();
    }

    @Override
    public String deleteMatchRecord(String puuid) {
        dao.delete(puuid);
        return "删除比赛记录成功";
    }

    @Override
    public String addMatchRecord(matchrecordclass record) {
        dao.insert(record);
        return "添加比赛记录成功";
    }

    @Override
    public String updateMatchRecord(matchrecordclass record) {
        dao.update(record);
        return "更新比赛记录成功";
    }
}