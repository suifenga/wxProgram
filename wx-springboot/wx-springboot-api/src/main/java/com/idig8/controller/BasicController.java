package com.idig8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.idig8.utils.RedisOperator;

@RestController
public class BasicController {

	@Autowired
	public RedisOperator redis;
	
	
	/**
	 * 用户session的分类名称
	 */
	public static final String USERS_REDIS_SESSION = "user-redis-session";
	
	/**
	 * 用户session的失效时间
	 */
	public static final long USERS_REDIS_SESSION_TL = 1000*60*30;
	
	/**
	 * 每一页分多少
	 */
	public static final Integer PAGE_SIZE=1;
}
