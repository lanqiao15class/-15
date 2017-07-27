<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

		<div class="content">
			<div class="content-inner-part">
				<div class="inner-reletive">
					<div>
						<a onclick="ARRANGEMENTLIST.paike()">dianjiasa </a>
					</div>
					<!--右侧标题部分开始-->
					<div class="tit-h1-box">
						<h1 class="tit-first">
							<span>教学管理</span><i class="gt-icon">&gt;</i><span class="curr">排课</span>
						</h1>
					</div>
					<!--右侧标题部分结束-->
					<!--右侧内容部分背景白色开始-->
					<div class="inner-white">
						<div class="tit-h2 clearfix">
							<div class="fr btn-box-right">
								<button class="btn btn-green" onclick="ARRANGEMENTLIST.arrangeCourse()">
									<i class="Hui-iconfont"></i> <span class="ml10">我要排课</span>
								</button>
							</div>
							<div class="tab-change-inner fl">
								<c:choose>
									<c:when test="${opType eq 100 }">
										<c:if  test="${lq:ifIn(myFunction,'63')}">
											<c:if test="${not empty default_opType }">
												<a   href="javascript:void(0)" id="myManage_lq">我管理的</a>
											</c:if>
											<c:if test="${empty default_opType }">
												<a  class="curr"   href="javascript:void(0)" id="myManage_lq">我管理的</a>
												<c:set var="default_opType" value="2"></c:set>
											</c:if>
										</c:if>

										<c:if test="${lq:ifIn(myFunction,'64')}">
											<c:if test="${not empty default_opType }">
												<a  href="javascript:void(0)" id="allStu_lq">全部</a>
											</c:if>
											<c:if test="${empty default_opType}">
												<a class="curr"  href="javascript:void(0)" id="allStu_lq">全部</a>
												<c:set var="default_opType" value="1"></c:set>
											</c:if>
										</c:if>
									</c:when>

									<c:otherwise>
										<c:if  test="${lq:ifIn(myFunction,'63')}">
											<a <c:if test="${opType eq '2' }">class="curr"</c:if>  href="javascript:void(0)" id="myManage_lq">我管理的</a>
										</c:if>
										<c:if test="${lq:ifIn(myFunction,'64')}">
											<a <c:if test="${opType eq '1' }">class="curr"</c:if> href="javascript:void(0)" id="allStu_lq">全部</a>
										</c:if>
									</c:otherwise>
								</c:choose>
								<!-- 初始化数据开始 -->
								<c:if test="${opType eq 100 }">
									<c:set var="opType" value="${default_opType }"></c:set>
								</c:if>

								<input type="hidden" id="opType" value="${opType}">
								<!-- 初始化数据结束 -->
							</div>
						</div>
						<div class="search-part-tall">
							<div class="query-opt-coopration clearfix">
								<div class="query-box-parent">
									<div class="query-box mt15" style="display:block;">
										<span class="select-box">
											<select class="select" id="classStatus" name="classStatus">
										<option value="">班级状态</option>
										<option value="0">未上课</option>
										<option value="1">上课中</option>
										<option value="2">已上课</option>
										<option value="3">取消</option>
									</select>
								</span>
								<span class="select-box">
									<select class="select" id="courseType" name="courseType">
										<option value="">课程类别</option>
										<option value="1">JAVAEE</option>
										<option value="2">Android</option>
										<option value="3">IOS</option>
										<option value="4">测试</option>
										<option value="5">UI</option>
										<option value="6">产品经理</option>
									</select>
								</span>

										<button class="btn btn-blue search-btn query_lq" type="button" onclick="ARRANGEMENTLIST.lookArrange()"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>

										<button class="btn btn-gray much-more" onclick="ARRANGEMENTLIST.muchmore()"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
										<button class="btn btn-gray put-away" onclick="ARRANGEMENTLIST.putaway()"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
									</div>
									<div class="query-box">
										<!-- 引入时间筛选查询 -->
										<input type="text" disableautocomplete autocomplete="off" name="beginTime_lq" value="" id="beginTime_lq" placeholder="授课时间" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
										-
										<input type="text" disableautocomplete autocomplete="off" name="endTime_lq" value="" id="endTime_lq" placeholder="授课时间" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
										<!-- 引入时间筛选结束 -->
										<button class="btn btn-blue search-btn query_lq" type="button" onclick="ARRANGEMENTLIST.lookArrangeByMore()"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
									</div>
								</div>
								<div class="opt-btn-box">

								</div>
							</div>
							<div class="table-scroll-parent" id="myGrid"></div>
						</div>
					</div>
				</div>
			</div>

			<!--右侧内容部分结束-->
			<!--分页开始-->
			<!-- 引入分页开始 -->
			<jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
				<jsp:param name="funcName" value="CLASSMANAGELIST.dopagechange" />
			</jsp:include>
			<!-- 引入分页结束 -->
			<!--分页结束-->
		</div>
			<script src="${ctxStatic}/myjs/course/arrangementlist.js"></script>

			<script>
                var functionIds="${myFunction}";
                $(function(){
                    ARRANGEMENTLIST.init();
                });
			</script>