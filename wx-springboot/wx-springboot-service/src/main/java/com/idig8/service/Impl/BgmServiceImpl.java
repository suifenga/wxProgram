package com.idig8.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idig8.mapper.BgmMapper;
import com.idig8.pojo.Bgm;
import com.idig8.service.BgmService;

@Service
public class BgmServiceImpl implements BgmService {

	@Autowired
	private BgmMapper bgmMapper;
	
	
	@Transactional(propagation =Propagation.SUPPORTS)
	@Override
	public List<Bgm> queryBgmList(){
		
		return bgmMapper.selectAll();
	}

}
