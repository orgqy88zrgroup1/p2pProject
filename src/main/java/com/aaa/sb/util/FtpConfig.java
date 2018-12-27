package com.aaa.sb.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * className:FtpConfig
 * discription:
 * author:gwd
 * createTime:2018-12-13 09:26
 */
@Component
@PropertySource("classpath:ftp.properties")
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {
    private String remoteIp;
    private Integer uploadPort;
    private String ftpUserName;
    private String ftpPassWord;
    private String remotePath;
    private String localPath;
    private String uploadPath;

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public Integer getUploadPort() {
        return uploadPort;
    }

    public void setUploadPort(Integer uploadPort) {
        this.uploadPort = uploadPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassWord() {
        return ftpPassWord;
    }

    public void setFtpPassWord(String ftpPassWord) {
        this.ftpPassWord = ftpPassWord;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    /*public static void main(String[] args) {
        //自己写方法读取配置文件
        InputStream inputStream = FtpConfig.class.getResourceAsStream("/application.properties");
        //Map properties = new Properties();//map 接口的一个实现类 多态向上转型
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            Set<Object> objects = properties.keySet();//返回所有key的集合
            if(objects!=null&&objects.size()>0){
                //objects.iterator(); 遍历map集合 至少知道两个方法 得到所有key 再interator
                for (Object object : objects) {
                    System.out.println("key:"+object+",value:"+properties.get(object));
                }
            }else {
                System.out.println("为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    /*public static void main(String[] args) {
        //自己编写的读取配置文件的方法
        InputStream inputStream = FtpConfig.class.getResourceAsStream("/ftp.properties");
        Properties properties =new Properties();//Map接口的实现类
        try {
            properties.load(inputStream);
            //System.out.println(properties.get("mybatis.typeAliasesPackage")+"............");
            Set<Object> objects = properties.keySet();//返回map的所有key
            //objects.iterator(); 遍历map至少要知道2种方法
            for (Object object : objects) {
                System.out.println("key:"+object+",value:"+properties.get(object));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
