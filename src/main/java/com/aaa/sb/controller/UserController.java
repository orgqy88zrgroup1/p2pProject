package com.aaa.sb.controller;

import com.aaa.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * className:UserController
 * discription:
 * author:gwd
 * createTime:2018-12-10 20:39
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 判断用户名是否重复
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkUser")
    public int checkUser(String userName){
        System.out.println(userName);
        List<Map> userList = userService.getUserList();
        if(userList!=null&&userList.size()>0&&userName!=null&&userName!=""&&userName.length()>0) {
            for (Map map : userList) {
                //从数据库里取值 大写
                if ((map.get("USERNAME") + "").equals(userName)) {
                    //用户名已存在
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * 判断密码是否正确
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkPwd")
    public int checkPwd(String userName,String password){
        //System.out.println(userName);
        int i = userService.checkPwd(userName,password);
        if(i==1){
            // 说明密码正确
            return 1;
        }
        return 0;
    }

    /**
     * 用户注册时添加用户信息
     * @param map
     * @return
     */
    @RequestMapping("/add")
    public String  userAdd(@RequestParam Map map){
        System.out.println(map);
        int i = userService.addUser(map);
        if(i>0){
            return "redirect:/login/login";
        }
        return null;
    }

}
