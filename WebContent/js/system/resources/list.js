var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			hide : true
		}, {
			colkey : "name",
			name : "Menu name",
			align : 'left'
		},  {
			colkey : "resKey",
			name : "KEY",
			align : 'left'
		},{
			colkey : "type",
			name : "Menu type",
			width : "70px",
		}, {
			colkey : "resUrl",
			name : "URL address"
		},{
			colkey : "ishide",
			name : "Hide",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="0"){
					return "否";
				}else if(data=="1"){
					return "是";
				}
			}
		}, {
			colkey : "description",
			width : "350px",
			name : "Description"
		} ],
		data:{FormMap:"ResFormMap",$orderby:" order by level asc "},
		jsonUrl : rootPath + '/system/resources/findByPage.shtml',
		pageSize:100,
		checkbox : true,
		usePage : false,
		treeGrid : {
			type:2,
			tree : true,
			hide:true,
			name : "name",
			id:"id",
			pid:"parentId"
		}
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJSON();
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addFun").click("click", function() {
		addFun();
	});
	$("#editFun").click("click", function() {
		editFun();
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#lyGridUp").click("click", function() {// 上移
		var jsonUrl=rootPath + '/system/resources/sortUpdate.shtml';
		grid.lyGridUp(jsonUrl);
	});
	$("#lyGridDown").click("click", function() {// 下移
		var jsonUrl=rootPath + '/system/resources/sortUpdate.shtml';
		grid.lyGridDown(jsonUrl);
	});
});
function editFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.alert("Can only select one");
		return;
	}
	var url = rootPath + '/system/resources/edit.shtml?FormMap=ResFormMap&id=' + cbox;
	pageii = layer.open({
		title : "Edit",
		type : 1,
		area : [ "600px", "550px" ],
		content : CommonUtil.ajax(url)
	});
}
function addFun() {
	var url =rootPath + '/system/resources/add.shtml';
	pageii = layer.open({
		title : "New",
		type : 1,
		area : [ "600px", "550px" ],
		content : CommonUtil.ajax(url)
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.alert("Please select removed item!");
		return;
	}
	layer.confirm('Delete or not?', function(index) {
		var url = rootPath + '/system/resources/deleteByIds.shtml';
		var s = CommonUtil.ajax(url, {
			id : cbox.join(","),
			FormMap:"ResFormMap"
		}, "json");
		if (s == "success") {
			layer.msg('Delete successfully');
			grid.loadData();
		} else {
			layer.msg('Delete failed');
		}
	});
}
