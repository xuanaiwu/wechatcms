<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
	<!-- 中间内容页面 -->
	<div style="margin-top:20px;margin-left:20px;">
			<h3>系统提示:</h3>
			<ul>
				<li>${message} &nbsp;<a href="javascript:" onclick="history.go(-1);">返回</a></li> 
			</ul>
	</div>	
	
</body>
</html>
