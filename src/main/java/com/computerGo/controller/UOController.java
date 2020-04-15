package com.computerGo.controller;

import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.pojo.Order;
import com.computerGo.pojo.UO;
import com.computerGo.pojo.UR;
import com.computerGo.service.OrderService;
import com.computerGo.service.UOService;
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
    private OrderService orderService;

    @GetMapping("/getUO")
    @ResponseBody
    @ApiOperation(value = "获取用户消费记录|商户订单记录",notes = "500报错")
    public ResultDTO getUO(
            HttpServletRequest request,
            @ApiParam(value = "订单状态",required = true)@RequestParam(value = "state",required = true)Integer state,
            @ApiParam(value = "起始位置",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            List<UO> uoList = uoService.selectByUid(uid,offset,limit);
            List<Order> orderList = new ArrayList<>();
            for (UO uo : uoList){
                try {
                    Order order = orderService.selectByOid(uo.getOid());
                    if (order.getState() == state) {
                        orderList.add(order);
                    }
                }catch (Exception e){
                    continue;
                }
            }
            return new ResultUtil().Success(orderList);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
}
