$package('dy.busLoanAdd');
dy.busLoanAdd = function(){
	var _this = {
		addForm:function(){
			return $("#addForm");
		},
		initForm:function(){
			//保存
			$("#btn-add").click(function(){
				alert("add");
			});
			
			//重置
			$("#btn-reset").click(function(){
				location.reload();
			});
		},
		init:function(){
			_this.initForm();		
		}
	}
	return _this;
}();

$(function(){
	alert("add");
	dy.busLoanAdd.init();
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