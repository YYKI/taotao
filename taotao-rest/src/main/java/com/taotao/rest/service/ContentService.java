package com.taotao.rest.service;

import java.util.List;

import com.taotao.common.pojo.EUDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	//获取内容
	public List<TbContent> getContentList(long categoryId);

}
