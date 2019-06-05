package com.trendhu.artical.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.Focus;
import com.trendhu.artical.bean.SourceW;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.FocusService;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/focus" })
public class FocusController extends RestfullBaseController<Focus> {

	@Autowired
	private FocusService focusService;
	
	
	@Override
	protected BaseService<Focus> getService() {
		return focusService;
	}
	
	@Autowired
    private SourceWService sourceWService;
   
    @RequestMapping(value = "/queryMyFocus/{userId}", method = { RequestMethod.POST })
    public ResponseBean queryMyFocus(@PathVariable("userId") String userId) {
        ResponseBean responseBean = super.getReturn();
        List<SourceW> list = new ArrayList<SourceW>();
        if (userId != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
               Collection<Focus> focusList=   this.getService().findAllByUserId(userId);
               List<String> sourceId = new ArrayList<String>();
               
               for(Focus f:focusList) {
            	   sourceId.add(f.getSourceId());
               }
           Collection<SourceW> sourceWList =   sourceWService.findAll(sourceId);
            for(SourceW s:sourceWList) {
            	for(Focus f:focusList) {
            		if(f.getSourceId().equals(s.getId())) {
            			s.setFocusSta(true);
            			s.setFocusDate(f.getFocusDate());
            			list.add(s);
            		}
            	}
            }
            responseBean.setData(list);
            }
        }
		return returnBean(responseBean);
      }	
    
    /**
     * 用户关注网站
     * @param T
     * @return
     */
    @Autowired
    private ArtFService artFService;
    @RequestMapping(value = "/addFocusSource",method = {RequestMethod.POST})
    public ResponseBean addFocusSource(@RequestBody Focus T) {
    	String artId = T.getArtId();
    	ResponseBean responseBean = super.getReturn();
    	ArtF art = artFService.findOne(artId);
    	String sourceName = art.getSource();//来源名
    	
    	String  sourceId = sourceWService.findSourceId(sourceName);//SourceId
    	
    	T.setSourceId(sourceId);
    	Focus focus = focusService.addFocusSource(T);
    	if(focus.getFocusSta().equals(false)) {
    		responseBean.setCode("A008");
    	}
    	responseBean.setData(focus);
    	return returnBean(responseBean) ;
    }
    @RequestMapping(value = "/cancleFocusSource",method = {RequestMethod.POST})
    public ResponseBean cancleFocusSource(@RequestBody Focus T) {
    	String artId = T.getArtId();
    	 ResponseBean responseBean = super.getReturn();
    	ArtF art = artFService.findOne(artId);
    	String sourceName = art.getSource();//来源名
    	
    	String  sourceId = sourceWService.findSourceId(sourceName);//SourceId
    	
    	T.setSourceId(sourceId);
    	
    	Focus focus = focusService.cancleFocusSource(T);
    	if(focus.getFocusSta().equals(true)) {
    		responseBean.setCode("A008");
    	}
    	responseBean.setData(focus);
    	return returnBean(responseBean) ;
    }
}