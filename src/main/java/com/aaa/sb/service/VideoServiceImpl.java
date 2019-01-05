package com.aaa.sb.service;

import com.aaa.sb.dao.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:VideoServiceImpl
 * discription:
 * author:ZXL
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao videoDao;
    @Override
    public List<Map> getPageByParam(Map map) {
        return videoDao.getPageByParam(map);
    }

    @Override
    public int getPageCount(Map map) {
        return videoDao.getPageCount(map);
    }

    @Override
    public int update(Map map) {
        return videoDao.update(map);
    }
}
