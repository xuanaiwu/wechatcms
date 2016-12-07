package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusBilingMapper;

@Service("busBilingService")
public class BusBilingService<T> extends BaseService<T>{
	
	@Autowired
	private BusBilingMapper<T> mapper;
	
	@Override
	public BusBilingMapper<T> getMapper() {
		
		return mapper;
	}

	/**
	 * 保存
	 * @return 影响记录数
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
	
	
	/**
	 * 根据bid返回对象
	 * */
	public T queryByBId(Object bid)throws Exception{
		if(bid==null){
			return null;
		}
		return mapper.queryByBId(bid);
		
	}
	

}
