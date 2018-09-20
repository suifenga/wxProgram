package com.idig8.mapper;

import java.util.List;

import com.idig8.pojo.Videos;
import com.idig8.pojo.vo.VideosVO;
import com.idig8.utils.MyMapper;

public interface VideosUsersMapper extends MyMapper<VideosVO> {
	
	public List<VideosVO> queryAllVideos();
}