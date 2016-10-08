<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
<div class="warp easyui-panel" data-options="border:false">
	<!-- Search panel start -->
 	 <div class="easyui-panel ui-search-panel" title="Search box" data-options="striped: true,collapsible:true,iconCls:'icon-search'">  
 	 <form id="searchForm">
 	 	<p class="ui-fields">
 	 	    <label class="ui-label">邮箱:</label><input name="email" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">昵称: </label><input name="nickName" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">用户类型：</label><select class="easyui-combobox" name="kind" >
            			<option value=""></option>
                    	<option value="个人">个人</option>
                    	<option value="企业">企业</option>
            </select>
        </p>
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <form id="listForm" method="post">
     <table id="data-list"></table>
     </form>

     <!-- Edit Form -->
     <div id="edit-win" class="easyui-dialog" title="添加/修改" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:500px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <input class="hidden" name="deleted">
     		 <div class="ui-edit">
	     	   <div class="ftitle">用户信息</div>    
	           <div class="fitem">  
	               <label>电子邮箱:</label>  
	               <input class="easyui-validatebox" type="text" name="email" data-options="required:true,validType:'email'">
	           </div>  
	            
	           <div class="fitem">  
	               <label>别名:</label>  
	               <input class="easyui-validatebox" type="text" name="nickName" data-options="required:true,validType:'length[1,10]'">
	           </div>
	           
	           
	            <div class="fitem">  
	               <label>用户性别:</label>  
	               <select class="easyui-combobox" name="gender" data-options="required:true">
                    	<option value="男" selected="selected">男</option>
                    	<option value="女">女</option>
                   	</select>
	           </div>
	           
	           <div class="fitem">  
	               <label>用户类型:</label>  
	               <select class="easyui-combobox" name="kind" data-options="required:true">
                    	<option value="个人" selected="selected">个人</option>
                    	<option value="企业">企业</option>
                   	</select>
	           </div>
	           
	            <div class="fitem">  
	               <label>生日:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-datebox"></input> 
	           </div>
	           
	            <div class="fitem">  
	               <label>地址:</label>  
	               <input id="addr" type="text" name="addr"  class="easyui-validatebox"></input> 
	           </div>
	           
	           
	            <div class="fitem">  
	               <label>电话:</label>  
	               <input id="phone" type="text" name="phone"  class="easyui-validatebox"   data-options="validType:'isNum'" ></input> 
	           </div>
	           
	             <div class="fitem">  
	               <label>状态:</label>  
	               <select class="easyui-combobox" name="state" data-options="required:true">
                    	<option value="0" selected="selected">可用</option>
                    	<option value="1">禁用</option>
                   	</select>
	           </div> 
	           
	           <div class="fitem">  
	               <label>备注:</label>  
	               <textarea class="easyui-validatebox" data-options="length:[0,100]" name="memo"></textarea>
	           </div>
	           
	          
	         </div>
     	</form>
  	 </div> 
  	 
  	 <!-- Edit Password Form -->
     <div id="edit-pwd-win" class="easyui-dialog" buttons="#editPwdbtn" title="Edit PassWord" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:300px;">
     	<form id="pwdForm" class="ui-form" method="post">  
     		 <input class="hidden" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">用户信息</div>    
	           <div class="fitem">  
	               <label>电子邮箱:</label>  
	               <input class="easyui-validatebox" type="text" readonly="readonly" name="email" data-options="required:true,validType:'email'">
	           </div>  
	           <div class="fitem">  
	              <label>旧密码:</label>  
	              <input id="oldPwd" name="oldPwd" type="password" class="easyui-validatebox"/>
	           </div>
	            <div class="fitem">  
	               <label>新密码:</label>  
	               <input id="newPwd" name="newPwd" type="password" class="easyui-validatebox" data-options="required:true" />
	           </div> 
	           <div class="fitem">  
	               <label>再次输入:</label>  
	              <input id="rpwd" name="rpwd" type="password" class="easyui-validatebox"   required="required" validType="equals['#newPwd']" />
	           </div> 
	         </div>
     	</form>
     	 <div id="editPwdbtn" class="dialog-button">  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-submit">提交</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-close">关闭</a>  
        </div>
  	 </div> 
  	
</div>

<script type="text/javascript" src="${msUrl}/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/sys/sysUser.js"></script>
  </body>
</html>
