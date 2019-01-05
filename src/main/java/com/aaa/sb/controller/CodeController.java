package com.aaa.sb.controller;

import com.aaa.sb.util.ImgCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * className:CodeController
 * discription:
 * author:gwd
 * createTime:2018-12-12 20:03
 */
@Controller
@RequestMapping("/getCode")
public class CodeController {
    private final ResourceLoader resourceLoader;
    @Autowired
    public CodeController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 取出配置文件中upload.path的值，赋给uploadPath类变量
     */
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 获取随机图片验证码
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCheckPic")
    public Map<String, String> getCheckPic() {
        return ImgCheckUtil.setPic();
    }

    /**
     * 图片展示
     * @param fileName
     * @return
     */
    @RequestMapping("showPic")
    public ResponseEntity show(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + uploadPath + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
