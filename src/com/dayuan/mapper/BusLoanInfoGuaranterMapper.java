package com.dayuan.mapper;

import com.dayuan.bean.BusLoanInfoGuaranter;

public interface BusLoanInfoGuaranterMapper<T> extends BaseMapper<T>{
    int deleteByPrimaryKey(Integer id);

    int save(T t);

    int insertSelective(BusLoanInfoGuaranter record);

    BusLoanInfoGuaranter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusLoanInfoGuaranter record);

    int updateReturnInfluences(T t);
    
    /**
	 * 
	 * 根据bid返回单个对象
	 * 
	 * */
	public T getBusLoanInfoGuaranter(Object bid);
	
	
	public void deleteByBid(Object bid);
	
	
	
}