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
     
     
     
  	 
  	 <!-- 用来请求的form -->	
  	 <form id="commonsForm" class="ui-form" method="post">
  	 </form>
  	 
  	 
  	 
  	  <!-- 生成文书开始    Form -->
     <div id="create-words-win" class="easyui-dialog" buttons="#editPwdbtn" title="生成Words" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:300px;">
     	<form id="createWordsForm" class="ui-form" method="post">  
     		 <input class="hidden" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">生成Words</div>  
		            <div class="fitem">  
			           <label class="ui-label">word类型:</label><select class="easyui-combobox" name="wordType"  style="width:250px" data-options="required:true,validType:'length[1,100]'" >
		            			<option value="0">全部</option>
		                    	<option value="1">1.贷前文件四合一</option>
		                    	<option value="2">2.小企业电商贷调查表</option>
		                    	<option value="31">3.融信通开立账户信息表 云贷推送</option>
		                    	<option value="32">3.融信通开立账户信息表 自流量</option>
		                    	<option value="4">4.电商贷客户贷后须知</option>
		                    	<option value="5">5.华夏银行小企业网络贷最高额借款合同</option>
		                    	<option value="61">6.个人最高额保证合同 1</option>
		                    	<option value="62">6.个人最高额保证合同 2</option>
		                    	<option value="71">7.最高额保证合同 1</option>
		                    	<option value="72">7.最高额保证合同 2</option>
		                    	<option value="81">8.股东会成员名单及签字样本1</option>
		                    	<option value="82">8.股东会成员名单及签字样本2</option>
		                    	<option value="83">8.股东会成员名单及签字样本3 （一个股东）</option>
		                    	<option value="91">9.股东会决议1</option>
		                    	<option value="92">9.股东会决议2</option>
		                    	<option value="93">9.股东会决议3 （一个股东）</option>
		                    	<option value="101">10.法定代表人证明书及签字样本1</option>
		                    	<option value="102">10.法定代表人证明书及签字样本2</option>
		                    	<option value="111">11.预留公章样本1</option>
		                    	<option value="112">11.预留公章样本2</option>
		                    	<option value="12">12.华夏银行小企业授信业务实地见证确认书</option>
		                    	<option value="13">13.华夏银行授信业务办理申请书</option>
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
  	 
  	 <!-- 生成文书 结束   Form --> 
      	 
</div>

<script type="text/javascript" src="${msUrl}/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/bus/filing.js"></script>
  </body>
</html>
