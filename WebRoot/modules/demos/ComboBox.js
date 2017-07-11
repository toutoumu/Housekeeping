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

require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        alert("as")
        // 绑定下拉框数据
        cbbType.store = menuType;
        // 设置选择之后需要显示的属性
        cbbType.searchAttr = "name1";
        // 设置显示的字段
        cbbType.labelAttr = "name1";
    });
});

function Submit() {
    alert(dojo.formToJson("myForm"))
    alert("asfd");
}
