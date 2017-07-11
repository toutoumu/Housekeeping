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
dojo.require("dojo.data.ObjectStore");
dojo.require("dijit.form.FilteringSelect");

var menuType = new dojo.store.Memory({
    data : [{
        name1 : "目录",
        id1 : "1"
    }, {
        name1 : "菜单",
        id1 : "2"
    }],
    // 指定下来选择的value值对应的属性
    idProperty : "id1"
});

var menuCategory = new HashTable();
require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        menuCategory.add("1", "目录");
        menuCategory.add("2", "菜单");
        // 绑定下拉框数据
        cbbType.store = menuType;
        // 设置选择之后需要显示的属性
        cbbType.searchAttr = "name1";
        // 设置显示的字段
        cbbType.labelAttr = "name1";
        // 设置默认值（必须这样设置，在html里面设置无效）
        cbbType.set("value", "2");

        // 绑定下拉框数据
        cbbType1.store = menuType;
        // 设置选择之后需要显示的属性
        cbbType1.searchAttr = "name1";
        // 设置显示的字段
        cbbType1.labelAttr = "name1";
        // 设置默认值（必须这样设置，在html里面设置无效）
        cbbType1.set("value", "2");
    });
});
function query() {

    if (myForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/MenuLoadServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "query"
                },
                parameter : {},
                data : {
                    menu : dojo.formToObject("myForm")
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    if ( typeof data.data.data == "undefined") {
                        dijit.byId('grid').setStore(null);
                        return;
                    }
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.data
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

/**
 * 显示添加对话框
 */
function showAdd() {
    myDialog.show();
}

/**
 * 菜单添加事件
 */
function add() {
    if (addForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/MenuLoadServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "add"
                },
                parameter : {},
                data : {
                    menu : dojo.formToObject("addForm")
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
 * 删除菜单
 * @param value
 *            单元格绑定值
 */
function del(value) {
    // 根目录不允许删除
    if (value == 1) {
        alert("根目录不允许删除");
        return;
    }
    ;
    if (confirm("确定要删除？")) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/MenuLoadServlet.do",
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
function formatter(value, index) {
    return "<div onClick=\"del('" + value + "')\" style='width:100%;text-align:center;' >删除</div>";
}

/**
 * 解码菜单类别
 * @param value
 * @param index
 * @returns
 */
function decode(value, index) {
    if (menuCategory == null || typeof menuCategory.getValue(value) == "undefined") {
        return value;
    }
    return menuCategory.getValue(value);
}

/**
 * DataGrid行点击事件
 * @param {Object} e
 */
function onRowclick(e) {
    alert(e.rowIndex);
}

