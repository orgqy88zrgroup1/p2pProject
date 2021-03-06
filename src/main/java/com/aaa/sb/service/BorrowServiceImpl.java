package com.aaa.sb.service;

import com.aaa.sb.dao.BorrowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:BorrowServiceImpl
 * discription:
 * author:zhangran
 * createTime:2018-12-26 15:36
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    BorrowDao borrowDao;

    @Override
    public int toBorrow(Map map) {
        return borrowDao.toBorrow(map);
    }

    @Override
    public Map toGetCheck(Map map) {
        Map realList = borrowDao.getRealList(map);
        Map videoList = borrowDao.getVideoList(map);
        Map map1 = new HashMap();
        if(realList==null||videoList==null){
            map1.put("check","尚未完成认证");
        }
        return map1;
    }
}
