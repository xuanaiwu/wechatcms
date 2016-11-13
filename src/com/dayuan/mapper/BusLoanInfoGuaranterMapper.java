package com.dayuan.mapper;

import com.dayuan.bean.BusLoanInfoGuaranter;

public interface BusLoanInfoGuaranterMapper<T> extends BaseMapper<T>{
    int deleteByPrimaryKey(Integer id);

    int save(T t);

    int insertSelective(BusLoanInfoGuaranter record);

    BusLoanInfoGuaranter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusLoanInfoGuaranter record);

    int updateReturnInfluences(T t);
}