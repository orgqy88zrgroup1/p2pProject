package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface BidsDao {
    /**
     * 招标分页查询
     * @param map
     * @return
     */
    @Select("<script>select id,realname,userid,auditid,bidid,to_char(audittime,'yyyy-mm-dd HH24:mi:ss') as audittime,auditresult,auditremarks,bidamount,bidrepaymentmethod,bidrate,biddesc,bidDeadDay,bidState from (" +
            "select rownum rn ,a.id,a.userid,a.bidid,r.realname,a.auditid,a.audittime,a.auditresult,a.auditremarks,b.bidamount,b.bidrepaymentmethod,b.bidrate,b.biddesc,b.bidDeadDay,b.bidState from " +
            "bid_audit a  join bid_info b on a.userid=b.userid  join realname_certification r on r.userid=a.userid where rownum &lt; #{end} " +
            "<if test=\"realname!=null and realname!=''\">and r.realname like '%'||#{realname}||'%'</if>"+
            "<if test=\"auditresult!=null and auditresult!=''\">and a.auditresult=#{auditresult}</if>"+
            ") c where c.rn &gt; #{start}</script>")
    List<Map> getPageByParam(Map map);

    /**
     * 招标分页总数量
     * @param map
     * @return
     */
    @Select("<script>select count(*) from bid_audit <where>" +
            "<if test=\"realname!=null and realname!=''\">and realname like '%'||#{realname}||'%'</if>"+
            "<if test=\"auditresult!=null and auditresult!=''\">and auditresult=#{auditresult}</if>"+
            "</where></script>")
    int getPageCount(Map map);
    /**
     * 更新审核表
     * @param map
     * @return
     */
    @Insert("insert into bid_audit values(seq_bidaudit_id.nextval,#{USERID},#{AUDITID},sysdate,#{AUDITRESULT},#{BIDID},#{AUDITREMARKS})")
    int update(Map map);

    /**
     *审核通过时更新标状态
     * @param map
     * @return
     */
    @Update("update bid_info set bidstate=1 where userid=#{USERID}")
    int upd(Map map);

    /**
     * 审核失败更新标状态
     * @param map
     * @return
     */
    @Update("update bid_info set bidstate=8 where userid=#{USERID}")
    int upda(Map map);
}
