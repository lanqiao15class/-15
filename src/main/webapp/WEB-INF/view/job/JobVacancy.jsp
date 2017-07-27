<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
  <style type="text/css">
 .jobvacancyli {width: 100%;background-color:#e9f0f9;height: 10px;margin-top: 5px;list-style-type:none;}
 .jobvacancylispan {margin-left: 20px;font-size: 120%} 
 .first {list-style-type:none;} 
  </style>
  <!-- 初始化数据 -->
 <input  type="hidden" value='${JobVacancyList}'  id="JobVacancyList" > 
            	<!--右侧内容部分开始-->
            	<div class="content-inner-part">
                	<div class="inner-reletive">
                    	<!--右侧标题部分开始-->
						<div class="tit-h1-box">
                            <h1 class="tit-first">
                                <span>就业服务</span><i class="gt-icon">&gt;</i><span class="curr">招聘职位</span>
                            </h1>
                        </div>
						<!--右侧标题部分结束-->
                        <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                            <div class="tit-h2 clearfix">
                                <div class="tab-change-inner fl">
                                    <a class="curr" href="javascript:void(0)">日程表</a>
                                </div>
                              <!--   <div class="fr btn-box-right">
                                	<button class="btn btn-green" onClick="addStuRegistration()"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">新增跟踪记录</span></button>
                                </div> -->
                            </div>
                            <div class="search-part-tall">
                               
                                <div class="table-scroll-parent"  style="overflow: auto;">
                                	<ul class="fangtan-list clearfix"  id="jobvacancyUl">
                                	<li class="jobvacancyli"> <span class="jobvacancylispan"  >2017 -1-1</span></li>
                                    	<li class="first">
                                    	<div style="float: left; width: 10%;margin-left: 15px;padding-top: 10px" ><span id="jobvacancybegin_time"></span><br>&emsp;~<br ><span id="jobvacancyend_time"></span></div>
                                    	<div  style="float: left ; width: 85%">
                                    			<div class="rel-fangtan-con mb10">
	                                            	<label class="label-blue-fangtan">招聘职位：</label>
	                                                <span  id="jobvacancyposition_name"  class="c-blue"  style="font-size: 120%"></span>
	                                            </div>
	                                        	<div class="clearfix mb10">
	                                            	<p class="left-in-ft"><label   id="jobvacancyent_name" style="font-size: 120%">蓝桥软件学院测试</label>&nbsp;&nbsp;&nbsp;&emsp; &emsp;<label   class="label-blue-fangtan" >招聘人数：</label><i class="c-blue"  id="jobvacancyrequired_quantity">0</i>&emsp; &emsp;&nbsp;&nbsp;&nbsp;<label  class="label-blue-fangtan"  >执行负责人：</label><i class="c-blue"  id="jobvacancyprincipal"></i>&emsp; &emsp;&nbsp;&nbsp;&nbsp;<label    class="label-blue-fangtan">当值HR：</label><i class="c-blue"  id="jobvacancyhr_name">2016-10-11</i></p>
	                                                <p class="right-in-ft">
	                                                	<button type="button" class="btn btn-blue jobvacancybutton" id="jobvacancybutton"  ><i class="Hui-iconfont"></i><span class="ml10" id="jobvacancybut">报名</span></button>
	                                                	<button type="button" class="btn btn-blue jobvacancybutxq" id="jobvacancybutxq"  ><i class="Hui-iconfont"></i><span class="ml10" id="">查看详情</span></button>
	                                                </p>
	                                            </div>
	                                            
	                                            <div class="rel-fangtan-con mb20">
	                                            	
	                                            	<label class="label-blue-fangtan">笔试时间：</label><i class="c-blue"  id="jobvacancywritten_time"></i>&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;   
	                                                <label class="label-blue-fangtan">面试时间：</label><i class="c-blue"  id="jobvacancyinterview_time"></i>
	                                            </div>
	                                           
                                            </div>
                                            <div style="clear: both;"></div>
                                        </li>
                                       
                                        
                                        
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--右侧内容部分结束-->
     
<!-- 弹出层开始 -->

<div  style="display: none;"  id="jobvacancyialoglayer">
  <div style="margin:40px">
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1"   style="font-size: 120%;padding-left: 20px" id="jkgszyent_name"></label>
	   [<span      id="jkgszyposition_name"></span>]
	</div>
	<br/>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">需求人数：</label>
	    <span  id="jkgszyrequired_quantity"  ></span>
	</div>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">薪资待遇：</label>
	    <span  id="jkgszylaborage"   ></span>
	</div>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">招聘要求：</label>
	    <span  id="jkgszyposition_require"  ></span>
	</div>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">面试形式：</label>
	    <span  id="jkgszyby_way"  ></span>
	</div>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">执行负责人：</label>
	    <span  id="jkgszyprincipal"   ></span>
	</div>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">参与班级：</label>
	    <span  id="jkgszyjoin_class"    ></span>
	</div>
	<div class="rel-fangtan-con mb10">
	  	<label class="label-blue-fangtan1">技术考核点：</label>
	    <span  id="jkgszyskill_point"    ></span>
	</div>
  </div>
	

</div>
<!--表格js结束-->
<script> 
$(function(){
	JOBVACANCY.init();
}); 
</script>

