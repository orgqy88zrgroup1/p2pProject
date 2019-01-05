package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface VideoDao {
    /**
     * 视频认证分页
     * @param map
     * @return
     */
    @Select("<script>select id,realname,to_char(appointmentTime,'yyyy-MM-dd HH24:mi:ss') as appointmentTime,to_char(appointTime,'yyyy-MM-dd HH24:mi:ss') as appointTime," +
            "auditorID,to_char(auditTime,'yyyy-MM-dd HH24:mi:ss') as auditTime,auditresult,auditRemarks from " +
            "(select rownum rn,v.id,r.realname,v.appointmentTime,v.appointTime,v.auditorID,v.auditTime,v.auditresult,v.auditRemarks from " +
            "video_audit v  join realname_certification r on r.userid=v.userid where rownum &lt; #{end}" +
            "<if test=\"realname!=null and realname!=''\">and r.realname like '%'||#{realname}||'%'</if>"+
            "<if test=\"auditresult!=null and auditresult!=''\">and v.auditresult=#{auditresult}</if>"+
            ")a where a.rn &gt; #{start}</script>")
    List<Map> getPageByParam(Map map);

    /**
     * 视频分页总数量
     * @param map
     * @return
     */
    @Select("<script>select count(*) from video_audit <where>" +
            "<if test=\"realname!=null and realname!=''\">and realname like '%'||#{realname}||'%'</if>"+
            "<if test=\"auditresult!=null and auditresult!=''\">and auditresult=#{auditresult}</if>"+
            "</where></script>")
    int getPageCount(Map map);
    /**
     * 审核通过时更新
     * @param map
     * @return
     */
    @Update("update video_audit set appointTime=to_date(#{APPOINTTIME},'yyyy-MM-dd HH24:mi:ss'),auditTime=sysdate,auditresult=#{AUDITRESULT},auditremarks=#{AUDITREMARKS} where id=#{ID}")
    int update(Map map);
}
