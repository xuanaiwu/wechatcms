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
	
	
	/**
	 * 根据bid返回对象
	 * */
	public T queryByBId(Object bid);
	
	
	/**根据LId删除数据*/
	public void deleteByLId(Object LId);

}
