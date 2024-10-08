package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @ResponseBody
    @RequestMapping("/hello")
    public String say(){
        return "Spring Boot";
    }

    @ResponseBody
    @RequestMapping("/data")
    public String getData(){
        return alphaService.find();
    }
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //请求行数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        //消息头数据
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+":"+value);
        }
        System.out.println(request.getParameter("code"));
        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = response.getWriter();) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //GET请求
    // /student?current=1&limit=20
    @RequestMapping(path="/students",method = RequestMethod.GET)//限制只能使用get请求
    @ResponseBody
    public String getStudents(
            @RequestParam(name="current",required = false,defaultValue = "1") int current,
            @RequestParam(name="limit",required = false,defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
    // /student/123    参数是路径的一部分
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }
    //POST请求
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }
    //响应html数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","ldx");
        mav.addObject("age",20);
        mav.setViewName("/demo/view");//实际是templates/demo/view.html
        return mav;
    }
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","CSU");
        model.addAttribute("age",80);
        return "/demo/view";
    }
    // 响应json请求（异步请求）
    // java对象 ->json字符串 ->js对象
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody //加上被认为返回的是字符串
    public Map<String, Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("salary",18000);
        emp.put("age",24);
        return emp;
    }
    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody //加上被认为返回的是字符串
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","ldx");
        emp.put("salary",18000);
        emp.put("age",24);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","ldx1");
        emp.put("salary",38000);
        emp.put("age",34);
        list.add(emp);
        return list;
    }
    //cookie示例
    @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效范围
        cookie.setPath("/community/alpha");
        cookie.setMaxAge(60*10);
        response.addCookie(cookie);
        return "set cookie";
    }
    @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "get cookie";
    }

    //session示例
    @RequestMapping(path = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","Test");
        return "set session";
    }
    @RequestMapping(path = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }
}






























