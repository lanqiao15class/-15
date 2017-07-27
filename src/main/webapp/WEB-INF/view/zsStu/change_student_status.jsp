<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!-- 设置学员状态  开除 -->
<c:set var="st_qiuzhizhong" value="106" />
<c:set var="st_kaichu" value="107" />
<c:set var="st_quantui" value="108" />
<c:set var="st_tuixue" value="109" />
<c:set var="st_xiuxue" value="110" />
<c:set var="st_chongfanxiuxue" value="111" />
<c:set var="st_yanqijieye" value="112" />
<c:set var="st_yanqijiuye" value="113" />

<input type="hidden" value="${realname }" id="input_realname" />

<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_chongfanxiuxue) }">
<!-- 设置学员状态  休学重返 -->
<form style="display:none" status-code ="${st_chongfanxiuxue }">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
            <c:forEach var="item" items="${statuslist }">
          
             	<c:if test="${item.value eq st_chongfanxiuxue  }">
             		<option value="${item.value }" selected>${item.text }</option>
             	</c:if>
             
             	<c:if test="${item.value != st_chongfanxiuxue  }">
             		<option value="${item.value }" >${item.text }</option>
             	</c:if>
             	
             	</c:forEach>
            	
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">休学重返时间：</i></label>
            <input placeholder="请输入休学重返时间"  type="text" name="happentime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
  
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>

</c:if>


<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_tuixue) }">

 <!--   退学 -->
<form status-code ="${st_tuixue }" style="display:block">
<input type="hidden" value="${userid }" name="userid" />


<div class="set-student-status mt20" >
  <div class="line-item-ver">
 	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
              <c:forEach var="item" items="${statuslist }">
          
             	<c:if test="${item.value eq st_tuixue  }">
             		<option value="${item.value }" selected>${item.text }</option>
             	</c:if>
             
             	<c:if test="${item.value != st_tuixue  }">
             		<option value="${item.value }" >${item.text }</option>
             	</c:if>
             	</c:forEach>
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">退学时间：</i></label>
            <input placeholder="请输入退学时间"  type="text" name="happentime" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
     <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">发生期间：</i></label>
            ${statusname }期间
        </div>
      
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
            <input name="finishcount" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text">
        </div>
        <span class="error-tips"></span>
    </div>
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>
</c:if>


 
<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_kaichu) }">
<!--  开除 -->
<form  status-code ="${st_kaichu }">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
            <c:forEach var="item" items="${statuslist }">
             <c:if test="${item.value ==st_kaichu  }">
             		<option value="${item.value }" selected>${item.text }</option>
             </c:if>
            
              <c:if test="${item.value !=st_kaichu  }">
             		<option value="${item.value }">${item.text }</option>
             </c:if>
             
             
            </c:forEach>
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">开除时间：</i></label>
            <input placeholder="请输入开除时间"  type="text" name="happentime" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
     <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">发生期间：</i></label>
            ${statusname}期间
        </div>
      
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
            <input name="finishcount" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text">
        </div>
        <span class="error-tips"></span>
    </div>
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>

</c:if>

<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_quantui) }">
<!--   劝退 -->
<form status-code ="${st_quantui}" style="display:none">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
            	  <c:forEach var="item" items="${statuslist }">
             <c:if test="${item.value ==st_quantui  }">
             		<option value="${item.value }" selected>${item.text }</option>
             </c:if>
            
              <c:if test="${item.value !=st_quantui  }">
             		<option value="${item.value }">${item.text }</option>
             </c:if>
             
            </c:forEach>
            	
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">劝退时间：</i></label>
            <input placeholder="请输入劝退时间"  type="text" name="happentime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
     <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">发生期间：</i></label>
            ${statusname}期间
        </div>
      
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
            <input name="finishcount" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text">
        </div>
        <span class="error-tips"></span>
    </div>
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>
</c:if>

<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_xiuxue) }">

<!-- 设置学员状态  休学 -->
<form  style="display:none" status-code ="${st_xiuxue}">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
            		 	  <c:forEach var="item" items="${statuslist }">
             <c:if test="${item.value ==st_xiuxue  }">
             		<option value="${item.value }" selected>${item.text }</option>
             </c:if>
            
              <c:if test="${item.value !=st_xiuxue  }">
             		<option value="${item.value }">${item.text }</option>
             </c:if>
             
            </c:forEach>
            	
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">休学发生时间：</i></label>
            <input placeholder="请输入休学发生时间"  type="text" name="happentime" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
            <input name="finishcount" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text">
        </div>
        <span class="error-tips"></span>
    </div>
  
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟休学重返时间：</i></label>
            <input placeholder="请输入拟休学重返时间"  type="text" name="otherdate"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>

</c:if>


<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_yanqijieye) }">

<!-- 设置学员状态  延期结业 -->
<form style="display:none" status-code ="${st_yanqijieye }">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
            		 		 	  <c:forEach var="item" items="${statuslist }">
             <c:if test="${item.value ==st_yanqijieye  }">
             		<option value="${item.value }" selected>${item.text }</option>
             </c:if>
            
              <c:if test="${item.value !=st_yanqijieye  }">
             		<option value="${item.value }">${item.text }</option>
             </c:if>
             
            </c:forEach>
            	
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">延期结业发生时间：</i></label>
            <input placeholder="请输入延期结业发生时间"  type="text" name="happentime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
  
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟结业时间：</i></label>
            <input placeholder="请输入拟结业时间"  type="text" name="otherdate"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>
</c:if>

<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_yanqijiuye) }">

<!-- 设置学员状态  延期就业 -->
<form style="display:none" status-code ="${st_yanqijiuye }">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
         	<c:forEach var="item" items="${statuslist }">
             <c:if test="${item.value ==st_yanqijiuye  }">
             		<option value="${item.value }" selected>${item.text }</option>
             </c:if>
            
              <c:if test="${item.value !=st_yanqijiuye  }">
             		<option value="${item.value }">${item.text }</option>
             </c:if>
             
            </c:forEach>
            	
            	
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">延期就业发生时间：</i></label>
            <input placeholder="请输入延期就业发生时间"  type="text" name="happentime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
  
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟就业时间：</i></label>
            <input placeholder="请输入拟就业时间"  type="text" name="otherdate"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>

</c:if>


<c:if test="${lq:hasInArray(requestScope.statucodesarray,st_qiuzhizhong) }">

<!-- 设置学员状态  求职中. -->
<form style="display:none" status-code ="${st_qiuzhizhong }">
<input type="hidden" value="${userid }" name="userid" />

<div class="set-student-status mt20" >
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">设置学员状态为：</i></label>
            <select onchange="ChangeStudentStatus.changediv(this)" name="newstatus">
         	<c:forEach var="item" items="${statuslist }">
             <c:if test="${item.value ==st_qiuzhizhong  }">
             		<option value="${item.value }" selected>${item.text }</option>
             </c:if>
            
              <c:if test="${item.value !=st_qiuzhizhong  }">
             		<option value="${item.value }">${item.text }</option>
             </c:if>
             
            </c:forEach>
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">结业时间：</i></label>
        
          <c:if test="${empty endstudytime }">
            	<input placeholder=""  value="" type="text" name="endstudytime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
          </c:if>
        
          <c:if test="${not empty endstudytime }">
            	<input placeholder=""  value="${endstudytime }" style="background:#cccccc" type="text" name="endstudytime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly />
          </c:if>
        	
        </div>
        <span class="error-tips"></span>
    </div>
  
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">就业服务开始时间：</i></label>
            <input placeholder="请输入就业服务开始时间"  type="text" name="happentime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
        </div>
        <span class="error-tips"></span>
    </div>
    
      <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
            <input name="finishcount" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text">
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button type="button" class="btn btn-wihte" onclick="ChangeStudentStatus.closeDialog()">返回</button>
    </div>
</div>

</form>
</c:if>
<script>
var _statusarray = ${statusjson};
$(function(){
	
	var stitle = $("#input_realname").val();
	$("#input_realname").parents(".layui-layer").find(".layui-layer-title").append("--"+stitle);
	ChangeStudentStatus.init({statuslist:_statusarray});

});
</script>
<!--设置学员状态结束-->
