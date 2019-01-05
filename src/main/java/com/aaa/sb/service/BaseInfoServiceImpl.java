package com.aaa.sb.service;

import com.aaa.sb.dao.BaseInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:BaseInfoServiceImpl
 * discription:
 * author:gwd
 * createTime:2018-12-17 11:35
 */
@SuppressWarnings("all")
@Service
public class BaseInfoServiceImpl implements BaseInfoService{

    @Autowired
    private BaseInfoDao baseInfoDao;

    @Override
    public List<Map> provinceList() {
        return baseInfoDao.provinceList();
    }

    @Override
    public List<Map> getCityByPcode(Integer pcode) {
        return baseInfoDao.getCityByPcode(pcode);
    }

    @Override
    public List<Map> getAreaByCcode(Integer ccode) {
        return baseInfoDao.getAreaByCcode(ccode);
    }

    @Override
    public Map getBaseInfo() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map)session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是："+(userInfo.get("ID")+""));
        return baseInfoDao.getBaseInfo(userInfo.get("ID")+"");
    }

    @Override
    public int baseInfoUpd(Map map) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map)session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是："+(userInfo.get("ID")+""));
        int userId = Integer.valueOf(userInfo.get("ID") + "");
        map.put("userId",userId);
        System.out.println(map);
        int i = baseInfoDao.baseInfoUpd1(map);
        System.out.println("i:"+i);
        int j = baseInfoDao.baseInfoUpd2(map);
        System.out.println("j:"+j);
        if(i>0&&j>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int addCertification(Map map) {
        System.out.println("service层里的map："+map);
        //找到所有省市区的编码
        int province = Integer.valueOf(map.get("province") + "");
        int city = Integer.valueOf(map.get("city") + "");
        int area = Integer.valueOf(map.get("area") + "");
        System.out.println(province+","+city+","+area);
        //根据省市区的编码 来查找相应的名称
        Map pmap = baseInfoDao.findProvincebyId(province);
        Map cmap = baseInfoDao.findCitybyId(city);
        Map amap = baseInfoDao.findAreabyId(area);
        System.out.println(pmap+","+cmap+","+amap);
        //通过得到的map来得到省市区的名称
        String pname = pmap.get("NAME") + "";
        String cname = cmap.get("NAME") + "";
        String aname = amap.get("NAME") + "";
        System.out.println(pname+","+cname+","+aname);
        //声明一个变量将省市区详细地址的名称放入该变量
        String address=null;
        String detail = map.get("detail") + "";
        address=pname+cname+aname+detail;
        System.out.println(address);
        //把address放入map中 传到数据库
        map.put("address",address);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map)session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是："+(userInfo.get("ID")+""));
        int userId = Integer.valueOf(userInfo.get("ID") + "");
        map.put("userId",userId);
        String date1 = map.get("date")+"";
        String time1 = map.get("time")+"";
        String newDate = date1 + " " + time1;
        map.put("newDate",newDate);
        System.out.println("插入后的map为："+map);
        //根据id查找该用户的信息，如果信息里面有值，就删除该条信息，两个表里的都删除，在插入新的信息
        Map info = baseInfoDao.findInfo(userId);
        Map video = baseInfoDao.showPoint1(userId);
        System.out.println(info);
        System.out.println(video);
        if(info!=null&&info.size()>0&&video!=null&&video.size()>0){
            //说明该用户存在  delete该用户
            int i = baseInfoDao.delReal(userId);
            int j = baseInfoDao.delVideo(userId);
            if(i>0&&j>0){
                System.out.println("删除成功");
            }
        }else{
            //如果不存在就不用管
        }
        int j = baseInfoDao.addVideo(map);
        int i = baseInfoDao.addCertification(map);
        if(i>0&&j>0){
            System.out.println("插入成功");
            return 1;
        }
        return 0;
    }

    @Override
    public Map showPoint() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是：" + (userInfo.get("ID") + ""));
        int userId = Integer.valueOf(userInfo.get("ID") + "");
        //查询该用户下的所有信息的审核状态 包括实名信息和视频认证信息
        //视频认证信息
        Map videoMap = baseInfoDao.showPoint1(userId);
        System.out.println("videoMap:"+videoMap);
        //实名信息
        Map realMap = baseInfoDao.showPoint2(userId);
        Map map = new HashMap();
        //说明还未填写信息
        if(videoMap==null||realMap==null){
            map.put("pointId",-2);
            return map;
        }
        System.out.println(videoMap + "," + realMap);
        //获得是否更新的状态
        String video = videoMap.get("AUDITRESULT") + "";
        String real = realMap.get("AUDITRESULT") + "";
        System.out.println(real + "," + video);
        //如果所有的都等于审核成功
        if (video .equals( "未审核" )|| real .equals( "未审核")) {
            map.put("pointId",0);
            return map;
            //如果实名失败 就直接返回实名的失败原因
        }else if((real .equals( "审核失败") && video .equals( "审核通过" ))||(real .equals( "审核失败" )&& video.equals("审核失败"))){
            //如果审核失败 找出审核失败的原因
            String auditremarks = realMap.get("AUDITREMARKS") + "";
            map.put("pointId",-4);
            map.put("errorInfo",auditremarks);
            return map;
        } else if(real.equals( "审核通过" )&& video .equals( "审核失败") ){
            //如果审核失败 找出审核失败的原因
            String auditremarks = videoMap.get("AUDITREMARKS") + "";
            map.put("pointId",-3);
            map.put("errorInfo",auditremarks);
            return map;
        }else {
            Map info = baseInfoDao.findInfo(userId);
            String realname = info.get("REALNAME")+"";
            String sex = info.get("SEX")+"";
            String idnumber = info.get("IDNUMBER")+"";
            String address = info.get("ADDRESS")+"";
            map.put("pointId",1);
            map.put("realname",realname);
            map.put("idnumber",idnumber);
            map.put("sex",sex);
            map.put("address",address);
            return map;
        }
    }

    @Override
    public int checkUserName(String userName) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是：" + (userInfo.get("ID") + ""));
        String userId = userInfo.get("ID") + "";
        Map baseInfo = baseInfoDao.getBaseInfo(userId);
        String username = baseInfo.get("USERNAME") + "";
        System.out.println("未修改的username是："+username);
        System.out.println("修改后的username是："+userName);
        if (userName.equals(username)){
            System.out.println("两个用户名相同，未修改");
            return 1;
        }else{
            //通过username来查找是否有同名的
            List<Map> maps = baseInfoDao.checkUsername(userName);
            if(maps!=null&&maps.size()>0){
                System.out.println("用户名重复，不可以修改");
                return 0;
            }else {
                System.out.println("用户名不重复，可以修改");
                return 1;
            }
        }

    }

    @Override
    public int checkPhone(String phone) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是：" + (userInfo.get("ID") + ""));
        String userId = userInfo.get("ID") + "";
        Map baseInfo = baseInfoDao.getBaseInfo(userId);
        String telephone = baseInfo.get("TELEPHONE") + "";
        System.out.println("未修改的telephone是："+telephone);
        System.out.println("修改后的phone是："+phone);
        if (telephone.equals(phone)){
            System.out.println("两个手机号相同，未修改");
            return 1;
        }else{
            //通过phone来查找是否有同名的
            List<Map> maps = baseInfoDao.checkPhone(phone);
            if(maps!=null&&maps.size()>0){
                System.out.println("手机号已被绑定，不可使用");
                return 0;
            }else {
                System.out.println("手机号未被绑定，可以使用");
                return 1;
            }
        }
    }

    @Override
    public int checkBankCard(String bankcard) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map) session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是：" + (userInfo.get("ID") + ""));
        String userId = userInfo.get("ID") + "";
        Map baseInfo = baseInfoDao.getBaseInfo(userId);
        String card = baseInfo.get("BANKCARD") + "";
        System.out.println("未修改的银行卡号是："+card);
        System.out.println("修改后的phone是："+bankcard);
        if (card.equals(bankcard)){
            System.out.println("两个银行卡号相同，未修改");
            return 1;
        }else{
            //通过phone来查找是否有同名的
            List<Map> maps = baseInfoDao.checkBankCard(bankcard);
            if(maps!=null&&maps.size()>0){
                System.out.println("银行卡已被绑定，不可使用");
                return 0;
            }else {
                System.out.println("银行卡未被绑定，可以使用");
                return 1;
            }
        }
    }

    @Override
    public int checkIDCard(String idcard) {
        List<Map> maps = baseInfoDao.checkIDCard(idcard);
        if(maps!=null&&maps.size()>0){
            //说明有重复的
            return 0;
        }
        return 1;
    }
}
