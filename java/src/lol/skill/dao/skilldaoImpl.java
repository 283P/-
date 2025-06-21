package lol.skill.dao;

import lol.DButil.DButil;
import lol.skill.entity.skillclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class skilldaoImpl implements skilldao {
    private Connection conn=DButil.getConnection();
    private PreparedStatement ps;
    private ResultSet rs;


    @Override
    /*插入数据*/
    public int insert(skillclass skillclass) {
        String sql = "insert into skill(skill_ID, champion_ID, skill_name, skill_type, cooldown, skill_effect) values(?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, skillclass.getSkill_ID());
            ps.setString(2, skillclass.getChampion_ID());
            ps.setString(3, skillclass.getSkill_name());
            ps.setString(4, skillclass.getSkill_type());
            ps.setString(5, skillclass.getCooldown());
            ps.setString(6, skillclass.getSkill_effect());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    /*更新数据*/
    public int update(skillclass skillclass) {
        String sql = "update skill set skill_name=?, skill_type=?, cooldown=?, skill_effect=? where skill_ID=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, skillclass.getSkill_name());
            ps.setString(2, skillclass.getSkill_type());
            ps.setString(3, skillclass.getCooldown());
            ps.setString(4, skillclass.getSkill_effect());
            ps.setString(5, skillclass.getSkill_ID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*删除数据*/
    @Override
    public int delete(String skill_ID) {
        String sql = "delete from skill where skill_ID=?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, skill_ID);
            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0) {
                System.out.println("警告: 未找到技能ID " + skill_ID + " 进行删除");
            }
            return affectedRows;
        } catch (SQLException e) {
            throw new RuntimeException("删除技能失败", e);
        }
    }
    @Override
    /*查询数据*/
    public skillclass select(String skill_ID) {
        String sql = "select * from skill where skill_ID=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, skill_ID);
            rs = ps.executeQuery();
            if(rs.next()) {
                skillclass skillclass = new skillclass();
                skillclass.setSkill_ID(rs.getString("skill_ID"));
                skillclass.setChampion_ID(rs.getString("champion_ID"));
                skillclass.setSkill_name(rs.getString("skill_name"));
                skillclass.setSkill_type(rs.getString("skill_type"));
                skillclass.setCooldown(rs.getString("cooldown"));
                skillclass.setSkill_effect(rs.getString("skill_effect"));
                return skillclass;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*查询所有数据*/
    @Override
    public List<skillclass> select() {
        String sql = "select * from skill";
        try (Connection conn = DButil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<skillclass> skillclassList = new ArrayList<>();
            while(rs.next()) {
                skillclass skill = new skillclass();
                skill.setSkill_ID(rs.getString("skill_ID"));
                skill.setChampion_ID(rs.getString("champion_ID"));
                skill.setSkill_name(rs.getString("skill_name"));
                skill.setSkill_type(rs.getString("skill_type"));
                skill.setCooldown(rs.getString("cooldown"));
                skill.setSkill_effect(rs.getString("skill_effect"));
                skillclassList.add(skill);
            }
            return skillclassList;
        } catch (SQLException e) {
            throw new RuntimeException("查询所有技能失败", e);
        }
    }
    @Override
    /*删除所有数据*/
    public int deleteAll() {
        String sql = "delete from skill";
        try {
            ps = conn.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}



