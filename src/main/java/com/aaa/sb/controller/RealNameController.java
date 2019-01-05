package com.aaa.sb.controller;

import com.aaa.sb.service.RealNameService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * className:RealNameController
 * discription:
 * author:ZXL
 */
@Controller
@RequestMapping("/realname")
public class RealNameController {
    @Autowired
    private RealNameService realNameService;

    //依赖注入ftp工具类
    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private FtpConfig ftpConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 跳转招标页面
     * @return
     */
    @RequestMapping("/toLists")
    public String toLists(){
        return "ifb/ifb";
    }
    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "realname/realnames";
    }
    /**
     * 分页
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public Object list(@RequestBody Map map){
        Map resultMap=new HashMap();
        resultMap.put("pageData",realNameService.getPageByParam(map));
        resultMap.put("total",realNameService.getPageCount(map));
        return resultMap;
    }

    /**
     * 更新
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Object update(@RequestBody Map map ){
        return realNameService.update(map);
    }

    /**
     * 显示Ftp图片
     * @param fileName
     * @return
     */
    @RequestMapping("/show")
    public ResponseEntity show(String fileName){
        try {
            return ResponseEntity.ok(resourceLoader.getResource("ftp://"+ftpConfig.getFtpUserName()+":"+ftpConfig.getFtpPassWord()+"@"+ ftpConfig.getRemoteIp()+ftpConfig.getRemotePath() + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
