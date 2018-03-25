/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 * 
 * @author lanyuan 2014-12-12
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
;
(function() {
	/**
	 * 
	 */
	CommonUtil = {
		/**
		 * CommonUtil.ajax(url, data,dataType,async) ajax同步请求 默认返回一个html内容,<br/>
		 * 例如：CommonUtil.ajax(url); <br/> 如果想返回dataType=json.
		 * CommonUtil.ajax(url,data,"json");async:默认为false <br/> <br/>
		 * 如果async=true 则需要自己去实现异步返回.. 例如:<br/>
		 * CommonUtil.ajax('XXX.shtml',null,'json',true) .success(function(res) {<br/>
		 * <br/> }).error(function(err) {<br/> <br/> });<br/>
		 * 
		 */
		ajax : function(url, data, dataType, async) {
			if (!CommonUtil.notNull(dataType)) {
				dataType = "html";
			}
			if (!CommonUtil.notNull(async)) {
				async = false;
			}
			var html = 'No result!';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date();
			} else {
				url = url + "?_t=" + new Date();
			}
			if (async) {

				return ly.ajax({
					type : "post",
					data : data,
					url : url,
					dataType : dataType,// 这里的dataType就是返回回来的数据格式了html,xml,json
					async : async,
					cache : false,// 设置是否缓存，默认设置成为true，当需要每次刷新都需要执行数据库操作的话，需要设置成为false
				});
			} else {

				ly.ajax({
					type : "post",
					data : data,
					url : url,
					dataType : dataType,// 这里的dataType就是返回回来的数据格式了html,xml,json
					async : async,
					cache : false,// 设置是否缓存，默认设置成为true，当需要每次刷新都需要执行数据库操作的话，需要设置成为false
					success : function(data) {
						html = data;
					}
				});
				return html;
			}

		},
		/**
		 * 判断某对象不为空..返回true 否则 false
		 */
		notNull : function(obj) {
			if (obj === null) {
				return false;
			} else if (obj === undefined) {
				return false;
			} else if (obj === "undefined") {
				return false;
			} else if (obj === "") {
				return false;
			} else if (obj === "[]") {
				return false;
			} else if (obj === "{}") {
				return false;
			} else if (obj.length === 0) {
				return false;
			} else {
				return true;
			}

		},

		/**
		 * 判断某对象不为空..返回obj 否则 ""
		 */
		notEmpty : function(obj) {
			if (obj === null) {
				return "";
			} else if (obj === undefined) {
				return "";
			} else if (obj === "undefined") {
				return "";
			} else if (obj === "") {
				return "";
			} else if (obj === "[]") {
				return "";
			} else if (obj === "{}") {
				return "";
			} else {
				return obj;
			}

		},
		loadingImg : function() {
			var html = '<div id="id_div_loading" class="alert alert-warning">' + '<button type="button" class="close" data-dismiss="alert">' + '<i class="ace-icon fa fa-times"></i></button><div style="text-align:center">' + '<img src="' + rootPath + '/images/loading.gif"/><div>' + '</div>';
			return html;
		},
		/**
		 * html标签转义
		 */
		htmlspecialchars : function(str) {
			var s = "";
			if (str == null || str.length == 0)
				return "";
			for (var i = 0; i < str.length; i++) {
				switch (str.substr(i, 1)) {
				case "<":
					s += "&lt;";
					break;
				case ">":
					s += "&gt;";
					break;
				case "&":
					s += "&amp;";
					break;
				case " ":
					if (str.substr(i + 1, 1) == " ") {
						s += " &nbsp;";
						i++;
					} else
						s += " ";
					break;
				case "\"":
					s += "&quot;";
					break;
				case "\n":
					s += "";
					break;
				default:
					s += str.substr(i, 1);
					break;
				}
			}
			return s;
		},
		/**
		 * in_array判断一个值是否在数组中
		 */
		in_array : function(array, string) {
			for (var s = 0; s < array.length; s++) {
				thisEntry = array[s].toString();
				if (thisEntry == string) {
					return true;
				}
			}
			return false;
		},
		btnlist : function() {
			var html = '<div id="but" class="doc-buttons">';
			html += '<span onclick="toBut(this)" id="span_addFun">';
			html += '<button type="button" id="addFun" name="addFun" class="btn btn-primary marR10">New</button></span>';
			html += '<span onclick="toBut(this)" id="span_editFun">';
			html += '<button type="button" id="editFun" name="editFun" class="btn btn-info marR10">Edit</button></span>';
			html += '<span onclick="toBut(this)" id="span_editFun">';
			html += '<button type="button" id="delFun" name="delFun" class="btn btn-danger marR10">Delete</button></span>';
			html += '<span onclick="toBut(this)" id="span_permissions">';
			html += '<button type="button" id="permissions" name="permissions" class="btn btn-grey marR10">Assign permissions</button></span>';
			html += '<span onclick="toBut(this)" id="span_upLoad">';
			html += '<button type="button" id="upLoad" name="upLoad" class="btn btn-primary marR10">Upload</button></span>';
			html += '<span onclick="toBut(this)" id="span_downLoad">';
			html += '<button type="button" id="downLoad" name="downLoad" class="btn btn-primary marR10">Download</button></span>';
			html += '<span onclick="toBut(this)" id="span_lyGridUp">';
			html += '<button type="button" id="lyGridUp" name="lyGridUp" class="btn btn-success marR10">Up</button></span>';
			html += '<span onclick="toBut(this)" id="span_lyGridDown">';
			html += '<button type="button" id="lyGridDown" name="lyGridDown" class="btn btn-grey marR10">Down</button></span>';
			html += '</div>';
			return html;
		},
		stampToTime : function(value) {
			return moment(value).format('YYYY-MM-DD HH:mm')
		},
		rate : function(value) {
			var v = parseFloat(value)
			if (isNaN(v) || !v) {
				return '0.00%';
			}
			return (v * 100).toFixed(2) + '%';
		},
		numberic : function(v) {
			var med, tmp_value, out_value = parseFloat(v);
			if (isNaN(out_value) || out_value === 0) {
				return "0.00";
			}
			med = 0;
			do {
				med += 2;
				tmp_value = out_value.toFixed(med);
			} while (parseFloat(tmp_value) === 0 && med < 4);
			out_value = tmp_value
			return out_value.toString().replace(/(\d{1,3})(?=(\d{3})+(?:\.))/g, "$1,");
		},
		integer : function(v) {
			var out_value = parseInt(v);
			if (isNaN(out_value)) {
				return "0";
			}
			return out_value.toString().replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, "$1,");
		},
		duration : function(v) {
			var d = new Date();
			return moment.duration(d.getTime() - v).humanize() + 'before'
		},
		clear_chart : function() {
			$("[data-highcharts-chart]").each(function() {
				$(this).empty();
			});
		},
		/**
		 * 导出表格数据 datas:表格数据 columnsTitle:表格头元素 fileName:下载文件名 注意与gridList组件配合使用
		 */
		exportExcel : function(datas, columnsTitle, fileName) {
			if (!this.notNull(datas) || datas.length == 0) {
				layer.msg('No data export!', {
					icon : 2
				});
				return;
			}
			var form = $("<form>");// 定义一个form表单
			form.attr("style", "display:none");
			form.attr("target", "");
			form.attr("method", "post");
			form.attr("action", rootPath + '/export.shtml');
			$("body").append(form);// 将表单放置在web中
			var input1 = $("<input>");
			input1.attr("type", "hidden");
			input1.attr("name", "headdata");
			input1.attr("value", JSON.stringify(columnsTitle));
			form.append(input1);
			var input2 = $("<input>");
			input2.attr("type", "hidden");
			input2.attr("name", "data");
			input2.attr("value", JSON.stringify(datas));

			form.append(input2);
			var input3 = $("<input>");
			input3.attr("type", "hidden");
			input3.attr("name", "fileName");
			input3.attr("value", fileName);
			form.append(input3);
			form.submit();// 表单提交
		},
		/**
		 * 排序
		 */
		sortObjArr : function(a, sortByParam) {

			var self = this;
			var ra = new Array();
			ra[0] = a[0];

			for (var i = 1; i < a.length; i++) {
				self.insertSortArray(ra, a[i], sortByParam);

			}
			/*
			 * var arr = new Array(); for(var j=0;j<top;j++){ }
			 */
			return ra;
		},
		insertSortArray : function(sortArr, insertItem, sortByParam) {
			var s1;
			var s2;
			var d = true;
			for (var i = 0; i < sortArr.length; i++) {
				s1 = sortArr[i][sortByParam];
				s2 = insertItem[sortByParam];
				if (s1 < s2) {
					for (var j = sortArr.length; j > i; j--) {
						sortArr[j] = sortArr[j - 1]
					}
					sortArr[i] = insertItem;
					d = false;
					break;
				}
			}
			if (d)
				sortArr.push(insertItem);
		},
		jsonObjLength : function(jsonObj) {// 统计json某一个对象的长度
			var Length = 0;
			for ( var item in jsonObj) {
				Length++;
			}
			return Length;
		},
		findJsonObjName : function(data, name) {// 要根据字符串名获取json数据的值
			if (!data || !name)
				return null;
			if (name.indexOf('.') == -1) {
				return data[name];
			} else {
				try {
					return new Function("data", "return data." + name + ";")(data);
				} catch (e) {
					return null;
				}
			}
		},
		formateNumber : function(num) {
			var formatStr = num + "";
			var dian = formatStr.indexOf(".");
			if (dian < 0) {
				return num;
			} else {
				var tailStr = formatStr.substring(dian + 1, formatStr.length);
				var prefixNum = formatStr.substring(0, dian);
				var tailNum = tailStr.substring(0, 2);
				if (tailNum != "00") {
					return Number(prefixNum + "." + tailNum);
				} else {
					tailNum = tailStr.substring(0, 3);
					if (tailNum != "000") {
						return Number(prefixNum + "." + tailNum);
					} else {
						tailNum = tailStr.substring(0, 4);
						if (tailNum != "0000") {
							return Number(prefixNum + "." + tailNum);
						}
						tailNum = tailStr.substring(0, 5);
						return Number(prefixNum + "." + tailNum);
					}
				}

			}
		},

		toDecimal : function(x) {
			var f = parseFloat(x);
			if (isNaN(f)) {
				return false;
			}
			var f = Math.round(x * 100) / 100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
			}
			while (s.length <= rs + 2) {
				s += '0';
			}
			return s;
		},
		onloadurl : function(id) {
			if (CommonUtil.notNull(id)) {
				var tb = $("#" + id);
				tb.html(CommonUtil.loadingImg());
				tb.load(rootPath + tb.attr("data-url"));
			} else {
				$("[data-url]").each(function() {
					var tb = $(this);
					tb.html(CommonUtil.loadingImg());
					tb.load(rootPath + tb.attr("data-url"));
				});
			}
		},
		paramToJson : function(url) {
			var reg_url = /^[^\?]+\?([\w\W]+)$/, reg_para = /([^&=]+)=([\w\W]*?)(&|$|#)/g, arr_url = reg_url.exec(url), ret = {};
			if (arr_url && arr_url[1]) {
				var str_para = arr_url[1], result;
				while ((result = reg_para.exec(str_para)) != null) {
					ret[result[1]] = result[2];
				}
			}
			return ret;
		},
		_getValueByName : function(data, name) {
			if (!data || !name)
				return null;
			if (name.indexOf('.') == -1) {
				return data[name];
			} else {
				try {
					return new Function("data", "return data." + name + ";")(data);
				} catch (e) {
					return null;
				}
			}
		}
	};
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S" : this.getMilliseconds()
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
	ly = {};
	ly.ajax = (function(params) {
		var pp = {
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					var obj = JSON.parse(XMLHttpRequest.responseText); 
					if(obj==null||obj==undefined){
						 layer.open({ 
							 type : 1, 
							 title : "Error!", 
							 area : [ '800px','400px' ], 
							 content : "<div id='layerError' style='color:red'>" + obj.message + "</div>"
						 });
					}else{
						layer.alert('Exception!', obj.message);
					}
				}
		};
		$.extend(pp, params);
		return $.ajax(pp);
	});
	ly.ajaxSubmit = (function(form, params) {// form 表单ID. params ajax参数
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var obj = JSON.parse(XMLHttpRequest.responseText); 
				if(obj==null||obj==undefined){
					 layer.open({ 
						 type : 1, 
						 title : "Error!", 
						 area : [ '800px','400px' ], 
						 content : "<div id='layerError' style='color:red'>" + obj.message + "</div>"
					 });
				}else{
					layer.alert('Exception!', obj.message);
				}
			}
		};
		$.extend(pp, params);
		return $(form).ajaxSubmit(pp);
	});
})();
// 表单json格式化方法……不使用&拼接
(function($) {
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()
		// millisecond
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
})(jQuery);