package cmo.mayikt.test;

import cmo.mayikt.Service.StudentService;
import cmo.mayikt.entity.StudentEntity;

import java.util.ArrayList;
import java.util.Scanner;

public class IndexText {
    /**
     * 首先 将业务逻辑层对象 定义成全局的
     *
     */
    private static StudentService studentService=new StudentService();
    public static void main(String[] args) {

        mainMenu();

    }
    /**
     * 1.  定义主菜单程序入口
     */
    public static void mainMenu() {
        while (true) {
            // 1. 提示语
            System.out.println("欢迎来到我们每特教育蚂蚁课堂学生管理系统");
            System.out.println("1.查询所有的学生信息");
            System.out.println("2.根据学生id查询学生信息");
            System.out.println("3.新增学生信息");
            System.out.println("4.根据学生id修改学生信息");
            System.out.println("5.根据学生id删除学生信息");
            System.out.println("请选择对应序号:");
            // 2. 选择 序号
            Scanner scanner = new Scanner(System.in);
            int result = scanner.nextInt();
            switch (result) {
                case 1:
                    showAllStudent();
                    break;
                case 2:
                    findByIdStudent();
                    break;
                case 3:
                    insertStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    delIdStudent();
                    break;

            }
        }
    }

    public static void showAllStudent(){
//        studentService = new StudentService();
        ArrayList<StudentEntity> studentEntities = studentService.allStudent();
        for(StudentEntity stu :studentEntities ){
            System.out.println(stu);
        }
    }

    public static void findByIdStudent(){
        System.out.println("请输入学生的id ：");
        Scanner scanner = new Scanner(System.in);
        Long stuId = scanner.nextLong();
        StudentEntity student = studentService.getByIdStudent(stuId);
        if(student == null){
            System.out.println("学生信息不存在");
        }else{
            System.out.println("学生信息："+student+"");
        }
    }

    public static void delIdStudent() {
        System.out.println("请输入需要删除学生的id ：");
        Scanner scanner = new Scanner(System.in);
        Long stuId = scanner.nextLong();
        int result = studentService.delStudent(stuId);
        if(result > 0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

    public static void insertStudent() {
        System.out.println("请输入需要插入学生的姓名");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("请输入需要插入学生的年龄");
        int age = scanner.nextInt();
        System.out.println("请输入需要插入学生的地址");
        scanner.nextLine();
        String address = scanner.nextLine();
        StudentEntity studentEntity = new StudentEntity(name,age,address);
        int result = studentService.insertStudent(studentEntity);
        if(result > 0){
            System.out.println("学生信息插入成功");
        }else{
            System.out.println("学生信息插入失败");
        }
    }

    public static void updateStudent() {
        System.out.println("请输入需要修改学生的id");
        Scanner scanner = new Scanner(System.in);
        Long stuId = scanner.nextLong();
        // 先根据学生的id 查询学生的信息
        StudentEntity student = studentService.getByIdStudent(stuId);
        if (student == null){
            System.out.println("查询不到该学生信息");
            return;
        }
        System.out.println("请输入需要修改学生的姓名");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("请输入需要修改学生的年龄");
        int age = scanner.nextInt();
        System.out.println("请输入需要修改学生的地址");
        scanner.nextLine();
        String address = scanner.nextLine();
        StudentEntity studentEntity = new StudentEntity(stuId,name,age,address);
        int result = studentService.updateStudent(studentEntity);
        if(result > 1){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
    }

}
