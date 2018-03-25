//单独验证某一个input  class="checkpass"
jQuery.validator.addMethod("checkacc", function(value, element) {
	return this.optional(element)
			|| ((value.length <= 30) && (value.length >= 3));
}, "Account is consist of 3 to 30 characters");
$(function() {
	$("#addform").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						layer.msg('Add successfully!');
						grid.loadData();
						layer.close(pageii);
					} else {
						layer.alert('Add failed!', data);
					}
				}
			});
		},
		rules : {
			"userFormMap.accountName" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : rootPath + '/system/user/isExist.shtml?FormMap=UserFormMap',
					data : {
						accountName : function(){
							return $("#add_accountName").val();
						}
					}
				}
			}
		},
		messages : {
			"userFormMap.accountName" : {
				required : "Please input account",
				remote : "Account exists"
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
