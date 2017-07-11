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
}
*/
{
data: [{
    name1: "待支付",
    id1: "1"
}, {
    name1: "待用户确认",
    id1: "2"
}, {
    name1: "已确认",
    id1: "3"
}, {
    name1: "已评价",
    id1: "4"
}, {
    name1: "待后台确认",
    id1: "5"
}, {
    name1: "已取消",
    id1: "6"
}],
// 指定下来选择的value值对应的属性
idProperty: "id1"
});

/**
* 支付状态
* 1.已经支付,2未支付
*/
var paymentState = new dojo.store.Memory({
    data: [{
        name1: "未知",
        id1: "0"
    }, {
        name1: "已经支付",
        id1: "1"
    }, {
        name1: "未支付",
        id1: "2"
    }],
    // 指定下来选择的value值对应的属性
    idProperty: "id1"
});

/**
* 支付方式
*/
var paywayData = new dojo.store.Memory({
    data: [{
        name1: "未知",
        id1: "0"
    }, {
        name1: "支付宝",
        id1: "1"
    }, {
        name1: "余额",
        id1: "2"
    }, {
        name1: "上门",
        id1: "3"
    }],
    // 指定下来选择的value值对应的属性
    idProperty: "id1"
});

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function (ready, lang, dom) {
    ready(function () {
        initData();
        initCategorys();
        // 添加行点击事件
        dojo.connect(dijit.byId('grid'), "onRowDblClick", onRowclick);
    });
});

/**
* 初始化页面数据
*/
function initData() {
    if (typeof orderNumber == "undefined" || orderNumber == null) {
        alert("orderNumber不能为空");
        return;
    }
    window.top.showMask();
    dojo.xhrPost({
        url: "/Housekeeping/OrderServlet.do",
        postData: dojo.toJson({
            header: {
                className: "",
                method: "initData"
            },
            parameter: {
                orderNumber: orderNumber
            },
            data: {}
        }),
        handleAs: "json",
        load: function (data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                // 初始化表单
                var order = data.data.order;
                initForm(editForm, order);

                // 初始化选择雇员数目
                setSelectedEmployeeCount(data.parameter.employeeCount);

                // 初始化订单明细
                if (typeof data.data.details == "undefined") {
                    dijit.byId('gridOrderDetail').setStore(null);
                } else {
                    var store = new dojo.data.ItemFileWriteStore({
                        data: {
                            identifier: "id",
                            items: data.data.details
                        }
                    });
                    dijit.byId('gridOrderDetail').setStore(store);
                }

                // 初始化弹窗口的下拉框
                var menuType = new dojo.store.Memory({
                    data: data.data.categorys,
                    // 指定下来选择的value值对应的属性
                    idProperty: "id"
                });
                // 绑定下拉框数据
                cbbCategory.store = menuType;
                // 设置选择之后需要显示的属性
                cbbCategory.searchAttr = "name";
                // 设置显示的字段
                cbbCategory.labelAttr = "name";
                // 设置默认值（必须这样设置，在html里面设置无效）
                cbbCategory.set("value", "1");

            } else {
                alert(data.header.message);
            }
        },
        // 服务器端错误
        error: function (error) {
            window.top.hideMask();
            alert(error);
        }
    });
}

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
    cbbPaymentState.store = paymentState;
    // 设置选择之后需要显示的属性
    cbbPaymentState.searchAttr = "name1";
    // 设置显示的字段
    cbbPaymentState.labelAttr = "name1";

    // 绑定下拉框数据
    cbbPayway.store = paywayData;
    // 设置选择之后需要显示的属性
    cbbPayway.searchAttr = "name1";
    // 设置显示的字段
    cbbPayway.labelAttr = "name1";

}

/**
* 显示已经选择的雇员信息
*/
function showSelectedEmployee() {
    selectedDialog.show();
    initSelectedEmployee();
}

/**
* 设置已经选择的雇员的数量
*/
function setSelectedEmployeeCount(count) {
    dojo.create("span", {
        id: "demo",
        className: "deLeGemo",
        innerHTML: "已经选择" + count + "个雇员",
        style: {
            "color": "red",
            "fontWeight": "bold"
        }
    }, document.getElementById('count'), "only");
}


/**
* 初始化选择的雇员列表
*/
function initSelectedEmployee() {
    dojo.xhrPost({
        url: "/Housekeeping/EmployeeServlet.do",
        postData: dojo.toJson({
            header: {
                className: "",
                method: "querySelected"
            },
            parameter: {
                orderNumber: orderNumber
            },
            data: {}
        }),
        handleAs: "json",
        load: function (data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                var store = null;
                if (typeof data.data.employees != "undefined") {
                    store = new dojo.data.ItemFileWriteStore({
                        data: {
                            identifier: "id",
                            items: data.data.employees
                        }
                    });
                    // 初始化选择雇员数目
                    setSelectedEmployeeCount(data.data.employees.length);
                };
                dijit.byId('gridSelected').setStore(store);
            } else {
                alert(data.header.message);
            }
        },
        // 服务器端错误
        error: function (error) {
            window.top.hideMask();
            alert(error);
        }
    });
}

/**
* 显示选择雇员弹出窗体
*/
function showSelect() {
    selectDialog.show();
}

/**
* 保存修改
*/
function save() {
    if (!confirm("确定处理？")) {
        return;
    }
    if (editForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url: "/Housekeeping/OrderServlet.do",
            postData: dojo.toJson({
                header: {
                    className: "",
                    method: "oper"
                },
                parameter: {},
                data: {
                    order: getFormData(editForm)
                }
            }),
            handleAs: "json",
            load: function (data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    initData();
                } else {
                    alert(data.header.message);
                }
            },
            // 服务器端错误
            error: function (error) {
                window.top.hideMask();
                alert(error);
            }
        });
    }
}

function query() {
    if (selectForm.validate()) {
        window.top.showMask();
        var employee = dojo.formToObject("selectForm");
        if (typeof employee == "undefined" || employee == null) {
            return;
        }
        for (var property in employee) {
            if (typeof property == "undefined" || property.trim() == "") {
                continue;
            }
            var value = employee[property];
            if (typeof value == "undefined" || value.trim() == "") {
                delete employee[property];
            }
        }

        dojo.xhrPost({
            url: "/Housekeeping/EmployeeServlet.do",
            postData: dojo.toJson({
                header: {
                    className: "",
                    method: "query"
                },
                parameter: {},
                data: {
                    employee: employee
                    // dojo.formToObject("selectForm")
                }
            }),
            handleAs: "json",
            load: function (data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    var store = new dojo.data.ItemFileWriteStore({
                        data: {
                            identifier: "id",
                            items: data.data.employees
                        }
                    });
                    dijit.byId('grid').setStore(store);
                } else {
                    alert(data.header.message);
                }
            },
            // 服务器端错误
            error: function (error) {
                window.top.hideMask();
                alert(error);
            }
        });
    }
}

/**
* 行点击事件为订单添加雇员
* @param {Object} e
*/
function onRowclick(e) {
    var row = grid.getItem(e.rowIndex);
    window.top.showMask();
    dojo.xhrPost({
        url: "/Housekeeping/OrderServlet.do",
        postData: dojo.toJson({
            header: {
                className: "",
                method: "addEmployee"
            },
            parameter: {
                orderNumber: orderNumber,
                employeeId: grid.store.getValue(row, "id")
            },
            data: {}
        }),
        handleAs: "json",
        load: function (data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                selectDialog.hide();
                initData();
            } else {
                alert(data.header.message);
            }
        },
        // 服务器端错误
        error: function (error) {
            window.top.hideMask();
            alert(error);
        }
    });
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
* 删除订单雇员
* @param {Object} 主键
*/
function del(value) {
    if (confirm("确定要删除？")) {
        window.top.showMask();
        dojo.xhrPost({
            url: "/Housekeeping/OrderServlet.do",
            // 提交Json数据
            postData: dojo.toJson({
                header: {
                    className: "",
                    method: "deleteEmployee"
                },
                parameter: {
                    orderNumber: orderNumber,
                    employeeId: value
                },
                data: {}
            }),
            handleAs: "json",
            load: function (data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    initSelectedEmployee();
                } else {
                    alert(data.header.message);
                }
            },
            error: function (error) {
                window.top.hideMask();
                alert(error);
            }
        });
    }
    ;
}

/**
*  使用json数据初始化表单
* @param {Object} form dojo表单
* @param {Object} data 用于初始化dojo表单的数据
*/
function initForm(form, data) {
    if (typeof form == "undefined" || form == null || typeof data == "undefined" || data == null) {
        return;
    }
    // 将值保存到表单属性中
    form.jsonData = data;
    // 遍历表单元素
    form.getChildren().forEach(function (input) {
        // 得到表单元素的nane属性
        var name = input.name;
        // 将data中相应name对应的值赋值给表单
        if (typeof name != "undefined" && name != "") {
            var value = data[input.name];
            if (typeof value != "undefined") {
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
    if (typeof form == "undefined" || form == null) {
        return {};
    }
    ;
    // 获取表单的绑定数据
    var jsonData = form.jsonData;
    if (jsonData == null || typeof jsonData == "undefined") {
        return {};
    }
    // 将表单数据收集回来，如果已经修改则覆盖
    form.getChildren().forEach(function (input) {
        // 得到表单元素的nane属性
        var name = input.name;
        // 获得表单元素的数据
        var value = input.getValue();
        // 将data中相应name对应的值赋值给表单
        if (typeof name != "undefined" && name != "" && typeof value != "undefined") {
            jsonData[name] = value;
        }
        ;
    });
    return jsonData;
}