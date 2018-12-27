package com.aaa.sb.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BorrowDao {

    @Insert(value="<script>insert into bid_info values (seq_bidinfo_id.nextval,#{userId},#{count},0,#{method},0.10,#{date},sysdate,#{time},sysdate,sysdate," +
            "<if test=\"type==1\">'车贷'</if>" +
            "<if test=\"type==2\">'房贷'</if>" +
            "<if test=\"type==3\">'置办物品'</if>" +
            ",#{type},1)</script>")
    List<Map> toBorrow(Map map);
}
