package com.lanqiao.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lanqiao.model.Email;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
* 项目名称:lqzp2
* 类名称： EmailService
* 类描述:邮件发送服务类
* 创建人: ZhouZhiHua
* 创建时间:2016年11月24日 下午2:26:58 
* 修改人： 
* 修改时间：2016年11月24日 下午2:26:58
* 修改备注:
 */
@Service
public class EmailService extends BaseService {

	// 具有所有的id
	public static int All_Data_ID = -1;

	private JavaMailSender mailSender;
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private static final String ENCODING = "utf-8";

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	/**
	 * 发送带附件的html格式邮件
	 */
	public void sendEmail(Email email, String type) throws Exception {
		MimeMessage msg = null;
		try {
			msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, ENCODING);

			org.springframework.mail.javamail.JavaMailSenderImpl sender = (org.springframework.mail.javamail.JavaMailSenderImpl) mailSender;

			log.info("mailusername:" + sender.getUsername() + " host:" + sender.getHost() + " pass:"
					+ sender.getPassword());
			//sender.setPort(port);

			helper.setFrom(new InternetAddress(sender.getUsername()));

			helper.setTo(email.getEmail());//发送地址
			if ("register".equals(type)) {
				helper.setSubject(MimeUtility.encodeText("蓝桥软件学院注册验证码", ENCODING, "B"));
			} else if ("bindEmail".equals(type)) {
				helper.setSubject(MimeUtility.encodeText("蓝桥软件学院绑定邮箱验证码", ENCODING, "B"));
			} else {
				helper.setSubject(MimeUtility.encodeText("蓝桥软件学院修改密码验证码", ENCODING, "B"));
			}
			helper.setText(getMailText(email, type), true); // true表示text的内容为html

			// 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
			//helper.addInline("welcomePic", new File("d:/welcome.gif")); // 附件内容

			// 这里的方法调用和插入图片是不同的，解决附件名称的中文问题
			//File file = new File("d:/欢迎注册.docx");
			//helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
			mailSender.send(msg);

			System.out.println("邮件发送成功...");
		} catch (Exception e) {
			throw new RuntimeException("error happens", e);
		}
	}

	/**
	 * 通过模板构造邮件内容，参数content将替换模板文件中的${content}标签。
	 */
	private String getMailText(Email email, String type) throws Exception {
		// 通过指定模板名获取FreeMarker模板实例
		Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
		freemarkerConfiguration.setDefaultEncoding("UTF-8");//转码
		String templatePath = this.getClass().getResource("/templates").getPath();//模板路径
		//System.out.println(templatePath);
		freemarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
		Template template = null;
		if ("register".equals(type)) {
			template = freemarkerConfiguration.getTemplate("registe.html");
		} else if ("resetPwd".equals(type)) {
			template = freemarkerConfiguration.getTemplate("resetPwd.html");
		} else if ("updatePwd".equals(type)) {
			template = freemarkerConfiguration.getTemplate("updatePwd.html");
		} else if ("bindEmail".equals(type)) {
			template = freemarkerConfiguration.getTemplate("bindEmail.html");
		}
		// FreeMarker通过Map传递动态数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("verificationCode", email.getVerificationCode()); // 注意动态数据的key和模板标签中指定的属性相匹配

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		return htmlText;
	}
}
