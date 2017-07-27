<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

		<div class="content">
			<div class="content-inner-part">
				<div class="inner-reletive">
					<!--右侧标题部分开始-->
					<div class="tit-h1-box">
						<h1 class="tit-first">
							<span>教学管理</span><i class="gt-icon">&gt;</i><span class="curr">课程设置</span>
						</h1>
					</div>
					<!--右侧标题部分结束-->
					<!--右侧内容部分背景白色开始-->
					<div class="inner-white">

						<div class="tit-h2 clearfix">
							<div class="fr btn-box-right">
								<button class="btn btn-green" onclick="COURSELIST.createCourse()">
									<i class="Hui-iconfont"></i> <span class="ml10">创建课程</span>
								</button>
							</div>
						</div>

						<!--filter-->
						<div class="search-part-tall mt20">
							<div class="table-scroll-parent" id="myGrid">表格</div>
						</div>
						<!--filter:end-->

					</div>
					<!--右侧内容部分背景白色结束-->
				</div>
			</div>
		</div>
		<script src="${ctxStatic}/myjs/course/courselist.js"></script>
<script>
$(function(){
	COURSELIST.init();
	$("body").delegate(".open_close","click",function(){
		$(this).toggleClass("open");
		console.log(123)
	})
})
</script>