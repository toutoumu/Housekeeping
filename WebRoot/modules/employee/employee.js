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

/**
 * 雇员状态
 * 1空闲. 2休假
 */
var cbbStateData = new dojo.store.Memory({
    data : [{
        name1 : "全部",
        id1 : "0"
    }, {
        name1 : "空闲",
        id1 : "1"
    }, {
        name1 : "休假",
        id1 : "2"
    }],
    // 指定下来选择的value值对应的属性
    idProperty : "id1"
});

var employeeStateHash = new HashTable();

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initCategorys();
        initHash();
    });
});

function initHash() {
    employeeStateHash.add("0", "未知");
    employeeStateHash.add("1", "空闲");
    employeeStateHash.add("2", "休假");
}

/**
 * 初始化文章类别
 */
function initCategorys() {
    dojo.xhrPost({
        url : "/Housekeeping/EmployeeServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "category"
            },
            parameter : {},
            data : {}
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                var menuType = new dojo.store.Memory({
                    data : data.data.categorys,
                    // 指定下来选择的value值对应的属性
                    idProperty : "id"
                });
                // 绑定下拉框数据
                cbbCategory.store = menuType;
                // 设置选择之后需要显示的属性
                cbbCategory.searchAttr = "name";
                // 设置显示的字段
                cbbCategory.labelAttr = "name";
                // 设置默认值（必须这样设置，在html里面设置无效）
                //cbbCategory.set("value", "1");

                // 绑定下拉框数据
                cbbState.store = cbbStateData;
                // 设置选择之后需要显示的属性
                cbbState.searchAttr = "name1";
                // 设置显示的字段
                cbbState.labelAttr = "name1";

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
        var employee = dojo.formToObject("myForm");
        if ( typeof employee == "undefined" || employee == null) {
            return;
        }
        for (var property in employee) {
            if ( typeof property == "undefined" || property.trim() == "") {
                continue;
            }
            var value = employee[property];
            if ( typeof value == "undefined" || value.trim() == "") {
                delete employee[property];
            }
        }

        dojo.xhrPost({
            url : "/Housekeeping/EmployeeServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "query"
                },
                parameter : {},
                data : {
                    employee : employee
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
                            items : data.data.employees
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
        url : "/Housekeeping/EmployeeServlet.do",
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
                        items : data.data.employees
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
 * 菜单添加事件
 */
function add() {
    if (addForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/EmployeeServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "add"
                },
                parameter : {},
                data : {
                    employee : dojo.formToObject("addForm")
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

    if (confirm("确定要删除？")) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/EmployeeServlet.do",
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
function formatterState(value, index) {
    return employeeStateHash.getValue(value);
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
    return "<div onClick=\"showDetail('" + value + "')\" style='width:100%;text-align:center;' >查看评论</div>";
}

/**
 * 显示雇员详情
 * @param value
 */
function showDetail(value) {
    var item = {};
    item.url = "/Housekeeping/modules/employee/comment.jsp?id=" + value;
    item.title = "雇员明细";
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
function formatter(value, index) {
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
function showAuthorizationFormatter(value, index) {
    return "<div onClick=\"showAuthorization('" + value + "')\" style='width:100%;text-align:center;' >编辑业务</div>";
}

var employeeId = null;

/**
 * 更新两个列表的显示数据
 * @param value
 *             管理员主键
 */
function updateAuthorization(value) {
    window.parent.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/EmployeeServlet.do",
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
                if ( typeof data.data.AuthorizedCategorys != "undefined") {
                    // 将数据绑定到grid
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.AuthorizedCategorys
                        }
                    });
                    dijit.byId("authorizationList").setStore(store);
                }

                if ( typeof data.data.UnAuthorizedCategorys != "undefined") {

                    // 将数据绑定到grid
                    var store1 = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.UnAuthorizedCategorys
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
    employeeId = value;
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
            url : "/Housekeeping/EmployeeServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "authorization"
                },
                parameter : {
                    employeeId : employeeId,
                    businessCategoryId : id
                },
                data : {}
            }),
            handleAs : "json",
            load : function(data) {
                window.parent.hideMask();
                if (data.header.isSuccess) {
                    updateAuthorization(employeeId);
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
            url : "/Housekeeping/EmployeeServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "unAuthorization"
                },
                parameter : {
                    employeeId : employeeId,
                    businessCategoryId : id
                },
                data : {}
            }),
            handleAs : "json",
            load : function(data) {
                window.parent.hideMask();
                if (data.header.isSuccess) {
                    updateAuthorization(employeeId);
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

function showUpload() {
    uploadForm.reset();
    uploadDialog.show();
    dojo.byId("file").value = "";
}

function upload() {
    if (dojo.byId("file").value == null || dojo.byId("file").value == "") {
        alert("请选择文件");
        return;
    }

    window.top.showMask();
    dojo.request.iframe("/Housekeeping/FileUpload", {
        form : "uploadForm",
        handleAs : "json"
    }).then(function(data) {
        window.top.hideMask();
        if (data.header.isSuccess) {
            dojo.byId("imgPhoto").src = data.parameter.src;
            photoSrc.setValue(data.parameter.src);
            uploadDialog.hide();
        } else {
            alert(data.header.message);
        }
    }, function(error) {
        window.top.hideMask();
        alert(error);
    });
}

/**
 * HashTable
 */
function HashTable() {
    var size = 0;
    var entry = new Object();

    this.add = function(key, value) {
        if (!this.containsKey(key)) {
            size++;
        }
        entry[key] = value;
    };

    this.getValue = function(key) {
        return this.containsKey(key) ? entry[key] : null;
    };

    this.remove = function(key) {
        if (this.containsKey(key) && (
        delete entry[key])) {
            size--;
        }
    };

    this.containsKey = function(key) {
        return ( key in entry);
    };

    this.containsValue = function(value) {
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    };

    this.getValues = function() {
        var values = new Array();
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    };

    this.getKeys = function() {
        var keys = new Array();
        for (var prop in entry) {
            keys.push(prop);
        }
        return keys;
    };

    this.getSize = function() {
        return size;
    };

    this.clear = function() {
        size = 0;
        entry = new Object();
    };
}

