package com.aaa.sb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * className:SpringBootMainApplication
 * discription:
 * author:ZXL
 */
@SpringBootApplication
@MapperScan("com.aaa.sb.dao")//启动类进行扫描
public class SpringbootMainApplication {
    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        //获取一个类的class对象
        SpringApplication.run(SpringbootMainApplication.class);
    }
}
