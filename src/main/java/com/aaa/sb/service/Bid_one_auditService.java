package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

/**
 * className:Bid_one_auditService
 * discription:
 * author:wuyanle
 * createTime:2018-12-18 16:45
 */
public interface Bid_one_auditService {
    /**
     * 满标一审查询
     * @return
     */
    Map getbidList(Map map);
    /**
     * 满标审核通过
     * @param map
     * @return
     */
    int updatetongguo(Map map);
    /**
     * 满标审核驳回
     * @param map
     * @return
     */
    int updatebohui(Map map);
}
