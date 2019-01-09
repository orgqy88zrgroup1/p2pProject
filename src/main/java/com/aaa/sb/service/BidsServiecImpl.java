package com.aaa.sb.service;

import com.aaa.sb.dao.BidsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //System.out.println(map);
        //System.out.println(map.get("BIDSTATE"));
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
