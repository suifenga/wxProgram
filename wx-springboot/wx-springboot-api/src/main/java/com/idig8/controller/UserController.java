package com.idig8.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.idig8.pojo.Users;
import com.idig8.service.UserService;
import com.idig8.utils.JSONResult;
import com.idig8.utils.file.FileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户接口",tags={"用户的controller"})
@RequestMapping(value = "/user")
public class UserController extends BasicController{
	
	@Autowired
	private UserService userService;
	
	@Value("${server.face.path}")
	private String fileSpace;
	
	@ApiOperation(value="用户上传头像",notes="用户上传头像的接口")
	
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="file",value="文件上传",required=true,dataType="String",paramType="query"),
	})
	@PostMapping("/uploadFace")
	public JSONResult uploadFace(String userId,@RequestParam("file") MultipartFile file) {
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
	

}
