package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * className:Bid_one_auditDao
 * discription:
 * author:wuyanle
 * createTime:2018-12-18 15:58
 */
public interface Bid_one_auditDao {
    /**
     * 满标一审查询
     * @return
     */
    @Select("<script> select id,username,name,userID,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,bidIssueDate,bidDeadDay,bidDeadDate,bidApplyDate,bidDesc,bidType,bidState from " +
            " (select rownum rn,b.id,u.username,s.name,userID,bidAmount,bidCurrentAmount,bidRepaymentMethod,bidRate,bidDeadline,to_char(bidIssueDate ,'yyyy-mm-dd') as bidIssueDate,bidDeadDay,to_char(bidDeadDate ,'yyyy-mm-dd') as bidDeadDate,to_char(bidApplyDate ,'yyyy-mm-dd') as bidApplyDate,bidDesc,bidType,bidState from bid_info b" +
            " left join user_login_info u on b.userID=u.ID" +
            " left join bid_state s on b.bidState=s.ID" +
            "   where rownum &gt; #{start}  and bidState=3 ) a where a.rn &lt; #{end} </script>")
    List<Map> getbidList(Map map);
    /**
     * 查询分页总数量1
     * @param map
     * @return
     */
    @Select("<script> select count(*) from bid_info where bidState=3 </script>")
    int getPageCount(Map map);

    /**
     * 满标审核通过
     * @param map
     * @return
     */
    @Update("update bid_info set bidState=4 where id=#{id}")
    int updatetongguo(Map map);

    /**
     * 满标审核驳回
     * @param map
     * @return
     */
    @Update("update bid_info set bidState=6 where id=#{id}")
    int updatebohui(Map map);
}
