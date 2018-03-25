<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		var zTree;
		var setting = {
			check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
			callback : {
				onCheck : onCheck,
			}
		};
		function onCheck(e, treeId, treeNode) {
			var sNodes = zTree.getCheckedNodes(true);
			var resId = new Array();
			for (var i = 0; i < sNodes.length; i++) {
				resId.push(sNodes[i].id);
			}
			;
			$("#resId").val(resId.join(','));
		}

		var res = CommonUtil.ajax(rootPath + '/system/resources/jsonlist.shtml', {
			FormMap : "ResFormMap"
		}, 'json');
		for (var i = 0; i < res.length; i++) {
			delete res[i].icon;
			delete res[i].description
			delete res[i].resUrl
			delete res[i].type
		}
		zTree = $.fn.zTree.init($("#pztree"), setting, res);
		//zTree.expandAll(true);
		zTree.setting.check.chkboxType = {
			"Y" : "ps",
			"N" : "s"
		};

		CommonUtil.ajax(rootPath + '/system/resources/jsonlist.shtml', {
			"roleId" : "${param.roleId}",
			"mapper_id" : "ResourcesMapper.findRes"
		}, 'json', true).success(function(json) {
			var resId = new Array();
			for (index in json) {
				if (json[index].id != undefined) {
					zTree.checkNode(zTree.getNodeByParam("id", json[index].id), true);
					resId.push(json[index].id);
				}
			}
			;
			$("#resId").val(resId);
		}).error(function(err) {

		});
		$("#subsucess").bind("click", function() {
			var sNodes = zTree.getCheckedNodes(true);
			if (sNodes == undefined) {
				layer.alert("Please select object!", {
					icon : 3
				});
			} else if (sNodes.length == 0) {
				layer.alert("Please select object!", {
					icon : 3
				});
			} else {
				var resId = "";
				for (var i = 0; i < sNodes.length; i++) {
					resId += sNodes[i].id + ",";
				};
			var d = CommonUtil.ajax('${pageContext.request.contextPath}/system/resources/addRoleRes.shtml', {
					"roleId" : "${param.roleId}",
					"resId" : resId
				}, 'json')
			if (d == "success") {
				layer.alert('Distribute successfully!', 3);
			} else {
				layer.alert(d, {
					icon : 2
				});
			};
			}
		});
	});
</script>
<ul id="pztree" class="ztree"></ul>
<%@include file="/common/buttom.jspf"%>