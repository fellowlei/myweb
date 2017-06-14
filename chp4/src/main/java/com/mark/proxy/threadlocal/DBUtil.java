package com.mark.proxy.threadlocal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by lulei on 2017/6/14.
 */
public class DBUtil {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/demo";
    private static final String username = "root";
    private static final String password = "root";

    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();

    // 获取连接
    public static Connection getConnection(){
        Connection conn = connContainer.get();
        try{
            if(conn == null){
                Class.forName(driver);
                conn = DriverManager.getConnection(url,username,password);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connContainer.set(conn);
        }
        return conn;
    }


    // 关闭连接
    public static void closeConnection(){
        Connection conn = connContainer.get();
        try{
            if (conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connContainer.remove();
        }
    }
}
