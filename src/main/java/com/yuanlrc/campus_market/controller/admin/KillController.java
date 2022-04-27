package com.yuanlrc.campus_market.controller.admin;

import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.entity.admin.Menu;
import com.yuanlrc.campus_market.entity.common.Goods;
import com.yuanlrc.campus_market.entity.common.GoodsCategory;
import com.yuanlrc.campus_market.entity.common.KillProduct;
import com.yuanlrc.campus_market.service.admin.MenuService;
import com.yuanlrc.campus_market.service.admin.OperaterLogService;
import com.yuanlrc.campus_market.service.common.GoodsCategoryService;
import com.yuanlrc.campus_market.service.common.GoodsService;
import com.yuanlrc.campus_market.service.common.KillProductServer;
import com.yuanlrc.campus_market.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/admin/kill")
@Controller
public class KillController {

    @Autowired
    private KillProductServer killProductServer;

    @Autowired
    private GoodsService goodsService;

    /**
     * 物品分类管理列表页面
     * @param
     * @param pageBean
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    public String list(KillProduct killProduct, PageBean<KillProduct> pageBean, Model model){
        model.addAttribute("title", "秒杀发布管理");
        model.addAttribute("name", killProduct.getGoodsName());
        model.addAttribute("pageBean", killProductServer.findlist(pageBean, killProduct));
        return "admin/kill/list";
    }

    /**
     * 删除角色
     * @param
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method= RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            String goodsName = killProductServer.findById(id).getGoodsName();
            Goods goods = goodsService.findByName(goodsName);
            goods.setStatus(Goods.GOODS_STATUS_UP);
            goodsService.save(goods);
            killProductServer.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_KILL_DELETE_ERROR);
        }
        return Result.success(true);
    }
}
