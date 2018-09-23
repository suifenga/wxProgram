package com.idig8.controller;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.idig8.pojo.Bgm;
import com.idig8.pojo.Videos;
import com.idig8.service.BgmService;
import com.idig8.service.VideoService;
import com.idig8.utils.FetchVideoCover;
import com.idig8.utils.JSONResult;
import com.idig8.utils.MergeVideoMp3;
import com.idig8.utils.PagedResult;
import com.idig8.utils.enums.VideoStatusEnum;
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
	
	@Autowired
	private VideoService videosService;
	
	@Value("${server.file.path}")
	private String fileSpace;
	
	@Value("${server.ffmpeg.path}")
	private String ffmpegexe;
	
	
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
		String videOutPath = "";
		String ImagePath = "";
		try {
			 path = FileUtil.uploadFile(file.getBytes(), fileSpace, fileName);
	        } catch (Exception e) {
	            e.getStackTrace();
	               return JSONResult.errorMsg(e.getMessage());
	        }                
		 
	
		if(StringUtils.isNotBlank(bgmId)){
			Bgm bgm = bgmService.queryBgmById(bgmId);
			String mp3BgmPath = fileSpace + bgm.getPath();
			MergeVideoMp3 mergeVideoMp3 = new MergeVideoMp3(ffmpegexe);
			String videOutPathName = UUID.randomUUID().toString()+".mp4";
			File targetFile = new File(fileSpace + userId);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			videOutPath = "/"+userId+"/"+videOutPathName;
			String videoInput = fileSpace +path;
			mergeVideoMp3.convertor(videoInput, mp3BgmPath, videoSeconds, fileSpace +videOutPath);
			
		}else{
			videOutPath = path;
			
		}
		
		ImagePath =  "/"+userId+"/"+UUID.randomUUID().toString()+".jpg";;
		FetchVideoCover fetchVideoCover = new FetchVideoCover(ffmpegexe);
		fetchVideoCover.getCover(fileSpace +videOutPath, fileSpace +ImagePath);
		
		
		Videos videos = new Videos();
		videos.setAudioId(bgmId);
		videos.setCreateTime(new Date());
		videos.setVideoDesc(desc);
		videos.setId(UUID.randomUUID().toString());
		videos.setUserId(userId);
		videos.setVideoHeight(videoHeight);
		videos.setVideoWidth(videoWidth);
		videos.setVideoPath(videOutPath);
		videos.setCoverPath(ImagePath);
		videos.setStatus(VideoStatusEnum.SUCCESS.value);
		videosService.saveVideo(videos);
				 
		return JSONResult.ok(path);
		
	}
	
	@PostMapping(value="/showAll")
	@ApiOperation(value="视频列表", notes="分页的视频列表")
	public JSONResult upload(@RequestBody Videos video,Integer isSaveRecord,
			Integer page) throws Exception {
		if(page == null){
			page = 1;
		}
		PagedResult result = videosService.getAllVideos(video,isSaveRecord,page, PAGE_SIZE);	 
		return JSONResult.ok(result);
		
	}
	
	@PostMapping(value="/userLike")
	@ApiOperation(value="热搜词列表", notes="热搜词列表")
	public JSONResult userLike(String userId,String videoId,String videoCreaterId) throws Exception {
		
		videosService.userLikeVideo(userId, videoId, videoCreaterId);
		return JSONResult.ok();
		
	}
	
	@PostMapping(value="/userUnLike")
	public JSONResult userUnLike(String userId,String videoId,String videoCreaterId) throws Exception {
		videosService.userUnLikeVideo(userId, videoId, videoCreaterId);
		return JSONResult.ok();
		
	}
	
	@PostMapping(value="/hot")
	public JSONResult upload() throws Exception {
 
		return JSONResult.ok(videosService.gethostList());
		
	}
}