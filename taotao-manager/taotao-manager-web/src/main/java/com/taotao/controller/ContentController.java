package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDatagridResult getContent(Integer page, Integer rows, Long categoryId){
		EUDatagridResult result = contentService.getContent(page, rows, categoryId);
		return result;
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent tbContent){
		TaotaoResult result = contentService.addContent(tbContent);
		return result;
	}

}
