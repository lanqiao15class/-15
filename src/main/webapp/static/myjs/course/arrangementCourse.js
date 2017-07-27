var getdate=function (time,year) {
    var d = new Date(year, 00, 1);
    while (d.getDay() != 1) {
        d.setDate(d.getDate() + 1);
    }
    var to = new Date(year + 1, 00, 1);
    var from = d;
    var h;
    for(var j=1;from<to;j++){
        h=(from.getMonth()+1);
        from.setDate(from.getDate() + 6);
        if (from < to) {
            from.setDate(from.getDate() + 1);
        } else {
            to.setDate(to.getDate() - 1);
        }
        if(j==time){
            return h;
        }
    }
}
var getD=function ( time,year) {
    var d = new Date(year, 00, 1);
    while (d.getDay() != 1) {
        d.setDate(d.getDate() + 1);
    }
    var to = new Date(year + 1, 00, 1);
    var from = d;
    var h;
    for(var j=1;from<to;j++){
        h= from.getDate()
        from.setDate(from.getDate() + 6);
        if (from < to) {
            from.setDate(from.getDate() + 1);
        } else {
            to.setDate(to.getDate() - 1);
        }
        if(j==time){
            return h;
        }
    }
}
$("#select").ready(function () {
    var yugi = function(year) {
        var d = new Date(year, 00, 1);
        while (d.getDay() != 1) {
            d.setDate(d.getDate() + 1);
        }
        var to = new Date(year + 1, 00, 1);
        var from = d;
        var h1;
        for (var i = 1; from < to;i++) {
            var h="<option value='"+i+"'>"+year + "-" + (from.getMonth() + 1) + "-" + from.getDate() + " - ";
            from.setDate(from.getDate() + 6);
            if (from < to) {
                h+= (from.getMonth() + 1) + "-" + from.getDate() +"(第"+i+"周)"+"</option>";
                from.setDate(from.getDate() + 1);
            } else {
                to.setDate(to.getDate() - 1);
                h+=(to.getMonth() + 1) + "-" + to.getDate() + "(第"+i+"周)"+"</option>";
            }
            h1+=h;
        }
        $("#select").html(h1);
    }
    var year=new Date();
    yugi(year.getFullYear());
     var time=$("#select").val();
     var month=getdate(time,year.getFullYear());
     var Day=getD(time,year.getFullYear());
    var html1=month+"/"+(Day)+"</br>"+"星期一";
    var html2=month+"/"+(Day+1)+"</br>"+"星期二";
    var html3=month+"/"+(Day+2)+"</br>"+"星期三";
    var html4=month+"/"+(Day+3)+"</br>"+"星期四";
    var html5=month+"/"+(Day+4)+"</br>"+"星期五";
    var html6=month+"/"+(Day+5)+"</br>"+"星期六";
    var html7=month+"/"+(Day+6)+"</br>"+"星期日";
    $("#th1").html(html1);
    $("#th2").html(html2);
    $("#th3").html(html3);
    $("#th4").html(html4);
    $("#th5").html(html5);
    $("#th6").html(html6);
    $("#th7").html(html7);

});
$("#select").change(function () {
    var time=$("#select").val();
    var d=new Date();
    var month=getdate(time,d.getFullYear());
    var Day=getD(time,d.getFullYear());
    var html1=month+"/"+(Day)+"</br>"+"星期一";
    var html2=month+"/"+(Day+1)+"</br>"+"星期二";
    var html3=month+"/"+(Day+2)+"</br>"+"星期三";
    var html4=month+"/"+(Day+3)+"</br>"+"星期四";
    var html5=month+"/"+(Day+4)+"</br>"+"星期五";
    var html6=month+"/"+(Day+5)+"</br>"+"星期六";
    var html7=month+"/"+(Day+6)+"</br>"+"星期日";
    $("#th1").html(html1);
    $("#th2").html(html2);
    $("#th3").html(html3);
    $("#th4").html(html4);
    $("#th5").html(html5);
    $("#th6").html(html6);
    $("#th7").html(html7);
});
