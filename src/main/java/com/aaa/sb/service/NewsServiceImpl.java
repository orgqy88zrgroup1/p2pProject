package com.aaa.sb.service;

import com.aaa.sb.dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:NewsServiceImpl
 * discription:
 * author:zhangran
 * createTime:2018-12-17 14:37
 */

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public List<Map> getList(Map map) {
        List<Map> list = newsDao.getList(map);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("STATE")!=null&&list.get(i).get("STATE")!=null){
                list.get(i).put("STATE",false);
            }
        }
        return list;
    }
}
