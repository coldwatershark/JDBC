package cmo.mayikt.test;

import cmo.mayikt.entity.StudentEntity;
import cmo.mayikt.entity.UserEntity;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * c3p0 数据库池
 */
public class c3p0Test {
    public static void main(String[] args) throws SQLException {
        ComboPooledDataSource pool = new ComboPooledDataSource();
//        pool.setUser("root");
//        pool.setPassword("zlt144367100");
//        pool.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mayiktmeite?serverTimezone=UTC");
//        pool.setDriverClass("com.mysql.jdbc.Driver");
        // 底层默认读取配置文件
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from  mayikt_users where id=?");
        preparedStatement.setLong(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return;
        }
        // 获取该行数据的第一列 id
        Long dbId = resultSet.getLong("id");
        // 获取该行数据的第二列 phone
        String dbPhone = resultSet.getString("phone");
        // 获取该行数据的第三列 pwd
        String dbPwd = resultSet.getString("pwd");
        // 将db中查询到数据封装成user对象
        UserEntity userEntity = new UserEntity(dbId, dbPhone, dbPwd);
        System.out.println(userEntity);
        resultSet.close();
        connection.close();
    }

    }

