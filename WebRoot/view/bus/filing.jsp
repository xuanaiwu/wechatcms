<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
<div class="warp easyui-panel" data-options="border:false">
	<!-- Search panel start -->
 	 <div class="easyui-panel ui-search-panel" title="信息查询" data-options="striped: true,collapsible:true,iconCls:'icon-search'">  
 	 <form id="searchForm">
 	 	<p class="ui-fields">
 	 	    <label class="ui-label">姓名:</label><input name="lName" class="easyui-box ui-text" style="width:100px;"> 
        </p>
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <form id="listForm" method="post">
     <table id="data-list"></table>
     </form>
     
     
      <!-- Edit Form 修改busfile -->
     <div id="edit-win" class="easyui-dialog" title="建档信息" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:549px;height:300px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <input class="hidden" type="text" name="lUserName">
     		 <input class="hidden" type="text"  name="lUId">
			   <div class="fitem">  
			          <label>姓名:</label>  
			          <input class="easyui-validatebox" type="text" name="lName"  data-options="required:true,validType:'length[1,100]'">
			          <label>身份证:</label>  
			          <input class="easyui-numberbox" type="text" name="lIdCard" data-options="required:true">
			          <label>电话:</label>  
			          <input class="easyui-numberbox" type="text" name="lTelPhone" data-options="required:true">
			          <label>当前状态:</label>  
			          <select class="easyui-combobox" name="lStatus" style="width:139px" data-options="required:true" >
		                    <option value="建档">建档</option>
		                    <option value="授信申报">授信申报</option>
		                    <option value="放款">放款</option>
		                    <option value="贷后台帐">贷后台帐</option>
		            </select> 
			  </div> 
     	</form>
  	 </div> 
  	 
  	 <!-- 用来请求的form -->	
  	 <form id="commonsForm" class="ui-form" method="post">
  	 </form> 
      	 
</div>

<script type="text/javascript" src="${msUrl}/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/bus/filing.js"></script>
  </body>
</html>
