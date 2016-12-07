package com.dayuan.mapper;

import java.util.List;

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
	
	/**
	 * 根据bid删除对象
	 * */
	public void deleteByBid(Object bid);
	
	/**
	 * 根据bid返回list
	 * */
	public List<T> queryListByBId(Object bid);
	
	
	
}