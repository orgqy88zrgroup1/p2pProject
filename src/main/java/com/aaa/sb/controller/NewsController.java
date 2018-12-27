package com.aaa.sb.controller;

import com.aaa.sb.service.NewsService;
import com.aaa.sb.util.FtpUtil;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * className:NewsController
 * discription:
 * author:zhangran
 * createTime:2018-12-13 10:34
 */
@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    private FtpUtil ftpUtil;

    @RequestMapping("/toPage")
    public String toPage(){
        return "news/list";
    }

    @ResponseBody
    @RequestMapping("/page")
    public Object list(@RequestParam Map map){
        System.out.println(newsService.getList(map));
        return newsService.getList(map);
    }

}
