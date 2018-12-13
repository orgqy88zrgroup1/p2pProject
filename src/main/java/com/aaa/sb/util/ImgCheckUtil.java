package com.aaa.sb.util;

//该类包含一些用来查找 ImageReader 和 ImageWriter 以及执行简单编码和解码的静态便捷方法
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * className:ImgCheckUtil
 * discription:图片验证码工具类
 * author:ZhangSenYao
 * createTime:2018-12-08 15:39
 */
public class ImgCheckUtil {

    public static Map<String, String> setPic() {
        //设置长宽高
        int width = 80;
        int height = 40;
        int lines = 7;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = img.getGraphics();
        //设置背景色
        graphics.setColor(Color.white);
        //画背景(填充指定的矩形。使用图形上下文的当前颜色填充该矩形）
        graphics.fillRect(0, 0, width, height);
        //设置字体
        graphics.setFont(new Font("宋体", Font.BOLD, 18));
        //随机数字
        Date date = new Date();
        Random random = new Random(date.getTime());
        String b = "";
        for (int i = 0; i < 4; i++) {
            //10以内的整数
            int a = random.nextInt(10);
            //10-30内的一个整数作为y坐标
            int y = 10 + random.nextInt(20);
            Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics.setColor(color);
            graphics.drawString(""+a, 5+i*width/4, y);
            b += a;
        }
        //System.out.println(b);
        //干扰线
        for (int i = 0; i < lines; i++) {
            Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            graphics.setColor(color);
            graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }
        //类似于流中的close()带动flush()，把数据刷到img对象中
        graphics.dispose();
        FileOutputStream fileOutputStream = null;
        String fileName = null;
        try {
            fileName = UUID.randomUUID() + ".jpg";
            fileOutputStream = new FileOutputStream("E:/img/" + fileName);
            ImageIO.write(img, "JPG", fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map map = new HashMap();
        map.put("picCode", b);
        map.put("fileName", fileName);
        return map;
    }

}
