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
 	 	    <label class="ui-label">申请贷款类型：</label><select class="easyui-combobox" name="loanType" >
            			<option value=""></option>
                    	<option value="个体工商户">个体工商户</option>
                    	<option value="小企业主">小企业主</option>
                    	<option value="小企业">小企业</option>
            </select>
            <label class="ui-label">紧急联系人地址: </label><input name="urgentContAddress" class="easyui-box ui-text" style="width:100px;">
            
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
     <div id="edit-win" class="easyui-dialog" title="总表信息" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:1029px;height:549px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <input class="hidden" type="text" name="uName" value="${user.nickName}">
     		 <input class="hidden" type="text"  name="uId" value="${user.id}"> 
     		<div id="tt" class="easyui-tabs" style="width:1025px;height:540px;">    
	     		 <div title="分表1" class="ui-edit" style="padding:20px;">
			     	   <div class="ftitle">上报信息</div>
			     	  
			           <div class="fitem">  
			               <label>调查机构名称:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyOrgName"   data-options="required:true,validType:'length[1,100]'">
			               <label>调查人姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPersonName" data-options="required:true,validType:'length[1,100]'">
			               <label>手机:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPhone" data-options="required:true,validType:'length[1,100]'">
			           </div>
			            
			       
			           
			            <div class="ftitle">申请人信息</div>
			            <div class="fitem">  
			               <label>借款申请人名称:</label>  
			               <input id="birthday" type="text" name="applicationName" class="easyui-validatebox"></input>
			               <label>申请金额:</label>  
			               <input id="birthday" type="text" name="applicationAmount" class="easyui-validatebox"></input>
			               <label>申请期限:</label>  
			               <input id="birthday" type="text" name="applicationTerm" class="easyui-validatebox"></input> 
							<label class="ui-label">申请贷款类型：</label><select class="easyui-combobox" name="loanType" style="width:129px">
		            			<option value=""></option>
		                    	<option value="个体工商户">个体工商户</option>
		                    	<option value="小企业主">小企业主</option>
		                    	<option value="小企业">小企业</option>
		            		</select> 
			           </div>
			           
			                 
			           <div class="fitem">  
			               <label>紧急联系人（限亲属，非保证人）:</label>  
			               <input id="birthday" type="text" name="urgentCont" class="easyui-validatebox"></input>
			               <label>紧急联系人电话:</label>  
			               <input id="birthday" type="text" name="urgentContPhone" class="easyui-validatebox"></input>  
			               <label>紧急联系人地址:</label>  
			               <input id="birthday" type="text" name="urgentContAddress" class="easyui-validatebox"></input>
			               <label>关系:</label>  
			               <input id="birthday" type="text" name="relationship" class="easyui-validatebox"></input>  
			           </div>
			           
			     
			           
			            <div class="ftitle">准入核查</div>
			            <div class="fitem">  
			               <label class="ui-label">淘宝三钻以上：</label><select class="easyui-combobox" name="taobaoTreeDiamondMore" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">其他平台有营业执照：</label><select class="easyui-combobox" name="otherPlatform" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		  <label class="ui-label">持续经营期限一年以上：</label><select class="easyui-combobox" name="operatingPeriodMore" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">是否网商店铺注册所有人：</label><select class="easyui-combobox" name="shopOwner" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		                    	
		            		</select>
			           </div>
			          
			          
			           
			           
			           <div class="fitem">
		            		 <label class="ui-label">非网商店铺注册所有人，但追加所有人担保：</label><select class="easyui-combobox" name="haveGuarantor" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">是否网商店铺实际控制人：</label><select class="easyui-combobox" name="shopController" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">近一年月均销售额10万以上：</label><select class="easyui-combobox" name="salesOfMore" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">授信合作金融机构（含我行）原则上不超过3家，最多不超过5家：</label><select class="easyui-combobox" name="than3credit" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            	
			           </div>
			           
			        
			           <div class="fitem">  
		            		  <label class="ui-label">借款企业、借款企业法人代表、实际控制人近2年内无经营性逾期、欠息记录：</label><select class="easyui-combobox" name="notOverdue" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            	
		            		 <label class="ui-label">个人消费类贷款无累计5次或连续3次的逾期、欠息记录</label><select class="easyui-combobox" name="perNotOverdue" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		
			           </div>
			           
			           
			           
			           
		         </div>
	         
	         	 <div title="分表2" class="ui-edit" style="padding:20px;" >   
			        	<div class="oz-form-fields"  style="width:450px;padding-top: 5px">  
						    <table cellpadding="0" cellspacing="0" style="width:450px;" id="optionContainer">  
						        <tr id="option0">   
						            <td class="oz-form-topLabel">所属问题  
						               
						            </td>  
						            <td class="oz-property" >  
						               1111
						            </td>  
						            <td></td>  
						        </tr>  
						        <tr id="option1">   
						            <td class="oz-form-topLabel">选项1：</td>  
						            <td class="oz-property" >  
						                <input type="text"  style="width:300px">  
						            </td>  
						            <td></td>  
						        </tr>  
						        <tr id="option2">   
						            <td class="oz-form-topLabel">选项2：</td>  
						            <td class="oz-property" >  
						                <input type="text"  style="width:300px" >  
						            </td>  
						            <td></td>  
						        </tr>  
						        <tr id="option3">   
						            <td class="oz-form-topLabel">选项3：</td>  
						            <td class="oz-property" >  
						                <input type="text"  style="width:300px">  
						            </td>  
						            <td></td>  
						        </tr>  
						        <tr id="option4">   
						            <td class="oz-form-topLabel">选项4：</td>  
						            <td class="oz-property" >  
						                <input type="text"  style="width:300px">  
						            </td>  
						            <td></td>  
						        </tr>  
						    </table>  
						    <div style="text-align: center;">  
						        <a href="#" onclick="addRow()">添加一行</a>  
						    </div>  
					</div>   
			    </div>   
			    <div title="分表3"  class="ui-edit" style="padding:20px;">   
			         <div class="ftitle">法定代表人或负责人</div>
			            
			            <div class="fitem">  
			                <label>姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="legalPerson" data-options="required:true,validType:'length[1,100]'">
			               <label>性别:</label>  
			               <select class="easyui-combobox" name="gender" style="width:139px" data-options="required:true,validType:'length[1,100]'">
		                    	<option value="男">男</option>
		                    	<option value="女">女</option>
		            		</select>
		            	   <label>身份证号码:</label>  
			               <input class="easyui-validatebox" type="text" name="idCard" data-options="required:true,validType:'length[1,100]'">
			               <label>公司名称:</label>  
			               <input class="easyui-validatebox" type="text" name="companyName" data-options="required:true,validType:'length[1,100]'">
			           </div>  
			    </div>   
	        </div>    
     	</form>
  	 </div> 
  	 
  	 <!-- 生成文书开始    Form -->
     <div id="edit-pwd-win" class="easyui-dialog" buttons="#editPwdbtn" title="生成word" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:300px;">
     	<form id="pwdForm" class="ui-form" method="post">  
     		 <input class="hidden" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">生成word</div>  
		            <div class="fitem">  
			           <label class="ui-label">word类型:</label><select class="easyui-combobox" name="wordType"  style="width:250px" data-options="required:true,validType:'length[1,200]'" >
		            			<option value="0">全部</option>
		                    	<option value="1">电商贷客户贷后须知</option>
		                    	<option value="2">法定代表人证明书及签字样本</option>
		            		</select>
		           </div>
		           <div class="fitem">
			           <label class="ui-label">word保存目录:</label><input class="easyui-validatebox" type="text" name="filePath" style="width:250px">
	            	</div>
	            	
	            	<br/>
	            	 <label class="ui-label"><font color="red">注：不填word保存目录则默认生成在D盘createWords文件夹里,填写格式为：D:/caeateWords</font></label>
	         </div>
	         <br/>
	         
     	</form>
     	 <div id="editPwdbtn" class="dialog-button">  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-submit">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-close">取消</a>  
        </div>
  	 </div> 
  	 
  	 <!-- 生成文书 结束   Form -->
  	
</div>

<script type="text/javascript" src="${msUrl}/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/bus/busLoan.js"></script>
  </body>
</html>
