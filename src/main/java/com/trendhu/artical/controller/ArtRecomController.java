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
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.artical.bean.FM;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.ArtRecomService;
import com.trendhu.artical.service.FMService;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/artRecom" })
public class ArtRecomController extends RestfullBaseController<ArtRecom> {

	@Autowired
	private ArtRecomService artRecomService;

	
	@Override
	protected BaseService<ArtRecom> getService() {
		return artRecomService;
	}
	
	@Autowired
	private HisActService hisActService;
	@Autowired
	private ArtFService artFService;
	// 查看推荐文章列表
    @RequestMapping(value = "/queryArtLists", method = { RequestMethod.POST })
    public ResponseBean queryArtLists(@RequestBody ArtRecom object) {
    	
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
               ArtRecom artRecom = artRecomService.queryArtLists(object);
               
                List<String> art_id_list = artRecom.getArt_id_list();
                
                List<ArtRecom> artRecomList = new ArrayList<>(); 
                
                
                for(String artId:art_id_list) {
                	ArtF art = artFService.findOne(artId);
                	object.setArtHisCount(hisActService.getHisCountByArtId(artId));
//                	object.setArtId(art.getId());
                	object.setArtId(art.getId());
                	object.setTitle(art.getTitle());
                	object.setImgF(art.getImgF());
                	object.setSource(art.getSource());
                	object.setArtResTime(art.getArtResTime());
                	artRecomList.add(object);
                }
                
                Page<ArtRecom> p = new Page<ArtRecom>(
                		artRecomList,	object.getPage().getPageNumber(),
                		object.getPage().getPageSize()); 
			  	responseBean.setData(p);
            }
        }
        return returnBean(responseBean);
    }
}