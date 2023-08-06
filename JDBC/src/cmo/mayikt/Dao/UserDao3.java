package cmo.mayikt.Dao;

import cmo.mayikt.entity.UserEntity;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * c3p0 数据库池 dao层
 *
 *  jdbc 连接信息  1. xml 2. config.properties 3. yml
 */
public class UserDao3 {
    /**
     *  创建的c3p0 连接池 可以通过 构造方法的形式指定  没有则使用默认的
     *  有指定的情况下 使用指定的信息
     * @param id
     * @return
     */
    public UserEntity getUserEntity(Long id) {
        ResultSet resultSet = null;
        Connection connection = null;
//        ComboPooledDataSource pool = new ComboPooledDataSource("mayikt-otherc3p0");  指定连接池
        ComboPooledDataSource pool = new ComboPooledDataSource();// 创建c3p0数据库连接池
        try {
//            pool.setUser("root");// 用户名称
//            pool.setPassword("zlt144367100");// 用户密码
//            pool.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mayiktmeite?zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");// MySQL数据库连接url
//            pool.setDriverClass("com.mysql.jdbc.Driver"); // 加载驱动
            // 自动读取 配置文件
            //2.获取连接对象
            connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from  mayikt_users where id=?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            // 获取该行数据的第一列 id
            Long dbId = resultSet.getLong("id");
            // 获取该行数据的第二列 phone
            String dbPhone = resultSet.getString("phone");
            // 获取该行数据的第三列 pwd
            String dbPwd = resultSet.getString("pwd");
            // 将db中查询到数据封装成user对象
            return new UserEntity(dbId, dbPhone, dbPwd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
