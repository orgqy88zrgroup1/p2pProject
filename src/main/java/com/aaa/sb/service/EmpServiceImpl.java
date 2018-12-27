package com.aaa.sb.service;

import com.aaa.sb.dao.EmpDao;
import com.aaa.sb.entity.Emp;
import com.aaa.sb.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * className:EmpServiceImpl
 * discription:
 * author:wzb
 * createTime:2018-12-08 22:19
 */
@Service
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpDao empDao;

    /**
     * 根据用户名查询对应的用户信息 实体
     * @param userName
     * @return
     */
    @Override
    public Emp findByUserName(String userName) {
        return empDao.findByUserName(userName);
    }

    /**
     * 根据用户名查询对应的用户信息 Map
     * @param userName
     * @return
     */
    @Override
    public List<Map> selByUserName(String userName) {
        return empDao.selByUserName(userName);
    }

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    @Override
    public Emp findByUserId(int id) {
        return empDao.findByUserId(id);
    }

    /**
     * 根据用户id查询对应的用户信息
     * @param id
     * @return
     */
    @Override
    public List<Map> selByUserId(int id) {
        return empDao.selByUserId(id);
    }

    /**
     * 员工带参分页查询
     * @param map
     * @return
     */
    @Override
    public List<Map> getPageByParam(Map map) {
        return empDao.getPageByParam(map);
    }

    /**
     * 员工查询分页总数量
     * @param map
     * @return
     */
    @Override
    public int getPageCount(Map map) {
        return empDao.getPageCount(map);
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public int batchDelete(String ids) {
        String[] idsArray = ids.split(",");
        List list = new ArrayList();
        for(String s : idsArray){
            list.add(s);
        }
        //System.out.println(list);
        return empDao.batchDelete(list);
    }

    /**
     * 添加
     * @param map
     * @return
     */
    @Override
    public int add(Map map)throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {


            String password = AESUtil.getInstance().encrypt((map.get("PASSWORD") + ""));
            System.out.println(password);
            map.put("PASSWORD", password);

            return  empDao.add(map);




    }

    /**
     * 更新
     * @param map
     * @return
     */
    @Override
    public int upd(Map map)throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException  {
        String password = AESUtil.getInstance().encrypt((map.get("PASSWORD") + ""));
        System.out.println(password);
        map.put("PASSWORD", password);
        return empDao.upd(map);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int del(Integer id) {
        return empDao.del(id);
    }


    /**
     * 用户名数组
     * @return
     */
    @Override
    public List<Map> selUsernameOfList() {
        return empDao.selUsernameOfList();
    }
}
