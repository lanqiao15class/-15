<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="y-block">
	<div class="y-block-t">基本信息</div>
	<div class="y-form">

		<input type="hidden" id="cid" value="${course.courseId}" />
		<!-- 课程id -->
		<input type="hidden" id="updateid" value="-1" />
		<!-- 更新阶段id -->
		<input type="hidden" id="pid" value="-1" />
		<!-- 新建课时父id -->
		<input type="hidden" id="subid" value="-1" />
		<!-- 更新课时id -->
		
		<input type="hidden" id="typeid" value="${course.type }" />

		<form id='courseform'>
			<input type="hidden" id="courseId" name="courseId" value="${course.courseId}" />
			<div class="item">
				<label class="title">课程名称</label> <input type="text"
					name="courseName" id="n" value="${course.courseName}" /><span
					id="m"> </span>
			</div>
			<div class="item">
				<label class="title">合同报名费¥</label> <input type="text"
					name="standardMoney" id="n2" value="${course.standardMoney}" /><span
					id="m2"> </span>
			</div>
			<div class="item">
				<label class="title">合同实训费¥</label> <input type="text"
					name="entryfee" id="n3" value="${course.entryfee}" /><span id="m3">
				</span>
			</div>
			<%--选择框开始--%>
			<div class="item">
				<label class="title">课程类别</label>
				<span>
					<select class="select" id="lqCoursetype" name="lqCoursetype" >
						<option value="">课程类别</option>
						<option value="1">JAVAEE</option>
						<option value="2">Android</option>
						<option value="3">iOS</option>
						<option value="4">测试</option>
						<option value="5">UI</option>
						<option value="6">产品经理</option>
					</select>
				</span>
				<span id="m5"></span>
			</div>
			<%--选择框结束--%>
			<div class="item">
				<label class="title">状态</label> 
				<label class="y-radio"> 
					<input	type="radio" name="type" id="" value="1" /> <span>启用</span>
				</label> 
				<label class="y-radio"> 
					<input type="radio" name="type"	id="" value="0" /> <span>禁用</span><span id="m4"></span>
				</label>
			</div>
		</form>
		<div class="center">
		<button class="btn btn-blue" ONCLICK="updatecourse()">修改</button>
		<button class="btn btn-wihte mr10" onclick="re()">重置</button>
	</div>
	</div>
</div>
<div class="y-block">
	<div class="y-block-t">教学大纲</div>
	<button class="btn btn-green" ONCLICK="createStageModal()">+&ensp;创建阶段</button>
	<ul class="y-tree" id="stageid">
		<c:forEach var="list" items="${lists}" >
		<li class="open open_close" id="sss${list[0].syllabusId}">
			<div>
				<p><span id="sn${list[0].syllabusId }">${list[0].syllabusName}</span>（课时：<span id="${list[0].syllabusId }">${list[0].classTime}</span>）</p>
				<div class="y-icon add" onclick="createSubjuctModal('${list[0].syllabusId }')">+&ensp;创建科目</div>
				<div class="y-icon edit" onclick="showUpdata('${list[0].syllabusId}','${list[0].syllabusName}')">修改</div>
				<div class="y-icon del" onclick="delstage('${list[0].syllabusId}')">删除</div>
				<div class="y-icon arrow-up" onclick="uprun('${list[0].syllabusId}')"></div>
				<div class="y-icon arrow-down" onclick="downrun('${list[0].syllabusId}')"></div>
			</div>
			<ul id="ul${list[0].syllabusId }">
			<c:forEach var="info" items="${list}" varStatus="status">
			<c:if test="${status.index!=0}">
				<li id="ddd${info.syllabusId }">
					<div>
						<p><span id="sbn${info.syllabusId }">${info.syllabusName}</span>（课时：<span id="sbc${info.syllabusId }">${info.classTime}</span>）</p>
						<div class="y-icon edit" onclick="updateSubject('${info.syllabusId}')">修改</div>
						<div class="y-icon del" onclick="delsub('${info.syllabusId}','${info.pid}')">删除</div>
						<div class="y-icon arrow-up" onclick="uprunsub('${info.syllabusId}')"></div>
						<div class="y-icon arrow-down" onclick="downrunsub('${info.syllabusId}')"></div>
					</div>
				</li>
			</c:if>
			</c:forEach>
			</ul>
		</li>
		</c:forEach>
	</ul>

</div>



<!--创建科目-->
<div class="y-modal" id="createSubject">
	<div class="y-form">
		<form id="idsub" class="ke">
			<div class="item">
				<label class="title">科目</label> <input type="text"
					name="syllabusName" id="subis2" value="" />
			</div>
			<div class="item">
				<label class="title">课时</label> <input type="text" name="classTime"
					id="subis3" value="" />
			</div>
		</form>
	</div>
	<div class="center">
		<button class="btn btn-blue" onclick="syllabussub()">提交</button>
		<button class="btn btn-wihte mr10" onclick="mycancel()">取消</button>
	</div>
</div>
<!--//创建科目-->
<div class="y-modal" id="updateSubject">
	<div class="y-form">
		<form id="uidsub" class="ke">
			<div class="item">
				<label class="title">科目</label> <input type="text"
					name="syllabusName" id="subis6" value="" />
			</div>
			<div class="item">
				<label class="title">课时</label> <input type="text" name="classTime"
					id="subis5" value="" />
			</div>
		</form>
	</div>
	<div class="center">
		<button class="btn btn-blue" onclick="updatesyllabussub()">修改</button>
		<button class="btn btn-wihte mr10" onclick="mycancel()">取消</button>
	</div>
</div>


<!--创建阶段-->
<div class="y-modal" id="createStage">
	<div class="y-form">
		<form id="ids">
			<div class="item">

				<label class="title">阶段</label> <input type="text"
					name="syllabusName" id="nameid" />
			</div>
		</form>
	</div>
	<div class="center">
		<button class="btn btn-blue" ONCLICK="syllabus()">提交</button>
		<button class="btn btn-wihte mr10" onclick="mycancel()">取消</button>
	</div>
</div>
<!--修改阶段-->
<div class="y-modal" id="updataStage">
	<div class="y-form">
			<div class="item">
				<label class="title">阶段</label> <input type="text"
					name="syllabusName" id="unameid" />
			</div>
	</div>
	<div class="center">
		<button class="btn btn-blue" ONCLICK="updatesyllabus()">修改</button>
		<button class="btn btn-wihte mr10" onclick="mycancel()">取消</button>
	</div>
</div>
<!--//修改阶段-->


<script type="text/javascript">
	function uprunsub(id){
		var prev_element_size=$("#ddd"+id).prev().size();
		if(prev_element_size==0){
			alert("已是最前");
			return;
		}
		var prev_obj=$("#ddd"+id).prev();
		var prev_id=prev_obj.attr("id").substring(3);
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/resetSortSysSyllabus.do",
			data:"id=" +id+"&prev_id=" + prev_id,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					$("#ddd"+id).insertBefore(prev_obj);
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
	}
	function uprun(id){
		var prev_element_size=$("#sss"+id).prev().size();
		if(prev_element_size==0){
			alert("已是最前");
			return;
		}
		var prev_obj=$("#sss"+id).prev();
		var prev_id=prev_obj.attr("id").substring(3);
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/resetSortSysSyllabus.do",
			data:"id=" +id+"&prev_id=" + prev_id,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					$("#sss"+id).insertBefore(prev_obj);
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
	}
	
	function downrun(id){
		var next_element_size=$("#sss"+id).next().size();
		if(next_element_size==0){
			alert("已是最后");
			return;
		}
		var next_obj=$("#sss"+id).next();
		var next_id=next_obj.attr("id").substring(3);
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/resetSortSysSyllabus.do",
			data:"id=" +id+"&prev_id=" + next_id,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					next_obj.insertBefore($("#sss"+id));
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
		
	}
	
	function downrunsub(id){
		var next_element_size=$("#ddd"+id).next().size();
		if(next_element_size==0){
			alert("已是最后");
			return;
		}
		var next_obj=$("#ddd"+id).next();
		var next_id=next_obj.attr("id").substring(3);
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/resetSortSysSyllabus.do",
			data:"id=" +id+"&prev_id=" + next_id,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					next_obj.insertBefore($("#ddd"+id));
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
		
	}
	
	//添加课时函数
	function syllabussub() {
		var cid = $("#cid").val();
		var pid = $("#pid").val();
		var name = $("#syllabusName2").val();
		var ctime = $("#subcoursetime3").val();
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/createSubCourse.do",
			data:"id=" + cid+ "&name=" + name + "&pid=" + pid + "&ctime=" + ctime,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					var d=res.resultData;
					var coursehtml = '<li id="ddd'+d.syllabusId+'"><div><p><span id="'+d.syllabusId +'">'
						+ d.syllabusName+'</span>（课时：<span id="sbc'+d.syllabusId+'">'+ d.classTime+ '<span>）</p>'
						+ '<div class="y-icon edit" onclick="updateSubject('+d.syllabusId+','+d.syllabusName+','+d.classTime+ ')">修改</div>'
						+'<div class="y-icon del" onclick="delsub('+d.syllabusId+','+d.pid+')">删除</div>	'
						+ '<div class="y-icon arrow-up" onclick="uprunsub('+d.syllabusId+')"></div>'
						+'<div class="y-icon arrow-down" onclick="downrunsub('+d.syllabusId+')"></div>'
						+ '</div></li>';
					$("#"+d.pid).text(res.otherProperties.pctime);
					$("#ul"+d.pid).append(coursehtml);
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
	}
	//更新课时
	function updatesyllabussub() {
		var id = $("#subid").val();
		var name = $("#syllabusName6").val();
		var ctime = $("#subcoursetime5").val();
		console.log(id+" "+name+"  "+ctime)
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/updateSubCourse.do",
			data:"id=" +id+"&name=" + name + "&ctime=" + ctime,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					var d=res.resultData;
					$("#"+d.pid).html(res.otherProperties.pctime);//更新页面阶段课时
					$("#sbc"+d.syllabusId).html(d.classTime);//更新页面科目课时
					$("#sbn"+d.syllabusId).html(d.syllabusName);//更新页面科目课时
					
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
	}
	//删除课时
	function delsub(id,pid){
		var ppid=pid;
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/delsub.do",
			data:"id=" +id+"&ppid=" + ppid,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					var d=res.resultData;
					$("#"+d.pid).html(res.otherProperties.pctime);//更新页面阶段课时
					$("#ddd"+d.syllabusId).remove();//更新页面科目课时
					
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
	}
	function createSubjuctModal(id) {
		$("#pid").val(id);
		var htt = $("#createSubject");
		htt.find("#subis2").attr("id", "syllabusName2");
		htt.find("#subis3").attr("id", "subcoursetime3");
		var htm = htt.html();
		htt.find("#syllabusName2").attr("id", "subis2");
		htt.find("#subcoursetime3").attr("id", "subis3");
		layer.open({
			type : 1,
			title : [ '创建科目' ],
			skin : 'demo-class',
			shade : 0.6,
			area : [ '600px', '250px' ],
			anim : 6,
			content : htm
		//这里content是一个普通的String  
		});
	}
	function delstage(id){
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/delstage.do",
			data:"id=" +id,
			dataType : "json",
			success : function(res) {
				if (res.resultCode == 0) {
					var d=res.resultData;
					$("#sss"+d.syllabusId).remove();//更新页面
				} else {
					alert(res.resultMsg);
				}
				console.log(res)
			}
		});
	}
	function mycancel(){
		var index = layer.index; //获取当前弹层的索引号
		layer.close(index);
	}
	//延时选中状态
	$(function(){
		setTimeout(function(){ty();},200);
		$(':radio[name="type"]').attr("checked",$("#typeid").val());
	})
	//选中方法
	function ty(){
		$(":radio[name='type'][value='" + $("#typeid").val() + "']").prop("checked", "checked");
	}
	//重置表单
	function re(){
		document.getElementById("courseform").reset(); 
	}
	function createStageModal() {
		var ht = $("#createStage");
		ht.find("#nameid").attr("id", "syllabusName");
		var htm = ht.html();
		ht.find("#syllabusName").attr("id", "nameid");
		layer.open({
			type : 1,
			title : [ '创建阶段' ],
			skin : 'demo-class',
			shade : 0.6,
			area : [ '500px', '200px' ],
			anim : 6,
			content : htm
		//这里content是一个普通的String
		});
	}
	//创建阶段函数
	function syllabus() {
		var cid = $("#cid").val();
		var name = $("#syllabusName").val();
		console.log(name + "  hhhhhhhhhhh")
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/createSub.do",
			data:"id="+ cid + "&name=" + name,
			dataType : "json",
			success : function(data) {
				if (data.resultCode == 0) {
					var d = data.resultData;
					var stagehtml = '<li class="open open_close" id="sss'+d.syllabusId+'"><div><p><span id="sn'+d.syllabusId+'">'
							+ d.syllabusName+ '</span>（课时：<span id="'+d.syllabusId+'">'+ d.classTime+ '<span>）</p>'
							+ '<div onclick="createSubjuctModal('+ d.syllabusId+')" class="y-icon add">+&ensp;创建科目</div>'
							+ '<div class="y-icon edit" onclick="showUpdata('+d.syllabusId+')">修改</div>	'
							+'<div class="y-icon del" onclick="delstage('+d.syllabusId+')">删除</div>'
							+ '	<div class="y-icon arrow-up" onclick="uprun('+d.syllabusId+')"></div>	'
							+'<div class="y-icon arrow-down" onclick="downrun('+d.syllabusId+')"></div>'
							+ '</div><ul id="ul'+d.syllabusId+'"></ul></li>';
					$("#stageid").append(stagehtml);
					$("#pid").val(d.syllabusId);//设置父id
	
				} else {
					alert(data.resultMsg);
				}
				console.log(data)
			}
		});
	}
	var nt='';
	var ct='';
	//修改课时弹框
	function updateSubject(id) {
		$("#subid").val(id);
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/getSysSyllabus.do?id="+id,
			dataType : "json",
			async:false,
			success : function(data) {
				if (data.resultCode == 0) {
					nt=data.resultData.syllabusName;
					ct=data.resultData.classTime;
					
				} else {
					alert(data.resultMsg);
				}
				console.log(data)
			}
		});
		var htt = $("#updateSubject");
		htt.find("#subis6").attr("id", "syllabusName6");
		htt.find("#subis5").attr("id", "subcoursetime5");
		var htm = htt.html();
		htt.find("#syllabusName6").attr("id", "subis6");
		htt.find("#subcoursetime5").attr("id", "subis5");
		layer.open({
			type : 1,
			title : [ '创建科目' ],
			skin : 'demo-class',
			shade : 0.6,
			area : [ '600px', '250px' ],
			anim : 6,
			content : htm
		//这里content是一个普通的String  
		});
		$("#syllabusName6").val(nt);
		$("#subcoursetime5").val(ct);
	}
	//更新阶段名称
	function updatesyllabus() {
		var name = $("#syllabusName4").val();
		if (name == null || name == undefined || name == "") {
			alert('请先填写阶段名称');
			return;
		}
		var id = $("#updateid").val();
		console.log(name + "  hhhhhhhhhhh")
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/updateSta.do",
			data : "id=" + id + "&name=" + name,
			dataType : "json",
			success : function(data) {
				if (data.resultCode == 0) {
					var d = data.resultData;

					console.log($("#sn" + d.syllabusId).val() + "name..");
					$("#sn" + d.syllabusId).html(d.syllabusName);//更新页面阶段名字

				} else {
					alert(data.resultMsg);
				}
				console.log(data)
			}
		});
	}
	//修改课程
	function updatecourse() {
		var m = $("#n").val();
		var m2 = $("#n2").val();
		var m3 = $("#n3").val();
		var m4 = $("#m4").val();
		var m5 = $("#select option:selected").val();
		if (m == null || m == undefined || m == "") {
			$("#m").html('请先填写课程名称');
			return;
		} else {
			$("#m").html('');
		}
		if (m2 == null || m2 == undefined || m2 == "") {
			$("#m2").html('合同报名费');
			return;
		} else {
			$("#m2").html('');
		}
		if (isNumber(m2)) {
			$("#m2").html('请填写数字');
			return;
		} else {
			$("#m2").html('');
		}
		if (m3 == null || m3 == undefined || m3 == "") {
			$("#m3").html('合同实训费');
			return;
		} else {
			$("#m3").html('');
		}
		if (isNumber(m3)) {
			$("#m3").html('请填写数字');
			return;
		} else {
			$("#m3").html('');
		}
        if(m5 == ""){
            $("#m5").html('请选择类型');
            return;
        }else{
            $("#m5").html('');
        }
		var val = $('input:radio[name="type"]:checked').val();
		if (val == null) {
			$("#m4").html('请选择类型');
			return;
		} else {
			$("#m4").html('');
		}
		var f = $("#courseform").serialize();
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/updateCourse.do",
			data : f,
			dataType : "json",
			success : function(data) {
				if (data.resultCode == 0) {
					$("#cid").val(data.resultData);
					alert("修改成功")
				} else {
					alert(data.resultMsg);
				}
				console.log(data)
			}
		});
	}

	function isNumber(value) { //验证是否为数字
		var patrn = /^(-)?\d+(\.\d+){1,7}$/;
		if (patrn.test(value)) {
			return false
		} else {
			return true
		}
	}
	var syllabusNametmp='';
	function showUpdata(id) {
		
		$("#updateid").val(id);
		$.ajax({
			type : "POST",
			url : COURSELIST.basePath + "/course/getSysSyllabus.do?id="+id,
			dataType : "json",
			async:false,
			success : function(data) {
				if (data.resultCode == 0) {
					syllabusNametmp=data.resultData.syllabusName;
				} else {
					alert(data.resultMsg);
				}
				console.log(data)
			}
		});
		var ht = $("#updataStage");
		ht.find("#unameid").attr("id", "syllabusName4");
		var htm = ht.html();
		ht.find("#syllabusName4").attr("id", "unameid");

		layer.open({
			type : 1,
			title : [ '修改阶段' ],
			skin : 'demo-class',
			shade : 0.6,
			area : [ '500px', '200px' ],
			anim : 6,
			content : htm
		//这里content是一个普通的String
		});
		$("#syllabusName4").val(syllabusNametmp);
	}
	//绑定点击展开闭合事件
	/* $(function(){
		$(".open_close").click(function(){
			$(this).toggleClass("open");
		})
	}) */
	$(function(){
		
	})

//	select标签默认选中
    $("#lqCoursetype option[value='${course.lqCoursetypeStr}']").attr("selected", "selected");
//	select标签默认选中

</script>


