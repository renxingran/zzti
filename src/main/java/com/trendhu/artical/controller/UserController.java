package com.trendhu.artical.controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.service.ArtCollectionService;
import com.trendhu.artical.service.FocusService;
import com.trendhu.artical.service.UserService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/user" })
public class UserController extends RestfullBaseController<User> {
	
	@Autowired
	private UserService userService;
	
	@Override
	protected BaseService<User> getService() {
		return userService;
	}	
	
	@Autowired 
	private FocusService focusService;
	@Autowired
	private ArtCollectionService artCollectionService;
	//管理员查看用户信息详情
    @RequestMapping(value = "/getUserDetails/{id}", method = { RequestMethod.GET })
    public ResponseBean getUserDetails(@PathVariable("id") String id) {
    	
    	User user = new User();
    	
        ResponseBean responseBean = super.getReturn();
        if (!id.isEmpty()) {
        	Long focusNum= focusService.getCountByUserId(id);//关注量
        	Long collectionNum = artCollectionService.getCountByUserId(id);//收藏量
        	
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
            	user  = userService.findOne(id);
            	user.setFocusNum(focusNum);
            	user.setCollectionNum(collectionNum);
                responseBean.setData(user);
            }
        }
        return returnBean(responseBean);
    }
    
    //用户注册
    @RequestMapping(value = "/userAdd", method = { RequestMethod.PUT })
    public ResponseBean userAdd(@RequestBody User object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                   object.prepareHBBean();
                   String phone  = object.getPhone();
                   String email = object.getEmail();
                    String s = userService.getExitByPhone(phone,email);
                    
	                if(s.equals("A001")) {
	                    Date currentTime = new Date();
	                    
	                	object.setRegistrationTime(currentTime);
	                	System.out.println(object.toString());
	                      userService.insert(object);
	                }
	                responseBean.setCode(s);
                } catch (Exception e) {
                	e.printStackTrace();
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }
    
        //用户登录
        @RequestMapping(value = "/login", method = { RequestMethod.PUT })
        public ResponseBean login(@RequestBody User object) {
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                    try {
	                      object.prepareHBBean();
	                      String phone  = object.getPhone();
	                      String pwd = object.getPwd();
	                      String  loginNum = userService.login(phone,pwd);
	                      responseBean.setCode(loginNum);
	                    if(loginNum == "A001") {
	                    	String id = userService.getUserIdByPhone(phone);//获取用户id
	                    	responseBean.setData(id);
	                    }
                    } catch (DuplicateKeyException e) {
                        responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                        logger.error("Key值重复", e);
                    }
                }
            }
            return returnBean(responseBean);
        }
        
        //忘记密码
        @RequestMapping(value = "/forgetPwd", method = { RequestMethod.PUT })
        public ResponseBean forgetPwd(@RequestBody User object) {
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                    try {
	                      object.prepareHBBean();
	                      String phone  = object.getPhone();
	                      String email = object.getEmail();
	                      String pwd = object.getPwd();
	                      String  forgetPwdNum = userService.forgetPwd(phone,email,pwd);
	                      responseBean.setCode(forgetPwdNum);
                    } catch (DuplicateKeyException e) {
                        responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                        logger.error("Key值重复", e);
                    }
                }
            }
            return returnBean(responseBean);
        }
    
     // 管理员查看用户列表
        @RequestMapping(value = "/userInfoList", method = { RequestMethod.POST })
        public ResponseBean userInfoList(@RequestBody User object) {
        	
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                	
                   Page<User> page = userService.queryUserLists(object.toMongoHashMap(),
                           object.getPage(),
                           this.getSortKey(object),
                           this.getDirection(object));
                   responseBean.setData(page);
                }
            }
            return returnBean(responseBean);
        }
        //管理员搜索用户的用户名、手机号
        @RequestMapping(value = "/searchUserInfoList", method = { RequestMethod.POST })
        public ResponseBean searchUserInfoList(@RequestBody User object) {
        	
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
//                   Page<User> page = userService.searchUserInfoList();
//                   responseBean.setData(page);
                }
            }
            return returnBean(responseBean);
        }
        
        //获取用户名、头像
        @RequestMapping(value = "/getUserNameAndImg", method = { RequestMethod.POST })
        public ResponseBean getUserNameAndImg(@RequestBody User object) {
        	
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                		String userId = object.getId();
                		User user = userService.findOne(userId);
                		object.setUserName(user.getUserName());
                		object.setUseImg(user.getUseImg());
                }
                responseBean.setData(object);
            }
            return returnBean(responseBean);
        }
        
        //管理员管理用户----搜索用户名
        @RequestMapping(value = "/searchUserName", method = { RequestMethod.POST })
        public ResponseBean searchUserName(@RequestBody User object) {
        	
            ResponseBean responseBean = super.getReturn();
            if (object != null) {
                if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                	List<User> artList = userService.searchUserName(object);
                	
                	responseBean.setData(artList);
                }
            }else {
            	responseBean.setCode("A008");
            }
            return returnBean(responseBean);
        }
        
        
}
 