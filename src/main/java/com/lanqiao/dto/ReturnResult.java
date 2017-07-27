package com.lanqiao.dto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;

/**
 * 返回结果
 * 
 * @author xinglt
 * @param <T>
 *
 */
public class ReturnResult<T> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private int resultCode;

	private String resultMsg;

	private T resultData;

    /**
     * 额外的属性.
     */
    private Map<String, Object> otherProperties = new HashMap<>();
	
	/**
	 * 构造方法，返回错误码和消息体
	 * 
	 * @param resultCode
	 * @param resultMsg
	 */
	public ReturnResult(int resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	/**
	 * 构造方法，返回错误码、消息体和消息类
	 * 
	 * @param resultCode
	 * @param resultMsg
	 * @param resultData
	 */
	public ReturnResult(int resultCode, String resultMsg, T resultData) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.resultData = resultData;
	}
	/**
	 * 构造方法，返回错误码、消息体和消息类
	 * 
	 * @param resultBody
	 * @param resultData
	 */
	public ReturnResult(ResultBody resultBody, T resultData) {
		this.resultCode = resultBody.getResultCode();
		this.resultMsg = resultBody.getLogMsg();
		this.resultData = resultData;
	}
	/**
	 * 构造方法，返回错误码、消息体和消息类
	 * 
	 * @param resultBody
	 */
	public ReturnResult(ResultBody resultBody) {
		this.resultCode = resultBody.getResultCode();
		this.resultMsg = resultBody.getResultMsg();
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

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}

	
	@JsonAnyGetter
    public Map<String, Object> getOtherProperties() {
        return this.otherProperties;
    }

    @JsonAnySetter
    public void setOtherProperties(final String name, final Object value) {
        this.otherProperties.put(name, value);
    }

    @JsonAnyGetter
    public Object getOtherProperties(final String name) {
        return this.otherProperties.get(name);
    }
}
