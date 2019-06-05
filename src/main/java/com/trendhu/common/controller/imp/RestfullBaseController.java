package com.trendhu.common.controller.imp;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.ArtComment;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.FM;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.service.ArtCommentService;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.BaseMgBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.BaseController;
import com.trendhu.common.controller.IRestfulController;
import com.trendhu.common.service.BaseService;

@RestController
public abstract class RestfullBaseController<T extends BaseMgBean<T>> extends BaseController
        implements IRestfulController<T> {


    protected abstract BaseService<T> getService();

    @Override
    @RequestMapping(value = "/add", method = { RequestMethod.PUT })
    public ResponseBean insert(@RequestBody T object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                    object.prepareHBBean();
                    responseBean.setData(this.getService().insert(object));
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }
    
    
    
    @Override
    @RequestMapping(value = "/addAll", method = { RequestMethod.PUT })
    public ResponseBean insertAll(@RequestBody List<T> object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                     for (T item : object) {
                         item.prepareHBBean();
                    }
                    responseBean.setData(this.getService().insertAll(object).size());
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值含有重复");
                    logger.error("Key值含有重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }

    @Override
    @RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE })
    public ResponseBean remove(@PathVariable("id") String id) {
        ResponseBean responseBean = super.getReturn();
        if (!id.isEmpty()) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                if (this.getService().remove(id)) {
                    responseBean.setData(id);
                } else {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DELETE_ERROR, "数据删除失败");
                }
            }
        }
        return returnBean(responseBean);
    }
    
    @Override
    @RequestMapping(value = "/deleteAll", method = { RequestMethod.POST })
    public ResponseBean removeAll(@RequestBody List<String> idList) {
        ResponseBean responseBean = super.getReturn();
        if (!idList.isEmpty()) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                Long removeCount = this.getService().removeAll(idList);
                if (removeCount > 0) {
                    responseBean.setData(removeCount);
                } else {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DELETE_ERROR, "数据删除失败");
                }
            }
        }
        return returnBean(responseBean);
    }
    
    @Override
    @RequestMapping(value = "/update", method = { RequestMethod.POST })
    public ResponseBean update(@RequestBody T object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {

                if (this.getService().findOne(object.toMongoHashMap()) != null) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_UPDATE_REPEAT_ERROR, "数据与原数据相同");
                    responseBean.setData(false);
                    return returnBean(responseBean);
                }

                if (this.getService().update(object.toMongoHashMap())) {
                    responseBean.setData(true);
                } else {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_UPDATE_ERROR, "数据修改失败");
                    responseBean.setData(false);
                }//5ce10d3ed9961e58dc2b98a3
            }
        }
        return returnBean(responseBean);
    }

    @Override
    @RequestMapping(value = "/get/{id}", method = { RequestMethod.GET })
    public ResponseBean findOne(@PathVariable("id") String id) {
        ResponseBean responseBean = super.getReturn();
        if (!id.isEmpty()) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                responseBean.setData(this.getService().findOne(id));
            }
        }
        return returnBean(responseBean);
    }
    
    @Autowired
    private ArtFService artFService;
    @Override
    @RequestMapping(value = "/queryMyCollection", method = { RequestMethod.POST })
    public ResponseBean queryMyCollection(@RequestBody T object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
//                responseBean.setData(
                		@SuppressWarnings("unchecked")
                		List<String> artIdList=new ArrayList<String>();
                		
						Page<ArtCollection> page=(Page<ArtCollection>) this.getService()
                                         .query(object.toMongoHashMap(),
                                                object.getPage(),
                                                this.getSortKey(object),
                                                this.getDirection(object));
                		List<ArtCollection> list=(List<ArtCollection>) page.getList();
                		
                		for(ArtCollection artCollection: list) {
                			artIdList.add(artCollection.getArtId());
//                			System.out.println("fm.getArtId:"+fm.getArtId()+"======="+"fm.getFmname:"+fm.getFmname());
                		}
//                	List<ArtF> artFList  = (List<ArtF>) artService.findAll(artIdList);
//                	
//                	List<FM> fmList=	(List<FM>) this.getService().findAll(artIdList);
//                	System.out.println("listId:"+artIdList);
                	responseBean.setData(artFService.findAll(artIdList));
            }
        }
        return returnBean(responseBean);
    }
    

    @Autowired
    private ArtCommentService artCommentService;
    @Override
    @RequestMapping(value = "/queryMyComments", method = { RequestMethod.POST })
    public ResponseBean queryMyComments(@RequestBody T object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                		@SuppressWarnings("unchecked")
                		List<String> artIdList=new ArrayList<String>();
                		
						Page<ArtComment> page=(Page<ArtComment>) this.getService()
                                         .query(object.toMongoHashMap(),
                                                object.getPage(),
                                                this.getSortKey(object),
                                                this.getDirection(object));
                		List<ArtComment> list=(List<ArtComment>) page.getList();
                		
                		for(ArtComment artComment: list) {
                			artIdList.add(artComment.getArtId());
                		}
                		responseBean.setData(artFService.findAll(artIdList));
            }
        }
        return returnBean(responseBean);
    }
    


    @Override
    @RequestMapping(value = "/query", method = { RequestMethod.POST })
    public ResponseBean query(@RequestBody T object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	
                responseBean.setData(this.getService()
                                         .query(object.toMongoHashMap(),
                                                object.getPage(),
                                                this.getSortKey(object),
                                                this.getDirection(object)));
            }
        }
        return returnBean(responseBean);
    }
    
    @Autowired
    private HisActService hisActService;
    @Override
    @RequestMapping(value = "/queryArtList", method = { RequestMethod.POST })
    public ResponseBean queryArtList(@RequestBody T object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	Page<ArtF> page = (Page<ArtF>) this.getService()
                        .query(object.toMongoHashMap(),
                                object.getPage(),
                                this.getSortKey(object),
                                this.getDirection(object));
            	
            	List<ArtF> artList = (List<ArtF>) page.getList();
            	List<ArtF> artL = new  ArrayList<ArtF>();
            	String artId;
            	for(ArtF art:artList) {
            		artId = art.getId();
            		art.setArtHisCount(hisActService.getCountById(artId)); 
            		artL.add(art);
            	}
            	
            	@SuppressWarnings("unchecked")
//				Page<ArtF> p = (Page<ArtF>) artL;
            	
            	Page<ArtF> p = new Page<ArtF>(artL, 
            			object.getPage().getPageNumber(), 
            			object.getPage().getPageSize(),
            			object.getPage().getTotalSize());
                responseBean.setData(p);
            }
        }
        return returnBean(responseBean);
    }
    
    @Override
    @RequestMapping(value = "/count", method = {RequestMethod.GET })
    public ResponseBean count() {
        ResponseBean responseBean = super.getReturn();
        if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            responseBean.setData(this.getService().getCount());
        }
        return returnBean(responseBean);
    }

    /**
     * sortKey和direction的配置
     */
    protected String getSortKey(T object) {
        return StringUtils.isNotEmpty(object.getSortKey()) ? object.getSortKey() : "id";
    }

    protected Direction getDirection(T object) {
        return object.getAsc() != null && object.getAsc() ? Direction.ASC : Direction.DESC;
    }

    @Override
    @RequestMapping(value = "/getArtAndComm/{id}", method = { RequestMethod.GET })
    public ResponseBean findArtAndComm(@PathVariable("id") String id) {
        ResponseBean responseBean = super.getReturn();
        if (!id.isEmpty()) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                responseBean.setData(this.getService().findArtAndComm(id));
            }
        }
        return returnBean(responseBean);
    }
    
}