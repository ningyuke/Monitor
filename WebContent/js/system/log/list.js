var grid;
$(function() {
	grid = lyGrid({
				pagId : 'paging',
				l_column : [ {
					colkey : "id",
					name : "ID",
					hide : true
				}, {
					colkey : "accountName",
					name : "Account"
				}, {
					colkey : "module",
					name : "Module"
				}, {
					colkey : "methods",
					name : "Operation type"
				}, {
					colkey : "actionTime",
					name : "Response time",
						width : "150px"
				} , {
					colkey : "userIP",
					name : "IP Address"
				}, {
					colkey : "operTime",
					name : "Operation time",
					renderData : function(rowindex,data, rowdata, column) {
						return new Date(data).format("yyyy-MM-dd hh:mm:ss");
					}
				}, {
					colkey : "description",
					name : "Execution Description",
					renderData : function(rowindex,data, rowdata, column) {
						if(data.indexOf("successfully")>-1)
						return data;
						else
						return "<a onclick='show(this)' style='cursor: pointer;'><font color='red'>Execution method exception:</font> <font color='blue'>View details</font>" +
								"<div style='display: none;'>"+data+"</div></a>";
					}
				}],
				data:{FormMap:"LogFormMap"},
				jsonUrl : rootPath + '/report/data/optLogs/findOptLogsByPage.shtml',
				checkbox : false
			});
	$("#search").click("click", function() {//绑定查询按扭
		var searchParams = $("#searchForm").serializeJSON();
		grid.setOptions({
			data : searchParams
		});
	});
	$("#exportOptLog").click("click",function(){
		 $(this).closest("form").attr("action",rootPath +'/report/data/optLogs/exportLogDataByPage.shtml');
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
function show(tb){
	layer.open({
	    type: 1,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['80%', '80%'], //宽高
	    content: $(tb).find("div").html()
	});
}