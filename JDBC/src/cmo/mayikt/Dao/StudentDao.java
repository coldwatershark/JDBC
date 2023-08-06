package cmo.mayikt.Dao;

import cmo.mayikt.entity.StudentEntity;
import cmo.mayikt.utils.MayiktJdbcUtils;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;

public class StudentDao {
    /**
     * 学生对象数据库访问层 Dao
     */
    // 1.查询所有学生的信息
    public ArrayList<StudentEntity> Allstudent() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.导入jdbc驱动
            //2.注册驱动(反射)
            Class.forName("com.mysql.jdbc.Driver");
            //3.获取数据库连接
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mayiktmeite?serverTimezone=UTC", "root", "zlt144367100");
            //4.获取执行者对象
            statement = connection.createStatement();
            //5.执行sql语句并获取返回结果
            resultSet = statement.executeQuery("SELECT * FROM mayikt_student");
            //6.对结果进行处理
            ArrayList<StudentEntity> studentEntities = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                // 将在db中查询到的数据封装成学生对象
                StudentEntity studentEntity = new StudentEntity(id, name, age, address);
                // 将对象存入集合
                studentEntities.add(studentEntity);
            }
            return studentEntities;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
            //7.释放jdbc资源
        } finally {
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
    }


    // 2.根据id查询学生信息
    public StudentEntity getByIdStudent(Long stuId) {
        // 先判断用户是否传递 学生id的值
        if (stuId == null) {
            return null;
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mayiktmeite?serverTimezone=UTC", "root", "zlt144367100");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM mayikt_student WHERE id=" + stuId);
            boolean result = resultSet.next();
            // 判断结果如果查询不到数据 则不会取值
            if (!result) {
                return null;
            }
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Integer age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            // 将在db中查询到的数据封装成学生对象
            StudentEntity studentEntity = new StudentEntity(id, name, age, address);
            return studentEntity;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
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
    }
    // 3.新增学生信息 ----insert into
    public int insertStudent(StudentEntity stu){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mayiktmeite?serverTimezone=UTC", "root", "zlt144367100");
            statement = connection.createStatement();
            String insertStudentSql="INSERT INTO mayikt_student VALUES(null,'"+stu.getName()+"','"+stu.getAge()+"','"+stu.getAddress()+"')";
            int result = statement.executeUpdate(insertStudentSql);
            return result;
           }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
        try {
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
        return 0;
    }
    //4.根据主键id修改学生信息
    public int updateStudent(StudentEntity stu){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mayiktmeite?serverTimezone=UTC", "root", "zlt144367100");
            statement = connection.createStatement();
            String updateStudentSql="UPDATE mayikt_student SET name='"+stu.getName()+"',age='"+stu.getAge()+"',address='"+stu.getAddress()+"' WHERE id='"+stu.getId()+"'";
            System.out.println(updateStudentSql);
            int result=statement.executeUpdate(updateStudentSql);
            return result;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
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
        return 0;
    }
    //5.根据主键id删除学生信息
    public int delStudent(Long id){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MayiktJdbcUtils.getConnection();
            statement = connection.createStatement();
            String delStudentSql = "DELETE FROM mayikt_student WHERE id="+id+"";
            System.out.println(delStudentSql);
            int result = statement.executeUpdate(delStudentSql);
            return result;
        } catch ( SQLException e) {
            e.printStackTrace();
        }finally {
            MayiktJdbcUtils.closeConnection(statement,connection);
        }
        return 0;

    }
}



