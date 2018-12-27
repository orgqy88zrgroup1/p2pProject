package com.aaa.sb.service;

import com.aaa.sb.dao.DeptDao;
import com.aaa.sb.dao.EmpDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private EmpDao empDao;
    @Autowired
    private HttpSession session;

    /**
     * 部门列表查询 部门状态为启用
     * @return
     */
    @Override
    public List<Map> getDeptList() {
        return deptDao.getDeptList();
    }

    /**
     * 部门列表查询分页 部门状态为启用
     * 条件查询 部门编号 部门名称 部门状态
     * @return
     */
    @Override
    public Map getDeptPage(Map map) {

        //设置当前第几页，每页显示数量
        PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("pageSize"));
        //用PageInfo对结果进行包装
        PageInfo<Map> pageInfo = new PageInfo<Map>(deptDao.getDeptPage(map));
        Map resultMap = new HashMap();
        //获取当前页数据
        resultMap.put("pageData", pageInfo.getList());
        //获取分页总数量
        resultMap.put("pageTotal",pageInfo.getTotal());

        return resultMap;
    }

    /**
     * 部门添加
     * @param map
     * @return
     */
    @Override
    public int addDept(Map map) {
        return deptDao.addDept(map);
    }

    /**
     * 部门更新
     * @param map
     * @return
     */
    @Override
    public int updDept(Map map) {
        return deptDao.updDept(map);
    }

    /**
     * 部门删除
     * @param id
     * @return
     */
    @Override
    public int delDept(int id) {
        int deptno = id;
        List<Map> empListByDeptNo = empDao.selByDeptNo(deptno);
        //如果该部门下有员工，list不为空，则不能删除该部门，返回0
        if(empListByDeptNo!=null&&empListByDeptNo.size()>0){
            return 0;
        }else{
            return deptDao.delDept(id);

        }
    }


    /**
     * 批量删除
     * @param
     * @return
     */
    @Override
    public int batchDelete(String ids) {
        String[] idsArray = ids.split(",");

        List list = new ArrayList();

        for(String s : idsArray){
            list.add(s);
        }
        int i = deptDao.batchDelete(list);

        if(i>0){
            //sqlSession.commit()
            return 1;
        }else{
            //sqlSession.rollback()
            return 0;
        }
    }

    /**
     * 改变部门状态
     * @param map
     * @return
     */
    @Override
    public int updDeptState(Map map) {

        String userName = (String) session.getAttribute("username");
        //System.out.println(userName);
        Map userMap = empDao.selByUserName(userName).get(0);
        Integer deptnoUser = Integer.valueOf(userMap.get("DEPTNO") + "");
        //System.out.println(deptnoUser);
        Integer state = Integer.valueOf(map.get("state") + "");
        Integer deptno = Integer.valueOf(map.get("deptno") + "");
        //System.out.println(deptno);

        //System.out.println(map);
        //如果
        //禁用开发部 部门编号为1 员工表部门编号为1的员工roleid修改为7
        //启用开发部 部门编号为1 员工表部门编号为1的员工roleid修改为之前的roleid

        //开始禁用
        if (state == 1) {
            //如果当前用户的部门编号等于禁用的部门编号 则返回0
            if(deptnoUser == deptno){
                return 0;
            }else{
                //先从员工表 拿到禁用的部门编号为1的roleid与id的集合     员工 ID ROLEID 部门编号 DEPTNO
                List<Map> roleIdUseList = empDao.selRoleIdUse(map);
                //System.out.println(roleIdUseList);
                int b = 0;
                //然后加入到新的表中 tb_emp_dept 员工 ID ROLEID 部门编号 DEPTNO
                for (Map roleMap : roleIdUseList) {
                    //System.out.println(roleMap);
                    b = empDao.addStopUseIdAndRoleId(roleMap);
                }

                //部门状态为1时，改变部门状态为2,此时部门为禁用状态
                //该部门下所有员工将失去权限 修改权限状态为7 不可用
                int i = empDao.updRoleId(map);
                //System.out.println(i);
                //System.out.println(b);
                if (i > 0 && b > 0) {
                    //返回部门状态为2
                    return deptDao.updDeptState1(map);
                }else{
                    return 0;
                }
            }

        } else if (state == 2) {
            //开始启用
            //根据部门编号查询 tb_emp_dept 表得到被禁用的字段数据 EMPID ROLEID DEPTNO ID
            List<Map> startList = empDao.selEmpDeptIdAndRoleId(map);
            int i = 0;
            int c = 0;
            //部门状态为2时，改变部门状态为1,此时部门为启用状态
            //ID DEPTNO EMPID ROLEID
            for (Map startMap : startList) {
                //roleMap 存放一个一个之前可用roleid
                //根据部门编号修改
                //System.out.println(startMap);
                i = empDao.updRoleIdUse(startMap);
                c = empDao.delEmpDeptId(startMap);
                //System.out.println(i);
                //System.out.println(c);
            }

            if (i > 0 && c > 0) {
                //System.out.println(33333);
                return deptDao.updDeptState2(map);
            }else{
                return 0;
            }
        }

                return 0;
    }



}
