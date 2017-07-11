dojo.require('dojo.dom');
dojo.require('dojo.domReady!');
dojo.require("dojo.parser");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.Dialog");
dojo.require("dijit.TitlePane");
dojo.require("dojox.grid.DataGrid");
dojo.require("dojo.store.Memory");

require(["dojo/ready"], function(ready) {
    ready(function() {
        // 添加行点击事件
        dojo.connect(dijit.byId('grid'), "onRowClick", function(e) {

        });
        query();
    });
});

/**
 * 刷新
 */
function query() {
    window.parent.showMask();
    // 以post的方式提交请求
    dojo.xhrPost({
        url : "/Housekeeping/ManagerServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "query"
            },
            parameter : {},
            data : {}
        }),
        handleAs : "json", // 返回值的类型为json/text等等
        load : function(data) {
            window.parent.hideMask();
            // 如果服务调用成功
            if (data.header.isSuccess) {
                if ( typeof data.data.data != "undefined") {
                    // 将数据绑定到grid
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.data
                        }
                    });
                    dijit.byId('grid').setStore(store);
                } else {
                    dijit.byId('grid').setStore(null);
                }
            } else {
                // 否则提示错误信息
                alert(data.header.message);
            }
        },
        // 服务异常
        error : function(error) {
            window.parent.hideMask();
            alert(error);
        }
    });
}

/**
 * 添加管理员
 */
function add() {
    if (myForm.validate()) {
        // 验证两次输入的是否一致
        if (dijit.byId("password").getValue() != dijit.byId("password1").getValue()) {
            alert("两次输入密码不一致");
            return;
        }
        window.parent.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/ManagerServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "add"
                },
                parameter : {},
                data : {
                    manager : dojo.formToObject("myForm")
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.parent.hideMask();
                if (data.header.isSuccess) {
                    query();
                    myDialog.hide();
                } else {
                    alert(data.header.message);
                }
            },
            error : function(error) {
                window.parent.hideMask();
                alert(error);
            }
        });
    }
}

/**
 * 显示添加对话框
 */
function showAdd() {
    myDialog.show();
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function deleteFormatter(value, index) {
    return "<div onClick=\"del('" + value + "')\" style='width:100%;text-align:center;' >删除</div>";
}

/**
 * 删除管理员
 * @param id
 *          单元格值
 */
function del(id) {
    if (id == 1) {
        alert("不能删除超级管理员");
        return;
    }

    if (confirm("确定要删除?")) {
        window.parent.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/ManagerServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "delete"
                },
                parameter : {
                    para : id
                },
                data : {
                    manager : {
                        id : id
                    }
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.parent.hideMask();
                if (data.header.isSuccess) {
                    query();
                    myDialog.hide();
                } else {
                    alert(data.header.message);
                }
            },
            error : function(error) {
                window.parent.hideMask();
                alert(error);
            }
        });
    }
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function editFormatter(value, index) {
    return "<div onClick=\"showEdit('" + value + "')\" style='width:100%;text-align:center;' >修改密码</div>";
}

/**
 * 编辑文章
 * @param value
 *            单元格绑定值
 */
function showEdit(value) {
    // 查询当前项
    window.top.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/ManagerServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "showEdit"
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
                editForm.reset();
                editDialog.show();
                // 表单编辑用这个初始化表单
                initForm(editForm, data.data.manager);
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
 * 保存修改
 */
function save() {

    if (editForm.validate()) {
        var formData = getFormData(editForm);
        // 验证原始密码是否正确
        if (dijit.byId("pwd0").getValue() != formData.password) {
            alert("原始密码输入有误");
            return;
        }
        // 验证两次输入的是否一致
        if (dijit.byId("pwd1").getValue() != dijit.byId("pwd2").getValue()) {
            alert("两次输入密码不一致");
            return;
        }
        formData.password = dijit.byId("pwd1").getValue();
        // 保存数据
        dojo.xhrPost({
            url : "/Housekeeping/ManagerServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "save"
                },
                parameter : {

                },
                data : {
                    // 表单编辑用这个收集数据
                    manager : formData
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    editDialog.hide();
                    query();
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

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function showAuthorizationFormatter(value, index) {
    return "<div onClick=\"showAuthorization('" + value + "')\" style='width:100%;text-align:center;' >编辑权限</div>";
}

var managerId = null;

/**
 * 更新两个列表的显示数据
 * @param value
 *             管理员主键
 */
function updateAuthorization(value) {
    window.parent.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/ManagerServlet.do",
        // 提交Json数据
        postData : dojo.toJson({
            header : {
                className : "",
                method : "showAuthorization"
            },
            parameter : {
                id : value
            },
            data : {}
        }),
        handleAs : "json",
        load : function(data) {
            window.parent.hideMask();
            if (data.header.isSuccess) {
                dijit.byId("authorizationList").setStore(null);
                dijit.byId("unAuthorizationList").setStore(null);
                if ( typeof data.data.AuthorizedMenus != "undefined") {
                    // 将数据绑定到grid
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.AuthorizedMenus
                        }
                    });
                    dijit.byId("authorizationList").setStore(store);
                }

                if ( typeof data.data.UnAuthorizedMenus != "undefined") {

                    // 将数据绑定到grid
                    var store1 = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.UnAuthorizedMenus
                        }
                    });
                    dijit.byId("unAuthorizationList").setStore(store1);
                }
            } else {
                alert(data.header.message);
            }
        },
        error : function(error) {
            window.parent.hideMask();
            alert(error);
        }
    });
}

/**
 * 显示对话框,权限管理对话框
 */
function showAuthorization(value) {
    managerId = value;
    authorizationDialog.show();
    updateAuthorization(value);
}

/*********************************************授权管理***************************************/
/**
 * 格式化 单元格(授权)
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function authorizationFormatter(value, index) {
    return "<div onClick=\"authorization('" + value + "')\" style='width:100%;text-align:center;' >授权</div>";
}

/**
 * 授权
 * @param {Object} id
 */
function authorization(id) {
    if (confirm("确定要授权?")) {
        window.parent.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/ManagerServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "authorization"
                },
                parameter : {
                    managerId : managerId,
                    menuId : id
                },
                data : {}
            }),
            handleAs : "json",
            load : function(data) {
                window.parent.hideMask();
                if (data.header.isSuccess) {
                    updateAuthorization(managerId);
                } else {
                    alert(data.header.message);
                }
            },
            error : function(error) {
                window.parent.hideMask();
                alert(error);
            }
        });
    }
}

/**
 * 格式化 单元格(取消授权)
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function unAuthorizationFormatter(value, index) {
    return "<div onClick=\"unAuthorization('" + value + "')\" style='width:100%;text-align:center;' >取消授权</div>";
}

/**
 * 取消授权
 * @param {Object} id
 */
function unAuthorization(id) {
    if (confirm("确定要取消授权?")) {
        window.parent.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/ManagerServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "unAuthorization"
                },
                parameter : {
                    managerId : managerId,
                    menuId : id
                },
                data : {}
            }),
            handleAs : "json",
            load : function(data) {
                window.parent.hideMask();
                if (data.header.isSuccess) {
                    updateAuthorization(managerId);
                } else {
                    alert(data.header.message);
                }
            },
            error : function(error) {
                window.parent.hideMask();
                alert(error);
            }
        });
    }
}

/**
 *  使用json数据初始化表单
 * @param {Object} form dojo表单
 * @param {Object} data 用于初始化dojo表单的数据
 */
function initForm(form, data) {
    if ( typeof form == "undefined" || form == null || typeof data == "undefined" || data == null) {
        return;
    }
    // 将值保存到表单属性中
    form.jsonData = data;
    // 遍历表单元素
    form.getChildren().forEach(function(input) {
        // 得到表单元素的nane属性
        var name = input.name;
        // 将data中相应name对应的值赋值给表单
        if ( typeof name != "undefined" && name != "") {
            var value = data[input.name];
            if ( typeof value != "undefined") {
                input.setValue(value);
            }
        }
    });
}

/**
 * 获取表单数据
 * @param {Object} form
 */
function getFormData(form) {
    if ( typeof form == "undefined" || form == null) {
        return {};
    }
    // 获取表单的绑定数据
    var jsonData = form.jsonData;
    if (jsonData == null || typeof jsonData == "undefined") {
        return {};
    }
    // 将表单数据收集回来，如果已经修改则覆盖
    form.getChildren().forEach(function(input) {
        // 得到表单元素的nane属性
        var name = input.name;
        // 获得表单元素的数据
        var value = input.getValue();
        // 将data中相应name对应的值赋值给表单
        if ( typeof name != "undefined" && name != "" && typeof value != "undefined") {
            jsonData[name] = value;
        }
    });
    return jsonData;
}
