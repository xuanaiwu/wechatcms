package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dayuan.mapper.BusFileMapper;

@Service("busFileService")
public class BusFileService<T> extends BaseService<T> {
	
	@Autowired
	private BusFileMapper<T> busFileMapper;
	
	@Override
	public BusFileMapper<T> getMapper() {
		
		return busFileMapper;
	}
	
	/**
	 * 保存BusFiLe
	 * @return 影响记录数
	 * */
	public int save(T t) throws Exception{
		if(t==null){
			return 0;
		}
		return busFileMapper.save(t);
	}
	
	
	/**
	 *更新返回影响记录数
	 * */
	public int updateReturnInfluences(T t) throws Exception{
		if(t==null){
			return 0;
		}
		return busFileMapper.updateReturnInfluences(t);
		
	}

}
