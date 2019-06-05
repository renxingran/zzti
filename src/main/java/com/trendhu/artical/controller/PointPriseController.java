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
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.service.ArtCollectionService;
import com.trendhu.artical.service.ArtCommentService;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.PointPriseService;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.BaseMgBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/pointPrise" })
public class PointPriseController extends RestfullBaseController<PointPrise> {

	@Autowired
	private PointPriseService pointPriseService;
	
	
	@Override
	protected BaseService<PointPrise> getService() {
		return pointPriseService;
	}
	
	@RequestMapping(value = "/addPointPrise", method = { RequestMethod.PUT })
    public ResponseBean addPointPrise(@RequestBody PointPrise object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                    object.prepareHBBean();
                    PointPrise pointPrise = pointPriseService.addPointPrise(object);
                    Boolean b = pointPrise.getPointPriseSta();
                    if(b.equals(false)) {
                    	responseBean.setCode("A008");//点赞失败
                    }
                    responseBean.setData(pointPrise);
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }

    @RequestMapping(value = "/canclePointPrise", method = { RequestMethod.PUT })
    public ResponseBean canclePointPrise(@RequestBody PointPrise object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                    object.prepareHBBean();
                    PointPrise pointPrise = pointPriseService.canclePointPrise(object);
                    System.out.println("quxiao7e41274"+pointPrise.toString());
                    
                    if(pointPrise.getPointPriseSta().equals(true)) {
                    	responseBean.setCode("A008");//取消收藏失败
                    }
                    responseBean.setData(pointPrise);//取消收藏成功
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }
    @Autowired
    private ArtFService artFService;
    @Autowired
    private ArtCollectionService artCollectionService;
    @Autowired
    private ArtCommentService artCommentService;
    @Autowired
    private SourceWService sourceWService;
    @RequestMapping(value = "/MyPointPrise", method = { RequestMethod.PUT })
    public ResponseBean MyPointPrise(@RequestBody PointPrise pointPrise) {
            ResponseBean responseBean = super.getReturn();
        	List<ArtF> artList = new ArrayList<>();
    		if(pointPrise != null) {
    			  if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
    					List<PointPrise> pointPriseList =  pointPriseService.queryMyPointPriseList(pointPrise.toMongoHashMap(), 
    						  pointPrise.getPage(),this.getSortKey(pointPrise), this.getDirection(pointPrise));
    			
    				  	for(PointPrise p:pointPriseList) {
    				  			String artId = p.getArtId();
    				  			ArtF art = artFService.findOne(artId);
    				  			art.setPointPriseSta(true);
    				  			art.setPointCount(pointPriseService.getPointCountByArtId(artId));
    				  			art.setCollectionSta(artCollectionService.getCollectionSta(artId, pointPrise.getUserId()));
    				  			art.setCollectionCount(artCollectionService.getCollectionCountByArtId(artId));
    				  			art.setPointPriseDate(p.getPointPriseDate());
    				  			art.setCommentCount(artCommentService.getCommentCountByArtId(artId));
    				  			art.setSource(sourceWService.getSourceNameByArtId(artId));
    				  			art.setImgSource(sourceWService.getSourceImgByArtId(artId));
    				  			artList.add(art);
    				  	}
    		               Page<ArtF> p = new Page<ArtF>(
    		            		   artList,//是一个List<ArtF>对象
    		            		   pointPrise.getPage().getPageNumber(),//当前页
    		            		   pointPrise.getPage().getPageSize(),//每页显示数据条数
    		            		   pointPrise.getPage().getTotalSize()); 
    				  	responseBean.setData(p);
    			  }
    		}else {
    			responseBean.setCode("A008");
    		}
    	
		return returnBean(responseBean);
    	
    }
    
}