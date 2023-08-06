package cmo.mayikt.test;

import cmo.mayikt.Service.StudentService;
import cmo.mayikt.entity.StudentEntity;

import java.util.ArrayList;

public class StudentTest01 {
    public static void main(String[] args) {
        StudentService studentService=new StudentService();
        //1.
//       ArrayList<StudentEntity> StudentEntities =studentService.allStudent();
//       for(StudentEntity stu: StudentEntities){
//           System.out.println(stu);
//       }
        //2.
//        StudentEntity student = studentService.getByIdStudent(1L);
//        System.out.println(student);
        //3.
//        StudentEntity studentEntity = new StudentEntity("张乐天", 22, "山东省济南市");
//        int result=studentService.insertStudent(studentEntity);
//        if(result>0){
//            System.out.println("数据插入成功");
//        }else {
//            System.out.println("数据插入失败");
//        }
//        updatetestStudent();
          delestStudentTest();
    }
    //4.
//    public static void updatetestStudent(){
//        StudentService studentService = new StudentService();
//        // 1.查询原来学生的信息
//        Long stuId=1l;
//         StudentEntity studentEntity = studentService.getByIdStudent(stuId);
//         if(studentEntity==null){
//             return;
//         }
//         //2. 修改学生的名称
//        studentEntity.setName("王麻子");
//        studentService.updateStudent(studentEntity);
//    }
    //5.
    public static void delestStudentTest(){
        StudentService studentService = new StudentService();
        //1. 要删除学生的信息
        Long id=3l;
        StudentEntity student = studentService.getByIdStudent(id);
        if(student==null){
            System.out.println("数据库中没有该数据，无法删除");
            return;
        }
        int result=studentService.delStudent(id);
        if(result>0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }
}
