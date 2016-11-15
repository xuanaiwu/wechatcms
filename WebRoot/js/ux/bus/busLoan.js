$package('dy.busLoan');
dy.busLoan = function(){
	var _box = null;
	var _this = {
		createWordsAction:'createWordsOnLine.do',
		editPwdForm:function(){
			return $("#pwdForm");
		},
		editPwdWin:function(){
			return $("#edit-pwd-win");
		},
		//生成words,需要返回值
		createSave:function(){
			if(_this.editPwdForm().form('validate')){
				_this.editPwdForm().attr('action',_this.createWordsAction);
				dy.saveForm(_this.editPwdForm(),function(data){
					_this.editPwdWin().dialog('close');
				});
			 }
		},
		//生成words,不需要返回值，用于在线下载
		createWords:function(){
			if(_this.editPwdForm().form('validate')){
				_this.editPwdForm().attr('action',_this.createWordsAction);
				_this.editPwdForm().submit();
				_this.editPwdWin().dialog('close');
				alert("生成成功!");
			}
		},
		initForm:function(){
			//确定，生成文书
			_this.editPwdWin().find("#btn-pwd-submit").click(function(){
				_this.createWords();
			});
			//关闭
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
						{field:'content',title:'内容',width:200,sortable:true},
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
						{field:'relationship',title:'关系',width:120,sortable:true}
				]],
				toolbar:[
					{id:'btnadd',text:'添加',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'word',text:'生成word',btnType:'edit',iconCls:'icon-edit',handler:function(){
							var selected = _box.utils.getCheckedRows();
							if ( _box.utils.checkSelectOne(selected)){
								_this.editPwdForm().resetForm();
								_this.editPwdForm().find("input[name='id']").val(selected[0].id);
								//_this.editPwdForm().find("input[name='email']").val(selected[0].email);
								_this.editPwdWin().window('open'); 
							}
						}},
						{id:'excel',text:'导出excel',btnType:'exportExcel',iconCls:'icon-edit',handler:function(){
							var selected = _box.utils.getCheckedRows();
							if ( _box.utils.checkSelectOne(selected)){
								_this.editPwdForm().find("input[name='id']").val(selected[0].id);
								$.post("exportExcel.do",{id:selected[0].id},function(data,status){
									//alert("Data: " + data + "\nStatus: " + status);
									alert("导出成功！");
								});
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

//添加行  
function addShopRow(){
	var num=$("#rowCount").val();//取值
	num=parseInt(num);
	num++;
	$("#tShopRow0").clone(true).attr("id","tShopRow"+num).appendTo("#tblShopData");
	$("#tShopRow"+num+" td").each(function(){
		$(this).find("input[type='text']").val("");//清空数据
		$(this).find("input[name='shop[0].shopName']").attr("id","shop['"+num+"'].shopName").attr("name","shop['"+num+"'].shopName");
		$(this).find("input[name='shop[0].platformName']").attr("id","shop['"+num+"'].platformName").attr("name","shop['"+num+"'].platformName");
		$(this).find("input[name='shop[0].shopLevel']").attr("id","shop['"+num+"'].shopLevel").attr("name","shop['"+num+"'].shopLevel");
		$(this).find("input[name='shop[0].operatingPeriod']").attr("id","shop['"+num+"'].operatingPeriod").attr("name","shop['"+num+"'].operatingPeriod");
		$(this).find("input[name='shop[0].shopOwner']").attr("id","shop['"+num+"'].shopOwner").attr("name","shop['"+num+"'].shopOwner");
		$(this).find("input[name='shop[0].subAccount']").attr("id","shop['"+num+"'].subAccount").attr("name","shop['"+num+"'].subAccount");
		$(this).find("input[name='shop[0].sbuPassword']").attr("id","shop['"+num+"'].sbuPassword").attr("name","shop['"+num+"'].sbuPassword");
		$(this).find("input[name='shop[0].businessOpera']").attr("id","shop['"+num+"'].businessOpera").attr("name","shop['"+num+"'].businessOpera");
		$(this).find("input[name='shop[0].businessAddress']").attr("id","shop['"+num+"'].businessAddress").attr("name","shop['"+num+"'].businessAddress");
		$(this).find("input[name='shop[0].warehouseAddress']").attr("id","shop['"+num+"'].warehouseAddress").attr("name","shop['"+num+"'].warehouseAddress");
		$(this).find("input[name='shop[0].salesIncome']").attr("id","shop['"+num+"'].salesIncome").attr("name","shop['"+num+"'].salesIncome");
		$(this).find("input[name='shop[0].totalLiability']").attr("id","shop['"+num+"'].totalLiability").attr("name","shop['"+num+"'].totalLiability");
		$(this).find("input[name='shop[0].bankLiabilities']").attr("id","shop['"+num+"'].bankLiabilities").attr("name","shop['"+num+"'].bankLiabilities");
		$(this).find("input[name='shop[0].netProfit']").attr("id","shop['"+num+"'].netProfit").attr("name","shop['"+num+"'].netProfit");
	});
    $('#rowCount').val(num);//重新赋值
}  
//删除行  
function delShopRow(){  
	var num=$("#rowCount").val();//取值
	num=parseInt(num);
	if(num>0){
		$("#tShopRow"+num).remove();
		num--;
		 $('#rowCount').val(num);//重新赋值
	}else{
		alert("这是第一行了！");
	}
	
}
//保证人添加行
function addGuaranterRow(){
	var num=$("#guaranterRowCount").val();
	num=parseInt(num);
	num++;//点击自加
	$("#tRow0").clone(true).attr("id","tRow"+num).appendTo("#tblData");
	$("#tRow"+num+" td").each(function(){
		$(this).find("input[type='text']").val("");//清空数据
		$(this).find("input[name='guaranter[0].guaranterName']").attr("id","guaranter['"+num+"'].guaranterName").attr("name","guaranter['"+num+"'].guaranterName");
		$(this).find("input[name='guaranter[0].guaranterCard']").attr("id","guaranter['"+num+"'].guaranterCard").attr("name","guaranter['"+num+"'].guaranterCard");
		$(this).find("input[name='guaranter[0].guaranterEmployer']").attr("id","guaranter['"+num+"'].guaranterEmployer").attr("name","guaranter['"+num+"'].guaranterEmployer");
		$(this).find("input[name='guaranter[0].guaranterDuties']").attr("id","guaranter['"+num+"'].guaranterDuties").attr("name","guaranter['"+num+"'].guaranterDuties");
		$(this).find("input[name='guaranter[0].guaranterPhone']").attr("id","guaranter['"+num+"'].guaranterPhone").attr("name","guaranter['"+num+"'].guaranterPhone");
		$(this).find("select[name='guaranter[0].guaranterMaritalStatus']").attr("id","guaranter['"+num+"'].guaranterMaritalStatus").attr("name","guaranter['"+num+"'].guaranterMaritalStatus");
		$(this).find("input[name='guaranter[0].guaranterHouseAddress']").attr("id","guaranter['"+num+"'].guaranterHouseAddress").attr("name","guaranter['"+num+"'].guaranterHouseAddress");
		$(this).find("input[name='guaranter[0].guaranterMonthlyIncome']").attr("id","guaranter['"+num+"'].guaranterMonthlyIncome").attr("name","guaranter['"+num+"'].guaranterMonthlyIncome");
		$(this).find("input[name='guaranter[0].guaranterValues']").attr("id","guaranter['"+num+"'].guaranterValues").attr("name","guaranter['"+num+"'].guaranterValues");
		$(this).find("input[name='guaranter[0].guaranterTotalLiabilities']").attr("id","guaranter['"+num+"'].guaranterTotalLiabilities").attr("name","guaranter['"+num+"'].guaranterTotalLiabilities");
	});
	$("#guaranterRowCount").val(num);//重新赋值
}
//保证人删除行
function delGuaranterRow(){
	var num=$("#guaranterRowCount").val();
	num=parseInt(num);
	if(num>0){//判断是不是第一行
		$("#tRow"+num).remove();
		num--;//删除后要自减
		$('#guaranterRowCount').val(num);//重新赋值
	}else{
		alert("这是第一行了！");
	}
}