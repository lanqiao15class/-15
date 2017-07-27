/**
 * 
 */
 ACCOUNTMANAGER={};
 //表格对象
ACCOUNTMANAGER.Grid= null;
ACCOUNTMANAGER.columns = [];
ACCOUNTMANAGER.id="";
//0代表部门列表  1代表角色列表
ACCOUNTMANAGER.status=0;
ACCOUNTMANAGER.url=null;
ACCOUNTMANAGER.layerc=null;
ACCOUNTMANAGER.layerc1=null;
//点击查询时保存查询参数for分页
ACCOUNTMANAGER.queryParam={};
var  zTree_Menu = null;
ACCOUNTMANAGER.roles="";
//初始化
 ACCOUNTMANAGER.init=function(){
 	//初始化树
 	ACCOUNTMANAGER.inittree();
	//初始化表格
	ACCOUNTMANAGER.initgrad();
	 //初始化角色
	ACCOUNTMANAGER.role();
	//初始化按钮点击事件
	ACCOUNTMANAGER.onclick();
	
	 ACCOUNTMANAGER.validate();

 }
 /**=====================初始化角色=========================**/
 ACCOUNTMANAGER.role=function(){
 	//获取用户所对应的角色和功能
 	$.ajax({
			type:'post',
			url:basePath+"/accountManager/getUserRole.do",
			dataType:"json",
			success: function(rdata) {
				ACCOUNTMANAGER.roles=rdata;
				//console.log("role            "+JSON.stringify(rdata));
			 	$.each(rdata.Roles,function(i,item){
			 		$("#accountli").append("<li roleid='"+item.id+"'><a style='overflow: hidden;text-overflow:ellipsis;white-space: normal;width: 130px' title='"+item.name+"'>"+item.name+"</a></li>");
			 		$("#accountrole").append('<label  style="display:inline-block"><input  type="checkbox" name="roleId"  value="'+item.id+'"><span  id="checkbox'+item.id+'">'+item.name+'</span>&emsp;&emsp;</label>');
 				})
 				ACCOUNTMANAGER.guanka();
			}
 	})
 	
 
 
 }
 
 //关卡切换角色点击
 ACCOUNTMANAGER.guanka=function(){
	 	//初始化ckeckbox  点击时间
 	$("input[type='checkbox']").on("click",function(){
 		var  Nodes=new Array();
 		//console.log(JSON.stringify(ACCOUNTMANAGER.roles));
 		if($("input[type='checkbox']:checked").length>0){
 			$("input[type='checkbox']:checked").each(function(){
 				var  roleid=$(this).val();
				for(var key in ACCOUNTMANAGER.roles){
					//console.log("roleid     "+roleid+"   key2     " +key+"           "+JSON.stringify(roles[key]));
					if(key==roleid){
							var no=ACCOUNTMANAGER.roles[key];
							$.each(no,function(i,item){
								var  flag=false;
								$.each(Nodes,function(i1,item1){
									if(item1.id==item.id){
										flag=true;
										return false;
									}else{
									}
								})
								if(flag==true){
									flag=false
								}else{
									//console.log("           "+JSON.stringify(item));
									Nodes.push(item);
								}
							});
					}
		 		}
 			})
 		}else{
 		}
	 	ACCOUNTMANAGER.treeAddUser(Nodes);
		ACCOUNTMANAGER.zNodes=Nodes;
 	})
 	
 	var  roleName="";
 	
	 //角色的点击时间
 	 $(".accountnav ul li a").click(function(e){
 	 	if(isNull(ACCOUNTMANAGER.parent)){
 	 		ACCOUNTMANAGER.parent = $(this).parent();
			ACCOUNTMANAGER.parent.css({"background-color":"#D4D4D4"}) 
			var roleid=$(this).parent().attr("roleid");
			//alert($(this).parent().attr("roleid"));
			var icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="ACCOUNTMANAGER.showRole('+roleid+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>';
			var icon_look1="";
			if(ACCOUNTMANAGER.rolestatus==1){
			icon_look1='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="编辑" onclick="ACCOUNTMANAGER.showRoleUpdate('+roleid+')"><i class="opti-icon Hui-iconfont">&#xe60c;</i></a>';
			}
			$(this).after(icon_look+icon_look1);
			roleName=$(this).text();
 	 	}else{
 	 		ACCOUNTMANAGER.parent.find("a").eq(1).remove();
 	 		ACCOUNTMANAGER.parent.find("a").eq(1).remove();
 	 		ACCOUNTMANAGER.parent.css({"background-color":"#ffffff"})
 	 		ACCOUNTMANAGER.parent = $(this).parent();
			ACCOUNTMANAGER.parent.css({"background-color":"#D4D4D4"})
			var roleid=$(this).parent().attr("roleid");
			var icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="ACCOUNTMANAGER.showRole('+roleid+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>';
			var icon_look1="";
			
			if(ACCOUNTMANAGER.rolestatus==1){
				icon_look1='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="编辑" onclick="ACCOUNTMANAGER.showRoleUpdate('+roleid+')"><i class="opti-icon Hui-iconfont">&#xe60c;</i></a>';
			}
			
			$(this).after(icon_look+icon_look1);
			
			roleName=$(this).text();
			//alert($(this).parent().attr("roleid"));
 	 	}
 	 	ACCOUNTMANAGER.roleId=$(this).parent().attr("roleid");
		//初始化数据
	    ACCOUNTMANAGER.queryParam.currpage=1;
	    ACCOUNTMANAGER.queryParam.pageSize=PageWiget.getPageSize();
		ACCOUNTMANAGER.queryParam.roleId=ACCOUNTMANAGER.roleId;
		ACCOUNTMANAGER.queryParam.roleName=roleName;
		ACCOUNTMANAGER.queryParam.status=ACCOUNTMANAGER.status;
		ACCOUNTMANAGER.url=	basePath+"/accountManager/getUserByDepartment.do";
		ACCOUNTMANAGER.ajaxSubmit(ACCOUNTMANAGER.queryParam,ACCOUNTMANAGER.url);
	 })
	 
	
 }
 
ACCOUNTMANAGER.roleId="";
  /**=====================初始化角色结束=========================**/
 
/**=========================查看角色开始=================================**/
ACCOUNTMANAGER.showRole=function(id){
	/*$.ajax({
			type: "POST",
			url:basePath+"/accountManager/getFunctionByLoginUser.do",
			dataType:"json",
			async:false,
			success: function(rdata) {
				console.log(JSON.stringify(rdata));
				var setting = {
					check: {
						enable: true,
						chkStyle: "checkbox",
						chkboxType: { "Y" : "ps", "N" : "s" }
					},
					view: {
						showIcon: ACCOUNTMANAGER.showIconForTree
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};
				
			}
 		})*/
 		$.ajax({
			type:'post',
			url:basePath+"/accountManager/getFunctionByRoleId.do",
			data:"roleId="+id,
			dataType:"json",
			async:false,
			success: function(rdata) {
					//console.log(JSON.stringify(rdata));
					$("#showUserRole").text(rdata.roleName);
					var setting = {
						view: {
							showIcon: ACCOUNTMANAGER.showIconForTree
						},
						data: {
							simpleData: {
								enable: true
							}
						}
					};
					
				$.fn.zTree.init($("#accounttreeDemo3"), setting, rdata.fucntion);	
 		}
 		})
		ACCOUNTMANAGER.showUroleDialogLayer2=layer.open({
		  type: 1, 
		  title: ['查看角色'],
		  skin: 'demo-class',
		  shade: 0.6,
		  anim:8,
		  area: ['610px', '550px'],
		  content:$("#showUroleDialogLayer2").show()  
		});
}

/**=========================查看角色结束=================================**/



/**=========================编辑角色开始=================================**/
ACCOUNTMANAGER.showRoleUpdate=function(id){
	$.ajax({
			type: "POST",
			url:basePath+"/accountManager/getFunctionByLoginUser.do",
			dataType:"json",
			async:false,
			success: function(rdata) {
					//console.log(JSON.stringify(rdata));
					var setting = {
						check: {
							enable: true,
							chkStyle: "checkbox",
							chkboxType: { "Y" : "ps", "N" : "s" }
						},
						view: {
							showIcon: ACCOUNTMANAGER.showIconForTree
						},
						data: {
							simpleData: {
								enable: true
							}
						}
					};
					$.fn.zTree.init($("#accounttreeDemo2"), setting, rdata.fucntion);
				}
 		})
 		$.ajax({
			type: "POST",
			url:basePath+"/accountManager/getFunctionByRoleId.do",
			data:"roleId="+id,
			dataType:"json",
			async:false,
			success: function(rdata) {
					//console.log("role   1111        "+JSON.stringify(rdata));
					$("#addUserRole").val(rdata.roleName);
					$("#RoleId").val(id);
					
					  var treeObj = $.fn.zTree.getZTreeObj("accounttreeDemo2");
					 // var nodes = treeObj.getNodes();
					  $.each(rdata.fucntion,function(i,item){
					  	if(item.isdir==1){
					  	}else{
					  	 var node = treeObj.getNodeByParam("id", item.id, null);
					      treeObj.checkNode(node, true, true);
					  	}
					  	 
					  })
			}
 		})
		ACCOUNTMANAGER.showUroleDialogLayer=layer.open({
		  type: 1, 
		  title: ['修改角色'],
		  skin: 'demo-class',
		  shade: 0.6,
		  anim:8,
		  area: ['610px', '550px'],
		  content:$("#showUroleDialogLayer").show()  
		});

}

/**=========================编辑角色结束=================================**/



ACCOUNTMANAGER.treeAddUser=function( roles){
	//console.log("22222"+roles);
	var setting = {
					view: {
						showIcon: ACCOUNTMANAGER.showIconForTree
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};
				
		$.fn.zTree.init($("#accounttreeDemo"), setting, roles);		
}



ACCOUNTMANAGER.showIconForTree=function (treeId, treeNode) {
	return false;
}; 

 /**=====================页面上点击事件初始化开始=========================**/
 ACCOUNTMANAGER.index;
  ACCOUNTMANAGER.parent=null;
var showAddAccUserDialogLayer="";
ACCOUNTMANAGER.showUroleDialogLayer="";
 ACCOUNTMANAGER.onclick=function(){
 	//添加角色 
 	$("#showAddAccRoleDialog").on("click",function(){
 		$("#RoleId").val("");
 		$("#addUserRoleError").text("");
 		$("#addUserRole").val("");
 		$.ajax({
			type: "POST",
			url:basePath+"/accountManager/getFunctionByLoginUser.do",
			dataType:"json",
			success: function(rdata) {
					//console.log(JSON.stringify(rdata));
					var setting = {
						check: {
							enable: true,
							chkStyle: "checkbox",
							chkboxType: { "Y" : "ps", "N" : "s" }
						},
						view: {
							showIcon: ACCOUNTMANAGER.showIconForTree
						},
						data: {
							simpleData: {
								enable: true
							}
						}
					};
					$.fn.zTree.init($("#accounttreeDemo2"), setting, rdata.fucntion);		
					ACCOUNTMANAGER.showUroleDialogLayer=layer.open({
					  type: 1, 
					  title: ['添加角色'],
					  skin: 'demo-class',
					  shade: 0.6,
					  anim:8,
					  area: ['610px', '550px'],
					  content:$("#showUroleDialogLayer").show()  
					});
			}
 		})
 		
 	})
 	
 //添加用户保存
 	$("#submit_save1").on("click",function(){
 		var roleName=$("#addUserRole").val();
 		if(isNull(roleName)){
 			$("#addUserRoleError").text("请输入角色名称");
 		}else  if(roleName.length>8){
 		$("#addUserRoleError").text("角色名称最多8位");
 		}else{
 			$("#addUserRoleError").text("");
 			//获取被点击的节点
 			  var treeObj = $.fn.zTree.getZTreeObj("accounttreeDemo2");
		        var nodes = treeObj.getCheckedNodes(true);
		        var functionid = "";
		        var arr=new Array();
		        for (var i = 0; i < nodes.length; i++) {
		        	if(nodes[i].isdir!="1"){
		        		 functionid += ","+nodes[i].id;
		        	}
		        	var functions={};
		        	functions.id=nodes[i].id;
		        	functions.isdir=nodes[i].isdir;
		        	functions.pId=nodes[i].pId;
		        	functions.name=nodes[i].name;
		            arr.push(functions);
		        }
		       // console.log("  functionid  "+functionid);
 			//添加角色到数据库
 			$.ajax({
				type: "POST",
				url:basePath+"/accountManager/addRole.do",
				data:"roleName="+roleName+"&functionid="+functionid+"&roleId="+$("#RoleId").val(),
				dataType:"json",
				async:false,
				success: function(rdata) {
					//console.log(JSON.stringify(rdata));
					if(rdata.code==200){
						layer.msg('角色添加成功', {time: 2000, icon:1});
						layer.close(ACCOUNTMANAGER.showUroleDialogLayer);
						$("#accountli").append("<li roleid='"+rdata.roleId+"'><a  style='overflow: hidden;text-overflow:ellipsis;white-space: normal;width: 130px' title='"+rdata.roleName+"'>"+rdata.roleName+"</a></li>");
 						$("#accountrole").append('<label  style="display:inline-block"><input  type="checkbox" name="roleId"  value="'+rdata.roleId+'"><span  id="checkbox'+rdata.roleId+'">'+rdata.roleName+'</span></label>&emsp;&emsp;');
						ACCOUNTMANAGER.guanka();
						var  id=rdata.roleId;
						ACCOUNTMANAGER.roles[id]=arr;
						//console.log(JSON.stringify(arr)+"添加+        "+JSON.stringify(ACCOUNTMANAGER.roles));
					}else if(rdata.code==400){
						layer.msg('角色修改成功', {time: 2000, icon:1});
						layer.close(ACCOUNTMANAGER.showUroleDialogLayer);
						ACCOUNTMANAGER.parent.find("a").eq(0).text(rdata.roleName);
						ACCOUNTMANAGER.parent.find("a").eq(0).attr("title",rdata.roleName);
						$("#checkbox"+rdata.roleId).text(rdata.roleName);
						ACCOUNTMANAGER.guanka();
						var  id=rdata.roleId;
						ACCOUNTMANAGER.roles[id]=arr;
						//console.log(JSON.stringify(arr)+"修改             "+JSON.stringify(ACCOUNTMANAGER.roles));
				
						}else{
						layer.msg('角色添加失败,请稍后再试', {time: 3000, icon:2});
						layer.close(ACCOUNTMANAGER.showUroleDialogLayer);
					}
					
				}
			
			})
 		}
 	})
 
 	 	//弹框取消
 $(".cancel_bnt").on("click",function(){
 	layer.close(showAddAccUserDialogLayer);
 	
 })
 	//弹框取消
 $(".cancel_bnt1").on("click",function(){
 	layer.close(ACCOUNTMANAGER.showUroleDialogLayer);
 	
 })
  	//弹框取消
 $(".cancel_bnt2").on("click",function(){
 	layer.close(ACCOUNTMANAGER.showUroleDialogLayer2);
 	
 })
 	//添加用户
 	$("#showAddAccUserDialog").on("click",function(){
 		$("#userId").val("");
 		ACCOUNTMANAGER.validate1.resetForm();
 		$("#loginTelerror").text("");
		$("#loginEmailerror").text("");
 		$('#validateForm')[0].reset();
 		$("input[type='radio']").eq(0).prop("checked",true);
 		$("input[type='checkbox']:checked").each(function(){
 			$(this).prop("checked",false);
 			
 			
 		})
 		var zTreeObj = $.fn.zTree.getZTreeObj("accounttreeDemo");
 		if(isNull(zTreeObj)){
 		
 		}else{
 			zTreeObj.destroy();
 		}
		
 		showAddAccUserDialogLayer=layer.open({
				  type: 1, 
				  title: ['添加用户'],
				  skin: 'demo-class',
				  shade: 0.6,
				  anim:8,
				  area: ['620px', '570px'],
				  content:$("#showAddAccUserDialogLayer").show()  
				});
 	})
 	
 	//切换选项卡curr
 	$("#accountManagerzh").on("click",function(){
 	    $(this).addClass("curr");
 	    $("#accountManagerRole").removeClass("curr");
 		$(".zTreeDemoBackground").show();
 		$(".accountnav").hide();
 		if(isNull(ACCOUNTMANAGER.parent)){
 		}else{
	 		ACCOUNTMANAGER.parent.css({"background-color":"#ffffff"});
	 		ACCOUNTMANAGER.parent.find("a").eq(1).remove();
	 		ACCOUNTMANAGER.parent.find("a").eq(1).remove();
	 		ACCOUNTMANAGER.parent="";
 		}
 		
 		ACCOUNTMANAGER.status=0;
 	})
 	$("#accountManagerRole").on("click",function(){
 	    $(this).addClass("curr");
 	      $("#accountManagerzh").removeClass("curr");
 		$(".zTreeDemoBackground").hide();
 		$(".accountnav").show();
 		ACCOUNTMANAGER.status=1;
 		//取消被选中节点
		 zTree_Menu.cancelSelectedNode();
 		
 	})
 	
 
 
 
 }
 ACCOUNTMANAGER.showUroleDialogLayer2="";
  ACCOUNTMANAGER.validate1="";
 /**=====================页面上点击事件初始化结束=========================**/
  ACCOUNTMANAGER.validate=function(){
  		 ACCOUNTMANAGER.validate1=$("#validateForm").validate({
		onfocusout: function(element) {//是否在失去时验证 
			$(element).valid(); 
		},
		//focusCleanup:true,
		rules:{
			 loginTel:{
				//required:true,
				isMobileCheck:true
			 },
			 realName:{
				required:true
				//isChinese:true
			 },
			 loginEmail:{
				required:true,
				isEmail:true
			 }/*,
			 pwd:{
				 required:true,
				 checkPwdIndexOfNull:true,
				 pwdCheck:true
			 },
             cpwd:{
            	 required:true,
                 equalTo:"#pwd"//验证密码是否一致
             }*/
		},
		messages:{
			 loginTel:{
				//required:"请输入手机号码",
				isMobileCheck:"手机号码格式不正确"
			 },
			 realName:{
				required:"请输入真实姓名"
			//	isChinese:"真实姓名必须是中文"
			 },
			 loginEmail:{
				required:"请输入邮箱",
				isEmail:"请正确填写您的邮箱"
			 }/*,
			 pwd:{
				 required:"请输入您的密码",
				 checkPwdIndexOfNull:"密码不能包含空格",
				 pwdCheck:"密码长度8~16位，其中数字，字母和符号至少包含两种"
			 },
             cpwd:{
            	 required:"请再次输入您的密码",
            	 equalTo:"两次密码输入不一致"
             }*/
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parents('.clearfix').next('.error-tips'));  
		},
		errorCallback:function(element){//自己添加的验证失败回调方法 
			/*
			$('#loginTel').focus(function(){
				if($('#loginTel').parent().next(".error-tips").children(".error").html()=='请输入手机号码'){
					$('#loginTel').parent().next(".error-tips").text('');//清空
					$('#loginTel').parent().next(".error-tips").append("<label class='error'>请输入手机号码</label>");
				}
				
			});
			$('#realName').focus(function(){
				
				if($('#realName').parent().next(".error-tips").children(".error").html()=='请输入真实姓名'){
					$('#realName').parent().next(".error-tips").text('');//清空
					$('#realName').parent().next(".error-tips").append("<label class='error'>请输入真实姓名</label>");
				}
			});
			$('#loginEmail').focus(function(){
				
				if($('#loginEmail').parent().next(".error-tips").children(".error").html()=='请输入邮箱'){
					$('#loginEmail').parent().next(".error-tips").text('');//清空
					$('#loginEmail').parent().next(".error-tips").append("<label class='error'>请输入邮箱</label>");
				}
			});*/
			
			
		},  
		submitHandler:function(){//验证通过后进入
			//alert(1);
				//保存
					$.ajax({
						type: "POST",
						url:basePath+"/accountManager/addUser.do",
						data:$('#validateForm').serialize(),
						dataType:"json",
						async:false,
						success: function(data) {
								//console.log(JSON.stringify(data));
								if(data.code=="200"){
									layer.msg('用户添加成功', {time: 2000, icon:1});
									 //调用数据显示部门
									ACCOUNTMANAGER.dopagechange(1,20);
									layer.close(showAddAccUserDialogLayer);
								}else  if(data.code=="400"){
									layer.msg('用户修改成功', {time: 2000, icon:1});
								 //调用数据显示部门
									ACCOUNTMANAGER.dopagechange(1,20);
									layer.close(showAddAccUserDialogLayer);
								}else  if(data.code=="103"){
									layer.msg('手机号已存在', {time: 3000, icon:2});
									$("#loginTelerror").text("手机号已存在");
								}else  if(data.code=="104"){
									$("#loginEmailerror").text("邮箱已存在");
									layer.msg('邮箱已存在', {time: 3000, icon:2});
								}else  if(data.code=="403"){
									layer.msg('手机号已存在', {time: 3000, icon:2});
									$("#loginTelerror").text("手机号已存在");
								}else  if(data.code=="404"){
									$("#loginEmailerror").text("邮箱已存在");
									layer.msg('邮箱已存在', {time: 3000, icon:2});
								}
							}
					});
				
			
		}
	});
  }

 
 /** 手机号码验证 */
jQuery.validator.addMethod("isMobileCheck", function(value, element) {  
	$("input[name='tel']").parent().next(".wrongTips").html('');
    var length = value.length;    
    return this.optional(element) || (length == 11 && /^1[3|4|5|7|8]\d{9}$/.test(value));    
  }, "请正确填写您的手机号码。");

/** 验证-手机号是否存在 *//*
$.validator.addMethod("loginTelCheck",function(value,element){
    $.ajax({
        type:"POST",
        url:basePath+"/regist/loginTelCheck.do",
        async:false, 
        data:{"loginTel":value},
        dataType:"json",
        success:function(data){
            if(data.code == 200) {
				res = false;
			} else {
				res = true;
			}
        }
    });
    return res;
},"手机号已存在");*/
/**========================ztree初始化开始=======================**/
ACCOUNTMANAGER.inittree=function(){
	var zNodes =eval($("#trees").val());
	//添加下拉框数据
	$.each(zNodes,function(i,item){
		/*if(i==0){
 			$("#accountdepartment").append('<option >请选择员工部门</option>');
 		}*/
		$("#accountdepartment").append('<option  value="'+item.id+'">'+item.name+'</option>');
	})
	
var setting = {
			view: {
				showLine: false,
				showIcon: false,
				selectedMulti: false,
				dblClickExpand: false,
				addDiyDom: ACCOUNTMANAGER.addDiyDom
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: ACCOUNTMANAGER.beforeClick,
				onClick: ACCOUNTMANAGER.zTreeOnClick
			
			}
		};
 	var  treeObj=$("#treeDemo");
	$.fn.zTree.init(treeObj, setting, zNodes);
	zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.addClass("showIcon");
	zTree_Menu.expandAll(true);//展开所有节点
	/*treeObj.hover(function () {
		if (!treeObj.hasClass("showIcon")) {
			treeObj.addClass("showIcon");
		}
	}, function() {
		treeObj.removeClass("showIcon");
	});*/
	  //查询该节点下所有的部门
		/*$.each(zNodes,function ( i,item){
		ACCOUNTMANAGER.id=ACCOUNTMANAGER.id+","+item.id;
			
		})*/
		//获取节点  
            var nodes = zTree_Menu.getNodes();  
            if (nodes.length>0)   
            {  
                var node = zTree_Menu.selectNode(nodes[0]);  
                ACCOUNTMANAGER.id=nodes[0].id;
            }  
            
            //初始化数据
    ACCOUNTMANAGER.queryParam.currpage=1;
    ACCOUNTMANAGER.queryParam.pageSize=PageWiget.getPageSize();
	ACCOUNTMANAGER.queryParam.ids=ACCOUNTMANAGER.id;
	ACCOUNTMANAGER.queryParam.status=ACCOUNTMANAGER.status;
	ACCOUNTMANAGER.roleId="";
	ACCOUNTMANAGER.url=	basePath+"/accountManager/getUserByDepartment.do";
	ACCOUNTMANAGER.ajaxSubmit(ACCOUNTMANAGER.queryParam,ACCOUNTMANAGER.url);
}
/**=================ztree初始化结束===================**/


 /**===============分页处理开始==============**/
ACCOUNTMANAGER.dopagechange=function(pageno,pagesize){
	//console.log(pageno+"   wwwww   "+pagesize);
	ACCOUNTMANAGER.url=	basePath+"/accountManager/getUserByDepartment.do";
	ACCOUNTMANAGER.queryParam.currpage=pageno;
	ACCOUNTMANAGER.queryParam.pageSize=pagesize;
	ACCOUNTMANAGER.queryParam.ids=ACCOUNTMANAGER.id;
	ACCOUNTMANAGER.queryParam.roleId=ACCOUNTMANAGER.roleId;
	ACCOUNTMANAGER.queryParam.status=ACCOUNTMANAGER.status;
	//alert(JSON.stringify(ACCOUNTMANAGER.queryParam));
	ACCOUNTMANAGER.ajaxSubmit(ACCOUNTMANAGER.queryParam,ACCOUNTMANAGER.url);
};
/**===============分页处理结束==============**/


/**================================表格开始======================================*/
	ACCOUNTMANAGER.initgrad=function(){
	ACCOUNTMANAGER.setTableHeight();
	//定义表格的功能
	var options = {
			editable: false,		//是否可以表格内编辑
			enableCellNavigation: true,  
			asyncEditorLoading: false,
			enableColumnReorder: false,
			forceFitColumns: true,//自动占满一行
			rowHeight:35, // 行高
			autoEdit: false,
			autoHeight: false//高度自动
		};

	//checkbox 列的定义. 
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass: "slick-cell-checkboxsel"
	});

	//定义表格的字段
	var columns = [];
//	columns.push(checkboxSelector.getColumnDefinition()); //checkbox 列

	columns.push({
		id: "indexNo",  // 唯一标识
		name: "序号",// 字段名字
		field: "indexNo", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width: 70		//宽度.

	});

	
	columns.push({
		id: "real_name", 
		name: "真实姓名",
		field: "real_name", 
		width: 150
		//formatter:STUDENT_INTO_CLASS.colorRender
	});
	columns.push({
		id: "roleName", 
		name: "角色名称",
		field: "roleName", 
		width: 170
	});
	columns.push({
		id: "depname", 
		name: "部门名称",
		field: "depname", 
		width: 170
	});
	columns.push({
		id: "login_email", 
		name: "登录邮箱",
		field: "login_email", 
		width:250
	});
	
	columns.push({
		id: "sex1", 
		name: "性别",
		field: "sex1", 
		width: 70
	});

	columns.push({
		id: "isvalidName", 
		name: "是否有效",
		field: "isvalidName", 
		width: 150
	});
	columns.push({
		id: "option", 
		name: "操作",
		field: "option", 
		cssClass:"slick-cell-action",
		width: 200,
		formatter:ACCOUNTMANAGER.actionRender
	});
	
	ACCOUNTMANAGER.columns = columns;
	//产生grid 控件
	ACCOUNTMANAGER.Grid = new Slick.Grid("#myGrid",[], columns, options);

	ACCOUNTMANAGER.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	ACCOUNTMANAGER.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	ACCOUNTMANAGER.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	ACCOUNTMANAGER.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = ACCOUNTMANAGER.Grid.getSelectedRows();
			//console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	//ACCOUNTMANAGER.ajaxSubmit({"ids":ACCOUNTMANAGER.id.substring(1)},basePath+"/ACCOUNTMANAGER/getUserByDepartment.do");


	}
/**================================表格结束======================================*/
	
//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
	//自定义显示列
ACCOUNTMANAGER.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',/*&#xe6dd;*/
	icon_key='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="重置密码" onclick="ACCOUNTMANAGER.resetpassword(\''+dataContext.real_name+'\','+dataContext.user_id+');"><i class="opti-icon Hui-iconfont">&#xe63f;</i></a>',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="ACCOUNTMANAGER.showUser('+dataContext.user_id+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_isValid,
	icon_end='</div>';
	var icon_look1="";
	if(ACCOUNTMANAGER.userstatus==1){
		icon_look1='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="编辑" onclick="ACCOUNTMANAGER.showUserUpdate('+dataContext.user_id+')"><i class="opti-icon Hui-iconfont">&#xe60c;</i></a>'
	}
	if(dataContext.isvalid==0){
		icon_isValid='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="账户禁用" onclick="ACCOUNTMANAGER.userIsvalid(1,\''+this+'\',\''+dataContext.real_name+'\','+dataContext.user_id+','+row+');"><i class="opti-icon Hui-iconfont">&#xe6dd;</i></a>';
	}else{
		icon_isValid='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="账户启用" onclick="ACCOUNTMANAGER.userIsvalid(0,\''+this+'\',\''+dataContext.real_name+'\','+dataContext.user_id+','+row+');"><i class="opti-icon Hui-iconfont">&#xe6e1;</i></a>';
	}
	s += icon_start+icon_key+icon_look+icon_look1+icon_isValid+icon_end;
	return s;
};	

/**================================查看事件触发开始======================================*/
ACCOUNTMANAGER.showUser=function(id){
	$.ajax({
		type: "POST",
		url:basePath+"/accountManager/selUserAndRoleByUserId.do",
		data:"id="+id,
		dataType:"json",
		success: function(rdata) {
				for(var key  in rdata ){
					if(key=="deparment"){
						if(rdata[key]!=null){
							//添加部门名称
							$("#accountdepartment1").text(rdata[key].depname);
						}else{
							//值空
							$("#accountdepartment1").text("");
						}
					}else if(key=="user"){
						if(rdata[key]!=null){
							
							if(rdata[key].loginEmail==null||rdata[key].loginEmail==""){
								//值空
								$("#loginEmail1").text("");
							}else{
								//添加登录邮箱
								$("#loginEmail1").text(rdata[key].loginEmail);
							}
							if(rdata[key].loginTel==null||rdata[key].loginTel==""){
								//值空
								$("#loginTel1").text("");
							}else{
								//添加登录邮箱
								$("#loginTel1").text(rdata[key].loginTel);
							}
							
							if(rdata[key].sex==null||rdata[key].sex==""){
								//值空
								$("#sex1").text("");
							}else{
								//添加性别
								if(rdata[key].sex=="0"){
									$("#sex1").text("男");
									
								}else{
									$("#sex1").text("女");
								}
							}
							
							if(rdata[key].realName==null||rdata[key].realName==""){
								//值空
								$("#realName1").text("");
							}else{
								//添加登录邮箱
								$("#realName1").text(rdata[key].realName);
							}
							
						}else{
							$("#userId").text("");
							$("#loginEmail1").text("");
							$("#loginTel1").text("");
							$("#sex1").text("");
							$("#realName1").text("");
						}
					}else if(key=="rolesName"){
						if(rdata[key]!=null){
							var  roleName="";
							$.each(rdata[key],function(i,item){
								roleName=roleName+","+item.name;
							})
							$("#accountrole1").text(roleName.substring(1));
						}else{
							$("#accountrole1").text("");
						}
						
					}else if(key=="fucntion"){
						if(rdata[key]!=null){
							var setting = {
								view: {
									showIcon: ACCOUNTMANAGER.showIconForTree
								},
								data: {
									simpleData: {
										enable: true
									}
								}
							};
							$.fn.zTree.init($("#accounttreeDemo1"), setting, rdata[key]);		
						}else{
							var zTreeObj = $.fn.zTree.getZTreeObj("accounttreeDemo1");
							zTreeObj.destroy();
						}
						
					}
					
					
				}
				showAddAccUserDialogLayer=layer.open({
					  type: 1, 
					  title: ['查看用户'],
					  skin: 'demo-class',
					  shade: 0.6,
					  anim:8,
					  area: ['610px', '585px'],
					  content:$("#showUserDialogLayer").show()  
					});
			}
	  })
}
/**================================查看事件触发结束======================================*/


/**================================编辑事件触发开始======================================*/
ACCOUNTMANAGER.showUserUpdate=function(id){
	$("#userId").val("");
	ACCOUNTMANAGER.validate1.resetForm();
	$("#loginTelerror").text("");
	$("#loginEmailerror").text("");
	$('#validateForm')[0].reset();
	$("input[type='checkbox']").each(function(){
		$(this).attr("checked",false);
	})
	$.ajax({
		type: "POST",
		url:basePath+"/accountManager/selUserAndRoleByUserId.do",
		data:"id="+id,
		dataType:"json",
		success: function(rdata) {
			//console.log("用户更新    "+rdata);
				for(var key  in rdata ){
					if(key=="deparment"){
						if(rdata[key]!=null){
							//添加部门名称
							$("#accountdepartment").val(rdata[key].depid);
						}
					}else if(key=="user"){
						if(rdata[key]!=null){
							if(rdata[key].userId==null||rdata[key].userId==""){
								//值空
								$("#userId").val("");
							}else{
								//添加登录邮箱
								//alert(rdata[key].userId);
								$("#userId").val(rdata[key].userId);
							}
							if(rdata[key].loginEmail==null||rdata[key].loginEmail==""){
							}else{
								//添加登录邮箱
								$("#loginEmail").val(rdata[key].loginEmail);
							}
							if(rdata[key].loginTel==null||rdata[key].loginTel==""){
							}else{
								//添加登录邮箱
								$("#loginTel").val(rdata[key].loginTel);
							}
							if(rdata[key].sex==null||rdata[key].sex==""){
							}else{
									$("input[type='radio']").each(function(){
										
										if($(this).val()==rdata[key].sex){
											$(this).prop("checked",true);
										}
									})
							}
							if(rdata[key].realName==null||rdata[key].realName==""){
							}else{
								//添加登录邮箱
								$("#realName").val(rdata[key].realName);
							}
							
						}
					}else if(key=="rolesName"){
						if(rdata[key]!=null){
							$.each(rdata[key],function(i,item){
								$("input[type='checkbox']").each(function(){
								if($(this).val()==item.id){
									$(this).prop("checked",true);
								}
							})
							})
							
						}
					}else if(key=="fucntion"){
						if(rdata[key]!=null){
							var setting = {
								view: {
									showIcon: ACCOUNTMANAGER.showIconForTree
								},
								data: {
									simpleData: {
										enable: true
									}
								}
							};
							$.fn.zTree.init($("#accounttreeDemo"), setting, rdata[key]);		
						}else{
							var zTreeObj = $.fn.zTree.getZTreeObj("accounttreeDemo");
							zTreeObj.destroy();
						}
						
					}
					
					
				}
				showAddAccUserDialogLayer=layer.open({
					  type: 1, 
					  title: ['修改用户'],
					  skin: 'demo-class',
					  shade: 0.6,
					  anim:8,
					  area: ['610px', '585px'],
					  content:$("#showAddAccUserDialogLayer").show()  
					});
		}
	  })
}
/**================================编辑事件触发结束======================================*/


/**================================表格事件触发开始======================================*/
ACCOUNTMANAGER.resetpassword=function(realName,id){
	//console.log(realName+"               "+id);
	//询问框
	layer.confirm('您确定要重置'+realName+'的密码吗？', {
	  btn: ['确定','取消'] //按钮
	}, function(){
	  $.ajax({
		type: "POST",
		url:basePath+"/accountManager/getUserBypassword.do",
		data:"id="+id,
		dataType:"json",
		success: function(rdata) {
			if(rdata.code=="201"){
				window.location.href=basePath+"/login.do";
			}else if(rdata.code=="202"){
			
			}else{	
				//console.log("    123123    "+JSON.stringify(rdata));
				if(rdata.code==200){
					
					layer.msg('用户'+realName+'的密码初始化成功！', {time: 2000, icon:1});
				}else{
					layer.msg('用户'+realName+'的密码初始化失败，请稍后再试。', {time: 3000, icon:2});
				}
			}
		}
	  })
	  
	  
	});

}
ACCOUNTMANAGER.userIsvalid=function(status,obj,realName,userId,row){
	//var  data1=JSON.parse(data);
	//console.log(status+"               "+realName+"   "+userId+"    "+row);
	//询问框
	var name="禁用";
	if(status==1){
	name="启用";
		
	}
	layer.confirm('您确定要'+name+''+realName+"的账户吗?", {
	  btn: ['确定','取消'] //按钮
	}, function(){
	  $.ajax({
		type: "POST",
		url:basePath+"/accountManager/userIsvalid.do",
		data:"id="+userId+"&isvalid="+status,
		dataType:"json",
		success: function(rdata) {
				if(rdata.code==200){
					var data = ACCOUNTMANAGER.Grid.getData();
					//console.log(JSON.stringify(data));
					if(status==1){
						data[row].isvalid =1;
						data[row].isvalidName="有效";
						layer.msg('用户'+realName+'的账户已启用可以登录！', {time: 3000, icon:1});
					}else{
						data[row].isvalid =0;
						data[row].isvalidName="无效";
						layer.msg('用户'+realName+'的账户已经禁用！', {time: 3000, icon:1});
					}
				  	ACCOUNTMANAGER.Grid.invalidateRows([row]);
				  	//alert(JSON.stringify(ACCOUNTMANAGER.Grid.getDataItem(row)));
				 	ACCOUNTMANAGER.Grid.render();
					
				}else{
					layer.msg('用户'+realName+'账户状态更新失败，请稍后再试！', {time: 3000, icon:2});
				}
		}
	  })
	  
	  
	});

}






/**================================表格事件触发结束======================================*/

/**================================ztree时间触发开始======================================*/
	ACCOUNTMANAGER.addDiyDom=function(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);

			if (treeNode.level > 0) {
				var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
				switchObj.before(spaceStr);
			}
		}
		
	 ACCOUNTMANAGER.beforeClick=function(treeId, treeNode) {
			/*if (treeNode.level == 0 ) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandNode(treeNode);
				return false;
			}*/
			return true;
		}

	ACCOUNTMANAGER.zTreeOnClick=function(event, treeId, treeNode) {
	   //显示该部门所有员工
		//console.log(JSON.stringify(treeNode));
		//查询该节点下所有的部门
		 ACCOUNTMANAGER.id="";
		//console.log("点击树形菜单显示的部门为       "+treeNode.id);
    	ACCOUNTMANAGER.id=treeNode.id;
		//调用数据显示部门
		ACCOUNTMANAGER.dopagechange(1,20);
		
		
	};

/*ACCOUNTMANAGER.f = function (treeNode) {  
    if (treeNode.children ==""||treeNode.children ==null) {
    
    	ACCOUNTMANAGER.id=ACCOUNTMANAGER.id+","+treeNode.id;
    } else { 
    	ACCOUNTMANAGER.id=ACCOUNTMANAGER.id+","+treeNode.id;
    	var ch=treeNode.children;
    	$.each(ch,function(i,item){
    	ACCOUNTMANAGER.f(item);
    	})
    } 
}; */

/**================================ztree时间触发结束======================================*/


/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
ACCOUNTMANAGER.ajaxSubmit1=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
				//console.log("    123123    "+JSON.stringify(rdata));
				  //填充表格数据
				  ACCOUNTMANAGER.Grid.setData(rdata.userByDepartment);
				/*$(".grid-canvas").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
				$(".slick-viewport").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
				$(".slick-viewport").css({"overflow-y": "auto"});*/
				ACCOUNTMANAGER.Grid.render();
				if(ACCOUNTMANAGER.queryParam.currpage==1){
				//初始化分页-总记录数/每页条数
				PageWiget.init(rdata.recordcount,PageWiget.getPageSize());
				}
				if(isNull(roleId)){
				}
		}
	});
	
};
/**========================================获取表格数据结束================================================*/
   
   
/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
ACCOUNTMANAGER.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			//console.log("    123123    "+JSON.stringify(rdata)+"       "+JSON.stringify(data));
			  //填充表格数据
			  ACCOUNTMANAGER.Grid.setData(rdata.userByDepartment);
			/*$(".grid-canvas").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
			$(".slick-viewport").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
			$(".slick-viewport").css({"overflow-y": "auto"});*/
			
			ACCOUNTMANAGER.Grid.render();
			if(ACCOUNTMANAGER.queryParam.currpage==1){
			//初始化分页-总记录数/每页条数
			PageWiget.init(rdata.recordcount,PageWiget.getPageSize());
			}
			if(ACCOUNTMANAGER.queryParam.status==0){
				//删除角色提示框
				layer.close(ACCOUNTMANAGER.layerc1);
				//部门列表
				if(rdata.userByDepartment==""||rdata.userByDepartment==null){
					var nodes = zTree_Menu.getSelectedNodes();
					ACCOUNTMANAGER.layerc=layer.msg(nodes[0].name+'部门下没有员工',{time:86400000});//24小时后关闭
				}else{
					if(isNull(ACCOUNTMANAGER.layerc)){
					}else{
						layer.close(ACCOUNTMANAGER.layerc);
					}
				
					
				}
				
			}else{
				//删除部门提示框
				layer.close(ACCOUNTMANAGER.layerc);
				//角色列表
				if(rdata.userByDepartment==""||rdata.userByDepartment==null){
					  ACCOUNTMANAGER.layerc1=layer.msg(data.roleName+'角色下没有员工',{time:86400000});//24小时后关闭
				}else{
					if(isNull(ACCOUNTMANAGER.layerc1)){
					}else{
						layer.close(ACCOUNTMANAGER.layerc1);
					}
				}
			}	
		}
	});
	
};
/**========================================获取表格数据结束================================================*/


//设置表格高度
ACCOUNTMANAGER.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
	$(".accountnav").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()-50});
	$(".zTreeDemoBackground").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()-$("#accountManagerzh").height()-10});
	

};	
	
