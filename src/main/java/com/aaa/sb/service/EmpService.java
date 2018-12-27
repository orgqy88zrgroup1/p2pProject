package com.aaa.sb.service;

import com.aaa.sb.entity.Emp;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * className:EmpService
 * discription:
 * author:wzb
 * createTime:2018-12-08 22:16
 */
public interface EmpService {

    /**
     * 根据用户名查询对应的用户信息 实体
     * @param userName
     * @return
     */
    Emp findByUserName(String userName);

    /**
     * 根据用户名查询对应的用户信息 Map
     * @param userName
     * @return
     */
    List<Map> selByUserName(String userName);

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    Emp findByUserId(int id);

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    List<Map> selByUserId(int id);

    /**
     * 查询所有用户名
     * @return
     */
    List<Map> selUsernameOfList();

    /**
     * 员工带参分页查询
     * @param map
     * @return
     */
    List<Map> getPageByParam(Map map);

    /**
     * 员工查询分页总数量
     * @param map
     * @return
     */
    int getPageCount(Map map);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(String ids);

    /**
     * 添加
     * @param map
     * @return
     */
    int add(Map map) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    /**
     * 更新
     * @param map
     * @return
     */
    int upd(Map map) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException ;

    /**
     * 删除
     * @param id
     * @return
     */
    int del(Integer id);


}
