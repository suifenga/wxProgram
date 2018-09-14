package com.idig8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idig8.service.BgmService;
import com.idig8.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="背景音乐接口",tags={"背景音乐接口的controller"})
@RequestMapping(value = "/bgm")
public class BgmController extends BasicController{
	
	@Autowired
	private BgmService bgmService;
	
	
	
	@ApiOperation(value="获取所有背景音乐",notes="通过获取所有背景音乐")
	@PostMapping("/list")
	public JSONResult list() {
		return JSONResult.ok(bgmService.queryBgmList());
	}
	

}
