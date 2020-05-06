package com.computerGo.controller;

import com.computerGo.DTO.RepertoryOneDTO;
import com.computerGo.DTO.RepertorySellerDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.base.redis.RedisUtil;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private PackageService packageService;
    @Autowired
    private RPService rpService;


    @GetMapping("/myrepertory")
    @ResponseBody
    @ApiOperation(value = "获取商户商品",notes = "500报错")
    public ResultDTO myrepertory(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "库存类型 0电脑 1cpu 2主板 3显卡 4散热器 5内存 ",required = true)
            @RequestParam(value = "type",required = true) Integer type,
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true) int offset,
            @ApiParam(value = "数据条数",required = true)@RequestParam(value = "limit",required = true) int limit){
        try {
            List<UR> urList = urService.selectByUid(uid,offset*limit,limit);
            List<RepertorySellerDTO> repertorySellerDTOList = new ArrayList<>();
            for (UR ur : urList){
                RepertorySellerDTO repertorySellerDTO = new RepertorySellerDTO();
                try {
                    Repertory repertory = repertoryService.selectByRid(ur.getRid());
                    if (repertory.getType() == type) {
                        repertorySellerDTO.setRepertoryDTO(repertory);
                        repertorySellerDTO.setWatched(redisUtil.get(ur.getRid().toString()).toString());
                        repertorySellerDTOList.add(repertorySellerDTO);
                    }
                }catch (Exception e){
                    continue;
                }
            }
            return new ResultUtil().Success(repertorySellerDTOList);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
    @PostMapping("/addrepertory")
    @ResponseBody
    @ApiOperation(value = "添加库存",notes = "500报错 400 文件上传失败 文件先上传套餐信息")
    public ResultDTO addrepertory(
            @ApiParam(value = "库存类型 0电脑 1cpu 2主板 3显卡 4散热器 5内存 ",required = true)
            @RequestParam(value = "type",required = true) Integer type,
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "库存数量",required = true)@RequestParam(value = "number",required = true) Integer number,
            @ApiParam(value = "库存标题",required = true)@RequestParam(value = "title",required = true) String title,
            @ApiParam(value = "轮播照片",required = true)@RequestParam(value = "photoarr",required = true)String[] photoarr,
            @ApiParam(value = "详情照片",required = true)@RequestParam(value = "photoarrdetails",required = true)String[] photoarrdetails){
        try {
            Repertory repertory = new Repertory();
            repertory.setTitle(title);
            repertory.setNumber(number);
            repertory.setTitle(title);
            repertory.setPrice(0.0);
            repertory.setEvaluation(0);
            repertory.setType(type);
            /*
             ftpOperation.connectToServer();
            StringBuilder photo = new StringBuilder();
            for (int i = photoarr.length ; i >0 ; i--){
                try {
                    String phototype = photoarr[i].getOriginalFilename().substring(photoarr[i].getOriginalFilename().lastIndexOf("."));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String path = simpleDateFormat.format(System.currentTimeMillis());
                    String name = path+"/"+System.currentTimeMillis()+"."+phototype;
                    boolean b = ftpOperation.uploadToFtp(photoarr[i].getInputStream(),name,"/photo/computerGo/"+path);
                    if(!b){
                        return new ResultUtil().Error("400",photoarr[i].getOriginalFilename()+"文件上传失败");
                    }
                    photo.append("http://39.96.68.53:70/computerGo/"+name+";");
                }catch (Exception e){
                    continue;
                }
            }
            repertory.setPhoto(photo.toString());
            StringBuilder detailsphoto = new StringBuilder();
            for (int i = photoarrdetails.length ; i >0 ; i--){
                try {
                    String phototype = photoarrdetails[i].getOriginalFilename().substring(photoarrdetails[i].getOriginalFilename().lastIndexOf("."));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String path = simpleDateFormat.format(System.currentTimeMillis());
                    String name = path+"/"+System.currentTimeMillis()+"."+phototype;
                    boolean b = ftpOperation.uploadToFtp(photoarrdetails[i].getInputStream(),name,"/photo/computerGo/"+path);
                    if(!b){
                        return new ResultUtil().Error("400",photoarrdetails[i].getOriginalFilename()+"文件上传失败");
                    }
                    detailsphoto.append("http://39.96.68.53:70/computerGo/"+name+";");
                }catch (Exception e){
                    continue;
                }
            }
            repertory.setDetailsphoto(detailsphoto.toString());
             ftpOperation.closeConnect();
            */
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : photoarr){
                stringBuilder.append(str+";");
            }
            repertory.setPhoto(stringBuilder.toString());
            StringBuilder stringBuilder1 = new StringBuilder();
            for (String str : photoarrdetails){
                stringBuilder1.append(str+";");
            }
            repertory.setDetailsphoto(stringBuilder1.toString());

            repertoryService.insertRepertory(repertory);
            //添加商户库存记录
            UR ur = new UR();
            ur.setUid(uid);
            ur.setRid(repertory.getRid());
            urService.insertUR(ur);
            redisUtil.set(repertory.getRid().toString(),0);

            return new ResultUtil().Success(repertory.getRid());
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }


    @PostMapping("/addpackage")
    @ResponseBody
    @ApiOperation(value = "添加套餐",notes = "500报错 ")
    public ResultDTO addpackage(
            @ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true) Integer rid,
            @ApiParam(value = "套餐描述",required = true)@RequestParam(value = "packagemessage",required = true) String packagemessage,
            @ApiParam(value = "套餐价格",required = true)@RequestParam(value = "price",required = true) double price){
        try {
            List<RP> rpList = rpService.selectByRid(rid);
            double newprice = 0;
            for (RP newrp : rpList){
                Package npackage = packageService.selectByPid(newrp.getPid());
                newprice = npackage.getPrice() > price ? price : npackage.getPrice();
            }
            Repertory repertory = repertoryService.selectByRid(rid);
            repertory.setPrice(newprice);
            repertoryService.updateRepertory(repertory);

            Package newpackage = new Package();
            newpackage.setPackagemessage(packagemessage);
            newpackage.setPrice(price);
            packageService.insertPackage(newpackage);
            RP rp = new RP();
            rp.setPid(newpackage.getPid());
            rp.setRid(rid);
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

    @DeleteMapping("/deleteRepertory")
    @ResponseBody
    @ApiOperation(value = "删除库存",notes = "500报错")
    public ResultDTO deleteRepertory(@ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true)Integer rid){
        try {
            urService.deleteByRid(rid);
            rpService.deleteByRid(rid);
            redisUtil.del(rid.toString());
           /* Repertory repertory = repertoryService.selectByRid(rid);
            String[] photots = repertory.getPhoto().split(";");
            ftpOperation.connectToServer();
            for (String str : photots){
                String path = str.replaceAll("http://39.96.68.53:70","/photo");
                ftpOperation.delectFile(str.substring(path.lastIndexOf("/")),str.substring(0,path.lastIndexOf("/")));
            }
            ftpOperation.closeConnect();*/
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }


    @PutMapping("/changerepertory")
    @ResponseBody
    @ApiOperation(value = "修改库存",notes = "500报错 400 文件上传失败 文件先上传套餐信息")
    public ResultDTO changerepertory(
            @ApiParam(value = "库存类型 0电脑 1cpu 2主板 3显卡 4散热器 5内存 ",required = true)
            @RequestParam(value = "type",required = true) Integer type,
            @ApiParam(value = "库存id",required = false)@RequestParam(value = "rid",required = false) Integer rid,
            @ApiParam(value = "库存标题",required = false)@RequestParam(value = "title",required = false) String title,
            @ApiParam(value = "轮播照片",required = false)@RequestParam(value = "photoarr",required = false)String[] photoarr,
            @ApiParam(value = "详情照片",required = false)@RequestParam(value = "photoarrdetails",required = false)String[] photoarrdetails){
        try {
            Repertory repertory = repertoryService.selectByRid(rid);
            if (StringUtils.isNotBlank(title))repertory.setTitle(title);
            if (type != null)repertory.setType(type);
            if (photoarr!=null && photoarr.length!=0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : photoarr) {
                    stringBuilder.append(str + ";");
                }
                repertory.setPhoto(stringBuilder.toString());
            }
            if (photoarr!=null && photoarr.length!=0) {
                StringBuilder stringBuilder1 = new StringBuilder();
                for (String str : photoarrdetails){
                    stringBuilder1.append(str+";");
                }
                repertory.setDetailsphoto(stringBuilder1.toString());
            }
           /*
            ftpOperation.connectToServer();
            if (photoarr != null || photoarr.length != 0){
                String[] photots = repertory.getPhoto().split(";");
                for (String str : photots){
                    String path = str.replaceAll("http://39.96.68.53:70","/photo");
                    ftpOperation.delectFile(str.substring(path.lastIndexOf("/"))+1,str.substring(0,path.lastIndexOf("/")));
                }
                for (int i = photoarr.length ; i >0 ; i--){
                    try {
                        String phototype = photoarr[i].getOriginalFilename().substring(photoarr[i].getOriginalFilename().lastIndexOf("."));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String path = simpleDateFormat.format(System.currentTimeMillis());
                        String name = path+"/"+System.currentTimeMillis()+"."+phototype;
                        if(!ftpOperation.uploadToFtp(photoarr[i].getInputStream(),name,"/photo/computerGo/"+path)){
                            return new ResultUtil().Error("400",photoarr[i].getOriginalFilename()+"文件上传失败");
                        }
                        photo.append("http://39.96.68.53:70/computerGo/"+name+";");
                    }catch (Exception e){
                        continue;
                    }
                }
                repertory.setPhoto(photo.toString());
            }
                   ftpOperation.closeConnect();
            */
            repertoryService.changeByRid(repertory);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }


    @DeleteMapping("/deletepackage")
    @ResponseBody
    @ApiOperation(value = "删除套餐",notes = "500报错 ")
    public ResultDTO changepackage(
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true) Integer pid){
        try {
            rpService.deleteByPid(pid);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PutMapping("/changePackage")
    @ResponseBody
    @ApiOperation(value = "修改套餐",notes = "500报错 ")
    public ResultDTO changePackage(
            @ApiParam(value = "套餐id",required = true)@RequestParam(value = "pid",required = true) Integer pid,
            @ApiParam(value = "套餐描述",required = false)@RequestParam(value = "packagemessage",required = false) String packagemessage,
            @ApiParam(value = "套餐价格",required = false)@RequestParam(value = "price",required = false) double price){
        try {
            Package newpackage = new Package();
            if (StringUtils.isNotBlank(packagemessage))newpackage.setPackagemessage(packagemessage);
            if (new Double(price) != null)newpackage.setPrice(price);
            packageService.updatePackage(newpackage);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @GetMapping("/getRepertory")
    @ResponseBody
    @ApiOperation(value = "获取库存套餐",notes = "500报错")
    public ResultDTO getRepertory(
            @ApiParam(value = "库存id",required = true)@RequestParam(value = "rid",required = true) Integer rid){
        try {
            Repertory repertory = repertoryService.selectByRid(rid);
            List<RP> rpList = rpService.selectByRid(rid);
            RepertoryOneDTO repertoryOneDTO = new RepertoryOneDTO();
            repertoryOneDTO.SetRepertoryOneDTO(repertory);
            List<Package> packageList = new ArrayList<>();
            for (RP rp : rpList){
                try{
                    packageList.add(packageService.selectByPid(rp.getPid()));
                }catch (Exception e){
                    continue;
                }
            }
            repertoryOneDTO.setPackageList(packageList);
            return new ResultUtil().Success(repertoryOneDTO);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultUtil().Error("500",e.toString());
        }
    }
}
