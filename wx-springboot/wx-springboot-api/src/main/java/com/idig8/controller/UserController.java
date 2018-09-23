package com.idig8.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.idig8.pojo.Users;
import com.idig8.pojo.vo.PublisherVideo;
import com.idig8.pojo.vo.UsersVO;
import com.idig8.service.UserService;
import com.idig8.utils.JSONResult;
import com.idig8.utils.file.FileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value="用户接口",tags={"用户的controller"})
@RequestMapping(value = "/user")
public class UserController extends BasicController{
	
	@Autowired
	private UserService userService;
	
	@Value("${server.file.path}")
	private String fileSpace;
	
	@ApiOperation(value="用户上传头像",notes="用户上传头像的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query"),
	})
	@PostMapping(value="/uploadFace",headers="content-type=multipart/form-data")
	public JSONResult uploadFace(String userId,@ApiParam(value="图片",required=true) MultipartFile file) {
		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户id不能为空...");
		}
		
		// 文件保存的命名空间
		String fileName = file.getOriginalFilename();
		// 保存到数据库中的相对路径
		String path = "";
		 try {
			 path = FileUtil.uploadFile(file.getBytes(), fileSpace, fileName);
	        } catch (Exception e) {
	            e.getStackTrace();
	        	return JSONResult.errorMsg(e.getMessage());
	        }
		 
		 Users user = new Users();
		 user.setId(userId);
		 user.setFaceImage(path);
		 userService.updateUser(user);
		
	
		return JSONResult.ok(path);
	}
	
	@ApiOperation(value="通过用户Id获取用户信息",notes="通过用户Id获取用户信息的接口")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping("/queryByUserId")
	public JSONResult queryByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户id不能为空...");
		}
		
		Users user = userService.queryUserId(userId);
		UsersVO usersVO= new UsersVO();
		BeanUtils.copyProperties(user, usersVO);
		
	
		return JSONResult.ok(usersVO);
	}
	
	@PostMapping("/queryPublisher")
	public JSONResult queryPublisher(String loginUserId, String videoId, 
			String publishUserId) throws Exception {
		
		if (StringUtils.isBlank(publishUserId)) {
			return JSONResult.errorMsg("");
		}
		
		// 1. 查询视频发布者的信息
		Users userInfo = userService.queryUserInfo(publishUserId);
		UsersVO publisher = new UsersVO();
		BeanUtils.copyProperties(userInfo, publisher);
		
		// 2. 查询当前登录者和视频的点赞关系
		boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);
		
		PublisherVideo bean = new PublisherVideo();
		bean.setPublisher(publisher);	
		bean.setUserLikeVideo(userLikeVideo);
		
		return JSONResult.ok(bean);
	}
	

}
