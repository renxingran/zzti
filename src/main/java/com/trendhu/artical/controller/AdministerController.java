package com.trendhu.artical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trendhu.artical.bean.Administer;
import com.trendhu.artical.service.AdministerService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/administer" })
public class AdministerController extends RestfullBaseController<Administer> {

	@Autowired
	private AdministerService administerService;
	
	@Override
	protected BaseService<Administer> getService() {
		return administerService;
	}	
	  //管理员登录
    @RequestMapping(value = "/login", method = { RequestMethod.PUT })
    public ResponseBean login(@RequestBody Administer object) {
        ResponseBean responseBean = super.getReturn();
        if (object != null) {
            if (responseBean.getCode().equals(StatusCode.CODE_SUCCESS)) {
                try {
                      object.prepareHBBean();
                      String admAccount  = object.getAdmAccount();
                      String admPwd = object.getAdmPwd();
                      String  loginNum = administerService.login(admAccount,admPwd);
                    responseBean.setCode(loginNum);
                } catch (DuplicateKeyException e) {
                    responseBean.setCodeAndErrMsg(StatusCode.CODE_DUPLICATE_KEY_ERROR, "Key值重复");
                    logger.error("Key值重复", e);
                }
            }
        }
        return returnBean(responseBean);
    }
}