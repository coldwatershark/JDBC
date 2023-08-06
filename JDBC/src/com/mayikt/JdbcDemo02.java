package com.mayikt;

import com.mysql.jdbc.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDemo02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.registerDriver(new Driver());
        /**
         *
         * 反射机制的底层------ 注册mysql的底层   调用 DriverManager.registerDriver(new Driver())
         * 类加载器机制 com.mysql.jdbc.Driver 加载到该程序中 new Driver()
         * 双亲委派机制
         *
         * 注册驱动方式
         * 1.DriverManager.registerDriver();
         * 2.写代码实现
         * Class.forName("com.mysql.jdbc.Driver");
         * 3.com.mysql.jdbc.Driver类中存在静态代码块 实现注册mysql驱动
         * 4.静态代码块
         * public class Driver extends NonRegisteringDriver implements java.sql.Driver {
         *     //
         *     // Register ourselves with the DriverManager
         *     //
         *     static {
         *         try {
         *             java.sql.DriverManager.registerDriver(new Driver());
         *         } catch (SQLException E) {
         *             throw new RuntimeException("Can't register driver!");
         *         }
         *     }
         * 开发者是不需要调用DriverManager.registerDriver();方法，因为我们在使用class.forName 会加载到我们的
         * com.mysql.jdbc.Driver 通过Driver静态代码快 注册我们的Driver驱动。
         *
         */



    }
}
