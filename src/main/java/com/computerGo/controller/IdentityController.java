package com.computerGo.controller;

import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.pojo.Identity;
import com.computerGo.pojo.UI;
import com.computerGo.service.IdentityService;
import com.computerGo.service.UIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IdentityController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/16 23:32
 **/
@Controller
@Api(value = "IdentityController")
@RequestMapping("/IdentityController")
public class IdentityController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private UIService uiService;

    @PostMapping("/addAddress")
    @ResponseBody
    @ApiOperation(value = "添加地址")
    public ResultDTO addAddress(
            HttpServletRequest request,
            @ApiParam(value = "姓名",required = true)@RequestParam(value = "uname",required = true)String uname,
            @ApiParam(value = "电话",required = true)@RequestParam(value = "uphone",required = true)String uphone,
            @ApiParam(value = "详细地址",required = true)@RequestParam(value = "uaddress",required = true)String uaddress
    ){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            Identity identity = new Identity();
            identity.setUname(uname);
            identity.setUphone(uphone);
            identity.setUaddress(uaddress);
            identityService.insertIdentity(identity);
            UI ui = new UI();
            ui.setUid(uid);
            ui.setIid(identity.getIid());
            uiService.insertUI(ui);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @DeleteMapping("/deleteAddress")
    @ResponseBody
    @ApiOperation(value = "删除地址")
    public ResultDTO deleteAddress(
            HttpServletRequest request,
            @ApiParam(value = "地址ip",required = true)@RequestParam(value = "iid",required = true)Integer iid
    ){
        try {
            identityService.deleteByIid(iid);
            uiService.deleteByIid(iid);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
}
