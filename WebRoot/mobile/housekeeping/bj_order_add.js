$(document).ready(function() {

});
/**
 * 提交订单
 * 订单和订单明细都在客户端生成, 订单id由后台生成
 * 如果前一个页面传递了雇员id那么在添加订单时 添加订单-雇员关联信息
 */
function addOrder() {
    var startTime = $("#startTime").val().trim();
    var workingTime = $("#workingTime").val().trim();
    var section = $("#section").val().trim();
    var address = $("#address").val().trim();
    var remark = $("#remark").val().trim();
    var orderPrice = $("#total").text().trim();
    if (startTime == "") {
        alert("服务开始时间不能为空");
        return false;
    }
    if (workingTime == "" || workingTime == "0") {
        alert("工作时长不能为空");
        return false;
    }
    if (section == "") {
        alert("城市不能为空");
        return false;
    }
    if (address == "") {
        alert("街道地址不能为空");
        return false;
    }

    /**
     * 订单
     */
    var order = {
        orderNumber : "",
        // orderTime : new Date(),
        workingTime : workingTime,
        businessCategoryId : businessCategory.id,
        businessCategoryName : businessCategory.name,
        startTime : startTime,
        address : section + address,
        userId : user.id,
        userName : user.userName,
        phoneNumber : user.phoneNumber,
        orderPrice : orderPrice,
        orderState : 1,
        paymentState : 1,
        remark : remark,
        payway : 3
    };

    /**
     * 订单明细
     */
    var orderDetail = {
        businessDetailId : businessDetail.id,
        businessDetailName : businessDetail.name,
        businessId : business.id,
        price : businessDetail.price,
        unit : businessDetail.unit,
        count : workingTime
    };

    $.postRequest({
        url : "/Housekeeping/Housekeeping.do",
        data : {
            header : {
                className : "",
                method : "addOrder"
            },
            parameter : {
                // 如果选择了雇员
                employeeId : employeeId == null ? "" : employeeId
            },
            data : {
                order : order,
                orderDetail : orderDetail
            }
        },
        success : function(data, para) {
            // 跳转到下单成功页面
            $.mobile.changePage("/Housekeeping/mobile/housekeeping/bj_order_submit_success.jsp?orderNumber=" + para.orderNumber, "pop");
        },
        error : function(message) {
            alert(message);
        }
    });
}

$(function() {
    var newjavascript = {
        plugdatetime : function($dateTxt, type) {
            // var curr = new Date().getFullYear();
            var opt = {};
            opt.time = {
                preset : type
            };
            opt.date = {
                preset : type
            };
            opt.datetime = {
                preset : type,
                minDate : new Date(2010, 1, 01, 00, 00),
                maxDate : new Date(2020, 12, 31, 24, 59),
                stepMinute : 1
            };
            $dateTxt.val('').scroller('destroy').scroller($.extend(opt[type], {
                // theme : "sense-ui",
                theme : "jqm",
                mode : "scroller",
                display : "modal",
                lang : "zh",
                monthText : "月",
                dayText : "日",
                yearText : "年",
                hourText : "时",
                minuteText : "分",
                secText : "秒",
                // ampmText : "上午/下午",
                timeWheels : "HHiiSS",
                setText : '确定',
                cancelText : '取消',
                dateOrder : 'yymmdd',
                dateFormat : 'yyyy-mm-dd',
                timeFormat : 'HH:ii:ss'
            }));
        }
    };
    newjavascript.plugdatetime($("#startTime"), "datetime");
});

$(function() {
    // 获得文本框对象
    var t = $("#workingTime");
    // 数量增加操作
    $("#add").click(function() {
        t.val(parseInt(t.val()) + 1);
        // if (parseInt(t.val()) != 1) {
        // $('#min').attr('disabled', false);
        // }
        setTotal();
    });
    // 数量减少操作
    $("#min").click(function() {
        if (parseInt(t.val()) <= 1) {
            return;
            // $('#min').attr('disabled', true);
        }
        t.val(parseInt(t.val()) - 1);
        setTotal();
    });
    // 计算操作
    function setTotal() {
        $("#total").text((parseInt(t.val()) * businessDetail.price).toFixed(2));
        // toFixed()是保留小数点的函数很实用哦
    }

    // 初始化
    setTotal();
});
