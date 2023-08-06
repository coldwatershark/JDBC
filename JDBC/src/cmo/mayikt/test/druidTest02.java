package cmo.mayikt.test;

import cmo.mayikt.entity.UserEntity;
import cmo.mayikt.utils.DataSourceUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class druidTest02 {
    public static void main(String[] args) throws SQLException {
        Connection connection = DataSourceUtils.getConnection();
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
        // 获取该行数据的第三列 pwdS
        String dbPwd = resultSet.getString("pwd");
        // 将db中查询到数据封装成user对象
        UserEntity userEntity = new UserEntity(dbId, dbPhone, dbPwd);
        System.out.println(userEntity);
        DataSourceUtils.closeConnection(connection,preparedStatement,resultSet);
    }
}
