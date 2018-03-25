<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="htmlBody">
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/monitor/list.js"></script>
	</head>
<body>
	<div class="page-content">
<div class="m-b-md">
	<form class="form-inline" id="searchForm" name="searchForm">
	   <div class="form-group">
			<label class="control-label"> <span
				class="h4 font-thin v-middle">Warning type:</span></label> <input
				class="input-medium ui-autocomplete-input" id="type"
				name="type">
		</div>
		<div class="form-group">
			<label class="control-label"> <span
				class="h4 font-thin v-middle">Email:</span></label> <input
				class="input-medium ui-autocomplete-input" id="email"
				name="email">
		</div>
		<div class="input-group">
		     <span class="h4">Time:</span>
		     <input class="input-middle date-picker" type="text" id="operTimeStart" name="operTimeStart" style="padding: 1px;">
		     <span class="input-group-addon" style="padding: 3px;">	<i class="fa fa-calendar bigger-110"></i>	</span>
		     
		     <input class="input-middle date-picker" type="text" id="operTimeEnd" name="operTimeEnd" style="padding: 1px;">
		     <span class="input-group-addon" style="padding: 3px;">	<i class="fa fa-calendar bigger-110"></i>	</span>
		</div>
		<a href="javascript:void(0)" class="btn btn-default" id="search">Query</a>
		<a href="javascript:void(0)" class="btn btn-default" id="exportOptLog">Export</a>
	</form>
</div><br/>
<div id="paging" class="pagclass"></div>
</div>
</body>
</html>