package cmo.mayikt.Service;

import cmo.mayikt.Dao.StudentDao;
import cmo.mayikt.entity.StudentEntity;

import java.util.ArrayList;

public class StudentService {
    /**
     * 通过 service层 来调用我们 dao 层的代码
     */
    // new出 dao层 学生对象
    private StudentDao studentDao=new StudentDao();
    // 1.查询所有学生的信息
    public ArrayList<StudentEntity> allStudent(){
        // 通过业务逻辑层 调用dao层的代码
        ArrayList<StudentEntity> StudentEntities=studentDao.Allstudent();
        return StudentEntities;
    }
    // 2.根据id查询学生信息
    public StudentEntity getByIdStudent(Long StuId){
        return studentDao.getByIdStudent(StuId);
    }
    // 3.新增学生信息 ----insert into
    public int insertStudent(StudentEntity stu){
        return studentDao.insertStudent(stu);
    }
    //4.根据主键id修改学生信息
    public int updateStudent(StudentEntity stu){
        return studentDao.updateStudent(stu);
    }
    //5.根据主键id删除学生信息
    public int delStudent(Long id){
        return studentDao.delStudent(id);
    }

}
