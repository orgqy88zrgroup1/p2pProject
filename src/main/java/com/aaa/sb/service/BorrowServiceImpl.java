package com.aaa.sb.service;

import com.aaa.sb.dao.BorrowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Map> toBorrow(Map map) {
        return borrowDao.toBorrow(map);
    }
}
