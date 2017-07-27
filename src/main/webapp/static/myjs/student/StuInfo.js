DialogStuInfo={};

var aCity = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
DialogStuInfo.init=function(qx,Userid,canEdit)
{
	if(qx=='yxStu'){//意向
		$("a[name=bjxx]").hide();
	 	$("div[name=bjxx]").hide();
		$("a[name=jyxx]").hide();
	 	$("div[name=jyxx]").hide();
		$("a[name=zstd]").hide();
	 	$("div[name=zstd]").hide();
		$("a[name=xyztls]").hide();
	 	$("div[name=xyztls]").hide();
		$("a[name=zyxx]").hide();
	 	$("div[name=zyxx]").hide();
		$("a[name=bmf]").hide();
	 	$("div[name=bmf]").hide();
		$("a[name=sxf]").hide();
	 	$("div[name=sxf]").hide();
	}else if(qx=='zsStu'){//正式
		$("a[name=zyxx]").hide();
	 	$("div[name=zyxx]").hide();
		$("a[name=bmf]").hide();
	 	$("div[name=bmf]").hide();
		$("a[name=sxf]").hide();
	 	$("div[name=sxf]").hide();
	}else if(qx=='jyStu'){//就业
		$("a[name=jyxx]").hide();
	 	$("div[name=jyxx]").hide();
		$("a[name=bmf]").hide();
	 	$("div[name=bmf]").hide();
		$("a[name=sxf]").hide();
	 	$("div[name=sxf]").hide();
	}else if(qx=='hkStu'){//回款
	 	$("a[name=ftjl]").hide();
	 	$("div[name=ftjl]").hide();
		$("a[name=bjxx]").hide();
	 	$("div[name=bjxx]").hide();
		$("a[name=jyxx]").hide();
	 	$("div[name=jyxx]").hide();
		$("a[name=xyztls]").hide();
	 	$("div[name=xyztls]").hide();
		$("a[name=zyxx]").hide();
	 	$("div[name=zyxx]").hide();
	}
	
    //==================省市=======================
    var provCodeTemp=$("#provCodeTemp").val();
    var cityCodeTemp=$("#cityCodeTemp").val();
    //1.异步加载省
      $.ajax({
        type:"post",
        url:basePath+"/student/getProvList.do",
        async:false,
        data:{},
        dataType:"json",
        success:function(data){
        	 var data=data.provList;
           	 var html="<option value=''> --请选择省份-- </option>";
             for(var i=0;i<data.length;i++){
               if(provCodeTemp==data[i].cityId){
                    html+="<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>";
               }else{
                    html+="<option value='"+data[i].cityId+"' >"+data[i].city+"</option>"; 
               }
             }
             $("#province").html(html);
             $("#city").html("<option value=''> --请选择城市-- </option>");
             if(cityCodeTemp!=null&&cityCodeTemp!=''){
               $("#province").trigger("change");//触发城市下拉框加载
             }
        } 
    });  
	//=====================上传图片监听====================
	$(document).on('change','#frontImgFile3',function(){
		DialogStuInfo.stuInfoSubmitImg("A");
	});
	$(document).on('change','#backImgFile3',function(){
		DialogStuInfo.stuInfoSubmitImg("B");
	});
	
	//=================================================
	//当前页面顶部tab切换
	$(".tabClick a").on("click",function(){
		var _this=$(this).parents(".contbox");
		$(this).parents(".tabClick").find("a").removeClass("curr");
		$(this).addClass("curr");
		_this.find(".con-ggcbox").hide();
		_this.find(".con-ggcbox").eq($(this).index()).show();
	});
		
	//点击编辑icon
	if(canEdit)
	{
	$(".able-edit").click(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".line-item");
		$(".eidt-span").hide();
		$(".read-span").show();
		_this.find(".read-span").hide();
		_this.find(".eidt-span").show();
		if(_this.find(".eidt-span").children("input").length>0){
			_this.find(".eidt-span").children("input").focus();
		}
		
		//1.编辑后赋值到文本框zzh
		if($.trim($(this).children("em").html())=="点击填写"||$.trim($(this).children("em").html())=="暂无"){
			$(this).next("span").children("input").val("");//为空					
		}else{
			$(this).next("span").children("input").val($.trim($(this).children("em").html()));//赋值
		}
	});
		
	}else
		{
			$("div.clearfix a.able-edit").removeClass("able-edit");
		}
	
	$(".eidt-span").click(function(e){
		e.stopPropagation();
	});
		
		//========文本框-失去焦点提交(姓名,身份证号,所在年级,在校担任职务,所在专业,宿舍楼号,电话,QQ,电子邮箱,家庭联系人,家庭联系人电话)============
		$(".eidt-span input[type='text']").blur(function(e){
			e.stopPropagation();
			var _this=$(this).parents(".line-item");
			//1.非空验证zzh
			if((_this.find(".label-text").html().replace("：","")=="姓名"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="民族"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="身份证号"&&($(this).val()==null||$(this).val()==""))||	
			   (_this.find(".label-text").html().replace("：","")=="院校名称"&&($(this).val()==null||$(this).val()==""))||	
			   (_this.find(".label-text").html().replace("：","")=="所在年级"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="在校担任职务"&&($(this).val()==null))||
			   (_this.find(".label-text").html().replace("：","")=="所在专业"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="宿舍楼号"&&($(this).val()==null))||
			   (_this.find(".label-text").html().replace("：","")=="电话"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="QQ"&&($(this).val()==null))||
			   (_this.find(".label-text").html().replace("：","")=="电子邮箱"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="通讯地址"&&($(this).val()==null))||
			   (_this.find(".label-text").html().replace("：","")=="家庭联系人"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="家庭联系人电话"&&($(this).val()==null||$(this).val()==""))||
			   (_this.find(".label-text").html().replace("：","")=="民族")||
			   (_this.find(".label-text").html().replace("：","")=="所在院校")
			){//为空
				$(".eidt-span").hide();
				$(".read-span").show();
				_this.find(".read-span").hide();
				_this.find(".eidt-span").show();
				if(_this.find(".eidt-span").children("input")){
					_this.find(".eidt-span").children("input").focus();
				}
				//非空验证zzh
				if(_this.find(".label-text").html().replace("：","")=="民族"){
					_this.find(".error-tips").html("");//民族不进行提示
				}else if(_this.find(".label-text").html().replace("：","")=="院校名称"){
					_this.find(".error-tips").html("");//院校名称不进行提示
				}else if(_this.find(".label-text").html().replace("：","")=="在校担任职务"){
					_this.find(".error-tips").html("");//不进行提示
					_this=$(this).parents(".line-item");
					_this.find(".read-span").show();
					_this.find(".eidt-span").hide();
				}else if(_this.find(".label-text").html().replace("：","")=="宿舍楼号"){
					_this.find(".error-tips").html("");//不进行提示
					_this=$(this).parents(".line-item");
					_this.find(".read-span").show();
					_this.find(".eidt-span").hide();
				}else if(_this.find(".label-text").html().replace("：","")=="QQ"){
					_this.find(".error-tips").html("");//不进行提示
					_this=$(this).parents(".line-item");
					_this.find(".read-span").show();
					_this.find(".eidt-span").hide();
				}else if(_this.find(".label-text").html().replace("：","")=="通讯地址"){
					_this.find(".error-tips").html("");//不进行提示
					_this=$(this).parents(".line-item");
					_this.find(".read-span").show();
					_this.find(".eidt-span").hide();
				}else{
					_this.find(".error-tips").html("请输入"+_this.find(".label-text").html().replace("：",""));
				}
			}else{//不为空
				_this=$(this).parents(".line-item");
				_this.find(".read-span").show();
				_this.find(".eidt-span").hide();
				
				//1.清空提示
				_this.find(".error-tips").html("");
				//2.定义传递的参数
				var parameterName;
				var parameterValue;
				//3.获取当前文本框的id名称
				var textId=_this.find(".eidt-span").children("input").attr("id");
				//4.验证是否相等,相等则不执行修改
				if($.trim($("#"+textId).val())==$.trim($("em[name="+textId+"]").html())){
					return;
				}
				//4.1验证
				var textCheck=$("#"+textId).val();
				if(textId=="realName"){//姓名
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("学员姓名包含非法字符");
						return;
					}
				}else if(textId=="idCard"){//身份证
					if(!DialogStuInfo.isIdCardNo(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("身份证号码格式不正确");
						return;
					}
				}else if(textId=="grade"){//年级
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("年级包含非法字符");
						return;
					}
					var patrn=/^[0-9]+$/;
					if(!patrn.test(textCheck)){ 
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("请填写正确的年份数字，如2012");
						return;
					}
				}else if(textId=="schoolDuty"){//在校担任职务
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("职务包含非法字符");
						return;
					}
				}else if(textId=="major"){//所在专业
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("专业包含非法字符");
						return;
					}
				}else if(textId=="schoolDormitory"){//宿舍楼号
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("宿舍楼号包含非法字符");
						return;
					}
				}else if(textId=="tel"){//电话
					if(DialogStuInfo.isMobile(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("手机号码格式不正确");
						return;
					}
				}else if(textId=="qq"){//qq
					if(!/^[1-9]\d{4,12}$/.test(textCheck)&&textCheck!=""){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("QQ号格式不正确");
						return;
					}
				}else if(textId=="email"){//邮箱
					if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("邮箱格式不正确");
						return;
					}
				}else if(textId=="address"){//通讯地址
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("通讯地址包含非法字符");
						return;
					}
				}else if(textId=="parentInfo"){//家庭联系人
					if(DialogStuInfo.isContainsSpecialChar(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("家庭联系人包含非法字符");
						return;
					}
				}else if(textId=="parentTel"){//家庭联系人电话
					if(DialogStuInfo.isTel(textCheck)){
						_this.find(".read-span").hide();
						_this.find(".eidt-span").show();
						_this.find(".eidt-span").children("input").focus();
						_this.find(".error-tips").html("联系电话格式不正确");
						return;
					}
				}
				//5赋值
				parameterName=textId;
				parameterValue=$("#"+textId).val();
				//6.组装
				var data="userId="+Userid+"&"+parameterName+"="+parameterValue;
				//7.异步并回显
				 $.ajax({
			    	  type:"post",
			             url:basePath+"/student/editStuInfo.do",  
			             data:data,
			             dataType:"json",
			             success:function(data){
			         		if(data.code == 200){
			         			//8.
			         			var originHtml = $.trim($("em[name=schoolDuty]").html());//在校担任职务
			         			var schoolDorHtml=$.trim($("em[name=schoolDormitory]").html());//宿舍楼号
			         			var qqHtml=$.trim($("em[name=qq]").html());//qq
			         			var addressHtml=$.trim($("em[name=address]").html());//地址
			         			//9.
			         			$("em[name="+textId+"]").html(parameterValue);
			         			//10.根据身份证设置出生年月
			         			if(textId=="idCard"){
			         				var birth=$("#"+textId).val().substring(6, 14);
			         				birth=birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6);
			         				$("em[name=birth]").html(birth);
			         			}
			         			//11在校担任职务
			         			if(textId=="schoolDuty"){
				         			if($("#schoolDuty").val()=="" && originHtml =="点击填写"){
				         				$("em[name=schoolDuty]").html("点击填写");
				         				return;
				         			}else if($("#schoolDuty").val()==""&& originHtml !="点击填写"){
				         				$("em[name=schoolDuty]").html("点击填写");
				         			}
			         			}
			         			//12
			         			if(textId=="schoolDormitory"){
				         			if($("#schoolDormitory").val()=="" && schoolDorHtml =="点击填写"){
				         				$("em[name=schoolDormitory]").html("点击填写");
				         				return;
				         			}else if($("#schoolDormitory").val()==""&& schoolDorHtml !="点击填写"){
				         				$("em[name=schoolDormitory]").html("点击填写");
				         			}
			         			}
			         			//13
			         			if(textId=="qq"){
				         			if($("#qq").val()=="" && qqHtml =="点击填写"){
				         				$("em[name=qq]").html("点击填写");
				         				return;
				         			}else if($("#qq").val()==""&& qqHtml !="点击填写"){
				         				$("em[name=qq]").html("点击填写");
				         			}
			         			}
			         			//14
			         			if(textId=="address"){
				         			if($("#address").val()=="" && addressHtml =="点击填写"){
				         				$("em[name=address]").html("点击填写");
				         				return;
				         			}else if($("#address").val()==""&& addressHtml !="点击填写"){
				         				$("em[name=address]").html("点击填写");
				         			}
			         			}
		         				layer.msg("修改成功");
			         		}else{
			          			layer.msg("修改失败");
			          		}
			             }
			    });
			}
		});
		//==========================单选框-失去焦点提交(性别)======================================
		$(":radio[name=sex]").click(function(){
   			//1.定义传递的参数
   			var parameterName;
			var parameterValue;
			//2.判断
			if($("#sexTemp").val()!=$(this).val()){
				parameterName="sex";
				parameterValue=$(this).val();
			}else{
				return;
			}
			//3.组装
   			var data="userId="+Userid+"&"+parameterName+"="+parameterValue;
   			//4.提交
   			$.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuInfo.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
		         			if(parameterValue==0){
			         			$("em[name=sex]").html("男");
		         			}else{
		         				$("em[name=sex]").html("女");
		         			}
		         			layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
  		});
		
		//===============================下拉框修改监听(院校类别)==========================================
		$('#schoolTypeCode').change(function(){
			//1.定义传递的参数
   			var parameterName;
			var parameterValue;
			//2.获取选中的
			var schoolTypeCode=$(this).children('option:selected').val();
			var schoolTypeName=$(this).children('option:selected').text();
			//3.判断
			if(schoolTypeName==$("em[name=schoolTypeCode]").html()||schoolTypeCode==""){
				return;
			}else{
				parameterName="schoolTypeCode";
				parameterValue=$(this).children('option:selected').val();
			}
			//4.组装
   			var data="userId="+Userid+"&"+parameterName+"="+parameterValue;
			//5.提交
   			$.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuInfo.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
			         		$("em[name=schoolTypeCode]").html(schoolTypeName);
		         			layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		});
		//===============================下拉框修改监听(课程类别)==========================================
		$('#courseTypeCode').change(function(){
			//1.定义传递的参数
   			var parameterName;
			var parameterValue;
			//2.获取选中的
			var courseTypeCode=$(this).children('option:selected').val();
			var courseTypeName=$(this).children('option:selected').text();
			//3.判断
			if(courseTypeName==$("em[name=courseTypeCode]").html()||courseTypeCode==""){
				return;
			}else{
				parameterName="courseTypeCode";
				parameterValue=$(this).children('option:selected').val();
			}
			//4.组装
   			var data="userId="+Userid+"&"+parameterName+"="+parameterValue;
			//5.提交
   			$.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuInfo.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
			         		$("em[name=courseTypeCode]").html(courseTypeName);
		         			layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		});
		//===============================下拉框修改监听(报名费)==========================================
		$('#isAvaiableCode').change(function(){
			//1.定义传递的参数
   			var parameterName;
			var parameterValue;
			//2.获取选中的
			var isAvaiableCode=$(this).children('option:selected').val();
			var isAvaiableName=$(this).children('option:selected').text();
			//3.判断
			if(isAvaiableName==$("em[name=isAvaiableCode]").html()||isAvaiableCode==""){
				return;
			}else{
				parameterName="isAvaiableCode";
				parameterValue=$(this).children('option:selected').val();
			}
			//4.组装
   			var data="userId="+Userid+"&"+parameterName+"="+parameterValue;
			//5.提交
   			$.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuInfo.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
			         		$("em[name=isAvaiableCode]").html(isAvaiableName);
		         			layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		});
		//===============================下拉框修改监听(学费)==========================================
		$('#hasPaidCode').change(function(){
			//1.定义传递的参数
   			var parameterName;
			var parameterValue;
			//2.获取选中的
			var hasPaidCode=$(this).children('option:selected').val();
			var hasPaidName=$(this).children('option:selected').text();
			//3.判断
			if(hasPaidName==$("em[name=hasPaidCode]").html()||hasPaidCode==""){
				return;
			}else{
				parameterName="hasPaidCode";
				parameterValue=$(this).children('option:selected').val();
			}
			//4.组装
   			var data="userId="+Userid+"&"+parameterName+"="+parameterValue;
			//5.提交
   			$.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuInfo.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
			         		$("em[name=hasPaidCode]").html(hasPaidName);
		         			layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		});
		//==============================复选框监听(就业意向)===========================================
		$("input[name='jobCityCode']").click(function(e){ 
			var jobCitys = [];//处理意向就业地点
			var jobCityNames=[];
			$("input[name='jobCityCode']").each(function(index,element){
				if($(element).is(':checked')){
					jobCitys.push(element.value);
				    jobCityNames.push(element.title);
				}
			});
			var data="userId="+Userid+"&jobCityCode="+jobCitys.join(",");
			//提交
   			$.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuInfo.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
			         		$("#jobCityCode").html(jobCityNames.join(","));
		         			layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		});
		
		//选择空白处提交
		$("body").click(function(e){
			$(".read-span").show();
			$(".eidt-span").hide();
			
		});
		//查看更多按钮点击事件
		$(".sl-down-btn").on("click",function(){
			$(this).hide();
			$(this).parents(".con-ggcbox").find(".more-list-disply").slideDown(100);
			$(this).siblings(".sl-up-btn").show();
		});
		$(".sl-up-btn").on("click",function(){
			$(this).hide();
			$(this).parents(".con-ggcbox").find(".more-list-disply").slideUp(100);
			$(this).siblings(".sl-down-btn").show();
		});
		
		
		//=================根据权限展示不同的选项卡zzh(tab)===================================	
};

/** 身份证号码的验证规则 */
DialogStuInfo.isIdCardNo=function (sId){ 
	var iSum = 0;
	var info = "";
	if (!/^\d{17}(\d|x|X)$/i.test(sId)){
		//alert("你输入的身份证长度或格式错误");
		return false;
	}
	sId = sId.replace(/x$/i, "a");
	if (aCity[parseInt(sId.substr(0, 2))] == null){
		//alert("你的身份证地区非法");
		return false;
	}
	sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-"+ Number(sId.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"));
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())){
		//alert("身份证上的出生日期非法");
		return false;
	}
	for (var i = 17; i >= 0; i--){
		iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
	}
	if (iSum % 11 != 1){
		//alert("你输入的身份证号非法");
		return false;
	}
	//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
	return true;
}
/**======================================图片上传开始===========================================**/
DialogStuInfo.stuInfoSubmitImg=function (type){
	var fileElementId = null,returnShow = null,imgName = null;
	if(type === 'A'){
//		alert("正面");
		fileElementId = 'frontImgFile3';returnShow = 'returnShowFront';imgName = 'frontImgName3';
	} 
	if(type === 'B'){
//		alert("反面");
		fileElementId = 'backImgFile3'; returnShow = 'returnShowBack';imgName = 'backImgName3';
	}
		
	$.ajaxFileUpload({
		valid_extensions : ['jpg','png','jpeg','bmp'],
		url : basePath+"/student/uploadImgAndSave.do", //上传文件的服务端
		secureuri : false, //是否启用安全提交
		dataType : 'json', //数据类型 
		data:{
			"userId":"${user.userId}",
			"type":type
		},
		fileElementId : fileElementId, //表示文件域ID
		//提交成功后处理函数      data为返回值，status为执行的状态
		success : function(data, status) {
	    switch(data.code) {
        	case 201:
        		layer.msg("请选择文件");
        		break;
        	case 202:
        		layer.msg("图片太大，请上传小于的3M的图片");
        		break;
        	case 203:
        		layer.msg("图片上传出现异常");
        		break;
        	case 204:
        		layer.msg("请选择bmp,jpg,jpeg,png格式的图片");
        	default:
        		var resourcePath = $("#resourcePath").val();
        		$("img[name="+returnShow+"]").attr("src",resourcePath + data.fileName);
        		$("#"+imgName).val(data.fileName);
        		break;
	        }
			
		},
		//提交失败处理函数
		error : function(html, status, e) {
			alert("错误:"+status);
		}
	});
};

/** 省改变触发  */
DialogStuInfo.tm_change_province=function (obj){
	   schoolProvName=$(obj).find("option:selected").html();//获取文本
	   $("em[name=schoolProv]").html(schoolProvName);
    var cityCodeTemp=$("#cityCodeTemp").val();
    var  objid=obj.value;
    schoolProvCode=obj.value;//保存时用
    if(!objid){//如果省为空
        $("#city").html("<option value=''> --请选择城市-- </option>");//清空市
    }
    $.ajax({
        type:"post",
        url:basePath+"/student/findCitys.do",
        data:{"id":objid},
        dataType:"json",
        success:function(data){
     	  var data=data.cityList;
           var html="<option value=''> --请选择城市-- </option>";
           for(var i=0;i<data.length;i++){
             if(cityCodeTemp==data[i].cityId){
                 html+="<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>";
             }else{
                 html+="<option value='"+data[i].cityId+"'>"+data[i].city+"</option>"; 
             }
           }
           $("#city").html(html);
        } 
     });
 }

//保存院校所在区域
var schoolProvCode="${stuInfo.schoolProvCode}";//省默认id
var schoolProvName="${schoolProvName}";//省默认名称
var schoolCityName="${schoolCityName}";//市默认名称
function editProvAndCity(obj){
	   schoolCityName=$(obj).find("option:selected").html();//获取文本
	   if(obj.value!=''){
		   var data="userId=${user.userId}&schoolProvCode="+schoolProvCode+"&schoolCityCode="+obj.value;
		   //提交
		   $.ajax({
	    	  type:"post",
	             url:basePath+"/student/editStuInfo.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
	         			$("em[name=schoolProv]").html(schoolProvName);
	         			$("em[name=schoolCity]").html(schoolCityName);
	         			layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
		    });
	   }else{
		   
	   //alert(obj.value);
	   }
};



/** 判断是否包含中英文特殊字符，除英文"-_"字符外  */
DialogStuInfo.isContainsSpecialChar= function (value){
	   var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
    if(reg.test(value)){
 	   return true;
    }else{
 	   return false;
    }
}

/** 手机号码验证  */
DialogStuInfo.isMobile= function (value){
	   var length = value.length;   
	   if(length == 11 && /^1[3|4|5|7|8]\d{9}$/.test(value)){
		   return false;
	   }else{
		   return true;
	   }
}

/** 联系电话(手机/电话皆可)验证   */
DialogStuInfo.isTel= function (value){
	   var length = value.length;
	   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	   var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
	   if((length == 11 && mobile.test(value)) || (length == 7 && tel.test(value))){
		   return false;
	   }else{
		   return true;
	   }
}