<%--
  Created by IntelliJ IDEA.
  User: wei62_000
  Date: 2017/7/19
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="y-block">
    <div class="y-form" style="width: 18cm">

        <form id='courseform'>
            <div class="item">
                <label class="title">课时名称</label>
                <input type="text" name="courseName" id="courseName" value="" /><span id="m">  </span>
            </div>

            <div class="item">
                <label class="title">授课类型</label>
                <label class="y-radio">
                    <input type="radio" name="courseType"  value="1" checked/> <span>正常授课</span>
                </label>
                <label class="y-radio">
                    <input type="radio" name="courseType"  value="0" /> <span>小班辅导</span><span id="m1"></span>
                </label>
            </div>

            <div class="item">
                <label class="title">主讲知识点</label>
                <textarea id="courseContent" name="desc" placeholder="请输入知识点描述" class="layui-textarea" style="width: 8cm;height: 2cm" ></textarea><span id="m2">  </span>
            </div>

            <%--选择框开始--%>
            <div style="float: none">
                <div class="item" style="float: left">
                    <label class="title">开始</label>
                    <span>
                        <select class="select" id="startTime" name="startTime" style="width: 3cm">
                            <option value="  请选择">&nbsp; 请选择</option>
                            <option value="08:00">08:00</option>
                            <option value="08:30">08:30</option>
                            <option selected value="09:00">09:00</option>
                            <option value="09:30">09:30</option>
                            <option value="10:00">10:00</option>
                            <option value="10:30">10:30</option>
                            <option value="11:00">11:00</option>
                            <option value="11:30">11:30</option>
                            <option value="12:00">12:00</option>
                            <option value="12:30">12:30</option>
                            <option value="13:00">13:00</option>
                            <option value="13:30">13:30</option>
                            <option value="14:00">14:00</option>
                            <option value="14:30">14:30</option>
                            <option value="15:00">15:00</option>
                            <option value="15:30">15:30</option>
                            <option value="16:00">16:00</option>
                            <option value="16:30">16:30</option>
                            <option value="17:00">17:00</option>
                            <option value="17:30">17:30</option>
                            <option value="18:00">18:00</option>
                            <option value="18:30">18:30</option>
                            <option value="19:00">19:00</option>
                            <option value="19:30">19:30</option>
                            <option value="20:00">20:00</option>
                            <option value="20:30">20:30</option>
                            <option value="21:00">21:00</option>
                            <option value="21:30">21:30</option>
                            <option value="22:00">22:00</option>
                            <option value="22:30">22:30</option>
                            <option value="23:30">23:30</option>
                            <option value="24:00">24:00</option>
                        </select>
                    </span>
                    <span id="m3"></span>
                </div>
                <div class="item" style="float: left">
                    <label class="title">&nbsp;结束</label>
                    <span>
                        <select class="select" id="endTime" name="endTime" style="width: 3cm">
                            <option value="  请选择">&nbsp; 请选择</option>
                            <option value="08:00">08:00</option>
                            <option value="08:30">08:30</option>
                            <option selected value="09:00">09:00</option>
                            <option value="09:30">09:30</option>
                            <option value="10:00">10:00</option>
                            <option value="10:30">10:30</option>
                            <option value="11:00">11:00</option>
                            <option value="11:30">11:30</option>
                            <option value="12:00">12:00</option>
                            <option value="12:30">12:30</option>
                            <option value="13:00">13:00</option>
                            <option value="13:30">13:30</option>
                            <option value="14:00">14:00</option>
                            <option value="14:30">14:30</option>
                            <option value="15:00">15:00</option>
                            <option value="15:30">15:30</option>
                            <option value="16:00">16:00</option>
                            <option value="16:30">16:30</option>
                            <option value="17:00">17:00</option>
                            <option value="17:30">17:30</option>
                            <option value="18:00">18:00</option>
                            <option value="18:30">18:30</option>
                            <option value="19:00">19:00</option>
                            <option value="19:30">19:30</option>
                            <option value="20:00">20:00</option>
                            <option value="20:30">20:30</option>
                            <option value="21:00">21:00</option>
                            <option value="21:30">21:30</option>
                            <option value="22:00">22:00</option>
                            <option value="22:30">22:30</option>
                            <option value="23:30">23:30</option>
                            <option value="24:00">24:00</option>
                        </select>
                    </span>
                    <span id="m4"></span>
                </div>
            </div>
            <%--选择框结束--%>
            <div class="item" style="float: left ">
                <label class="title" >课时</label>
                <input type="text" name="courseNumber" id="courseNumber"  /><span id="m5">  </span>
            </div>

        </form>
            <div class="btns" style="position: fixed ;bottom: 30%;left: 16%;width: 100%;z-index: 100; " >
                <button class="btn btn-gray" onclick="mycancel()">取消</button>
                <button class="btn btn-gray" onclick="mydelete()">删除</button>
                <button class="btn btn-blue" onclick="createCourseArrangement()">确定</button>
            </div>
    </div>
</div>

<script>
    function createCourseArrangement() {

        var params = {};
        params.courseName=$("#courseName").val();
        params.courseType=$('input:radio[name="courseType"]:checked').val();
        params.courseContent=$("#courseContent").val();
        params.startTime=$("#startTime option:selected").val();
        params.endTime=$("#endTime option:selected").val();
        params.courseNumber=$("#courseNumber").val();
        if(m==null||m==undefined||m==""){
            $("#m").html('请先填写课程名称');
            return;
        }else{
            $("#m").html('');
        }

        if(m2==null||m2==undefined||m2==""){
            $("#m2").html('主讲知识点');
            return;
        }else{
            $("#m2").html('');
        }



        if(m5 == ""){
            $("#m5").html('请填写课时');
            return;
        }else{
            $("#m5").html('');
        }

        var f = $("#courseform").serialize();


        $.ajax({
            type : "POST",
            url : ARRANGEMENTLIST.basePath + "/arrangement/createCourseArrange.do",
            data : params,
            dataType : "json",
            success : function(data) {
                if (data.resultCode == 0) {
                    $("#cid").val(data.resultData);
                    alert("添加成功")
                    parent.window.location.reload();
                } else {
                    alert(data.resultMsg);
                }
                console.log(data)
            }
        });

    }
</script>

