package com.computerGo.controller;

import com.computerGo.DTO.TheorderDTO;
import com.computerGo.DTO.TheorderONOrderDTO;
import com.computerGo.DTO.TheorderSuccessDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.pojo.*;
import com.computerGo.pojo.Package;
import com.computerGo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private RepertoryService repertoryService;
    @Autowired
    private IdentityService identityService;


    @PostMapping("/inorder")
    @ResponseBody
    @ApiOperation(value = "下订单",notes = "500报错 404 没有余量 403 商户购买自己发布的商品")
    public ResultDTO inorder(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true)Integer rid,
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true)Integer pid){
        try {
            UR ur = urService.selectByRid(rid);
            if(uid == ur.getUid()){
                return new ResultUtil().Error("403","禁止刷单");
            }
            Repertory repertory = repertoryService.selectByRid(rid);
            if (StringUtils.isEmpty(repertory) || repertory.getNumber() == 0){
                return new ResultUtil().Error("400","售罄");
            }
            String ordernum = System.currentTimeMillis()+"";
            //添加订单
            Theorder theorder = new Theorder();

            theorder.setOrdertime(new Date());
            theorder.setRid(rid);
            theorder.setPid(pid);
            theorder.setNum(1);
            theorder.setState(-1);
            theorder.setUaddress("无");
            theorder.setUname("无");
            theorder.setUphone("无");
            theorder.setOrdernumber(ordernum);
            theorder.setEvaluation(0);
            orderService.insertOrder(theorder);
            //添加用户订单记录
            UO uo = new  UO();
            uo.setUid(uid);
            uo.setOid(theorder.getOid());
            uo.setShelluid(urService.selectByRid(rid).getUid());
            uoService.insertUO(uo);

            TheorderONOrderDTO theorderONOrderDTO = new TheorderONOrderDTO();
            theorderONOrderDTO.SetTheorderDTO(theorder);
            theorderONOrderDTO.setTitle(repertoryService.selectByRid(theorder.getRid()).getTitle());
            theorderONOrderDTO.setPackagemessage(packageService.selectByPid(theorder.getPid()).getPackagemessage());

            return new ResultUtil().Success(theorderONOrderDTO);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
    @GetMapping("/getOrder")
    @ResponseBody
    @ApiOperation(value = "获取订单",notes = "500报错 ")
    public ResultDTO getOrder(
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit,
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid){
        try {
            List<UO> uoList = uoService.selectByUid(uid,offset*limit,limit);
            List<TheorderONOrderDTO> theorderONOrderDTOS = new ArrayList<>();
            for (UO uo : uoList){
                TheorderONOrderDTO theorderONOrderDTO = new TheorderONOrderDTO();
                Theorder theorder = orderService.selectByOid(uo.getOid());
                if (theorder.getState() == -1){
                    theorderONOrderDTO.SetTheorderDTO(theorder);
                    theorderONOrderDTO.setTitle(repertoryService.selectByRid(theorder.getRid()).getTitle());
                    theorderONOrderDTO.setPackagemessage(packageService.selectByPid(theorder.getPid()).getPackagemessage());
                    theorderONOrderDTOS.add(theorderONOrderDTO);
                }
            }
            return new ResultUtil().Success( theorderONOrderDTOS);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PostMapping("/byRepertory")
    @ResponseBody
    @ApiOperation(value = "购买",notes = "500报错")
    public ResultDTO byRepertory(
            @ApiParam(value = "地址id",required = true)@RequestParam(value = "iid",required = true)Integer iid,
            @ApiParam(value = "订单id",required = true)@RequestParam(value = "oid",required = true)Integer oid,
            @ApiParam(value = "购买数量",required = true)@RequestParam(value = "num",required = true)Integer num){
        try {
            Theorder theorder = orderService.selectByOid(oid);
            theorder.setNum(num);
            theorder.setState(0);
            Identity identity = identityService.selectByIid(iid);
            theorder.setUaddress(identity.getUaddress());
            theorder.setUname(identity.getUname());
            theorder.setUphone(identity.getUphone());
            orderService.updateOrderState(theorder);
            UR ur = urService.selectByRid(theorder.getRid());
            //添加商户订单记录
            UO uo = uoService.selectByOid(oid);
            uo.setUid(ur.getUid());
            uo.setOid(theorder.getOid());
            uoService.insertUO(uo);
            //修改套餐数量
            Repertory repertory = repertoryService.selectByRid(theorder.getRid());
            repertory.setNumber(repertory.getNumber()-1);
            repertoryService.updateRepertory(repertory);

            TheorderSuccessDTO theorderSuccessDTO = new TheorderSuccessDTO();
            theorderSuccessDTO.SetTheorderDTO(theorder);
            theorderSuccessDTO.setTitle(repertory.getTitle());
            theorderSuccessDTO.setPackagemessage(packageService.selectByPid(theorder.getPid()).getPackagemessage());
            return new ResultUtil().Success(theorderSuccessDTO);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
}
