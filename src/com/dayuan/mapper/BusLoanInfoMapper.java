package com.dayuan.mapper;

import java.util.List;

import com.dayuan.model.BusLoanInfoModel;

public interface BusLoanInfoMapper<T> extends BaseMapper<T> {
	
	/**
	 * 保存数据并返回主键id和插入记录数
	 * */
	public int save(T t);
	
	/**
	 *更新返回影响记录数
	 * */
	public int updateReturnInfluences(T t);
	
	
	public List<T> queryList(BusLoanInfoModel busLoanInfoModel);
	
	/**根据lid获取对象*/
	public T queryByLId(Object id);
	
	
	/**根据LId删除对象*/
	public void deleteByLId(Object id);
	

}
