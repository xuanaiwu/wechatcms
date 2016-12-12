$package('dy.filing');
dy.filing = function(){
	var _box = null;
	var _this = {
		createWordsAction:'createWords.do',
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
		//生成words,不需要返回值，用于在线下载
		createWords:function(){
			if(_this.createWordsForm().form('validate')){
				_this.createWordsForm().attr('action',_this.createWordsAction);
				_this.createWordsForm().submit();
				_this.createWordsWin().dialog('close');
			}
		},
		initForm:function(){
			//确定，生成文书
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
					{id:'word',text:'生成Word',btnType:'createWords',iconCls:'icon-edit',handler:function(){
							var selected = _box.utils.getCheckedRows();
							if ( _box.utils.checkSelectOne(selected)){
								_this.createWordsForm().resetForm();
								_this.createWordsForm().find("input[name='id']").val(selected[0].id);
								_this.createWordsWin().window('open'); 
							}
						}},
						{id:'excel',text:'导出Excel',btnType:'exportExcel',iconCls:'icon-edit',handler:function(){
								
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
	dy.filing.init();
});