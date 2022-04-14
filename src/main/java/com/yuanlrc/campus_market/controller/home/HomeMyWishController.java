package com.yuanlrc.campus_market.controller.home;

import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.constant.SessionConstant;
import com.yuanlrc.campus_market.dao.common.GoodsCategoryDao;
import com.yuanlrc.campus_market.dao.common.MyWishDao;
import com.yuanlrc.campus_market.entity.common.*;
import com.yuanlrc.campus_market.service.common.*;
import com.yuanlrc.campus_market.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/home/my_wish")
@Controller
public class HomeMyWishController {



    @Autowired
    private MyWishServer myWishServer;


    @RequestMapping(value="/list")
    public String list(Wish wish,PageBean<Wish> pageBean,Model model){
        Student loginedStudent = (Student) SessionUtil.get(SessionConstant.SESSION_STUDENT_LOGIN_KEY);
        wish.setLoginStudent(loginedStudent.getId());

        model.addAttribute("title", "愿望列表");
        model.addAttribute("pageBean", myWishServer.findlist(pageBean,wish));
        return "home/my_wish/list";
    }

    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            myWishServer.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.HOME_STUDENT_WISH_DELETE_ERROR);
        }
        return Result.success(true);
    }
}