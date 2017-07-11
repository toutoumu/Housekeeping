require(["dojo/parser", "dijit/layout/BorderContainer", "dijit/layout/TabContainer", "dijit/layout/AccordionContainer", "dijit/layout/AccordionPane"]);

require(["dojo/ready", "dijit/form/Button", "dojo/dom", "dojo/store/Memory", "dojo/store/Observable", "dijit/tree/ObjectStoreModel", "dijit/Tree", "dojo/parser", "dijit/layout/ContentPane"], function(ready, Button, dom, Memory, Observable, ObjectStoreModel, Tree, parser, ContentPane) {

    ready(function() {
        // 给get请求按钮添加点击事件，点击事件调研dojo.xhrGet请求服务器数据
        dojo.connect(dojo.byId("btnGet"), "onclick", loginBtnClick);
    });
    // end ready
});

function loginBtnClick() {
    var userName = dojo.byId("name").value;
    var password = dojo.byId("password").value;
    if (userName == null || password == null || userName == "" || password == "") {
        alert("用户名或密码不能为空");
        return;
    }
    dojo.xhrPost({
        url : "/Housekeeping/ManagerServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "login"
            },
            parameter : {},
            data : {
                manager : dojo.formToObject("loginForm")
            }
        }),
        handleAs : "json",
        load : function(data) {
            // 登录成功跳转页面
            if (data.header.isSuccess) {
                window.location = "/Housekeeping/modules/framework/main.jsp";
            } else {
                alert(data.header.message);
            }
        },
        // 服务器端错误
        error : function(error) {
            alert(error);
        }
    });
}
