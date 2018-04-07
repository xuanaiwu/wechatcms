$package('dy.swIndexManage');
dy.swIndexManage = function(){
	var _box = null;
	var _this = {
		uploadExcepAction:'uploadExcel.do',
		toEdit:function(parentId){	
			if(parentId){
				window.location.href="toEdit.do?id="+parentId;
			}else{
				alert("找不到对应记录！");
				return;
			}
		},
		uploadExcelWin:function(){//上传文件窗口
			return $("#upload-excel-win");
		},
		uploadExcelForm:function(){//上传文件form
			return $("#uplaodExcelForm");
		},
		//上传excel文件，导入数据
		uploadExcel:function(){
			//$("#uplaodExcelForm").ajaxSubmit(options);//提交代码时看看base.js有没有被修改...
			_this.uploadExcelForm().attr('action',_this.uploadExcepAction);
			_this.uploadExcelForm().submit();
			_this.uploadExcelWin().dialog('close');
		},
		initForm:function(){
			
			//确定，导入文件
			_this.uploadExcelWin().find("#btn-upload-submit").click(function(){
				_this.uploadExcel();
			});
			//关闭
			_this.uploadExcelWin().find("#btn-upload-close").click(function(){	
				$.messager.confirm('Confirm','你确定要关闭当前窗口?',function(r){  
				    if (r){  
				     	_this.uploadExcelWin().dialog('close');
				    }  
				});
			});
		},
		config:{
  			dataGrid:{
  				title:'申万指数行情列表',
	   			url:'dataList.do',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'indexCode',title:'指数代码',width:120,sortable:true},
						{field:'indexName',title:'指数名称',width:135,sortable:true},
						{field:'releaseDate',title:'发布日期',width:120,sortable:true},
						{field:'indexStart',title:'开盘指数',width:120,sortable:true},
						{field:'indexEnd',title:'收盘指数',width:120,sortable:true},
						{field:'indexHigh',title:'当天最高指数',width:120,sortable:true},
						{field:'indexLow',title:'当天最低指数',width:120,sortable:true},
						{field:'turnover',title:'成交量（亿股）',width:120,sortable:true},
						{field:'volume',title:'成交额（亿元）',width:120,sortable:true},
						{field:'range',title:'涨跌幅（%）',width:120,sortable:true},
						{field:'trade',title:'换手率（%）',width:120,sortable:true},
						{field:'pe',title:'市盈率（倍）',width:120,sortable:true},
						{field:'pb',title:'市净率（倍）',width:120,sortable:true},
						{field:'average',title:'均价',width:120,sortable:true},
						{field:'volumeRatio',title:'成交额占比（%）',width:120,sortable:true},
						{field:'circulateValue',title:'流通市值（亿元）',width:120,sortable:true},
						{field:'averageCirculateValue',title:'平均流通市值（亿元）',width:120,sortable:true},
						{field:'dividendRatio',title:'股息率（%）',width:120,sortable:true},
						{field:'childMenus',title:'操作',width:200,align:'center',formatter:function(value,row,index){
							var html ="<a href='#' onclick='dy.swIndexManage.toEdit("+row.id+")'>修改</a>";
							return html;
						}}
				]],
				toolbar:[
				         {id:'excelUpload',text:'数据导入',btnType:'excelUpload',iconCls:'icon-edit',handler:function(){
						_this.uploadExcelForm().resetForm();
						_this.uploadExcelWin().window('open'); 
				         }}
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
	dy.swIndexManage.init();
});