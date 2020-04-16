package com.computerGo.controller;

import com.computerGo.DTO.RepertoryDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.base.redis.RedisUtil;
import com.computerGo.ftp.FtpOperation;
import com.computerGo.pojo.Package;
import com.computerGo.pojo.RP;
import com.computerGo.pojo.Repertory;
import com.computerGo.pojo.UR;
import com.computerGo.service.PackageService;
import com.computerGo.service.RPService;
import com.computerGo.service.RepertoryService;
import com.computerGo.service.URService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.MessageUtils;

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
    @Autowired
    private FtpOperation ftpOperation;
    @Autowired
    private PackageService packageService;
    @Autowired
    private RPService rpService;


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
                    List<RP> rpList = rpService.selectByRid(ur.getRid());
                    List<Package> packageList = new ArrayList<>();
                    for (RP rp : rpList){
                        packageList.add(packageService.selectByPid(rp.getPid()));
                    }
                    repertoryDTO.setPackageList(packageList);
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
    @PostMapping("/addrepertory")
    @ResponseBody
    @ApiOperation(value = "添加库存",notes = "500报错 400 文件上传失败 文件先上传套餐信息")
    public ResultDTO addrepertory(
            HttpServletRequest request,
            @ApiParam(value = "库存标题",required = true)@RequestParam(value = "title",required = true) String title,
            @ApiParam(value = "照片",required = true)@RequestParam(value = "photoarr",required = true)MultipartFile[] photoarr){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            Repertory repertory = new Repertory();
            repertory.setTitle(title);
            repertory.setPrice(0.0);
            repertory.setEvaluation(0);
            ftpOperation.connectToServer();
            StringBuilder photo = new StringBuilder();
            for (int i = photoarr.length ; i >0 ; i--){
                try {
                    photoarr[i].getName();
                    photoarr[i].getOriginalFilename();
                    photoarr[i].getContentType();
                    String phototype = photoarr[i].getOriginalFilename().substring(photoarr[i].getOriginalFilename().lastIndexOf("."));
                    String name = System.currentTimeMillis()+"."+phototype;
                    boolean b = ftpOperation.uploadToFtp(photoarr[i].getInputStream(),name,"/computerGo");
                    if(!b){
                        return new ResultUtil().Error("400",photoarr[i].getOriginalFilename()+"文件上传失败");
                    }
                    photo.append(name+";");
                }catch (Exception e){
                    continue;
                }
            }
            repertory.setPhoto(photo.toString());
            repertoryService.insertRepertory(repertory);
            //添加商户库存记录
            UR ur = new UR();
            ur.setUid(uid);
            ur.setRid(repertory.getRid());
            urService.insertUR(ur);
            redisUtil.set(repertory.getRid().toString(),0);
            ftpOperation.closeConnect();
            return new ResultUtil().Success(repertory.getRid());
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }@PostMapping("/addpackage")
    @ResponseBody
    @ApiOperation(value = "添加套餐",notes = "500报错 ")
    public ResultDTO addpackage(
            HttpServletRequest request,
            @ApiParam(value = "库存类型 0电脑 1cpu 2主板 3显卡 4散热器 5内存 ",required = true)
            @RequestParam(value = "type",required = true) Integer type,
            @ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true) Integer rid,
            @ApiParam(value = "套餐",required = true)@RequestParam(value = "packagemessage",required = true) String packagemessage,
            @ApiParam(value = "套餐数量",required = true)@RequestParam(value = "number",required = true) Integer number,
            @ApiParam(value = "套餐价格",required = true)@RequestParam(value = "price",required = true) double price){
        try {
            List<RP> rpList = rpService.selectByRid(rid);
            Package npackage = new Package();
            for (RP newrp : rpList){
                npackage = packageService.selectByPid(newrp.getPid());
                price = npackage.getPrice() > price ? price : npackage.getPrice();
            }
            Package newpackage = new Package();
            newpackage.setPackagemessage(packagemessage);
            newpackage.setNumber(number);
            newpackage.setPrice(price);
            packageService.insertPackage(newpackage);
            RP rp = new RP();
            rp.setPid(newpackage.getPid());
            rp.setRid(rid);
            rp.setType(type);
            rpService.insertRP(rp);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
    @PutMapping("/addWatched")
    @ResponseBody
    @ApiOperation(value = "添加浏览人数",notes = "500报错")
    public ResultDTO addWatched(@ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true)Integer rid){
        try {
            redisUtil.incr(rid.toString(),1);
            return new ResultUtil().Success(redisUtil.get(rid.toString()));
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PutMapping("/deleteRepertory")
    @ResponseBody
    @ApiOperation(value = "删除库存",notes = "500报错")
    public ResultDTO deleteRepertory(@ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true)Integer rid){
        try {
            urService.deleteByRid(rid);
            redisUtil.del(rid.toString());
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

}
