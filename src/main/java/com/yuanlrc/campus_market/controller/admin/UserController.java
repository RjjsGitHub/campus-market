package com.yuanlrc.campus_market.controller.admin;

import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.entity.admin.Menu;
import com.yuanlrc.campus_market.entity.admin.Role;
import com.yuanlrc.campus_market.entity.admin.User;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.service.admin.MenuService;
import com.yuanlrc.campus_market.service.admin.OperaterLogService;
import com.yuanlrc.campus_market.service.admin.UserService;
import com.yuanlrc.campus_market.util.MenuUtil;
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

import java.util.List;

@RequestMapping("/admin/user")
@Controller
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private MenuService menuService;
    @RequestMapping(value="/list")
    public String list(User user, PageBean<User> pageBean, Model model){
        model.addAttribute("title", "用户列表");
        model.addAttribute("name", user.getUsername());
        model.addAttribute("pageBean", userService.findList(user, pageBean));
        return "admin/user/list";
    }



    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model){

        return "admin/user/add";
    }

    @RequestMapping(value="/update_status",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> upDown(@RequestParam(name="username",required=true)String username ,@RequestParam(name="status",required=true)Integer status){
        User user = userService.findByUsername(username);
        if(user == null){
            return Result.error(CodeMsg.ADMIN_GOODS_NO_EXIST);
        }
        if(user.getStatus() == status){
            return Result.error(CodeMsg.ADMIN_STUDENT_STATUS_NO_CHANGE);
        }
        if(status != Student.STUDENT_STATUS_ENABLE && status != Student.STUDENT_STATUS_UNABLE){
            return Result.error(CodeMsg.ADMIN_STUDENT_STATUS_ERROR);
        }
        user.setStatus(status);
        //进行更新数据库
        if(userService.save(user) ==null){
            return Result.error(CodeMsg.ADMIN_STUDENT_EDIT_ERROR);
        }
        return Result.success(true);
    }


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
