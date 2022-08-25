package com.keshe_ll.keshe_ll.controller;

import com.keshe_ll.keshe_ll.DAO.StudentDAO;
import com.keshe_ll.keshe_ll.entity.Course;
import com.keshe_ll.keshe_ll.entity.Student;
import com.keshe_ll.keshe_ll.pojo.SelectOfCourse;
import com.keshe_ll.keshe_ll.pojo.SelectedOfStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    StudentDAO studentDAO;

    @RequestMapping("/studentIndex")
    public String studentIndex(Model model, HttpSession session){
        List<Course> courses = studentDAO.getAllCourse();
        model.addAttribute("courses",courses);

        session.setAttribute("lastPage",session.getAttribute("localPage"));
        session.setAttribute("localPage","studentIndex");

        return "studentIndex";
    }

    @RequestMapping("/selectCourse")
    public String selectCourse(Integer sno,Integer cno, Model model){
        List<Course> courses = studentDAO.getAllCourse();
        model.addAttribute("courses",courses);
        Student student = studentDAO.getStudentById(sno);
        Course course = studentDAO.getCourseById(cno);
        List<Integer> cnoList = studentDAO.getCnoFromStc(sno);
        if (cnoList.contains(cno)){
            model.addAttribute("msg","课程已被选择");
            List<SelectedOfStudent> selectedOfStudents = studentDAO.getYourCourse(sno);
            model.addAttribute("ss",selectedOfStudents);
            return "studentIndex";
        }
        studentDAO.selectCourse(sno,cno,student.getSname(),course.getCname());
        model.addAttribute("msg","选课成功");
        List<SelectedOfStudent> selectedOfStudents = studentDAO.getYourCourse(sno);
        model.addAttribute("ss",selectedOfStudents);

        return "studentIndex";
    }

    @RequestMapping("/getYourCourses")
    public String getYourCourses(Model model,Integer sno){
        List<Course> courses = studentDAO.getAllCourse();
        model.addAttribute("courses",courses);

        List<SelectedOfStudent> selectedOfStudents = studentDAO.getYourCourse(sno);
        model.addAttribute("ss",selectedOfStudents);
        return "studentIndex";
    }

    @RequestMapping("/deleteSelection")
    public String deletestc(Model model,Integer sno,Integer cno){
        List<Course> courses = studentDAO.getAllCourse();
        model.addAttribute("courses",courses);
        studentDAO.deletestc(sno, cno);
        List<SelectedOfStudent> selectedOfStudents = studentDAO.getYourCourse(sno);
        model.addAttribute("ss",selectedOfStudents);
        model.addAttribute("msg","退选成功");
        return "studentIndex";

    }

    @RequestMapping("logOut")
    public String logOut(HttpSession session){
        session.setAttribute("user",null);
        return "index";
    }

    @RequestMapping("/search")
    public String search(Model model,String str,Integer sno){
        List<Course> searchResult = studentDAO.searchCourse("%"+str+"%");
        List<SelectedOfStudent> selectedOfStudents = studentDAO.getYourCourse(sno);
        model.addAttribute("ss",selectedOfStudents);
        model.addAttribute("courses",searchResult);

        return "studentIndex";
    }
}
