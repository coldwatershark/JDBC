package cmo.mayikt.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class c3p0Test03 {
    public static void main(String[] args) throws SQLException {
        ComboPooledDataSource pool = new ComboPooledDataSource(); // 初始化 连接数量5  最大连接数量 10 等待时间 3
        for (int i = 1; i <=20; i++) {
            Connection connection = pool.getConnection();
            System.out.println(i + "," + connection);
            if(i==10){
                // 归还
                connection.close(); // 释放连接将 connection归还给数据库连接池
            }

        }
    }
}
