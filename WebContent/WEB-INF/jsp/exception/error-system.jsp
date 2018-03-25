<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head><title>Exception!</title></head>
<body>
<% Exception e = (Exception)request.getAttribute("ex"); %>
<H2 style="color: blue;">Business error: <%= e.getClass().getName()%></H2>
<hr />
<P>Error description:</P>
<span style="color: red;">
<%= e.getMessage()%></span>
<P>Error message:</P>
<span style="color: blue;">
<% e.printStackTrace(new java.io.PrintWriter(out)); %></span>
</body>
</html>