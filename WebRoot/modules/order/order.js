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

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initCategorys();
        initHash();
        query();
    });
});

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

/**
 * 订单状态
 * 订单状态   0.待支付； 1.待用户确认（已经后台确认）；2.已确认（待用户评价）；3.已评价 4.待后台确认5.已取消
 */
var orderState = new dojo.store.Memory(
/*{
 data : [{
 name1 : "全部",
 id1 : "0"
 }, {
 name1 : "待确定",
 id1 : "1"
 }, {
 name1 : "已确定",
 id1 : "2"
 }, {
 name1 : "已完成",
 id1 : "3"
 }, {
 name1 : "已取消",
 id1 : "4"
 }],
 // 指定下来选择的value值对应的属性
 idProperty : "id1"
 }*/
{
    data : [{
        name1 : "全部",
        id1 : "0"
    }, {
        name1 : "待支付",
        id1 : "1"
    }, {
        name1 : "待用户确认",
        id1 : "2"
    }, {
        name1 : "已确认",
        id1 : "3"
    }, {
        name1 : "已评价",
        id1 : "4"
    }, {
        name1 : "待后台确认",
        id1 : "5"
    }, {
        name1 : "已取消",
        id1 : "6"
    }],
    // 指定下来选择的value值对应的属性
    idProperty : "id1"
});

/**
 * 支付状态
 * 1.已经支付,2未支付
 */
var paymentState = new dojo.store.Memory({
    data : [{
        name1 : "全部",
        id1 : "0"
    }, {
        name1 : "已经支付",
        id1 : "1"
    }, {
        name1 : "未支付",
        id1 : "2"
    }],
    // 指定下来选择的value值对应的属性
    idProperty : "id1"
});

/**
 * 初始化下拉框数据
 */
function initCategorys() {
    // 绑定下拉框数据
    cbbOrderState.store = orderState;
    // 设置选择之后需要显示的属性
    cbbOrderState.searchAttr = "name1";
    // 设置显示的字段
    cbbOrderState.labelAttr = "name1";

    // 绑定下拉框数据
    operState.store = orderState;
    // 设置选择之后需要显示的属性
    operState.searchAttr = "name1";
    // 设置显示的字段
    operState.labelAttr = "name1";

    // 绑定下拉框数据
    cbbPaymentState.store = paymentState;
    // 设置选择之后需要显示的属性
    cbbPaymentState.searchAttr = "name1";
    // 设置显示的字段
    cbbPaymentState.labelAttr = "name1";

    window.top.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/OrderServlet.do",
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

/*
* dojo.declare("dijit.form.TimestampTextBox",dijit.form.TimeTextBox,{
* postMixInProperties: function(){ this.inherited('postMixInProperties',
* arguments); this.constraints.selector = 'timestamp'; } });
*/
// 自定义的日期控件,重写serialize方法使得日期格式为yyyy-MM-dd HH:mm:ss
dojo.declare("TimestampTextBox", [dijit.form.DateTextBox], {
    serialize : function(d, options) {
        return dojo.date.locale.format(d, {
            selector : 'datetime',
            datePattern : 'yyyy-MM-dd',
            timePattern : 'HH:mm:ss'
        }).toLowerCase();
    }
});

function query() {
    if (myForm.validate()) {
        window.top.showMask();
        // 删除表单中的空值对(如name:"")
        var order = dojo.formToObject("myForm");
        if ( typeof order == "undefined" || order == null) {
            return;
        }
        for (var property in order) {
            if ( typeof property == "undefined" || property.trim() == "") {
                continue;
            }
            var value = order[property];
            if ( typeof value == "undefined" || value.trim() == "") {
                delete order[property];
            }
        }
        dojo.xhrPost({
            url : "/Housekeeping/OrderServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "query"
                },
                parameter : {},
                data : {
                    order : order
                    // dojo.formToObject("myForm")
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    if ( typeof data.data.orders == "undefined") {
                        dijit.byId('grid').setStore(null);
                        return;
                    }
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "orderNumber",
                            items : data.data.orders
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
    if (myForm.validate()) {
        window.top.showMask();

        dojo.xhrPost({
            url : "/Housekeeping/OrderServlet.do",
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
                    if ( typeof data.data.orders == "undefined") {
                        dijit.byId('grid').setStore(null);
                        return;
                    }
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "orderNumber",
                            items : data.data.orders
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
            url : "/Housekeeping/OrderServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "add"
                },
                parameter : {},
                data : {
                    article : dojo.formToObject("addForm")
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
            url : "/Housekeeping/OrderServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "delete"
                },
                parameter : {
                    orderNumber : value
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
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterOper(value, index) {
    return "<div onClick=\"showOper('" + value + "')\" style='width:100%;text-align:center;' >处理</div>";
}

/**
 * 显示处理对话框
 * @param value
 */
function showOper(value) {
    dojo.xhrPost({
        url : "/Housekeeping/OrderServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "showOper"
            },
            parameter : {
                orderNumber : value
            },
            data : {}
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                if ( typeof data.data.order == "undefined") {
                    return;
                }
                operForm.reset();
                operDialog.show();
                // 表单编辑用这个初始化表单
                initForm(operForm, data.data.order);
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
 * 订单处理操作
 */
function oper() {
    if (!confirm("确定处理？")) {
        return;
    }
    if (operForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/OrderServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "oper"
                },
                parameter : {},
                data : {
                    order : getFormData(operForm)
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                operDialog.hide();
                if (data.header.isSuccess) {
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
 * DataGrid行点击事件
 * @param {Object} e
 */
function onRowclick(e) {
    alert(e.rowIndex);
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
            ;
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
    ;
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
        ;
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