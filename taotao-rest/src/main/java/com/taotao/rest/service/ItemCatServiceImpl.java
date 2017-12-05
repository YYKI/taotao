package com.taotao.rest.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEMCAT_REDIS_KEY}")
    private String ITEMCAT_REDIS_KEY;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        //获取缓存内容
        try {
            String result = jedisClient.get(ITEMCAT_REDIS_KEY);
            if(!StringUtils.isEmpty(result)){
                catResult = JsonUtils.jsonToPojo(result, CatResult.class);
                return catResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        catResult.setData(getCatList(0));

        //设置缓存
        try {
            String result = JsonUtils.objectToJson(catResult);
            jedisClient.set(ITEMCAT_REDIS_KEY, result);
        }catch(Exception e){
            e.printStackTrace();
        }

        return catResult;
    }

    private List<?> getCatList(long parentId) {




        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        //返回值 list
        List resultList = new ArrayList<>();

        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>"
                            + tbItemCat.getName() + "</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                catNode.setItem(getCatList(tbItemCat.getId()));
                resultList.add(catNode);
            } else {
                resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }



        return resultList;

    }

}
