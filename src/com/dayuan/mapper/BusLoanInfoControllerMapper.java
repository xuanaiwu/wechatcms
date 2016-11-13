package com.dayuan.mapper;

public interface BusLoanInfoControllerMapper<T> extends BaseMapper<T>{
	/**
	 * 保存数据并返回主键id和插入记录数
	 * */
	public int save(T t);
	
	/**
	 *更新返回影响记录数
	 * */
	public int updateReturnInfluences(T t);

}