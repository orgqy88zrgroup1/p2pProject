package com.aaa.sb.service;

import com.aaa.sb.dao.BidDao;
import com.aaa.sb.util.MoneyUtil;
import com.alibaba.druid.sql.dialect.odps.ast.OdpsUDTFSQLSelectItem;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:BidServiceImpl
 * discription:
 * author:gwd
 * createTime:2018-12-24 14:02
 */
@Service
public class BidServiceImpl implements BidService{

    @Autowired
    private BidDao bidDao;

    @Override
    public Map getBidInfoById(int id) {
        System.out.println("标的id是："+id);
        //获得招标人id
        Map bidUserIdMap = bidDao.findBidUserId(id);
        Integer bidUserId = Integer.valueOf( bidUserIdMap.get("USERID")+ "");
        //根据招标人id进行查找招标人的相关信息
        Map realInfo = bidDao.getBaseInfoByUserId(bidUserId);
        String realname = realInfo.get("REALNAME") + "";
        //设置拼接字符串
        String names =null;
        //截取姓名的姓
        String name = realname.substring(0,1);
        String sex = realInfo.get("SEX") + "";
        System.out.println(sex);
        if(sex.equals("女")){
            names = name + "女士";
        }else {
            names = name +"先生";
        }
        System.out.println(names);
        Map bidInfo = bidDao.getBidInfoById(id);
        bidInfo.put("names",names);
        return bidInfo;
    }

    @Override
    public Map getBaseInfoByUserId() {
        int userId = getUserId();
        System.out.println(userId);
        return bidDao.getBaseInfoByUserId(userId);
    }

    @Override
    public int investBid(Map map) {
        int userId = getUserId();
        map.put("userId",userId);
        //需要计算利息
        Double bidRate = 0.000;
        //获得年利率 将string类型的转换为double
        Double repayRate = Double.parseDouble(map.get("repayRate") + "");
        //获得贷款期数
        int repayDate = Integer.valueOf(map.get("repayDate") + "");
        //获得投资钱数
        int bidNum = Integer.valueOf(map.get("bidNum") + "");
        bidRate= MoneyUtil.getInterestTotle(repayDate, repayRate, bidNum);
        System.out.println("投资的钱数是："+bidNum+",投资的总利润是："+bidRate);
        map.put("bidRate",bidRate);
        System.out.println("投资的信息map是："+map);
        int i = bidDao.investBid(map);
        System.out.println("插入bid_submit表中的返回结果为："+i);
        //查找总的待收利息和待收本金
        Map totalRateAndMoney = bidDao.findTotalRateAndMoney(userId);

        //总利息
        double totalRate = Double.parseDouble(totalRateAndMoney.get("BIDRATE")+"");
        //总本金
        double totalBenMoney = Double.parseDouble(totalRateAndMoney.get("BIDAMOUNT")+"");
        System.out.println("总的利息和本金为："+totalRateAndMoney);
        //查到该账户的所有信息
        Map userAccount = bidDao.findUserAccount(userId);
        //得到该用户的账户id
        int accountId = Integer.valueOf(userAccount.get("ID")+"");
        //该账户总余额
        double totalMoney = Double.parseDouble(userAccount.get("AVAILABLEBALANCE") + "");
        //投资之后的可用余额
        Double balance = totalMoney - bidNum;
        //创建更新user_account表的map
        Map userAccountMap = new HashMap();
        userAccountMap.put("userID",userId);
        userAccountMap.put("availableBalance",balance);
        userAccountMap.put("receiveInterest",totalRate);
        userAccountMap.put("receivePrincipal",totalBenMoney);
        userAccountMap.put("freezingAmount",totalBenMoney);
        System.out.println("要更改的数据是："+userAccountMap);
        int j = bidDao.updateUserAccount(userAccountMap);
        System.out.println("更新到user_account中的j："+j);
        //要插入到用户流水表中的是：userID  accountID用户流水id  amount投资金额  availableBalance可用余额
        Map insertFlowMap = new HashMap();
        insertFlowMap.put("userId",userId);
        insertFlowMap.put("accountId",accountId);
        insertFlowMap.put("amount",bidNum);
        insertFlowMap.put("availableBalance",balance);
        System.out.println("insertFlowMap："+insertFlowMap);
        int k = bidDao.insertUserAccountFolw(insertFlowMap);
        System.out.println("插入用户流水表中的数据k是："+k);
        if(i==1&&j==1&&k==1){
            System.out.println("三表都成功");
            return 1;
        }
        return 0;
    }

    @Override
    public Map findInvestMoney(Map map) {
        System.out.println("service层里的map："+map);
        System.out.println("从dao层里返回的map："+bidDao.findInvestMoney(map));
        return bidDao.findInvestMoney(map);
    }

    @Override
    public List<Map> investItem(int id) {
        return bidDao.findInvestInfo(id);
    }

    @Override
    public List<Map> findRepayByBidUserId(int id) {
        //根据标的id查找该招标人的id
        Map bidUserIdMap = bidDao.findBidUserId(id);
        Integer bidUserId = Integer.valueOf( bidUserIdMap.get("USERID")+ "");
        System.out.println("该招标人的id是："+bidUserId);
        //根据招标人得到该人的还款列表
        List<Map> repayListMap = bidDao.findRepayByBidUserId(bidUserId);
        System.out.println("repayListMap:"+repayListMap);
        if(repayListMap==null){
            System.out.println("还款列表为空");
        }
        return repayListMap;
    }

    @Override
    public int getPayPwd(Map map) {
        int userId = getUserId();
        Map payPwd = bidDao.getPayPwd(userId);
        System.out.println("页面的密码："+map.get("pass")+"");
        System.out.println("数据库的密码："+payPwd.get("PAYPWD")+"");
        if((payPwd.get("PAYPWD")+"").equals(map.get("pass"))){
            System.out.println("密码相等");
            return 1;
        }
        System.out.println("密码不相等");
        return 0;
    }

    @Override
    public int checkMoney(Map map) {
        int userId = getUserId();
        Map userAccount = bidDao.findUserAccount(userId);
        //获得当前可用余额
        double currMoney = Double.parseDouble(userAccount.get("AVAILABLEBALANCE")+"");
        //获得投资金额
        double investMoney = Double.parseDouble(map.get("money")+"");
        System.out.println("当前余额："+currMoney+"，投资金额："+investMoney);
        if(investMoney<=currMoney){
            System.out.println("项目可投");
            return 1;
        }
        return 0;
    }

    private int getUserId(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        System.out.println("userInfo:"+userInfo);
        System.out.println("现在登录的用户id是：" + (userInfo.get("ID") + ""));
        Integer userId = Integer.valueOf(userInfo.get("ID") + "");
        return userId;
    }

}
