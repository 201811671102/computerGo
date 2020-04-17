package com.computerGo.controller;


import com.alibaba.fastjson.JSONObject;
import com.computerGo.DTO.UserDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.base.utils.WechatUtil;
import com.computerGo.pojo.Identity;
import com.computerGo.pojo.UI;
import com.computerGo.pojo.UR;
import com.computerGo.pojo.User;
import com.computerGo.service.IdentityService;
import com.computerGo.service.UIService;
import com.computerGo.service.UserService;
import io.swagger.annotations.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 14:26
 **/
@Controller
@RequestMapping("/UserController")
@Api(value = "UserController")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UIService uiService;
    @Autowired
    private IdentityService identityService;


    @GetMapping("/message1")
    @ResponseBody
    @ApiOperation(value = "message1")
    public ResultDTO message1(HttpServletRequest request){
        request.getSession().setAttribute("uid","1");
        return new ResultUtil().Success(request.getSession().getAttribute("uid"));
    }
    @GetMapping("/message2")
    @ResponseBody
    @ApiOperation(value = "message2")
    public ResultDTO message2(HttpServletRequest request){
        request.getSession().setAttribute("uid","2");
        return new ResultUtil().Success(request.getSession().getAttribute("uid"));
    }

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "登录",notes = "role 0普通用户 1 商户")
    public ResultDTO user_login(
            HttpServletRequest request,
            @ApiParam(value = "code",required = true)@RequestParam(value = "code", required = false) String code,
            @ApiParam(value = "rawData",required = true)@RequestParam(value = "rawData", required = false) String rawData,
            @ApiParam(value = "signature",required = true)@RequestParam(value = "", required = false) String signature) {
        try {
            // 用户非敏感信息：rawData
            // 签名：signature
            //  JSONObject rawDataJson = JSON.parseObject(rawData);
            // 1.接收小程序发送的code
            // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
            JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
            // 3.接收微信接口服务 获取返回的参数
            String openid = SessionKeyOpenId.getString("openid");
            String sessionKey = SessionKeyOpenId.getString("session_key");
            // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
            String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
            if (!signature.equals(signature2)) {
                return new ResultUtil().Error("500","签名校验失败");
            }
            User user = userService.selectByOpenid(openid);
            UserDTO userDto = new UserDTO();
            if (user == null) {
                user.setOpenid(openid);
                userService.insertUser(user);
                userDto.setUserDto(user);
                userDto.setRole(0);
            } else {
                List<UI> uiList = uiService.selectByUid(user.getUid());
                List<Identity> identities = new ArrayList<>();
                Boolean b = true;
                userDto.setUserDto(user);
                for (UI ui : uiList){
                    Identity identity = identityService.selectByIid(ui.getIid());
                    if (!StringUtils.isEmpty(identity.getUidcard())){
                        b = false;
                        continue;
                    }
                    identities.add(identity);
                }
                userDto.setRole(0);
                if (!b){
                    userDto.setRole(1);
                }
                userDto.setIdentityList(identities);
            }
            request.getSession().setAttribute("uid",userDto.getUid());
            return new ResultUtil().Success(userDto);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PostMapping("/usersignin")
    @ResponseBody
    @ApiOperation(value = "商户注册")
    public ResultDTO usersignin(
            HttpServletRequest request,
            @ApiParam(value = "姓名",required = true)@RequestParam(value = "uname",required = true)String uname,
            @ApiParam(value = "电话",required = true)@RequestParam(value = "uphone",required = true)String uphone,
            @ApiParam(value = "身份证",required = true)@RequestParam(value = "uidcard",required = true)String uidcard,
            @ApiParam(value = "详细地址",required = true)@RequestParam(value = "uaddress",required = true)String uaddress
    ){
        try {
            Integer uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            Identity identity = new Identity();
            identity.setUname(uname);
            identity.setUphone(uphone);
            identity.setUaddress(uaddress);
            identity.setUidcard(new BASE64Decoder().decodeBuffer(uidcard).toString());
            identityService.insertIdentity(identity);
            UI ui = new UI();
            ui.setUid(uid);
            ui.setIid(identity.getIid());
            uiService.insertUI(ui);
            return new ResultUtil().Success();
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

}
