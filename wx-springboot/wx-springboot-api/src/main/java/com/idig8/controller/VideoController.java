package com.idig8.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.idig8.pojo.Bgm;
import com.idig8.pojo.Users;
import com.idig8.pojo.Videos;
import com.idig8.service.BgmService;
import com.idig8.utils.JSONResult;
import com.idig8.utils.file.FileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@Api(value="视频相关业务的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {
	
	@Autowired
	private BgmService bgmService;
	
	@Value("${server.face.path}")
	private String fileSpace;
	
	
	@ApiOperation(value="上传视频", notes="上传视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoHeight", value="视频高度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="desc", value="视频描述", required=false, 
				dataType="String", paramType="form")
	})
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public JSONResult upload(String userId, 
				String bgmId, double videoSeconds, 
				int videoWidth, int videoHeight,
				String desc,
				@ApiParam(value="短视频", required=true)
				MultipartFile file) throws Exception {
		
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
				 
				
			
				return JSONResult.ok(path);
		
	}
}