package com.aaa.sb.service;

import com.aaa.sb.dao.Bid_two_auditDao;
import com.aaa.sb.dao.EmpDao;
import com.aaa.sb.util.MoneyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * className:Bid_tow_auditServiceImpl
 * discription:
 * author:wuyanle
 * createTime:2018-12-20 10:43
 */
@Service
@SuppressWarnings("all")
public class Bid_two_auditServiceImpl implements Bid_two_auditService {
    /**
     * 依赖注入
     * @param map
     * @return
     */
    @Autowired
    private Bid_two_auditDao bidTowAuditDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private EmpDao empDao;

    /**
     * 二审审核查询
     * @param map
     * @return
     */
    @Override
    public Map getbidList(Map map) {

        List<Map> maps = bidTowAuditDao.getbidList(map);
        int count = bidTowAuditDao.getPageCount(map);
        HashMap resultMap=new HashMap();
        resultMap.put("pageData",maps);
        resultMap.put("total",count);

        return resultMap;
    }

    /**、
     * 分页查询历史审核记录
     * @param map
     * @return
     */
    @Override
    public Map getaudit(Map map) {
        List<Map> maps = bidTowAuditDao.getaudit(map);
        int count = bidTowAuditDao.getPageaudit(map);
        HashMap resultMap=new HashMap();
        resultMap.put("pageData",maps);
        resultMap.put("total",count);

        return resultMap;
    }



    /**
     * 二审审核通过
     * @param map
     * @return
     */
    @Override
    public int updatetongguo(Map map) {
        //通过标id改变标的状态为已拨款
        int a = bidTowAuditDao.updatetongguo(map);

        //拿到当前时间
        Date date22 =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String parse = sdf.format(date22);
        Date date = null;
        try {
            date = sdf.parse(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String username = session.getAttribute("username").toString();
        //审核人id
        Integer id = Integer.valueOf(empDao.selByUserName(username).get(0).get("ID").toString());
        //招标用户id
        Integer userid = Integer.valueOf(map.get("USERID").toString());
        //标id
        Integer bid = Integer.valueOf(map.get("ID").toString());
        //根据userid查询出accountid
        Integer accountid = Integer.valueOf(bidTowAuditDao.getAccountId(userid).get(0).get("ID").toString());


        //期数 利率 本金
        Integer biddeadline = Integer.valueOf(map.get("biddeadline").toString());
         Double bidrate = Double.parseDouble(map.get("bidrate").toString());
        Integer bidCurrentAmount = Integer.valueOf(map.get("bidCurrentAmount").toString());
        double benXi = MoneyUtil.getBenXi(biddeadline, bidrate, bidCurrentAmount);
        //每月应还数额
        double monMoney = MoneyUtil.getLiXiMon(biddeadline,bidrate,bidCurrentAmount);

        //根据用户id得到所有投资人的id
        List<Map> touziUserList = bidTowAuditDao.selUserAccountFlow(bid);

        //2 user_account_flow 用户账户流水表   资金变动金额   变动后 可用余额额  accountID  userID

        map.put("accountid",accountid);
        map.put("returnAmount",benXi);
        map.put("auditID", id);
        map.put("auditTime",date);
        //查询审核过后的标状态
        Integer newNum = Integer.valueOf(bidTowAuditDao.insertauditjilu(userid).get(0).get("BIDSTATE").toString());
        if(newNum==5){
            map.put("bidState","审核通过");
        }else {
            map.put("bidState","审核失败");
        }

        //添加审核记录
        int b = bidTowAuditDao.insertaudit(map);
        //通过用户id给用户可用余额充值 （招标金额的钱）
        int c = bidTowAuditDao.updatejiaqian(map);
        //招标可用余额
        Integer availablebalance = Integer.valueOf(bidTowAuditDao.getAccountId(userid).get(0).get("AVAILABLEBALANCE").toString());
        map.put("availablebalance",availablebalance);

        Map map2 = new HashMap();
        map2.put("benXi",benXi);
        map2.put("bid",bid);
        int g = 0;

        //生成还款计划
        for (int o = 1; o <= biddeadline; o++) {
            Calendar rightNow = Calendar.getInstance();
            Calendar rightNow2 = Calendar.getInstance();
            //Date date =new Date();

            rightNow.setTime(date22);
            rightNow.add(Calendar.MONTH, o);

            rightNow2.setTime(date22);
            rightNow2.add(Calendar.MONTH, o-1);

            Date date2 = rightNow.getTime();
            Date date3 = rightNow2.getTime();

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

            //format = sdf1.parse(date3);
            //format2 = sdf1.parse(date2);
            String format = null;
            String format2 = null;
            try {
                format = sdf1.format(date3);
                format2 = sdf1.format(date2);


            map2.put("date",format);
            map2.put("date1",format2);
            map2.put("date2",format2);
            map2.put("monmoney",monMoney);
            map2.put("bidRepayState",1);
            map2.put("bidRepayNumber",o);
            map2.put("bidRepayTotPmts",biddeadline);
            map2.put("bidRepayMethod",1);
            map2.put("bidRepayUserID",userid);
            g = bidTowAuditDao.insertHuankuanjihua(map2);

            } catch (Exception e) {
                e.printStackTrace();
            }



        }




        //账户流水
        int d = bidTowAuditDao.insertliushui(map);
        int i = 0;
        int i1 = 0;
        //投资流水
        for (Map map1 : touziUserList) {
            System.out.println(map1);
            //投资用户id
            int userId = Integer.valueOf(map1.get("USERID").toString());
            System.out.println(userId+".............................."+bid);
            //投资用户可用余额
            Double availablebalance1 = Double.parseDouble( bidTowAuditDao.getAccountId(userId).get(0).get("AVAILABLEBALANCE").toString());
            //投资用户accountid
            int accountId = Integer.valueOf(bidTowAuditDao.getAccountId(userId).get(0).get("ID").toString());
            //1 拿到user_account 用户账户标 的冻结金额
            Double freezingAmount = Double.parseDouble( bidTowAuditDao.getAccountId(Integer.valueOf(map1.get("USERID").toString())).get(0).get("FREEZINGAMOUNT").toString());
            //根据标id和用户userid拿到投资金额  注意拿到的是当前标
            Double bidAmount = Double.parseDouble( bidTowAuditDao.gettoubiaojine(userId,bid).get(0).get("BIDAMOUNT").toString());
            //算出当前冻结金额
            double v = freezingAmount - bidAmount;
            //操作投资用户表 算出冻结金额
            i = bidTowAuditDao.updatedongjie(v, userId);
            i1 = bidTowAuditDao.insertliushui2(userId, accountId, freezingAmount, availablebalance1, date);
        }

        if(a>0&&b>0&&c>0&&d>0&&i>0&&i1>0&&g>0){
            return 1;
        }else {
            return 0;
        }



    }

    /**
     * 用户标查询
     * @param map
     * @return
     */
    @Override
    public List<Map> gatList(Map map) {

        return  bidTowAuditDao.gatList(map);
    }

    /**
     * 用户标更新
     * @param map
     * @return
     */
    @Override
    public int updatejiaqian(Map map) {
        return bidTowAuditDao.updatejiaqian(map);
    }

    /**
     * 二审审核驳回
     * @param map
     * @return
     */
    @Override
    public int updatebohui(Map map) {
        return bidTowAuditDao.updatebohui(map);
    }
}
