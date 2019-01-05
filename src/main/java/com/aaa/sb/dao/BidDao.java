package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * className:BidDao
 * discription:
 * author:gwd
 * createTime:2018-12-24 11:20
 */
@Component
public interface BidDao {

    /**
     * 通过标id查找该标的信息
     * @return
     */
    @Select(value = "select id,userId,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,substr(to_char(bidIssueDate,'yyyy-mm-dd hh24:mi:ss'),0,19) bidIssueDate,bidDeadDay,bidDeadDate,bidApplyDate,bidDesc,bidType from bid_info where id=#{id}")
    Map getBidInfoById(int id);

    /**
     * 根据用户id查找该用户的信息
     * @param userId
     * @return
     */
    @Select(value = "select realname,sex,address,idnumber,academic,housed,marriage,income from realname_certification where userId=#{userId}")
    Map getBaseInfoByUserId(int userId);

    /**
     * 将投标信息放入到bid_submit投资记录表中
     * @param map
     * @return
     */
    @Insert(value = "insert into bid_submit values(seq_bidinfo_id.nextval,#{bidid},#{userId},#{bidNum},#{bidRate},sysdate)")
    int investBid(Map map);

    /**
     * 根据标的id来查投该标的总钱数
     * @param map
     * @return
     */
    @Select(value = "select nvl(sum(bidAmount),0) bidAmount,nvl(sum(bidRate),0) bidRate from bid_submit where bidInfoID=#{bidid}")
    Map findInvestMoney(Map map);

    /**
     * 通过当前用户查找该用户的登录信息
     * @param userId
     * @return
     */
    @Select(value = "select username,password,telephone from user_login_info where id=#{userId}")
    Map findUserName(int userId);

    /**
     * 根据标id查找该标的投资状况
     * @param id
     * @return
     */
    @Select(value = "select b.id,b.BIDINFOID,b.BIDAMOUNT,b.BIDRATE,substr(to_char(b.BIDDATE,'yyyy-mm-dd hh24:mi:ss'),0,19) BIDDATE,u.id,u.USERNAME,u.PASSWORD,u.TELEPHONE from bid_submit b left join USER_LOGIN_INFO u on u.ID=b.USERID where b.BIDINFOID=#{id}")
    List<Map> findInvestInfo(int id);

    /**
     * 通过标id找到招标人id
     * @param id
     * @return
     */
    @Select(value = "select userId from bid_info where id=#{id}")
    Map findBidUserId(int id);

    /**
     * 根据招标人的id查找该人的还款信息
     * @param bidUserId
     * @return
     */
    /*@Select(value = "select id,bidID,bidRepayAmount,bidRepayDate,bidRepayDeadDate,bidNextRepayDate,bidNextReapyAmount,bidRepayState,bidRepayNumber,bidRepayTotPmts,bidRepayMethod,(select max(bidrepaydeaddate) from bid_repay_info where bidRepayUserID=#{bidUserId}) lastDate from bid_repay_info where bidRepayUserID=#{bidUserId}")*/
    @Select(value = "select id,bidID,bidRepayAmount,bidRepayDate,bidRepayDeadDate,substr(to_char(bidNextRepayDate,'yyyy-mm-dd hh24:mi:ss'),0,19) bidNextRepayDate,bidNextReapyAmount,bidRepayState,bidRepayNumber,bidRepayTotPmts,bidRepayMethod,(select substr(to_char(max(bidrepaydeaddate),'yyyy-mm-dd hh24:mi:ss'),0,19) from bid_repay_info where bidRepayUserID=#{bidUserId}) lastDate from bid_repay_info where bidRepayUserID=#{bidUserId}")
    List<Map> findRepayByBidUserId(int bidUserId);

    /**
     * 根据登录人id查找用户的支付密码
     * @param userId
     * @return
     */
    @Select(value = "select paypwd from user_info where userId=#{userId}")
    Map getPayPwd(int userId);

    /**
     * 根据登录人的id查找该用户的总的待收利息，总的待收本金
     * @param userId
     * @return
     */
    @Select(value = "select sum(bidrate) bidrate,sum(bidAmount) bidAmount from bid_submit where userId = #{userId}")
    Map findTotalRateAndMoney(int userId);

    /**
     * 根据用户id查找该用户的账户信息表
     * @param userId
     * @return
     */
    @Select(value = "select id,USERID,AVAILABLEBALANCE,RECEIVEINTEREST,RECEIVEPRINCIPAL,RETURNAMOUNT,FREEZINGAMOUNT,CREDITLINE,SURPLUSCREDITLINE,TRANSACTIONPASSWORD from user_account where userId=#{userId}")
    Map findUserAccount(int userId);

    /**
     * 根据用户的投标相应的更新用户账户表
     * @param userAccountMap
     * @return
     */
    @Update(value = "update user_account set availableBalance=#{availableBalance},receiveInterest=#{receiveInterest},receivePrincipal=#{receivePrincipal},freezingAmount=#{freezingAmount} where userId = #{userID}")
    int updateUserAccount(Map userAccountMap);

    /**
     * 用户投标之后将该条信息插入到用户账户流水表中去
     * @param map
     * @return
     */
    @Insert(value = "insert into user_account_flow values(seq_user_account_flow_id.nextval,#{userId},#{accountId},#{amount},#{availableBalance},sysdate,8)")
    int insertUserAccountFolw(Map map);
}
