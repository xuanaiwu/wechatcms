package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusLoanInfoMapper;

@Service("busLoanInfoService")
public class BusLoanInfoService<T> extends BaseService<T>{
	
	@Autowired
	private BusLoanInfoMapper<T> mapper;

	
	public BusLoanInfoMapper<T> getMapper() {
		
		return mapper;
	}

	

}
