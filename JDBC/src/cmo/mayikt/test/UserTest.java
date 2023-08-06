package cmo.mayikt.test;

import cmo.mayikt.Service.UserService;
import cmo.mayikt.entity.UserEntity;

import java.util.Scanner;

public class UserTest {
    private UserService userService = new UserService();
    private Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        UserTest userTest = new UserTest();
        userTest.index();
    }

    public void index(){
        System.out.println("请输入需要进行的操作序号");
        System.out.println("1.注册账号");
        System.out.println("2.登录账号");
        int number = scanner.nextInt();
        scanner.nextLine();
        switch (number){
            case 1 :
                registerUser();
            case 2 :
                loginUser();
        }
    }

    public void registerUser(){
        System.out.println("请输入注册的手机号");
        String phone = scanner.nextLine();
        System.out.println("请输入注册的密码");
        String pwd = scanner.nextLine();
        int result = userService.registerUser(new UserEntity(phone, pwd));
        if(result > 0){
            System.out.println("用户注册成功");
        }else{
            System.out.println("用户注册失败");
        }
    }
    public void loginUser(){
        for(int i = 1; i <= 3; i++){
        System.out.println("请输入手机号");
        String phone = scanner.nextLine();
        System.out.println("请输入密码");
        String pwd = scanner.nextLine();
        UserEntity dbUserEntity = userService.loginUser(new UserEntity(phone, pwd));
        if (dbUserEntity != null){
            System.out.println("登陆成功");
            return;
        }else{
            System.out.println("登录失败 密码不正确 错误次数"+i );
        }
        }
    }
}
