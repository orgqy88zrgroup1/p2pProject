package com.aaa.sb.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * className:BaseInfoDao
 * discription:
 * author:gwd
 * createTime:2018-12-17 11:32
 */
public interface BaseInfoDao {

    /**
     * 得到所有的省份
     * @return
     */
    @Select(value = "select id,code,name from province")
    List<Map> provinceList();

    /**
     * 通过省份id查找市的信息
     * @param pcode
     * @return
     */
    @Select(value = "select id,name,code,provincecode from city where provincecode=#{pcode}")
    List<Map> getCityByPcode(Integer pcode);

    /**
     * 通过市id查找县
     * @param ccode
     * @return
     */
    @Select(value = "select id,name,code,citycode from area where citycode=#{ccode}")
    List<Map> getAreaByCcode(Integer ccode);

    /**
     * 根据userid连表查询得到用户名，密码，电话
     * @param userId
     * @return
     */
    @Select(value = "select ul.username,ul.password,ul.telephone,ui.email,ui.headimage,ui.bankcard,ui.paypwd from user_login_info ul left join user_info ui on ul.id=ui.userid where ul.id=#{userId}")
    Map getBaseInfo(String userId);

    /**
     * 修改用户的基本信息user_info
     * @param map
     * @param
     * @return
     */
    @Update(value = "update user_info set email=#{EMAIL},headimage=#{HEADIMAGE},bankcard=#{BANKCARD},paypwd=#{PAYPWD} where userid=#{userId} ")
    int baseInfoUpd1(Map map);

    /**
     * 修改用户的基本信息user_login_info
     * @param map
     * @param
     * @return
     */
    @Update(value = "update user_login_info set telephone=#{TELEPHONE},password=#{PASSWORD},username=#{USERNAME} where id=#{userId} ")
    int baseInfoUpd2(Map map);

    /**
     * 将页面的实名信息插入到实名认证表
     * @param map
     * @return
     */
    @Insert(value = "insert into realname_certification(id,userID,realname,sex,address,IDNumber,academic,housed,marriage,income,IDImageFountVar,IDImageBackVar,applyTime，auditResult) " +
            " values(SEQ_REALNAME_CERTIFICATION_ID.nextval,#{userId},#{name},#{sex},#{address},#{idcard},#{study},#{housed},#{marriage},#{income},#{fountImg},#{backImg},sysdate,'未审核') ")
    int addCertification(Map map);

    /**
     * 将预约时间插入到视频认证表中
     * @param map
     * @return
     */
    @Insert(value = "insert into video_audit(id,userId,appointmentTime,auditResult) values(SEQ_VIDEO_AUDIT_ID.nextval,#{userId},to_date(#{newDate},'yyyy-mm-dd HH:mi:ss'),'未审核') ")
    int addVideo(Map map);

    /**
     * 根据省份id查找省份名称
     * @param pid
     * @return
     */
    @Select(value = "select id,code,name from province where code=#{pid}")
    Map findProvincebyId(int pid);

    /**
     * 根据市id查找市名称
     * @param cid
     * @return
     */
    @Select(value = "select id,code,name from city where code=#{cid}")
    Map findCitybyId(int cid);

    /**
     * 根据省份id查找省份名称
     * @param aid
     * @return
     */
    @Select(value = "select id,code,name from area where code=#{aid}")
    Map findAreabyId(int aid);

    /**
     * 根据用户id查找该用户信息的审核状态状态
     * @param userId
     * @return
     */
    @Select(value = "select auditresult,auditremarks from video_audit where userid=#{userId}")
    Map showPoint1(int userId);

    /**
     * 根据用户id查找该用户信息的审核状态状态
     * @param userId
     * @return
     */
    @Select(value = "select auditresult,auditremarks from realname_certification where userid=#{userId}")
    Map showPoint2(int userId);

    /**
     * 根据用户id来找实名认证的基本信息
     * @param userId
     * @return
     */
    @Select(value = "select realname,sex,idnumber,address from REALNAME_CERTIFICATION where userId=#{userId}")
    Map findInfo(int userId);

    /**
     * 在实名表中删除该条用户信息
     * @param userId
     * @return
     */
    @Delete(value = "delete from REALNAME_CERTIFICATION where userid=#{userId}")
    int delReal(int userId);

    /**
     * 在视频表中删除该条用户信息
     * @param userId
     * @return
     */
    @Delete(value = "delete from video_audit where userid=#{userId}")
    int delVideo(int userId);

    /**
     * 查找用户名是否相同
     * @param userName
     * @return
     */
    @Select(value = "select id,userName,password,telephone from user_login_info where username=#{userName}")
    List<Map> checkUsername(String userName);

    /**
     * 查找手机号是否相同
     * @param phone
     * @return
     */
    @Select(value = "select id,userName,password,telephone from user_login_info where telephone=#{phone}")
    List<Map> checkPhone(String phone);

    /**
     * 查看银行卡号是否被绑定
     * @param bankcard
     * @return
     */
    @Select(value = "select * from user_info where bankcard=#{bankcard}")
    List<Map> checkBankCard(String bankcard);

    /**
     * 查看身份证号是否重复
     * @param idcard
     * @return
     */
    @Select(value = "select realname from REALNAME_CERTIFICATION where idnumber=#{idcard}")
    List<Map> checkIDCard(String idcard);
}
