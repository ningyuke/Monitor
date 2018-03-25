<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en"
	class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<%@include file="/common/common.jspf"%>
<script src="${pageContext.request.contextPath}/echarts/esl/esl.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/echarts/echarts-all.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/system/monitor/systemInfo.js"></script>
	<script type="text/javascript">
	function modifySer(key,id){
		$.ajax({
	        async: false,
	        url: "${pageContext.request.contextPath}/system/monitor/modifySer.shtml",
	        data:{"id":id,"value":$("#"+key).val()},
	        dataType: "json",
	        success: function (data) {
	    	    if(data.flag){
	    	    	alert("Modified Successfully!");
	    	    }else{
	    	    	alert("Modified Failed");
	    	    }
	        }
		});
	}
	</script>
</head>
<body class="" style="">
	<section class="vbox">
		<div class="row"
			style="padding-right: 8px; padding-left: 8px; padding-top: 8ps; padding-bottom: 30px;">
			<div class="col-md-12 portlet ui-sortable"
				style="padding-bottom: 15px">
				<section class="panel panel-success portlet-item">
					<header class="panel-heading">
						<i class="fa fa-briefcase"></i> Alert Settings
					</header>
					<table class="table table-striped table-bordered table-hover" width="100%" style="vertical-align: middle;">
						<thead>
							<tr style="background-color: #faebcc; text-align: center;">
								<td width="100">Item Name</td>
								<td width="100">Parameters</td>
								<td width="205">Alert Settings</td>
								<td width="375">Email Settings</td>
							</tr>
						</thead>
						<tbody id="tbody">
							<tr>
								<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>CPU</td>
								<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>Current usage:<span id="td_cpuUsage" style="color: red;">50</span> %
								</td>
								<td align="center">
									<table>
										<tr>
											<td>CPU usage over</td>
											<td><input class='inputclass' name='cpu' id='cpu' type='text' value='${cpu}' /> %,</td>
											<td>Send Email warning message <a class='btn btn-info' href='javascript:void(0)' onclick='modifySer("cpu",1);'>Modify </a></td>
										</tr>
									</table>
								</td>
								<td rowspan='3' align="center" style="vertical-align: middle;">
								  <input class='inputclass' style='width: 250px; height: 32px;' name='toEmail' id='toEmail' type='text' value='${toEmail}' />
								   <a class='btn btn-info'  href='javascript:void(0)' onclick='modifySer("toEmail",4);'>Modify </a>
								</td>
							</tr>
							<tr>
								<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>Server RAM</td>
								<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>Current usage:<span id="td_serverUsage" style="color: blue;">50</span> %
								</td>
								<td align="center">
									<table>
										<tr>
											<td>Server RAM usage over</td>
											<td><input class='inputclass' name='ram' id='ram' type='text' value='${ram}' /> %,</td>
											<td>Send Email warning message<a class='btn btn-info'  href='javascript:void(0)' onclick='modifySer("ram",2);'>Modify </a></td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>JVM RAM</td>
								<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>Current usage:<span id="td_jvmUsage" style="color: green;">50</span> %
								</td>
								<td align="center">
									<table>
										<tr>
											<td>JVM RAM usage over</td>
											<td><input class='inputclass' name='jvm' id='jvm' type='text' value='${jvm}' /> %,</td>
											<td>Send Email warning message <a class='btn btn-info'  href='javascript:void(0)' onclick='modifySer("jvm",3);'>Modify </a></td>
										</tr>
									</table>
							</tr>
						</tbody>
					</table>
				</section>
			</div>
			<div class="col-md-6">
				<section class="panel panel-info portlet-item">
					<header class="panel-heading">
						<i class="fa fa-th-list"></i> Server Information
					</header>
					<div class="panel-body" style="padding: 0px"
						data-url="/system/monitor/systemInfo.shtml"></div>
				</section>
			</div>
			<div class="col-md-6">
				<section class="panel panel-danger portlet-item">
					<header class="panel-heading">
						<i class="fa fa-fire"></i> Real-time Monitoring
					</header>

					<div class="panel-body">
						<div id="main" style="height: 370px;"></div>
					</div>
				</section>
			</div>
			<!-- /.span -->
			<div class="col-md-12" style="margin-top: 10px; height: 330px">
				<section class="panel panel-primary portlet-item">
					<header class="panel-heading">
						<i class="fa fa-rss-square"></i> Real-time Monitoring
					</header>

					<div class="panel-body">
						<table style="width: 100%;">
							<tr>
								<td width="33.3%"><div id="main_one"  style="height: 240px;"></div></td>
								<td width="33.3%"><div id="main_two"  style="height: 240px;"></div></td>
								<td width="33.3%"><div id="main_three" style="height: 240px;"></div></td>
							</tr>
						</table>
					</div>
				</section>
			</div>
			<!-- /.span -->
		</div>
	</section>
</body>
</html>