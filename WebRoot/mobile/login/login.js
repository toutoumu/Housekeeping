$(document).ready(function() {

});
// 下面两行是允许跨域设置, 此项目不存在跨域
$.support.cors = true;
$.mobile.allowCrossDomainPages = true;
// 匹配手机号码的正则表达式
var isMobile = /^(?:13\d|15\d|18\d|17\d)\d{5}(\d{3}|\*{3})$/;
// 保存返回回来的验证码
var captcha = null;

/**
 * 获取验证码
 */
function getCaptcha() {
    var phoneNumber = $('#phone').val().trim();
    if (phoneNumber == "") {
        alert("请输入手机号码");
        return false;
    }

    if (!isMobile.test(phoneNumber)) {
        alert("请正确填写电话号码，例如:13415764179");
        return false;
    }

    // 以post的方式调用服务
    $.postRequest({
        // 请求的url
        url : "/Housekeeping/LoginServlet.do",
        // post请求的数据
        data : {
            // 自定义的请求头, 非Http请求头
            header : {
                // 请求的类名称, 保留做扩展用
                className : "",
                // 请求的方法
                method : "getCaptcha"
            },
            // 请求的参数(如果有非实体类型的参数可以用参数传递),对于删除单条数据等可以用参数传递主键值
            parameter : {
                // 这里添加了一个phoneNumber参数,值为变量phoneNumber的值
                phoneNumber : phoneNumber
            },
            // 请求数据实体如User(用户)对象,如果没有则此属性可以不写
            data : {}
        },
        // 服务调用成功的方法
        success : function(data) {
            if ( typeof data.captcha != "undefined") {
                captcha = data.captcha;
                disableGetCaptcha();
            }
        },
        // 下面三行是服务调用出错(jsonresponse.setSuccess(false))时候调用的方法,此方法不是必须的,下面给出的是默认代码即不写就默认是下面这样的代码
        error : function(message) {
            alert(message);
        }
    });
    // 对于a标签的按钮必须return false
    return false;
}

/**
 * 倒计时60秒
 */
function disableGetCaptcha() {
    var i = 60;
    var btn = $("#getCaptcha");
    btn[0].onclick = "";

    var interval = setInterval(function() {
        btn.html('<span class="ui-btn-inner"><span class="ui-btn-text">' + (i--) + '秒后再试</span></span>');
    }, 1000);

    setTimeout(function() {
        btn.click(getCaptcha);
        btn.html('<span class="ui-btn-inner"><span class="ui-btn-text">获取验证码</span></span>');
        clearInterval(interval);
    }, 60 * 1000);
}

function login() {
    if (captcha == null) {
        alert("请先获取验证码");
        return false;
    }
    var phoneNumber = $('#phone').val().trim();
    if (phoneNumber == "") {
        alert("请输入手机号码");
        return false;
    }

    if (!isMobile.test(phoneNumber)) {
        alert("请正确填写电话号码，例如:13415764179");
        return false;
    }
    var code = $('#code').val().trim();
    if (code == "") {
        alert("请输入验证码");
        return false;
    }
    ;

    captcha.code = code;
    captcha.phoneNumber = phoneNumber;

    $.postRequest({
        url : "/Housekeeping/LoginServlet.do",
        data : {
            header : {
                className : "",
                method : "validateCaptcha"
            },
            parameter : {},
            data : {
                captcha : captcha
            }
        },
        success : function(data) {
            alert("登录成功");
            window.history.back();
        },
        error : function(message) {
            alert(message);
        }
    });

    return false;
}
