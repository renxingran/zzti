package com.trendhu.artical.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.FM;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.FMService;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/fm" })
public class FMController extends RestfullBaseController<FM> {

	@Autowired
	private FMService fmService;

	
	@Override
	protected BaseService<FM> getService() {
		return fmService;
	}
	
	 
		@Autowired
		private ArtFService  artService;
		@Autowired
		private HisActService hisActService;
		
	    @RequestMapping(value = "/queryFM", method = { RequestMethod.POST })
	    public ResponseBean queryFM(@RequestBody FM object) {
	        ResponseBean responseBean = super.getReturn();
	        if (object != null) {
	            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
	                		@SuppressWarnings("unchecked")
	                		List<String> artIdList=new ArrayList<String>();
	                		
	                		List<FM> list= fmService
	                                         .queryFM(object.toMongoHashMap(),
	                                                object.getPage(),
	                                                this.getSortKey(object),
	                                                this.getDirection(object));
	                		
	                	      for(FM fm:list) {
	                	    	  artIdList.add(fm.getArtId());
	                          }
	                	Collection<ArtF> c=  artService.findAll(artIdList);
	                	 ArtF art = new ArtF();
	                	 List<ArtF> a = new ArrayList<>();
	                	for(ArtF artF :c) {
	                		artF.setArtHisCount(hisActService.getHisCountByArtId(artF.getId()));
	                		a.add(artF);
	                	}
	                	
	                	responseBean.setData(a);
	            }
	        }
	        return returnBean(responseBean);
	    }
}