<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@include file="/common/common.jspf"%>
     <script src="${ctx}/js/websocket/head.js" ></script>
     <script type="text/javascript">var wimadress="${pd.WIMIP}:${pd.WIMPORT}";</script>
	 <script type="text/javascript">var oladress="${pd.OLIP}:${pd.OLPORT}";</script>
	 <link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	 <link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/css/websocket.css" />
	 <script type="text/javascript" src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	 <script type="text/javascript" src="plugins/websocketInstantMsg/websocket.js"></script>
<script type="text/javascript">
var tab = null;
var accordion = null;
var tree = null;
var tabItems = [];
$(document).ready(function(){  
	$("[to-url]").each(function () {
		$(this).bind("click",function(){
				var nav = $(this).attr("nav-n");
				var sn = nav.split(",");
				 tab.addTabItem({
			         tabid: sn[1],
			         text: sn[0],
			         url: rootPath+$(this).attr("to-url")
			     });
		});
	});
	//布局
    $("#layout1").ligerLayout({height: '100%',space:4, onHeightChanged: f_heightChanged });

    var height = $(".l-layout-center").height();
    
    $("#sidebar").height(height);
    //Tab
   tab = $("#framecenter").ligerTab({
        height: height
    });
   var lf = $('#submenu');
	if (lf.length != 0) {
		var submenu = lf.eq(0).find('li').eq(0).find('ul');
		if (submenu.length != 0) {
			submenu.find('li').filter(':first').children('a')
			.click();
		} else {
			lf.eq(0).find('li').removeClass('active').filter(':first')
					.addClass('active').children('a')
					.click();

		}
	}
});
function f_heightChanged(options)
{  
    if (tab)
        tab.addHeight(options.diff);
    if (accordion && options.middleHeight - 24 > 0)
        accordion.setHeight(options.middleHeight - 24);
}
function f_addTab(tabid, text, url)
{
    tab.addTabItem({
        tabid: tabid,
        text: text,
        url: url
    });
}
</script>
</head>

<body class="skin-1">
	<!-- #section:basics/navbar.layout -->
	<div id="navbar" class="navbar navbar-default">

		<div class="navbar-container" id="navbar-container">
			<!-- #section:basics/sidebar.mobile.toggle -->
			<button type="button" class="navbar-toggle menu-toggler pull-left"
				id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>

			<!-- /section:basics/sidebar.mobile.toggle -->
			<div class="navbar-header pull-left">
				<!-- #section:basics/navbar.layout.brand -->
				<a  class="navbar-brand"> <small> <i
						class="fa fa-cog"></i> Remote Monitoring and Management System
				</small>
				</a>
			</div>
			<div  class="navbar-buttons navbar-header" style="padding-top: 6px;cursor: pointer;margin-left:40%" onclick="creatw();">
			   <img  alt="聊天室" src="${ctx}/images/myChat.jpg" style="width:30px;height:30px">
			</div>
			<div role="navigation" class="navbar-buttons navbar-header pull-right">
				 <div id="user_info" class="navbar-brand"></div>	<a href="${ctx}/logout.shtml" class="navbar-brand"> <small>Logout</small></a>
			</div>
		</div>

	</div>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">

		<!-- #section:basics/sidebar -->
		<div id="sidebar" class="sidebar responsive">
			<ul class="nav nav-list">
				<c:forEach var="key" items="${list}" varStatus="s">
					<c:if test="${key.parentId eq 0}">
						<li <c:if test="${s.index==0}">class="open"</c:if>><a href="#"
							class="dropdown-toggle"><i class="menu-icon fa ${key.icon}"></i><span class="menu-text"> ${key.name} </span> <b
								class="arrow fa fa-angle-down"></b>
						</a> <b class="arrow"></b>
							<ul class="submenu" id="submenu">
								<c:forEach var="kc" items="${key.children}" varStatus="ks">
										<li <c:if test="${ks.index==0}">class="active"</c:if>><a
											href="javascript:void(0)" to-url="${kc.resUrl}${fn:indexOf(kc.resUrl,'?') > -1 ? '&':'?'}resId=${kc.id}"
											nav-n="${kc.name},${kc.resKey}" class="dropdown-toggle"> <i
												class="menu-icon fa fa-caret-right"></i>${kc.name}
										</a></li>
								</c:forEach>
							</ul></li>
					</c:if>
				</c:forEach>
			</ul>
			<!-- /.nav-list -->

			<!-- #section:basics/sidebar.layout.minimize -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left"
					data-icon1="ace-icon fa fa-angle-double-left"
					data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>

			<!-- /section:basics/sidebar.layout.minimize -->
		</div>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			 <div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
		        <div position="center" id="framecenter"> 
		            <!-- <div tabid="home" title="我的主页">
		            </div>  -->
		        </div> 
		        
		    </div>
		</div>
		<!-- /.navbar-container -->
	</div>
	<!-- /.main-container -->
</body>
</html>