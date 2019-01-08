package com.aaa.sb.controller;

import com.aaa.sb.service.UserService;
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
     * 跳转首页
     * @param map
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex(@RequestParam Map map, HttpSession session){
        System.out.println("map："+map);
        userService.setSession(map,session);
        return "homePage/userindex";
    }

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

    /**
     * 检查电话号是否重复
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkPhone")
    public int checkPhone(String phone){
        System.out.println("前台接收的电话号是："+phone);
        //如果电话号存在就返回0 否则就返回1
        int i = userService.checkPhone(phone);
        if (i==1){
            //说明不重复
            return 1;
        }
        return 0;
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

    @RequestMapping("Index")
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
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userID",userID);
        System.out.println(map);
        System.out.println(userService.getAccountList(map));
        return userService.getAccountList(map);
    }

    @ResponseBody
    @RequestMapping("toRepayInfo")
    public Object toRepayInfo(@RequestBody Map map, HttpServletRequest request) {
        HttpSession user=request.getSession();
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userID",userID);
        System.out.println(map);
        System.out.println(userService.getRepayInfo(map));
        return userService.getRepayInfo(map);
    }

    @ResponseBody
    @RequestMapping("torepayment")
    public Object toRepayment(@RequestBody Map map, HttpServletRequest request) {
        HttpSession user=request.getSession();
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userID",userID);
        System.out.println(map);
        userService.getRepayment(map);
        return userService.getAccountList(map);
    }

    @ResponseBody
    @RequestMapping("tocash")
    public Object toCash(@RequestBody Map map, HttpServletRequest request) {
        HttpSession user=request.getSession();
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userID",userID);
        System.out.println(map);
        userService.getCash(map);
        return userService.getAccountList(map);
    }

    @ResponseBody
    @RequestMapping("torecharge")
    public Object toRecharge(@RequestBody Map map, HttpServletRequest request){
        HttpSession user=request.getSession();
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userID",userID);
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

}
