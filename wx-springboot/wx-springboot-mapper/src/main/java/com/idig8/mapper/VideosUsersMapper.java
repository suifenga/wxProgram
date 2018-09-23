package com.idig8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idig8.pojo.Videos;
import com.idig8.pojo.vo.VideosVO;
import com.idig8.utils.MyMapper;

public interface VideosUsersMapper extends MyMapper<VideosVO> {
	
	public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
	
	public void addVideoLikeCount(String videoId);
	
	public void reduceVideoLikeCount(String videoId);
	
}