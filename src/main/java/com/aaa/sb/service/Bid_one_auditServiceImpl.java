package com.aaa.sb.service;

import com.aaa.sb.dao.Bid_one_auditDao;
import com.aaa.sb.dao.Bid_two_auditDao;
import com.aaa.sb.dao.EmpDao;
import oracle.sql.DATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:bid_one_auditServiceImpl
 * discription:
 * author:wuyanle
 * createTime:2018-12-18 16:47
 */
@Service
@SuppressWarnings("all")
public class Bid_one_auditServiceImpl implements Bid_one_auditService {
    /**
     * 依赖注入
     */
    @Autowired
    private Bid_one_auditDao bidOneAuditDao;

    @Autowired
    private Bid_two_auditDao bidTowAuditDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private EmpDao empDao;

    /**
     * 一审满标分页查询1
     * @param map
     * @return
     */
    @Override
    public Map getbidList(Map map) {

        List<Map> maps = bidOneAuditDao.getbidList(map);
        int count = bidOneAuditDao.getPageCount(map);
        HashMap resultMap=new HashMap();
        resultMap.put("pageData",maps);
        resultMap.put("total",count);

        return resultMap;
    }

    /**
     * 一审审核通过
     * @param map
     * @return
     */
    @Override
    public int updatetongguo(Map map) {

       int a = bidOneAuditDao.updatetongguo(map);
        //审核人（当前登录用户）
        String username = session.getAttribute("username").toString();
        //审核人id
        Integer id = Integer.valueOf(empDao.selByUserName(username).get(0).get("ID").toString());
        //招标用户id
        Integer userid = Integer.valueOf(map.get("USERID").toString());
        //标id
        Integer bid = Integer.valueOf(map.get("ID").toString());
        //根据userid查询出accountid
        System.out.println(bidTowAuditDao.getAccountId(userid));
        Integer accountid = Integer.valueOf(bidTowAuditDao.getAccountId(userid).get(0).get("ID").toString());
        //2 user_account_flow 用户账户流水表   资金变动金额 变动后 可用余额额  accountID  userID
        Date date = new Date();
        map.put("accountid",accountid);//accountid 根据userid查询

        map.put("auditID", id);//审核人id
        map.put("auditTime",date);//当前时间
        //查询审核过后的标状态 满标一审通过 4
        Integer newNum = Integer.valueOf(bidTowAuditDao.insertauditjilu(userid).get(0).get("BIDSTATE").toString());
        if(newNum==4){
            map.put("bidState","一审通过");
            map.put("auditRemarks","ok，一审通过");
        }else {
            map.put("bidState","一审失败");
            map.put("auditRemarks","sorry，一审失败");
        }


        //3 添加审核记录
        int b = bidTowAuditDao.insertaudit(map);
        if(a>0&&b>0){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 一审审核驳回
     * @param map
     * @return
     */
    @Override
    public int updatebohui(Map map) {
        return bidOneAuditDao.updatebohui(map);
    }
}
