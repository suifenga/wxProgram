package com.idig8.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idig8.pojo.Users;
import com.idig8.pojo.vo.UsersVO;
import com.idig8.service.UserService;
import com.idig8.utils.JSONResult;
import com.idig8.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户注册登录的接口",tags={"注册和登录的controller"})
public class RegistLoginController extends BasicController{
	
	@Autowired
	private UserService userService;
	
	
	@ApiOperation(value="用户注册",notes="用户注册的接口")
	@PostMapping("/regist")
	public JSONResult regist(@RequestBody Users user) {
		//1.判断用户名和密码不能为空
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())) {
			return JSONResult.errorMsg("用户名或密码不能为空");
		}
		
		//2.判断用户名是否存在
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		if(!usernameIsExist) {
			user.setNickname(user.getUsername());
			try {
				user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			} catch (Exception e) {
				
				return JSONResult.errorMsg(e.getMessage());
			}
			user.setFollowCounts(0);
			user.setReceiveLikeCounts(0);
			user.setFansCounts(0);
			userService.saveUser(user);
		}else {
			return JSONResult.errorMsg("用户名或已经存在，请更换在试试！");
		}
		
		UsersVO userVO = setUserRedisSessionToken(user);
		
		return JSONResult.ok(userVO);
	}
	
	@ApiOperation(value="用户登录",notes="用户登录的接口")
	@PostMapping("/login")
	public JSONResult login(@RequestBody Users user) {
		//1.判断用户名和密码不能为空
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())) {
			return JSONResult.errorMsg("用户名或密码不能为空");
		}
		
		//2.判断用户名是否存在
		Users userObject = userService.queryUserIsExist(user);
		
		if(userObject==null){
			return JSONResult.errorMsg("用户名或密码不存在！");
		}
		
		UsersVO userVO = setUserRedisSessionToken(userObject);
		return JSONResult.ok(userVO);
	}
	
	@ApiOperation(value="用户注销",notes="用户注销的接口")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping("/logout")
	public JSONResult logout(String userId) {
		
		try {
			redis.del(USERS_REDIS_SESSION + ":" + userId);
		} catch (Exception e) {
			return JSONResult.errorMsg("注销失败"+e.getMessage());
		}
		return JSONResult.ok();
	}
	
	public UsersVO setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USERS_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, USERS_REDIS_SESSION_TL);
		
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userModel, userVO);
		userVO.setUserToken(uniqueToken);
		return userVO;
	}
}
