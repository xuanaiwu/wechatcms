package com.dayuan.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusLoanInfoGuaranterMapper;
@Service("busLoanInfoGuaranterService")
public class BusLoanInfoGuaranterService<T> extends BaseService<T> {
	@Autowired
	private BusLoanInfoGuaranterMapper<T> mapper;

	@Override
	public BusLoanInfoGuaranterMapper<T> getMapper() {
	
		return mapper;
	}
	
	public int save(T t){
		if(t==null){
			return 0;
		}
		return mapper.save(t);
		
	}
	
	
	public int updateReturnInfluences(T t){
		if(t==null){
			return 0;
		}
		return mapper.updateReturnInfluences(t);
	}
	
	/**
	 * 
	 * 根据bid返回单个对象
	 * 
	 * */
	public T getBusLoanInfoGuaranter(Object bid) throws Exception{
		if(bid==null){
			return null;
		}
		return getMapper().getBusLoanInfoGuaranter(bid);
	}
	
	
	public void deleteByBid(Object bid){
		if(bid!=null){
			mapper.deleteByBid(bid);
		}
		
	}

}
