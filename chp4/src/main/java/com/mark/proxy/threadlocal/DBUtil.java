package com.mark.proxy.threadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lulei on 2017/6/14.
 */
public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
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

    /**
     * 开启事物
     */
    public static  void beginTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try{
                conn.setAutoCommit(false);
            }catch (SQLException e){
                logger.error("begin transaction failure", e);
                e.printStackTrace();
            }finally {
                connContainer.set(conn);
            }
        }
    }

    /**
     * 提交事物
     */
    public static  void commitTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try{
                conn.commit();
                conn.close();
            }catch (SQLException e){
                logger.error("commit transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                connContainer.remove();
            }
        }
    }

    /**
     * 回滚事物
     */
    public static  void rollbackTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try{
                conn.rollback();
                conn.close();
            }catch (SQLException e){
                logger.error("rollback transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                connContainer.remove();
            }
        }
    }
}
