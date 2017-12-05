package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDatagridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class PageController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EUDatagridResult getItemList(Integer page, Integer rows) {
        EUDatagridResult result = itemService.getItemList(page, rows);
        return result;

    }

    @RequestMapping("/item/param/list")
    @ResponseBody
    public EUDatagridResult getItemCatParamList(int page, int rows) {
        EUDatagridResult result = itemService.geItemCatParamList(page,rows);
        return result;

    }
}
