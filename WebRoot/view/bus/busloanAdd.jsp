<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
	<form id="editForm" class="ui-form" method="post">
	<input class="hidden" type="text" name="uName" value="${user.nickName}">
    <input class="hidden" type="text"  name="uId" value="${user.id}">  
		<div id="infoAdd" class="easyui-tabs" style="margin-left:5px">   
		    <div title="建档" style="overflow:auto;padding:20px;">   
		        <div class="ftitle">建档信息</div>
			           <div class="fitem">  
			               <label>姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="name"   data-options="required:true,validType:'length[1,100]'">
			               <label>身份证:</label>  
			               <input class="easyui-validatebox" type="text" name="idcard" data-options="required:true,validType:'length[18,18]'">
			               <label>电话:</label>  
			               <input class="easyui-validatebox" type="text" name="telphone" data-options="required:true,validType:'length[11,11]'">
			           </div>    
		    </div>   
		    <div title="授信申报"  style="overflow:auto;padding:20px;">   
		         <div class="ftitle">机构信息</div>
			           <div class="fitem">  
			               <label>调查机构名称:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyOrgName"   data-options="required:true,validType:'length[1,200]'">
			               <label>调查人姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPersonName" data-options="required:true,validType:'length[1,100]'">
			               <label>手机:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPhone" data-options="required:true,validType:'length[1,100]'">
			           </div>    
		    </div>   
		    <div title="放贷"  style="overflow:auto;padding:20px;display:none;">   
		        tab3    
		    </div>
		    
		    <div title="贷后台帐"  style="overflow:auto;padding:20px;display:none;">   
		        tab4    
		    </div>
		       
		</div>
	</form>  
  </body>
</html>
