package com.dayuan.mapper;

import java.util.List;

import com.dayuan.model.DataSwIndexModel;

public interface DataSwIndexMapper<T> extends BaseMapper{
	public List<T> querySimpleLine(DataSwIndexModel model);
	public List<T> queryIndexType();

}
