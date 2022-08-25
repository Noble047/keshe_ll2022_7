package com.keshe_ll.keshe_ll.DAO;

import com.keshe_ll.keshe_ll.entity.Course;
import com.keshe_ll.keshe_ll.entity.Student;
import com.keshe_ll.keshe_ll.pojo.SelectedOfStudent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDAO {
    /**
     * 所有课程列表*/
    @Select("select * from course order by Cno")
    List<Course> getAllCourse();

    /**
     * 根据课程号查询课程
     */
    @Select("select * from course where Cno=#{cno}")
    Course getCourseById(Integer cno);

    /**
     * 根据学号查询学生
     */
    @Select("select * from student where Sno=#{sno}")
    Student getStudentById(Integer sno);

    /**
     * 插入选课信息
     */
    @Insert("insert into stc(Sno,Cno,Sname,Cname) values(#{sno},#{cno},#{sname},#{cname})")
    int selectCourse(Integer sno,Integer cno,String sname,String cname);

    /**
     * 根据sno查询cno
     */
    @Select("select Cno from stc where Sno=#{sno}")
    List<Integer> getCnoFromStc(Integer sno);

    /**
     * 根据sno查询cno cname
     */
    @Select("select stc.Cno as Cno,stc.Cname,Score from stc,course where Sno=#{sno} and stc.Cno=course.Cno")
    List<SelectedOfStudent> getYourCourse(Integer sno);

    /**
     * 根据sno cno删除选课信息
     */
    @Delete("delete from stc where Sno=#{sno} and Cno=#{cno}")
    void deletestc(Integer sno,Integer cno);

    /**
     * 模糊查询
     */
    @Select("select * from course where Cname like #{str}")
    List<Course> searchCourse(String str);
}
