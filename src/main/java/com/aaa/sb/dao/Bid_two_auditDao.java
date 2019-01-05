package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * className:Bid_two_auditDao
 * discription:
 * author:wuyanle
 * createTime:2018-12-20 10:40
 */
public interface Bid_two_auditDao {
    /**
     * 满标二审查询
     * @return
     */
    @Select("<script> select id,username,name,userID,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,bidIssueDate,bidDeadDay,bidDeadDate,bidApplyDate,bidDesc,bidType,bidState from " +
            " (select rownum rn,b.id,u.username,s.name,userID,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,to_char(bidIssueDate ,'yyyy-mm-dd') as bidIssueDate,bidDeadDay,to_char(bidDeadDate ,'yyyy-mm-dd') as bidDeadDate,to_char(bidApplyDate ,'yyyy-mm-dd') as bidApplyDate,bidDesc,bidType,bidState from bid_info b" +
            " left join user_login_info u on b.userID=u.ID" +
            " left join bid_state s on b.bidState=s.ID" +
            "   where rownum &gt; #{start}  and bidState=4 ) a where a.rn &lt; #{end} </script>")
    List<Map> getbidList(Map map);
    /**
     * 查询分页总数量
     * @param map
     * @return
     */
    @Select("<script> select count(*) from bid_info where bidState=4 </script>")
    int getPageCount(Map map);

    /**
     * 查询历史审核分页总数量
     * @param map
     * @return
     */
    @Select("<script> select count(*) from bid_audit</script>")
    int getPageaudit(Map map);
    /**
     * 查询历史审核记录
     * @param map
     * @return
     */
    @Select("<script> select id,userID,bidID,auditID,auditTime,auditResult from " +
            " ( select rownum rn,id,userID,bidID,auditID,auditTime,auditResult from bid_audit" +
            " where rownum &gt; #{start}  ) a where a.rn &lt; #{end} </script> ")
    List<Map> getaudit(Map map);
    /**
     * 插入审核记录
     * @param map
     * @return
     */
    @Insert("insert into bid_audit (id,userID,bidID,auditID,auditTime,auditResult,auditRemarks)values(SEQ_BIDAUDIT_ID.nextval,#{USERID},#{ID},#{auditID},#{auditTime},#{bidState},#{auditRemarks})")
    int insertaudit(Map map);

    /**
     * 查询审核过后的标状态
     * @param userid
     * @return
     */
    @Select("<script> select * from bid_info where userid=#{userid} </script>")
    List<Map> insertauditjilu(int userid);
    /**
     * 根据标id查询投资用户列表
     * @return
     */
    @Select("select * from bid_submit where bidInfoID = #{bid}")
    List<Map> selUserAccountFlow(int bid);

    /**
     * 用户账户标查询
     * @param map
     * @return
     */
    @Select("select id,userID,availableBalance,receiveInterest,receivePrincipal,returnAmount,freezingAmount,creditLine" +
            "surplusCreditLine,transactionPassword from user_account ")
    List<Map> gatList(Map map);

    /**
     * 生成还款计划
     */
    @Insert("insert into bid_repay_info (id,bidID,bidRepayAmount,bidRepayDate,bidRepayDeadDate,bidNextRepayDate,bidNextReapyAmount,bidRepayState,bidRepayNumber,bidRepayTotPmts,bidRepayMethod,bidRepayUserID)values(SEQ_BIDREPAYINFO_ID.nextval,#{bid},#{benXi},to_date(substr(#{date},1,10),'yyyy-mm-dd'),to_date(substr(#{date1},1,10),'yyyy-mm-dd'),to_date(substr(#{date2},1,10),'yyyy-mm-dd'),#{monmoney},#{bidRepayState},#{bidRepayNumber},#{bidRepayTotPmts},#{bidRepayMethod},#{bidRepayUserID})")
    int insertHuankuanjihua(Map map);

    /**
     * 用户可用余额更新
     * @param map
     * @return
     */
    @Update("update user_account set availableBalance= availableBalance+(select bidcurrentamount from bid_info where id=#{ID}),returnAmount=#{returnAmount} where userid=#{USERID}")
    int updatejiaqian(Map map);

    /**
     * 借钱的用户流水表记录更新  招标
     * 4 账户拨款
     * bidCurrentAmount 变动余额 = 招标金额   availableBalance  变动后可用余额 = 变动前 + 招标金额
     * @param map
     * @return
     */
    @Insert("insert into user_account_flow(id,userID,accountID,amount,availableBalance,flowDate,flowType) values(SEQ_SYSTEMACCOUNTFLOW_ID.nextval,#{USERID},#{accountid},#{bidCurrentAmount},#{availablebalance},#{auditTime},4) ")
    int insertliushui(Map map);

    /**
     * 投资用户流水表
     * 8 投资扣款
     * @return
     */
    @Insert("insert into user_account_flow (id,userID,accountID,amount,availableBalance,flowDate,flowType)values(SEQ_SYSTEMACCOUNTFLOW_ID.nextval,#{userid},#{accountid},#{amount},#{availableBalance},#{flowDate},8)")
    int insertliushui2(@Param("userid") int userid,@Param("accountid") int accountid,@Param("amount") double amount,@Param("availableBalance") double availableBalance,@Param("flowDate") Date flowDate);

    /**
     * 根据用户userid查询标user_account id
     * @param userid
     * @return
     */
    @Select("select * from user_account where userid=#{userid}")
    List<Map> getAccountId(int userid);

    /**
     * 投资用户
     * 解除冻结金额变为投资扣款 = 冻结金额-投标金额
     * a 冻结金额 b 代收本金 c 代收利息
     * @param
     * @return
     */
    @Update("update user_account set freezingAmount=#{a},receivePrincipal=#{b},receiveInterest=#{c} where userID=#{userid}")
    int updatedongjie(@Param("a") double a,@Param("b") double b,@Param("c") double c,@Param("userid") int userid);

    /**
     * 根据标id和用户id查询投资金额
     * @param userId
     * @param bid
     * @return
     */
    @Select("select * from bid_submit where userid = #{userId} and bidInfoID=#{bid}")
    List<Map> gettoubiaojine(@Param("userId") int userId, @Param("bid") int bid);


    /**
     * 满标二审通过
     * @param map
     * @return
     */
    @Update("update bid_info set bidState=5 where id=#{ID}")
    int updatetongguo(Map map);

    /**
     * 满标二审驳回
     * @param map
     * @return
     */
    @Update("update bid_info set bidState=7 where id=#{id}")
    int updatebohui(Map map);

    /**
     * 历史审核记录
     * @param map
     * @return
     */
   /* @Select("<script> select id,username,name,userID,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,bidIssueDate,bidDeadDay,bidDeadDate,bidApplyDate,bidDesc,bidType,bidState from " +
            " (select rownum rn,b.id,u.username,s.name,userID,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,to_char(bidIssueDate ,'yyyy-mm-dd') as bidIssueDate,bidDeadDay,to_char(bidDeadDate ,'yyyy-mm-dd') as bidDeadDate,to_char(bidApplyDate ,'yyyy-mm-dd') as bidApplyDate,bidDesc,bidType,bidState from bid_info b" +
            " left join user_login_info u on b.userID=u.ID" +
            " left join bid_state s on b.bidState=s.ID" +
            "   where rownum &gt; #{start}  and bidState not in(1,2,3)) a where a.rn &lt; #{end} </script>")
   List<Map> getAuditList(Map map);*/

}
