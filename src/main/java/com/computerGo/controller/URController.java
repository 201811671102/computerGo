package com.computerGo.controller;

import com.computerGo.DTO.RepertoryDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.base.redis.RedisUtil;
import com.computerGo.pojo.UR;
import com.computerGo.service.RepertoryService;
import com.computerGo.service.URService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName URController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 21:15
 **/
@Controller
@Api(value = "URController")
@RequestMapping("/URController")
public class URController {
    @Autowired
    private URService urService;
    @Autowired
    private RepertoryService repertoryService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/myrepertory")
    @ResponseBody
    @ApiOperation(value = "获取商户商品",notes = "500报错")
    public ResultDTO myrepertory(
            HttpServletRequest request,
            @ApiParam(value = "库存类型 0电脑 1cpu 2主板 3显卡 4散热器 5内存 ",required = true)
            @RequestParam(value = "type",required = true) Integer type,
            @ApiParam(value = "起始位置",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit){
        try {
            List<UR> urList = urService.selectByUid(
                    Integer.parseInt(request.getSession().getAttribute("uid").toString()),offset,limit);
            List<RepertoryDTO> repertoryDTOS = new ArrayList<>();
            for (UR ur : urList){
                RepertoryDTO repertoryDTO = new RepertoryDTO();
                try {
                    repertoryDTO.setRepertoryDTO(repertoryService.selectByRid(ur.getRid()));
                    repertoryDTO.setWatched(redisUtil.get(ur.getRid().toString()).toString());
                }catch (Exception e){
                    continue;
                }
                repertoryDTOS.add(repertoryDTO);
            }
            return new ResultUtil().Success(repertoryDTOS);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

}
