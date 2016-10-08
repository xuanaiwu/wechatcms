package com.dayuan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wechat.devel.MyWechat;

@Controller
@RequestMapping("/wechat") 
public class WechatAction extends BaseAction{
	
	@RequestMapping("/wechatServer")
	public void wechatServer(HttpServletRequest request, HttpServletResponse response)throws Exception{
		/*MyWechat myWechat = new MyWechat(request);
		String result = myWechat.execute();
		response.getOutputStream().write(result.getBytes());*/
		System.out.println("wechatServer");
	}

}
