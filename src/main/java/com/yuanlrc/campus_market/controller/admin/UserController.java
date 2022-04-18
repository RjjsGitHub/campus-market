package com.yuanlrc.campus_market.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.entity.admin.Menu;
import com.yuanlrc.campus_market.entity.admin.Role;
import com.yuanlrc.campus_market.entity.admin.User;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.service.admin.MenuService;
import com.yuanlrc.campus_market.service.admin.OperaterLogService;
import com.yuanlrc.campus_market.service.admin.RoleService;
import com.yuanlrc.campus_market.service.admin.UserService;
import com.yuanlrc.campus_market.util.MenuUtil;
import com.yuanlrc.campus_market.util.PassWordUtil;
import com.yuanlrc.campus_market.util.ValidateEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/admin/user")
@Controller
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 学生管理列表页面
     * @param
     * @param pageBean
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    public String list(User user,PageBean<User> pageBean,Model model){
        model.addAttribute("title", "用户列表");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("pageBean", userService.findList(user,pageBean));
        return "admin/user/list";
    }



    /**
     * 用户添加页面
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("roles", roleService.findAll());
        return "admin/user/add";
    }

    /**
     * 用户添加表单提交处理
     * @param user
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(User user ,@RequestParam(name="password",required=true)String password){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(user);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        String eptPassword = PassWordUtil.getEptPassword(password, salt);
        user.setPassword(eptPassword);
        user.setSalt(salt);
        if(userService.save(user) == null){
            return Result.error(CodeMsg.ADMIN_ROLE_ADD_ERROR);
        }
        log.info("添加角色【"+user+"】");
        operaterLogService.add("添加角色【"+user.getUsername()+"】");
        return Result.success(true);
    }

    /**
     * 用户编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String edit(@RequestParam(name="id",required=true)Long id,Model model){
        model.addAttribute("user",userService.find(id));
        model.addAttribute("roles", roleService.findAll());
        return "admin/user/edit";
    }

    /**
     * 角色修改表单提交处理
     * @param
     * @param user
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(User user ,@RequestParam(name="password",required=true)String password){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(user);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        User existUser = userService.find(user.getId());
        if(existUser == null){
            return Result.error(CodeMsg.ADMIN_ROLE_NO_EXIST);
        }

        String newsalt = UUID.randomUUID().toString().toUpperCase();
        String newPassword = PassWordUtil.getEptPassword(password, newsalt);

        existUser.setHeadPic(user.getHeadPic());
        existUser.setUsername(user.getUsername());
        existUser.setPassword(newPassword);
        existUser.setSalt(newsalt);
        existUser.setRole(user.getRole());
        existUser.setMobile(user.getMobile());
        existUser.setEmail(user.getEmail());
        existUser.setSex(user.getSex());
        existUser.setStatus(user.getStatus());
        if(userService.save(existUser) == null){
            return Result.error(CodeMsg.ADMIN_ROLE_EDIT_ERROR);
        }
        log.info("编辑角色【"+user+"】");
        operaterLogService.add("编辑角色【"+user.getUsername()+"】");
        return Result.success(true);
    }

    /**
     * 删除角色
     * @param
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            userService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_STUDENT_DELETE_ERROR);
        }
        return Result.success(true);
    }

}
