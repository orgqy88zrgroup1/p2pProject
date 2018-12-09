package com.aaa.sb.controller;

import com.aaa.sb.service.EmpService;
import com.aaa.sb.util.AESUtil;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private EmpService empService;
    @Autowired
    private HttpSession session;

    /**
     * 查询分页
     * 模拟测试用 @RequestParam
     * http://localhost:8888/sb/emp/page?start=0&end=20
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public Object list(@RequestParam Map map){
        Map resultMap = new HashMap();
        resultMap.put("pageData",empService.getPageByParam(map));
        resultMap.put("total",empService.getPageCount(map));
        return resultMap;
    }


    /**
     * 添加页面
     * @return
     */
    /*@RequestMapping("toAdd")
    public String toAdd(){
        System.out.println("hello..........");
        return "emp/add";
    }*/

    /**
     * 更新
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/upd")
    public Object upd(@RequestBody Map map){
        return empService.upd(map);
    }

    /**
     * 删除
     * @param
     * @return
     */

    @ResponseBody
    @RequestMapping("/del")
    //RequestMapping("/del/{id}")
    //del(@PathVariable Integer id)
    public Object del(int id){
        return empService.del(id);
    }

    /**
     * 批量删除
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchDel")
    public Object batchDel(String ids){

        return empService.batchDelete(ids);

    }

    /**
     * 添加
     * @param map
     * @return
     * RequestBody 该方法接收的数据为json对象
     * ResponseBody 改方法返回值为json对象
     */
    @ResponseBody
    @RequestMapping("/add")
    public Object add(@RequestBody Map map){
        return empService.add(map);
    }

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
     * @return
     */
    @ResponseBody
    @RequestMapping("login")
    public Object login(String username, String password1,@RequestParam Map map, Model model) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        /**
         * 使用shiro编写认证操作
         */
        System.out.println(username+"...1111..."+password1);
        //加密
        String password = AESUtil.getInstance().encrypt(password1);
        //1 获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //3 执行登录方法
        try {
            //执行过程中把token传递给shiro，然后shiro执行登录操作,根据有无异常判断登录成功还是失败
            subject.login(token);
            session.setAttribute("username",username);
            //model.addAttribute("name",username);
            //model.addAttribute("msg","登录成功！");
            map.put("msg","登录成功！");
            return map;


        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败
            //返回 UnknownAccountException 此异常，用户名不存在

            //model.addAttribute("errorUnm","用户名不存在！");
            map.put("errorUnm","用户名不存在！");
            return map;
        } catch (IncorrectCredentialsException e){
            //e.printStackTrace();
            //登录失败
            //返回 IncorrectCredentialsException 此异常，密码错误
            map.put("errorPwd","密码错误！");
            //model.addAttribute("errorPwd","密码错误！");
            return map;
        }

    }

    /**
     * 登录成功跳转主页
     * @param model
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(Model model){
        model.addAttribute("username",session.getAttribute("username"));
        return "homePage/index";

    }


    /**
     * 未授权登录跳转页面
     * @return
     */
    /*@RequestMapping("unAuthorization")
    public String unAuthorization(){

        return "emp/unAut";

    }*/


}
