package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusInsuranceVerificationMapper;

@Service("busInsuranceVerificationService")
public class BusInsuranceVerificationService<T> extends BaseService<T> {
	
	@Autowired
	private BusInsuranceVerificationMapper<T> busInsuranceVerificationMapper;
	
	@Override
	public BusInsuranceVerificationMapper<T> getMapper() {
		
		return busInsuranceVerificationMapper;
	}
	
	
	/**
	 * 保存BusInsuranceVerification
	 * @return 影响记录数
	 * */
	public int addT(T t){
		if(t==null){
			return 0;
		}
		return busInsuranceVerificationMapper.addT(t);
	}
	
	
	/**
	 * 更新BusInsuranceVerification
	 * @return 影响记录数
	 * */
	public int updateT(T t){
		if(t==null){
			return 0;
		}
		return busInsuranceVerificationMapper.updateT(t);
	}
	
	
	
	/**
	 * 根据bid查询BusInsuranceVerification
	 * @return T
	 * */
	public T queryBybid(Object id){
		if(id==null){
			return null;
		}
		return busInsuranceVerificationMapper.queryBybid(id);
	}
	
	
	/**
	 * 根据bid删除BusInsuranceVerification
	 * @return int
	 * */
	public int deleteBybid(Object id){
		if(id==null){
			return 0;
		}
		return busInsuranceVerificationMapper.deleteBybid(id);
	}

}
