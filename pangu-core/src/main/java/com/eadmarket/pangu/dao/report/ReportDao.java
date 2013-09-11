package com.eadmarket.pangu.dao.report;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.ReportDO;

/**
 * �����洢��ӿ�
 * 
 * @author liuyongpo@gmail.com
 */
public interface ReportDao {
	List<ReportDO> query() throws DaoException;
	
	int count() throws DaoException;
	
	void insert(ReportDO report) throws DaoException;
}