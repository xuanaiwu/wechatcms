$package('dy.busLoan');
dy.busLoan = function(){
	var _box = null;
	var _this = {
		createWordsAction:'createWords.do',
		editPwdForm:function(){
			return $("#pwdForm");
		},
		editPwdWin:function(){
			return $("#edit-pwd-win");
		},
		createSave:function(){
			if(_this.editPwdForm().form('validate')){
				_this.editPwdForm().attr('action',_this.createWordsAction);
				dy.saveForm(_this.editPwdForm(),function(data){
					_this.editPwdWin().dialog('close');
				});
			 }
		},
		initForm:function(){
			//生成文书
			_this.editPwdWin().find("#btn-pwd-submit").click(function(){
				_this.createSave();
			});
			_this.editPwdWin().find("#btn-pwd-close").click(function(){	
				$.messager.confirm('Confirm','你确定要关闭当前窗口?',function(r){  
				    if (r){  
				     	_this.editPwdWin().dialog('close');
				    }  
				});
			});
		},
		config:{
  			dataGrid:{
  				title:'商贷资料信息列表',
	   			url:'dataList.do',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'surveyOrgName',title:'调查机构名称',width:120,sortable:true},
						{field:'surveyPersonName',title:'调查人姓名',width:80,sortable:true},
						{field:'surveyPhone',title:'手机',width:120,sortable:true},
						{field:'applicationName',title:'借款申请人名称',align:'right',width:80,sortable:true},
						{field:'applicationAmount',title:'申请金额',width:120,sortable:true},
						{field:'applicationTerm',title:'申请期限',width:120,sortable:true},
						{field:'loanType',title:'申请贷款类型',width:120,sortable:true},
						{field:'urgentCont',title:'紧急联系人',width:120,sortable:true},
						{field:'urgentContPhone',title:'紧急联系人电话',width:120,sortable:true},
						{field:'urgentContAddress',title:'紧急联系人地址',width:120,sortable:true},
						{field:'relationship',title:'关系',width:120,sortable:true},
						{field:'legalPerson',title:'法人姓名',width:120,sortable:true},
						{field:'gender',title:'性别',width:120,sortable:true},
						{field:'idCard',title:'身份证',width:120,sortable:true},
						{field:'companyName',title:'公司名称',width:120,sortable:true}
						
				]],
				toolbar:[
					{id:'btnadd',text:'添加',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btnedit',text:'生成word',btnType:'edit',iconCls:'icon-edit',handler:function(){
							var selected = _box.utils.getCheckedRows();
							if ( _box.utils.checkSelectOne(selected)){
								_this.editPwdForm().resetForm();
								_this.editPwdForm().find("input[name='id']").val(selected[0].id);
								//_this.editPwdForm().find("input[name='email']").val(selected[0].email);
								_this.editPwdWin().window('open'); 
							}
						}},
					{id:'btndelete',text:'删除',btnType:'remove'}
				]
			}
		},
		init:function(){
			_this.initForm();
			_box = new YDataGrid(_this.config); 
			_box.init();
			
		}
	}
	return _this;
}();

$(function(){
	dy.busLoan.init();
	
});	

var rowCount=0;  //从0开始
//添加行  
function addRow(){  
    rowCount++;
    var newRow='<br/><div id="option'+rowCount+'"class="ui-edit"  style="padding-top:5px"><div class="fitem">'+
    '<label>经营网店名称:</label><input class="easyui-validatebox" type="text" name="shopName'+rowCount+'">'+
    '<label>所属电商平台名称:</label><input class="easyui-validatebox" type="text" name="platformName'+rowCount+'"><label>网店级别（仅限淘宝及天猫商户）:</label><input class="easyui-validatebox" type="text" name="shopLevel'+rowCount+'"><label>网店持续经营年限:</label><input class="easyui-validatebox" type="text" name="operatingPeriod'+rowCount+'"></div>'+
    '<div class="fitem"><label>网店实际所有者（个人名称或公司名称):</label><input class="easyui-validatebox" type="text" name="shopOwner'+rowCount+'"><label>子帐号:</label>  <input class="easyui-validatebox" type="text" name="subAccount'+rowCount+'"><label>密码:</label>  <input class="easyui-validatebox" type="text" name="sbuPassword'+rowCount+'"><label>主营业务及主要产品、品牌:</label><input class="easyui-validatebox" type="text" name="businessOpera'+rowCount+'"></div>'+
    '<div class="fitem"><label>经营地址（如有）:</label><input class="easyui-validatebox" type="text" name="businessAddress'+rowCount+'" ><label>仓库地址:</label><input class="easyui-validatebox" type="text" name="warehouseAddress'+rowCount+'" ><label>经营实体上年度销售/营业收入:</label><input class="easyui-validatebox" type="text" name="salesIncome'+rowCount+'"><label>经营实体总负债:</label>  <input class="easyui-validatebox" type="text" name="totalLiability'+rowCount+'"></div>'+
    '<div class="fitem"><label>银行负债:</label>  <input class="easyui-validatebox" type="text" name="bankLiabilities'+rowCount+'" ><label>上年度净利润:</label><input class="easyui-validatebox" type="text" name="netProfit'+rowCount+'"></div>'+
    '<br /><div style="text-align:center;"><a href="#" onclick=delRow('+rowCount+')>删除</a></div></div>';
    
    //var newRow='<tr id="option'+rowCount+'"><td class="oz-form-topLabel">选项'+rowCount+'：</td><td class="oz-property" ><input type="text"  style="width:300px"></td><td><a href="#" onclick=delRow('+rowCount+')>删除</a></td></tr>';
    
    $('#optionContainer').append(newRow);
    $('#rowCount').val(rowCount);
}  
//删除行  
function delRow(rowIndex){  
    $("#option"+rowIndex).remove();  
    rowCount--;
    $('#rowCount').val(rowCount);
}  