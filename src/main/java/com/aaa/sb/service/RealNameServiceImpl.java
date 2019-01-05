package com.aaa.sb.service;

import com.aaa.sb.dao.RealNameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:RealNameServiceImpl
 * discription:
 * author:ZXL
 */
@Service
public class RealNameServiceImpl implements RealNameService{
    @Autowired
    private RealNameDao realNameDao;
    @Override
    public List<Map> getPageByParam(Map map) {
        return realNameDao.getPageByParam(map);
    }

    @Override
    public int getPageCount(Map map) {
        return realNameDao.getPageCount(map);
    }

    @Override
    public int update(Map map) {
        return realNameDao.update(map);
    }


}
