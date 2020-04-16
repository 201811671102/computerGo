package com.computerGo.controller;

import com.computerGo.DTO.TheorderDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.pojo.*;
import com.computerGo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UOController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 21:03
 **/
@Controller
@RequestMapping("/UOController")
@Api(value = "UOController")
public class UOController {
    @Autowired
    private UOService uoService;
    @Autowired
    private TheorderService orderService;
    @Autowired
    private RPService rpService;
    @Autowired
    private RepertoryService repertoryService;
    @Autowired
    private PackageService packageService;

    @GetMapping("/getUO")
    @ResponseBody
    @ApiOperation(value = " 订单状态 0 待发货 1 已签收  2已评价 获取用户消费记录|商户订单记录",notes = "500报错")
    public ResultDTO getUO(
            HttpServletRequest request,
            @ApiParam(value = "订单状态",required = true)@RequestParam(value = "state",required = true)Integer state,
            @ApiParam(value = "起始位置",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            List<UO> uoList = uoService.selectByUid(uid,offset,limit);
            List<TheorderDTO> theorderDTOList = new ArrayList<>();
            for (UO uo : uoList){
                try {
                    TheorderDTO theorderDTO  = new TheorderDTO();
                    theorderDTO.SetTheorderDTO(orderService.selectByOid(uo.getOid()));
                    if (theorderDTO.getState() == state) {
                        theorderDTO.setNewPackage(packageService.selectByPid(theorderDTO.getPid()));
                        theorderDTO.setRepertory(repertoryService.selectByRid(rpService.selectBypid(theorderDTO.getPid()).getRid()));
                        theorderDTOList.add(theorderDTO);
                    }
                }catch (Exception e){
                    continue;
                }
            }
            return new ResultUtil().Success(theorderDTOList);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PutMapping("/signover")
    @ResponseBody
    @ApiOperation(value = "用户签收",notes = "500报错")
    public ResultDTO signover(
            HttpServletRequest request,
            @ApiParam(value = "订单id",required = true)@RequestParam(value = "oid",required = true) int oid){
        try {
            Theorder theorder = new Theorder();
            theorder.setOid(oid);
            theorder.setState(1);
            orderService.updateOrderState(theorder);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }


    @PutMapping("/setEvaluation")
    @ResponseBody
    @ApiOperation(value = "用户评价",notes = "500报错")
    public ResultDTO setEvaluation(
            HttpServletRequest request,
            @ApiParam(value = "订单id",required = true)@RequestParam(value = "oid",required = true) int oid,
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true) int pid,
            @ApiParam(value = "评价",required = true)@RequestParam(value = "ealeuation",required = true) int ealeuation){
            try {
                Theorder theorder = new Theorder();
                theorder.setState(2);
                RP rp = rpService.selectBypid(pid);
                Repertory repertory = repertoryService.selectByRid(rp.getRid());
                long count = uoService.getCount(oid);
                repertory.setEvaluation((int) (Math.round((count * repertory.getEvaluation())+ealeuation)/(count+1)));
                repertoryService.changeByRid(repertory);
                orderService.updateOrderState(theorder);
                return new ResultUtil().Success(repertory.getEvaluation());
            }catch (Exception e){
                return new ResultUtil().Error("500",e.toString());
            }
    }
}
