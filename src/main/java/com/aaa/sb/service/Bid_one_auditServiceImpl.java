package com.aaa.sb.service;

import com.aaa.sb.dao.Bid_one_auditDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:bid_one_auditServiceImpl
 * discription:
 * author:wuyanle
 * createTime:2018-12-18 16:47
 */
@Service
@SuppressWarnings("all")
public class Bid_one_auditServiceImpl implements Bid_one_auditService {
    /**
     * 依赖注入
     */
    @Autowired
    private Bid_one_auditDao bidOneAuditDao;

    /**
     * 一审满标分页查询
     * @param map
     * @return
     */
    @Override
    public Map getbidList(Map map) {

        List<Map> maps = bidOneAuditDao.getbidList(map);
        int count = bidOneAuditDao.getPageCount(map);
        HashMap resultMap=new HashMap();
        resultMap.put("pageData",maps);
        resultMap.put("total",count);

        return resultMap;
    }

    /**
     * 一审审核通过
     * @param map
     * @return
     */
    @Override
    public int updatetongguo(Map map) {
        return bidOneAuditDao.updatetongguo(map);
    }

    /**
     * 一审审核驳回
     * @param map
     * @return
     */
    @Override
    public int updatebohui(Map map) {
        return bidOneAuditDao.updatebohui(map);
    }
}
