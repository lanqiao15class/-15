<%--
  Created by IntelliJ IDEA.
  User: wei62_000
  Date: 2017/7/12
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>创建排课</title>
    <script src="${ctxStatic}/js/jquery-1.9.1.min.js"></script>
    <style>
        #d1{width:100%;height: 100px}
        #select{height: 50px}
    </style>
</head>
<body>
<div id="d1">
    排课时间：<select id="select"></select>
</div>
<div >
    <table width="100%" cellspacing="0" cellpadding="0" border="1px">
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
        <tr>
            <td><a class="c-blue opt-incon-btn" href="javascript:void(0);">增加</a>
                <a class="c-blue opt-incon-btn" href="javascript:void(0);">删除</a>
            </td>
            <td>
                <select id="s1">

                </select>
            </td>
            <td>
                <select id="s2"></select>
            </td>
            <td>
                <select id="s3"></select>
            </td>
            <td>
               <a href="">增加</a>
            </td>
            <td>
                <a href="">增加</a>
            </td>
            <td>
                <a href="">增加</a>
            </td>
            <td>
                <a href="">增加</a>
            </td>
            <td>
                <a href="">增加</a>
            </td>
            <td>
                <a href="">增加</a>
            </td>
            <td>
                <a href="">增加</a>
            </td>
        </tr>
        <tr>
            <td colspan="4">总计：</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>



</body>
<script src="${ctxStatic}/myjs/course/arrangementCourse.js"></script>
</html>
