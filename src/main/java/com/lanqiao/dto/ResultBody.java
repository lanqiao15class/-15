package com.lanqiao.dto;

/**
 * 返回消息体
 * 
 * @resultCode：消息CODE为6位长度的int类型，不可为空
 * @resultMsg：消息内容，可为空，为空时在具体业务中编写消息内容
 * @logMsg：日志消息，可为空，为空时在具体业务中编写消息内容
 * 
 * @author Kreese
 *
 */
public enum ResultBody {

	// --- 未知异常 --- START ---
	/**
	 * -1
	 * 
	 * 系统异常，请稍后重试
	 * 
	 * 系统未知异常
	 */
	ERROR(-1, "系统异常，请稍后重试", "系统未知异常"),
	ERROR_001(-2, "请先登录.", "请先登录."),
	// --- 未知异常 --- END ---

	// --- 成功 --- START ---
	/**
	 * 0
	 * 
	 * 成功
	 * 
	 * 请求处理成功
	 */
	SUCCESS(0, "成功", "请求处理成功"),
	// --- 成功 --- END ---

	// --- 10xxxx,开头为系统级异常 --- START ---

	// --- 10xxxx,开头为系统级异常 --- END ---

	// --- 11xxxx,开头为控制层异常 --- START ---
	/**
	 * 110000
	 * 
	 * 系统异常，请刷新后重试
	 * 
	 * 控制层[Action]未知异常
	 */
	ERROR_110000(110000, "系统异常，请刷新后重试", "控制层[Action]未知异常"),
	/**
	 * 110000
	 * 
	 * 系统异常，请刷新后重试
	 * 
	 * 控制层[Action]未知异常
	 */
	ERROR_110001(110001, "请先创建课程", "请先创建课程"),
	// --- 11xxxx,开头为控制层异常 --- END ---

	// --- 12xxxx,开头为业务层异常 --- START ---
	/**
	 * 120000
	 * 
	 * 系统异常，请刷新后重试
	 * 
	 * 业务逻辑层[Service]未知异常
	 */
	ERROR_120000(120000, "系统异常，请刷新后重试", "业务逻辑层[Service]未知异常"),
	// --- 12xxxx,开头为业务层异常 --- END ---

	// --- 13xxxx,开头为数据层异常 --- START ---
	/**
	 * 130000
	 * 
	 * 系统异常，请刷新后重试
	 * 
	 * 数据层[Dao]未知异常
	 */
	ERROR_130000(130000, "系统异常，请刷新后重试", "数据层[Dao]未知异常"),
	/**
	 * 130001
	 * 
	 * 获取数据失败，请刷新后重试
	 * 
	 * 获取数据失败，请刷新后重试
	 */
	ERROR_130001(130001, "获取数据失败，请刷新后重试", "获取数据失败，请刷新后重试"),
	/**
	 * 130002
	 * 
	 * 插入数据失败，请刷新后重试
	 * 
	 * 插入数据失败，请刷新后重试
	 */
	ERROR_130002(130002, "插入数据失败，请刷新后重试", "插入数据失败，请刷新后重试"),
	/**
	 * 130003
	 * 
	 * 更新数据失败，请刷新后重试
	 * 
	 * 更新数据失败，请刷新后重试
	 */
	ERROR_130003(130003, "更新数据失败，请刷新后重试", "更新数据失败，请刷新后重试"),

	/**
	 * 210000
	 * 
	 * 系统异常，请刷新后重试
	 * 
	 * 控制层[Action]未知异常
	 */
	ERROR_210000(210000, "系统异常，请刷新后重试", "控制层[Action]未知异常"),;

	/**
	 * 返回码
	 */
	private int resultCode;

	/**
	 * 返回消息
	 */
	private String resultMsg;

	/**
	 * 日志消息
	 */
	private String logMsg;

	/**
	 * 构造方法
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @param logMsg
	 */
	private ResultBody(int resultCode, String resultMsg, String logMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.logMsg = logMsg;
	}

	/**
	 * 根据返回码查询返回消息体
	 * 
	 * @param resultCode
	 * @return
	 */
	public static String getResultMsg(int resultCode) {

		for (ResultBody rb : ResultBody.values()) {
			if (rb.getResultCode() == resultCode) {

				return rb.getResultMsg();
			}
		}

		return null;
	}

	/**
	 * 根据返回码查询日志消息体
	 * 
	 * @param resultCode
	 * @return
	 */
	public static String getLogMsg(int resultCode) {

		for (ResultBody rb : ResultBody.values()) {
			if (rb.getResultCode() == resultCode) {

				return rb.getLogMsg();
			}
		}

		return null;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

}
