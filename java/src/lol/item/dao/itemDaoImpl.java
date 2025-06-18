package lol.item.dao;

import lol.DButil.DButil;
import lol.item.entity.itemclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class itemDaoImpl implements itemdao {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private itemclass item;
    @Override
    /*插入数据*/
    public void insert(itemclass itemclass) {
        String sql="insert into item values(?,?,?,?)";
        try {
            conn= DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, itemclass.getItem_ID());
            pstmt.setString(2, itemclass.getItem_name());
            pstmt.setString(3, itemclass.getItem_type());
            pstmt.setString(4, itemclass.getItem_cost());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    /*更新数据*/
    public void update(itemclass itemclass) {
        String sql="update item set item_name=?,item_type=?,item_cost=? where item_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, itemclass.getItem_name());
            pstmt.setString(2, itemclass.getItem_type());
            pstmt.setString(3, itemclass.getItem_cost());
            pstmt.setString(4, itemclass.getItem_ID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    /*删除数据*/
    public void delete(itemclass itemclass) {
        String sql="delete from item where item_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, itemclass.getItem_ID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    /*id查询数据*/
    public itemclass select(String item_ID) {
        String sql="select * from item where item_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, item_ID);
            rs=pstmt.executeQuery();
            if(rs.next()){
                return new itemclass(rs.getString("item_ID"),rs.getString("item_name"),rs.getString("item_type"),rs.getString("item_cost"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    /*查询所有数据*/
    public List<itemclass> selectAll() {
        String sql="select * from item";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            List<itemclass> itemclassList=new ArrayList<itemclass>();
            while(rs.next()){
                itemclassList.add(new itemclass(rs.getString("item_ID"),rs.getString("item_name"),rs.getString("item_type"),rs.getString("item_cost")));
            }
            return itemclassList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


