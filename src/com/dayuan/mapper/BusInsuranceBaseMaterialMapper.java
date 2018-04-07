package com.dayuan.mapper;

public interface BusInsuranceBaseMaterialMapper<T> extends BaseMapper<T>{
	
	
	/**
	 * 保存BusInsuranceBaseMaterial
	 * @return 影响记录数
	 * */
	public int addT(T t);
	
	
	/**
	 * 更新BusInsuranceBaseMaterial
	 * @return 影响记录数
	 * */
	public int updateT(T t);

}
