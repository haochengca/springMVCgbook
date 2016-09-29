package com.licyun.controller;

import com.licyun.model.User;
import com.licyun.service.UserService;
import com.licyun.util.RegistValid;
import com.licyun.util.UpdateValid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by 李呈云
 * Description:
 * 2016/9/27.
 */
@Controller
public class UserController {

    private UserService userService;
    private UpdateValid updateValid;
    private RegistValid registValid;

    //首页
    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String gbook(){
        return "index";
    }

    //注册
    @RequestMapping(value = {"/user/register"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String register(Model model){
        model.addAttribute(new User());
        return "user/register";
    }
    @RequestMapping(value = {"/user/register"}, method = {RequestMethod.POST, RequestMethod.HEAD})
    public String register(@Valid  User user,BindingResult result){
        registValid = new RegistValid();
        userService = new UserService();
        registValid.validate(user, result);
        if(result.hasErrors()){
            return "user/register";
        }
        userService.saveUser(user);
        return "user/usermanager";
    }

    //登录
    @RequestMapping(value = "/user/login", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String login(){
        return "user/login";
    }
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.HEAD})
    public String login(@Valid User user, HttpSession session){
        return "user/login";
    }

    //编辑
    @RequestMapping(value = "/user/edit-{id}", method = {RequestMethod.GET, RequestMethod.HEAD})
         public String edit(@PathVariable Long id, Model model){
        userService = new UserService();
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit-{id}", method = {RequestMethod.POST, RequestMethod.HEAD})
    public String edit(@Valid User user,@Valid Long id, BindingResult result){
        updateValid = new UpdateValid();
        userService = new UserService();
        updateValid.validate(user, result);
        if(result.hasErrors()){
            return "user/edit";
        }
        userService.updateUser(id, user);
        return "user/usermanager";
    }
}