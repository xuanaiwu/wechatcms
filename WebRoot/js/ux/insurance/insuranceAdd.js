$package('dy.insuranceAdd');
dy.insuranceAdd = function(){
	var _this = {
		addForm:function(){
			return $("#addForm");
		},
		initForm:function(){
			//保存
			$("#btn-add").click(function(callback){
				if(_this.addForm().form('validate')){
					_this.addForm().attr('action','save.do');
					dy.saveForm(_this.addForm(),function(data){
						//alert("建档成功！");
						window.location.href="insuranceManage.shtml";
						//location.reload();
					});
			     }
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
	dy.insuranceAdd.init();
});	

//保证人添加行
function addGuaranterRow(){
	var num=$("#guaranterRowCount").val();
	num=parseInt(num);
	num++;//点击自加
	$("#tRow0").clone(true).attr("id","tRow"+num).appendTo("#tblData");
	$("#tRow"+num+" td").each(function(){
		$(this).find("input[type='text']").val("");//清空数据
		$(this).find("input[name='guarantee[0].guaranteeNumber']").attr("id","guarantee['"+num+"'].guaranteeNumber").attr("name","guarantee['"+num+"'].guaranteeNumber");
		$(this).find("input[name='guarantee[0].guaranteePersonName']").attr("id","guarantee['"+num+"'].guaranteePersonName").attr("name","guarantee['"+num+"'].guaranteePersonName");
		$(this).find("input[name='guarantee[0].guaranteePersonIdCard']").attr("id","guarantee['"+num+"'].guaranteePersonIdCard").attr("name","guarantee['"+num+"'].guaranteePersonIdCard");
		$(this).find("input[name='guarantee[0].lguaranteePersonPhone']").attr("id","guarantee['"+num+"'].lguaranteePersonPhone").attr("name","guarantee['"+num+"'].lguaranteePersonPhone");
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

$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	//return m+'/'+d+'/'+y;
	return y+"-"+m+'-'+d;
}