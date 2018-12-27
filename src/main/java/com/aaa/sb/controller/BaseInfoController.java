package com.aaa.sb.controller;

import com.aaa.sb.service.BaseInfoService;
import com.aaa.sb.util.FtpConfig;
import com.aaa.sb.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * className:BaseInfoController
 * discription:
 * author:gwd
 * createTime:2018-12-17 11:37
 */
@Controller
@RequestMapping("/baseInfo")
public class BaseInfoController {

    //依赖注入ftp工具类
    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private FtpConfig ftpConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private BaseInfoService baseInfoService;

    /**
     * 跳转到用户详细信息页面
     * @return
     */
    @RequestMapping("/info")
    public String userInfo(){
        return "/userInfo/userInfo";
    }

    /**
     * 查询所有省份
     */
    @ResponseBody
    @RequestMapping("/provinceList")
    public Object getProvince(){
        List<Map> maps = baseInfoService.provinceList();
        if (maps!=null&&maps.size()>0){
            return maps;
        }else{
            return null;
        }
    }

    /**
     * 通过省份id查找市的信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCity")
    public Object getCityByPcode(@RequestBody Map map){
        System.out.println(map.get("pcode")+"");
        List<Map> cityInfo = baseInfoService.getCityByPcode(Integer.valueOf(map.get("pcode")+""));
        if(cityInfo!=null&&cityInfo.size()>0){
            System.out.println(cityInfo);
            return cityInfo;
        }else{
            System.out.println("市的信息为空");
            return null;
        }
    }

    /**
     * 通过省份id查找市的信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/getArea")
    public Object getAreaByCcode(@RequestBody Map map){
        System.out.println(map.get("ccode")+"");
        /*ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Map userInfo = (Map)session.getAttribute("userInfo");
        System.out.println("现在登录的用户id是："+(userInfo.get("ID")+""));*/
        List<Map> areaInfo = baseInfoService.getAreaByCcode(Integer.valueOf(map.get("ccode") + ""));
        if(areaInfo!=null&&areaInfo.size()>0){
            System.out.println(areaInfo);
            return areaInfo;
        }else {
            System.out.println("区的信息为空");
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/userBaseInfo")
    public Object getBaseInfo(){
        Map baseInfo = baseInfoService.getBaseInfo();
        System.out.println(baseInfo);
        if(baseInfo!=null&&baseInfo.size()>0){
            return baseInfo;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/update")
    public int baseInfoUpd(@RequestBody Map map){
        System.out.println("页面传来的map为："+map);
        int i = baseInfoService.baseInfoUpd(map);
        if(i==1){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 上传方法
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/upLoadPic")
    public Object upLoadPic(@RequestParam MultipartFile file){
        String s = ftpUtil.upLoad(file);//调用上传方法，返回新文件的名称
        System.out.println("新文件的名称为："+s);
        return s;
    }


    /**
     * 下载方法
     * @param fileName
     * @param response
     */
    @RequestMapping("/downloadPic")
    public void downloadPic(String fileName, HttpServletResponse response){
        ftpUtil.downLoad(fileName,response);
    }

    /**
     * 显示Ftp图片
     * @param fileName
     * @return
     */
    @RequestMapping("/show")
    public ResponseEntity show(String fileName){
        try {
            //  ftp://192.168.1.14/98f20a5d-7304-41c7-ac5a-db07d2aaffd3.png
            return ResponseEntity.ok(resourceLoader.getResource("ftp://"+ftpConfig.getFtpUserName()+":"+ftpConfig.getFtpPassWord()+"@"+ ftpConfig.getRemoteIp()+ftpConfig.getRemotePath() + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * 将用户的实名信息加入到数据库
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRealName")
    public int addRealName(@RequestParam Map map){
        System.out.println("要插入数据库的信息是："+map);
        int i = baseInfoService.addCertification(map);
        if(i>0){
            return 1;
        }
        return 0;
    }

    /**
     * 数据库查找该用户信息的审核状态
     * @return
     */
    @ResponseBody
    @RequestMapping("/showPoint")
    public Map showPoint(){
        Map i = baseInfoService.showPoint();
        System.out.println(i);
        //如果返回1 说明都审核通过 如果返回0 说明未审核 如果返回-1 说明审核失败 如果是-2说明还未提交
        return i;
    }

}
