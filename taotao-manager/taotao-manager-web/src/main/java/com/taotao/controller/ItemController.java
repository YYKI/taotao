package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.service.ItemServiceImpl;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem geTbItemById(@PathVariable long itemId) {
        TbItem item = itemService.getItemById(itemId);
        return item;
    }

    //添加商品
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult creatItem(TbItem item, String desc, String itemParams) throws Exception {
        TaotaoResult result = itemService.createItem(item, desc, itemParams);
        return result;
    }

    //删除商品
    @RequestMapping(value = "/item/delete", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteItem(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            itemService.deleteItem(Long.parseLong(id));
        }

        return TaotaoResult.ok();
    }

    //获取商品规格
    @RequestMapping(value = "/item/param/query/itemcatid/{cid}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult getItemCatParam(@PathVariable long cid) {
        TaotaoResult result = itemService.getItemCatParam(cid);
        return result;
    }

    //添加商品规格
    @RequestMapping(value = "/item/param/save/{cid}", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItemCatParam(@PathVariable long cid, String paramData) {
        TaotaoResult result = itemService.createItemCatParam(cid, paramData);
        return result;
    }



}
