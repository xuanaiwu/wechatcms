package com.dayuan.mapper;

public interface BusInsuranceVerificationMapper<T> extends BaseMapper<T>{
	
	/**
	 * 保存BusInsuranceVerification
	 * @return 影响记录数
	 * */
	public int addT(T t);
	
	
	/**
	 * 更新BusInsuranceVerification
	 * @return 影响记录数
	 * */
	public int updateT(T t);
	
	
	/**
	 * 根据bid查询BusInsuranceVerification
	 * @return T
	 * */
	public T queryBybid(Object id);
	
	/**
	 * 根据bid删除BusInsuranceVerification
	 * @return int
	 * */
	public int deleteBybid(Object id);

}
