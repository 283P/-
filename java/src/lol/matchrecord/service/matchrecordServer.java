package lol.matchrecord.service;

import lol.matchrecord.entity.matchrecordclass;

import java.util.List;
import java.util.Map;

public interface matchrecordServer {
    List<Map<String, Object>> getMatchRecords(String username); // 修改返回值类型
    String deleteMatchRecord(String puuid);
    String addMatchRecord(matchrecordclass record);
    String updateMatchRecord(matchrecordclass record);

    int getTotalMatches(String username);
}
