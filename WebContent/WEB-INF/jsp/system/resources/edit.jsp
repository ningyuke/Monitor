<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="editform" name="editform" class="form-horizontal" method="post"
		action="${pageContext.request.contextPath}/system/resources/editEntity.shtml">
			<input type="hidden" value="${id}" name="id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">Menu name</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="Please input menu name" name="name" id="name" value="${name}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">Menu KEY</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="Please input menu KEY" name="resKey" id="resKey" value="${resKey}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">Menu URL</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="Please input menu URL" name="resUrl" id="resUrl" value="${resUrl}">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">Upper menu</label>
				<div class="col-sm-9">
					<select id="parentId" name="parentId" class="form-control m-b">
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">Menu type</label>
				<div class="col-sm-9">
					<div class="btn-group m-r" id="typeId">
						<select id="type" name="type" class="select2-offscreen"
							tabindex="-1" onchange="but(this)">
							<option value="0">------  Content ------</option>
							<option value="1">--------  Menu -------</option>
							<option value="2">-------  Button ------</option>
						</select>
					</div>
				</div>
			</div>
			<div class="form-group" id="divbut" style="display: none;">
				<label class="col-sm-3 control-label">Selection</label>
				<div class="col-sm-9">
				<div id="but" class="doc-buttons">
				</div><font color="red">User-defined HTML code</font>
				</div>
			</div>
			<div class="form-group" id="divbutshe" style="display: none;">
				<label class="col-sm-3 control-label">Button settings</label>
				<div class="col-sm-9">
				<font color="red" class="col-sm-12">
						<label class="col-sm-2 control-label">id=</label><input type="text" class="col-xs-4" id="btnId" name="btnId" value="${fn:split(btn,',')[0]}">
						<label class="col-sm-2 control-label">name=</label><input type="text" class="col-xs-4" id="btnName" name="btnName" value="${fn:split(btn,',')[1]}">
					</font>
				<font color="red" class="col-sm-12" style="margin-top: 5px;">
						<label class="col-sm-2 control-label">class=</label><input type="text" class="col-xs-4" id="btnClass" name="btnClass" value="${fn:split(btn,',')[2]}">
						<label class="col-sm-2 control-label">value=</label><input type="text" class="col-xs-4" id="btnValue" name="btnValue" value="${fn:split(btn,',')[3]}">
					</font>
				</div>
			</div>
<div class="form-group">
				<label class="col-sm-3 control-label">Icon</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="Please input icon" name="icon" id="icon" value="${icon}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Hide</label>
				<div class="col-sm-9">
				<label class="pull-left inline" style="margin-top:5px;">
					<input id="gritter-light" type="checkbox" <c:if test="${ishide eq 1}"> checked="checked"</c:if> name="ishide" id="ishide" class="ace ace-switch ace-switch-5" value="1">
					<span class="lbl middle"></span>
					</label>
				</div>
			</div>			<div class="form-group">
				<label class="col-sm-3 control-label">Menu description</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="Please input menu description" name="description" id="description" value="${description}">
				</div>
			</div>
			
		</div>
<%@include file="/common/buttom.jspf"%>
	</section>
	</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/resources/edit.js"></script>	
<script type="text/javascript">
	$("#type").val("${type}");
	if("${type}"=="2"){
		 showBut();
	}
	byRes("${parentId}");
</script>