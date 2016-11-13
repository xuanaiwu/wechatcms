package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusLoanInfoControllerMapper;
@Service("busLoanInfoControllerService")
public class BusLoanInfoControllerService<T> extends BaseService<T>{
	@Autowired
	private BusLoanInfoControllerMapper<T> mapper;

	@Override
	public BusLoanInfoControllerMapper<T> getMapper() {
		
		return mapper;
	}
	
	
	/**
	 * 保存数据并返回主键id和插入记录数
	 * */
	public int save(T t) throws Exception{
		if(t==null){
			return 0;
		}
		return mapper.save(t);
	}
	
	
	/**
	 *更新返回影响记录数
	 * */
	public int updateReturnInfluences(T t) throws Exception{
		if(t==null){
			return 0;
		}
		return mapper.updateReturnInfluences(t);
		
	}

}
