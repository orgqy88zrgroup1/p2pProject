package com.aaa.sb.controller;

import com.aaa.sb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * className:UserController
 * discription:
 * author:wzb
 * createTime:2018-11-30 16:17
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("toAdd")
    public String toAdd(){
        System.out.println("hello..........");
        return "user/add";
    }

    @RequestMapping("toUpd")
    public String toUpd(){
        System.out.println("hello..........");
        return "user/update";
    }

    @ResponseBody
    @RequestMapping("borrowList")
    public Object toBorrowList(@RequestBody Map map){
        Map mmp = new HashMap();
        mmp.put("data",userService.getList(map));
        mmp.put("total",userService.getcount(map));
        System.out.println(userService.getList(map));
        return mmp;
    }

    @RequestMapping("toPage")
    public String toPage(){
        return "homePage/userindex";
    }

    @RequestMapping("toIndex")
    public String toIndex(){
        return "user/index";
    }

    @RequestMapping("toBorrow")
    public String toBorrow(){
        return "user/borrow";
    }

    @RequestMapping("toLend")
    public String toLend(){
        return "user/lend";
    }

    @RequestMapping("toLendTo")
    public String toLendTo(Model model,@RequestBody Map map){
        System.out.println(map);
        model.addAttribute("userlist",map);
        model.addAttribute("total",userService.getcount(map));
        return "user/lendto";
    }

    @RequestMapping("toPersonalcenter")
    public String toPersonalcenter(){
        return "user/personalcenter";
    }

    @RequestMapping("toProperty")
    public String toProperty(){
        return "user/property";
    }

    @ResponseBody
    @RequestMapping("toAccountList")
    public Object toPropertyList(@RequestBody Map map, HttpServletRequest request){
        HttpSession user=request.getSession();
        Integer userID = Integer.valueOf(user.getAttribute("userID")+"");
        System.out.println(map);
        System.out.println(userService.getAccountList(map));
        return userService.getAccountList(map);
    }

    @ResponseBody
    @RequestMapping("toRepayInfo")
    public Object toRepayInfo(@RequestBody Map map, HttpServletRequest request){
        HttpSession user=request.getSession();
        Integer userID = Integer.valueOf(user.getAttribute("userID")+"");
        System.out.println(map);
        System.out.println(userService.getRepayInfo(map));
        return userService.getRepayInfo(map);
    }

    @ResponseBody
    @RequestMapping("torepayment")
    public Object toRepayment(@RequestBody Map map, HttpServletRequest request){
        HttpSession user=request.getSession();
        Integer userID = Integer.valueOf(user.getAttribute("userID")+"");
        System.out.println(map);
        userService.getRepayment(map);
        return userService.getAccountList(map);
    }

    @ResponseBody
    @RequestMapping("tocash")
    public Object toCash(@RequestBody Map map, HttpServletRequest request){
        HttpSession user=request.getSession();
        Integer userID = Integer.valueOf(user.getAttribute("userID")+"");
        System.out.println(map);
        userService.getCash(map);
        return userService.getAccountList(map);
    }

    @ResponseBody
    @RequestMapping("torecharge")
    public Object toRecharge(@RequestBody Map map, HttpServletRequest request){
        HttpSession user=request.getSession();
        Integer userID = Integer.valueOf(user.getAttribute("userID")+"");
        System.out.println(map);
        userService.getRecharge(map);
        return userService.getAccountList(map);
    }

    @RequestMapping("toInfo")
    public String toInfo(){
        return "user/info";
    }

    @RequestMapping("toUserInfo")
    public String toPerson(){
        return "userInfo/userInfo";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "user/login";
    }

    /*@RequestMapping("test")
    public String test(Model model){
        model.addAttribute("name","厉害了！");

        return "user/test";
    }*/

    @RequestMapping("login")
    public String login(String username, String password, Model model){

        /**
         * 使用shiro编写认证操作
         */
        System.out.println(username+"...1111..."+password);
        //1 获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //3 执行登录方法
        try {
            //执行过程中把token传递给shiro，然后shiro执行登录操作,根据有无异常判断登录成功还是失败
            subject.login(token);
            model.addAttribute("name",username);
            model.addAttribute("msg","登录成功！");
            return "user/test";

        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败
            //返回 UnknownAccountException 此异常，用户名不存在
            model.addAttribute("msg","用户名不存在！");

            return "user/login";
        } catch (IncorrectCredentialsException e){
            //e.printStackTrace();
            //登录失败
            //返回 IncorrectCredentialsException 此异常，密码错误
            model.addAttribute("msg","密码错误！");

            return "user/login";
        }


    }

    @RequestMapping("unAuthorization")
    public String unAuthorization(){


        return "user/unAut";
    }


}
