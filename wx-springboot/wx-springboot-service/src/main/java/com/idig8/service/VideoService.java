package com.idig8.service;

import com.idig8.pojo.Videos;
import com.idig8.utils.PagedResult;

public interface VideoService {

	
	/**
	 * 保存视频信息
	 * @param Id
	 * @return
	 */
	public String saveVideo(Videos video);
	
	/**
	 * 分析查询视频列表
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResult getAllVideos(Integer page,Integer pageSize);
}
	
	
