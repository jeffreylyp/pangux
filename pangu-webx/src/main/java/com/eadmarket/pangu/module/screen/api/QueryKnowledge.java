package com.eadmarket.pangu.module.screen.api;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.fastjson.JSON;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.KnowledgeDO;
import com.eadmarket.pangu.domain.LightKnowledgeDO;
import com.eadmarket.pangu.manager.knowledge.KnowledgeManager;
import com.eadmarket.pangu.query.KnowledgeQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 站长助手使用的数据服务
 *
 * @author liuyongpo@gmail.com
 */
public class QueryKnowledge {

  private final static Logger LOG = LoggerFactory.getLogger(QueryKnowledge.class);

  @Resource
  private KnowledgeManager knowledgeManager;
  private Splitter splitter = Splitter.on('_').trimResults().omitEmptyStrings();

  public void execute(TurbineRunData runData, Context context) {
    Map<String, Object> result = Maps.newHashMap();

    Long minId = runData.getParameters().getLong("min_id", 0);
    Long maxId = runData.getParameters().getLong("max_id", 0);
    if (minId <= 0 && maxId <= 0) {
      result.put("success", 0);
      String json = JSON.toJSONString(result);
      context.put("JSON", json);
      return;
    }

    int pageSize = runData.getParameters().getInt("p_s", 100);

    KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
    if (minId > 0) {
      knowledgeQuery.setMinKnowledgeId(minId);
    }
    if (maxId > 0) {
      knowledgeQuery.setMaxKnowledgeId(maxId);
    }

    String categories = runData.getParameters().getString("cats", "14");
    if (StringUtils.isNotBlank(categories)) {
      List<String> strings = splitter.splitToList(categories);
      List<Long> list = Lists.newArrayList();
      for (String str : strings) {
        list.add(Long.valueOf(str));
      }
      knowledgeQuery.setCategorys(list);
    }

    try {
      Query<KnowledgeQuery> query = Query.create(knowledgeQuery);
      query.setPageSize(pageSize);

            /*
             * 现在建立了一个假设，就是minId和maxId不会同时都传过来
             */
      if (minId > 0) {
        query.setOrderBy("id");
        query.setOrderType("asc");
      } else if (maxId > 0) {
        query.setOrderType("desc");
        query.setOrderBy("id");
      }

      List<KnowledgeDO> knowledgeDOs = knowledgeManager.queryByMinIdWithComments(query);
      result.put("success", 1);
      result.put("data", lightKnowledge(knowledgeDOs));
      String json = JSON.toJSONString(result);
      context.put("JSON", json);
    } catch (ManagerException ex) {
      LOG.error("queryByMinId,min_id=" + minId, ex);
    }

  }

  private List<LightKnowledgeDO> lightKnowledge(List<KnowledgeDO> knowledgeDOs) {
    List<LightKnowledgeDO> list = Lists.newArrayList();
    for (KnowledgeDO knowledgeDO : knowledgeDOs) {
      list.add(LightKnowledgeDO.from(knowledgeDO));
    }
    return list;
  }
}
