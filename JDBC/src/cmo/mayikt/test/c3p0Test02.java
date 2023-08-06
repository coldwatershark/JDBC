package cmo.mayikt.test;

import cmo.mayikt.Service.UserService;
import cmo.mayikt.entity.UserEntity;

import java.util.Scanner;

public class c3p0Test02 {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要查询的id");
        Long id = sc.nextLong();
        System.out.println(userService.selectUser(id));
    }
}
