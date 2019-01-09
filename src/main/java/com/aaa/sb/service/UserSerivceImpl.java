package com.aaa.sb.service;

import com.aaa.sb.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * className:UserSerivceImpl
 * discription:
 * author:gwd
 * createTime:2018-12-10 20:38
 */
@SuppressWarnings("all")
@Service
public class UserSerivceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    @Override
    public int addUser(Map map) {
        System.out.println("插入用户登录信息表的map："+map);
        int i = userDao.addUser(map);
        //得到该条信息
        Map info = userDao.info(map);
        int id = Integer.valueOf(info.get("ID")+"");
        System.out.println("外键id是："+id);
        int j = userDao.insertUserAccount(id);
        int k = userDao.insertUserInfo(id);
        if(i==1&&j==1&&k==1){
            System.out.println("插入成功");
            return 1 ;
        }else{
            System.out.println("插入失败");
            return 0;
        }
    }

    @Override
    public int checkPwd(String userName,String password) {
        //System.out.println(userName+"password:"+password);
        Map map = userDao.checkPwd(userName);
        //System.out.println("根据用户名取的map："+map);
        if((map.get("PASSWORD")+"").equals(password)){
            //说明密码正确
            return 1;
        }
        return 0;
    }

    /**
     * 放入session
     * @param map
     */
    @Override
    public void setSession(Map map, HttpSession session) {
        Map map1 = userDao.getSession(map);
        session.setAttribute("userInfo",map1);
    }

    @Override
    public int checkPhone(String phone) {
        List<Map> map = userDao.checkPhone(phone);
        //说明有重复
        if (map!=null&&map.size()>0){
            return 0;
        }
        return 1;
    }

    @Override
    public List<Map> getList(Map map) {
        List<Map> lists = userDao.getList(map);
        for (Map list : lists) {
            double BIDCURRENTAMOUNT = Double.valueOf(list.get("BIDCURRENTAMOUNT")+"");
            double BIDAMOUNT = Double.valueOf(list.get("BIDAMOUNT")+"");
            double percent = BIDCURRENTAMOUNT/BIDAMOUNT*100;
            list.put("percent",percent);
        }
        return lists;
    }

    @Override
    public int getcount(Map map) {
        userDao.getcount(map);
        return Integer.valueOf(userDao.getcount(map).get(0).get("CNT")+"");
    }

    @Override
    public List<Map> getAccountList(Map map) {


        return userDao.getAccountList(map);
    }

    @Override
    public int getRepayment(Map map) {
        System.out.println(userDao.getAvailableBalance(map));
        Map map2 = userDao.getAvailableBalance(map);
        Integer AVAILABLEBALANCE = Integer.valueOf(map2.get("AVAILABLEBALANCE")+"");
        Integer repayment = Integer.valueOf(map.get("repaymentCount")+"");
        Integer newCount = AVAILABLEBALANCE-repayment;
        System.out.println(AVAILABLEBALANCE+"    "+repayment+"   "+newCount);
        map.put("availableBalance",newCount);
        map2.put("availableBalance",newCount);
        map2.put("changeCount",repayment);
        map2.put("state",6);
        System.out.println(map2);
        userDao.getAccountFlow(map2);
        userDao.getRepayment(map);
        userDao.getRepay(map);
        return 0;
    }

    @Override
    public int getCash(Map map) {
        System.out.println(userDao.getAvailableBalance(map));
        Map map2 = userDao.getAvailableBalance(map);
        Integer AVAILABLEBALANCE = Integer.valueOf(map2.get("AVAILABLEBALANCE")+"");
        Integer cash = Integer.valueOf(map.get("cashCount")+"");
        Integer newCount = AVAILABLEBALANCE-cash;
        System.out.println(AVAILABLEBALANCE+"    "+cash+"   "+newCount);
        map.put("availableBalance",newCount);
        map2.put("availableBalance",newCount);
        map2.put("changeCount",cash);
        map2.put("state",5);
        System.out.println(map2);
        userDao.getCash(map);
        userDao.getAccountFlow(map2);
        return 0;
    }

    @Override
    public int getRecharge(Map map) {
        System.out.println(userDao.getAvailableBalance(map));
        Map map2 = userDao.getAvailableBalance(map);
        Integer AVAILABLEBALANCE = Integer.valueOf(map2.get("AVAILABLEBALANCE")+"");
        Integer Recharge = Integer.valueOf(map.get("rechargeCount")+"");
        Integer newCount = AVAILABLEBALANCE+Recharge;
        System.out.println(AVAILABLEBALANCE+"    "+Recharge+"   "+newCount);
        map.put("availableBalance",newCount);
        map2.put("availableBalance",newCount);
        map2.put("changeCount",Recharge);
        map2.put("state",1);
        System.out.println(map2);
        userDao.getRecharge(map);
        userDao.getAccountFlow(map2);
        return 0;
    }

    @Override
    public Map getRepayInfo(Map map) {
        return userDao.getRepayInfo(map);
    }

    @Override
    public List<Map> getUserList() {
        return userDao.getUserList();
    }
}
