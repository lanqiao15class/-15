package com.lanqiao.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.lanqiao.model.TSmssendLog;
import com.lanqiao.service.SmsService;
/**
 * 
* 项目名称:lqzp
* 类名称： SmsServiceImpl
* 类描述:发送短信
* 创建人: ZhouZhiHua
* 创建时间:2016年8月11日 下午1:52:28 
* 修改人： 
* 修改时间：2016年8月11日 下午1:52:28
* 修改备注:
* 具体查看帮助
* https://help.aliyun.com/document_detail/44366.html?spm=5176.2020520168.101.5.eZ6BFI
 */
@Service
public class SmsService extends BaseService
{

	// 具有所有的id
	public static int All_Data_ID = -1;
	
	@Resource
	SmssendLogService smssendLogService;
	
	 /**
     * 发送短信
     * @param smscode  短信模板, 需要在阿里  系统中定义.
     * @param param   短信参数
     * @return  : null 发送失败:  返回交易id
     */
	public String SendRegisterSMS( String param, String phone) throws Exception {
		
		String reqid =null;
		
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "xkQUWu7pu8SkvWyb", "poQ44Emn7Ii00SrPbk0xlTVu1uITqg");
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
        //以上这些信息固定, 不能动. 
        
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        try 
        {
            request.setSignName("国信erp系统"); // 不能改, 必须是这个字符, 要改只能去阿里平台改, 
            request.setTemplateCode("SMS_25410215"); // 短信的模板id , 阿里 申请的.
            request.setParamString("{\"code\":\""+param+"\"}"); // 替换短信模板中的变量code 
            request.setRecNum(phone);//发送的目标手机
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            reqid = httpResponse.getRequestId(); // 返回交易码
            System.out.println("短信发送成功...");
            //2.保存发送短信记录,用于对账
			TSmssendLog smssendLog=new TSmssendLog();
			smssendLog.setDestphone(phone);
			smssendLog.setSendtime(new Date());
			smssendLog.setSmscode("SMS_25410215");
			smssendLog.setPlatform(0);
			smssendLogService.insert(smssendLog);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        catch (ClientException e) {
            e.printStackTrace();
        }
		return reqid;
	}
	
}
