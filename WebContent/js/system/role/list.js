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
			name : "Role name"
		}, {
			colkey : "state",
			name : "Enabled",
			width : "100px",
			renderData : function(rowindex,data, rowdata, column) {
				var ck = "";
				if(data=="1"){
					ck = "checked='checked'";
				}
				var html ='<label class="inline">';
				html+='<input id="id-button-borders" '+ck+' onclick="checkstate(this,\''+rowdata.id+'\')" type="checkbox" class="ace ace-switch ace-switch-6">';
				html+='<span class="lbl middle"></span></label>';
				return html;
			}
		}, {
			colkey : "roleKey",
			name : "roleKey"
		}, {
			colkey : "description",
			name : "Description"
		} ],
		data:{FormMap:"RoleFormMap"},
		jsonUrl : rootPath + '/system/role/findByPage.shtml',
		checkbox : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJSON();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addrole").click("click", function() {
		addRole();
	});
	$("#editrole").click("click", function() {
		editRole();
	});
	$("#delrole").click("click", function() {
		delRole();
	});
	$("#permissions").click("click", function() {
		permissions();
	});
});
function checkstate(obj,id){
		var d = '0';
		if(obj.checked){
			d = '1';
		}
		var url = rootPath + '/system/role/update.shtml';
		var s = CommonUtil.ajax(url, {
			FormMap:"RoleFormMap",
			"id" : id,
			"state" : d
		}, "json");
		if (s == "success") {
			layer.msg('Operate successfully');
		} else {
			layer.msg('Operate failed');
		}
}
function editRole() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("Can only select one");
		return;
	}
	var url = rootPath +'/system/role/edit.shtml?FormMap=RoleFormMap&id=' + cbox;
	pageii = layer.open({
		title : "Edit",
		type : 1,
		area : [ "800px", "70%" ],
		content : CommonUtil.ajax(url)
	});
}
function permissions() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("Please select one object!");
		return;
	}
	var url = rootPath + '/system/resources/permissions.shtml?roleId='+cbox;
	pageii = layer.open({
		title : "Assign permissions",
		type : 1,
		area : [ "250px", "80%" ],
		content : CommonUtil.ajax(url)
	});
}
function addRole() {
	var url = rootPath + '/system/role/add.shtml';
	pageii = layer.open({
		title : "New",
		type : 1,
		area : [ "800px", "70%" ],
		content : CommonUtil.ajax(url)
	});
}
function delRole() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("Please select removed item!");
		return;
	}
	layer.confirm('Delete or not?', function(index) {
		var url = rootPath + '/system/role/deleteByIds.shtml';
		var s = CommonUtil.ajax(url, {
			id : cbox.join(","),
			FormMap:"RoleFormMap"
		}, "json");
		if (s == "success") {
			layer.msg('Delete successfully');
			grid.loadData();
		} else {
			layer.msg('Delete failed');
		}
	});
}