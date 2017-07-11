$(document).ready(function() {
    $('.panels_slider').flexslider({
        animation : "slide",
        directionNav : false,
        controlNav : true,
        animationLoop : true,
        slideToStart : 1,
        animationDuration : 300,
        slideshow : true
    });
});

/**
 * @param url {Object}
 *  跳转到的页面, 如果已经登录那么跳转到相应页面,否则跳转到登录页面
 */
function toPage(url) {
    $.postRequest({
        url : "/Housekeeping/LoginServlet.do",
        data : {
            header : {
                className : "",
                method : "isLogin"
            }
        },
        success : function(data, parameter) {
            // 如果用户已经登录, 跳转到相应页面,否则跳转到登录页面
            if (parameter.isLogin == "true") {
                $.mobile.changePage(url, "pop");
            } else {
                $.mobile.changePage("/Housekeeping/mobile/login/login.jsp", "pop");
            }
        }
    });
    return false;
}
