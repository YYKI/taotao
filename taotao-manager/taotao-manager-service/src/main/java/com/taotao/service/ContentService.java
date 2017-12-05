package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	//查询内容以表显示
	public EUDatagridResult getContent(int page, int rows, long categoryId);
	//添加内容
	public TaotaoResult addContent(TbContent tbContent);
	//获取内容
	public List<TbContent> getContentList(long categoryId);

}
