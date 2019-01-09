package com.aaa.sb.service;

import com.aaa.sb.dao.RoleDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     * 角色列表查询分页
     * 条件查询
     * @return
     */
    @Override
    public Map getRolePage(Map map) {
        //设置当前第几页，每页显示数量
        PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("pageSize"));
        //用PageInfo对结果进行包装
        PageInfo<Map> pageInfo = new PageInfo<Map>(roleDao.getRolePage(map));
        Map resultMap = new HashMap();
        //获取当前页数据
        resultMap.put("pageData", pageInfo.getList());
        //获取分页总数量
        resultMap.put("pageTotal",pageInfo.getTotal());

        return resultMap;
    }

    /**
     * 角色添加
     * @param map
     * @return
     */
    @Override
    public int addRole(Map map) {
        return roleDao.addRole(map);
    }

    /**
     * 角色更新
     * @param map
     * @return
     */
    @Override
    public int updRole(Map map) {
        return roleDao.updRole(map);
    }

    /**
     * 角色删除
     * @param id
     * @return
     */
    @Override
    public int delRole(int id) {
        if(id==1){
            return 3;
        }else{
            int i = roleDao.delRole(id);
            if(i>0){
                return 1;
            }else{
                return 0;
            }
        }

    }

    /**
     * 批量添加 tb_role_power
     * @return
     */
    @Override
    public int batchAdd(Map map) {


        //System.out.println(map.get("roleList")); [{roleid=1, powerid=2}, {roleid=1, powerid=9}]
        //得到list map(roleid: powerid:)

        List<Map> list = (List<Map>) map.get("roleList");
        //System.out.println("进入服务层");
        //System.out.println(list);
        //return 0;

        //删除该角色之前关联的权限
        Integer roleid = Integer.valueOf(list.get(0).get("roleid") + "");
        //System.out.println(roleid);
        int r = roleDao.delRoleAndPower(roleid);



        int i = roleDao.batchAdd(list);

        if(i>0&&r>0){
            //sqlSession.commit()
            return 1;
        }else{
            //sqlSession.rollback()
            return 0;
        }

    }


    /**
     * 根据roleid查询tb_power表具体的数据
     * @return
     */
    @Override
    public List<Map> selRoleIdAndPowerId(Map map) {
        return roleDao.selRoleIdAndPowerId(map);
    }


}
