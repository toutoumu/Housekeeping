require(["dojo/parser", "dijit/layout/BorderContainer", "dijit/layout/TabContainer", "dijit/layout/AccordionContainer", "dijit/layout/AccordionPane"]);

require(["dojo/ready", "dijit/form/Button", "dojo/dom", "dojo/store/Memory", "dojo/store/Observable", "dijit/tree/ObjectStoreModel", "dijit/Tree", "dojo/parser", "dijit/layout/ContentPane"], function(ready, Button, dom, Memory, Observable, ObjectStoreModel, Tree, parser, ContentPane) {

    ready(function() {
        // 加载树
        loadTree();
        return;
        dojo.connect(dojo.byId("btnGet"), "onclick", getClick);
        dojo.connect(dojo.byId("btnPost"), "onclick", postClick);

        // 创建一个按钮与页面上的id=‘button’的关联
        var myButton = new Button({
            label : "Click me!",
            onClick : function() {
                dom.byId("result").innerHTML += "Thank you! ";
                var page = new ContentPane({
                    title : "Third Tab",
                    href : "TabContent.jsp",
                    closable : true,
                    selected : true
                });
                tabContainer.addChild(page);
            }
        }, "button");
        // 创建一个div放入到result中
        dojo.create("div", {
            innerHTML : "Hello world!"
        }, "result");

    });

});

function loadTree() {
    // ture 表示post方法加载树，false 表示get方法加载树
    if (true) {
        dojo.xhrPost({
            url : "/Housekeeping/TreeServlet.do",
            postData : dojo.toJson({
                header : {
                    className : "",
                    method : "query"
                },
                parameter : {},
                data : {}
            }),
            handleAs : "json", // 返回值的类型为json/text等等
            load : function(data) {
                // 如果服务调用成功
                if (data.header.isSuccess) {
                    // 新建一个数据对象
                    var myStore = new dojo.store.Memory({
                        // 数据为返回的Menu对象集合，data.data必须是[{},{},{}]类型数据
                        data : data.data.data,
                        // 根据上级节点的id查询子节点
                        getChildren : function(object) {
                            return this.query({
                                parent : object.id
                            });
                        }
                    });
                    var myModel = new dijit.tree.ObjectStoreModel({
                        // 树节点显示的属性，这里显示为Menu对象的title属性
                        labelAttr : "title",
                        store : myStore,
                        // 查询Menu集合中id=1的对象作为根节点
                        query : {
                            id : '1'
                        }
                    });
                    var tree = new dijit.Tree({
                        model : myModel
                    });
                    tree.placeAt(dojo.byId("myTree"));
                    tree.startup();
                    tree.set("onClick", treeItemclick);
                } else {
                    // 否则提示错误信息
                    alert(data.header.message);
                }
            },
            // 服务异常
            error : function(error) {
                alert(error);
            }
        });
    } else {
        // get方法加载树
        dojo.xhrGet({
            url : "/Housekeeping/TreeServlet.do",
            handleAs : "json",
            preventCache : true,
            headers : {
                "Content-Type" : "text/plain",
                "Content-Encoding" : "utf-8",
                "X-Method-Override" : "FANCY-GET"
            },
            load : function(data) {
                // 新建一个数据对象
                var myStore = new dojo.store.Memory({
                    // 数据为返回的Menu对象集合
                    data : data,
                    // 根据上级节点的id查询子节点
                    getChildren : function(object) {
                        return this.query({
                            parent : object.id
                        });
                    }
                });
                // myStore = new Observable(myStore);
                var myModel = new dijit.tree.ObjectStoreModel({
                    // 树节点显示的属性，这里显示为Menu对象的title属性
                    labelAttr : "title",
                    store : myStore,
                    // 查询Menu集合中id=1的对象作为根节点
                    query : {
                        id : '1'
                    }
                });
                var tree = new dijit.Tree({
                    model : myModel
                });
                tree.placeAt(dojo.byId("myTree"));
                tree.startup();
                tree.set("onClick", treeItemclick);

            },
            error : function(error) {
                dojo.byId("getContent").innerHTML = "An unexpected error occurred: " + error;
            }
        });
    }
}

/**
 * 保存当前打开的页面
 */
var pageMap = new HashTable();

/**
 *  Tree项点击事件
 * @param {Object} item 项
 * @param {Object} node 节点
 * @param {Object} event 时间
 */
function treeItemclick(item, node, event) {
    // 如果类型为目录则不打开
    if (item.type == 1) {
        return;
    }
    var key = item.title;
    var page = pageMap.getValue(key);
    if (page == null) {
        page = new dijit.layout.ContentPane({
            title : item.title,
            // href :
            // item.url,
            content : "<iframe src='" + item.url + "' style='height:100%;width:100%;' framespacng='0' frameborder='0' />",
            closable : true,
            selected : true,
            onClose : function() {
                if (confirm("确定要关闭当前页面?")) {
                    pageMap.remove(key);
                    return true;
                }
                ;
            }
        });
        tabContainer.addChild(page);
        pageMap.add(key, page);
    }
    // 只有打开了之后才需要刷新
    else {
        if (item.refresh == true) {
            page.set("content", "<iframe src='" + item.url + "' style='height:100%;width:100%;' framespacng='0' frameborder='0' />");
        }
    }
    tabContainer.selectChild(page);
}

/**
 *get请求点击事件
 */
function getClick() {
    dojo.xhrGet({
        url : "/Housekeeping/TreeServlet.do",
        handleAs : "text",
        preventCache : true,
        headers : {
            "Content-Type" : "text/plain",
            "Content-Encoding" : "UTF-8",
            "X-Method-Override" : "FANCY-GET"
        },
        load : function(data) {
            // Replace newlines with
            // nice HTML tags.
            data = data.replace(/\n/g, "<br>");
            // Replace tabs with spaces.
            data = data.replace(/\t/g, "&nbsp;&nbsp;&nbsp;");
            // 将内容写入到getContent标签
            dojo.byId("getContent").innerHTML = data;
        },
        error : function(error) {
            dojo.byId("getContent").innerHTML = "An unexpected error occurred: " + error;
        }
    });
}

/**
 * post请求点击事件
 */
function postClick() {
    // post请求
    dojo.byId("postContent").innerHTML = "Message being sent...";
    dojo.xhrPost({
        url : "/Housekeeping/TreeServlet.do",
        postData : dojo.toJson({
            header : {
                className : "",
                method : "query"
            },
            parameter : {},
            data : {}
        }),
        // 返回值的类型为json/text等等
        handleAs : "json",
        load : function(data) {
            // 如果服务调用成功
            if (data.header.isSuccess) {
                dojo.byId("postContent").innerHTML = dojo.toJson(data);
            } else {
                // 否则提示错误信息
                alert(data.header.message);
            }
        },
        // 服务异常
        error : function(error) {
            alert(error);
        }
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
