package com.keshe_ll.keshe_ll.controller;

import com.keshe_ll.keshe_ll.DAO.TeacherDAO;
import com.keshe_ll.keshe_ll.entity.Course;
import com.keshe_ll.keshe_ll.entity.Student;
import com.keshe_ll.keshe_ll.entity.Teacher;
import com.keshe_ll.keshe_ll.pojo.SelectOfCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    TeacherDAO teacherDAO;

    @RequestMapping("/index")
    public String index(HttpSession session){
        session.setAttribute("lastPage","index");
        session.setAttribute("localPage","index");

        return "index";
    }


    /**
     * 学生注册*/
    @RequestMapping("/registerrequest")
    public String register(String Saccount,String Spassword,String confirm,String Sex,String Classs,String name){
        if (Saccount.isEmpty() && Spassword.isEmpty() && confirm.isEmpty() && Sex.isEmpty() && Classs.isEmpty() && name.isEmpty()){
            return "register";
        }
        if (!confirm.equals(Spassword)){throw new RuntimeException("密码不一致!");}
        Student student = new Student();
        student.setSaccount(Saccount);
        student.setSpassword(Spassword);
        student.setSname(name);
        student.setSex(Sex);
        student.setClasss(Classs);
        teacherDAO.addStudentByTeacher(student);
        return "index";
    }

    /**
     * 学生登录*/
    @RequestMapping("/studentlogin")
    public String studentLogin(String username, String password, HttpSession session,Model model){
        if (username.isEmpty() || password.isEmpty()){
            model.addAttribute("msg","用户名或密码不能为空");
            return "index";
        }
        String studentPassword = null;
        int sno = 0;
        try {
            Student student = teacherDAO.getStudentsPassword(username);
            studentPassword = student.getSpassword();
            sno = student.getSno();
        }catch (Exception e){
            System.out.println(e);
        }
        if (studentPassword == null){
            model.addAttribute("msg","用户名不存在");
            return "index";
        }

        if (!password.equals(studentPassword)){
            model.addAttribute("msg","密码不正确");
            return "index";
        }
        session.setAttribute("id","student");
        session.setAttribute("user",username);
        session.setAttribute("sno",sno);

        session.setAttribute("lastPage",session.getAttribute("localPage"));
        session.setAttribute("localPage","studentIndex");
        return "studentIndex";
    }

    /**
     * 教师登陆*/
    @RequestMapping("/teacherloginRequest")
    public String teacherLogin(String username,String password,HttpSession session,Model model){
        if (username.isEmpty() || password.isEmpty()){
            model.addAttribute("msg","用户名或密码不能为空");
            return "index";
        }
        String teacherPassword = "";
        int tno = 0;
        try {
            Teacher teacher = teacherDAO.getTeachersPassword(username);
            teacherPassword = teacher.getTpassword();
            tno = teacher.getTno();
        }catch (Exception e){
            System.out.println(e);
        }
        if (teacherPassword == null){
            model.addAttribute("msg","用户名不存在");
            return "index";
        }

        if (!password.equals(teacherPassword)){
            model.addAttribute("msg","密码不正确");
            return "index";
        }
        session.setAttribute("id","teacher");
        session.setAttribute("user",username);
        session.setAttribute("tno",tno);

        session.setAttribute("lastPage",session.getAttribute("localPage"));
        session.setAttribute("localPage","teacherIndex");
        return "teacherIndex";
    }

    /**
     * 修改密码界面
     */
    @RequestMapping("/changePassword")
    public String changePasswordPage(String id,Integer no,Model model,HttpSession session){
        try{
            if (id.equals("teacher")){
                Teacher teacher = teacherDAO.getTeacherById(no);
                model.addAttribute("username",teacher.getTname());
                model.addAttribute("password",teacher.getTpassword());
                model.addAttribute("userno",teacher.getTno());
                model.addAttribute("id","teacher");
            }else if (id.equals("student")){
                Student student = teacherDAO.getStudentById(no);
                model.addAttribute("username",student.getSname());
                model.addAttribute("password",student.getSpassword());
                model.addAttribute("userno",student.getSno());
                model.addAttribute("id","student");
            }
        }catch (Exception e){
            System.out.println(e);
        }

        session.setAttribute("lastPage",session.getAttribute("localPage"));
        session.setAttribute("localPage","changePassword");

        return "changePassword";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/changePasswordReq")
    public String changePassword(String id,String old,String neww,String confirm,Integer userno,String username,String oldpassword,Model model){
        if (id.equals("teacher")){
            if (!old.equals(oldpassword)){
                model.addAttribute("msg","与原密码不符");
                model.addAttribute("username",username);
                model.addAttribute("password",oldpassword);
                model.addAttribute("userno",userno);
                model.addAttribute("id",id);
                return "changePassword";
            }
            if (!neww.equals(confirm)){
                model.addAttribute("msg","密码不一致");
                model.addAttribute("username",username);
                model.addAttribute("password",oldpassword);
                model.addAttribute("userno",userno);
                model.addAttribute("id",id);
                return "changePassword";
            }
            teacherDAO.modifyTeacherPassword(neww,userno);
            model.addAttribute("msg","修改成功");
            model.addAttribute("username",username);
            model.addAttribute("password",neww);
            model.addAttribute("userno",userno);
            model.addAttribute("id",id);
            return "changePassword";

        }else if (id.equals("student")){
            if (!old.equals(oldpassword)){
                model.addAttribute("msg","与原密码不符");
                model.addAttribute("username",username);
                model.addAttribute("password",oldpassword);
                model.addAttribute("userno",userno);
                model.addAttribute("id",id);
                return "changePassword";
            }
            if (!neww.equals(confirm)){
                model.addAttribute("msg","密码不一致");
                model.addAttribute("username",username);
                model.addAttribute("password",oldpassword);
                model.addAttribute("userno",userno);
                model.addAttribute("id",id);
                return "changePassword";
            }
            teacherDAO.modifyStudentPassword(neww,userno);
            model.addAttribute("msg","修改成功");
            model.addAttribute("username",username);
            model.addAttribute("password",neww);
            model.addAttribute("userno",userno);
            model.addAttribute("id",id);
            return "changePassword";
        }

        return "changePassword";
    }

    /**
     * 学生管理页面*/
    @RequestMapping("/studentManage")
    public String studentManage(Model model){
        List<Student> allStudent = teacherDAO.getAllStudent();
        model.addAttribute("studentList",allStudent);
        return "studentManage";
    }

    /**
     * 删除学生*/
    @RequestMapping("/deleteStudent")
    public String deleteStudent(Integer delno,Model model){
        try{
            teacherDAO.deleteStudent(delno);
        }catch (Exception e){
            System.out.println("删除异常");
        }

        List<Student> allStudent = teacherDAO.getAllStudent();
        model.addAttribute("studentList",allStudent);
        return "studentManage";
    }

    /**
     * 添加学生*/
    @RequestMapping("/addStudent")
    public String addStudent(Integer Sno,String Saccount,String Spassword,String Sex,String Classs,String name,Model model){
        Student student = new Student();
        student.setSaccount(Saccount);
        student.setSpassword(Spassword);
        student.setSname(name);
        student.setSex(Sex);
        student.setClasss(Classs);
        student.setSno(Sno);
        teacherDAO.addStudentByTeacher(student);

        List<Student> allStudent = teacherDAO.getAllStudent();
        model.addAttribute("studentList",allStudent);
        return "studentManage";
    }

    /**
     * 添加课程
     */
    @RequestMapping("/addCourseReq")
    public String addCourse(Integer cno,String cname,String meta,String grade,Model model){
        Course course = new Course();
        course.setCname(cname);
        course.setMeta(meta);
        course.setGrade(Integer.parseInt(grade));
        course.setCno(cno);
        try {
            teacherDAO.addNewCourse(course);
        }catch (Exception e){
            System.out.println(e);
        }

        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);
        return "courseManage";
    }

    /**
     * 课程管理页面
     */
    @RequestMapping("courseManage")
    public String courseManage(Model model){
        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);
        return "courseManage";
    }

    /**
     * 删除课程
     */
    @RequestMapping("/deleteCourse")
    public String deleteCourse(Integer delno,Model model){
        try{
            teacherDAO.deleteCourse(delno);
        }catch (Exception e){
            System.out.println("删除异常");
        }

        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);
        return "courseManage";
    }

    /**
     * 选课管理界面
     */
    @RequestMapping("/teacherIndex")
    public String teacherIndex(Model model,HttpSession session){
        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);

        session.setAttribute("lastPage",session.getAttribute("localPage"));
        session.setAttribute("localPage","teacherIndex");
        return "teacherIndex";
    }

    private int CnoFromSelected = 0;
    private int SnoFromSelected = 0;

    @RequestMapping("/getSelected")
    public String getSelected(Integer Cno,Model model,HttpSession session){
        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);

        List<SelectOfCourse> soc = teacherDAO.getSelectOfCourse(Cno);
        model.addAttribute("soc",soc);
        session.setAttribute("selectedCno",Cno);

        CnoFromSelected = Cno;
        return "teacherIndex";
    }

    @RequestMapping("/setScore")
    public String setScore(Integer sno,Model model,Integer cno,HttpSession session){
        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);

        List<SelectOfCourse> soc = teacherDAO.getSelectOfCourse(cno);
        model.addAttribute("soc",soc);

        Student student = teacherDAO.getStudentById(sno);
        model.addAttribute("msg","已选择"+student.getSname()+"进行打分");
        session.setAttribute("selectedSno",sno);
        SnoFromSelected = sno;

        return "teacherIndex";
    }

    @RequestMapping("/setScoreReq")
    public String setScoreReq(Integer score,Model model,Integer cno){
        List<Course> courses = teacherDAO.getAllCourse();
        model.addAttribute("courses",courses);
        teacherDAO.setScore(SnoFromSelected,CnoFromSelected,score);

        List<SelectOfCourse> soc = teacherDAO.getSelectOfCourse(cno);
        model.addAttribute("soc",soc);
        return "teacherIndex";
    }


}
