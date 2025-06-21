package lol.rune.service;

import lol.rune.entity.runeclass;

import java.sql.SQLException;

public interface runeServer {
    String insert(runeclass rune);
    String update(runeclass rune);
    String delete(String rune_ID) throws SQLException;
    String select(String rune_ID);
}
