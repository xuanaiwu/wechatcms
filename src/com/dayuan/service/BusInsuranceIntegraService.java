package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dayuan.mapper.BusInsuranceIntegraMapper;
@Service("busInsuranceIntegraService")
public class BusInsuranceIntegraService<T> extends BaseService<T> {
	
	@Autowired
	private BusInsuranceIntegraMapper<T> mapper;

	@Override
	public BusInsuranceIntegraMapper<T> getMapper() {
		return mapper;
	}

}
