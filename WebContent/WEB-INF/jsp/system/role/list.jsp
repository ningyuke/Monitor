<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="htmlBody">
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/system/role/list.js"></script>
</head>
<body>
	<div class="page-content">
		<div class="m-b-md">
			<form class="form-inline" name="form" id="searchForm"
				name="searchForm">
				<div class="form-group">
					<label class="control-label"> <span
						class="h4 font-thin v-middle">Role name:</span>
					</label> <input class="input-medium ui-autocomplete-input" id="name"
						name="name">
				</div>
				<a href="javascript:void(0)" class="btn btn-default" id="search">Query</a>
			</form>
		</div>
		<div class="doc-buttons" style="padding: 10px 0;">
			<c:forEach items="${res}" var="key">
				<button type="button" id="${fn:split(key.btn,',')[0]}" name="${fn:split(key.btn,',')[1]}" class="${fn:split(key.btn,',')[2]}">${fn:split(key.btn,',')[3]}</button>
			</c:forEach>
		</div>
		<div id="paging" class="pagclass"></div>
		</div>
</body>
</html>