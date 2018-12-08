package com.aaa.sb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * className:UserController
 * discription:
 * author:wzb
 * createTime:2018-11-30 16:17
 */
@Controller
@RequestMapping("user")
public class UserController {


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
