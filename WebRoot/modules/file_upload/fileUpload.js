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
require(["dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!'], function(ready, lang, dom) {
    ready(function() {
        initData();
    });
});
/**
 * 文件上传
 */
function upload() {
    if (dojo.byId("file").value == null || dojo.byId("file").value == "") {
        alert("请选择文件");
        return false;
    }
    window.top.showMask();
    dojo.request.iframe("/Housekeeping/FileUpload", {
        form : "uploadForm",
        handleAs : "json"
    }).then(function(data) {
        window.top.hideMask();
        if (data.header.isSuccess) {
            dojo.byId("imgPhoto").src = data.parameter.src;
            path.setValue(data.parameter.src);
        } else {
            alert(data.header.message);
        }
    }, function(error) {
        window.top.hideMask();
        alert(error);
    });
}

function save() {
    if (path.getValue() == "") {
        alert("请上传图片");
        return;
    };
    dojo.xhrPost({
        url : "/Housekeeping/PhotoServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "add"
            },
            parameter : {
                path : path.getValue()
            },
            data : { }
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {
                dojo.byId("imgPhoto").src = "";
                path.setValue("");
                initData();
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

function initData() {

    window.top.showMask();
    dojo.xhrPost({
        url : "/Housekeeping/PhotoServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "init"
            },
            parameter : { },
            data : { }
        }),
        handleAs : "json",
        load : function(data) {
            window.top.hideMask();
            if (data.header.isSuccess) {

                if ( typeof data.data.data != "undefined") {
                    var store = new dojo.data.ItemFileWriteStore({
                        data : {
                            identifier : "id",
                            items : data.data.data
                        }
                    });
                    dijit.byId('grid').setStore(store);
                } else {
                    dijit.byId('grid').setStore(null);
                }
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
 * 格式化 单元格
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterImage(value, index) {
    return "<img src ='" + value + "' width='100px'/>";
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
 * 删除文章
 * @param value
 *            单元格绑定值
 */
function del(value) {

    if (confirm("确定要删除？")) {
        window.top.showMask();
        dojo.xhrPost({
            url : "/Housekeeping/PhotoServlet.do",
            // 提交Json数据
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "delete"
                },
                parameter : {
                    id : value
                    // 这里传递了一个参数,名称为id,值为alue参数的值
                },
                data : {}
            }),
            handleAs : "json",
            load : function(data) {
                window.top.hideMask();
                if (data.header.isSuccess) {
                    initData();
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