$(function() {
	// 异步加载所有菜单列表
	$("#editform").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form,{//验证新增是否成功
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						layer.msg('Update successfully!');
						grid.loadData();
						layer.close(pageii);
					} else {
						layer.alert('Edit failed!', 3);
					}
				}
			});
		},
		rules : {
			resKey : {
				required : true
			},
			resUrl : {
				required : true
			}
		},
		messages : {
			resKey : {
				required : "Menu KEY can not be empty"
			},
			resUrl : {
				required : "URL can not be empty"
			}
		},
		errorPlacement : function(error, element) {// 自定义提示错误位置
			$(".l_err").css('display', 'block');
			// element.css('border','3px solid #FFCCCC');
			$(".l_err").html(error.html());
		},
		success : function(label) {// 验证通过后
			$(".l_err").css('display', 'none');
		}
	});
});
function but(v){
	if(v.value==2){
		 showBut();
	}else{
		$("#divbut").css("display","none");
		$("#divbutshe").css("display","none")
	}
}
function toBut(b){
	var bt = $(b).find("button");
	$("#btnId").val(bt.attr("id"));
	$("#btnName").val(bt.attr("name"));
	$("#btnClass").val(bt.attr("class"));
	$("#btnValue").val(bt.html());
}
function showBut(){
	$("#divbut").css("display","block");
	$("#divbutshe").css("display","block");
	$("#but").html(CommonUtil.btnlist());
}
function byRes(id){
	var url = rootPath + '/system/resources/reslists.shtml';
	var data = CommonUtil.ajax(url, null,"json");
	if (data != null) {
		var h = "<option value='0'>------Top content------</option>";
		for ( var i = 0; i < data.length; i++) {
			if(id==data[i].id){
				h+="<option value='" + data[i].id + "' selected='selected'>"
								+ data[i].name + "</option>";
			}else{
				h+="<option value='" + data[i].id + "'>"+ data[i].name + "</option>";
			}
		}
		$("#parentId").html(h);
	} else {
		bootbox.alert("Get wrong menu information! Please contact administrator!");
	}
}