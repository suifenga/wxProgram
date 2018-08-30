package com.idig8.service;

import com.idig8.pojo.Users;

public interface UserService {

	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean queryUsernameIsExist(String username);
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public void saveUser(Users user);
	
}
