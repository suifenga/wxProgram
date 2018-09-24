package com.idig8.service.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idig8.mapper.UsersFansMapper;
import com.idig8.mapper.UsersLikeVideosMapper;
import com.idig8.mapper.UsersMapper;
import com.idig8.pojo.Users;
import com.idig8.pojo.UsersFans;
import com.idig8.pojo.UsersLikeVideos;
import com.idig8.service.UserService;
import com.idig8.utils.MD5Utils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	
	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private UsersFansMapper usersFansMapper;
	
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
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", userId);
		Users user = userMapper.selectOneByExample(userExample);
		return user;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isUserLikeVideo(String userId, String videoId) {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return false;
		}
		
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		
		List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);
		
		if (list != null && list.size() >0) {
			return true;
		}
		
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUserFanRelation(String userId, String fanId) {

		String relId = sid.nextShort();
		
		UsersFans userFan = new UsersFans();
		userFan.setId(relId);
		userFan.setUserId(userId);
		userFan.setFanId(fanId);
		
		usersFansMapper.insert(userFan);
		
		userMapper.addFansCount(userId);
		userMapper.addFollersCount(fanId);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteUserFanRelation(String userId, String fanId) {
		
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fanId);
		
		usersFansMapper.deleteByExample(example);
		
		userMapper.reduceFansCount(userId);
		userMapper.reduceFollersCount(fanId);
		
	}
	

	@Override
	public boolean queryIfFollow(String userId, String fanId) {

		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fanId);
		
		List<UsersFans> list = usersFansMapper.selectByExample(example);
		
		if (list != null && !list.isEmpty() && list.size() > 0) {
			return true;
		}
		
		return false;
	}

}
