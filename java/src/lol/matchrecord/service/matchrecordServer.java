package lol.matchrecord.service;

import lol.matchrecord.entity.matchrecordclass;

public interface matchrecordServer {
    String getMatchRecord(String puuid);
    String deleteMatchRecord(String puuid);
    String addMatchRecord(matchrecordclass record);
    String updateMatchRecord(matchrecordclass record);
}
