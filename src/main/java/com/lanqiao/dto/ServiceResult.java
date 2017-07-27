
package com.lanqiao.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 服务方法返回的类型.
 *
 *
 * 默认构造该类对象时 {@link #succeed} 为 {@code true}。
 *
 * @param <T> 返回的数据类型
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public final class ServiceResult<T> implements Serializable {

    /**
     * 序列化版本 id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 成功与否.
     */
    private boolean succeed = true;

    /**
     * 业务返回码.
     */
    private int code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 数据对象.
     */
    private T data;

    /**
     * 额外的列属性.
     */
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * 默认的构造器.
     */
    public ServiceResult() {
    }

    /**
     * 使用指定的数据对象构造.
     *
     * @param data 指定的数据对象
     */
    public ServiceResult(final T data) {
        this.data = data;
    }

    /**
     * 使用指定的成功标识、业务返回码以及提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param code 指定的业务返回码
     * @param msg 指定的提示信息
     */
    public ServiceResult(final boolean succeed, final int code, final String msg) {
        this.succeed = succeed;
        this.code = code;
        this.msg = msg;
    }
    /**
     * 使用指定的成功标识、业务返回码以及提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param code 指定的业务返回码
     * @param msg 指定的提示信息
     */
    public ServiceResult(final boolean succeed, ResultBody resultBody) {
    	this.succeed = succeed;
    	this.code = resultBody.getResultCode();
    	this.msg = resultBody.getResultMsg();
    }

    /**
     * 使用指定的成功标识、数据对象、提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param data 指定的数据对象
     * @param msg 指定的提示信息
     */
    public ServiceResult(final boolean succeed, final T data, final String msg) {
        this.succeed = succeed;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 使用指定的成功标识、数据对象、业务返回码以及提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param data 指定的数据对象
     * @param code 指定的业务返回码
     * @param msg 指定的提示信息
     */
    public ServiceResult(final boolean succeed, final T data, final int code, final String msg) {
        this.succeed = succeed;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 使用指定的成功标识、提示信息构造.
     *
     * @param succeed 指定的成功标识
     * @param msg 指定的提示信息
     */
    public ServiceResult(final boolean succeed, final String msg) {
        this.succeed = succeed;
        this.msg = msg;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public void setResultBody(ResultBody resultBody){
    	setCode(resultBody.getResultCode());
    	setMsg(resultBody.getResultMsg());
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonAnyGetter
    public Object getAdditionalProperties(final String name) {
        return this.additionalProperties.get(name);
    }
    /**
     * 将Service转为ReturnResult.
     * @return
     */
    public ReturnResult<?> toReturnResult(){
    	ReturnResult<?> result =new ReturnResult<>(code,ResultBody.getResultMsg(code),data);
    	for (String  key : this.additionalProperties.keySet()) {
    		result.setOtherProperties(key, this.additionalProperties.get(key));
		}
    	return result;
    }
}