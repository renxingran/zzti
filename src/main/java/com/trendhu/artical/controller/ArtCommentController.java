package com.trendhu.artical.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trendhu.artical.bean.ArtComment;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.service.ArtCollectionService;
import com.trendhu.artical.service.ArtCommentService;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.PointPriseService;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.artical.service.UserService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/artComment" })
public class ArtCommentController extends RestfullBaseController<ArtComment> {

	@Autowired
	private ArtCommentService artCommentService;
	
	@Override
	protected BaseService<ArtComment> getService() {
		return artCommentService;
	}
	
	    @RequestMapping(value = "/addComment", method = { RequestMethod.PUT })
	    public ResponseBean addComment(@RequestBody ArtComment object) {
	        ResponseBean responseBean = super.getReturn();
	        if (object != null) {
	            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
	                try {
	                    object.prepareHBBean();
	                    responseBean.setData(artCommentService.addComment(object));
	                } catch (DuplicateKeyException e) {
	                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
	                    logger.error("Key值重复", e);
	                }
	            }
	        }
	        return returnBean(responseBean);
	    }
	    
	    
	    @Autowired
	    private UserService userService;
	    // 管理员--评论列表
        @RequestMapping(value = "/queryCommentInfoList", method = { RequestMethod.POST })
        public ResponseBean queryCommentInfoList(@RequestBody ArtComment object) {
        	
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                	
                   Page<ArtComment> page = artCommentService.queryCommentInfoList(object.toMongoHashMap(),
                           object.getPage(),
                           this.getSortKey(object),
                           this.getDirection(object));
                   
                   responseBean.setData(page);
                }
            }
            return returnBean(responseBean);
        }
        
        //我的评论
        @Autowired
        private ArtFService artFService;
        @Autowired
        private ArtCollectionService artCollectionService;
        @Autowired
        private SourceWService sourceWService;
        @Autowired
        private PointPriseService pointPriseService;
        @RequestMapping(value = "/MyComments", method = { RequestMethod.PUT })
        public ResponseBean MyComments(@RequestBody ArtComment artComment) {
                ResponseBean responseBean = super.getReturn();
            	List<ArtF> artList = new ArrayList<>();
        		if(artComment != null) {
        			  if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
        					List<ArtComment> artCommentList =  artCommentService.queryMyComments(artComment.toMongoHashMap(), 
        							artComment.getPage(),this.getSortKey(artComment), this.getDirection(artComment));
        			
        				  	for(ArtComment p:artCommentList) {
        				  			String artId = p.getArtId();
        				  			ArtF art = artFService.findOne(artId);
        				  			art.setPointPriseSta(pointPriseService.getPointPriseSta(p.getArtId(), artComment.getUserId()));
        				  			art.setComment(p);
        				  			art.setPointCount(artCommentService.getPointCountByArtId(artId));
        				  			art.setCollectionSta(artCollectionService.getCollectionSta(artId, artComment.getUserId()));
        				  			art.setCollectionCount(artCollectionService.getCollectionCountByArtId(artId));
//        				  			art.setPointPriseDate(p.getComTime());
        				  			art.setCommentCount(artCommentService.getCommentCountByArtId(artId));
        				  			art.setSource(sourceWService.getSourceNameByArtId(artId));
        				  			art.setImgSource(sourceWService.getSourceImgByArtId(artId));
        				  			artList.add(art);
        				  	}
        		               Page<ArtComment> p = new Page<ArtComment>(
        		            		   artList,//是一个List<ArtF>对象
        		            		   artComment.getPage().getPageNumber(),//当前页
        		            		   artComment.getPage().getPageSize(),//每页显示数据条数
        		            		   artComment.getPage().getTotalSize()); 
        				  	responseBean.setData(p);
        			  }
        		}else {
        			responseBean.setCode("A008");
        		}
    		return returnBean(responseBean);
        }
}