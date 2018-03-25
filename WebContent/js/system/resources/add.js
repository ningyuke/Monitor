$(function() {
	// 异步加载所有菜单列表
	$("#addform").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			var  data = $('#add_data_form').serializeJSON();
			var  but = $('#addform').serializeJSON();
			var blist = but.btn;
			var d='';
			for(var b in blist){
				d+=JSON.stringify(blist[b])+","
			}
			var b = {buttom:"["+d+"]"};
			data=$.extend(data, b );
			ly.ajaxSubmit(form,{//验证新增是否成功
				type : "post",
				dataType : "json",
				data:data,
				success : function(data) {
					if (data == "success") {
						layer.confirm('Add successfully! Close window!', function(index){
							grid.loadData();
							if(parent.configBtn)
							parent.configBtn(''+$("#resKey").val());
							layer.close(pageii);
							layer.close(index);
						}); 
					} else {
						layer.alert('Add failed!', 3);
					}
				}
			});
		},
		rules : {
			"resFormMap.name" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : rootPath + '/system/resources/isExist.shtml?FormMap=ResFormMap',
					data : {
						name : function() {
							return $("#name").val();
						}
					}
				}
			},
			"resFormMap.resKey" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : rootPath + '/system/resources/isExist.shtml?FormMap=ResFormMap',
					data : {
						resKey : function() {
							return $("#resKey").val();
						}
					}
				}
			},
			"resFormMap.resUrl" : {
				required : true
			}
		},
		messages : {
			"resFormMap.name" : {
				required : "Menu name can not be empty",
				remote : "Menu name exsits"
			},
			"resFormMap.resKey" : {
				required : "Menu KEY can not be empty",
				remote : "Menu KEY exists"
			},
			"resFormMap.resUrl" : {
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
	var url = rootPath + '/system/resources/reslists.shtml';
	var data = CommonUtil.ajax(url, null,"json");
	if (data != null) {
		var h = "<option value='0'>------Top content------</option>";
		for ( var i = 0; i < data.length; i++) {
			h+="<option value='" + data[i].id + "'>"+ data[i].name + "</option>";
		}
		$("#parentId").html(h);
	} else {
		layer.msg("Get wrong menu information! Please contact administrator!");
	}
	$("#level").val($("#mytable tr").length);
	var inurl = parent.$(".in_url").val();
	if(CommonUtil.notNull(inurl)){
		$("#add_data_form #name").val(parent.$("#name").val());
		$("#add_data_form #name").attr("readonly","readonly");
		var key ="ckey_"+parent.$("#code").val();
		$("#add_data_form #resKey").val(key);
		$('#add_data_form #resKey').attr("readonly","readonly");
		if(inurl.indexOf("resKey=")==-1){
			if(inurl.indexOf("?")>-1){
				inurl+="&resKey="+key;
			}else{
				inurl+="?resKey="+key;
			}
		}
		$('#resUrl').attr("readonly","readonly");
		$("#resUrl").val(inurl);
	}
});
function but(v){
	if(v.value==2){
		 showBut();
	}else if(v.value==1){
		$("#type_but").css("display","initial");
	}else{
		$("#type_but").css("display","none");
		$("#divbut").css("display","none");
		$("#divbutshe").css("display","none")
	}
}
function type_but(v){
	if(v.checked){
		showBut();
	}else{
		$("#divbut").css("display","none");
		$("#divbutshe").css("display","none")
	}
	
}
function toBut(b){
	var bt = $(b).find("button");
	var bid = "#"+bt.attr("id")+"_div_but";
	if($("#type").val()=="2"){
		$("#addFun_div_but #btnId").val(bt.attr("id"));
			$("#addFun_div_but #btnName").val(bt.attr("name"));
			$("#addFun_div_but #btnClass").val(bt.attr("class"));
			$("#addFun_div_but #btnValue").val(bt.html());
	}else if($(bid).length>0){
		if(bid=="#addFun_div_but"){
			$(bid+" #btnId").val(bt.attr("id"));
			$(bid+" #btnName").val(bt.attr("name"));
			$(bid+" #btnClass").val(bt.attr("class"));
			$(bid+" #btnValue").val(bt.html());
		}else{
			$(bid).remove();
		}
	}else{
		var s = $("legend[id*='_legend']").length;
		var h = '<div class="col-sm-9" id="'+bt.attr("id")+'_div_but" style="float: right;">';
		h +=$("#addFun_div_but").html();
		h +="</div>";
		$("#divbutshe").append(h);
		$(bid+" #btnUrl").val($("#addFun_div_but #btnUrl").val());
		$(bid+" #btnUrl").attr("name","btn["+s+"][btnUrl]");
		$(bid+" #btnId").val(bt.attr("id"));
		$(bid+" #btnId").attr("name","btn["+s+"][btnId]");
		$(bid+" #btnName").val(bt.attr("name"));
		$(bid+" #btnName").attr("name","btn["+s+"][btnName]");
		$(bid+" #btnClass").val(bt.attr("class"));
		$(bid+" #btnClass").attr("name","btn["+s+"][btnClass]");
		$(bid+" #btnValue").val(bt.html());
		$(bid+" #btnValue").attr("name","btn["+s+"][btnValue]");
		$(bid+" #addFun_legend").html("按扭  "+s)
		$(bid+" #addFun_legend").attr("id",bt.attr("id")+"_legend");
	}
}
function showBut(){
	$("#divbut").css("display","block");
	$("#divbutshe").css("display","block");
	$("#but").html(CommonUtil.btnlist());
}