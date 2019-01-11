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
     * @param map1
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
        //1 通过标id改变标的状态为已拨款
        int a = bidTowAuditDao.updatetongguo(map);

        //拿到当前时间
        Date date22 =new Date();

        //1.16
        Calendar calMon1 = Calendar.getInstance();
        calMon1.setTime(date22);
        calMon1.set(Calendar.DAY_OF_MONTH,16);
        calMon1.add(Calendar.MONTH, 1);
        Date onestart = calMon1.getTime();
        //2.16
        Calendar calMon2 = Calendar.getInstance();
        calMon2.setTime(date22);
        calMon2.set(Calendar.DAY_OF_MONTH,16);
        calMon2.add(Calendar.MONTH, 2);
        Date twostart = calMon2.getTime();
        //2.15
        Calendar calMon3 = Calendar.getInstance();
        calMon3.setTime(date22);
        calMon3.set(Calendar.DAY_OF_MONTH,15);
        calMon3.add(Calendar.MONTH, 2);
        Date oneend = calMon3.getTime();


        //转换格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

        String parse = sdf.format(date22);
        Date date = null;
        try {
            date = sdf.parse(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
            //获取当前用户名
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
        //还款总金额 本金+利息
        double benXi = MoneyUtil.getBenXi(biddeadline, bidrate, bidCurrentAmount);
        //每月应还数额 monMoney
        double monMoney = MoneyUtil.getLiXiMon(biddeadline,bidrate,bidCurrentAmount);
        //每月应还利息 monLixi
        double monLixi = MoneyUtil.getLiXiMon(biddeadline,bidrate,bidCurrentAmount);
        //最后一期应还利息+本金 monEndBenXi
        double monEndBenXi = MoneyUtil.getEndBenXi(biddeadline,bidrate,bidCurrentAmount);

        //根据用户id得到所有投资人的id
        List<Map> touziUserList = bidTowAuditDao.selUserAccountFlow(bid);

        //2 user_account_flow 用户账户流水表   资金变动金额 变动后 可用余额额  accountID  userID

        map.put("accountid",accountid);//accountid 根据userid查询
        map.put("returnAmount",benXi);//本息和
        map.put("auditID", id);//审核人id
        map.put("auditTime",date);//当前时间
        //查询审核过后的标状态 满标二审通过 5
        Integer newNum = Integer.valueOf(bidTowAuditDao.insertauditjilu(userid).get(0).get("BIDSTATE").toString());
        if(newNum==5){
            map.put("bidState","二审通过");
            map.put("auditRemarks","ok，二审通过");
        }else {
            map.put("bidState","二审失败");
            map.put("auditRemarks","sorry，二审失败");
        }

        //3 添加审核记录
        int b = bidTowAuditDao.insertaudit(map);

        //4 通过用户id给招标用户可用余额充值（招标金额的钱）
        // 1 可用余额 = 可用余额 + 招标金额  2  待还金额 = 本息和
        int c = bidTowAuditDao.updatejiaqian(map);

        //招标用户 变动后可用余额 = 之前的余额 + 招标金额
        //因为之前在充值的时候已经算过 所以直接拿到就行
        Integer availablebalance = Integer.valueOf(bidTowAuditDao.getAccountId(userid).get(0).get("AVAILABLEBALANCE").toString());
        map.put("availablebalance",availablebalance);


        //还款计划参数
        Map map2 = new HashMap();
        int g = 0;

        //根据招标的userid查询当前标的还款方式  当前用户为招标 标信息表一个标id对应一个招标人id
        String bieMethod = bidTowAuditDao.insertauditjilu(userid).get(0).get("BIDREPAYMENTMETHOD").toString();
        if("定期还款".equals(bieMethod)){
            //5 生成定期还款计划
            //double benXi,int bid,Date date21,double monMoney,int bidRepayState,int biddeadline, int bidRepayMethod,int userid
            g = getLoopBack(benXi, bid, onestart,twostart,oneend, monMoney, 1, biddeadline, 1, userid);
            /*for (int o = 1; o <= biddeadline; o++) {
                //当前时间作为还款日期
                Calendar rightNow = Calendar.getInstance();
                //本期还款结束日期 加一个月
                Calendar rightNow2 = Calendar.getInstance();
                //下期还款开始日期 本期还款结束日期加一天
                Calendar rightNow3 = Calendar.getInstance();
                //Date date =new Date();

                rightNow.setTime(date21);
                rightNow.add(Calendar.MONTH, o);

                rightNow2.setTime(date21);
                rightNow2.add(Calendar.MONTH, o-1);

                Date date2 = rightNow.getTime();
                Date date3 = rightNow2.getTime();

                rightNow3.setTime(date2);
                rightNow3.add(Calendar.DAY_OF_MONTH, 1);

                Date date4 = rightNow3.getTime();

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

                //format = sdf1.parse(date3);
                //format2 = sdf1.parse(date2);
                String format = null;
                String format2 = null;
                String format3 = null;
                try {
                    format = sdf1.format(date3);
                    format2 = sdf1.format(date2);
                    format3 = sdf.format(date4);

                    map2.put("benXi",benXi);
                    map2.put("bid",bid);
                    map2.put("date",format);
                    map2.put("date1",format2);
                    map2.put("date2",format3);
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
            }*/

        }else if("一次还清".equals(bieMethod)){
            g = getLoopBack(benXi, bid, onestart,twostart,oneend, benXi, 1, biddeadline, 2, userid);
            //5 生成一次还清还款计划
            /*for (int o = 1; o <= biddeadline; o++) {
                //当前时间作为还款日期
                Calendar rightNow = Calendar.getInstance();
                //本期还款结束日期 加一个月
                Calendar rightNow2 = Calendar.getInstance();
                //下期还款开始日期 本期还款结束日期加一天
                Calendar rightNow3 = Calendar.getInstance();
                //Date date =new Date();

                rightNow.setTime(date21);
                rightNow.add(Calendar.MONTH, o);

                rightNow2.setTime(date21);
                rightNow2.add(Calendar.MONTH, o-1);

                Date date2 = rightNow.getTime();
                Date date3 = rightNow2.getTime();

                rightNow3.setTime(date2);
                rightNow3.add(Calendar.DAY_OF_MONTH, 1);

                Date date4 = rightNow3.getTime();

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

                //format = sdf1.parse(date3);
                //format2 = sdf1.parse(date2);
                String format = null;
                String format2 = null;
                String format3 = null;
                try {
                    format = sdf1.format(date3);
                    format2 = sdf1.format(date2);
                    format3 = sdf.format(date4);

                    map2.put("benXi",benXi);
                    map2.put("bid",bid);
                    map2.put("date",format);
                    map2.put("date1",format2);
                    map2.put("date2",format3);
                    map2.put("monmoney",benXi);
                    map2.put("bidRepayState",1);
                    map2.put("bidRepayNumber",o);
                    map2.put("bidRepayTotPmts",biddeadline);
                    map2.put("bidRepayMethod",2);
                    map2.put("bidRepayUserID",userid);
                    g = bidTowAuditDao.insertHuankuanjihua(map2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        }else if("定期还息，到期还本".equals(bieMethod)){
            //5 生成定期还息，到期还本还款计划
            //每月应还利息 monLixi
            //最后一期应还利息+本金 monEndBenXi
            g = getLoopBack2(benXi, bid, onestart,twostart,oneend, benXi, 1, biddeadline, 3, userid, monEndBenXi, monLixi);
            /*for (int o = 1; o <= biddeadline; o++) {

                //当前时间作为还款日期
                Calendar rightNow = Calendar.getInstance();
                //本期还款结束日期 加一个月
                Calendar rightNow2 = Calendar.getInstance();
                //下期还款开始日期 本期还款结束日期加一天
                Calendar rightNow3 = Calendar.getInstance();
                //Date date =new Date();

                rightNow.setTime(date21);
                rightNow.add(Calendar.MONTH, o);

                rightNow2.setTime(date21);
                rightNow2.add(Calendar.MONTH, o-1);

                Date date2 = rightNow.getTime();
                Date date3 = rightNow2.getTime();

                rightNow3.setTime(date2);
                rightNow3.add(Calendar.DAY_OF_MONTH, 1);

                Date date4 = rightNow3.getTime();

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

                String format = null;
                String format2 = null;
                String format3 = null;
                try {
                    format = sdf1.format(date3);
                    format2 = sdf1.format(date2);
                    format3 = sdf.format(date4);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(o==biddeadline){
                    //标id 还款金额 还款日期 还款结束日期 下一个还款日期
                    //下一次还款金额 还款状态  1 待还款 2 已经还款 3 提前还款
                    //还款期数 还款总期数 还款计算方式方式  1定期还款 2一次还清 3定期还息，到期还本 还款人
                    map2.put("benXi",benXi);
                    map2.put("bid",bid);
                    map2.put("date",format);
                    map2.put("date1",format2);
                    map2.put("date2",format3);
                    map2.put("monmoney",monEndBenXi);
                    map2.put("bidRepayState",1);
                    map2.put("bidRepayNumber",o);
                    map2.put("bidRepayTotPmts",biddeadline);
                    map2.put("bidRepayMethod",3);
                    map2.put("bidRepayUserID",userid);
                    g = bidTowAuditDao.insertHuankuanjihua(map2);
                }else{
                    map2.put("benXi",benXi);
                    map2.put("bid",bid);
                    map2.put("date",format);
                    map2.put("date1",format2);
                    map2.put("date2",format3);
                    map2.put("monmoney",monLixi);
                    map2.put("bidRepayState",1);
                    map2.put("bidRepayNumber",o);
                    map2.put("bidRepayTotPmts",biddeadline);
                    map2.put("bidRepayMethod",3);
                    map2.put("bidRepayUserID",userid);
                    g = bidTowAuditDao.insertHuankuanjihua(map2);
                }

            }*/
        }


        //6 招标用户流水
        int d = bidTowAuditDao.insertliushui(map);
        int i = 0;
        int i1 = 0;
        //7 投标用户流水
        for (Map map1 : touziUserList) {
            //System.out.println(map1);
            //投资用户id
            int userId = Integer.valueOf(map1.get("USERID").toString());
            //System.out.println(userId+".............................."+bid);
            //投资用户可用余额
            Double availablebalance1 = Double.parseDouble( bidTowAuditDao.getAccountId(userId).get(0).get("AVAILABLEBALANCE").toString());
            //投资用户accountid
            int accountId = Integer.valueOf(bidTowAuditDao.getAccountId(userId).get(0).get("ID").toString());
            //拿到user_account 用户账户标 投标用户的冻结金额
            Double freezingAmount = Double.parseDouble( bidTowAuditDao.getAccountId(Integer.valueOf(map1.get("USERID").toString())).get(0).get("FREEZINGAMOUNT").toString());
            //根据标id和用户userid拿到投资金额  注意拿到的是当前标
            Double bidAmount = Double.parseDouble( bidTowAuditDao.gettoubiaojine(userId,bid).get(0).get("BIDAMOUNT").toString());
            //算出当前冻结金额 = 原冻结金额 - 投标金额
            double v = freezingAmount - bidAmount;
            //操作投资用户表 算出冻结金额     代收本金 = 投标的钱     代收利息 = 投标的利息钱

            //代收利息
            int bidAmount1 = Integer.valueOf(bidAmount.toString().substring(0,bidAmount.toString().indexOf(".")));//截取小数点以前
            Double waitLiXi = MoneyUtil.getInterestTotle(biddeadline, bidrate, bidAmount1);

            //当前冻结金额 代收本金 代收利息 投标id
            i = bidTowAuditDao.updatedongjie(v,bidAmount,waitLiXi,userId);
            //添加投资用户流水记录  变动金额 = 冻结金额  变动后可用余额 = 投资用户的可用余额 在投标时已经减去了投标用额
            i1 = bidTowAuditDao.insertliushui2(userId, accountId, freezingAmount, availablebalance1, date);
        }

        if(a>0&&b>0&&c>0&&d>0&&i>0&&i1>0&&g>0){
            return 1;
        }else {
            return 0;
        }

    }


    /**
     * 还款计划封装 定期还款 一次还清
     * @return
     */
    private int getLoopBack(double benXi,int bid,Date onestart,Date twostart,Date oneend,double monMoney,int bidRepayState,int biddeadline, int bidRepayMethod,int userid){
        //benXi bid monMoney bidRepayState 1 ,bidRepayNumber o ,bidRepayTotPmts biddeadline, bidRepayMethod 2,bidRepayUserID userid
        Map map2 = new HashMap();
        int g = 0;
        for (int o = 1; o <= biddeadline; o++) {
            //当前时间作为还款日期
            Calendar rightNow = Calendar.getInstance();
            //本期还款结束日期 加一个月
            Calendar rightNow2 = Calendar.getInstance();
            //下期还款开始日期 本期还款结束日期加一天
            Calendar rightNow3 = Calendar.getInstance();
            //Date date =new Date();

            //2.15
            rightNow.setTime(oneend);
            rightNow.add(Calendar.MONTH, o-1);
            //1.16
            rightNow2.setTime(onestart);
            rightNow2.add(Calendar.MONTH, o-1);
            //2.16
            rightNow3.setTime(twostart);
            rightNow3.add(Calendar.DAY_OF_MONTH, o);

            Date date2 = rightNow.getTime();
            Date date3 = rightNow2.getTime();
            Date date4 = rightNow3.getTime();

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

            String format = null;
            String format2 = null;
            String format3 = null;
            try {
                format = sdf1.format(date3);
                format2 = sdf1.format(date2);
                format3 = sdf1.format(date4);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(o==biddeadline){
                map2.put("benXi",benXi);
                map2.put("bid",bid);
                map2.put("date",format);
                map2.put("date1",format2);
                map2.put("date2","");
                map2.put("monmoney",monMoney);
                map2.put("bidRepayState",bidRepayState);
                map2.put("bidRepayNumber",o);
                map2.put("bidRepayTotPmts",biddeadline);
                map2.put("bidRepayMethod",bidRepayMethod);
                map2.put("bidRepayUserID",userid);
                g = bidTowAuditDao.insertHuankuanjihua(map2);
            }else {
                map2.put("benXi",benXi);
                map2.put("bid",bid);
                map2.put("date",format);
                map2.put("date1",format2);
                map2.put("date2",format3);
                map2.put("monmoney",monMoney);
                map2.put("bidRepayState",bidRepayState);
                map2.put("bidRepayNumber",o);
                map2.put("bidRepayTotPmts",biddeadline);
                map2.put("bidRepayMethod",bidRepayMethod);
                map2.put("bidRepayUserID",userid);
                g = bidTowAuditDao.insertHuankuanjihua(map2);
            }


        }
            return g;

    }

    /**
     * 还款计划封装 定期还息 到期还本
     * @return
     */
    private int getLoopBack2(double benXi,int bid,Date onestart,Date twostart,Date oneend,double monMoney,int bidRepayState,int biddeadline, int bidRepayMethod,int userid,double monEndBenXi, double monLixi){
        //benXi bid monMoney bidRepayState 1 ,bidRepayNumber o ,bidRepayTotPmts biddeadline, bidRepayMethod 2,bidRepayUserID userid
        Map map2 = new HashMap();
        int g = 0;

        for (int o = 1; o <= biddeadline; o++) {

            //当前时间作为还款日期
            Calendar rightNow = Calendar.getInstance();
            //本期还款结束日期 加一个月
            Calendar rightNow2 = Calendar.getInstance();
            //下期还款开始日期 本期还款结束日期加一天
            Calendar rightNow3 = Calendar.getInstance();
            //Date date =new Date();

            //2.15
            rightNow.setTime(oneend);
            rightNow.add(Calendar.MONTH, o-1);
            //1.16
            rightNow2.setTime(onestart);
            rightNow2.add(Calendar.MONTH, o-1);
            //2.16
            rightNow3.setTime(twostart);
            rightNow3.add(Calendar.DAY_OF_MONTH, o);

            Date date2 = rightNow.getTime();
            Date date3 = rightNow2.getTime();
            Date date4 = rightNow3.getTime();

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");

            String format = null;
            String format2 = null;
            String format3 = null;
            try {
                format = sdf1.format(date3);
                format2 = sdf1.format(date2);
                format3 = sdf1.format(date4);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(o==biddeadline){
                //标id 还款金额 还款日期 还款结束日期 下一个还款日期
                //下一次还款金额 还款状态  1 待还款 2 已经还款 3 提前还款
                //还款期数 还款总期数 还款计算方式方式  1定期还款 2一次还清 3定期还息，到期还本 还款人
                map2.put("benXi",benXi);
                map2.put("bid",bid);
                map2.put("date",format);
                map2.put("date1",format2);
                map2.put("date2","");
                map2.put("monmoney",monEndBenXi);
                map2.put("bidRepayState",bidRepayState);
                map2.put("bidRepayNumber",o);
                map2.put("bidRepayTotPmts",biddeadline);
                map2.put("bidRepayMethod",bidRepayMethod);
                map2.put("bidRepayUserID",userid);
                g = bidTowAuditDao.insertHuankuanjihua(map2);
            }else{
                map2.put("benXi",benXi);
                map2.put("bid",bid);
                map2.put("date",format);
                map2.put("date1",format2);
                map2.put("date2",format3);
                map2.put("monmoney",monLixi);
                map2.put("bidRepayState",bidRepayState);
                map2.put("bidRepayNumber",o);
                map2.put("bidRepayTotPmts",biddeadline);
                map2.put("bidRepayMethod",bidRepayMethod);
                map2.put("bidRepayUserID",userid);
                g = bidTowAuditDao.insertHuankuanjihua(map2);
            }

        }


        return g;

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
