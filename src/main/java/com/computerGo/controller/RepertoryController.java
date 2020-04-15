package com.computerGo.controller;

import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.pojo.Order;
import com.computerGo.pojo.Package;
import com.computerGo.pojo.UO;
import com.computerGo.service.OrderService;
import com.computerGo.service.PackageService;
import com.computerGo.service.UOService;
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
    private OrderService orderService;
    @Autowired
    private UOService uoService;


    @PostMapping("/buyRepertory")
    @ResponseBody
    @ApiOperation(value = "购买",notes = "500报错 404 没有余量")
    public ResultDTO buyRepertory(
            HttpServletRequest request,
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true)Integer pid,
            @ApiParam(value = "购买数量",required = true)@RequestParam(value = "num",required = true)Integer num){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            Package packages = packageService.selectByPid(pid);
            if (StringUtils.isEmpty(packages) || packages == null){
                return new ResultUtil().Error("400","售罄");
            }
            Order order = new Order();
            order.setState(0);
            order.setPid(pid);
            order.setTime(new Date());
            order.setNum(num);
            orderService.insertOrder(order);
            UO uo = new  UO();
            uo.setUid(uid);
            uo.setOid(order.getOid());
            uoService.insertUO(uo);
            packages.setNumber(packages.getNumber()-1);
            packageService.updatePackage(packages);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

}
