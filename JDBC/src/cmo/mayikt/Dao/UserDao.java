package cmo.mayikt.Dao;

import cmo.mayikt.entity.StudentEntity;
import cmo.mayikt.entity.UserEntity;
import cmo.mayikt.utils.MayiktJdbcUtils;

import java.sql.*;

public class UserDao {
    /**
     *   需要思考？
     *   如果手机号码 重复 or 输入的值为空 ？
     *   写一个查询的方法 来判断手机号码是否 存在
     * @param user
     * @return
     */

    public int registerUser(UserEntity user){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MayiktJdbcUtils.getConnection();
            statement = connection.createStatement();
            String insertUserSql="INSERT INTO `mayiktmeite`.`mayikt_users`(`id`, `phone`, `pwd`) VALUES (null, '"+user.getPhone()+"','"+user.getPwd()+"')";
            System.out.println(insertUserSql);
            int result = statement.executeUpdate(insertUserSql);
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MayiktJdbcUtils.closeConnection(statement,connection);
        }
        return 0;
    }
    public UserEntity getByIdPhone(String phone){
        if(phone == null || phone.length() == 0 ){
            return null;
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MayiktJdbcUtils.getConnection();
            statement = connection.createStatement();
            String SelectUserSql = "SELECT * FROM mayikt_users WHERE phone='"+phone+"'";
            resultSet = statement.executeQuery(SelectUserSql);
            System.out.println(SelectUserSql);
            boolean result = resultSet.next();
            // 判断结果如果查询不到数据 则不会取值
            if (!result) {
                return null;
            }
            Long dbId = resultSet.getLong("id");
            String dbPhone = resultSet.getString("phone");
            String dbPwd = resultSet.getString("pwd");
            UserEntity userEntity = new UserEntity(dbId, dbPhone, dbPwd);
            return userEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            MayiktJdbcUtils.closeConnection(resultSet,statement,connection);
        }
    }
    public UserEntity loginUser(UserEntity user){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MayiktJdbcUtils.getConnection();
            statement = connection.createStatement();
            String SelectUserSql = "SELECT * FROM mayikt_users WHERE phone='"+user.getPhone()+"' and pwd='"+user.getPwd()+"'";
            resultSet = statement.executeQuery(SelectUserSql);
            System.out.println(SelectUserSql);
            boolean result = resultSet.next();
            // 判断结果如果查询不到数据 则不会取值
            if (!result) {
                return null;
            }
            Long dbId = resultSet.getLong("id");
            String dbPhone = resultSet.getString("phone");
            String dbPwd = resultSet.getString("pwd");
            UserEntity userEntity = new UserEntity(dbId, dbPhone, dbPwd);
            return userEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            MayiktJdbcUtils.closeConnection(resultSet,statement,connection);
        }
    }

    /**
     * 解决sql 注入攻击 的登录方法
     *
     * @param user
     * @return
     */
    public UserEntity loginUser2(UserEntity user){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MayiktJdbcUtils.getConnection();
            //调用预编译sql语句的方式
            String SelectUserSql = "SELECT * FROM mayikt_users WHERE phone='"+user.getPhone()+"' and pwd='"+user.getPwd()+"'";
            statement = connection.prepareStatement(SelectUserSql);
            statement.setString(1,user.getPhone());
            statement.setString(2,user.getPwd());
            resultSet = statement.executeQuery(SelectUserSql);
            System.out.println(SelectUserSql);
            boolean result = resultSet.next();
            // 判断结果如果查询不到数据 则不会取值
            if (!result) {
                return null;
            }
            Long dbId = resultSet.getLong("id");
            String dbPhone = resultSet.getString("phone");
            String dbPwd = resultSet.getString("pwd");
            UserEntity userEntity = new UserEntity(dbId, dbPhone, dbPwd);
            return userEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            MayiktJdbcUtils.closeConnection(resultSet,statement,connection);
        }
    }
}
