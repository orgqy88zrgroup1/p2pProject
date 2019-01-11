package com.aaa.sb.service;

import com.aaa.sb.dao.BidsDao;
import com.aaa.sb.dao.EmpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * className:BidsServiecImpl
 * discription:
 * author:ZXL
 */
@Service
public class BidsServiecImpl implements BidsService {
    @Autowired
    private BidsDao bidsDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private EmpDao empDao;
    @Override
    public List<Map> getPageByParam(Map map) {

        return bidsDao.getPageByParam(map);
    }

    @Override
    public int getPageCount(Map map) {
        return bidsDao.getPageCount(map);
    }

    @Override
    public int update(Map map) {
        //审核人（当前登录用户）
        String username = session.getAttribute("username").toString();
        //审核人auditid
        Integer auditid = Integer.valueOf(empDao.selByUserName(username).get(0).get("ID").toString());
        map.put("auditid",auditid);
        return bidsDao.update(map);
    }

    @Override
    public int upd(Map map) {
            return bidsDao.upd(map);
    }

    @Override
    public int upda(Map map) {
        return bidsDao.upda(map);
    }
}
