<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#forSelect").dblclick(function() {
		selected();
	});
	$("#unSelect").dblclick(function() {
		unselected();
	});
});
function selected() {
	var selOpt = $("#forSelect option:selected");

	selOpt.remove();
	var selObj = $("#unSelect");
	selObj.append(selOpt);

	var selOpt = $("#unSelect")[0];
	ids = "";
	for (var i = 0; i < selOpt.length; i++) {
		ids += (selOpt[i].value  + ",");
	}

	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	$('#txtSelect').val(ids);
}

function selectedAll() {
	var selOpt = $("#forSelect option");

	selOpt.remove();
	var selObj = $("#unSelect");
	selObj.append(selOpt);

	var selOpt = $("#unSelect")[0];
	ids = "";
	for (var i = 0; i < selOpt.length; i++) {
		ids += (selOpt[i].value  + ",");
	}

	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	$('#txtSelect').val(ids);
}

function unselected() {
	var selOpt = $("#unSelect option:selected");
	selOpt.remove();
	var selObj = $("#forSelect");
	selObj.append(selOpt);

	var selOpt = $("#unSelect")[0];
	ids = "";
	for (var i = 0; i < selOpt.length; i++) {
		ids += (selOpt[i].value + ",");
	}
	
	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	$('#txtSelect').val(ids);
}

function unselectedAll() {
	var selOpt = $("#unSelect option");
	selOpt.remove();
	var selObj = $("#forSelect");
	selObj.append(selOpt);

	$('#txtSelect').val("");
}
</script>
<div class="form-group">
<input id="txtSelect" type="hidden" value="${txtSelect}"
			name="txtSelect" />
	<label for="host" class="col-sm-3 control-label">${lableName}</label>
	<div class="col-sm-9">
		<table class="tweenBoxTable" name="t_tweenbox"
			id="t_tweenbox" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td>Selected</td>
					<td></td>
					<td>Unselected</td>
				</tr>
				<tr>
					<td width="200"><select id="unSelect" multiple="multiple"
						class="input-large" name="unSelect"
						style="height: 150px; width: 150px">
						<c:forEach items="${useSelect}" var="key">
						<option value="${key.id}">${key.name}</option>
						</c:forEach>
					</select></td>
					<td align="center">
						<div style="margin-left: 5px; margin-right: 5px">
							<button onclick="selectedAll()" class="btn btn-primary"
								type="button" style="width: 50px;" title="全选">&lt;&lt;</button>
						</div>
						<div style="margin-left: 5px; margin-right: 5px; margin-top: 5px;">
							<button onclick="selected()" class="btn btn-primary"
								type="button" style="width: 50px;" title="选择">&lt;</button>
						</div>
						<div style="margin-left: 5px; margin-right: 5px; margin-top: 5px;">
							<button onclick="unselected()" class="btn btn-primary"
								type="button" style="width: 50px;" title="取消">&gt;</button>
						</div>
						<div style="margin-left: 5px; margin-right: 5px; margin-top: 5px">
							<button onclick="unselectedAll()" class="btn btn-primary"
								type="button" style="width: 50px;" title="全取消">&gt;&gt;</button>
						</div>
					</td>
					<td width="200"><select id="forSelect"
						multiple="multiple" class="input-large"
						style="height: 150px; width: 150px">
						<c:forEach items="${unSelect}" var="key">
						<option value="${key.id}">${key.name}</option>
						</c:forEach>
					</select></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>