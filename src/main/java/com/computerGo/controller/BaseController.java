package com.computerGo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName BaseController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/15 23:54
 **/
@Controller
public class BaseController {
   /* @RequestMapping("/{page_url}")
    public String page(@PathVariable String page_url){
        return "/html/"+page_url;
    }
    @RequestMapping("/")
    public String index(){
        return "forward:html/index.html";
    }*/
}
