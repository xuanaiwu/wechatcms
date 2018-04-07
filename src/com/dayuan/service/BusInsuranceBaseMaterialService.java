package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dayuan.mapper.BusInsuranceBaseMaterialMapper;

@Service("busInsuranceBaseMaterialService")
public class BusInsuranceBaseMaterialService<T> extends BaseService<T> {
	
	@Autowired
	private BusInsuranceBaseMaterialMapper<T> busInsuranceBaseMaterialMapper;

	@Override
	public BusInsuranceBaseMaterialMapper<T> getMapper() {
		return busInsuranceBaseMaterialMapper;
	}
	
	
	/**
	 * 保存BusInsuranceBaseMaterial
	 * @return 影响记录数
	 * */
	public int addT(T t){
		if(t==null){
			return 0;
		}
		return busInsuranceBaseMaterialMapper.addT(t);
	}
	
	
	/**
	 * 更新BusInsuranceBaseMaterial
	 * @return 影响记录数
	 * */
	public int updateT(T t){
		if(t==null){
			return 0;
		}
		return busInsuranceBaseMaterialMapper.updateT(t);
	}

}
