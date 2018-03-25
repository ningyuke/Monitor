var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			width : "50px",
			hide : true
		}, {
			colkey : "userId",
			name : "User ID",
			hide : true
		}, {
			colkey : "accountName",
			name : "Account"
		},{
			colkey : "loginTime",
			name : "Login time",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		} , {
			colkey : "loginIP",
			name : "Login IP"
		}],
		data:{FormMap:"UserLoginFormMap"},
		jsonUrl : rootPath + '/report/data/loginLogs/findByPages.shtml',
		checkbox : false
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJSON();
		grid.setOptions({
			data : searchParams
		});
	});
	$("#exportOptLog").click("click",function(){
		 $(this).closest("form").attr("action",rootPath +'/report/data/loginLogs/exportLogDataByPage.shtml');
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
