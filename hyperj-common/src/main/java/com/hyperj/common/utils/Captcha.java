package com.hyperj.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class Captcha {

    /**
     * 验证码字符
     */
    private String code;


    /**
     * 图片
     */
    private BufferedImage buffImg;

    /**
     * 随机数发生器
     */
    private Random random = new Random();

    /**
     *
     * @param width 宽
     * @param height 高
     * @param codeCount 验证码字符数
     * @param lineCount 干扰数
     */
    public Captcha(int width,int height,int codeCount, int lineCount){
        // 1、生成rgb格式图像
        buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        // 2、背景色
        Graphics g = buffImg.getGraphics(); // 拿到画笔
        g.setColor(getRandColor(200,250)); // 设置画笔颜色
        g.fillRect(0,0,width,height); // 填充颜色到背景
        Font font = new Font("Fixedsys",Font.BOLD,height-5); // 给画笔设置字体
        g.setFont(font);
        // 3、设置干扰线 噪点
        for(int i=0;i<lineCount;i++ ){
            // 随机生成坐标
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys +  random.nextInt(height);
            // 划线
            g.setColor(getRandColor(1,255) );
            g.drawLine(xs,ys,xe,ye);
        }
        // 画噪点
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            buffImg.setRGB(x, y, random.nextInt(255));
        }
        // 4、添加字符到图片
        this.code = randomStr(codeCount);
        int fontWidth = width / codeCount;
        int fontHeight = height - 5;
        for (int i = 0; i < codeCount; i++) {
            String str = this.code.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            g.drawString(str, i * fontWidth + 3, fontHeight - 3);
        }
    }

    /**
     * 随机生成字符
     *
     * @param codeCount
     * @return
     */
    private String randomStr(int codeCount) {
        // 定义字典
        String str = "ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        int len = str.length() - 1;
        double r;
        for (int i = 0; i < codeCount; i++) {
            // 生成字典随机索引
            r = (Math.random()) * len;
            sb.append(str.charAt((int) r));
        }
        return sb.toString();
    }

    /**
     * 获取随机色
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        if(fc > 255){
            fc = 255;
        }
        if(bc > 255){
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }

    /**
     * 返回验证码值
     * @return
     */
    public String getCode(){
        return this.code;
    }

    /**
     * 获得验证码的base64编码
     * @return
     */
    public String getBase64ByteStr() throws IOException {
        // 获得字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 使用ImageIO类，将buffImage图片写入到流中
        ImageIO.write(buffImg,"png",baos);

        // 使用Base64工具类对流进行编码
        String s = Base64.getEncoder().encodeToString(baos.toByteArray());
        // 编码后的字符中包含回车换行导致图片不能显示，所以要去除
        s = s.replaceAll("\n","").replaceAll("\r","");
        return "data:image/jpg;base64,"+s;
    }

}
