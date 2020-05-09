package com.computerGo.controller;

import com.computerGo.DTO.FileDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.ftp.FtpOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;

/**
 * @ClassName FileController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/5/8 13:07
 **/
@Controller
@RequestMapping("/FileController")
@Api(value = "FileController")
public class FileController {

    @Autowired
    private FtpOperation ftpOperation;

    /*@PostMapping("/upfile")
    @ResponseBody
    @ApiOperation(value = "上传套餐照片",notes = "400 上传失败")
    public ResultDTO upfile(
            @ApiParam(value = "轮播照片",required = true)@RequestParam(value = "photoarr",required = true) MultipartFile[] photoarr,
            @ApiParam(value = "详情照片",required = true)@RequestParam(value = "photoarrdetails",required = true)MultipartFile[] photoarrdetails){
        try {
            ftpOperation.connectToServer();
            String[] newphotoarr = new String[photoarr.length];
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
                    newphotoarr[i] = "http://39.96.68.53:70/computerGo/"+name+";";
                }catch (Exception e){
                    continue;
                }
            }
            String[] detailsphoto = new String[photoarrdetails.length];
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
                    detailsphoto[i] = "http://39.96.68.53:70/computerGo/"+name+";";
                }catch (Exception e){
                    continue;
                }
            }
            ftpOperation.closeConnect();
            return new ResultUtil().Success(new FileDTO(newphotoarr,detailsphoto));
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }*/

    @PostMapping("/upfile")
    @ResponseBody
    @ApiOperation(value = "上传照片",notes = "400 上传失败")
    public ResultDTO upfile(
            @ApiParam(value = "照片",required = true)@RequestParam(value = "photo",required = true) MultipartFile photo){
        try {
            String phototype = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String path = simpleDateFormat.format(System.currentTimeMillis());
            String name = System.currentTimeMillis()+phototype;
            boolean b = ftpOperation.uploadToFtp(photo.getInputStream(),name,"/photo/computerGo/"+path);
            if(!b){
                return new ResultUtil().Error("400",photo.getOriginalFilename()+"文件上传失败");
            }
            return new ResultUtil().Success("http://39.96.68.53:70/computerGo/"+path+"/"+name);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }
}
