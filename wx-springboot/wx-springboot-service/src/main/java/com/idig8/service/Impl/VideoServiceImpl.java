package com.idig8.service.Impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.idig8.mapper.SearchRecordsMapper;
import com.idig8.mapper.UsersLikeVideosMapper;
import com.idig8.mapper.UsersMapper;
import com.idig8.mapper.VideosMapper;
import com.idig8.mapper.VideosUsersMapper;
import com.idig8.pojo.SearchRecords;
import com.idig8.pojo.UsersLikeVideos;
import com.idig8.pojo.Videos;
import com.idig8.pojo.vo.VideosVO;
import com.idig8.service.VideoService;
import com.idig8.utils.PagedResult;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private VideosUsersMapper videosUsersMapper;

	@Autowired
	private SearchRecordsMapper searchRecordsMapper;

	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;

	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.REQUIRED)
	public String saveVideo(Videos video) {
		String id = sid.nextShort();
		video.setId(id);

		videosMapper.insertSelective(video);
		return id;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {

		String desc = video.getVideoDesc();
		String userId = video.getUserId();
		if (isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			String recordId = sid.nextShort();
			record.setId(recordId);
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}

		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosUsersMapper.queryAllVideos(desc,userId);
		PageInfo<VideosVO> pageList = new PageInfo<>(list);

		PagedResult result = new PagedResult();
		result.setPage(page);
		result.setTotal(pageList.getPages());
		result.setRows(list);
		result.setRecords(pageList.getTotal());

		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> gethostList() {

		return searchRecordsMapper.gethotList();
	}

	@Override
	public void userLikeVideo(String userId, String videoId, String videoCreaterId) {

		// 1.保存用戶和视频的关联关系
		String likeId = sid.nextShort();
		UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
		usersLikeVideos.setId(likeId);
		usersLikeVideos.setUserId(userId);
		usersLikeVideos.setVideoId(videoId);
		usersLikeVideosMapper.insert(usersLikeVideos);

		// 2.视频喜欢的累加
		videosUsersMapper.addVideoLikeCount(videoId);

		// 3. 用户喜欢的累加
		usersMapper.addReceiveLikeCount(userId);

	}

	@Override
	public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		usersLikeVideosMapper.deleteByExample(example);
		// 2.视频喜欢的累减
		videosUsersMapper.reduceVideoLikeCount(videoId);

		// 3. 用户喜欢的累减
		usersMapper.reduceReceiveLikeCount(userId);
	}

	@Override
	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page,pageSize);
		List<VideosVO> list = videosUsersMapper.queryMyLikeVideos(userId);
		
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}

	@Override
	public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page,pageSize);
		List<VideosVO> list = videosUsersMapper.queryMyFollowVideos(userId);
		
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}
	
	
}
