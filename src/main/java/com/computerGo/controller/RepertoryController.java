package com.computerGo.controller;

import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.pojo.Package;
import com.computerGo.pojo.Theorder;
import com.computerGo.pojo.UO;
import com.computerGo.pojo.UR;
import com.computerGo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @ClassName RepertoryController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 19:53
 **/
@Controller
@RequestMapping("/RepertoryController")
@Api(value = "RepertoryController")
public class RepertoryController {

    @Autowired
    private PackageService packageService;
    @Autowired
    private TheorderService orderService;
    @Autowired
    private UOService uoService;
    @Autowired
    private URService urService;
    @Autowired
    private UIService uiService;


    @PostMapping("/buyRepertory")
    @ResponseBody
    @ApiOperation(value = "购买",notes = "500报错 404 没有余量")
    public ResultDTO buyRepertory(
            HttpServletRequest request,
            @ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true)Integer rid,
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true)Integer pid,
            @ApiParam(value = "购买数量",required = true)@RequestParam(value = "num",required = true)Integer num,
            @ApiParam(value = "送货地址",required = true)@RequestParam(value = "uaddress",required = true)String uaddress){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            Package packages = packageService.selectByPid(pid);
            if (StringUtils.isEmpty(packages) || packages == null){
                return new ResultUtil().Error("400","售罄");
            }
            //添加订单
            Theorder theorder = new Theorder();
            theorder.setState(0);
            theorder.setPid(pid);
            theorder.setOrdertime(new Date());
            theorder.setNum(num);
            theorder.setUaddress(uaddress);
            orderService.insertOrder(theorder);
            //添加用户订单记录
            UO uo = new  UO();
            uo.setUid(uid);
            uo.setOid(theorder.getOid());
            uoService.insertUO(uo);
            //添加商户订单记录
            UR ur = urService.selectByRid(rid);
            uo.setUid(ur.getUid());
            uo.setOid(theorder.getOid());
            uoService.insertUO(uo);
            //修改套餐数量
            packages.setNumber(packages.getNumber()-1);
            packageService.updatePackage(packages);
            return new ResultUtil().Success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultUtil().Error("500",e.toString());
        }
    }

}
