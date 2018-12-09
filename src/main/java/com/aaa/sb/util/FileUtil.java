package com.aaa.sb.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * className:FileUtil
 * discription:
 * author:wzb
 * createTime:2018-11-23 08:36
 */
public class FileUtil {

    /**
     * 创建文件放到指定位置
     * @param savePath
     * @param multipartFile
     * @return
     */
    public static Map<String,Object> uploadFile(String savePath, MultipartFile multipartFile){
        //获取
        String originalFilename = multipartFile.getOriginalFilename();
        //获取原文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//.jpg
        //拼装新文件名称
        String newFileName = UUID.randomUUID()+suffix;//UUID.randomUUID()随机字符串 "ADAFDFADFADSFADSFADFADSFA.jpg"
        //System.out.println(uploadPath+" "+newFileName);
        File file = new File(savePath+ newFileName);//创建一个文件夹 D:/...files/xxx.jpg
        //调用spring提供的方法文件读写
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("fileName",originalFilename);
        map.put("picPath",newFileName);
        return map;
    }

    /**
     * 通用下载方法
     * @param filename
     * @param response
     * @return
     */
    public static String downLoad(String filename,HttpServletResponse response){
        String filePath = "D:/files" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");//MIME类型
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
           /* int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }*/
                int i=0;
                while((i = bis.read(buffer))!=-1){
                    os.write(buffer,0,i);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}
