require([ "dijit/form/Form", "dijit/form/ValidationTextBox",
		"dijit/form/DateTextBox" ]);
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

dojo.require("dijit.Editor");
// Require a few extra plugins
dojo.require("dijit._editor.plugins.TextColor");
dojo.require("dijit._editor.plugins.LinkDialog");
dojo.require("dijit._editor.plugins.FullScreen");
dojo.require("dijit._editor.plugins.ViewSource");
dojo.require("dijit._editor.plugins.NewPage");

var category = new HashTable();
// 也没Dom加载完成后执行下面的ready方法
require([ "dojo/ready", 'dojo/_base/lang', 'dojo/dom', 'dojo/domReady!' ],
		function(ready, lang, dom) {
			ready(function() {
				// 初始化文章类别下拉框
				initCategorys();
			});
		});

/**
 * 初始化文章类别
 */
function initCategorys() {
	// 使用post请求servlet:/Housekeeping/ArticleServlet.do
	dojo.xhrPost({
		url : "/Housekeeping/ArticleServlet.do",
		postData : dojo.toJson({
			header : {
				className : "",// 暂时不需要填
				method : "category"// ArticleServlet中的方法名称
			},
			parameter : {},// 参数格式{id:12,name:"liubin",age:12}
			data : {}
		// 请求的实体数据参数格式(objectName:dojo.formToObject("myForm"))
		}),
		handleAs : "json",// 返回的数据以json处理
		load : function(data) {
			window.top.hideMask();// 隐藏遮罩
			if (data.header.isSuccess) {// 如果执行成功
				if (typeof data.data.categorys == "undefined") {// 如果没有查询到数据
					return;
				}
				// 下拉框的数据定义
				var menuType = new dojo.store.Memory({
					data : data.data.categorys, // 下拉框数据
					idProperty : "id" // 指定下来选择的value值对应的属性,这个值必须要表字段对应,此处为表的id

				});
				// 绑定下拉框数据
				cbbCategory.store = menuType;
				// 设置选择之后需要显示的属性
				cbbCategory.searchAttr = "name";
				// 设置显示的字段
				cbbCategory.labelAttr = "name";
				// 设置默认值（必须这样设置，在html里面设置无效）
				cbbCategory.set("value", "1");// 这里的value是固定写法,1代表的是上面id对应的值而不是第一项

				// 绑定下拉框数据
				cbbCategory1.store = menuType;
				// 设置选择之后需要显示的属性
				cbbCategory1.searchAttr = "name";
				// 设置显示的字段
				cbbCategory1.labelAttr = "name";
				// 设置默认值（必须这样设置，在html里面设置无效）
				cbbCategory1.set("value", "1");

				// 绑定下拉框数据
				cbbCategory2.store = menuType;
				// 设置选择之后需要显示的属性
				cbbCategory2.searchAttr = "name";
				// 设置显示的字段
				cbbCategory2.labelAttr = "name";

				// 生成HashTable
				var categorys = data.data.categorys;
				category = objectArrayToHashTable(categorys,"id","name");
				/*for (var i = 0; i < categorys.length; i++) {
					var item = categorys[i];
					if (!category.containsKey(item.id)) {
						category.add(item.id, item.name);
					}
				}*/

			} else {
				alert(data.header.message);// 如果服务发送错误弹出错误消息
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
 * 查询
 */
function query() {
	if (myForm.validate()) {// 表单验证通过
		window.top.showMask();// 显示遮罩层
		dojo.xhrPost({// post请求
			url : "/Housekeeping/ArticleServlet.do",
			postData : dojo.toJson({
				header : {
					className : "",
					method : "query" // 请求query方法
				},
				parameter : {},// 请求参数,这里没有请求参数
				data : {
					article : dojo.formToObject("myForm")
				// 请求数据,这里为表单数据"article"为数据的名称,必须要servlet中的query方法中
				// 代码行:jsonRequest.getEntity("article",
				// Article.class);中的article对应
				}
			}),
			handleAs : "json",// 返回值以json方式处理
			load : function(data) {
				window.top.hideMask();// 隐藏遮罩
				if (data.header.isSuccess) {// 如果服务调用成功
					if (typeof data.data.articles == "undefined") {// 如果查询没有数据
						dijit.byId('grid').setStore(null);// 清空网格列表
						return;
					}
					// 设置网格数据
					var store = new dojo.data.ItemFileWriteStore({
						data : {
							identifier : "id",
							items : data.data.articles
						// 这个data.data.articles的articles应该与query方法中代码行jsonResponse.setData("articles",
						// articles.getArticles());中的articles一样
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
	addForm.reset();// 清空表单addForm为data-dojo-id="addForm"中的addForm
	addDialog.show();// 显示对话框addDialog为data-dojo-id="addDialog"中的addDialog
}

/**
 * 菜单添加事件
 */
function add() {
	if (addContent.getValue() == ""
			|| typeof addContent.getValue() == "undefined") {
		alert("文章内容不能为空");
		return;
	}
	if (addForm.validate()) {
		window.top.showMask();
		var article = dojo.formToObject("addForm");
		article.content = addContent.getValue();
		dojo.xhrPost({
			url : "/Housekeeping/ArticleServlet.do",
			// 提交Json数据
			postData : dojo.toJson({
				header : {
					className : "",
					method : "add"
				},
				parameter : {},
				data : {
					article : article
				// dojo.formToObject("addForm")
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
 *  单元格数据转换
 * @param value
 *            绑定字段值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function decode(value, index) {
	if (typeof category.getValue(value) == "undefined") {
		return value;
	}
	return category.getValue(value);
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
	return "<div onClick=\"del('" + value
			+ "')\" style='width:100%;text-align:center;' >删除</div>";
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
			url : "/Housekeeping/ArticleServlet.do",
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
 *            绑定字段值即field字段的值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterEdit(value, index) {
	return "<div onClick=\"showEdit('" + value
			+ "')\" style='width:100%;text-align:center;' >编辑</div>";
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
		url : "/Housekeeping/ArticleServlet.do",
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
				initForm(editForm, data.data.article);
				editContent.setValue(data.data.article.content);
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
 *            绑定字段值即field字段的值
 * @param index
 *            行索引
 * @returns {___anonymous1325_1325}
 */
function formatterPush(value, index) {
	return "<div onClick=\"push('" + value
			+ "')\" style='width:100%;text-align:center;' >推送</div>";
}

/**
 * 编辑文章
 * @param value
 *            单元格绑定值
 */
function push(value) {
	if (!confirm("确定要推送？")) {
		return;
	}

	// 查询当前项
	window.top.showMask();
	dojo.xhrPost({
		url : "/Housekeeping/ArticleServlet.do",
		postData : dojo.toJson({
			header : {
				className : "",
				method : "push"
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
				alert("文章已经推送");
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
	if (editContent.getValue() == ""
			|| typeof editContent.getValue() == "undefined") {
		alert("文章内容不能为空");
		return;
	}
	if (editForm.validate()) {
		// 表单编辑用这个收集数据
		var article = getFormData(editForm);
		article.content = editContent.getValue();
		// 保存数据
		dojo.xhrPost({
			url : "/Housekeeping/ArticleServlet.do",
			postData : dojo.toJson({
				header : {
					className : "",
					method : "save"
				},
				parameter : {

				},
				data : {
					// 表单编辑用这个收集数据
					article : article
				// getFormData(editForm)
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
	if (typeof form == "undefined" || form == null
			|| typeof data == "undefined" || data == null) {
		return;
	}
	// 将值保存到表单属性中
	form.jsonData = data;
	// 遍历表单元素
	form.getChildren().forEach(function(input) {
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
	form.getChildren().forEach(
			function(input) {
				// 得到表单元素的nane属性
				var name = input.name;
				// 获得表单元素的数据
				var value = input.getValue();
				// 将data中相应name对应的值赋值给表单
				if (typeof name != "undefined" && name != ""
						&& typeof value != "undefined") {
					jsonData[name] = value;
				}
				;
			});
	return jsonData;
}

/**
 * object数组对象转hash对象
 * @param obj [{name:"liubin",age:27},{name:"liubin",age:27}]
 * @param key key字段名称
 * @param value value字段名称
 * @returns
 */
function objectArrayToHashTable(obj, key, value) {
	if (obj == null || typeof obj == "undefined") {
		return null;
	}
	var hash = new HashTable();
	for (var i = 0; i < obj.length; i++) {
		var item = obj[i];
		if (!hash.containsKey(item[key])) {
			hash.add(item[key], item[value]);
		}
	}
	return hash;
}
