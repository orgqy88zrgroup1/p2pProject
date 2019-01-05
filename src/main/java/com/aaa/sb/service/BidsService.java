package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

public interface BidsService {
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
     * 审核（审核表）
     * @param map
     * @return
     */
    int update(Map map);

    /**
     * 审核通过（标状态）
     * @param map
     * @return
     */
    int upd(Map map);
    /**
     * 审核失败（标状态）
     * @param map
     * @return
     */
    int upda(Map map);
}
