package com.aaa.sb.controller;

import com.aaa.sb.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * className:BorrowController
 * discription:
 * author:zhangran
 * createTime:2018-12-26 15:25
 */
@Controller
@RequestMapping("borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;

    @ResponseBody
    @RequestMapping("toBorrow")
    public Object toBorrow(@RequestBody Map map, HttpServletRequest request) {
        HttpSession user=request.getSession();
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userId",userID);
        System.out.println(map);
        return borrowService.toBorrow(map);
    }

    @ResponseBody
    @RequestMapping("toGetCheck")
    public Object toGetCheck(HttpServletRequest request){
        Map map = new HashMap();
        HttpSession user=request.getSession();
        Map map1 = (Map) user.getAttribute("userInfo");
        Integer userID = Integer.valueOf(map1.get("ID")+"");
        map.put("userId",userID);
        System.out.println(map);
        return borrowService.toGetCheck(map);
    }
}
