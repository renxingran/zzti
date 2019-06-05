package com.trendhu.artical.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.service.ArtCollectionService;
import com.trendhu.artical.service.ArtCommentService;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.PointPriseService;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/artCollection" })
public class ArtCollectionController extends RestfullBaseController<ArtCollection> {

	@Autowired
	private ArtCollectionService artCollectionService;
	
	@Override
	protected BaseService<ArtCollection> getService() {
		return artCollectionService;
	}	
	

    @RequestMapping(value = "/addCollection", method = { RequestMethod.PUT })
    public ResponseBean addCollection(@RequestBody ArtCollection object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                    object.prepareHBBean();
                    ArtCollection artCollection = artCollectionService.addCollection(object);
                    
                    if(artCollection.getCollectionSta().equals(false)) {
                    	responseBean.setCode("A008");//收藏失败
                    }
                    responseBean.setData(artCollection);
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }
	
    @RequestMapping(value = "/cancleCollection", method = { RequestMethod.PUT })
    public ResponseBean cancleCollection(@RequestBody ArtCollection object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                    object.prepareHBBean();
                    ArtCollection artCollection = artCollectionService.cancleCollection(object);
                    if(artCollection.getCollectionSta().equals(true)) {
                    	responseBean.setCode("A008");//取消收藏失败
                    }
                    responseBean.setData(artCollection);//取消收藏成功
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }
    
    //我的收藏
    @Autowired
    private ArtFService artFService;
    @Autowired
    private ArtCommentService artCommentService;
    @Autowired
    private SourceWService sourceWService;
    @Autowired
    private PointPriseService pointPriseService;
    @RequestMapping(value = "/MyCollection", method = { RequestMethod.PUT })
    public ResponseBean MyPointPrise(@RequestBody  ArtCollection artCollection) {
        ResponseBean responseBean = super.getReturn();
    	List<ArtF> artList = new ArrayList<>();
		if(artCollection != null) {
			  if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
					List<ArtCollection> artCollectionList =  artCollectionService.
							queryMyCollectionList(artCollection.toMongoHashMap(), 
							artCollection.getPage(),this.getSortKey(artCollection), this.getDirection(artCollection));
			
				  	for(ArtCollection ac:artCollectionList) {
				  			String artId = ac.getArtId();
				  			String userId = ac.getUserId();
				  			ArtF art = artFService.findOne(artId);
				  			art.setPointPriseSta(pointPriseService.getPointPriseSta(artId, artCollection.getUserId()));
				  			art.setPointCount(pointPriseService.getPointCountByArtId(artId));
				  			art.setCollectionSta(true);
				  			art.setCollectionCount(artCollectionService.getCollectionCountByArtId(artId));
				  			art.setArtCollectionDate(ac.getArtCollectionDate());
				  			art.setCommentCount(artCommentService.getCommentCountByArtId(artId));
				  			art.setSource(sourceWService.getSourceNameByArtId(artId));
				  			art.setImgSource(sourceWService.getSourceImgByArtId(artId));
				  			art.setUserId(artCollection.getUserId());
//				  			artCollectionService.findOne(params);
//				  			art.setArtCollectionDate();
				  			artList.add(art);
				  	}
		               Page<ArtF> p = new Page<ArtF>(
		            		   artList,//是一个List<ArtF>对象
		            		   artCollection.getPage().getPageNumber(),//当前页
		            		   artCollection.getPage().getPageSize(),//每页显示数据条数
		            		   artCollection.getPage().getTotalSize()); 
				  	responseBean.setData(p);
			  }
		}else {
			responseBean.setCode("A008");
		}
	
	return returnBean(responseBean);
	
}
}