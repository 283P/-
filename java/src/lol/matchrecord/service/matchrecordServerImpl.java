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
    public String deleteMatchRecord(String match_ID) {
        dao.delete(match_ID);
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
    // 新增插入方法
    @Override
    public boolean insertMatchRecord(String playerId, String result, int goldEarned, String championName) {
        matchrecordclass record = new matchrecordclass();
        // 假设matchrecordclass有以下字段，请根据实际类定义调整
        record.setPlayer_ID(playerId);
        record.setMatch_result(result);
        record.setMatch_goldearned(goldEarned);
        record.setChampion_name(championName);

        try {
            dao.insert(record);
            return true;
        } catch (Exception e) {
            System.err.println("插入记录失败: " + e.getMessage());
            return false;
        }
    }
}
