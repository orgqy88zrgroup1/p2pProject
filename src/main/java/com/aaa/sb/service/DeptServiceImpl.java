package com.aaa.sb.service;

import com.aaa.sb.dao.DeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:DeptServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-12-09 13:13
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    /**
     * 部门列表查询 部门状态为启用
     * @return
     */
    @Override
    public List<Map> getDeptList() {
        return deptDao.getDeptList();
    }





}
