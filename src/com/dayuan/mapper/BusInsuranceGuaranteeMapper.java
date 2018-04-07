package com.dayuan.mapper;

import java.util.List;

public interface BusInsuranceGuaranteeMapper<T> extends BaseMapper<T> {
	
	/**
	 * 保存BusInsuranceGuarantee
	 * @return 影响记录数
	 * */
	public int addT(T t);
	
	
	/**
	 * 更新BusInsuranceGuarantee
	 * @return 影响记录数
	 * */
	public int updateT(T t);
	
	
	/**
	 *根据bid查询BusInsuranceGuarantee
	 *@return list
	 * */
	public List<T> queryBybid(Object id);
	
	
	/**
	 * 根据bid删除BusInsuranceGuarantee
	 * @return int
	 * */
	public int deleteBybid(Object id);


}
