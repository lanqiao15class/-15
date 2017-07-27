<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <style type="text/css">
        .add-course{
            margin-top: -1cm;
        }
        .relative1{
            display: none;
        }
    </style>
    <meta charset="utf-8">
    <title>蓝桥软件学院</title>
    <link rel="stylesheet" type="text/css" href="../../../static/css/jquery.searchableSelect.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/main.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/iconfont.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/slick.grid.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/mytable.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/basic.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/style201706.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/classManager.css">

</head>
<body class="p-class">
<!--头部开始-->


<div class="main-right">
    <div class="inner-box">
        <div class="content">
            <div class="content-inner-part">
                <div class="inner-reletive">
                    <!--右侧标题部分开始-->
                    <div class="tit-h1-box">
                        <h1 class="tit-first">
                            <span>教学管理</span><i class="gt-icon">&gt;</i><span class="curr">创建排课</span>
                        </h1>
                    </div>
                    <!--右侧标题部分结束-->
                    <!--右侧内容部分背景白色开始-->
                    <div class="inner-white y-course">
                        <div class="courseWeek">
                            <label>排课Time：</label>
                            <select id="select">
                            </select>
                        </div>

                        <!-- 排课列表开始 -->
                        <div id=00001>
                            <div class="addCourseTab">

                                <table>
                                    <thead>
                                    <tr>
                                        <th>操作</th>
                                        <th>班级</th>
                                        <th>所属课程</th>
                                        <th>所属科目</th>
                                        <th id="th1"></th>
                                        <th id="th2"></th>
                                        <th id="th3"></th>
                                        <th id="th4"></th>
                                        <th id="th5"></th>
                                        <th id="th6"></th>
                                        <th id="th7"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <div class="t-c">
                                                <a href="#" title="复制"><i class="Hui-iconfont">&#xe600;</i></a>
                                                <a href="#" title="删除"><i class="Hui-iconfont">&#xe6a1;</i></a></div>
                                        </td>
                                        <td>
                                            <select>
                                                <option value="">点击选择班级</option>
                                                <option value="">班级1</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select >
                                                <option value="">点击选择课程</option>
                                                <option value="">课程1</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select>
                                                <option value="">点击选择科目</option>
                                                <option value="">科目</option>
                                            </select>
                                        </td>
                                        <td>
                                            <a class="course-add" id="abcde" onclick="xyz()">+添加</a>
                                        </td>
                                        <div id="00001" class="00001">
                                            <div class="relative1"  id="00000" >
                                                <!-- 添加课时弹框开始 -->
                                                <div class="add-course"  >
                                                    <div class="line-item-ver">
                                                        <div class="clearfix">
                                                            <label class="align-right-label"><i class="label-text">课时名称</i></label>
                                                            <input placeholder="请输入" type="text" name="">
                                                        </div>
                                                        <!-- <span class="error-tips">请输入课时名称</span>-->
                                                    </div>
                                                    <div class="line-item-ver">
                                                        <div class="clearfix">
                                                            <label class="align-right-label"><i class="label-text">授课类型</i></label>
                                                            <label class="label-radio mr20"><input type="radio" name="courseType">正常授课</label>
                                                            <label class="label-radio"><input type="radio" name="courseType">小班辅导</label>
                                                        </div>
                                                    </div>
                                                    <div class="line-item-ver">
                                                        <div class="clearfix">
                                                            <label class="align-right-label">主讲知识点</label>
                                                            <textarea class="status-appendix" placeholder="请描述知识点"></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="line-item-ver">
                                                        <div class="clearfix">
                                                            <label class="align-right-label">开始时间</label>
                                                            <!--<div id="u1630" class="ax_default droplist" > -->
                                                            <select id="u1630_input">
                                                                <option value="  请选择">&nbsp; 请选择</option>

                                                                <option value="08:00">08:00</option>
                                                                <option value="08:30">08:30</option>
                                                                <option selected value="09:00">09:00</option>
                                                                <option value="09:30">09:30</option>
                                                                <option value="10:00">10:00</option>
                                                                <option value="00:00">10:30</option>
                                                                <option value="00:30">10:00</option>
                                                                <option value="01:00">11:30</option>
                                                                <option value="01:30">12:00</option>
                                                                <option value="02:00">12:30</option>
                                                                <option value="02:30">13:00</option>
                                                                <option value="03:00">13:30</option>
                                                                <option value="03:30">14:00</option>
                                                                <option value="04:00">14:30</option>
                                                                <option value="04:30">15:00</option>
                                                                <option value="01:00">15:30</option>
                                                                <option value="01:30">16:00</option>
                                                                <option value="02:00">16:30</option>
                                                                <option value="02:30">17:00</option>
                                                                <option value="03:00">17:30</option>
                                                                <option value="03:30">18:00</option>
                                                                <option value="04:00">18:30</option>
                                                                <option value="04:30">19:00</option>
                                                                <option value="01:00">19:30</option>
                                                                <option value="01:30">20:00</option>
                                                                <option value="02:00">20:30</option>
                                                                <option value="02:30">21:00</option>
                                                                <option value="03:00">21:30</option>
                                                                <option value="03:30">22:00</option>
                                                                <option value="04:00">22:30</option>
                                                                <option value="23:30">23:30</option>
                                                                <option value="23:30">24:00</option>
                                                            </select><br />

                                                            &nbsp;&nbsp;&nbsp; <label class="align-right-label w68">结束时间</label>
                                                            <select id="u1631_input">
                                                                <option value="  请选择">&nbsp; 请选择</option>

                                                                <option value="08:00">08:00</option>
                                                                <option value="08:30">08:30</option>
                                                                <option selected value="09:00">09:00</option>
                                                                <option value="09:30">09:30</option>
                                                                <option value="10:00">10:00</option>
                                                                <option value="00:00">10:30</option>
                                                                <option value="00:30">10:00</option>
                                                                <option value="01:00">11:30</option>
                                                                <option value="01:30">12:00</option>
                                                                <option value="02:00">12:30</option>
                                                                <option value="02:30">13:00</option>
                                                                <option value="03:00">13:30</option>
                                                                <option value="03:30">14:00</option>
                                                                <option value="04:00">14:30</option>
                                                                <option value="04:30">15:00</option>
                                                                <option value="01:00">15:30</option>
                                                                <option value="01:30">16:00</option>
                                                                <option value="02:00">16:30</option>
                                                                <option value="02:30">17:00</option>
                                                                <option value="03:00">17:30</option>
                                                                <option value="03:30">18:00</option>
                                                                <option value="04:00">18:30</option>
                                                                <option value="04:30">19:00</option>
                                                                <option value="01:00">19:30</option>
                                                                <option value="01:30">20:00</option>
                                                                <option value="02:00">20:30</option>
                                                                <option value="02:30">21:00</option>
                                                                <option value="03:00">21:30</option>
                                                                <option value="03:30">22:00</option>
                                                                <option value="04:00">22:30</option>
                                                                <option value="23:30">23:30</option>
                                                                <option value="23:30">24:00</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="line-item-ver">
                                                        <div class="clearfix">
                                                            <label class="align-right-label"><i class="label-text">课时</i></label>
                                                            <input placeholder="" type="text" name="">
                                                        </div>
                                                        <span class="error-tips"></span>
                                                    </div>
                                                    <div class="center mb20">
                                                        <button class="btn btn-wihte mr10" onclick="a()" id="qx">取消</button>
                                                        <button class="btn btn-wihte mr10" onclick="b()" id="sc">删除</button>
                                                        <button class="btn btn-blue" onclick="c()" id="tj">确定</button>
                                                    </div>
                                                </div>
                                                <!-- 添加课时弹框结束 -->
                                            </div>
                                        </div>
                                        <td><a href="#" class="course-add" onclick="xyz()">+添加</a></td>
                                        <td><a href="#" class="course-add" onclick="xyz()">+添加</a></td>
                                        <td><a class="showCourse"></a><a class="showCourse"></a><a href="#" class="course-add" onclick="xyz()">+添加</a></td>
                                        <td><a href="#" class="course-add" onclick="xyz()">+添加</a></td>
                                        <td><a href="#" class="course-add" onclick="xyz()">+添加</a></td>
                                        <td><a href="#" class="course-add" onclick="xyz()">+添加</a></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <td colspan="4" class="t-r">总计：</td>
                                        <td></td><td></td>
                                        <td></td><td></td>
                                        <td></td><td></td>
                                        <td></td><td></td>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- 排课列表结束 -->
                        </div>
                        <!--右侧内容部分背景白色结束-->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="height:0; overflow:hidden;" id="dialogcontant"></div>


    <script src="${ctxStatic}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/js/placeholder.js"></script>
    <script src="${ctxStatic}/js/effect.js"></script>
    <script src="${ctxStatic}/js/jquery.searchableSelect.js"></script>
    <!--搜索框下拉自动填充js开始-->
    <script src="${ctxStatic}/js/query.autocompleter.js"></script>
    <script src="${ctxStatic}/js/main.js"></script>
    <!--搜索框下拉自动填充js结束-->
    <script src="${ctxStatic}/js/layer/layer.js"></script>

    <script src="${ctxStatic}/myjs/course/arrangementCourse.js></script>

    <script>
        $(function(){
            $(".course-add").click(function(){
                $(this).parent().find(".add-course").show();
            })


        });
        function xyz(){
            $("#00000").show();
        }
        function a () {
            $("#00000").hide();}
        function b () {
            $("#00000").hide();}
        function c () {
            $("#00000").hide();}
    </script>
    </div>
</body>
</html>