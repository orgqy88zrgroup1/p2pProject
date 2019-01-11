package com.aaa.sb.service;

import com.aaa.sb.dao.CapitalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * className:CapitalServiceImpl
 * discription:
 * author:ZXL
 */
@Service
public class CapitalServiceImpl implements CapitalService {
    @Autowired
    private CapitalDao capitalDao;
    @Override
    public List<Map> getparam(Map map) {
        /*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是：" + (userInfo.get("ID") + ""));
        int userid = Integer.valueOf(userInfo.get("userid")+"");*/
        map.put("userid",143);
        return capitalDao.getparam(map);
    }

    @Override
    public int getCount(Map map) {
       /* ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        int userid = Integer.valueOf(userInfo.get("userid")+"");*/
        map.put("userid",143);
        return capitalDao.getCount(map);
    }

    @Override
    public List<Map> getparams(Map map) {
        /*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        int userid = Integer.valueOf(userInfo.get("userid")+"");*/
        map.put("userid",143);
        return capitalDao.getparams(map);
    }

    @Override
    public int getCounts(Map map) {
        /*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        int userid = Integer.valueOf(userInfo.get("userid")+"");*/
        map.put("userid",143);
        return capitalDao.getCounts(map);
    }
}
