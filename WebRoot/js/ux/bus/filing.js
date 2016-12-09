$package('dy.filing');
dy.filing = function(){
	var _box = null;
	var _this = {
		createWordsAction:'createWordsOnLine.do',
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
  				title:'电商贷信息列表',
	   			url:'dataList.do',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'lName',title:'姓名',width:120,sortable:true},
						{field:'lIdCard',title:'身份证',width:135,sortable:true},
						{field:'lTelPhone',title:'电话',width:120,sortable:true},
						{field:'lStatus',title:'当前状态',width:120,sortable:true},
						{field:'createTime2',title:'创建时间',width:120,sortable:true},
						{field:'updateTime2',title:'修改时间',width:120,sortable:true},
						{field:'childMenus',title:'操作',width:200,align:'center',formatter:function(value,row,index){
							var html ="<a href='#' onclick='dy.filing.toEdit("+row.id+")'>修改</a>";
							return html;
						}}
				]],
				toolbar:[
					{id:'btnedit',text:'修改',btnType:'edit222'},
					{id:'word',text:'生成word',btnType:'edit222',iconCls:'icon-edit',handler:function(){
							var selected = _box.utils.getCheckedRows();
							if ( _box.utils.checkSelectOne(selected)){
								_this.editPwdForm().resetForm();
								_this.editPwdForm().find("input[name='id']").val(selected[0].id);
								//_this.editPwdForm().find("input[name='email']").val(selected[0].email);
								_this.editPwdWin().window('open'); 
							}
						}},
						{id:'excel',text:'导出excel',btnType:'edit222',iconCls:'icon-edit',handler:function(){
							var selected = _box.utils.getCheckedRows();
							if ( _box.utils.checkSelectOne(selected)){
								
								/**1
								 * var iframe = document.createElement("iframe");
								iframe.style.display = "none";
								iframe.id = "iframe";
								document.body.appendChild(iframe);
								document.getElementById("iframe").src = "exportExcel.do";
								*/
								
								//2
								_this.editPwdForm().find("input[name='id']").val(selected[0].id);
								_this.editPwdForm().attr('action','exportExcel.do');
								_this.editPwdForm().submit();
								alert("导出成功！");
								
								
								//3,下载时不能用异步
								//$.post("exportExcel.do",{id:selected[0].id},function(data,status){
									//alert("Data: " + data + "\nStatus: " + status);
									//alert("导出成功！");
								//});
								
								
							}
						}},
						{id:'excel',text:'导出Excel',btnType:'exportExcel',iconCls:'icon-edit',handler:function(){
								
								//用表单form的方式提交
								_this.commonsForm().attr('action','exportExcel.do');
								_this.commonsForm().submit();
								
								
								
								alert("导出成功！");

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
	dy.filing.init();
});