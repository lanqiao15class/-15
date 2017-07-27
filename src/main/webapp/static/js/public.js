var gl_selectmenu=null;
var gl_changeIndex=0;
var gl_hashpage=new HashMap();
var gl_defaulthtml="";
var gl_layerindex;
var gl_forwardtag=false;
var gl_initMenu =false;

//菜单初始化.
	$(document).ready(function(){
	    
		
		//alert("init ...");
		if(gl_initMenu) return ; // 保证菜单初始化只执行一次.
		//点击一级菜单
		
		setTableHeight();
		$(".nav-line-row").click(function(){
			if($(".nav-sub").is(":animated")){
				return false;
			}
			if(!$(this).parents("dl").hasClass("current")){
				$(this).parents("dl").addClass("current");
				$(this).parents("dl").find(".nav-sub").slideDown(300);
			}else {
				$(this).parents("dl").removeClass("current");
				$(this).parents("dl").find(".nav-sub").slideUp(300);
			}
		});
		//点二级菜单
		$(".nav-sub a").click(function(){
			$(".nav-sub a").removeClass("current");
			$(this).addClass("current");
			var menuid = $(this).attr("id");
			layer.closeAll();
			__loadPage($(this).attr("data-href"), menuid);
		});
		//菜单显示隐藏
		$(document).on("mouseenter",".nav-left",function(){
			if($(this).is(":animated")) $(this).stop();
			$(".show").show();
			$(".hide").hide();
			$(this).animate({"width":"200px"},200);
		});
		$(document).on("mouseleave",".nav-left",function(){
			if($(this).is(":animated")) $(this).stop();
			$(".show").hide();
			$(".hide").css({"display":"block"});
			$(this).animate({"width":"50px"},200);
		});
		//用户部分名显示隐藏
		$(".userInfo").hover(function(){
			$(".optionuserinfo").show();
		});
		$(".userInfo").mouseleave(function(){
			$(".optionuserinfo").hide();
		});
		
		
		//兼容IE   支持后退
		var browser=navigator.appName;
		//alert(browser);
		if(browser=="Microsoft Internet Explorer")
		{
			$(window).bind("hashchange", _onHashChage);
		} else 
		{
			$(window).bind("popstate", _onHashChage);
		}
		
		gl_defaulthtml= $("#contentDiv").html();
		
		// 打开第一个页面. 
		
	
		
		var firsturl = $(".nav-sub a").eq(0).attr("data-href");
		var firstid = $(".nav-sub a").eq(0).attr("id");
		
		if(firstid)
		{
				console.log("window.location.hash:" + window.location.hash);
				if(window.location.hash  )
				{
					var menuid = parseMenuIdByHashstring(window.location.hash );
					if(menuid !=null)
					{
						firstid = menuid;
						firsturl=$("#"+menuid).attr("data-href");
					}
					
				}
				
				console.log("firsturl:" + firsturl);
				selectMenuId(firstid);
				__loadPage(firsturl, firstid);
		}
		
		gl_initMenu = true;
		
	});

/**
 * 将下来列表中的老师名称添加部门名称. 
 * @returns {Array}
 */
function _wrapteacherList()
{
	//将部门放入hashmap
	var hs = new HashMap();
	
	//console.log("gl_departmentlist size:" + gl_departmentlist.length)
	for(i=0;i< gl_departmentlist.length;i++)
	{
		hs.put(gl_departmentlist[i].did,gl_departmentlist[i].depname);
		//console.log("gl_departmentlist[i].did:" + gl_departmentlist[i].did + "gl_departmentlist[i].depname:" + gl_departmentlist[i].depname);
	}

	var arr = new Array();
	for(i=0;i<gl_teacherlist.length;i++)
	{
		var otemp = $.extend({}, gl_teacherlist[i]);
		var sname = otemp.label;
		var op = hs.get(otemp.did);
		if(op)
		{
			sname +="-" + op
		}
		
		otemp.label = sname;
		
		arr.push(otemp);
	}
	
	return arr;
}



function printObject(obj) 
{ 
	console.log("=====================");
	    var description = "";  
	    for (var i in obj) 
	    {
	        description += i + " = " + obj[i] + "\n";  
	    }  
	console.log(description);	
	console.log("=====================");
	
} 

//左边菜单处理
function setTableHeight(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
}

(function($){
	  $.showTipError = function(element, msg) {
		  layer.tips(msg, element);
	  }
	
	  var $sS = $.showTipError;
	  $.fn.showTipError = function(msg){
		    this.each(function(){
		      var sS = new $sS($(this), msg);
		    });

		    return this;
		  };
})(jQuery);


/*$.fn.extend({
	showTipError:function(msg){
		 var target = arguments[0];
		// alert(target);
		layer.tips(msg, this);
     }       
});     */


function showTipError(msg,idstring)
{
	 layer.tips(msg, '#search-btn');	
}


function selectMenuId(id)
{
	if(gl_selectmenu == id) return ;
	var _this=$("#"+id);
	//console.log(!_this.parents("dl").hasClass("current"));
	if(!_this.parents("dl").hasClass("current")){
		$(".nav-menu dl").removeClass("current");
		_this.parents("dl").addClass("current");
		$(".nav-sub").slideUp(300);
		$(".nav-sub a").removeClass("current");
		_this.addClass("current");
		$("dl.current").find(".nav-sub").slideDown(300);
	}else {
		$(".nav-sub a").removeClass("current");
		_this.addClass("current");
		gl_selectmenu = id;
	}
}
String.prototype.startWith=function(str){     
	  var reg=new RegExp("^"+str);     
	  return reg.test(this);        
	}

//访ajax请求页面
function __loadPage(strurl, menuid)
{
	console.log("openwin1:" + strurl);
	// 支持在新窗口打开URL 
	if(strurl.startWith("http"))
	{
		console.log("openwin:" + strurl);
		window.open (strurl);
		
		return ;
	}
	
	gl_selectmenu = menuid;
  if(gl_layerindex) 
	  layer.close(gl_layerindex);
	
  $.ajax({
	  dataType:"html",
	  url:ctxBase+strurl,
	  data:{menuid:menuid},
	  canback:true,
	  type:"post",
	  success: function(data){
	  $("#contentDiv").html(data);
	  }
  });
}
//===========================================================


//解析出一个菜单id
function parseMenuIdByHashstring(str)
{
   var arr = str.split("_");
   return arr[1];
}

function _onHashChage()
{
//	alert("popstate...");
	var setting = gl_hashpage.get(window.location.hash);
		if(setting !=null)	
		{
			
		//通过ajax 得到要显示数据
		
			var set2 =setting ;
			if(setting.repeatsend ==null )
				set2=$.extend(setting,{repeatsend: true});
			
			$.ajax(set2);
		
			//更新菜单选中
			var menuid = parseMenuIdByHashstring(window.location.hash);
			console.log("back menuid:" + menuid);
			//alert("menuid:"+menuid);
			//var menuobj = $("#"+menuid)[0];
			selectMenuId(menuid);
			return ;
		}else
			{
			   // 显示默认页面内容. 
			  if(gl_layerindex) 
				  layer.close(gl_layerindex);
			  if(gl_forwardtag==false)
				 $("#contentDiv").html(gl_defaulthtml);
			
			}
	
	
}



//全局的ajax访问，处理ajax清求时sesion超时

jQuery.ajaxSetup({
    contentType : "application/x-www-form-urlencoded;charset=utf-8",
    error:function(err) {
    	//错误统一处理
    	gl_layerindex = layer.msg("错误码:" + err.status +"<br/> 错误原因:" + err.statusText,{icon: 2});
	},
	beforeSend: function(jqXHR, settings) {
		//loadingopen();
		//是否显示加载层, 可以通过notloading=true指定不显示. 默认显示
		if(settings.notloading !=null && settings.notloading ) 
		{
			;
			
		}else
			{
				loadingopen();
			
			}
		
		//后退,或前进的时候重复发送数据包到webserver
		if(settings.repeatsend == true) return ;
			//需要支持back 
		if(settings.canback !=null && settings.canback ) 
			{
				//产生hash, 规则:序号_菜单id
					gl_changeIndex++;
					
					gl_forwardtag = true;
					window.location.hash="#click"+gl_changeIndex+"_"+gl_selectmenu;
					gl_hashpage.put(window.location.hash,settings);
				//	alert("beforeSend:" + window.location.hash);
					gl_forwardtag=false;
			}
	
		
	},
    complete : function(XMLHttpRequest, textStatus)  
    {
    	
    	if(XMLHttpRequest.getResponseHeader)
    		{
		        var strcode = XMLHttpRequest.getResponseHeader("code"); // 通过XMLHttpRequest取得响应头，sessionstatus，
		        if (strcode == "201") 
		        {
		        	layer.alert("没登录,请重新登录");
		        	
		            // 如果超时就处理 ，指定要跳转的页面
		            window.location.href = basePath + "/sso/login.do";
		        }
		        else if(strcode =="202")
		        {
		        	layer.alert("您的账户无权访问这个功能.");
		        }
    		}
        //关闭加载层. 
       // console.log("loadingclose");
        loadingclose();
    }
});

function showDialog(ele){	
	$(ele).fadeIn(200);
}

function showBox(id){
	closeDialog();
	var _this= $("#"+ id);
	_this.fadeIn(200);
	var bodyheight = document.body.clientHeight,
    windowheight = document.documentElement.clientHeight,
    openbox = _this.children(":first");
	var boxTop = (windowheight - openbox.height())/2,
	boxLeft = (document.documentElement.clientWidth - openbox.width())/2;
	boxTop = boxTop>0 ? boxTop : 0;
	boxLeft = boxLeft>0 ? boxLeft : 0;
    openbox.css({'top':boxTop + 'px','left':boxLeft + 'px'});
    var	mainHeight=windowheight-_this.find(".title").height(),mHeightR=$(".mainInBox").height();
	if($(".dialog .main").length > 0 && mHeightR>mainHeight){
		$(".dialog .main").css({"height":mainHeight,"overflow-y":"scroll"});
	}
}

//alert弹框调用
function alertbox(title,tips){
	if($(".tipsBoxMask").length>0){
		$(".tipsBoxMask").remove();
	}
	var alertboxhtml='<div class="tipsBoxMask" id="alertBox"><div class="alertBox"><h4 class="titBox clearfix"><span class="fl">'+title+'</span><a class="fr close" href="javascript:void(0);" onClick="closeDialog()"></a></h1><div class="conMain">';
		alertboxhtml=alertboxhtml+tips+'</div><div class="btn_part"><a class="btn btn-blue" href="javascript:void(0)" onClick="closeDialog()">确定</a><a class="btn btn-gray" href="javascript:void(0)" onClick="closeDialog()">取消</a></div></div></div>';
		$("body").append(alertboxhtml);
		showBox('alertBox');
}

function closeDialog(){	
	$(".dialog,.wbox,.tipsBoxMask").hide();
}

$(".dialogclose").click(function(){
	if(event.target==this){//阻止冒泡
		closeDialog();
	}
});
//表格气泡
$(".tableTdBox").hover(function(){
	$(this).children(".showAllTd").css({"display":"block"});
},function(){
	$(this).children(".showAllTd").css({"display":"none"});
});

/*加载中效果*/
function loadingopen(){
	if($(".loadinglayer").length>0){
		loadingclose();
	}
	var loadingBoxhtml='<div class="loadinglayer" style="z-index:2147483647;" id="loadingBox"><div class="loadingpic"></div></div>';
		$("body").append(loadingBoxhtml);
		showBox('loadingBox');
}
function loadingclose(){
	$(".loadinglayer").hide().remove();
}


/*
 * html 特殊字符转义。
 * 
 * @param strdata
 */

function convertToHtml(strdata) {
	if (strdata == null)
		return "";
	var sb = "";
	for (i = 0; i < strdata.length; i++) {
		var c = strdata.charAt(i);
		switch (c) {
		case '<':
			sb += ("&lt;");
			continue;
		case '>':
			sb += ("&gt;");
			continue;
		case '&':
			sb += ("&amp;");
			continue;
		case '\'':
			sb += ("&apos;");
			continue;
		case '"':
			sb += ("&quot;");
			continue;
		case '\r':
			sb += ("&#xd;");
			continue;
		case '\n':
			sb += ("&#xa;");
			continue;
		default:
			sb += (c);
			continue;
		}
	}

	return sb;
}

/*
 * 空值判断
 */
function isNotNull(value) {
	if (null != value  && "" != value && undefined != value
			&& "undefined" != value) {
		return true;
	} else {
		return false;
	}
}


/*
 * 空值判断
 */
function isNull(value) {
	if (null == value  || "" == value ||  typeof value == 'undefined') {
		return true;
	} else {
		return false;
	}
}

/*
 * 判断值是否为空并返回值
 */
function isNotNullReturnValue(value) {
	if (null != value && "null" != value && "" != value && undefined != value
			&& "undefined" != value) {
		return value;
	} else {
		return "";
	}
}

/*
 * url参数解析
 */
function urlParamParse(key) {
	var paramValue = null;
	if (null != key && undefined != key && "" != key && "null" != key && "undefined" != key
			&& key.length > 0) {
		var param = document.location.search;
		if (null != param && undefined != param && "" != param && "null" != param
				&& "undefined" != param && param.length > 0) {
			param = unescape(param);
			param = param.substring(param.indexOf("?", 0) + 1, param.length);
			for (var i = 0; i < param.split("&").length; i++) {
				if (null != String(param.split("&")[i]) && undefined != String(param.split("&")[i])
						&& "" != String(param.split("&")[i])
						&& "null" != String(param.split("&")[i])
						&& "undefined" != String(param.split("&")[i])
						&& String(param.split("&")[i]).length > 0
						&& String(param.split("&")[i]).split("=").length > 0) {
					var paramkey = String(param.split("&")[i]).split("=")[0];
					//
					if (null != paramkey && undefined != paramkey && "" != paramkey
							&& "null" != paramkey && "undefined" != paramkey && paramkey.length > 0
							&& paramkey.toUpperCase() == key.toUpperCase()) {
						paramValue = String(param.split("&")[i]).split("=")[1];
						break;
					}
				}
			}
		}
	}

	return paramValue;
}


$.jheartbeat = {
	options : {
		delay : 10000
	},
	beatfunction : function() {
	},
	timeoutobj : {
		id : -1
	},

	set : function(options, onbeatfunction) {
		if (this.timeoutobj.id > -1) {
			clearTimeout(this.timeoutobj);
		}
		if (options) {
			$.extend(this.options, options);
		}
		if (onbeatfunction) {
			this.beatfunction = onbeatfunction;
		}

		this.timeoutobj.id = setTimeout("$.jheartbeat.beat();", this.options.delay);
	},

	beat : function() {
		this.timeoutobj.id = setTimeout("$.jheartbeat.beat();", this.options.delay);
		this.beatfunction();
	}
};

/**
 * 每隔一定的时间 执行func
 */
function lanqiaotimer(func, interval) {
	$.jheartbeat.set({
		delay : interval
	}, func);
}

function html_encode(str) {
	var s = "";
	if (str.length == 0)
		return "";
	s = str.replace(/&/g, "&amp;");
	s = s.replace(/</g, "&lt;");
	s = s.replace(/>/g, "&gt;");
	// s = s.replace(/ /g, "&nbsp;");
	s = s.replace(/\'/g, "&#39;");
	s = s.replace(/\"/g, "&quot;");
	// s = s.replace(/\n/g, "<br>");
	return s;
}

function html_decode(str) {
	var s = "";
	if (str.length == 0)
		return "";
	s = str.replace(/&gt;/g, "&");
	s = s.replace(/&lt;/g, "<");
	s = s.replace(/&gt;/g, ">");
	s = s.replace(/&nbsp;/g, " ");
	s = s.replace(/&#39;/g, "\'");
	s = s.replace(/&quot;/g, "\"");
	s = s.replace(/<br>/g, "\n");
	return s;
}

/**
 * 
 * 类似 java 的hashmap ，
 * 
 */
function HashMap() {

	/** Map 大小 * */
	var size = 0;

	/** 对象 * */
	var entry = new Object();

	/** 存 * */
	this.put = function(key, value) {
		if (!this.containsKey(key)) {
			size++;
		}
		entry[key] = value;
	///	console.log("hashmap put "+ key );
	}
	
	//清空所有
 this.removeAll=function()
 {
	 size = 0;
	 entry=new Object();
	 
 }
 
	/** 取 * */
	this.get = function(key) {
		if (this.containsKey(key)) {
			return entry[key];
		} else {
			return null;
		}
	}

	/** 删除 * */
	this.remove = function(key) {
		if (this.containsKey(key)) {
			if (delete entry[key]) {
				size--;
			}
		}
	}

	/** 是否包含 Key * */
	this.containsKey = function(key) {
		return (key in entry);
	}

	/** 是否包含 Value * */
	this.containsValue = function(value) {
		for ( var prop in entry) {
			if (entry[prop] == value) {
				return true;
			}
		}
		return false;
	}

	/** 所有 Value * */
	this.values = function() {
		var values = new Array();
		for ( var prop in entry) {
			values.push(entry[prop]);
		}
		return values;
	}

	/** 所有 Key * */
	this.keys = function() {
		var keys = new Array();
		for ( var prop in entry) {
		//	console.log("prop:" + prop);
			keys.push(prop);
		}
		return keys;
	}

	/** Map Size * */
	this.size = function() {
		return size;
	}
};

/**
 * 取出字符串中的空格
 */
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}


/**
 * 将字符串转换为 html
 * 将特殊的字符, 转义
 */
String.prototype.toHtml = function() {
	var REGX_HTML_ENCODE = /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g; 
	      return  this.replace(REGX_HTML_ENCODE,
	                    function($0){
	                        var c = $0.charCodeAt(0), r = ["&#"];
	                        c = (c == 0x20) ? 0xA0 : c;
	                        r.push(c); r.push(";");
	                        return r.join("");
	          });
};

function toZero(s)
{
	s = s+"";
	if(s !=null && s.length ==1)
		return "0" + s;
	return s;
}

function getNowDate()
{
	
	var d=new Date();
	return d.getFullYear()+"-" + toZero((d.getMonth()+1))+"-"+ toZero(d.getDate());
	
}


function getNowMonth()
{
	var d=new Date();
	return d.getFullYear()+"-" + toZero((d.getMonth()+1));
}


//判断是否有某功能权限
function gl_isHashRight(funcIds,funcId){
	var back=false;
	var functionIds=funcIds.split(",");
	for(var i=0;i<functionIds.length;i++){
		if(functionIds[i]==funcId){
			back=true;
			break;
		}
	}
	return back;
}

//放大显示。
function ZoomBig(imgurl)
{
	
	var nW="600px";
	var nH= "560px";
	//imgurl ="http://service.lanqiao.org/image/idcard/201612/98be7dde-df55-4d27-9198-73acd384482b.png";
	var gl_dind = layer.open({
		  type: 1, 
		  title:false,
		  skin: 'demo-class',
		  shade: false,
		  area: nW,
		  anim:8,
		  content: "<p align='center'><img src='"+imgurl+"' width="+nW+"></p>"
	});
	
	layer.style(gl_dind, {
		width: nW,
		top: '10px',
		left:'200px',
		height: nH
		}); 
}

//同步显示用户的信息, 头像和名称.
function gl_syncUserInfo()
{
	 console.log("调用了:gl_syncUserInfo");
	 
	 $.ajax({
		  dataType:"json",
		  url:basePath + "/user/syncUserInfo.do",
		  data:{},
		  canback:false,
		  notloading:true,
		  type:"post",
		  success: function(jsondata){
			  if(jsondata.code ==200)
				  {
				    console.log("jsondata.realname:"+ jsondata.realname);
				    console.log("jsondata.photo:"+ jsondata.photo);
				    
				   $("#gl_realName").text(jsondata.realname);
				   
				   $("#gl_realName_menu").text(jsondata.realname);
				   
				   $("#gl_photoimage").attr("src",jsondata.photo);
				   
				  }
		  }
	  });	
}
