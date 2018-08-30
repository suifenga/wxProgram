package com.idig8.service;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idig8.mapper.UsersMapper;
import com.idig8.pojo.Users;

public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation =Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		Users user = new Users();
		user.setUsername(username);
		Users result = usersMapper.selectOne(user);
		return result==null? false:true;
	}

	@Transactional(propagation =Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		String userId =sid.nextShort();
		user.setId(userId);
		usersMapper.insert(user);
	}

}
