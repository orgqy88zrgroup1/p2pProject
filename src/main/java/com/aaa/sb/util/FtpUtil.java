package com.aaa.sb.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.util.UUID;

/**
 * className:FtpUtil
 * discription:
 * author:gwd
 * createTime:2018-12-13 09:49
 */
@Component
public class FtpUtil {
    @Autowired
    private FtpConfig ftpConfig;

    private static FTPClient ftp = new FTPClient();

    /**
     * 通用ftp上传方法
     * @param multipartFile
     * @return
     */
    public  String  upLoad(MultipartFile multipartFile){
        // System.out.println(remoteIp+"...."+ftpUserName+","+ftpPassWord);
        //创建客户端对象

        InputStream local = null;
        try {
            //  System.out.println(new FileUpAndDown().remoteIp);
            //连接ftp服务器
            ftp.connect(ftpConfig.getRemoteIp(),ftpConfig.getUploadPort());
            //登录
            ftp.login(ftpConfig.getFtpUserName(),ftpConfig.getFtpPassWord());
            //设置上传路径 到远程
            String path = ftpConfig.getRemotePath();
            //检查上传路径是否存在 如果不存在返回false 相当于切换目录 如果目录存在就直接切换 如果不存在就创建
            boolean flag = ftp.changeWorkingDirectory(path);
            if(!flag){
                //创建上传的路径 该方法只能创建一级目录,在这里如果/home/ftpadmin存在则可以创建image
                ftp.makeDirectory(path);
            }
            //指定上传路径 创建之后将上传目录给他
            ftp.changeWorkingDirectory(path);
            //指定上传文件的类型 二进制文件
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // MultipartFile multipartFile=null;
            //获取文件的绝对路径
            //String absolutePath = multipartFile.getResource().getFile().getAbsolutePath();
            //System.out.println(absolutePath+"绝对路径。。。。。。。。。。。");
            //获取原文件名称
            String originalFilename = multipartFile.getOriginalFilename();
            //组装新的名称 截取后缀名
            String newFileName= UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
            //读取本地文件 文件读到本地 D:/uploadpath/ 创建新的文件空文件
            File file =new File(ftpConfig.getUploadPath()+File.separator+newFileName);
            //传输文件 本地文件上传到ftpConfig.getUploadPath()   D:/uploadpath/
            multipartFile.transferTo(file);
            // org.apache.commons.io.FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
            //  System.out.println(file.length()+"............");
            //读取刚上传的文件到文件输入流中
            local = new FileInputStream(file);
            //第一个参数是文件名 把本地文件输入流传输到远程
            ftp.storeFile(file.getName(),local);
            //返回新文件名
            return newFileName;
        }catch (SocketException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                //关闭文件流
                if(local!=null)
                    local.close();
                //退出
                if(ftp!=null) {
                    ftp.logout();
                    //断开连接
                    ftp.disconnect();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * FTP文件下载
     * @param fileName
     */
    public   void downLoad(String fileName,HttpServletResponse response){
        InputStream local = null;
        OutputStream os =null;
        try {
            //连接ftp服务器
            ftp.connect(ftpConfig.getRemoteIp(),ftpConfig.getUploadPort());
            //登录
            ftp.login(ftpConfig.getFtpUserName(),ftpConfig.getFtpPassWord());

            // System.out.println("开始下载文件");
            //initFtpClient();
            //切换FTP目录
            ftp.changeWorkingDirectory(ftpConfig.getRemotePath());
            //返回该目录下所有文件 ftp://127.0.0.1
            FTPFile[] ftpFiles = ftp.listFiles();
            // ftpFiles  ftp 服务器上该目录下的所有文件 循环遍历 如果文件与想下载的名称一致就进行下载
            for(FTPFile file : ftpFiles){
                //找到文件名称等于要下载的文件名称    equalsIgnoreCase  忽略大小写比较
                if(fileName.equalsIgnoreCase(file.getName())){
                    //ftpConfig.getLocalPath() + "/" + fileName=D:/localfile/4e844773-a5cd-442e-8565-9bd92223ae70.jpg
                    File localFile = new File(ftpConfig.getLocalPath() + "/" + fileName);
                    //os = response.getOutputStream();
                    os =new FileOutputStream(localFile);
                    //从远程读写文件到本地
                    ftp.retrieveFile(file.getName(), os);
                    os.flush();
                    //下载
                    downloadFile(localFile,response);
                    // os.close();
                }
            }
            // ftpClient.logout();
            //System.out.println("下载文件成功");
        } catch (Exception e) {
            // System.out.println("下载文件失败");
            e.printStackTrace();
        } finally{

            try{
                if(ftp.isConnected()) {
                    ftp.disconnect();
                }
                if(null != os) {
                    os.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 通用下载
     * @param file
     * @param response
     * @return
     */
    public static String downloadFile(File file, HttpServletResponse response) {
        // String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
        if (file != null) {
            //设置文件路径   savePath ="d:/images/"   file ="d:/images/1.jpg"
            //  File file = new File(savePath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开 MIME
                response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                  /* int i=0;
                   while((i=bis.read(buffer))!=-1){
                       os.write(buffer, 0, i);
                   }*/
                    // System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}
