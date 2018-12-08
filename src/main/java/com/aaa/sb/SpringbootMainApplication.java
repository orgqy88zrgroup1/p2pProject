package com.aaa.sb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * className:SpringbootMainApplication
 * discription:
 * author:wzb
 * createTime:2018-12-08 19:19
 */
@SpringBootApplication
@MapperScan("com.aaa.sb.dao") //扫描dao层接口
public class SpringbootMainApplication {
    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootMainApplication.class);
    }
}
