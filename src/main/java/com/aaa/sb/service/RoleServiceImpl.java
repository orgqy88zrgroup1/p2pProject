package com.aaa.sb.service;

import com.aaa.sb.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:RoleServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-12-10 18:43
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 职位列表查询
     * @return
     */
    @Override
    public List<Map> getRoleList() {
        return roleDao.getRoleList();
    }

}
