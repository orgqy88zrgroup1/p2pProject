package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface NewsDao {

    @Select(value = "select STATE,TIP,ID,CONTENT,to_char(CREATEDATE,'yyyy-MM-dd  HH:mm:ss') CREATEDATE from tb_news order by createdate desc")
    List<Map> getList(Map map);
}
