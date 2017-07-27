<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="add-every-day mt20">
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="label-text">一讲时间：</i></label>
	            <input placeholder="选择时间" readonly disableautocomplete autocomplete="off" type="text">
	        </div>
	        <span class="error-tips">请选择一讲时间</span>
	    </div>
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="label-text">学生姓名：</i></label>
	             <span class="select-search-box">
                    <select class="scoolname">
                        <option value="">搜索</option>
                        <option value="张崇杰">张崇杰</option>
                        <option value="张崇杰">张崇杰</option>
                        <option value="张崇杰">张崇杰</option>
                        <option value="张崇杰">张崇杰</option>
                    </select>
                </span>
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="label-text">演讲主题：</i></label>
	            <textarea class="status-appendix" placeholder="请输入演讲主题"></textarea>
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="label-text">老师点评：</i></label>
	            <label class="radio-comments good"><input type="radio" name="a">好评</label>
	            <label class="radio-comments z"><input type="radio" name="a">中评</label>
	            <label class="radio-comments b"><input type="radio" name="a">差评</label>
	        </div>
	        <div class="clearfix">
	        	<label class="align-right-label"></label>
	            <textarea class="status-appendix" placeholder="请输入点评"></textarea>
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <div class="center mb20">
	    	<button class="btn btn-blue mr10">提交</button>
	        <button class="btn btn-wihte">取消</button>
	    </div>
	</div>