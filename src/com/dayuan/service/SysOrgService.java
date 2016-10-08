package com.dayuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dayuan.mapper.SysOrgMapper;

@Service("sysOrgService")
public class SysOrgService<T> extends BaseService<T> {
	
	 

	@Autowired
	private  SysOrgMapper<T> mapper;

	@Override
	public SysOrgMapper<T> getMapper() {
		
		return  mapper;
	}

}
