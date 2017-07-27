package com.lanqiao.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author zjyou
 * @date 2014-01-15  下午8:21:29
 * @func 随机生成验证码图片
 */
public class RandomValidateCode {

	private static final Logger logger = LogManager.getLogger(RandomValidateCode.class);
	
	private String type;//验证码类型login:登录；resetPwd：重置密码；updatePwd：修改密码
    public static final String RANDOMCODEKEY_LOGIN = "RANDOMCODEKEY_LOGIN";//登录放到session中的key
    public static final String RANDOMCODEKEY_RESETPWD = "RANDOMCODEKEY_RESETPWD";//重置密码放到session中的key
    public static final String RANDOMCODEKEY_UPDATEPWD = "RANDOMCODEKEY_UPDATEPWD";//修改密码放到session中的key
    public static final String RANDOMCODEKEY_BINDMOBILE = "RANDOMCODEKEY_BINDMOBILE";//绑定手机放到session中的key
    public static final String RANDOMCODEKEY_BINDEMAIL = "RANDOMCODEKEY_BINDEMAIL";//绑定邮箱放到session中的key
    
    //验证密码类型
    private static final String VALIDATE_TYPE_LOGIN = "login";//登录
    private static final String VALIDATE_TYPE_RESETPWD = "resetPwd";//重置密码
    private static final String VALIDATE_TYPE_UPDATEPWD = "updatePwd";//修改密码
    private static final String VALIDATE_TYPE_BINDMOBILE = "bindMobile";//绑定手机号
    private static final String VALIDATE_TYPE_BINDEMAIL = "bindEmail";//绑定邮箱
    
    private Random random = new Random();
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
    
    private int width = 80;//图片宽
    private int height = 38;//图片高
    private int lineSize = 40;//干扰线数量
    private int stringNum = 4;//随机产生字符数量
    
    /**
     * 验证码类型
     * @param type
     */
    public RandomValidateCode(String type) {
    	this.type = type;
    }
    
    /*
     * 获得字体
     */
    private Font getFont(){
        return new Font("Times New Roman",Font.CENTER_BASELINE,26);
    }
    /*
     * 获得颜色
     */
    private Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
    /**
     * 生成随机图片
     */
    public void getRandcode(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.setBackground(Color.gray);
        g.fillRect(0, 0, width, height);
//        g.fillRect(5, 5, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,24));
//        g.setColor(getRandColor(110, 133));
        //绘制干扰线
//        for(int i=0;i<=lineSize;i++){
//            drowLine(g);
//        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=stringNum;i++){
            randomString=drowString(g,randomString,i);
        }
        //存入作用域
        if(RandomValidateCode.VALIDATE_TYPE_LOGIN.equals(this.getType())) {//登录
            session.removeAttribute(RandomValidateCode.RANDOMCODEKEY_LOGIN);
            session.setAttribute(RandomValidateCode.RANDOMCODEKEY_LOGIN, randomString);
//            session.setMaxInactiveInterval(5*60);//有效期5分钟
        } else if(RandomValidateCode.VALIDATE_TYPE_RESETPWD.equals(this.getType())){//重置密码
            session.removeAttribute(RandomValidateCode.RANDOMCODEKEY_RESETPWD);
            session.setAttribute(RandomValidateCode.RANDOMCODEKEY_RESETPWD, randomString);
        } else if(RandomValidateCode.VALIDATE_TYPE_UPDATEPWD.equals(this.getType())){//修改密码
            session.removeAttribute(RandomValidateCode.RANDOMCODEKEY_UPDATEPWD);
            session.setAttribute(RandomValidateCode.RANDOMCODEKEY_UPDATEPWD, randomString);
        } else if(RandomValidateCode.VALIDATE_TYPE_BINDMOBILE.equals(this.getType())){//绑定手机号
            session.removeAttribute(RandomValidateCode.RANDOMCODEKEY_BINDMOBILE);
            session.setAttribute(RandomValidateCode.RANDOMCODEKEY_BINDMOBILE, randomString);
        } else if(RandomValidateCode.VALIDATE_TYPE_BINDEMAIL.equals(this.getType())){//绑定邮箱
            session.removeAttribute(RandomValidateCode.RANDOMCODEKEY_BINDEMAIL);
            session.setAttribute(RandomValidateCode.RANDOMCODEKEY_BINDEMAIL, randomString);
        }
        logger.debug("#######################本次验证码为：{}#######################",randomString);
        g.dispose();
        try {
        	response.flushBuffer();
            ImageIO.write(image, "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * 绘制字符串
     */
    private String drowString(Graphics g,String randomString,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(131),random.nextInt(141),random.nextInt(151)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13*i, 25);
        return randomString;
    }
    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);
    }
    /*
     * 获取随机的字符
     */
    public String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}