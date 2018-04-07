<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
	<form id="addForm" class="ui-form" method="post">
	<input class="hidden" type="text" name="id" value="${material.id}">
	<input class="hidden" type="text" name="uName" value="${user.nickName}">
    <input class="hidden" type="text"  name="uId" value="${user.id}">
        <div style="text-align:center;">  
			<a href="#" id="btn-add" class="easyui-linkbutton" >保存</a>
			<a href="#" id="btn-reset" class="easyui-linkbutton">重置</a> 
	    </div>
	    <br>
		<div id="infoAdd" class="easyui-tabs" style="margin-left:5px">   
		    <div title="授信" style="overflow:auto;padding:20px;">   
		        <div class="ftitle">用款企业情况</div>
			           <div class="fitem">  
			               <label style="width:80px">企业名称:</label>  
			               <input class="easyui-validatebox" type="text" name="companyName"  value="${material.companyName}" data-options="required:true" style="width:139px">
			               <label style="width:120px">成立时间:</label>  
			               <input id="setupTime" type="text" name="setupTime"  class="easyui-validatebox" value="${material.setupTime}"  style="width:139px"></input>
			               <label style="width:120px">办公地址:</label>  
			               <input class="easyui-validatebox" type="text" name="companyAddress" value="${material.companyAddress}" style="width:139px">
			               <label style="width:120px">员工人数:</label>  
			               <input class="easyui-validatebox" type="text" name="employeeNumber" value="${material.employeeNumber}" style="width:139px">
			           </div>
			           <div class="fitem">  
			               <label style="width:80px">法定代表人:</label>  
			               <input class="easyui-validatebox" type="text" name="companyLegal"  value="${material.companyLegal}"  style="width:139px">
			               <label style="width:120px">关联企业:</label>  
			               <input id="associationCompany" type="text" name="associationCompany" value="${material.associationCompany }" class="easyui-validatebox"  style="width:139px"></input>
			               <label style="width:120px">其他情况:</label>  
			               <input  class="easyui-validatebox" type="text" name="otherHappening" value="${material.otherHappening}"  style="width:139px">
			           </div>
			           
			           
			           <div class="ftitle">实际控制人基本信息</div>
			           <div class="fitem">  
			               <label style="width:80px">姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerName"  value="${material.controllerName}" style="width:139px">
			               <label style="width:120px">性别:</label>
			               <input  class="easyui-validatebox" name="controllerGender"  value="${material.controllerGender}"/>  
			               <label style="width:120px">身份证:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerIdCard" value="${material.controllerIdCard}"   style="width:139px">
			               <label style="width:120px">移动电话:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerPhone" value="${material.controllerPhone}"   style="width:139px">
			           </div>
			            <div class="fitem">  
			               <label style="width:80px">常住地址:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerAddress"  value="${material.controllerAddress}"  style="width:139px">
			               <label style="width:120px">是否法定代表人:</label>  
			               <input class="easyui-validatebox" type="text" name="ifLegal"  value="${material.ifLegal}"  style="width:139px">
			           </div>
			           
			           <div class="ftitle">实际控制人配偶信息（如有）</div>
			           <div class="fitem">  
			               <label style="width:80px">姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="spouseName"  value="${material.spouseName}" " style="width:139px">
			               <label style="width:120px">移动电话:</label>  
			               <input class="easyui-validatebox" type="text" name="spousePhone" value="${material.spousePhone}"   style="width:139px">
			               <label style="width:120px">身份证号:</label>  
			               <input class="easyui-validatebox" type="text" name="spouseIdCard" value="${material.spouseIdCard}"  style="width:139px">
			           </div>
			           
			            <div class="ftitle">家庭资产负债情况</div>
			           <div class="fitem">  
			               <label style="width:80px">家庭资产合计:</label>  
			               <input class="easyui-validatebox" type="text" name="familyAssets"  value="${material.familyAssets}"  style="width:139px">
			               <label style="width:120px">主要资产明细:</label>  
			               <input class="easyui-validatebox" type="text" name="mainAssets" value="${material.mainAssets}"   style="width:139px">
			               <label style="width:120px">家庭负债合计:</label>  
			               <input class="easyui-validatebox" type="text" name="liabilities" value="${material.liabilities}"   style="width:139px">
			           </div>
			           
			           
			            <div class="ftitle">抵押物情况</div>
			           <div class="fitem">  
			               <label style="width:80px">抵押人:</label>  
			               <input class="easyui-validatebox" type="text" name="mortgageOwner"  value="${material.mortgageOwner}"  style="width:139px">
			               <label style="width:120px">抵押地址:</label>  
			               <input class="easyui-validatebox" type="text" name="mortgageAddress" value="${material.mortgageAddress}"   style="width:139px">
			               <label style="width:120px">建筑面积:</label>  
			               <input class="easyui-validatebox" type="text" name="mortgageArea" value="${material.mortgageArea}"   style="width:139px">
			               <label style="width:120px">房产证号:</label>  
			               <input class="easyui-validatebox" type="text" name="propertyNumber" value="${material.propertyNumber}"   style="width:139px">
			           </div>
			           
			            <div class="fitem">  
			               <label style="width:80px">评估单价:</label>  
			               <input class="easyui-validatebox" type="text" name="evaluationPrice"  value="${material.evaluationPrice}"  style="width:139px">
			               <label style="width:120px">评估总价:</label>  
			               <input class="easyui-validatebox" type="text" name="evaluationTotalPrice" value="${material.evaluationTotalPrice}"   style="width:139px">
			           </div>
			                  
		    </div>   
		    <div title="核保"  style="overflow:auto;padding:20px;">
		    		   <input class="hidden" type="text" name="verificationTempId" value="${verification.id}">
			           <div class="fitem">  
			               <label style="width:120px">借款合同号:</label>  
			               <input class="easyui-validatebox" type="text" name="contrateNumber" value="${verification.contrateNumber}" style="width:139px" >
			               <label style="width:120px">借款金额（大写）:</label>  
			               <input class="easyui-validatebox" type="text" name="loanAmount" value="${verification.loanAmount}" style="width:139px">
			               <label style="width:120px">贷款期限(年/月/日):</label>  
			               <input class="easyui-validatebox" type="text" name="loanTerm" value="${verification.loanTerm}"  style="width:139px">
			               <label style="width:120px">借款人:</label>  
			               <input class="easyui-validatebox" type="text" name="loanPerson" value="${verification.loanPerson}"   style="width:139px">
			           </div>
			           
			           
			            <div class="fitem">  
			               <label style="width:120px">身份证号码:</label>  
			               <input  type="text" name="loanIdCard" value="${verification.loanIdCard}" class="easyui-validatebox"  style="width:139px"></input> 
			               <label style="width:120px">手机:</label>  
			               <input  type="text" name="loanPhone" value="${verification.loanPhone}" class="easyui-validatebox" style="width:139px"></input> 
						   <label style="width:120px">个人最高额抵押合同号:</label>  
			               <input class="easyui-validatebox" id="highMortgageNumber" type="text" name="highMortgageNumber" value="${verification.highMortgageNumber}"   style="width:139px"></input>
			                <label style="width:120px">抵押财产价值（大写）:</label>  
			               <input class="easyui-validatebox" id="mortgageValue" type="text" name="mortgageValue" value="${verification.mortgageValue}"   style="width:139px"></input>    
			           </div>
			           
			           <div class="fitem">  
			               <label style="width:120px">抵押人姓名:</label>  
			               <input class="easyui-validatebox" id="mortgagePerson" type="text" name="mortgagePerson" value="${verification.mortgagePerson}"   style="width:139px"></input>
			               <label style="width:120px">身份证号码（按姓名顺序排列，用、号隔开）:</label>  
			               <input id="birthday" type="text" name="mortgageIdCard" value="${verification.mortgageIdCard}" class="easyui-validatebox"  style="width:139px"></input>
			               <label style="width:120px">电话号码（按姓名顺序排列，用、号隔开）:</label>  
			               <input id="mortgagePhone" type="text" name="mortgagePhone" value="${verification.mortgagePhone}" class="easyui-validatebox"  style="width:139px"></input>  
			           </div>
			           
			           
			           <br />
			            <div class="fitem">  
			              	<table id="tblData">
			              		<tr>
			              			<td style="width:130px">个人最高额保证合同号</td>
			              			<td style="width:100px">保证人姓名</td>
			              			<td style="width:100px">身份证</td>
			              			<td style="width:100px">手机</td>
			              		</tr>
			              			<c:if test="${guaranteeList==null}">
			              			  <input class="hidden" id="guaranterRowCount" type="text" name="guaranterRowCount" value="0" />
			              			<tr id="tRow0">
			              			<td>
			              			<input type="text" class="easyui-validatebox" id="guarantee[0].guaranteeNumber" name="guarantee[0].guaranteeNumber"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guarantee[0].guaranteePersonName"  name="guarantee[0].guaranteePersonName"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guarantee[0].guaranteePersonIdCard"  name="guarantee[0].guaranteePersonIdCard"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guarantee[0].lguaranteePersonPhone"  name="guarantee[0].lguaranteePersonPhone"  style="width:89px">
			              			</td>
				              		</tr>
				              		</c:if>
				              	
				              		<c:if test="${guaranteeList!=null}">
			              			<c:forEach var="guarantee" items="${guaranteeList}"  varStatus="status">
			              				<c:if test="${status.last}">
				              				<input class="hidden" id="guaranterRowCount" type="text" name="guaranterRowCount" value="${status.count-1}">
				              			</c:if>
				              			<input class="hidden" id="guarantee[${status.index}].guaranteeTempId" type="text" name="guarantee[${status.index}].guaranteeTempId" value="${guarantee.id}"> 
					              		<tr id="tRow0${status.index}">
					              			<td>
					              			<input id="guarantee[${status.index}].guaranteeNumber" class="easyui-validatebox" name="guarantee[${status.index}].guaranteeNumber" value="${guarantee.guaranteeNumber}" style="width:89px"  >
					              			</td>
					              			<td>
					              			<input id="guarantee[${status.index}].guaranteePersonName" class="easyui-validatebox" name="guarantee[${status.index}].guaranteePersonName" value="${guarantee.guaranteePersonName}" style="width:89px"  >
					              			</td>
					              			<td>
					              			<input id="guarantee[${status.index}].guaranteePersonIdCard" class="easyui-validatebox" name="guarantee[${status.index}].guaranteePersonIdCard" value="${guarantee.guaranteePersonIdCard}" style="width:89px"  >
					              			</td>
					              			<td>
					              			<input id="guarantee[${status.index}].lguaranteePersonPhone" class="easyui-validatebox"  name="guarantee[${status.index}].lguaranteePersonPhone" value="${guarantee.lguaranteePersonPhone}" style="width:89px"  >
					              			</td>
					              			<td>
					              		</tr>
					              	</c:forEach>
			              		</c:if>
				              		
			              	</table>
			              	<br />
					        <div >  
								  <a href="#" onclick="addGuaranterRow()">添加一行</a>
								   &nbsp;&nbsp;<a href="#" onclick="delGuaranterRow()">删除一行</a>   
							</div>
			              	<br />
			           </div>
			           
			          
			           
			           
			           <div class="ftitle">用款企业情况</div>
			           <br>
			            <div class="fitem">  
			               <label class="ui-label" style="width:120px">企业名字：</label>
			                <input id="useCompanyName" type="text" name="useCompanyName" value="${verification.useCompanyName}" class="easyui-validatebox"  style="width:139px"></input>  
		            		 <label class="ui-label" style="width:120px">股东人数：</label>
		            		 <input id="shareholders" type="text" name="shareholders" value="${verification.shareholders}" class="easyui-validatebox"  style="width:139px"></input>  
		            		  <label class="ui-label" style="width:120px">股东名字（以、号隔开）：</label>
		            		  <input id="shareholdersName" type="text" name="shareholdersName" value="${verification.shareholdersName}" class="easyui-validatebox"  style="width:139px"></input>
			           </div>
			           
			            <div class="ftitle">特别授信内容</div>
			           <div class="fitem">
			           		 <label class="ui-label" style="width:120px">特别授信借款人：</label>
		            		 <input id="creditPerson" type="text" name="creditPerson" value="${verification.creditPerson}" class="easyui-validatebox"  style="width:139px"></input>
		            		 <label class="ui-label" style="width:120px">身份证号码：</label>
		            		 <input id="creditPersonIdCard" type="text" name="creditPersonIdCard" value="${verification.creditPersonIdCard}" class="easyui-validatebox"  style="width:139px"></input> 
		            		 <label class="ui-label" style="width:120px">手机：</label>
		            		 <input id="creditPersonPhone" type="text" name="creditPersonPhone" value="${verification.creditPersonPhone}" class="easyui-validatebox"  style="width:139px"></input> 
		            		 <label class="ui-label" style="width:120px">特别授信借款合同号：</label>
		            		 <input id="creditLoanContract" type="text" name="creditLoanContract" value="${verification.creditLoanContract}" class="easyui-validatebox"  style="width:139px"></input>  
			           </div>
			           
			           <div class="fitem">
			           		 <label class="ui-label" style="width:120px">特别授信金额（大写）：</label>
		            		 <input id="creditAmount" type="text" name="creditAmount" value="${verification.creditAmount}" class="easyui-validatebox"  style="width:139px"></input> 
		            		 <label class="ui-label" style="width:120px">个人最高额质押合同号：</label>
		            		 <input id="pledgeContrateNumber" type="text" name="pledgeContrateNumber" value="${verification.pledgeContrateNumber}" class="easyui-validatebox"  style="width:139px"></input> 
		            		 <label class="ui-label" style="width:120px">质押人姓名：</label>
		            		 <input id="pledgePerson" type="text" name="pledgePerson" value="${verification.pledgePerson}" class="easyui-validatebox"  style="width:139px"></input>
		            		 <label class="ui-label" style="width:120px">质押人身份证号码：</label>
		            		 <input id="pledgePersonIdCard" type="text" name="pledgePersonIdCard" value="${verification.pledgePersonIdCard}" class="easyui-validatebox"  style="width:139px"></input> 
			           </div>
			           
			           
			           <div class="fitem">
		            		 <label class="ui-label" style="width:120px">质押人手机号码：</label>
		            		 <input id="pledgePersonPhone" type="text" name="pledgePersonPhone" value="${verification.pledgePersonPhone}" class="easyui-validatebox"  style="width:139px"></input> 
			           </div>
			           

			               
		    </div>   
		    <div title="入押"  style="overflow:auto;padding:20px;">
		   
			        <div class="fitem">  
			               <label style="width:120px">领证代理人:</label>  
			               <input class="easyui-validatebox" type="text" name="proxyPerson" value="${verification.proxyPerson}" style="width:139px">
			               <label style="width:120px">代理人身份证号:</label>  
			               <input class="easyui-validatebox" type="text" name="proxyPersonIdCard" value="${verification.proxyPersonIdCard}" style="width:139px">
			           </div>    

		    </div>
		    
		    <div title="放款"  style="overflow:auto;padding:20px;">
			      <div class="fitem">  
			               <label style="width:120px">实地见证员:</label>  
			               <input class="easyui-validatebox" type="text" name="witnessPerson" value="${verification.witnessPerson}" style="width:139px">
			               <label style="width:120px">客户经理:</label>  
			               <input class="easyui-validatebox" type="text" name="userManager" value="${verification.userManager}" style="width:139px">
			           </div>    
		    </div>
		       
		</div>
	</form>
	<script type="text/javascript" src="${msUrl}/js/ux/insurance/insuranceAdd.js"></script> 
  </body>
</html>
