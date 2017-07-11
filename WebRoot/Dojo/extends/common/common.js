dojoConfig = {
    parseOnLoad : true
};
dojo.require('dojo.dom');
dojo.require('dojo.domReady!');
dojo.require("dojo.parser");
dojo.require("dijit.Dialog");
dojo.require("dijit.TitlePane");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dojox.grid.DataGrid");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojo.store.Memory");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.data.ItemFileWriteStore");

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
            };
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
    };
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
        };
    });
    return jsonData;
}