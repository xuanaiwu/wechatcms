package com.dayuan.mapper;

public interface BusBilingMapper<T> extends BaseMapper<T> {
	
	/**
	 * 保存
	 * @return 影响记录数
	 * */
	public int save(T t);
	
	
	/**
	 *更新返回影响记录数
	 * */
	public int updateReturnInfluences(T t);

}
