package com.dayuan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuan.mapper.BusInsuranceGuaranteeMapper;
@Service("busInsuranceGuaranteeService")
public class BusInsuranceGuaranteeService<T> extends BaseService<T>{
	
	@Autowired
	private BusInsuranceGuaranteeMapper<T> busInsuranceGuaranteeMapper;
	@Override
	public BusInsuranceGuaranteeMapper<T> getMapper() {

		return busInsuranceGuaranteeMapper;
	}
	
	
	/**
	 * 保存BusInsuranceGuarantee
	 * @return 影响记录数
	 * */
	public int addT(T t){
		if(t==null){
			return 0;
		}
		return busInsuranceGuaranteeMapper.addT(t);
	}
	
	
	/**
	 * 更新BusInsuranceGuarantee
	 * @return 影响记录数
	 * */
	public int updateT(T t){
		if(t==null){
			return 0;
		}
		return busInsuranceGuaranteeMapper.updateT(t);
	}
	
	/**
	 *根据bid查询BusInsuranceGuarantee
	 *@return list
	 * */
	public List<T> queryBybid(Object id){
		if(id==null){
			return null;
		}
		return busInsuranceGuaranteeMapper.queryBybid(id);
	}
	
	
	/**
	 * 根据bid删除BusInsuranceGuarantee
	 * @return int
	 * */
	public int deleteBybid(Object id){
		if(id==null){
			return 0;
		}
		return busInsuranceGuaranteeMapper.deleteBybid(id);
	}

}
