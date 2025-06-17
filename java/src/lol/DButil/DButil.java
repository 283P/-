package lol.DButil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DButil {
    /*连接到数据库 */
    public static Connection getConnction(){
        Connection conn=null;
        String url="jdbc:mysql://localhost:3306/lol?serverTimezone=UTC&useSSL=false";
        String username="root";
        String password="114514";
        try {
            conn= DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return conn;
    }
    /*关闭数据库连接 */
    public static Connection close(Connection conn){
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
