package com.computerGo.controller;

import com.computerGo.DTO.TheorderDTO;
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
import org.springframework.web.bind.annotation.*;


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
    private TheorderService theorderService;
    @Autowired
    private RPService rpService;
    @Autowired
    private RepertoryService repertoryService;
    @Autowired
    private PackageService packageService;

    @GetMapping("/getUO")
    @ResponseBody
    @ApiOperation(value = " 订单状态 0 待发货 1已发货 2 已签收  3已评价 获取用户消费记录|商户订单记录",notes = "500报错")
    public ResultDTO getUO(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "订单状态",required = true)@RequestParam(value = "state",required = true)Integer state,
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit){
        try {
            List<UO> uoList = uoService.selectByUid(uid,offset*limit,limit);
            List<TheorderDTO> theorderDTOList = new ArrayList<>();
            for (UO uo : uoList){
                try {
                    Theorder theorder = theorderService.selectByOid(uo.getOid());
                    TheorderDTO theorderDTO  = new TheorderDTO();
                    theorderDTO.SetTheorderDTO(theorder);
                    if (theorder.getState() == state) {
                        Package newpackage =  packageService.selectByPid(theorder.getPid());
                        Repertory repertory = repertoryService.selectByRid(theorder.getRid());
                        theorderDTO.setTitle(repertory.getTitle());
                        theorderDTO.setRid(repertory.getRid());
                        theorderDTO.setPackagemessage(newpackage.getPackagemessage());
                        switch (repertory.getType()){
                            case 0:theorderDTO.setType("品牌机");break;
                            case 1:theorderDTO.setType("CPU处理器");break;
                            case 2:theorderDTO.setType("主板");break;
                            case 3:theorderDTO.setType("显卡");break;
                            case 4:theorderDTO.setType("散热器");break;
                            case 5:theorderDTO.setType("内存");break;
                        }
                        theorderDTO.setEvaluation(repertoryService.selectByRid(theorder.getRid()).getEvaluation());
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
    @PutMapping("/delivered")
    @ResponseBody
    @ApiOperation(value = "商户已经发货",notes = "500报错")
    public ResultDTO delivered(
            @ApiParam(value = "订单id",required = true)@RequestParam(value = "oid",required = true) int oid){
        try {
            Theorder theorder = new Theorder();
            theorder.setOid(oid);
            theorder.setState(1);
            theorderService.updateOrderState(theorder);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PutMapping("/signover")
    @ResponseBody
    @ApiOperation(value = "用户签收",notes = "500报错")
    public ResultDTO signover(
            @ApiParam(value = "订单id",required = true)@RequestParam(value = "oid",required = true) int oid){
        try {
            Theorder theorder = new Theorder();
            theorder.setOid(oid);
            theorder.setState(2);
            theorderService.updateOrderState(theorder);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }


    @PutMapping("/setEvaluation")
    @ResponseBody
    @ApiOperation(value = "用户评价",notes = "500报错")
    public ResultDTO setEvaluation(
            @ApiParam(value = "订单id",required = true)@RequestParam(value = "oid",required = true) int oid,
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true) int pid,
            @ApiParam(value = "评价",required = true)@RequestParam(value = "ealeuation",required = true) int ealeuation){
            try {
                Theorder theorder = new Theorder();
                theorder.setOid(oid);
                theorder.setState(3);
                RP rp = rpService.selectBypid(pid);
                Repertory repertory = repertoryService.selectByRid(rp.getRid());
                long count = uoService.getCount(oid);
                repertory.setEvaluation((int) (Math.round((count * repertory.getEvaluation())+ealeuation)/(count+1)));
                repertoryService.changeByRid(repertory);
                theorderService.updateOrderState(theorder);
                return new ResultUtil().Success();
            }catch (Exception e){
                return new ResultUtil().Error("500",e.toString());
            }
    }
}
