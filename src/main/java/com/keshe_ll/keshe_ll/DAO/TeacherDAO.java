package com.keshe_ll.keshe_ll.DAO;


import com.keshe_ll.keshe_ll.entity.Course;
import com.keshe_ll.keshe_ll.entity.Student;
import com.keshe_ll.keshe_ll.entity.Teacher;
import com.keshe_ll.keshe_ll.pojo.SelectOfCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherDAO {

    /**
     * 根据教师工号获取对应教师*/
    @Select("select * from teacher where Tname=#{tname}")
    Teacher getTeachersPassword(String tname);

    /**
     * 根据学生名获取对应学生
     */
    @Select("select * from student where Saccount=#{sname}")
    Student getStudentsPassword(String sname);

    /**
     * 根据学号获取学生
     */
    @Select("select * from student where Sno=#{sno}")
    Student getStudentById(Integer sno);

    /**
     * 根据工号获取教师
     */
    @Select("select * from teacher where Tno=#{tno}")
    Teacher getTeacherById(Integer tno);

    /**
     * 修改密码教师
     */
    @Update("update teacher set Tpassword=#{Tpassword} where Tno=#{Tno}")
    void modifyTeacherPassword(String Tpassword,Integer Tno);

    /**
     * 修改密码学生
     */
    @Update("update student set Spassword=#{Spassword} where Sno=#{Sno}")
    void modifyStudentPassword(String Spassword,Integer Sno);

    /**
     * 添加课程*/
    @Insert("insert into course(Cno,Cname,Meta,Grade) values(#{Cno},#{Cname},#{Meta},#{Grade})")
    int addNewCourse(Course course);

    /**
     * 所有课程列表*/
    @Select("select * from course order by Cno")
    List<Course> getAllCourse();

    /**
     * 删除课程*/
    @Delete("delete from course where Cno=#{Cno}")
    void deleteCourse(Integer Cno);

    /**
     * 添加学生*/
    @Insert("insert into student(Sno,Sname,Sex,Classs,Saccount,Spassword) values(#{Sno},#{Sname},#{Sex},#{Classs},#{Saccount},#{Spassword})")
    int addStudentByTeacher(Student student);

    /**
     * 删除学生*/
    @Delete("delete from student where Sno=#{Sno}")
    void deleteStudent(Integer Sno);

    /**
     * 获取所有学生*/
    @Select("select * from student order by Sno")
    List<Student> getAllStudent();

    /**
     * 获取某门课的所有选课信息
     */
    @Select("select Sno,Sname,Score from stc where Cno=#{cno}")
    List<SelectOfCourse> getSelectOfCourse(Integer cno);

    /**
     * 打分*/
    @Update("update stc set Score=#{score} where Sno=#{sno} and Cno=#{cno}")
    void setScore(Integer sno,Integer cno,Integer score);
}
