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
     		 <input class="hidden" id="rowCount" type="text" name="rowCount" value="0">
     		 <input class="hidden" id="guaranterRowCount" type="text" name="guaranterRowCount" value="0">
     		 <input class="hidden" type="text" name="uName" value="${user.nickName}">
     		 <input class="hidden" type="text"  name="uId" value="${user.id}"> 
     		<div id="tt" class="easyui-tabs" style="width:1025px;height:540px;">    
	     		 <div title="分表1" class="ui-edit" style="padding:20px;">
			     	   <div class="ftitle">上报信息</div>
			     	  
			           <div class="fitem">  
			               <label>调查机构名称:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyOrgName"   data-options="required:true,validType:'length[1,200]'">
			               <label>调查人姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPersonName" data-options="required:true,validType:'length[1,100]'">
			               <label>手机:</label>  
			               <input class="easyui-validatebox" type="text" name="surveyPhone" data-options="required:true,validType:'length[1,100]'">
			           </div>
			            
			       
			           
			            <div class="ftitle">申请人信息</div>
			            
			            <div class="fitem">  
			               <label>借款申请人名称:</label>  
			               <input id="applicationName" type="text" name="applicationName" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>
			               <label>申请人性别:</label>
			               <select class="easyui-combobox" name="applicationGender" style="width:139px" data-options="required:true,validType:'length[1,200]'" >
		                    	<option value="男">男</option>
		                    	<option value="女">女</option>
		            	   </select>  
			               <label>申请人住址:</label>  
			               <input id="applicationAddress" type="text" name="applicationAddress" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input> 
						   <label>申请人身份证号码:</label>  
			               <input id="applicationIdCard" type="text" name="applicationIdCard" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>  
			           </div>
			            
			            
			            
			            <div class="fitem">  
			               <label>申请人电话:</label>  
			               <input id="applicationPhone" type="text" name="applicationPhone" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>
			               <label>申请金额（万元）:</label>  
			               <input id="birthday" type="text" name="applicationAmount" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>
			               <label>申请期限（年）:</label>  
			               <input id="birthday" type="text" name="applicationTerm" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input> 
							<label class="ui-label">申请贷款类型：</label><select class="easyui-combobox" name="loanType" style="width:129px" data-options="required:true,validType:'length[1,50]'" >
		            			<option value=""></option>
		                    	<option value="个体工商户">个体工商户</option>
		                    	<option value="小企业主">小企业主</option>
		                    	<option value="小企业">小企业</option>
		            		</select> 
			           </div>
			           
			                 
			           <div class="fitem">  
			               <label>紧急联系人（限亲属，非保证人）:</label>  
			               <input id="birthday" type="text" name="urgentCont" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>
			               <label>紧急联系人电话:</label>  
			               <input id="birthday" type="text" name="urgentContPhone" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>  
			               <label>紧急联系人地址:</label>  
			               <input id="birthday" type="text" name="urgentContAddress" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>
			               <label>关系:</label>  
			               <input id="birthday" type="text" name="relationship" class="easyui-validatebox" data-options="required:true,validType:'length[1,200]'" ></input>  
			           </div>
			           
			     
			           
			            <div class="ftitle">准入核查</div>
			            <div class="fitem">  
			               <label class="ui-label">淘宝三钻以上：</label><select class="easyui-combobox" name="taobaoTreeDiamondMore" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">其他平台有营业执照：</label><select class="easyui-combobox" name="otherPlatform" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		  <label class="ui-label">持续经营期限一年以上：</label><select class="easyui-combobox" name="operatingPeriodMore" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">是否网商店铺注册所有人：</label><select class="easyui-combobox" name="ifShopOwner" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		                    	
		            		</select>
			           </div>
			          
			          
			           
			           
			           <div class="fitem">
		            		 <label class="ui-label">非网商店铺注册所有人，但追加所有人担保：</label><select class="easyui-combobox" name="haveGuarantor" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">是否网商店铺实际控制人：</label><select class="easyui-combobox" name="shopController" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">近一年月均销售额10万以上：</label><select class="easyui-combobox" name="salesOfMore" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		 <label class="ui-label">授信合作金融机构（含我行）原则上不超过3家，最多不超过5家：</label><select class="easyui-combobox" name="than3credit" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            	
			           </div>
			           
			        
			           <div class="fitem">  
		            		  <label class="ui-label">借款企业、借款企业法人代表、实际控制人近2年内无经营性逾期、欠息记录：</label><select class="easyui-combobox" name="notOverdue" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            	
		            		 <label class="ui-label">个人消费类贷款无累计5次或连续3次的逾期、欠息记录</label><select class="easyui-combobox" name="perNotOverdue" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
		            		
			           </div>
			           
			           <div class="ftitle">渠道信息</div>
			            <div class="fitem">  
			               <label class="ui-label">渠道：</label><select class="easyui-combobox" name="channel" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="自流量">自流量</option>
		                    	<option value="云贷推送">云贷推送</option>
		            		</select>
			           </div>
			           
			           
			           
		         </div>
	         
			       
			    <div title="分表2"  class="ui-edit" style="padding:20px;">   
			         <div class="ftitle">法定代表人或负责人基本信息（必填）</div>
			            <div class="fitem">  
			               <label>姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="legalPerson" data-options="required:true,validType:'length[1,200]'" >
			               <label>证件号码:</label>  
			               <input class="easyui-validatebox" type="text" name="idCard" data-options="required:true,validType:'length[1,200]'" >
			               <label>性别:</label>  
			               <select class="easyui-combobox" name="gender" style="width:139px" data-options="required:true,validType:'length[1,100]'" >
		                    	<option value="男">男</option>
		                    	<option value="女">女</option>
		            	   </select>
			               <label>公司名称:</label>  
			               <input class="easyui-validatebox" type="text" name="companyName" data-options="required:true,validType:'length[1,200]'" >
			           </div>
			           <div class="fitem">  
			               <label>身份证有效期:</label>  
			               <input class="easyui-validatebox" type="text" name="idCardPeriod" data-options="required:true,validType:'length[1,200]'" >
			               <label>身份证地址:</label>  
			               <input class="easyui-validatebox" type="text" name="idCardAddress" data-options="required:true,validType:'length[1,500]'" >
			               <label>户籍地址:</label>  
			               <input class="easyui-validatebox" type="text" name="householdRegistration" data-options="required:true,validType:'length[1,500]'" >
			               <label>现住址:</label>  
			               <input class="easyui-validatebox" type="text" name="houseAddress" data-options="required:true,validType:'length[1,500]'" >
			           </div>
			           <div class="fitem">  
			               <label>可接受快递额地址:</label>  
			               <input class="easyui-validatebox" type="text" name="deliveryAddress" data-options="required:true,validType:'length[1,500]'" >
			               <label>手机:</label>  
			               <input class="easyui-validatebox" type="text" name="legalPhone" data-options="required:true,validType:'length[1,200]'" >
			           </div>
			           
			           <div class="ftitle">法定代表人或负责人家庭资产（必填）</div>
			            <div class="fitem">  
			               <label>房产数量:</label>  
			               <input class="easyui-validatebox" type="text" name="propertyQuantity" data-options="required:true,validType:'length[1,100]'" >
			               <label>总面积（平方米）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalArea" data-options="required:true,validType:'length[1,100]'" >
			              	<label>总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalValue" data-options="required:true,validType:'length[1,100]'" >
			               <label>详细地址（以;隔开）:</label>  
			               <input class="easyui-validatebox" type="text" name="propertyAddress" data-options="required:true,validType:'length[1,500]'" >
			           </div>
			           
			           <div class="fitem">  
			               <label>是否已为他人(企业)债权设定抵押:</label>  
			               <select class="easyui-combobox" name="mortgage" style="width:139px" data-options="required:true,validType:'length[1,100]'" >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>
			               <label>车辆总数（辆）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalCar" data-options="required:true,validType:'length[1,500]'" >
			              	<label>牌照号（以;隔开）:</label>  
			               <input class="easyui-validatebox" type="text" name="licenseNumber" data-options="required:true,validType:'length[1,500]'" >
			               <label>总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="totalCarValue" data-options="required:true,validType:'length[1,100]'" >
			           </div>
			           
			            <div class="fitem">  
			               <label>其他资产:</label>  
			             	<input class="easyui-validatebox" type="text" name="otherAssets" data-options="required:true,validType:'length[1,500]'" >
			               <label>借款所属银行:</label>  
			               <input class="easyui-validatebox" type="text" name="borrowOfBank" data-options="required:true,validType:'length[1,200]'" >
			              	<label>金额（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="amount" data-options="required:true,validType:'length[1,200]'" >
			               <label>期限（年）:</label>  
			               <input class="easyui-validatebox" type="text" name="theTerm" data-options="required:true,validType:'length[1,200]'" >
			           </div>
			           
			           <div class="ftitle">是否为实际控制人，选否要填写实际控制人信息</div>
			            <div class="fitem">  
			               <label class="ui-label">是否为实际控制人：</label><select class="easyui-combobox" name="ifController" style="width:139px" data-options="required:true,validType:'length[1,20]'" >
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
			           </div>
			           
			           
			           <div class="ftitle">实际控制人基本信息</div>
			            <div class="fitem">  
			               <label>姓名:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerName" >
			               <label>证件号码 :</label>  
			               <input class="easyui-validatebox" type="text" name="controllerIdCard" >
			              	<label>户籍地址:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerRegistration" >
			               <label>家庭住址:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerHouseAddress" >
			           </div>
			           
			           <div class="fitem">  
			               <label>手机:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerPhone">
			               <label>房产数量:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerPropertyQuantity" >
			              	<label>房产总面积（平方米）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTotalArea" >
			               <label>房产总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllertotalValue">
			           </div>
			           
			            <div class="fitem">
			            <label>是否已为他人(企业)债权设定抵押:</label>  
			               <select class="easyui-combobox" name="contrallerMortgage" style="width:139px" >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>	  
			               <label>房产详细地址（以;隔开）:</label>  
			             	<input class="easyui-validatebox" type="text" name="controllerPropertyAddress">
			               <label>车辆总数（辆）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTotalCar" >
			              	<label>牌照号（以;隔开）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerLicenseNumber" >
			               
			           </div>
			           
			            <div class="fitem">
			               <label>总价值（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTotalCarValue">  
			               <label>其他资产:</label>  
			             	<input class="easyui-validatebox" type="text" name="controllerOtherAssets">
			               <label>借款所属银行:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerBorrowOfBank" >
			              	<label>金额（万元）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerAmount" >
			               
			           </div>
			           <div class="fitem">
			               <label>期限（年）:</label>  
			               <input class="easyui-validatebox" type="text" name="controllerTheTerm">
			           </div>
			           
			           
			               
			    </div>
			    <div title="分表3"  class="ui-edit" style="padding:20px;">
			    	<div id="optionContainer" class="ftitle">经验实体信息(必填，多个网商店铺需加行)</div>
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
			              			<input id="shop[0].shopName" class="easyui-validatebox" name="shop[0].shopName" style="width:59px" data-options="required:true,validType:'length[1,200]'" >
			              			</td>
			              			<td>
			              			<input id="shop[0].platformName" class="easyui-validatebox" name="shop[0].platformName" style="width:59px" data-options="required:true,validType:'length[1,200]'" >
			              			</td>
			              			<td>
			              			<input id="shop[0].shopLevel" class="easyui-validatebox" name="shop[0].shopLevel" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              			<input id="shop[0].operatingPeriod" class="easyui-validatebox"  name="shop[0].operatingPeriod" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              			<input id="shop[0].shopOwner" class="easyui-validatebox"  name="shop[0].shopOwner" style="width:59px" data-options="required:true,validType:'length[1,200]'" >
			              			</td>
			              			<td>
			              			<input id="shop[0].subAccount" class="easyui-validatebox"  name="shop[0].subAccount" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].sbuPassword" class="easyui-validatebox" name="shop[0].sbuPassword" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].businessOpera" class="easyui-validatebox"  name="shop[0].businessOpera" style="width:59px" data-options="required:true,validType:'length[1,300]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].businessAddress" class="easyui-validatebox" name="shop[0].businessAddress" style="width:59px" data-options="required:true,validType:'length[1,300]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].warehouseAddress" class="easyui-validatebox"  name="shop[0].warehouseAddress" style="width:59px" data-options="required:true,validType:'length[1,300]'" >
			              			</td>
			              			
			              			<td>
			              				<input id="shop[0].salesIncome" class="easyui-validatebox" name="shop[0].salesIncome" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].totalLiability" class="easyui-validatebox"  name="shop[0].totalLiability" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].bankLiabilities" class="easyui-validatebox" name="shop[0].bankLiabilities" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			<td>
			              				<input id="shop[0].netProfit" class="easyui-validatebox" name="shop[0].netProfit" style="width:59px" data-options="required:true,validType:'length[1,100]'" >
			              			</td>
			              			
			              		</tr>
			              	</table>
			              	<br />
					        <div style="text-align:center;">  
								  <a href="#" onclick="addShopRow()">添加一行</a>  
								  &nbsp;&nbsp;<a href="#" onclick="delShopRow()">删除一行</a> 
							</div>	
			           </div>   
			    	
			    	
			    	
			    	<div class="ftitle">是否提供自然人保证，是就填写下面信息</div>
			            <div class="fitem">  
			               <label class="ui-label">是否提供自然人保证：</label><select  name="ifGuaranter" style="width:139px">
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
			              			<input type="text" class="easyui-validatebox" id="guaranter[0].guaranterName" name="guaranter[0].guaranterName" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterCard"  name="guaranter[0].guaranterCard" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterEmployer"  name="guaranter[0].guaranterEmployer" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterDuties"  name="guaranter[0].guaranterDuties" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              			<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterPhone"  name="guaranter[0].guaranterPhone" data-options="required:true" style="width:89px">
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
			              				<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterHouseAddress" name="guaranter[0].guaranterHouseAddress" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              				<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterMonthlyIncome"  name="guaranter[0].guaranterMonthlyIncome" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              				<input  type="text" class="easyui-validatebox" id="guaranter[0].guaranterValues" name="guaranter[0].guaranterValues" data-options="required:true" style="width:89px">
			              			</td>
			              			<td>
			              				<input   type="text" class="easyui-validatebox"  id="guaranter[0].guaranterTotalLiabilities"  name="guaranter[0].guaranterTotalLiabilities" data-options="required:true" style="width:89px">
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
			                <label>是否在当地缴纳社保:</label>  
			             	<select class="easyui-combobox" name="localPaySocialSecurity" style="width:139px">
		            			<option value=""></option>
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            		</select>
			               <label>是否华夏银行金融资产类贵宾客户:</label>
			               <select class="easyui-combobox" name="ifCustomersVIP" style="width:139px" data-options="required:true,validType:'length[1,200]'" >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>    
			              	<label>子女是否在当地入学:</label>
			              	<select class="easyui-combobox" name="childrenIfLocally" style="width:139px" data-options="required:true,validType:'length[1,200]'" >
		                    	<option value="是">是</option>
		                    	<option value="否">否</option>
		            	   </select>
			               <label>其他补充信息:</label>  
			               <input class="easyui-validatebox" type="text" name="additionInfo">
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
			           <label class="ui-label">word类型:</label><select class="easyui-combobox" name="wordType"  style="width:250px" data-options="required:true,validType:'length[1,100]'" >
		            			<option value="0">全部</option>
		                    	<option value="1">1.电商贷客户贷后须知</option>
		                    	<option value="2">2.董事会（股东会）成员名单及签字样本（参考文本）</option>
		                    	<option value="3">3.法定代表人证明书及签字样本</option>
		                    	<option value="4">4.股东会决议</option>
		                    	<option value="5">5.预留公章样本</option>
		                    	<option value="61">6.个人最高额保证合同1</option>
		                    	<option value="62">6.个人最高额保证合同2</option>
		                    	<option value="71">7.最高额保证合同1</option>
		                    	<option value="72">7.最高额保证合同2</option>
		                    	<option value="8">8.华夏银行小企业网络贷最高额借款合同</option>
		                    	<option value="9">9.华夏银行小企业授信业务实地见证确认书</option>
		                    	<option value="10">10.华夏银行授信业务办理申请书.doc</option>
		                    	<option value="11">11.贷前文件4合1</option>
		                    	<option value="12">12.融信通开立账户信息表</option>
		                    	<option value="13">13.小企业电商贷调查表(execl)</option>
		            		</select>
		           </div>
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
