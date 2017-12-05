package com.taotao.service;

import com.taotao.common.pojo.EUDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    public TbItem getItemById(long id);

    public EUDatagridResult getItemList(int page, int rows);

    public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception;

    public TaotaoResult deleteItem(long id);

    public TaotaoResult getItemCatParam(long cid);

    public TaotaoResult createItemCatParam(long cid, String paramData);

    public EUDatagridResult geItemCatParamList(int page, int rows);

}
