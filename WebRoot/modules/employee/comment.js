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
 * 订单状态hash
 */
var orderStateHash = new HashTable();
/**
 * 支付方式 hash
 */
var paywayStateHash = new HashTable();
/**
 *支付状态hash
 */
var paymentStateHash = new HashTable();

/**
 * 初始化hash值
 */
function initHash() {
    // 订单状态
    orderStateHash.add("0", "未知");
    orderStateHash.add("1", "待支付");
    orderStateHash.add("2", "待用户确认");
    orderStateHash.add("3", "已确认");
    orderStateHash.add("4", "已评价");
    orderStateHash.add("5", "待后台确认");
    orderStateHash.add("6", "已取消");

    /*orderStateHash.add("0", "未知");
    orderStateHash.add("1", "待确定");
    orderStateHash.add("2", "已确定");
    orderStateHash.add("3", "已完成");
    orderStateHash.add("4", "已取消");*/

    // 支付方式
    paywayStateHash.add("0", "未知");
    paywayStateHash.add("1", "支付宝");
    paywayStateHash.add("2", "余额");
    paywayStateHash.add("3", "上门");

    // 支付状态
    paymentStateHash.add("0", "未知");
    paymentStateHash.add("1", "已支付");
    paymentStateHash.add("2", "未支付");
}

/**
 * 雇员状态
 * 1空闲. 2休假
 */
var cbbStateData = new dojo.store.Memory({
    data : [{
        name1 : "未知",
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

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initEmployeeForm();

        // 绑定下拉框数据
        cbbState.store = cbbStateData;
        // 设置选择之后需要显示的属性
        cbbState.searchAttr = "name1";
        // 设置显示的字段
        cbbState.labelAttr = "name1";

        initHash();
    });
});

function initEmployeeForm() {// 绑定下拉框数据
    if ( typeof id == "undefined" || id == null) {
        alert("id不能为空");
        return;
    }
    window.top.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/EmployeeServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "init"
            },
            parameter : {
                id : id
            },
            data : { }
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                // 初始化表单
                var employee = data.data.employee;
                initForm(editForm, employee);
                dojo.byId("imgPhoto").src = employee.photo;
                // 初始化评论
                /*<div>
                 <div>
                 <span>${comment.userId }</span>
                 <span>评分:${comment.grade }</span>
                 <span>${comment.content }</span>
                 </div>
                 <div>${comment.commentTime }</div>
                 <div>
                 <a href="#" onclick="return deleteComment(${comment.id})">删除</a>
                 </div>
                 </div>
                 <hr />*/
                if ( typeof data.data.comments != "undefined") {
                    // 评论列表的div
                    var comments = dojo.byId("comment");
                    dojo.empty(comments);
                    data.data.comments.forEach(function(comm) {
                        //单条评论的最外层div
                        var comment = dojo.create("div", {
                            style : {
                                'marginLeft' : '5px'
                            }
                        }, comments, "last");
                        // 包装用户名,评分,内容的div
                        var commentDiv = dojo.create("div", { }, comment, "last");
                        // 用户名
                        dojo.create("span", {
                            innerHTML : comm.userName,
                            style : {
                                'color' : 'blue',
                                'fontWeight' : 'bold'
                            }
                        }, commentDiv, "last");
                        //评分
                        dojo.create("span", {
                            innerHTML : "<span style='font-weight:normal'>评分:</span>" + comm.grade,
                            style : {
                                'marginLeft' : '5px',
                                'fontWeight' : 'bold'
                            }
                        }, commentDiv, "last");
                        //内容
                        dojo.create("span", {
                            innerHTML : comm.content,
                            style : {
                                'marginLeft' : '5px'
                            }
                        }, commentDiv, "last");

                        // 评论时间
                        dojo.create("div", {
                            innerHTML : comm.commentTime,
                            style : {
                                'color' : 'gray',
                                'marginTop' : '5px'
                            }
                        }, comment, "last");
                        // 删除
                        dojo.create("a", {
                            innerHTML : "删除",
                            href : "#",
                            onclick : "return deleteComment(" + comm.id + ")",
                            style : {
                                'marginTop' : '5px'
                            }
                        }, comment, "last");
                        // 分割线
                        dojo.create("hr", { }, comment, "last");
                    });
                } else {
                    var comments = dojo.byId("comment");
                    dojo.empty(comments);
                    dojo.create("div", {
                        innerHTML : "暂时没有评论"
                    }, comments, "last");
                }

                // 初始化订单列表
                if ( typeof data.data.orders == "undefined") {
                    dijit.byId('gridOrder').setStore(null);
                    return;
                }
                var store = new dojo.data.ItemFileWriteStore({
                    data : {
                        identifier : "orderNumber",
                        items : data.data.orders
                    }
                });
                dijit.byId('gridOrder').setStore(store);
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

function save() {
    if (editForm.validate()) {
        window.top.showMask();
        var employee = getFormData(editForm);
        if ( typeof employee == "undefined" || employee == null) {
            return;
        }
        dojo.xhrPost({
            url : "/Housekeeping/EmployeeServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "edit"
                },
                parameter : {},
                data : {
                    employee : employee
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    //刷新
                    initEmployeeForm();
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

function deleteComment(id) {
    if (confirm("确定要删除该评论?")) {
        window.top.showMask();
        var employee = getFormData(editForm);
        if ( typeof employee == "undefined" || employee == null) {
            return;
        }
        dojo.xhrPost({
            url : "/Housekeeping/EmployeeServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "deleteComment"
                },
                parameter : {
                    id : id
                },
                data : { }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    //刷新
                    initEmployeeForm();
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
    return false;
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterPayway(value, index) {
    return paywayStateHash.getValue(value);
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */

function formatterPaymentState(value, index) {
    return paymentStateHash.getValue(value);
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterOrderState(value, index) {
    return orderStateHash.getValue(value);
}

/**
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterDetail(value, index) {
    return "<div onClick=\"showDetail('" + value + "')\" style='width:100%;text-align:center;' >订单详情</div>";
}

/**
 * 显示订单详情
 * @param value
 *            单元格绑定值
 */
function showDetail(value) {
    var item = {};
    item.url = "/Housekeeping/modules/order/orderDetail.jsp?orderNumber=" + value;
    item.title = "订单详情";
    // 需要刷新
    item.refresh = true;
    window.top.treeItemclick(item, null, null);
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