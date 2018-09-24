package com.idig8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idig8.pojo.vo.VideosVO;
import com.idig8.utils.MyMapper;

public interface VideosUsersMapper extends MyMapper<VideosVO> {
	
	public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc,@Param("userId")String userId);
	
	public void addVideoLikeCount(String videoId);
	
	public void reduceVideoLikeCount(String videoId);
	
	public List<VideosVO> queryMyLikeVideos(String userId);
	
	public List<VideosVO> queryMyFollowVideos(String userId);
	
}