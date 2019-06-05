package com.trendhu.artical.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.BaseService;

public interface ArtRecomService extends BaseService<ArtRecom>{
	
    public  ArtRecom queryArtLists(ArtRecom object);
}