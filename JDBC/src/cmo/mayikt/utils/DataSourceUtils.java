package cmo.mayikt.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataSourceUtils {
    /**
     *  将 构造方法私有化
     */
    private static DataSource dataSource;
    private DataSourceUtils(){

    }
    static {
        try {
        Properties properties = new Properties();
        InputStream resourceAsStream =
                DataSourceUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装获取连接的方法
     */
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 封装释放连接的方法
     */
    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet){
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

    public static void closeConnection(Connection connection, Statement statement){
        closeConnection(connection,statement,null);
    }
}
