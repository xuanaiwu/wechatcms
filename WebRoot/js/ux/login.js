$package('dy.login');
dy.login = function(){
	return {
		toLogin:function(){
			try{
				var form = $("#loginForm");
				if(form.form('validate')){
					dy.progress('Please waiting','Loading...');
					dy.submitForm(form,function(data){
						dy.closeProgress();
						//dy.login.loadVrifyCode();//刷新验证码,电商贷不需要验证码 20161130
						if(data.success){
					 		window.location= "main.shtml";
				        }else{
				       	   dy.alert('提示',data.msg,'error');  
				        }
					});
				}
			}catch(e){
				
			}
			return false;
		},
		loadVrifyCode:function(){//刷新验证码
			var _url = "ImageServlet?time="+new Date().getTime();
			$(".vc-pic").attr('src',_url);
		},
		init:function(){
			if(window.top != window.self){
				window.top.location =  window.self.location;
			}
			//验证码图片绑定点击事件
			//$(".vc-pic").click(dy.login.loadVrifyCode);电商贷不需要验证码 20161130
			
			var form = $("#loginForm");
			form.submit(dy.login.toLogin);
		}
	}
}();

$(function(){
	dy.login.init();
});		