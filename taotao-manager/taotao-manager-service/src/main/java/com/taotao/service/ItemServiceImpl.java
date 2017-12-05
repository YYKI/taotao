package com.taotao.service;

import java.util.Date;
import java.util.List;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    //不能使用static
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamMapper itemParamMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;


    @Override
    public TbItem getItemById(long id) {
        TbItemExample example = new TbItemExample();
        example.createCriteria().andIdEqualTo(id);
        List<TbItem> items = itemMapper.selectByExample(example);
        if (items != null && items.size() != 0) {
            return items.get(0);
        }
        return null;
    }

    @Override
    public EUDatagridResult getItemList(int page, int rows) {
        TbItemExample example = new TbItemExample();
        PageHelper.startPage(page, rows);
        List<TbItem> items = itemMapper.selectByExample(example);
        EUDatagridResult result = new EUDatagridResult();
        result.setRows(items);
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        result.setTotal(pageInfo.getTotal());
        return result;

    }

    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
        //item补全
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //1 上架 2下架 3删除
        item.setStatus((byte) 1);
        itemMapper.insert(item);
        //添加商品描述
        TaotaoResult result = insertItemDesc(itemId, desc);
        createItemParams(itemId, itemParams);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        return TaotaoResult.ok();
    }

    private TaotaoResult insertItemDesc(Long itemId, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    //添加商品规格参数
    private void createItemParams(long id, String itemParams){
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(id);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insert(itemParamItem);
    }

    public TaotaoResult deleteItem(long id) {
        itemMapper.deleteByPrimaryKey(id);
        return TaotaoResult.ok();
    }

    //获取商品规格
    @Override
    public TaotaoResult getItemCatParam(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        if(itemParams!=null && itemParams.size()>0){
            return TaotaoResult.ok(itemParams.get(0));
        }
        return TaotaoResult.ok();
    }

    //添加商品规格
    @Override
    public TaotaoResult createItemCatParam(long cid, String paramData) {
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    //获取商品规格列表
    @Override
    public EUDatagridResult geItemCatParamList(int page, int rows) {
        TbItemParamExample example = new TbItemParamExample();
        PageHelper.startPage(page, rows);
        TbItemParam param = itemParamMapper.selectByPrimaryKey(1L);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        EUDatagridResult result = new EUDatagridResult();
        result.setRows(itemParams);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(itemParams);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
