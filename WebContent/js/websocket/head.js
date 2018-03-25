var USER_ID;
var user = "admin";	//用于即时通讯（ 当前登录用户）

$(function(){
	$.ajax({
		type: "POST",
		url:rootPath +'/head/getUname.shtml?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		cache: false,
		success: function(data){
			 $.each(data.list, function(i, list){
				 $("#user_info").html('<small>Welcome:</small> '+list.name+'');
				 user = list.name;
				 USER_ID = list.id;//用户ID
				 online();//连接在线管理
			 });
		}
	});
});

//在线管理
var websocket;
function online(){
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI('ws://'+oladress));
		
		websocket.onopen = function() {
			//连接成功
			websocket.send('[join]'+user);
		};
		websocket.onerror = function() {
			//连接失败
		};
		websocket.onclose = function() {
			//连接断开
		};
		//消息接收
		websocket.onmessage = function(message) {
			var mss=message.data;
			if(mss.indexOf("{")==-1){
				mss='{"type":"nothing","data":"'+mss+'"}';
			}
			var message = JSON.parse(mss);
			if (message.type == 'count') {
				userCount = message.msg;
			}else if(message.type == 'goOut'){
				$("body").html("");
				goOut("This user has logined through other terminal. You can not login temporarily");
			}else if(message.type == 'thegoout'){
				$("body").html("");
				goOut("You are forced offline by administrator");
			}else if(message.type == 'changeMenu'){
				window.location.href=rootPath+'/main/yes.shtml';
			}else if(message.type == 'userlist'){
				userlist = message.list;
			}
		};
	}
}

//在线总数
var userCount = 0;
function getUserCount(){
	websocket.send('[count]'+user);
	return userCount;
}
//用户列表
var userlist = "";
function getUserlist(){
	websocket.send('[getUserlist]'+user);
	return userlist;
}
//强制下线
function goOut(msg){
	alert(msg);
	window.location.href=rootPath+"/logout.shtml";
}
//强制某用户下线
function goOutUser(theuser){
	websocket.send('[goOut]'+theuser);
}