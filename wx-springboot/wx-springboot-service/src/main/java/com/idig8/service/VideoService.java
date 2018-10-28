package com.idig8.service;

import java.util.List;

import com.idig8.pojo.Comments;
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
	
	public void userLikeVideo(String userId,String videoId,String videoCreaterId);
	
	public void userUnLikeVideo(String userId,String videoId,String videoCreaterId);
	
	public PagedResult queryMyLikeVideos(String userId,Integer page,Integer pageSize);
	
	public PagedResult queryMyFollowVideos(String userId,Integer page,Integer pageSize);

	/**
	 * @Description: 用户留言
	 */
	public void saveComment(Comments comment);

	/**
	 * @Description: 留言分页
	 */
	public PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
	
}
	
	
