package com.eadmarket.pangu.dao.trade.impl;

import com.google.common.collect.Maps;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
import com.eadmarket.pangu.query.TradeQuery;

import java.util.List;
import java.util.Map;

/**
 * @author liuyongpo@gmail.com
 */
class TradeDaoImpl extends BaseDao implements TradeDao {

  @Override
  public TradeDO getById(Long id) throws DaoException {
    return selectOne("TradeDao.getById", id);
  }

  @Override
  public Long createTrade(TradeDO trade) throws DaoException {
    insert("TradeDao.createTrade", trade);
    return trade.getId();
  }

  @Override
  public List<TradeDO> query(Query<TradeQuery> query) throws DaoException {
    return selectList("TradeDao.query", query);
  }

  @Override
  public Integer count(Query<TradeQuery> query) throws DaoException {
    return selectOne("TradeDao.count", query);
  }

  @Override
  public int updateStatus(Long id, TradeStatus originalStatus, TradeStatus targetStatus)
      throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("id", id);
    param.put("originalStatus", originalStatus);
    param.put("targetStatus", targetStatus);
    return update("TradeDao.updateStatus", param);
  }

  @Override
  public void updateTrade(TradeDO trade) throws DaoException {
    update("TradeDao.updateTrade", trade);
  }

}
