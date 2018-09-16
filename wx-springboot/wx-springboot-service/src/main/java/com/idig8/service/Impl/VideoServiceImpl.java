package com.idig8.service.Impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idig8.mapper.VideosMapper;
import com.idig8.pojo.Videos;
import com.idig8.service.VideoService;

@Service
public class VideoServiceImpl implements  VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation =Propagation.REQUIRED)
	public String  saveVideo(Videos video){
		String id = sid.nextShort();
		video.setId(id);
				
		videosMapper.insertSelective(video);
		return id;
		
		
	}
}
