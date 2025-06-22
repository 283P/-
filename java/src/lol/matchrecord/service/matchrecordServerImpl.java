package lol.matchrecord.service;

import lol.matchrecord.dao.matchrecorddaoImpl;
import lol.matchrecord.entity.matchrecordclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class matchrecordServerImpl implements matchrecordServer {
    private matchrecorddaoImpl dao = new matchrecorddaoImpl();



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
//查询所有比赛记录
    @Override
    public List<Map<String, Object>> getMatchRecords(String username) {
        List<matchrecordclass> records = dao.select(username);
        List<Map<String, Object>> result = new ArrayList<>();

        for (matchrecordclass record : records) {
            Map<String, Object> map = new HashMap<>();
            map.put("match_id", record.getMatch_ID());
            map.put("goldearned", record.getMatch_goldearned());
            map.put("result", record.getMatch_result());
            // 添加其他需要的字段...
            result.add(map);
        }
        return result;
    }
    @Override
    public int getTotalMatches(String username) {
        return dao.select().size();
    }
}
