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
 	 	    <label class="ui-label">申请人姓名:</label><input name="applicationName" class="easyui-box ui-text" style="width:100px;">
 	 	    <label class="ui-label">申请贷款类型：</label><select class="easyui-combobox" name="LoanType" >
            			<option value=""></option>
                    	<option value="个体工商户">个体工商户</option>
                    	<option value="小企业主">小企业主</option>
                    	<option value="小企业">小企业</option>
            </select>
            <label class="ui-label">联系人地址: </label><input name="contAddress" class="easyui-box ui-text" style="width:100px;">
            
        </p>
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <form id="listForm" method="post">
     <table id="data-list"></table>
     </form>

     <!-- Edit Form 添加 -->
     <div id="edit-win" class="easyui-dialog" title="总表信息" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:600px;height:500px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <input class="hidden" name="deleted">
     		 <div class="ui-edit">
	     	   <div class="ftitle">上报信息</div>
	           <div class="fitem">  
	               <label>调查机构名称:</label>  
	               <input class="easyui-validatebox" type="text" name="nickName" data-options="required:true,validType:'length[1,10]'">
	              
	           </div>
	            
	           <div class="fitem">  
	               <label>调查人姓名:</label>  
	               <input class="easyui-validatebox" type="text" name="nickName" data-options="required:true,validType:'length[1,10]'">
	               <label>手机:</label>  
	               <input class="easyui-validatebox" type="text" name="nickName" data-options="required:true,validType:'length[1,10]'">
	           </div>
	           
	           
	            <div class="ftitle">申请人信息</div>
	            <div class="fitem">  
	               <label>借款申请人名称:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input> 
	           </div>
	           
	            <div class="fitem">  
	               <label>申请金额:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input>
	               <label>申请期限:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input>  
	           </div>
	           
	            <div class="fitem">  
	                <label class="ui-label">申请贷款类型：</label><select class="easyui-combobox" name="LoanType" >
            			<option value=""></option>
                    	<option value="个体工商户">个体工商户</option>
                    	<option value="小企业主">小企业主</option>
                    	<option value="小企业">小企业</option>
            		</select>
	           </div>
	           
	           
	           <div class="fitem">  
	               <label>紧急联系人（限亲属，非保证人）:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input>
	               <label>紧急联系人电话:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input>  
	           </div>
	           
	           <div class="fitem">  
	               <label>紧急联系人地址:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input>
	               <label>关系:</label>  
	               <input id="birthday" type="text" name="birthday" class="easyui-validatebox"></input>  
	           </div>
	           
	            <div class="ftitle">准入核查</div>
	            
	            <div class="fitem">  
	               <label class="ui-label">淘宝三钻以上：</label><select class="easyui-combobox" name="LoanType" >
            			<option value=""></option>
                    	<option value="是">是</option>
                    	<option value="否">否</option>
                    	
            		</select>
            		 <label class="ui-label">其他平台商户有营业执照：</label><select class="easyui-combobox" name="LoanType" >
            			<option value=""></option>
                    	<option value="是">是</option>
                    	<option value="否">否</option>
            		</select>
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
<script type="text/javascript" src="${msUrl}/js/ux/bus/busLoan.js"></script>
  </body>
</html>
