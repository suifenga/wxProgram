package com.idig8.mapper;

import java.util.List;

import com.idig8.pojo.SearchRecords;
import com.idig8.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
	
	public List<String> gethotList();
}