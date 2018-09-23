package com.idig8.mapper;

import com.idig8.pojo.Users;
import com.idig8.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {
	
	public void addReceiveLikeCount(String userId);
	
	public void reduceReceiveLikeCount(String userId);
}