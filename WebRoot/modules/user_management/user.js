require(["dijit/form/Form", "dijit/form/ValidationTextBox", "dijit/form/DateTextBox"]);
dojo.require("dojo.parser");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dijit.TitlePane");
dojo.require("dijit.form.Button");
dojo.require("dojox.grid.DataGrid");
dojo.require("dijit.form.NumberTextBox");
dojo.require("dijit.Dialog");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.form.ComboBox");
dojo.require("dojo.store.Memory");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Textarea");
dojo.require("dojo.data.ObjectStore");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dojo.io.iframe");
dojo.require("dojo.request.iframe");

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initUser();
    });
});

/**
 * 初始化用户列表
 */
function initUser() {
    dojo.xhrPost({
        url : "/Housekeeping/UserServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "query"
            },
            parameter : {},
            data : {}
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                var menuType = new dojo.store.Memory({
                    data : data.data.users,
                    // 指定下来选择的value值对应的属性
                    idProperty : "id"
                });
//                // 绑定下拉框数据
//                cbbCategory.store = menuType;
//                // 设置选择之后需要显示的属性
//                cbbCategory.searchAttr = "name";
//                // 设置显示的字段
//                cbbCategory.labelAttr = "name";
//                // 设置默认值（必须这样设置，在html里面设置无效）
//                cbbCategory.set("value", "1");

            } else {
                alert(data.header.message);
            }
        },
        // 服务器端错误
        error : function(error) {
            window.top.hideMask();
            alert(error);
        }
    });
}

function query() {
	if (search == true) {
        if (searchContent.getValue() == "" || searchContent.getValue() == null) {

            alert("请输入搜索条件");
            searchContent.focu();
            return;
        }
    }
    if (myForm.validate()) {
        window.top.showMask();
        var user = dojo.formToObject("myForm");
        if ( typeof user == "undefined" || user == null) {
            return;
        }
        for (var property in user) {
            if ( typeof property == "undefined" || property.trim() == "") {
                continue;
            }
            var value = user[property];
            if ( typeof value == "undefined" || value.trim() == "") {
                delete user[property];
            }
        }

        dojo.xhrPost({
            url : "/Housekeeping/UserServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "query"
                },
                parameter : {},
                data : {
                    user : user
                    // dojo.formToObject("myForm")
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.users
                        }
                    });
                    dijit.byId('grid').setStore(store);
                } else {
                    alert(data.header.message);
                }
            },
            // 服务器端错误
            error : function(error) {
                window.top.hideMask();
                alert(error);
            }
        });
    }
}

function search() {

    if (searchContent.getValue() == "" || searchContent.getValue() == null) {
        alert("请输入搜索条件");
        searchContent.focus();  
        return;
    }
    window.top.showMask();

    dojo.xhrPost({
        url : "/Housekeeping/UserServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "search"
            },
            parameter : {
                searchContent : searchContent.getValue()
            },
            data : {}
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                var store = new dojo.data.ItemFileWriteStore({
                    data : {
                        identifier : "id",
                        items : data.data.users
                    }
                });
                dijit.byId('grid').setStore(store);
            } else {
                alert(data.header.message);
            }
        },
        // 服务器端错误
        error : function(error) {
            window.top.hideMask();
            alert(error);
        }
    });
}

/**
 * 显示添加对话框
 */
function showAdd() {
    myDialog.show();
    dojo.byId("imgPhoto").src = "";
}

/**
 * 会员添加事件
 */
function add() {
    if (addForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/UserServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "add"
                },
                parameter : {},
                data : {
                    user : dojo.formToObject("addForm")
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    query();
                    myDialog.hide();
                } else {
                    alert(data.header.message);
                }
            },
            error : function(error) {
                window.top.hideMask();
                alert(error);
            }
        });
    }
}

/**
 * 删除会员信息
 * @param value
 *            单元格绑定值
 */
function del(value) {

    if (confirm("确定要删除？")) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/UserServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "delete"
                },
                parameter : {
                    id : value
                },
                data : {}
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    query();
                } else {
                    alert(data.header.message);
                }
            },
            error : function(error) {
                window.top.hideMask();
                alert(error);
            }
        });
    }
    ;
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterDetial(value, index) {
    return "<div onClick=\"showDetail('" + value + "')\" style='width:100%;text-align:center;' >查看详情</div>";
}

/**
 * 显示雇员详情
 * @param value
 */
function showDetail(value) {
    var item = {};
    item.url = "/Housekeeping/modules/user_management/userDetail.jsp?id=" + value;
    item.title = "会员明细";
    // 需要刷新
    item.refresh = true;
    window.top.treeItemclick(item, null, null);
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterDel(value, index) {
    return "<div onClick=\"del('" + value + "')\" style='width:100%;text-align:center;' >删除</div>";
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterEdit(value, index) {
    return "<div onClick=\"editUser('" + value + "')\" style='width:100%;text-align:center;' >编辑</div>";
}

var userId = null;


