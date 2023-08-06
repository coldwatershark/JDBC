package cmo.mayikt.test;

import cmo.mayikt.entity.UserEntity;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 德鲁伊数据库连接池（企业开发常用）
 *
 */
public class druidTest {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        // 1. 读取 druid.properties 配置文件
        InputStream resourceAsStream =
                druidTest.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        // 2. 将配置文件 放入数据库连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        // 3.通过dataSource 连接
        Connection connection = dataSource.getConnection();
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
