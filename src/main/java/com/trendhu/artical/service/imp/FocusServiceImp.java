package com.trendhu.artical.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.Focus;
import com.trendhu.artical.dao.FocusDao;
import com.trendhu.artical.service.FocusService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class FocusServiceImp extends BaseServiceImp<Focus>implements FocusService{
	
	@Autowired
	FocusDao focusDao;
	
	public FocusDao getDao() {
		return focusDao;
	}

	@Override
	public Boolean getFocusSta(String sourceId, String userId) {
		return this.getDao().getFocusSta(sourceId, userId);
	}
	
    @Override
    public Page<Focus> query(Map<String, Object> params,
                         Page<Focus> page) {
        return this.getDao().query(params, page);
    }

	@Override
	public Long getFocusCount(String id) {
		return this.getDao().getFocusCount(id);
	}

	@Override
	public Focus addFocusSource(Focus object) {
		
		return this.getDao().addFocusSource(object);
	}

	@Override
	public Focus cancleFocusSource(Focus object) {
		return this.getDao().cancleFocusSource(object);
	}
}