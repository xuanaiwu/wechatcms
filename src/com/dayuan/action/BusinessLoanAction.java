package com.dayuan.action;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping("/BusLoan") 
public class BusinessLoanAction extends BaseAction{
	
	
	@RequestMapping("/loan") 
	public ModelAndView loan(HttpServletRequest request)throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("bus/busLoan",context); 
	}
	

}
