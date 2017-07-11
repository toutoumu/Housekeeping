
// DataGrid
var grid = dijit.byId("grid1");
var row = grid.getItem(3);
// 获取值
var value = grid.store.getValue(row, "f3");
// 修改值
grid.store.setValue(row, "f3", "abc");
// 2. 获取(取消)选中行
var grid = dijit.byId("grid1");
var selectedRows = grid.selection.getSelected();
var value = selectedRows[0]["f3"];
// 因为支持多选，所以 grid.selection.getSelected 返回的是选中的行数组。
// 获得选择的第一行
grid.selection.getFirstSelected();
// 清除所有选中行
grid.selection.deselectAll();
// 选中第n行
grid.selection.select(4);

// 设置第一行，第二列的单元格的焦点
grid.focus.setFocusIndex(0, 1);

// 滚动显示出第4行
grid.scrollToRow(3);
// 隐藏第五列
grid.layout.setColumnVisibility(4, false);

// 改变行的显示 onStyleRow (参数：row对象)
dojo.connect(grid, "onStyleRow", function(row) {
	// 第二行背景设为红色
	if (row.index == 1) {
		row.customClasses += " redRow ";
	}
});
grid.resize();

/*
 * <style type="text/css"> .redRow tr { background-color: red !important; }
 * </style>
 */

// 添加行点击事件
dojo.connect(grid, "onRowClick", function(e) {
	alert(e.rowIndex);
});

// 行选择事件 onSelected (参数: row index)
dojo.connect(grid, "onSelected", function(index) {
	alert(index);
});

// 单元格点击事件 onCellClick (参数: Event 对象)
dojo.connect(grid, "onCellClick", function(e) {
	console.log("[" + e.rowIndex + "," + e.cellIndex + "]");
	// rowIndex=-1 为表头
	if (e.rowIndex < 0)
		return;
	e.cellNode.style.backgroundColor = "red";
});

alert(dojo.formToJson("myForm"));
alert(dojo.objectToQuery("myForm"));
alert(dojo.queryToObject("user=1&name=2"));
alert(dojo.formToQuery("myForm"));
alert(dojo.formToObject("myForm"));

/** ************************post*************************** */
dojo.xhrPost({
	url : "/Housekeeping/MenuLoadServlet.do",
	// form : dojo.byId("myForm"),// 提交表单
	// postData : dojo.toJson(dojo.formToObject("myForm")),
	postData : dojo.toJson({
		file : "",
		method : "add",
		data : dojo.toJson(dojo.formToObject("myForm"))
	}),
	handleAs : "json",
	load : function(data) {
		var store = new dojo.data.ItemFileWriteStore({
			data : {
				identifier : "id",
				items : data
			}
		});
		dijit.byId('grid').setStore(store);
	},
	error : function(error) {
		alert(error);
	}
});
/** ************************post*************************** */
/** ************************get*************************** */
dojo.xhrGet({
	url : "wode.txt",
	handleAs : "text",
	preventCache : true,
	headers : {
		"Content-Type" : "text/plain",
		"Content-Encoding" : "ISO-8859-1",
		"X-Method-Override" : "FANCY-GET"
	},
	load : function(data) {

	},
	error : function(error) {

	}
});
/** ************************get*************************** */
