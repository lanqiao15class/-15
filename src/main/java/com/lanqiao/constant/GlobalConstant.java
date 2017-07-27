package com.lanqiao.constant;

import org.apache.commons.lang3.StringUtils;

public class GlobalConstant {

	
	/** 学生权限 */
	public static final String STUDENTAUTHORITY = "15";

	public static String uploadPath = ""; // 上传的文件存储的物理文件路径. 
	public static String httpUploadURL = ""; // 上传的文件访问路径主目录 

	//public static String roleId=""; //

	public static String defaultheadface = ""; // 默认头像地址, 配置文件载入
	public static String LoginURL = "";// 登录地址, 配置文件载入. 

	public static String GlobalWebURL = "";
	public static String LanqiaoZYXTURL = "";//蓝桥资源系统url
	public static String appid = "";//单点登录id
	public static String SSOURL = "";//单点url

	//微信 
	public static String weixin_appid = ""; //微信公众号的appid
	public static String weixin_secret = ""; // 微信公众号密钥

	

	public  void setWeixin_appid(String weixin_appid) {
		GlobalConstant.weixin_appid = weixin_appid;
	}

	
	public  void setWeixin_secret(String weixin_secret) {
		GlobalConstant.weixin_secret = weixin_secret;
	}

	
	public static void setSSOURL(String sSOURL) {
		SSOURL = sSOURL;
	}

	public static void setAppid(String appid) {
		GlobalConstant.appid = appid;
	}

	public void setLanqiaoZYXTURL(String lanqiaoZYXTURL) {
		LanqiaoZYXTURL = lanqiaoZYXTURL;
	}

	public void setGlobalWebURL(String globalWebURL) {
		GlobalWebURL = globalWebURL;
	}

	public void setLoginURL(String loginURL) {
		LoginURL = loginURL;
	}

	public void setDefaultheadface(String defaultheadface) {
		GlobalConstant.defaultheadface = defaultheadface;
	}

	/**
	 *  * 配置文件注入
	 * @param s
	 */
	public void setHttpUploadURL(String s) {
		GlobalConstant.httpUploadURL = s;

	}

	/**
	 * 配置文件注入
	 * @param s
	 */
	public void setUploadPath(String s) {
		uploadPath = s;
	}

	/**
	 * 图片后缀格式
	 * @param s
	 */
	public static final String PICTURESUFFIX = ".bmp,.jpg,.jpeg,.png";

	//	public static void setRoleId(String s) {
	//		roleId = s;
	//	}
	/**
	 * 操作符
	 */
	public static final String[] OPERATOR = { "ADD", "UPDATE" };

	/**
	 * 0:用户名错误
	 * 1：密码错误
	 * 2：验证码输入错误
	 * 3:登陆成功
	 * 4:发生未知异常
	 */
	public static final String[] STRRESULTS = { "0", "1", "2", "3", "4" };

	/**
	 * 0:用户名错误
	 * 1：密码错误
	 * 2：验证码输入错误
	 * 3:登陆成功
	 * 4:发生未知异常
	 */
	public static final int[] INTRESULTS = { 0, 1, 2, 3, 4, 5 };

	/**
	 * 用户信息cookie存放时间
	 */
	public static final int COOKETIME = 8 * 60 * 60;

	/**
	 * mysql驱动地址
	 */
	public static final String MYSQLDRIVERCLASS = "com.mysql.jdbc.Driver";

	/**
	 * 基础时间类型
	 */
	public static final String STDATEFORMAT = "yyyy-MM-dd";

	/**
	 * 校区
	 */
	public enum CampusEnum {

		BEIJINGCAMPUS("0", "北京校区"), DONGGUANCAMPUS("1", "东莞校区");

		private String value;
		private String text;

		private CampusEnum(String value, String text) {
			this.value = value;
			this.text = text;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 学生状态
	 */
	public enum StuStatusEnum {
		/**未报名 */
		NOREGISTRATION(100, "未报名"),
		/** 报名待审核*/
		AUDIT(101, "报名待审核"),
		/** 未分班*/
		NOCLASS(102, "未分班"),
		/**未开课 */
		NOSTARTCLASS(103, "未开课"),
		/**在读 */
		BESTUDY(104, "在读"),
		/**结业 */
		EndStudy(105, "结业"),
		/**求职中 */
		FindJobing(106, "求职中"),
		/**开除 */
		EXPEL(107, "开除"),
		/**劝退 */
		QUANTUI(108, "劝退"),
		/**退学 */
		LEAVESCHOLL(109, "退学"),
		/**休学 */
		XIUXUE(110, "休学"),
		/**休学重返 */
		XIUXUEBack(111, "休学重返"),
		/**延期结业 */
		DELAYGRADUATE(112, "延期结业"),
		/**延期就业 */
		DELAYWORK(113, "延期就业"),
		/**已就业 */
		WORKED(114, "已就业");

		private int value;
		private String text;

		private StuStatusEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	/**
	 * 学员审核状态
	 */
	public enum auditStatusEnum {
		/**未通过*/
		NOPASS(0, "未通过"), //身份证不符合、未缴费
		/**已通过*/
		PASS(1, "已通过"), //身份证符合，已缴费或者特批延迟缴纳
		/**未缴费 */
		NOPAY(2, "未缴费"), //身份证信息相符，但未缴费
		/**补充资料*/
		SUPPLYINFO(3, "补充资料"), //身份证信息不符，但已缴费
		/**未报名*/
		NOREGIST(4, "未报名"), //还未报名
		/**报名待审核*/
		NOAUDIT(5, "报名待审核");//报名待审核

		private int value;
		private String text;

		private auditStatusEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 班级状态
	 */
	public enum ClassStatusEnum {
		/**未开课*/
		NOCLASSES(0, "未开课"),
		/**授课中*/
		INTHELECTURE(1, "授课中"),
		/**集训前结课*/
		BEFORETHETRAININGSESSION(2, "集训前结课"),
		/**集训中*/
		INTHETRAINING(3, "集训中"),
		/**结业*/
		THEGRADUATION(4, "结业"),
		/**就业中*/
		INTHEEMPLOYMENT(5, "就业中"),
		/**100%就业*/
		OFTHEEMPLOYMENT(6, "100%就业"),
		/**关闭*/
		SHUTDOWN(7, "关闭");

		private int value;
		private String text;

		private ClassStatusEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	//	/**
	//	 * 通知通告类型
	//	 */
	//	public enum OaNotifyTypeEnum {
	//
	//		MEETINGNOTICE("1", "会议通告"), 
	//		REWARDSNOTICE("2", "奖惩通告"),
	//		ACTIVITYNOTICE("3", "活动通告");
	//
	//		private String value;
	//		private String text;
	//
	//		private OaNotifyTypeEnum(String value, String text) {
	//			this.value = value;
	//			this.text = text;
	//		}
	//
	//		public String getValue() {
	//			return value;
	//		}
	//
	//		public void setValue(String value) {
	//			this.value = value;
	//		}
	//
	//		public String getText() {
	//			return text;
	//		}
	//
	//		public void setText(String text) {
	//			this.text = text;
	//		}
	//	}
	//	

	/**
	 * 学费类型
	 */
	//	public enum TuitionTypeEnum {
	//		/**待定*/
	//		HOLD("0", "待定"),
	//		/**一次性支付*/
	//		DISPOSABLE("1", "一次性支付"),
	//		/**分期还款*/
	//		INSTALMENT("2", "分期还款");
	//
	//		private String value;
	//		private String text;
	//
	//		private TuitionTypeEnum(String value, String text) {
	//			this.value = value;
	//			this.text = text;
	//		}
	//
	//		public String getValue() {
	//			return value;
	//		}
	//
	//		public void setValue(String value) {
	//			this.value = value;
	//		}
	//
	//		public String getText() {
	//			return text;
	//		}
	//
	//		public void setText(String text) {
	//			this.text = text;
	//		}
	//	}

	/**
	 * 用户角色类型
	 */
	public enum UserTypeEnum {

		STUDENT(0, "学生"), HEADTEACHER(1, "职业经纪人"), TEACHTEACHER(2, "技术老师"), CEPTEACHER(3, "CEP老师"), RECRUITMENTTEACHER(
				4, "招生老师"), OCCUPATIONBROKER(5, "企业BD"), HOUSEPARENT(6, "宿舍管理员"), FINANCIAL(7, "财务"), EMPASSISTANT(8,
				"就业助理"), ZSASSISTANT(9, "招生部门助理"), JWASSISTANT(10, "教务助理"), LANQIAOTEA(-1, "蓝桥学院老师");

		private int value;
		private String text;

		private UserTypeEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 考试状态
	 */
	public enum ExamStatusEnum {

		UNINPUT("0", "未录入"), UNPUBLISH("1", "未发布"), PUBLISHED("2", "已发布");

		private String value;
		private String text;

		private ExamStatusEnum(String value, String text) {
			this.value = value;
			this.text = text;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 标记的数据类型
	 */
	public enum DataTagTypeEnum {
		UNIVERSITIES("1", "院校"), LQCLASS("2", "班级"), STUDENTS("3", "学员");

		private String value;
		private String text;

		private DataTagTypeEnum(String value, String text) {
			this.value = value;
			this.text = text;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 报名费,学费 费用状态,
	 */
	public enum StuPaidEnum {
		/** 未缴纳 */
		NOTPAY(0, "未缴纳"),
		/** 部分缴纳 */
		PARTOFTHEPAY(1, "部分缴纳"),
		/** 全部缴纳 */
		ALLPAY(2, "全部缴纳"),
		/** 特批后缴 */
		SPECIALPAY(3, "特批后缴"),
		/** 部分退费 */
		PartReturn(4, "部分退费"),
		/** 全额退费 */
		AllReturn(5, "全额退费");

		private int value;
		private String text;

		private StuPaidEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 付款目的
	 */
	public enum PayGoalEnum {
		SIGNMONEY(1, "报名费"), LEARNMONEY(2, "实训费");

		private int value;
		private String text;

		private PayGoalEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/** 交费的业务类型 */
	//	public enum feeServiceType {
	//		HUIKUAN("A"),
	//		TUIKUAN("B"),
	//		JIANMIAN("C");
	//		private String type;
	//		feeServiceType(String type){
	//			this.type = type;
	//		}
	//		public String getType() {
	//			return type;
	//		}
	//		public void setType(String type) {
	//			this.type = type;
	//		}
	//		
	//	}

	/**
	 *学员 职业状态
	 * @author lenovo
	 *
	 */
	public enum jobStatusEnum {
		NOJOB(0, "未就业"), JOBING(1, "在职"), LEAVEJOB(2, "离职");

		private int value;
		private String text;

		private jobStatusEnum(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 学员就业方式
	 * @author lenovo
	 *
	 */
	public enum JOBWAY {
		SELJOB(0, "自主就业"), RECOJOB(1, "推荐就业");

		private int value;
		private String text;

		private JOBWAY(int value, String text) {
			this.value = value;
			this.text = text;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 数据映射
	 * @author lenovo
	 *
	 */
	public enum DBTableName {
		DICT("sys_dict", "字典表"), LQCity("lq_city", "地点"), LQUniversities("lq_universities", "蓝桥网_学校信息_字典"), repcoursetype(
				"rep_course_type", ""), repfactstudentexception("rep_fact_student_exception", "异常状态学员报"), repfactstudentformal(
				"rep_fact_student_formal", ""), repfactstudentpay("rep_fact_student_pay", ""), repstustatus(
				"rep_stustatus", ""), SysDeparment("sys_deparment", "部门"), sysDict("sys_dict", "字典"), SysFunction(
				"sys_function", "系统功能"), sysLog("sys_log", "日志"), SysMenu("sys_menu", "菜单"), SysRole("sys_role", "角色"), SysRoleFunction(
				"sys_role_function", "角色-功能"), SysStation("sys_station", "员工岗位定义"), SysStationLevel(
				"sys_station_level", "岗位级别"), SysUserRole("sys_user_role", "用户-角色"), TClassStatusLog(
				"t_class_status_log", "班级状态修改记录"), TClassVisitLog("t_class_visit_log", "班级访谈记录."), TConsole_log(
				"t_console_log", "后台操作日志"), TDataTag("t_data_tag", "关注"), TIncomeLog("t_income_log", "学费，报名费记录"), TJobDetail(
				"t_job_detail", "工作详情"), TLqclass("t_lqclass", "班级"), TSmssendLog("t_smssend_log", "短信发送记录"), Student(
				"t_student", "学生基本信息"), TStudentReceivableLog("t_student_receivable_log", "学员回款记录"), TStudentRefundLog(
				"t_student_refund_log", "学员退费记录"), TStudentRemissionLog("t_student_remission_log", "学员减免记录"), TStudentStatusLog(
				"t_student_status_log", "学生状态修改记录"), TStudentclassLog("t_studentclass_log", "学生换班记录"), TStuno(
				"t_stuno", "学员编号"), Teacher("t_teacher", "老师"), User("t_user", "用户基本信息"), TUserBind("t_user_bind",
				"第3方绑定"), TUserDataAuth("t_user_data_auth", "某一用户拥有的数据权限定义"), TVisit_log("t_visit_log",
				"访谈记录,被访谈的对象可以是多种;如:院校,学生,企业等."), TVisitSubLog("t_visit_sub_log", "访谈对象");

		private String value;
		private String text;

		// 普通方法  
		public static String getName(String index) {
			for (DBTableName c : DBTableName.values()) {
				if (StringUtils.equalsIgnoreCase(c.getValue(), index)) {
					return c.getText();
				}
			}
			return null;
		}

		private DBTableName(String value, String text) {
			this.value = value;
			this.text = text;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
}
