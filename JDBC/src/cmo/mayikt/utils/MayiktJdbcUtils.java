package cmo.mayikt.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * jdbc工具类的封装
 *
 */


public class MayiktJdbcUtils {
    /**
     *   1. 需要将我们的构造方法私有化 ----工具类   不需要new出来 通过 类名称.方法名称来访问
     */
    private  MayiktJdbcUtils(){
    }


    /**
     *  2. 定义工具类需要的  声明 变量
     */
    private static String driverclass;
    private static String url;
    private static String user;
    private static String password;

    /**
     *  3.  静态代码块  给声明好的  jdbc 变量进行赋值操作 （读取config.properties）
     */
    static {
        //1. 读取config.properties   IO 路径 --- 相对路径
        InputStream resourceAsStream = MayiktJdbcUtils.class.getClassLoader().getResourceAsStream("config.properties");
        //2. 赋值给声明好的变量
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            driverclass=properties.getProperty("driverclass");
            url=properties.getProperty("url");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
            //3.  注册驱动类
            Class.forName(driverclass);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println(MayiktJdbcUtils.driverclass);
    }


    /**
     *  4.  封装连接方法
     */
    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     *   5. 封装释放连接方法 (重载)
     */
    public static void closeConnection(ResultSet resultSet,Statement statement,Connection connection){
        // 1. 查询释放连接
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // 2. 增删改的 释放连接
    public static void closeConnection(Statement statement,Connection connection){
        closeConnection(null, statement, connection);
    }
    /**
     * 开启事务
     *
     * @param connection
     * @throws SQLException
     */
    public static void beginTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * 提交事务
     *
     * @param connection
     * @throws SQLException
     */
    public static void commitTransaction(Connection connection) throws SQLException {
        connection.commit();
    }

    /**
     * 回滚事务
     *
     * @param connection
     */
    public static void rollBackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭事务
     *
     * @param connection
     */
    public static void endTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}