package com.idig8.service.Impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.idig8.mapper.SearchRecordsMapper;
import com.idig8.mapper.VideosMapper;
import com.idig8.mapper.VideosUsersMapper;
import com.idig8.pojo.SearchRecords;
import com.idig8.pojo.Videos;
import com.idig8.pojo.vo.VideosVO;
import com.idig8.service.VideoService;
import com.idig8.utils.PagedResult;

@Service
public class VideoServiceImpl implements  VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private VideosUsersMapper videosUsersMapper;
	
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation =Propagation.REQUIRED)
	public String  saveVideo(Videos video){
		String id = sid.nextShort();
		video.setId(id);
				
		videosMapper.insertSelective(video);
		return id;
		
		
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public PagedResult getAllVideos(Videos video,Integer isSaveRecord,Integer page, Integer pageSize) {
		
		String desc = video.getVideoDesc();
		if(isSaveRecord!=null && isSaveRecord ==1){
			SearchRecords record = new SearchRecords();
			String recordId = sid.nextShort();
			record.setId(recordId);
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}
		
		PageHelper.startPage(page,pageSize);
		List<VideosVO> list = videosUsersMapper.queryAllVideos(desc);
		PageInfo<VideosVO> pageList =new PageInfo<>(list);
		
		PagedResult result = new PagedResult();
		result.setPage(page);
		result.setTotal(pageList.getPages());
		result.setRows(list);
		result.setRecords(pageList.getTotal());
		
		return result;
	}

	@Transactional(propagation =Propagation.SUPPORTS)
	@Override
	public List<String> gethostList() {
		
		return searchRecordsMapper.gethotList();
	}
}
