package com.yuanlrc.campus_market.controller.home;

import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.constant.SessionConstant;
import com.yuanlrc.campus_market.entity.common.Goods;
import com.yuanlrc.campus_market.entity.common.KillProduct;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.service.common.KillProductServer;
import com.yuanlrc.campus_market.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/home/kill")
@Controller
public class HomeKillController {

    @Autowired
    private KillProductServer killProductServer;

    @GetMapping("/list")
    public String list(KillProduct killProduct, PageBean<KillProduct> pageBean,Model model){
        model.addAttribute("killProducts",killProductServer.findlist(pageBean, killProduct));
        return "/home/kill/list";
    }

    @PostMapping("/kill_product")
    @ResponseBody
    public Result<Boolean> kill(@RequestParam(name = "id",required = false)Long killProductId){
        Student loginedStudent = (Student) SessionUtil.get(SessionConstant.SESSION_STUDENT_LOGIN_KEY);
        KillProduct kill = killProductServer.findById(killProductId);
        if (!kill.getStudentName().equals("暂无")) {
            if (!kill.getStudentName().equals(loginedStudent.getNickname())){
                return Result.error(CodeMsg.HOEM_KILL_FAIL);
            }else {
                return Result.error(CodeMsg.HOEM_KILL_SUCCESS);
            }
        }else {
            KillProduct killProduct = killProductServer.killProduct(killProductId,loginedStudent.getNickname());
            if (killProduct.getState().equals("成功")){
                return Result.success(true);
            }
        }
        return Result.error(CodeMsg.HOEM_KILL_FAIL);
    }

}
