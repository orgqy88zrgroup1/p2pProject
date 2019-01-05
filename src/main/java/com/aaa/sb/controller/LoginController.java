package com.aaa.sb.controller;

import com.aaa.sb.util.PhoneMsgUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * className:LoginController
 * discription:
 * author:gwd
 * createTime:2018-12-10 11:35
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * 跳转至
     * @return
     */
    @RequestMapping("/registe")
    public String toLogin(){
        return "registe";
    }

    /**
     * 根据电话号码获取验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping("/check")
    public int checkNum(String phone){
        int code = PhoneMsgUtil.getModelMsg(phone);
        return code;
    }

    /**
     * 跳转首页
     * @return
     */
    @RequestMapping("/login")
    public String toHomePage(){
        return "login";
    }


}
