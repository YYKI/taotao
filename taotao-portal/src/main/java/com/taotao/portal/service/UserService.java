package com.taotao.portal.service;

import org.springframework.beans.factory.annotation.Value;

import com.taotao.pojo.TbUser;

public interface UserService {
	
	public TbUser getUserByToken(String token);

}
