var dialog;
var grid;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			hide:true
		}, {
			colkey : "type",
			name : "Warning type",
			width : "85px"
		},{
			colkey : "threshold",
			name : "Preset value",
			width : "85px"
		},{
			colkey : "currentValue",
			name : "Monitoring value",
			width : "85px"
		}, {
			colkey : "email",
			name : "Email"
		}, {
			colkey : "emailText",
			name : "Email content"
		} ,{
			colkey : "operTime",
			name : "Warning time",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}],
		data:{FormMap:"ServerInfoFormMap"},
		jsonUrl : rootPath + '/system/monitor/findMonitorLogPages.shtml',
		checkbox : false
	});
	$("#searchForm").click("click", function() {//绑定查询按扭
		var searchParams = $("#searchForm").serializeJSON();
		grid.setOptions({
			data : searchParams
		});
	});
	$("#exportOptLog").click("click",function(){
		 $(this).closest("form").attr("action",rootPath +'/system/monitor/exportMonitorDataByPage.shtml');
		 $(this).closest("form").submit();
		 $(this).closest("form").attr("action","");
	});
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		format:'yyyy-mm-dd'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
});