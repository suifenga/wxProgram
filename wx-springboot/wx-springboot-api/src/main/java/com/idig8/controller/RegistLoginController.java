package com.idig8.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idig8.pojo.Users;
import com.idig8.service.UserService;
import com.idig8.utils.JSONResult;
import com.idig8.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户注册登录的接口",tags={"注册和登录的controller"})
public class RegistLoginController {
	
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
		
		//防止密码返回被获取到
		user.setPassword("");
		return JSONResult.ok(user);
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
		
		//防止密码返回被获取到
		user.setPassword("");
		return JSONResult.ok(userObject);
	}
}
