package com.idig8.service.Impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idig8.mapper.UsersMapper;
import com.idig8.pojo.Users;
import com.idig8.service.UserService;
import com.idig8.utils.MD5Utils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
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

	@Transactional(propagation =Propagation.SUPPORTS)
	@Override
	public Users queryUserIsExist(Users user) {
		Example queryExample = new Example(Users.class);
		Criteria criteria = queryExample.createCriteria();
		criteria.andEqualTo("username",user.getUsername());
		try {
			criteria.andEqualTo("password",MD5Utils.getMD5Str(user.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Users userOne =  usersMapper.selectOneByExample(queryExample);
		return userOne;
	}
	
	@Transactional(propagation =Propagation.REQUIRED)
	@Override
	public void updateUser(Users user) {
		
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", user.getId());
		usersMapper.updateByExampleSelective(user, userExample);
	}
	
	@Transactional(propagation =Propagation.SUPPORTS)
	@Override
	public Users queryUserId(String userId){
		Example queryExample = new Example(Users.class);
		Criteria criteria = queryExample.createCriteria();
		criteria.andEqualTo("id",userId);
		Users userOne =  usersMapper.selectOneByExample(queryExample);
		return userOne;
	}

}
