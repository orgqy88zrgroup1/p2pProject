package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

public interface CapitalService {

    /**
     * 分页查询资金记录
     * @param map
     * @return
     */
    List<Map> getparam(Map map);

    /**
     * 查询资金记录总数量
     * @param map
     * @return
     */
    int getCount(Map map);

    /**
     * 投资记录分页查询
     * @param map
     * @return
     */
    List<Map> getparams(Map map);

    /**
     * 查询投资记录总数量
     * @param map
     * @return
     */
    int getCounts(Map map);
}
