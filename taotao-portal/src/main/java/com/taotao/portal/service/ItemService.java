package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemById(long itemId);
	String getItemDescById(long itemId);
	public String getItemParam(Long itemId);
}
