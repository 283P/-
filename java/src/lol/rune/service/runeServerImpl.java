package lol.rune.service;

import lol.rune.dao.runedaoImpl;
import lol.rune.entity.runeclass;

import java.sql.SQLException;

public class runeServerImpl implements runeServer {
    private runedaoImpl dao = new runedaoImpl();

    @Override
    public String insert(runeclass rune)  {
        dao.insert(rune);
        return "符文添加成功";
    }

    @Override
    public String update(runeclass rune) {
        dao.update(rune);
        return "符文更新成功";
    }

    @Override
    public String delete(String rune_ID) throws SQLException {
        dao.delete(rune_ID);
        return "符文删除成功";
    }

    @Override
    public String select(String rune_ID) {
        runeclass rune = dao.select(rune_ID);
        return rune != null ? rune.toString() : "未找到该符文";
    }
}