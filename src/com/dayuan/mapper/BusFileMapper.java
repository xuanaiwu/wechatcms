package com.dayuan.mapper;

public interface BusFileMapper<T> extends BaseMapper<T> {
	
	/**
	 * 保存BusFiLe
	 * @return 影响记录数
	 * */
	public int save(T t);
	
	
	/**
	 *更新返回影响记录数
	 * */
	public int updateReturnInfluences(T t);

}
