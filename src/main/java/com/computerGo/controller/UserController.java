package com.computerGo.controller;


import com.alibaba.fastjson.JSONObject;
import com.computerGo.DTO.UserDTO;
import com.computerGo.base.ResultUtil;
import com.computerGo.base.dto.ResultDTO;
import com.computerGo.base.utils.WechatUtil;
import com.computerGo.pojo.Identity;
import com.computerGo.pojo.UI;
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

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 微信用户登录详情
     */
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
                UI ui = uiService.selectByUid(user.getUid());
                Identity identity = identityService.selectByIid(ui.getIid());
                userDto.setUserDto(user);
                if (StringUtils.isEmpty(identity.getUidcard())){
                    userDto.setRole(0);
                }else{
                    userDto.setRole(1);
                }
            }
            request.getSession().setAttribute("uid",userDto.getUid());
            return new ResultUtil().Success(userDto);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

}
