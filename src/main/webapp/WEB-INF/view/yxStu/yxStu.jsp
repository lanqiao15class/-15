<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
    <div class="main-right">
    	<div class="inner-box">
        	<div class="content">
            	<!--右侧内容部分开始-->
            	<div class="content-inner-part">
                	<div class="inner-reletive">
                    	<!--右侧标题部分开始-->
						<div class="tit-h1-box">
                            <h1 class="tit-first">
                                <span>学员管理</span><i class="gt-icon">&gt;</i><span class="curr">学员查询</span>
                            </h1>
                        </div>
						<!--右侧标题部分结束-->
                        <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                            <div class="tit-h2 clearfix">
                                <div class="tab-change-inner fl">
                                    <a class="curr" href="javascript:void(0)" id="myFocus">我关注的</a><a href="javascript:void(0)" id="all">全部</a>
                               		<a href="${ctxBase}/student/goYxStuInfo.do">测试查看学员详情</a>
                                </div>
                                <div class="fr btn-box-right">
                                	<button class="btn btn-green" id="addStuRegistration"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">新增意向学员</span></button>
                                </div>
                            </div>
                            <div class="search-part-tall">
                                <div class="query-opt-coopration clearfix">
                                    <div class="query-box-parent">
                                        <div class="query-box mt15" style="display:block;">
                                            <span class="select-search-box">
                                                <select class="scoolname">
                                                    <option value="">院校名称</option>
                                                    <option value="清华大学">清华大学</option>
                                                    <option value="北京大学">清华大学</option>
                                                    <option value="华中科技大学">华中科技大学</option>
                                                    <option value="武汉大学">武汉大学</option>
                                                </select>
                                            </span>
                                            <span class="select-box">
                                                <select class="select">
                                                    <option>状态</option>
                                                    <option>全部</option>
                                                    <option>报名待审核</option>
                                                    <option>审核不通过</option>
                                                    <option>未报名</option>
                                                </select>
                                            </span>
                                            <span class="search-auto-input">
                                                <div class="field">
                                                    <input disableautocomplete autocomplete="off" type="text" name="nope" id="stuname" placeholder="学员姓名" maxlength="40" />
                                                </div>
                                            </span>
                                            <button class="btn btn-blue search-btn"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                                            
                                            <button class="btn btn-gray much-more"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
                                            <button class="btn btn-gray put-away"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
                                        </div>
                                        <div class="query-box">
                                            <span class="search-auto-input">
                                                <div class="field">
                                                    <input disableautocomplete autocomplete="off" type="text" name="nope" id="stuprofession" placeholder="所在专业" maxlength="40" />
                                                </div>
                                            </span>
                                            <span class="search-auto-input">
                                                <div class="field">
                                                    <input disableautocomplete autocomplete="off" type="text" name="nope" id="stucollege" placeholder="所在学院" maxlength="40" />
                                                </div>
                                            </span>
                                            <button class="btn btn-blue search-btn"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                                        </div>
                                    </div>
                                    <div class="opt-btn-box">
                                        <i class="mr20">已选：0</i><a class="c-blue" href="javascript:void(0)">标记为我的</a><span>|</span><a class="c-blue" href="javascript:void(0)">标记为重点</a><span>|</span><a class="c-blue" href="javascript:void(0)">放弃</a>
                                    </div>
                                </div>
                                <div class="table-scroll-parent" id="yxGrid">
                                
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--右侧内容部分结束-->
            	<!--分页开始-->
                <div class="pager-part clearfix">
                	<div class="fl">学员总数：<em class="stu_total">62</em>人</div>
                	<div class="pager fr">
                        <span>每页显示<input type="text" disableautocomplete autocomplete="off">条</span>
                        <ul>
                            <li><a class="current prev" href="javascript:void(0);"><span>«上一页</span></a></li>
                            <li><a class="current" href="javascript:void(0);">1</a></li>
                            <li><a href="javascript:void(0);">2</a></li>
                            <li><a href="javascript:void(0);">3</a></li>
                            <li><a href="javascript:void(0);">4</a></li>
                            <li><span>...</span></li>
                            <li><a href="javascript:void(0);">15</a></li>
                            <li><a href="javascript:void(0);"><span>下一页»</span></a></li>
                        </ul>	
                    </div>
                </div>
                <!--分页结束-->
            </div>
        </div>
    </div>
</div>
<div style="height:0; overflow:hidden;" id="dialogcontant"></div>
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="myjs/yxTable.js" />
</jsp:include>
<script> 
$(function(){
	YXSTU.init();
	$('.scoolname').searchableSelect();
}); 
</script>