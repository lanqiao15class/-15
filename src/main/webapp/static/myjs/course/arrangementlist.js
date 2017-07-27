ARRANGEMENTLIST=function(){};

ARRANGEMENTLIST.basePath=basePath;

//表格对象. 
ARRANGEMENTLIST.Grid=null;

ARRANGEMENTLIST.url=null;

ARRANGEMENTLIST.dataParam={};

//点击查询时保存查询参数for分页
ARRANGEMENTLIST.queryParam={};

//存放表格选中行的stuUserIds
ARRANGEMENTLIST.selectStudentId=new HashMap();

//存放表格选中行的status
ARRANGEMENTLIST.selectStatus=new HashMap();


//存放表格数据
ARRANGEMENTLIST.tableData = null;

ARRANGEMENTLIST.init=function(){
    //设置表格部分的高度
    ARRANGEMENTLIST.setTableHeight();

    /**============表格开始============**/
        //定义表格的功能
    var options = {
            editable: false,		//是否可以表格内编辑
            enableCellNavigation: true,
            asyncEditorLoading: false,
            enableColumnReorder: false,
            forceFitColumns: true,//自动占满一行
            rowHeight:35, // 行高
            autoEdit: false
        };

    //checkbox 列的定义. 
//	var checkboxSelector = new Slick.CheckboxSelectColumn({
//		cssClass: "slick-cell-checkboxsel"
//	});

    //定义表格的字段
    var columns = [];
//	columns.push(checkboxSelector.getColumnDefinition()); //checkbox 列

    columns.push({
        id: "caId",  // 唯一标识
        name: "序号",// 字段名字
        field: "caId", // 显示数据中的某一个字段.对应到数据对象中的属性名
        width: 80,		//宽度.
    });

    columns.push({
        id: "className",
        name: "班级名称",
        field: "className",
        width: 300
    });

    columns.push({
        id: "caName",
        name: "课时名称",
        field: "caName",
        width: 100
    });

    columns.push({
        id: "teaName",
        name: "上课老师",
        field: "teaName",
        width: 100,
        formatter:ARRANGEMENTLIST.realnameRender
    });

    columns.push({
        id: "courseTime",
        name: "上课时间",
        field: "courseTime",
        width: 300
    });

    columns.push({
        id: "classTime",
        name: "上课课时",
        field: "classTime",
        width: 100
    });

    columns.push({
        id: "realCount",
        name: "实到人数",
        field: "realCount",
        width: 200,
        formatter:ARRANGEMENTLIST.realCount
    });

    columns.push({
        id: "caType",
        name: "授课类型",
        field: "caType",
        width: 100
    });
    columns.push({
        id: "caStatus",
        name: "状态",
        field: "caStatus",
        width: 100
    });

    columns.push({
        id: "option",
        name: "操作",
        field: "option",
        cssClass:"slick-cell-action",
        width: 100,
        formatter: ARRANGEMENTLIST.actionRender
    });
    //产生grid 控件
    ARRANGEMENTLIST.Grid = new Slick.Grid("#myGrid",[], columns, options);

    ARRANGEMENTLIST.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
    //设置为行选无效
    ARRANGEMENTLIST.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
    //注册插件. checkbox 显示方式做为一种插件
//	ARRANGEMENTLIST.Grid.registerPlugin(checkboxSelector);
    //检测表格选中事件
//	ARRANGEMENTLIST.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
//		
//		ARRANGEMENTLIST.updateRowHashMap();
//		
//		});



    //移动到行上, 改变背景颜色
    $(document).on("mouseover","div.slick-cell",function(e){
        $(e.target).parent("div.slick-row").addClass("hover_bgcolor");
    });

    $(document).on("mouseout","div.slick-cell",function(e){
        $(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
    });
    /**============表格结束============**/
    ARRANGEMENTLIST.getTableData();

};//init end

/**===========更多筛选开始======**/
ARRANGEMENTLIST.muchmore=function(){
    $(".much-more").click(function(){
        $(this).hide();
        $(".put-away").show();
        $(this).parents(".query-box-parent").find(".query-box").show();
        $(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
        ARRANGEMENTLIST.setTableHeight();
    });
};

ARRANGEMENTLIST.putaway=function(){
    $(".put-away").click(function () {
        $(this).hide();
        $(".much-more").show();
        $(this).parents(".query-box-parent").find(".query-box").hide();
        $(this).parents(".query-box-parent").find(".query-box:first").show();
        $(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
        ARRANGEMENTLIST.setTableHeight();
        //重置更多筛选出来的筛选项
        $("#zsSelectSearch").val("0");
        $(".ser-out-span").hide();
    })
};

//判断时间的输入是否合法
ARRANGEMENTLIST.endtimeChange = function () {
    $("#end-time").change(function () {
        var startTime = $("#start-time").val;
        var endTime = $(this).val;
        startTime = startTime.replace("-","/");
        endTime = endTime.replace("-","/");
        alert("startTime:"+startTime+"   endTime:"+endTime);
        startTime = Date.parse(startTime);
        alert(startTime);
    })
}


//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
ARRANGEMENTLIST.actionRender=function (row, cell, value, columnDef, dataContext) {
    var s= "",
        icon_start='<div class="gird-box">',
        icon_editStatus='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除" onclick="ARRANGEMENTLIST.deleteCourse('+dataContext.caId+')"><i class="opti-icon Hui-iconfont">&#xe6e2;</i></a>',
        icon_end='</div>';
    s=icon_start+icon_editStatus+icon_end;
    return s;
};


//获取选中的rows
ARRANGEMENTLIST.resolveSelectIndex=function(){
    //获取选中的行rows数组
    var datarows = ARRANGEMENTLIST.Grid.getData();
    var rows=new Array();
    var selectuserid = ARRANGEMENTLIST.selectStudentId.keys();

    for(var i=0;i<datarows.length;i++){
        for(var j=0;j<selectuserid.length;j++){
            if(selectuserid[j] == datarows[i].stuUserId){
                rows.push(i);
                break;
            }
        }
    }
    return rows;
};


//dataContext : 行数据对象。
ARRANGEMENTLIST.realCount=function (row, cell, value, columnDef, dataContext) {
    var  a=dataContext.currentSignCount/dataContext.classCount;
    var s=  "<div class='y-process'><div class='inner'><div style='width:"+a*100+"%;'>" +
        "</div></div><p>"+dataContext.currentSignCount+"/"+ dataContext.classCount+"</p> </div>";
    console.log(s);
    return s;
};

ARRANGEMENTLIST.arrangeCourse=function () {
    window.location.href=ARRANGEMENTLIST.basePath + "/arrangement/arrangeCourse.do";
}

ARRANGEMENTLIST.deleteCourse = function (caId) {
    layer.open({
        content: '确定要删除该课程？'
        ,btn: ['确认', '取消']
        ,yes: function(index, layero){
            $.getJSON(ARRANGEMENTLIST.basePath+"/arrangement/deleteCourse.do",{"caId":caId},function (result) {
                if(result == 1 ){
                    ARRANGEMENTLIST.init();
                    layer.msg('删除成功！');
                }else {
                    alert("删除出错！");
                }
            })
            layer.close(index);
        }
        ,btn2: function(index, layero){
            return;
            //按钮【按钮二】的回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,cancel: function(){
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
    });

}

//添加排课
ARRANGEMENTLIST.paike=function () {
    $.get(ARRANGEMENTLIST.basePath+'/arrangement/paike.do', null, function(form) {
        layer.open({
            type: 1,
            title: '创建排课',
            content: form,
            area: ['800px', '550px'],
            yes: function (index) {

            },
            full: function (elem) {
                var win = window.top === window.self ? window : parent.window;
                $(win).on(
                    'resize',
                    function () {
                        var $this = $(this);
                        elem.width($this.width()).height($this.height()).css({top: 0, left: 0});
                        elem.children('div.layui-layer-content').height($this.height() - 95);
                    }
                );
            },
        });
    });
}

//设置表格高度
ARRANGEMENTLIST.setTableHeight=function(){
    $(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};


//ajax提交-for获取表格数据(公用)
ARRANGEMENTLIST.ajaxSubmit=function(url){
    $.ajax({
        type: "POST",
        url:url,
        dataType:"json",
        success: function(data) {
            //清除当前选择.
            ARRANGEMENTLIST.tableData = data;
            ARRANGEMENTLIST.isuserselect = false;
            ARRANGEMENTLIST.Grid.setSelectedRows([]);
            //填充表格数据
            ARRANGEMENTLIST.Grid.setData(ARRANGEMENTLIST.tableData);
            ARRANGEMENTLIST.Grid.render();
            //设置选中多行
//			 var rows =  ARRANGEMENTLIST.resolveSelectIndex();
//			 ARRANGEMENTLIST.Grid.setSelectedRows(rows);
        }
    });
};

//无更多筛选时
ARRANGEMENTLIST.lookArrange=function () {
    var courseType=$("#courseType").val();
    var classStatus=$("#classStatus").val();
    $.ajax({
        type: "POST",
        url: ARRANGEMENTLIST.basePath + "/arrangement/findByInfo.do",
        data: "courseType=" + courseType + "&classStatus=" + classStatus,
        dataType:"json",
        success: function(data) {
            //清除当前选择.
            ARRANGEMENTLIST.isuserselect = false;
            ARRANGEMENTLIST.Grid.setSelectedRows([]);
            //填充表格数据
            ARRANGEMENTLIST.Grid.setData(data);
            ARRANGEMENTLIST.Grid.render();
        }
    });
}

//有更多筛选时
ARRANGEMENTLIST.lookArrangeByMore=function () {
    var courseType=$("#courseType").val();
    var classStatus=$("#classStatus").val();
    var beginTime=$("#beginTime_lq").val();
    var endTime=$("#endTime_lq").val();
    if(beginTime==""&&endTime==""){

    }else if(beginTime==""||endTime==""){
        layer.msg('输入时间不对！', {icon: 5});
        return;
    }
    $.ajax({
        type: "POST",
        url: ARRANGEMENTLIST.basePath + "/arrangement/findByInfo.do",
        data: "courseType=" + courseType + "&classStatus=" + classStatus +
        "&beginTime=" + beginTime + "&endTime=" + endTime,
        dataType:"json",
        success: function(data) {
            //清除当前选择.
            ARRANGEMENTLIST.isuserselect = false;
            ARRANGEMENTLIST.Grid.setSelectedRows([]);
            //填充表格数据
            ARRANGEMENTLIST.Grid.setData(data);
            ARRANGEMENTLIST.Grid.render();
        }
    });
}

ARRANGEMENTLIST.getTableData=function(){
    ARRANGEMENTLIST.url=ARRANGEMENTLIST.basePath+"/arrangement/showList.do";
    ARRANGEMENTLIST.ajaxSubmit(ARRANGEMENTLIST.url);
};
/**=============获取表格数据结束====================**/