package com.yuanlrc.campus_market.controller.admin;

import com.yuanlrc.campus_market.entity.admin.Menu;
import com.yuanlrc.campus_market.service.admin.MenuService;
import com.yuanlrc.campus_market.service.admin.OperaterLogService;
import com.yuanlrc.campus_market.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin/kill")
@Controller
public class KillController {
    @Autowired
    private MenuService menuService;


    /**
     * 菜单列表展示页面
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    public String list(Model model){
        List<Menu> findAll = menuService.findAll();
        model.addAttribute("title","菜单列表");
        model.addAttribute("topMenus", MenuUtil.getTopMenus(findAll));
        model.addAttribute("secondMenus",MenuUtil.getSecondMenus(findAll));
        model.addAttribute("thirdMenus",MenuUtil.getThirdMenus(findAll));
        return "admin/kill/list";
    }
}
