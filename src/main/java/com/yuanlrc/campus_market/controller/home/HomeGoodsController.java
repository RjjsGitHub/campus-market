package com.yuanlrc.campus_market.controller.home;

import java.util.*;

import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.constant.SessionConstant;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.entity.common.Wish;
import com.yuanlrc.campus_market.service.common.*;
import com.yuanlrc.campus_market.util.SessionUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.entity.common.Goods;
import com.yuanlrc.campus_market.entity.common.GoodsCategory;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 前台物品管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/home/goods")
@Controller
public class HomeGoodsController {

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private MyWishServer myWishServer;

	/**
	 * 秒杀
	 * @param model
	 * @param goodsid
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value = "/kill")
	public String KillProduct(
			Model model ,
			@RequestParam(name="goodsid", required=true)Long goodsid ,
			@RequestParam(name="studentid",required=true)Long studentid){

		Student student = studentService.findById(studentid);
		Goods goods = goodsService.findById(goodsid);

		model.addAttribute("killgoods",goods);

		Goods killProductgoods = goodsService.killProduct(goods, student);
		if (killProductgoods != null){
			return "success";
		}
		return "fail";
	}


	/**
	 * 物品详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String detail(@RequestParam(name="id",required=true)Long id,Model model){
		Goods goods = goodsService.findById(id);
		Student seller_student = goods.getStudent();
		if(goods == null){
			model.addAttribute("msg", "物品不存在！");
			return "error/runtime_error";
		}
		model.addAttribute("goods", goods);
		model.addAttribute("commentList", commentService.findByGoods(goods));
		model.addAttribute("seller_student",seller_student);

		//更新商品浏览量
		goods.setViewNumber(goods.getViewNumber() + 1);
		goodsService.save(goods);
		return "home/goods/detail";
	}


	/**
	 * 根据商品分类搜索商品信息
	 * @param cid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@RequestParam(name="cid",required=true)Long cid,PageBean<Goods> pageBean,Model model){
		GoodsCategory goodsCategory = goodsCategoryService.findById(cid);
		if(goodsCategory == null){
			model.addAttribute("msg", "物品分类不存在！");
			return "error/runtime_error";
		}
		//接下来分两步来考虑，分类是否是二级分类。
		List<Long> ids = new ArrayList<Long>();
		ids.add(goodsCategory.getId());
		if(goodsCategory.getParent() == null){
			//选中的是顶级分类，此时需要获取该顶级分类下的所有子分类
			List<GoodsCategory> findChildren = goodsCategoryService.findChildren(goodsCategory);
			for(GoodsCategory gc : findChildren){
				ids.add(gc.getId());
			}
		}
		model.addAttribute("pageBean", goodsService.findlist(ids, pageBean));
		model.addAttribute("gc",goodsCategory);
		return "home/goods/list";
	}
	
	/**
	 * 获取已出售的商品总数
	 * @return
	 */
	@RequestMapping(value="/get_sold_total",method=RequestMethod.POST)
	@ResponseBody
	public Result<Long> getSoldTotal(){
		return Result.success(goodsService.getSoldTotalCount());
	}

	/**
	 * 我的愿望清单
	 * @param id
	 *
	 * @return
	 */
	@RequestMapping(value="/add_to_wish")
	@ResponseBody
	public Result<Boolean> addToWish(@RequestParam(name="id",required=true)Long id){
		try {
			Student loginedStudent = (Student) SessionUtil.get(SessionConstant.SESSION_STUDENT_LOGIN_KEY);
			Goods goods = goodsService.findById(id);
			Wish wish = myWishServer.saveToWish(goods, loginedStudent);
			if (myWishServer.isExit(goods.getName(),loginedStudent.getId()) != goods.getId()) {
				myWishServer.save(wish);
				return Result.success(true);
			}else {
				return Result.error(CodeMsg.HOME_STUDENT_WISH_EXIXT);
			}
		} catch (Exception e) {
			return Result.error(CodeMsg.HOME_STUDENT_WISH_ADD_ERROR);
		}
	}
}
