package cmo.mayikt.Dao;

import cmo.mayikt.entity.UserEntity;
import cmo.mayikt.utils.MayiktJdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  开启事务  增加，修改，删除数据的时候需要加上事务
 */
public class UserDao2 {
    public int registerUser(UserEntity user){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MayiktJdbcUtils.getConnection();
            connection.setAutoCommit(false);  // fasle 为开启事务 手动提交  true 为自动提交
            statement = connection.createStatement();
            String insertUserSql="INSERT INTO `mayiktmeite`.`mayikt_users`(`id`, `phone`, `pwd`) VALUES (null, '"+user.getPhone()+"','"+user.getPwd()+"')";
            System.out.println(insertUserSql);
            int result = statement.executeUpdate(insertUserSql);
            connection.commit(); // 提交事务
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            MayiktJdbcUtils.closeConnection(statement,connection);
        }
        return 0;
    }
}
