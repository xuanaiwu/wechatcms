package com.dayuan.mapper;

public interface BusLendingMapper<T> extends BaseMapper<T> {
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
	 * 跟bid返回list
	 * */
	public T queryByBId(Object bid);
	
	
	/**根据LId删除数据*/
	public void deleteByLId(Object LId);

}
