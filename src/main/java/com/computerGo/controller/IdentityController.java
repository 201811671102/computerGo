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

import java.util.ArrayList;
import java.util.List;


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
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "姓名",required = true)@RequestParam(value = "uname",required = true)String uname,
            @ApiParam(value = "电话",required = true)@RequestParam(value = "uphone",required = true)String uphone,
            @ApiParam(value = "详细地址",required = true)@RequestParam(value = "uaddress",required = true)String uaddress
    ){
        try {
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
    @GetMapping("/getAddress")
    @ResponseBody
    @ApiOperation(value = "获取地址地址")
    public ResultDTO deleteAddress(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit
    ){
        try {
            List<UI> uiList  = uiService.selectByUidRow(uid,offset*limit,limit);
            List<Identity> identities = new ArrayList<>();
            for (UI ui : uiList){
                identities.add(identityService.selectByIid(ui.getIid()));
            }
            return new ResultUtil().Success(identities);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
}
