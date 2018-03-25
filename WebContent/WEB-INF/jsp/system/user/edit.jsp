<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
<form id="editform" name="editform" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/system/user/editEntity.shtml">
	<input type="hidden" class="form-control checkacc" value="${id}"
		name="userFormMap.id" id="id">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">Account</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="Please input account" value="${accountName}"
						name="userFormMap.accountName" id="accountName"
						readonly="readonly">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">User name</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" placeholder="Please input user name"
						name="userFormMap.name" value="${name}">
				</div>
			</div>
			<div id="selGroup"
				data-url="/system/role/seletRole.shtml?roleFormMap.userId=${id}&lableName=角色组"></div>
			<div class="form-group">
					<label class="col-sm-3 control-label">Enabled</label>
					<div class="col-sm-9">
					<label class="inline"><input id="id-button-borders" <c:if test="${locked eq 1}"> checked="checked"</c:if> onclick="checkstate(this)" type="checkbox" class="ace ace-switch ace-switch-6"><span class="lbl middle"></span></label>
					<input type="hidden"
							name="userFormMap.locked" id="locked" value="${locked}">
				</div>
				</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Description</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="Please input account description"
						value="${description}" name="userFormMap.description"
						id="description">
				</div>
			</div>
		</div>
		<%@include file="/common/buttom.jspf"%>
	</section>
</form>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/system/user/edit.js"></script>
<script type="text/javascript">
function checkstate(obj){
	if(obj.checked){
		$("#locked").val("1")
	}else{
		$("#locked").val("0")
	}
}
	onloadurl("selGroup");
</script>