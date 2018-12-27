package com.aaa.sb.service;

import java.util.List;
import java.util.Map;

/**
 * className:BaseInfoService
 * discription:
 * author:gwd
 * createTime:2018-12-17 11:34
 */
public interface BaseInfoService {

    /**
     * 得到所有的省份
     * @return
     */
    List<Map> provinceList();

    /**
     * 通过省份id查找市的信息
     * @param pcode
     * @return
     */
    List<Map> getCityByPcode(Integer pcode);

    /**
     * 通过市id查找区的信息
     * @param ccode
     * @return
     */
    List<Map> getAreaByCcode(Integer ccode);

    /**
     * 根据userid连表查询得到用户名，密码，电话
     * @param
     * @return
     */
    Map getBaseInfo();

    /**
     * 根据用户填写的信息更新两个表
     * @param map
     * @return
     */
    int baseInfoUpd(Map map);

    /**
     * 将页面的实名信息插入到实名认证表
     * @param map
     * @return
     */
    int addCertification(Map map);

    /**
     * 根据用户id查找该用户信息的审核状态状态
     * @param
     * @return
     */
    Map showPoint();


}
