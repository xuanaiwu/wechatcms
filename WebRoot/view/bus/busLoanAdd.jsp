<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
	<form id="addForm" class="ui-form" method="post">
	<input class="hidden" type="text" name="id">
    <input class="hidden" id="rowCount" type="text" name="rowCount" value="0">
    <input class="hidden" id="guaranterRowCount" type="text" name="guaranterRowCount" value="0">
	<input class="hidden" type="text" name="uName" value="${user.nickName}">
    <input class="hidden" type="text"  name="uId" value="${user.id}">
    <input class="hidden" type="text" name="lUserName" value="${user.nickName}">
    <input class="hidden" type="text"  name="lUId" value="${user.id}">
        <div style="text-align:center;">  
			<a href="#" id="btn-add" class="easyui-linkbutton" >保存</a>
			<a href="#" id="btn-reset" class="easyui-linkbutton">重置</a> 
	    </div>
	    <br>
		<div id="infoAdd" class="easyui-tabs" style="margin-left:5px">   
		    <div title="建档" style="overflow:auto;padding:20px;">   
		        <div class="ftitle">建档信息</div>
			           <div class="fitem">  
			               <label style="width:80px">姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="lName"   data-options="required:true,validType:'length[1,100]'" style="width:139px">
			               <label style="width:120px">身份证:</label>  
			               <input class="easyui-numberbox" type="text" name="lIdCard" data-options="required:true" style="width:139px">
			               <label style="width:120px">电话:</label>  
			               <input class="easyui-numberbox" type="text" name="lTelPhone" data-options="required:true" style="width:139px">
			               <label style="width:120px">当前状态:</label>  
			               <select class="easyui-combobox" name="lStatus" style="width:139px" data-options="required:true" style="width:139px">
		                    	<option value="建档">建档</option>
		                    	<option value="授信申报">授信申报</option>
		                    	<option value="放款">放款</option>
		                    	<option value="贷后台帐">贷后台帐</option>
		            	   </select> 
			           </div>    
		    </div>   
		    <div title="授信申报"  style="overflow:auto;padding:20px;">   
		         <div class="ftitle">机构信息</div>
			           <div class="fitem">  
			               <label style="width:120px">调查机构名称:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyOrgName"  style="width:139px" >
			               <label style="width:120px">调查人姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPersonName" style="width:139px">
			               <label style="width:120px">手机:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPhone" style="width:139px">
			           </div>
			           <div class="ftitle">申请人信息</div>
			           
			            <div class="fitem">  
			               <label style="width:120px">借款申请人名称:</label>  
			               <input id="applicationName" type="text" name="applicationName" class="easyui-validatebox"  style="width:139px"></input>
			               <label style="width:120px">申请人性别:</label>
			               <select class="easyui-combobox" name="applicationGender" style="width:139px"  >
		                    	<option value="男">男</option>
		                    	<option value="女">女</option>
		            	   </select>  
			               <label style="width:120px">申请人住址:</label>  
			               <input id="applicationAddress" type="text" name="applicationAddress" class="easyui-validatebox" style="width:139px"></input> 
						   <label style="width:120px">申请人身份证号码:</label>  
			               <input id="applicationIdCard" type="text" name="applicationIdCard" class="easyui-validatebox"  style="width:139px"></input>  
			           </div>
			           
			           <div class="fitem">  
			               <label style="width:120px">申请人电话:</label>  
			               <input id="applicationPhone" type="text" name="applicationPhone" class="easyui-validatebox"  style="width:139px"></input>
			               <label style="width:120px">申请金额（万元）:</label>  
			               <input id="birthday" type="text" name="applicationAmount" class="easyui-validatebox"  style="width:139px"></input>
			               <label style="width:120px">申请期限（年）:</label>  
			               <input id="birthday" type="text" name="applicationTerm" class="easyui-validatebox"  style="width:139px"></input> 
							<label class="ui-label" style="width:120px">申请贷款类型：</label><select class="easyui-combobox" name="loanType" style="width:139px">
		            			<option value=""></option>
		                    	<option value="个体工商户">个体工商户</option>
		                    	<option value="小企业主">小企业主</option>
		                    	<option value="小企业">小企业</option>
		            		</select> 
			           </div>
			           
			           <div class="fitem">  
			               <label style="width:120px">紧急联系人（限亲属，非保证人）:</label>  
			               <input id="birthday" type="text" name="urgentCont" class="easyui-validatebox"  style="width:139px"></input>
			               <label style="width:120px">紧急联系人电话:</label>  
			               <input id="birthday" type="text" name="urgentContPhone" class="easyui-validatebox" style="width:139px"></input>  
			               <label style="width:120px">紧急联系人地址:</label>  
			               <input id="birthday" type="text" name="urgentContAddress" class="easyui-validatebox" style="width:139px"></input>
			               <label style="width:120px">关系:</label>  
			               <input id="birthday" type="text" name="relationship" class="easyui-validatebox"  style="width:139px"></input>  
			           </div>
			           
			           
			           <div class="ftitle">准入核查</div>
			            <div class="fitem">  
			               <label class="ui-label" style="width:120px">淘宝三钻以上：</label><select class="easyui-combobox" name="taobaoTreeDiamondMore" style="width:139px" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label" style="width:120px">其他平台有营业执照：</label><select class="easyui-combobox" name="otherPlatform" style="width:139px" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		  <label class="ui-label" style="width:120px">持续经营期限一年以上：</label><select class="easyui-combobox" name="operatingPeriodMore" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label" style="width:120px">是否网商店铺注册所有人：</label><select class="easyui-combobox" name="ifShopOwner" style="width:139px" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		                    	
		            		</select>
			           </div>
			           
			           
			           <div class="fitem">
		            		 <label class="ui-label" style="width:120px">非网商店铺注册所有人，但追加所有人担保：</label><select class="easyui-combobox" name="haveGuarantor" style="width:139px" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label" style="width:120px">是否网商店铺实际控制人：</label><select class="easyui-combobox" name="shopController" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label" style="width:120px">近一年月均销售额10万以上：</label><select class="easyui-combobox" name="salesOfMore" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label" style="width:120px">授信合作金融机构（含我行）原则上不超过3家，最多不超过5家：</label><select class="easyui-combobox" name="than3credit" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            	
			           </div>
			           
			           
			           <div class="fitem">  
		            		  <label class="ui-label" style="width:120px">借款企业、借款企业法人代表、实际控制人近2年内无经营性逾期、欠息记录：</label><select class="easyui-combobox" name="notOverdue" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            	
		            		 <label class="ui-label" style="width:120px">个人消费类贷款无累计5次或连续3次的逾期、欠息记录</label><select class="easyui-combobox" name="perNotOverdue" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		
			           </div>
			           
			           <div class="ftitle">渠道信息</div>
			            <div class="fitem">  
			               <label class="ui-label" style="width:120px">渠道：</label><select class="easyui-combobox" name="channel" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="自流量">自流量</option>
		                    	<option value="云贷推送">云贷推送</option>
		            		</select>
			           </div>
			           
			           
			           <div id="optionContainer" class="ftitle" >经验实体信息(必填，多个网商店铺需加行)</div>
	         	 		 <div class="fitem">  
			              	<table id="tblShopData">
			              		<tr>
			              			<td>网店名称</td>
			              			<td>电商平台名称</td>
			              			<td>网店级别（仅限淘宝及天猫商户）</td>
			              			<td>网店持续经营年限（年）</td>
			              			<td>网店实际所有者（个人名称或公司名称)</td>
			              			<td>子帐号</td>
			              			<td>密码</td>
			              			<td>主要产品、品牌</td>
			              			<td>经营地址</td>
			              			<td>仓库地址</td>
			              			<td>上年度销售（万元）</td>
			              			<td>总负债（万元）</td>
			              			<td>银行负债（万元）</td>
			              			<td>上年度净利润（万元）</td>
			              		</tr>
			              		<tr id="tShopRow0">
			              			<td>
			              			<input id="shop[0].shopName" class="easyui-validatebox" name="shop[0].shopName" style="width:59px"  >
			              			</td>
			              			<td>
			              			<input id="shop[0].platformName" class="easyui-validatebox" name="shop[0].platformName" style="width:59px"  >
			              			</td>
			              			<td>
			              			<input id="shop[0].shopLevel" class="easyui-validatebox" name="shop[0].shopLevel" style="width:59px"  >
			              			</td>
			              			<td>
			              			<input id="shop[0].operatingPeriod" class="easyui-validatebox"  name="shop[0].operatingPeriod" style="width:59px"  >
			              			</td>
			              			<td>
			              			<input id="shop[0].shopOwner" class="easyui-validatebox"  name="shop[0].shopOwner" style="width:59px"  >
			              			</td>
			              			<td>
			              			<input id="shop[0].subAccount" class="easyui-validatebox"  name="shop[0].subAccount" style="width:59px"  >
			              			</td>
			              			<td>
			              				<input id="shop[0].sbuPassword" class="easyui-validatebox" name="shop[0].sbuPassword" style="width:59px" >
			              			</td>
			              			<td>
			              				<input id="shop[0].businessOpera" class="easyui-validatebox"  name="shop[0].businessOpera" style="width:59px"  >
			              			</td>
			              			<td>
			              				<input id="shop[0].businessAddress" class="easyui-validatebox" name="shop[0].businessAddress" style="width:59px"  >
			              			</td>
			              			<td>
			              				<input id="shop[0].warehouseAddress" class="easyui-validatebox"  name="shop[0].warehouseAddress" style="width:59px"  >
			              			</td>
			              			
			              			<td>
			              				<input id="shop[0].salesIncome" class="easyui-validatebox" name="shop[0].salesIncome" style="width:59px"  >
			              			</td>
			              			<td>
			              				<input id="shop[0].totalLiability" class="easyui-validatebox"  name="shop[0].totalLiability" style="width:59px"  >
			              			</td>
			              			<td>
			              				<input id="shop[0].bankLiabilities" class="easyui-validatebox" name="shop[0].bankLiabilities" style="width:59px" >
			              			</td>
			              			<td>
			              				<input id="shop[0].netProfit" class="easyui-validatebox" name="shop[0].netProfit" style="width:59px" >
			              			</td>
			              			
			              		</tr>
			              	</table>
			              	<br />
					        <div style="text-align:center;">  
								  <a href="#" onclick="addShopRow()">添加一行</a>  
								  &nbsp;&nbsp;<a href="#" onclick="delShopRow()">删除一行</a> 
							</div>	
			           </div>
			           
			           
			           <div class="ftitle">法定代表人或负责人基本信息（必填）</div>
			            <div class="fitem">  
			               <label style="width:120px">姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="legalPerson" style="width:139px" >
			               <label style="width:120px">证件号码:</label>  
			               <input class="easyui-validatebox" type="text" name="idCard"  style="width:139px">
			               <label style="width:120px">性别:</label>  
			               <select class="easyui-combobox" name="gender" style="width:139px"  >
		                    	<option value="男">男</option>
		                    	<option value="女">女</option>
		            	   </select>
			               <label style="width:120px">公司名称:</label>  
			               <input class="easyui-validatebox" type="text" name="companyName" style="width:139px" >
			           </div>
			           <div class="fitem">  
			               <label style="width:120px">身份证有效期:</label>  
			               <input class="easyui-validatebox" type="text" name="idCardPeriod"  style="width:139px">
			               <label style="width:120px">身份证地址:</label>  
			               <input class="easyui-validatebox" type="text" name="idCardAddress"  style="width:139px">
			               <label style="width:120px">户籍地址:</label>  
			               <input class="easyui-validatebox" type="text" name="householdRegistration"  style="width:139px">
			               <label style="width:120px">现住址:</label>  
			               <input class="easyui-validatebox" type="text" name="houseAddress"  style="width:139px">
			           </div>
			           <div class="fitem">  
			               <label style="width:120px">可接受快递额地址:</label>  
			               <input class="easyui-validatebox" type="text" name="deliveryAddress"  style="width:139px">
			               <label style="width:120px">手机:</label>  
			               <input class="easyui-validatebox" type="text" name="legalPhone"  style="width:139px">
			           </div>
			           
			           <div class="ftitle">法定代表人或负责人家庭资产（必填）</div>
			            <div class="fitem">  
			               <label style="width:120px">房产数量:</label>  
			               <input class="easyui-validatebox" type="text" name="propertyQuantity"  style="width:139px">
			               <label style="width:120px">总面积（平方米）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalArea"  style="width:139px">
			               <label style="width:120px">总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalValue"  style="width:139px">
			               <label style="width:120px">详细地址（以;隔开）:</label>  
			               <input class="easyui-validatebox" type="text" name="propertyAddress" style="width:139px">
			           </div>
			           
			           <div class="fitem">  
			               <label style="width:120px">是否已为他人(企业)债权设定抵押:</label>  
			               <select class="easyui-combobox" name="mortgage" style="width:139px"  >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>
			               <label style="width:120px">车辆总数（辆）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalCar"  style="width:139px">
			              	<label style="width:120px">牌照号（以;隔开）:</label>  
			               <input class="easyui-validatebox" type="text" name="licenseNumber"  style="width:139px">
			               <label style="width:120px">总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalCarValue" style="width:139px" >
			           </div>
			           
			            <div class="fitem">  
			               <label style="width:120px">其他资产:</label>  
			             	<input class="easyui-validatebox" type="text" name="otherAssets"  style="width:139px">
			               <label style="width:120px">借款所属银行:</label>  
			               <input class="easyui-validatebox" type="text" name="borrowOfBank" style="width:139px" >
			              <label style="width:120px">金额（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="amount" style="width:139px" >
			               <label style="width:120px">期限（年）:</label>  
			               <input class="easyui-validatebox" type="text" name="theTerm"  style="width:139px">
			           </div>
			           
			           <div class="ftitle">是否为实际控制人，选否要填写实际控制人信息</div>
			           <div class="fitem">  
			               <label class="ui-label" style="width:120px">是否为实际控制人：</label><select class="easyui-combobox" name="ifController" style="width:139px"  >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
			           </div>
			           
			           
			           <div class="ftitle">实际控制人基本信息</div>
			            <div class="fitem">  
			               <label style="width:120px">姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerName" style="width:139px">
			               <label style="width:120px">证件号码 :</label>  
			               <input class="easyui-validatebox" type="text" name="controllerIdCard" style="width:139px">
			              	<label style="width:120px">户籍地址:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerRegistration" style="width:139px">
			               <label style="width:120px">家庭住址:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerHouseAddress" style="width:139px">
			           </div>
			           
			           <div class="fitem">  
			               <label style="width:120px">手机:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerPhone" style="width:139px">
			               <label style="width:120px">房产数量:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerPropertyQuantity" style="width:139px" >
			              	<label style="width:120px">房产总面积（平方米）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTotalArea" style="width:139px">
			               <label style="width:120px">房产总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllertotalValue" style="width:139px">
			           </div>
			           
			            <div class="fitem">
			            <label style="width:120px">是否已为他人(企业)债权设定抵押:</label>  
			               <select class="easyui-combobox" name="contrallerMortgage" style="width:139px" >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>	  
			               <label style="width:120px">房产详细地址（以;隔开）:</label>  
			             	<input class="easyui-validatebox" type="text" name="controllerPropertyAddress" style="width:139px">
			               <label style="width:120px">车辆总数（辆）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTotalCar" style="width:139px">
			              	<label style="width:120px">牌照号（以;隔开）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerLicenseNumber" style="width:139px">
			               
			           </div>
			           
			            <div class="fitem">
			               <label style="width:120px">总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTotalCarValue" style="width:139px">  
			               <label style="width:120px">其他资产:</label>  
			             	<input class="easyui-validatebox" type="text" name="controllerOtherAssets" style="width:139px">
			               <label style="width:120px">借款所属银行:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerBorrowOfBank" style="width:139px">
			              	<label style="width:120px">金额（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerAmount" style="width:139px">
			               
			           </div>
			           <div class="fitem">
			               <label style="width:120px">期限（年）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTheTerm" style="width:139px">
			           </div>
			           
			           
			           <div class="ftitle">是否提供自然人保证，是就填写下面信息</div>
			            <div class="fitem">  
			               <label class="ui-label" style="width:120px">是否提供自然人保证：</label><select  name="ifGuaranter" style="width:139px">
		                    	<option value="是" selected>是</option>
		                    	<!--  <option value="否">否</option>-->
		            		</select>
			           </div>
			           
			           <div class="ftitle">保证人信息</div>
			            <div class="fitem">  
			              	<table id="tblData">
			              		<tr>
			              			<td>保证人姓名</td>
			              			<td>证件号码</td>
			              			<td>工作单位</td>
			              			<td>职务</td>
			              			<td>联系电话</td>
			              			<td>婚姻状况</td>
			              			<td>家庭地址</td>
			              			<td>月收入情况（万元）</td>
			              			<td>资产总额（万元）</td>
			              			<td>负债总额（万元）</td>
			              		</tr>
			              		<tr id="tRow0">
			              			<td>
			              			<input type="text" class="easyui-validatebox" id="guaranter[0].guaranterName" name="guaranter[0].guaranterName"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterCard"  name="guaranter[0].guaranterCard"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterEmployer"  name="guaranter[0].guaranterEmployer"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterDuties"  name="guaranter[0].guaranterDuties"  style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterPhone"  name="guaranter[0].guaranterPhone"  style="width:89px">
			              			</td>
			              			<td>
			              			<select  id="guaranter[0].guaranterMaritalStatus"  name="guaranter[0].guaranterMaritalStatus"  style="width:89px">
			              			        <option value="未婚">未婚</option>
					                    	<option value="已婚">已婚</option>
					                    	<option value="离异">离异</option>
					                    	<option value="丧偶">丧偶</option>
					                    	<option value="其他">其他</option>
					            	</select>
			              			</td>
			              			<td>
			              				<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterHouseAddress" name="guaranter[0].guaranterHouseAddress"  style="width:89px">
			              			</td>
			              			<td>
			              				<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterMonthlyIncome"  name="guaranter[0].guaranterMonthlyIncome"  style="width:89px">
			              			</td>
			              			<td>
			              				<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterValues" name="guaranter[0].guaranterValues"  style="width:89px">
			              			</td>
			              			<td>
			              				<input   type="text" class="easyui-validatebox"  id="guaranter[0].guaranterTotalLiabilities"  name="guaranter[0].guaranterTotalLiabilities"  style="width:89px">
			              			</td>
			              		</tr>
			              	</table>
			              	<br />
					        <div style="text-align:center;">  
								  <a href="#" onclick="addGuaranterRow()">添加一行</a>
								   &nbsp;&nbsp;<a href="#" onclick="delGuaranterRow()">删除一行</a>   
							</div>
			              	
			           </div>
			           
			           <div class="ftitle">附加信息</div>
			            <div class="fitem">  
			                <label style="width:120px">是否在当地缴纳社保:</label>  
			             	<select class="easyui-combobox" name="localPaySocialSecurity" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
			               <label style="width:120px">是否华夏银行金融资产类贵宾客户:</label>
			               <select class="easyui-combobox" name="ifCustomersVIP" style="width:139px"  >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>    
			              	<label style="width:120px">子女是否在当地入学:</label>
			              	<select class="easyui-combobox" name="childrenIfLocally" style="width:139px"  >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>
			               <label style="width:120px">其他补充信息:</label>  
			               <input class="easyui-validatebox" type="text" name="additionInfo" style="width:139px">
			           </div>
			               
		    </div>   
		    <div title="放款"  style="overflow:auto;padding:20px;">
		    	 <div class="ftitle">保证人公司信息</div>
			            <div class="fitem">  
			              	<table id="guaranteeCompanyRowtblData">
			              		<tr>
			              			<td style="width:120px">保证人公司</td>
			              			<td style="width:120px">地址</td>
			              			<td style="width:120px">法人</td>
			              			<td style="width:120px">股东人数</td>
			              			<td style="width:120px">股东名（以、隔开）</td>
			              		</tr>
			              		<tr id="guaranteeCompanyRow0">
			              			<td>
			              			<input type="text" class="easyui-validatebox" id="guaranteeCompany1" name="guaranteeCompany1"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranteeAddress1"  name="guaranteeAddress1"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranteeLegal1"  name="guaranteeLegal1"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="numberOfShareHolders1"  name="numberOfShareHolders1" " style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="shareHoldersName1"  name="shareHoldersName1"  style="width:250px">
			              			</td>
			              		</tr>
			              		
			              		<tr id="guaranteeCompanyRow1">
			              			<td>
			              			<input type="text" class="easyui-validatebox" id="guaranteeCompany2" name="guaranteeCompany2"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranteeAddress2"  name="guaranteeAddress2"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranteeLegal2"  name="guaranteeLegal2"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="numberOfShareHolders2"  name="numberOfShareHolders2"  style="width:139px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="shareHoldersName2"  name="shareHoldersName2"  style="width:250px">
			              			</td>
			              		</tr>
			              	</table>
			           </div>
		    
		    
		       
		         <div class="ftitle">放款信息</div>
			            <div class="fitem">  
			               <label style="width:120px">放款金额（大写）:</label>  
			               <input id="loanAmount" type="text" name="loanAmount" class="easyui-validatebox" style="width:139px"></input>
			               <label style="width:120px">开通额度:</label>
			               <input id="openingQuota" type="text" name="openingQuota" class="easyui-validatebox"  style="width:139px"/>
			    
			           		<label style="width:120px">授信有效期（年）:</label>  
			               <input id="creditTerm" type="text" name="creditTerm" class="easyui-validatebox"  style="width:139px"></input>
			           		<label style="width:60px">期限:</label>  
			               	<input id="startTerm" type="text" name="startTerm" class="easyui-datebox"  style="width:89px"></input>-
			               	<input id="endTerm" type="text" name="endTerm" class="easyui-datebox" style="width:89px"></input>  
			           </div>    
		    </div>
		    
		    <div title="贷后台帐"  style="overflow:auto;padding:20px;">
		        <div class="ftitle">贷后台帐信息</div>   
		           <div class="fitem">
			           	<label style="width:80px">检查日期:</label>  
			            <input id="checkDate" type="text" name="checkDate" class="easyui-datebox"  style="width:139px"></input>
  						<label style="width:120px">借款人征信是否正常:</label>
			            <select class="easyui-combobox" name="creditorIfNormal" style="width:139px"  >
		                    <option value="是">是</option>
		                    <option value="否">否</option>
		            	</select>
		            	<label style="width:120px">保证人征信是否正常:</label>
		            	<select class="easyui-combobox" name="guarantorIfNormal" style="width:139px" >
		                    <option value="是">是</option>
		                    <option value="否">否</option>
		            	</select>
		            	<label style="width:120px">云贷是否有预警信息:</label>
		            	<select class="easyui-combobox" name="cloudLoanIfWarning" style="width:139px"  >
		                    <option value="是">是</option>
		                    <option value="否">否</option>
		            	</select>
			      </div> 
			      
			      <div class="fitem">
			           	<label style="width:80px">店铺经营情况:</label>  
			            <input id="shopOperation" type="text" name="shopOperation" class="easyui-validatebox"  style="width:139px"></input>
  						<label style="width:120px">其他需要说明的地方:</label>
			            <input id="otherNeedToExplained" type="text" name="otherNeedToExplained" class="easyui-validatebox"  style="width:139px"></input>
		            	<label style="width:120px">贷款卡号:</label>
		            	<input id="loanCardNumber" type="text" name="loanCardNumber" class="easyui-validatebox"  style="width:139px"></input>
		            	<label style="width:120px">授信到期日:</label>
		            	<input id="creditEndDate" type="text" name="creditEndDate" class="easyui-datebox" style="width:139px"></input>
			      </div>
			      
			      
			      <div class="fitem">
			           	<label style="width:80px">融信通帐号:</label>  
			            <input id="loanAccount" type="text" name="loanAccount" class="easyui-validatebox" style="width:139px"></input>
  						<label style="width:120px">固定贷款期限(年):</label>
			            <input id="loanTerm" type="text" name="loanTerm" class="easyui-validatebox" style="width:139px"></input>
			      </div>
			       
		    </div>
		       
		</div>
	</form>
	<script type="text/javascript" src="${msUrl}/js/ux/bus/busLoanAdd.js"></script> 
  </body>
</html>
