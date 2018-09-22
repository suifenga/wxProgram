package com.idig8.service;

import java.util.List;

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
	 * @param video
	 * @param isSaveRecord
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResult getAllVideos(Videos video,Integer isSaveRecord,Integer page,Integer pageSize);
	
	/**
	 * 获取热搜词列表
	 * @return
	 */
	public List<String> gethostList();
}
	
	
