package com.aaa.sb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * className:EmpController
 * discription:
 * author:wzb
 * createTime:2018-12-08 21:56
 */
@Controller
@RequestMapping("emp")
public class EmpController {

    /**
     * 查询分页
     * 模拟测试用 @RequestParam
     * http://localhost:8888/sb/emp/page?start=0&end=20
     * @param map
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/page")
    public Object list(@RequestParam Map map){
        Map resultMap = new HashMap();
        resultMap.put("pageData",tbUserService.getPageByParam(map));
        resultMap.put("total",tbUserService.getPageCount(map));
        return resultMap;
    }*/


    /**
     * 添加页面
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd(){
        System.out.println("hello..........");
        return "emp/add";
    }

    /**
     * 更新
     * @param map
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/upd")
    public Object upd(@RequestBody Map map){
        return tbUserService.upd(map);
    }*/

    /**
     * 删除
     * @param
     * @return
     */
    /*
    @ResponseBody
    @RequestMapping("/del")
    //RequestMapping("/del/{id}")
    //del(@PathVariable Integer id)
    public Object del(int id){
        return tbUserService.del(id);
    }*/

    /**
     * 批量删除
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/batchDel")
    public Object batchDel(String ids){

        return tbUserService.delMany(ids);

    }*/

    /**
     * 添加
     * @param map
     * @return
     * RequestBody 该方法接收的数据为json对象
     * ResponseBody 改方法返回值为json对象
     */
    /*@ResponseBody
    @RequestMapping("/add")
    public Object add(@RequestBody Map map){
        return tbUserService.add(map);
    }*/

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "loginPage/login";
    }

    /**
     * 登录页面
     * @param username
     * @param password1
     * @param model
     * @return
     */
    @RequestMapping("login")
    public String login(String username, String password1, Model model){

        /**
         * 使用shiro编写认证操作
         */
        System.out.println(username+"...1111..."+password1);
        //1 获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password1);
        //3 执行登录方法
        try {
            //执行过程中把token传递给shiro，然后shiro执行登录操作,根据有无异常判断登录成功还是失败
            subject.login(token);
            model.addAttribute("name",username);
            model.addAttribute("msg","登录成功！");
            return "emp/empList";

        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败
            //返回 UnknownAccountException 此异常，用户名不存在
            model.addAttribute("msg","用户名不存在！");

            return "loginPage/login";
        } catch (IncorrectCredentialsException e){
            //e.printStackTrace();
            //登录失败
            //返回 IncorrectCredentialsException 此异常，密码错误
            model.addAttribute("msg","密码错误！");

            return "loginPage/login";
        }

    }


    /**
     * 未授权登录跳转页面
     * @return
     */
    @RequestMapping("unAuthorization")
    public String unAuthorization(){

        return "emp/unAut";

    }


}
