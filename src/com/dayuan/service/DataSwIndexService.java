package com.dayuan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dayuan.mapper.DataSwIndexMapper;
import com.dayuan.model.DataSwIndexModel;
@Service("dataSwIndexService")
public class DataSwIndexService<T> extends BaseService {
	@Autowired
	private DataSwIndexMapper<T> dataSwIndexMapper;

	@Override
	public DataSwIndexMapper<T> getMapper() {
		return dataSwIndexMapper;
	}
	
	public List<T> querySimpleLine(DataSwIndexModel model){
		return dataSwIndexMapper.querySimpleLine(model);
	}
	
	public List<T> queryIndexType(){
		return dataSwIndexMapper.queryIndexType();
	}

}
