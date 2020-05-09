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
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;


import java.util.ArrayList;
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


    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "登录",notes = "role 0普通用户 1 商户")
    public ResultDTO user_login(
           @ApiParam(value = "接收小程序发送的code",required = true)@RequestParam(value = "code", required = false) String code) {
        try {
            JSONObject SessionKeyOpenId = new WechatUtil().getSessionKeyOrOpenId(code);
            String openid = SessionKeyOpenId.getString("openid");
            User user = userService.selectByOpenid(openid);
            UserDTO userDto = new UserDTO();
            userDto.setJsonObject(SessionKeyOpenId);
            if (user == null) {
                user = new User();
                user.setOpenid(openid);
                userService.insertUser(user);
                userDto.setUserDto(user);
                userDto.setRole(0);
            } else {
                List<UI> uiList = uiService.selectByUid(user.getUid());
                Boolean b = true;
                userDto.setUserDto(user);
                for (UI ui : uiList){
                    Identity identity = identityService.selectByIid(ui.getIid());
                    if (!StringUtils.isEmpty(identity.getUidcard())){
                        b = false;
                        break;
                    }
                }
                userDto.setRole(0);
                if (!b){
                    userDto.setRole(1);
                }
            }
            return new ResultUtil().Success(userDto);
        }catch (Exception e){
            return new ResultUtil().Error("500",e.toString());
        }
    }

    @PostMapping("/usersignin")
    @ResponseBody
    @ApiOperation(value = "商户注册")
    public ResultDTO usersignin(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "姓名",required = true)@RequestParam(value = "uname",required = true)String uname,
            @ApiParam(value = "电话",required = true)@RequestParam(value = "uphone",required = true)String uphone,
            @ApiParam(value = "身份证",required = true)@RequestParam(value = "uidcard",required = true)String uidcard,
            @ApiParam(value = "详细地址",required = true)@RequestParam(value = "uaddress",required = true)String uaddress
    ){
        try {
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
