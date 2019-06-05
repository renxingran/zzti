package com.trendhu.artical.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.SourceW;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.FocusService;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/sourceW" })
public class SourceWController extends RestfullBaseController<SourceW>{

	@Autowired
	private SourceWService serviceWService;
	
	@Override
	protected BaseService<SourceW> getService() {
		return serviceWService;
	}
	@Autowired
	private FocusService focusService;
	@Autowired 
	private ArtFService artFService;
	//管理员查看文章来源详情
   @RequestMapping(value = "/getSourceWDetails", method = { RequestMethod.POST })
    public ResponseBean findOne(@RequestBody SourceW sourceW) {
    	
    	String sourceId = sourceW.getId();
    	String sourceName = sourceW.getSourceName();
    	
        ResponseBean responseBean = super.getReturn();
        Boolean b = sourceId.isEmpty();
        
        if (b.equals(false)) {
        	Long focusNum  = focusService.getFocusCount(sourceId);// 关注量
        	Long releaseNum = artFService.getArtReleaseCount(sourceName);//发布量
        	SourceW s = serviceWService.findOne(sourceId);
        	s.setFocusNum(focusNum);
        	s.setReleaseNum(releaseNum);
        	
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {

                responseBean.setData(s);
            }
        }
        return returnBean(responseBean);
    }
   //管理员--文章来源管理
   @RequestMapping(value = "/querySourceWInfoLists", method = { RequestMethod.POST })
   public ResponseBean querySourceWInfoLists(@RequestBody SourceW object) {
       ResponseBean responseBean = super.getReturn();
       if (object != null) {
           if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
           	
              Page<SourceW> page = serviceWService.querySourceWInfoLists(object.toMongoHashMap(),
                      object.getPage(),
                      this.getSortKey(object),
                      this.getDirection(object));
              
              List<SourceW> sourceList = (List<SourceW>) page.getList();
              String sourceName;
              String sourceURL;
              for(SourceW source:sourceList) {
           	   		source.setFocusNum(focusService.getFocusCount(source.getId()));
           	   		source.setReleaseNum(artFService.getArtReleaseCount(source.getId()));
              }
              responseBean.setData(page);
           }
       }
       return returnBean(responseBean);
   }
}