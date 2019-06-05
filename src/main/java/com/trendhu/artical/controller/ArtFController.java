package com.trendhu.artical.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trendhu.artical.bean.ArtComment;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.service.ArtCollectionService;
import com.trendhu.artical.service.ArtCommentService;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.ArticleModelService;
import com.trendhu.artical.service.FocusService;
import com.trendhu.artical.service.HisActService;
import com.trendhu.artical.service.PointPriseService;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.artical.service.UserService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/artAndComment" })
public class ArtFController extends RestfullBaseController<ArtF>{

	@Autowired
	private ArtFService artFService;
	@Autowired
	private ArtCommentService artCommentService;
	@Autowired
	private ArtCollectionService artCollectionService;
	@Autowired
	private PointPriseService pointPriseService;
	@Autowired
	private FocusService focusService;
	@Autowired
	private UserService userService;
	
	@Override
	protected BaseService<ArtF> getService() {
		return artFService;
	}	
	@Autowired
	private SourceWService sourceWService;
	@RequestMapping(value = "/getArtAndComment", method = { RequestMethod.POST})
    public ResponseBean findArtAndComment(@RequestBody ArtF T) {
		
		String artId = T.getId();
		String userId = T.getUserId();
		
		
    	ArtF art  =artFService.findOne(artId);
    	String sourceName = art.getSource();
    	
    	String  sourceId = sourceWService.findSourceId(sourceName);//SourceId
    	
    	T.setSourceId(sourceId);
		
        ResponseBean responseBean = super.getReturn();
       
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	ArtComment artComment = new ArtComment();
            	artComment.setArtId(artId);
            	
            	List<ArtComment> comments = artCommentService.query(artComment.toMongoHashMap("artId"));
            	Boolean collectionSta =  artCollectionService.getCollectionSta(artId,userId);
            	Boolean pointPriseSta = pointPriseService.getPointPriseSta(artId, userId);
            	Boolean focusSta = focusService.getFocusSta(sourceId, userId);

            	Long commentsCount = artCommentService.getCommentCountByArtId(artId);//文章评论量
            	Long pointCount = pointPriseService.getPointCountByArtId(artId);//文章点赞量
            	Long collectionCount = artCollectionService.getCollectionCountByArtId(artId);//文章收藏量
            	
            	List<ArtComment> commentsList = new ArrayList<>();
            	for(ArtComment comment : comments) {
            	User user =	userService.findOne(comment.getUserId());
            		comment.setUserName(user.getUserName());
            		comment.setUseImg(user.getUseImg());
            		commentsList.add(comment);
            	}
            	
            	ArtF a = artFService.findOne(artId);
            	try {
		            	a.setArtcomments(commentsList);
		            	a.setCommentCount(commentsCount);
		            	a.setPointCount(pointCount);
		            	a.setCollectionCount(collectionCount);
		            	a.setCollectionSta(collectionSta);
		            	a.setPointPriseSta(pointPriseSta);
		            	a.setFocusSta(focusSta);
		            	a.setUserId(T.getUserId());
		                responseBean.setData(a);
            	} catch (Exception e) {
				}
        }
        return returnBean(responseBean);
    }
	@Autowired
	private HisActService hisActService;
	//管理员查看文章信息详情
    @RequestMapping(value = "/getArtDetails/{id}", method = { RequestMethod.GET })
    public ResponseBean getArtDetails(@PathVariable("id") String id) {
    	
    	ArtF artF = new ArtF();
    	
        ResponseBean responseBean = super.getReturn();
        if (!id.isEmpty()) {
        	Long artHisCount= hisActService.getHisCountByArtId(id);//文章浏览量
        	Long commentCount = artCommentService.getCommentCountByArtId(id);//文章评论量
        	
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	artF  = artFService.findOne(id);
            	artF.setArtHisCount(artHisCount);
            	artF.setCommentCount(commentCount);
         	   artF.setFeatures(articleModelService.getFeatureByArtId(artF.getId()));

                responseBean.setData(artF);
            }
        }
        return returnBean(responseBean);
    }

    @Autowired
    private  ArticleModelService articleModelService;
    // 管理员查看文章信息列表
    @RequestMapping(value = "/ArtInfoList", method = { RequestMethod.POST })
    public ResponseBean ArtInfoList(@RequestBody ArtF object) {
    	
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	
               Page<ArtF> page = artFService.queryArtInfoLists(object.toMongoHashMap(),
                       object.getPage(),
                       this.getSortKey(object),
                       this.getDirection(object));
               
               List<ArtF> artList = (List<ArtF>) page.getList();
               
               for(ArtF art:artList) {
            	   art.setArtHisCount(hisActService.getHisCountByArtId(art.getId()));
            	   art.setCommentCount(artCommentService.getCommentCountByArtId(art.getId()));
               }
               responseBean.setData(page);
            }
        }
        return returnBean(responseBean);
    }

    //用户搜索新闻
    @RequestMapping(value = "/searchNews", method = { RequestMethod.POST })
    public ResponseBean searchNews(@RequestBody ArtF object) {
    	
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
        	String keyWords = object.getKeyWords();
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	List<ArtF> artList = artFService.queryKeyWordsLists(keyWords);
            	
                List<ArtF> artList1 = new ArrayList<ArtF>();
            	for(ArtF art : artList) {
            		  String id = art.getId();
               	   art.setArtHisCount(hisActService.getHisCountByArtId(id));
               	   artList1.add(art);
            	}
            	
            	responseBean.setData(artList1);
            }
        }else {
        	responseBean.setCode("A008");
        }
        return returnBean(responseBean);
    }
   
    //管理员--文章发布管理--文章标题搜索
    @RequestMapping(value = "/searchNewsTitle", method = { RequestMethod.POST })
    public ResponseBean searchNewsTitle(@RequestBody ArtF object) {
    	
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
        	
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	List<ArtF> artList = artFService.searchNewsTitle(object);
            	
            	List<ArtF>  artLists = new ArrayList<>();
                  for(ArtF art:artList) {
              	   art.setArtHisCount(hisActService.getHisCountByArtId(art.getId()));
              	   art.setCommentCount(artCommentService.getCommentCountByArtId(art.getId()));
              	 artLists.add(art);
                 }
            	responseBean.setData(artLists);
            }
        }else {
        	responseBean.setCode("A008");
        }
        return returnBean(responseBean);
    }
  }