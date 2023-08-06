package com.mayikt;
import com.mysql.jdbc.Driver;

import java.sql.*;


/**
 * JDBC 快速入门
 */



public class JdbcDemo01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.导入mysql驱动jar包;
        //2.注册驱动 javase 反射机制Class.forName()
        Class.forName("com.mysql.jdbc.Driver"); // 反射机制 ， jdbc注册驱动   1. 反射机制 2. SPI 3. 类加载器
        //3.获取数据库连接
        Connection connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mayikt?serverTimezone=UTC","root","zlt144367100");
        //4.获取执行者对象
        Statement statement=connection.createStatement();
        //5.执行sql语句并获取返回结果
        ResultSet resultSet=statement.executeQuery("SELECT * FROM mayikt_student"); // 执行查询的sql语句
        //6.对结果进行处理
        while(resultSet.next()){ // 判断下一行是否有数据 ，有则取出，没有则返回false
            int id= resultSet.getInt("id");
            int age=resultSet.getInt("age");
            String address=resultSet.getString("address");
            int class_id=resultSet.getInt("class_id");
            System.out.println("id:"+id);
            System.out.println("age:"+age);
            System.out.println("address:"+address);
            System.out.println("class_id:"+class_id);
        }
        //7.释放jdbc资源
        connection.close();
        statement.close();
    }
}
