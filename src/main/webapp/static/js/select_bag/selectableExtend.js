/**
 * options:{inputid :标签id placeholder:"提示信息", isshow : 是否一直显示. afterSelectItem:
 * function(sval,stext) ; 选中后, 回调的方法.
 *  }
 */
function selectableExtend(options) {
	this.selectObject = null;
	var _this= this;
	//当searchableSelect 实例化后, 回调这个方法, 
	this.myAfterInit = function() {
		_this.selectObject = this;

		console.log("myAfterInit...." + this.selectObject);
	};
	
	//初始化.创建后必须首先调用这个.  
	this.init = function() {

		$("#" + this.optionext.inputid).searchableSelect(this.optionext);
	};
	//扩展对象, 增加自己的方法, 
	this.optionext = $.extend({
		afterInit : this.myAfterInit
	}, options);
	
	
	//选中某一项数据, 如果没有自动添加一条数据到下拉列表. 
	this.selectOption=function(dataid, displayText)
	{
		if(_this.selectObject)
			_this.selectObject.setValue(dataid, displayText);
		else
			console.log("this.selectObject:"+_this.selectObject)
	};
	
	
}
