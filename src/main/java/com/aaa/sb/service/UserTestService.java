package com.aaa.sb.service;

import com.aaa.sb.entity.UserTest;

import java.util.List;
import java.util.Map;

/**
 * className:UserTestService
 * discription:
 * author:wzb
 * createTime:2018-11-30 20:27
 */
public interface UserTestService {

    UserTest findByUserName(String userName);

    List<Map> selByUserName(String userName);

    UserTest findByUserId(int userId);

    List<Map> selByUserId(int userId);

}
