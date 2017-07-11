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
require(["dojo/ready", 'dojo/_base/lang', 'dojox/grid/DataGrid', 'dojo/data/ItemFileWriteStore', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, DataGrid, ItemFileWriteStore, Button, dom) {
    ready(function() {
        // 添加行点击事件
        dojo.connect(dijit.byId('unitGrid'), "onRowClick", onRowclick);
    });
});
function loadData() {
    dojo.xhrPost({
        // 请求路径
        url : "/Housekeeping/DataGridServlet.do",
        // 请求数据
        postData : dojo.toJson({
            // 请求数据头包
            header : {
                className : "",
                method : "query"
            },
            // 请求参数
            parameter : {
                age : 33,
                userName : "liubin"
            },
            // 请求实体参数
            data : {
                // dataName1 : dojo.formToObject("表单id"),
                // dataName1 : dojo.formToObject("表单id")
            }
        }),
        // 返回数据作为json/text处理
        handleAs : "json",
        // 服务执行成功，只要服务没有抛出异常则算成功
        load : function(data) {
            // 如果服务正常执行
            if (data.header.isSuccess) {
                // 设置DataGrid数据
                var store = new dojo.data.ItemFileWriteStore({
                    data : {
                        identifier : "id",
                        // data.data.user 为服务器返回的数据名字“user”必须与服务器端设置的key对应
                        items : data.data.user
                    }
                });
                // 将数据绑定到DataGrid
                dijit.byId('grid').setStore(store);
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

/**
 * 删除菜单
 * @param value
 *            单元格绑定值
 */
function del(value) {
    if (confirm("确定要删除？")) {
        // 执行删除操作
        alert("删除：" + value);
    };
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
 * 行点击事件
 * @param {Object} e
 */
function onRowclick(e) {
    alert(e.rowIndex);
}