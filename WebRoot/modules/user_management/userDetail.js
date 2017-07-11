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
    orderStateHash.add("4", "已取消");
    */
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

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initUserForm();
        initHash();
    });
});

function initUserForm() {
    if ( typeof id == "undefined" || id == null) {
        alert("id不能为空");
        return;
    }
    window.top.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/UserServlet.do",
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
                var user = data.data.user;
                initForm(editForm, user);

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

                // 初始化用户支出列表
                if ( typeof data.data.accounts == "undefined") {
                    dijit.byId('gridAccount').setStore(null);
                    return;
                }
                var storeAccount = new dojo.data.ItemFileWriteStore({
                    data : {
                        identifier : "orderNumber",
                        items : data.data.accounts
                    }
                });
                dijit.byId('gridAccount').setStore(storeAccount);

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
        var user = getFormData(editForm);
        if ( typeof user == "undefined" || user == null) {
            return;
        }
        dojo.xhrPost({
            url : "/Housekeeping/UserServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "edit"
                },
                parameter : {},
                data : {
                    user : user
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    //刷新
                    initUserForm();
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
            if (value == "" || value == null) {
                delete jsonData[name];
            } else {
                jsonData[name] = value;
            }
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