<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
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
 	 	    <label class="ui-label">借款人:</label><input name="loanPerson" class="easyui-box ui-text" style="width:100px;"> 
        </p>
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <form id="listForm" method="post">
     <table id="data-list"></table>
     </form>

  	 <!-- 用来请求的form -->	
  	 <form id="commonsForm" class="ui-form" method="post">
  	 </form>
  	 
  	 
  	 
  	 <!-- 上传excel文件开始    Form -->
     <div id="upload-excel-win" class="easyui-dialog" buttons="#editExcel"  title="导入数据" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:300px;">
     	<form id="uplaodExcelForm" class="ui-form" enctype="multipart/form-data" method="post">  
     		 <div class="ui-edit">
	     	   <div class="ftitle">导入数据</div>  
		            <div class="fitem">  
			           <label class="ui-label"><font color="red">*</font>选择文件:</label>
			           <input type="file" name="file" style="width:250px" data-options="required:true"><br/>
		           </div>
	         </div>
	         <br/>
     	</form>
     	 <div id="editExcel" class="dialog-button">  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-upload-submit">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-upload-close">取消</a>  
        </div>
  	 </div>
  	 <!-- 上传excel文件结束   Form --> 
  	 
  	
  	 <!-- 生成文件开始    Form -->
     <div id="create-words-win" class="easyui-dialog" buttons="#editPwdbtn" title="生成文件" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:300px;">
     	<form id="createWordsForm" class="ui-form" method="post">  
     		 <input class="hidden" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">生成文件</div>  
		            <div class="fitem">  
			           <label class="ui-label">文件类型:</label><select class="easyui-combobox" name="wordType"  style="width:250px" data-options="required:true" >
		            			<option value="0">全部</option>
		                    	<option value="1">生成授信阶段文件</option>
		                    	<option value="5">生成特别授信文件</option>
		                    	<option value="2">生成核保阶段文件</option>
		                    	<option value="3">生成入押阶段文件</option>
		                    	<option value="4">生成放款阶段文件</option>
		            		</select>
		           </div>
	         </div>
	         <br/>
     	</form>
     	 <div id="editPwdbtn" class="dialog-button">  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-create-submit">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-create-close">取消</a>  
        </div>
  	 </div>
  	 <!-- 生成文件结束   Form --> 
      	 
</div>

<script type="text/javascript" src="${msUrl}/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/insurance/insuranceManage.js"></script>
  </body>
</html>
