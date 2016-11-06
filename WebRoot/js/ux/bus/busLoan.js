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
						{field:'relationship',title:'关系',width:120,sortable:true}
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

//添加行  
function addShopRow(){
	var num=$("#rowCount").val();//取值
	num=parseInt(num);
	num++;
	var str='<td><a href="#" onclick=delShopRow('+num+')>删除</a></td>';
	$("#tShopRow0").clone(true).attr("id","tShopRow"+num).appendTo("#tblShopData");
	$("#tShopRow"+num+"td").each(function(){
		$(this).find("input[type='text']").val("");//清空数据
		$(this).find("input[name='shopName']").attr("id","shopName"+num).attr("name","shopName"+num);
		$(this).find("input[name='platformName']").attr("id","platformName"+num).attr("name","platformName"+num);
		$(this).find("input[name='shopLevel']").attr("id","shopLevel"+num).attr("name","shopLevel"+num);
		$(this).find("input[name='operatingPeriod']").attr("id","operatingPeriod"+num).attr("name","operatingPeriod"+num);
		$(this).find("input[name='shopOwner']").attr("id","shopOwner"+num).attr("name","shopOwner"+num);
		$(this).find("input[name='subAccount']").attr("id","subAccount"+num).attr("name","subAccount"+num);
		$(this).find("input[name='sbuPassword']").attr("id","sbuPassword"+num).attr("name","sbuPassword"+num);
		$(this).find("input[name='businessOpera']").attr("id","businessOpera"+num).attr("name","businessOpera"+num);
		$(this).find("input[name='businessAddress']").attr("id","businessAddress"+num).attr("name","businessAddress"+num);
		$(this).find("input[name='warehouseAddress']").attr("id","warehouseAddress"+num).attr("name","warehouseAddress"+num);
		$(this).find("input[name='salesIncome']").attr("id","salesIncome"+num).attr("name","salesIncome"+num);
		$(this).find("input[name='totalLiability']").attr("id","totalLiability"+num).attr("name","totalLiability"+num);
		$(this).find("input[name='bankLiabilities']").attr("id","bankLiabilities"+num).attr("name","bankLiabilities"+num);
		$(this).find("input[name='netProfit']").attr("id","netProfit"+num).attr("name","netProfit"+num);
	});
    $('#rowCount').val(num);//重新赋值
    $("#tShopRow"+num).append(str);
}  
//删除行  
function delShopRow(rowIndex){  
	var num=$("#rowCount").val();//取值
	num=parseInt(num);
	if(rowIndex>0&&num>0){
		$("#tShopRow"+rowIndex).remove();
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
	var str='<td><a href="#" onclick=delGuaranterRow('+num+')>删除</a></td>';
	$("#tRow0").clone(true).attr("id","tRow"+num).appendTo("#tblData");
	$("#tRow"+num+"td").each(function(){
		$(this).find("input[type='text']").val("");//清空数据
		$(this).find("input[name='guaranterName']").attr("id","guaranterName"+num).attr("name","guaranterName"+num);
		$(this).find("input[name='guaranterCard']").attr("id","guaranterCard"+num).attr("name","guaranterCard"+num);
		$(this).find("input[name='guaranterEmployer']").attr("id","guaranterEmployer"+num).attr("name","guaranterEmployer"+num);
		$(this).find("input[name='guaranterDuties']").attr("id","guaranterDuties"+num).attr("name","guaranterDuties"+num);
		$(this).find("input[name='guaranterPhone']").attr("id","guaranterPhone"+num).attr("name","guaranterPhone"+num);
		$(this).find("input[name='guaranterMaritalStatus']").attr("id","guaranterMaritalStatus"+num).attr("name","guaranterMaritalStatus"+num);
		$(this).find("input[name='guaranterHouseAddress']").attr("id","guaranterHouseAddress"+num).attr("name","guaranterHouseAddress"+num);
		$(this).find("input[name='guaranterMonthlyIncome']").attr("id","guaranterMonthlyIncome"+num).attr("name","guaranterMonthlyIncome"+num);
		$(this).find("input[name='guaranterValues']").attr("id","guaranterValues"+num).attr("name","guaranterValues"+num);
		$(this).find("input[name='guaranterTotalLiabilities']").attr("id","guaranterTotalLiabilities"+num).attr("name","guaranterTotalLiabilities"+num);
	});
	$("#guaranterRowCount").val(num);//重新赋值
	$("#tRow"+num).append(str);
}
//保证人删除行
function delGuaranterRow(rowIndex){
	var num=$("#guaranterRowCount").val();
	num=parseInt(num);
	if(rowIndex>0&&num>0){//判断是不是第一行
		$("#tRow"+rowIndex).remove();
		num--;//删除后要自减
		$('#guaranterRowCount').val(num);//重新赋值
	}else{
		alert("这是第一行了！");
	}
}