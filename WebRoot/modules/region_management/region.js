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

var regionType = new dojo.store.Memory({
    data : [{
        name1 : "省",
        id1 : "1"
    }, {
        name1 : "城市",
        id1 : "2"
    }],
    // 指定下来选择的value值对应的属性
    idProperty : "id1"
});
/**
 * 区域类别hash
 */
var regionTypeHash = new HashTable();

/**
 * 初始化hash值
 */
function initHash() {
    // 区域类别
    regionTypeHash.add("0", "未知");
    regionTypeHash.add("1", "省");
    regionTypeHash.add("2", "城市");

}

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initHash();

        // 绑定下拉框数据
        cbbType.store = regionType;
        // 设置选择之后需要显示的属性
        cbbType.searchAttr = "name1";
        // 设置显示的字段
        cbbType.labelAttr = "name1";
        // 设置默认值（必须这样设置，在html里面设置无效）
        cbbType.set("value", "2");

        // 绑定下拉框数据
        cbbType1.store = regionType;
        // 设置选择之后需要显示的属性
        cbbType1.searchAttr = "name1";
        // 设置显示的字段
        cbbType1.labelAttr = "name1";
        // 设置默认值（必须这样设置，在html里面设置无效）
        cbbType1.set("value", "2");

        // 绑定下拉框数据
        cbbType2.store = regionType;
        // 设置选择之后需要显示的属性
        cbbType2.searchAttr = "name1";
        // 设置显示的字段
        cbbType2.labelAttr = "name1";
        query();
    });
});

function query() {
    if (myForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/RegionServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "query"
                },
                parameter : {},
                data : {
                    category : dojo.formToObject("myForm")
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
    addForm.reset();
    addDialog.show();
    cbbType1.set("value", "2");
}

/**
 * 菜单添加事件
 */
function add() {
    if (addForm.validate()) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/RegionServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "add"
                },
                parameter : {},
                data : {
                    category : dojo.formToObject("addForm")
                }
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    query();
                    addDialog.hide();
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
            url : "/Housekeeping/RegionServlet.do",
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
function formatterType(value, index) {
    return regionTypeHash.getValue(value);
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
    return "<div onClick=\"showEdit('" + value + "')\" style='width:100%;text-align:center;' >编辑</div>";
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
        url : "/Housekeeping/RegionServlet.do",
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
                initForm(editForm, data.data.category);
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
        window.top.showMask();
        // 保存数据
        dojo.xhrPost({
            url : "/Housekeeping/RegionServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "save"
                },
                parameter : {

                },
                data : {
                    // 表单编辑用这个收集数据
                    category : getFormData(editForm)
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
 * DataGrid行点击事件
 * @param {Object} e
 */
function onRowclick(e) {
    // alert(e.rowIndex);
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