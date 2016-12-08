package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusLendingMapper;

@Service("busLendingService")
public class BusLendingService<T> extends BaseService<T> {
	
	@Autowired
	private BusLendingMapper<T> mapper;
	
	@Override
	public BusLendingMapper<T> getMapper() {
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
	 * 跟bid返回list
	 * */
	public T queryByBId(Object bid) throws Exception{
		if(bid==null){
			return null;
		}
		return mapper.queryByBId(bid);
		
	}
	
	/**根据lId删除数据*/
	public void deleteByLId(Object lId)throws Exception{
		if(lId!=null){
			mapper.deleteByLId(lId);
		}
		
	}
	

}
