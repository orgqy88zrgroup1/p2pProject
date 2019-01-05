package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

public interface VideoService {
    /**
     * 带参分页查询
     * @param map
     * @return
     */
    List<Map> getPageByParam(Map map);

    /**
     * 分页总数量
     * @param map
     * @return
     */
    int getPageCount(Map map);

    /**
     * 审核通过
     * @param map
     * @return
     */
    int update(Map map);
}
