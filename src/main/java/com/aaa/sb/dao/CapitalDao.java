package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * className:CapitalDao
 * discription:
 * author:ZXL
 */

public interface CapitalDao {
    /**
     * 分页查询资金记录
     * @param map
     * @return
     */
    @Select("<script>select id,realname,accountid,amount,availablebalance,to_char(flowdate,'yyyy-MM-dd HH24:mi:ss') as flowdate,flowtype from " +
            "(select rownum rn,f.id,r.realname,f.accountid,f.amount,f.availablebalance,f.flowdate,f.flowtype from user_account_flow f " +
            "join realname_certification r on f.userid=r.userid where 1=1 and f.userid=#{userid} and rownum &lt; #{end}" +
            "<if test=\"flowtype!=null and flowtype!=''\">  and flowtype =#{flowtype}</if>" +
            ")a where a.rn &gt; #{start}</script>")
    List<Map> getparam( Map map);

    /**
     * 查询资金记录总数量
     * @param map
     * @return
     */
    @Select("<script>select count(*) from user_account_flow f join realname_certification r on f.userid=r.userid <where> 1=1 and f.userid=#{userid} " +
            "<if test=\"flowtype!=null and flowtype!=''\">  and flowtype =#{flowtype}</if>" +
            "</where></script>")
    int getCount( Map map);





    /**
     * 投资记录分页查询
     * @param map
     * @return
     */
    @Select("<script>select id,realname,bidamount,bidrate,to_char(biddate,'yyyy-MM-dd HH24:mi:ss') as biddate" +
            " from (select rownum rn,s.id,r.realname,s.bidamount,s.bidrate,s.biddate from bid_submit s " +
            "join realname_certification r on s.userid=r.userid where 1=1 and s.userid=#{userid} and rownum &lt; #{end}" +
            "<if test=\"biddate!=null and biddate!=''\">and to_char(s.biddate,'yyyy/MM/dd HH24:mi:ss') like '%'||#{biddate}||'%'</if>" +
            ") a where a.rn &gt; #{start}</script>")
    List<Map> getparams(Map map);

    /**
     * 投资记录总数量
     * @param map
     * @return
     */
    @Select("<script>select count(*) from bid_submit s join realname_certification r on s.userid=r.userid <where> 1=1 and s.userid=#{userid}  " +
            "<if test=\"biddate!=null and biddate!=''\">  and to_char(s.biddate,'yyyy/MM/dd HH24:mi:ss') like '%'||#{biddate}||'%'</if>" +
            "</where></script>")
    int getCounts(Map map);
}
