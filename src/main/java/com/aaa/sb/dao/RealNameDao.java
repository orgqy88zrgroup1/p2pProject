package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface RealNameDao {

    /**
     * 实名认证带参分页查询
     * @param map
     * @return
     */
    @Select("<script>select id,userid,realname,sex,address,idnumber,academic,housed,marriage," +
            "income,IDImageFountVar,IDImageBackVar,to_char(applyTime,'yyyy-MM-dd') as applyTime," +
            "auditorID,to_char(auditTime,'yyyy-MM-dd HH24:mi:ss') auditTime,auditresult,auditremarks from " +
            "(select rownum rn,r.id,r.userid,r.realname,r.sex,r.address,r.idnumber,r.academic,r.housed,r.marriage," +
            "r.income,r.IDImageFountVar,r.IDImageBackVar,r.applyTime,r.auditorID,r.auditTime,r.auditresult,r.auditremarks from " +
            "REALNAME_CERTIFICATION r  where rownum &lt; #{end}" +
            "<if test=\"realname!=null and realname!=''\">and realname like '%'||#{realname}||'%'</if>"+
            "<if test=\"auditresult!=null and auditresult!=''\">and auditresult=#{auditresult}</if>"+
            ")a where a.rn &gt; #{start}</script>")
    List<Map> getPageByParam(Map map);
    /**
     * 实名认证查询分页总数量
     * @return
     */
    @Select("<script>select count(*) from REALNAME_CERTIFICATION <where>" +
            "<if test=\"realname!=null and realname!=''\">and realname like '%'||#{realname}||'%'</if>"+
            "<if test=\"auditresult!=null and auditresult!=''\">and auditresult=#{auditresult}</if>"+
            "</where></script>")
    int getPageCount(Map map);

    /**
     * 审核通过时更新
     * @param map
     * @return
     */
    @Update("update REALNAME_CERTIFICATION set auditTime=sysdate,auditresult=#{AUDITRESULT},auditremarks=#{AUDITREMARKS} where id=#{ID}")
    int update(Map map);


}
