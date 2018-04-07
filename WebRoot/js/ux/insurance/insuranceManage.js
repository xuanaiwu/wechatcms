$package('dy.insuranceManage');
dy.insuranceManage = function(){
	var _box = null;
	var _this = {
		createWordsAction:'createWords.do',
		uploadExcepAction:'uploadExcel.do',
		toEdit:function(parentId){	
			if(parentId){
				window.location.href="toEdit.do?id="+parentId;
			}else{
				alert("找不到对应记录！");
				return;
			}
		},
		commonsForm:function(){
			return $("#commonsForm");
		},
		createWordsForm:function(){
			return $("#createWordsForm");
		},
		createWordsWin:function(){
			return $("#create-words-win");
		},
		uploadExcelWin:function(){//上传文件窗口
			return $("#upload-excel-win");
		},
		uploadExcelForm:function(){//上传文件form
			return $("#uplaodExcelForm");
		},
		//生成文件,不需要返回值，用于在线下载
		createWords:function(){
			if(_this.createWordsForm().form('validate')){
				_this.createWordsForm().attr('action',_this.createWordsAction);
				_this.createWordsForm().submit();
				_this.createWordsWin().dialog('close');
			}
		},
		
		//上传excel文件，导入数据
		uploadExcel:function(){
			//$("#uplaodExcelForm").ajaxSubmit(options);//提交代码时看看base.js有没有被修改...
			_this.uploadExcelForm().attr('action',_this.uploadExcepAction);
			_this.uploadExcelForm().submit();
			_this.uploadExcelWin().dialog('close');
		},
		initForm:function(){
			//确定，生成文件
			_this.createWordsWin().find("#btn-create-submit").click(function(){
				_this.createWords();
			});
			//关闭
			_this.createWordsWin().find("#btn-create-close").click(function(){	
				$.messager.confirm('Confirm','你确定要关闭当前窗口?',function(r){  
				    if (r){  
				     	_this.createWordsWin().dialog('close');
				    }  
				});
			});
			
			
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
  				title:'保险贷信息列表',
	   			url:'dataList.do',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'loanPerson',title:'借款人',width:120,sortable:true},
						{field:'mortgagePerson',title:'抵押人',width:135,sortable:true},
						{field:'creditPerson',title:'特别授信借款人',width:120,sortable:true},
						{field:'otherHappening',title:'其他情况',width:120,sortable:true},
						{field:'createTime',title:'创建时间',width:120,sortable:true},
						{field:'updateTime',title:'修改时间',width:120,sortable:true},
						{field:'childMenus',title:'操作',width:200,align:'center',formatter:function(value,row,index){
							var html ="<a href='#' onclick='dy.insuranceManage.toEdit("+row.id+")'>修改</a>";
							return html;
						}}
				]],
				toolbar:[{id:'excel',text:'数据导入模版下载',btnType:'excelDownloan',iconCls:'icon-edit',handler:function(){
					//用表单form的方式提交
					_this.commonsForm().attr('action','templateDownload.do');
					_this.commonsForm().submit();
				}},
				{id:'word',text:'数据导入',btnType:'excelUpload',iconCls:'icon-edit',handler:function(){
					_this.uploadExcelForm().resetForm();
					_this.uploadExcelWin().window('open'); 
				}},
				{id:'word',text:'生成Word',btnType:'createWords',iconCls:'icon-edit',handler:function(){
					var selected = _box.utils.getCheckedRows();
					if ( _box.utils.checkSelectOne(selected)){
						_this.createWordsForm().resetForm();
						_this.createWordsForm().find("input[name='id']").val(selected[0].id);
						_this.createWordsWin().window('open'); 
					}
				}},
				{id:'excel',text:'导出保险贷台帐',btnType:'exportExcel',iconCls:'icon-edit',handler:function(){
					//用表单form的方式提交
					_this.commonsForm().attr('action','exportExcel.do');
					_this.commonsForm().submit();
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
	dy.insuranceManage.init();
});