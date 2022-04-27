package com.yuanlrc.campus_market.controller.admin;

import java.util.Date;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yuanlrc.campus_market.entity.common.KillProduct;
import com.yuanlrc.campus_market.service.common.KillProductServer;
import com.yuanlrc.campus_market.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yuanlrc.campus_market.bean.CodeMsg;
import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.entity.common.Goods;
import com.yuanlrc.campus_market.entity.common.GoodsCategory;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.service.common.GoodsCategoryService;
import com.yuanlrc.campus_market.service.common.GoodsService;
import com.yuanlrc.campus_market.service.common.StudentService;

/**
 * 后台物品管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/goods")
@Controller
public class GoodsController {

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private KillProductServer killProductServer;
	
	/**
	 * 物品管理列表页面
	 * @param
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Goods goods,PageBean<Goods> pageBean,Model model){
		if(goods.getStudent() != null && goods.getStudent().getSn() != null){
			Student student = studentService.findBySn(goods.getStudent().getSn());
			if(student != null){
				goods.setStudent(student);
			}
		}
		if(goods.getGoodsCategory() != null && goods.getGoodsCategory().getName() != null){
			List<GoodsCategory> goodsCategorys = goodsCategoryService.findByName(goods.getGoodsCategory().getName());
			if(goodsCategorys != null && goodsCategorys.size() > 0){
				goods.setGoodsCategory(goodsCategorys.get(0));
			}
		}
		goods.setStatus(-1);
		model.addAttribute("title", "物品列表");
		model.addAttribute("name", goods.getName());
		model.addAttribute("goodsCategoryName", goods.getGoodsCategory() == null ? null : goods.getGoodsCategory().getName());
		model.addAttribute("sn", goods.getStudent() == null ? null : goods.getStudent().getSn());
		model.addAttribute("pageBean", goodsService.findlist(pageBean, goods));
		return "admin/goods/list";
	}
	
	

	/**
	 * 物品上架
	 * @param id,status
	 * @return
	 */
	@RequestMapping(value="/up_down",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> upDown(@RequestParam(name="id",required=true)Long id ,@RequestParam(name="status",required=true)Integer status){
		Goods goods = goodsService.findById(id);
		if(goods == null){
			return Result.error(CodeMsg.ADMIN_GOODS_NO_EXIST);
		}
		if(goods.getStatus() == status){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_NO_CHANGE);
		}
		if(status != Goods.GOODS_STATUS_UP && status != Goods.GOODS_STATUS_DOWN){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_ERROR);
		}
		if(goods.getStatus() == Goods.GOODS_STATUS_SOLD){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_UNABLE);
		}
		if(goods.getStatus() == Goods.GOODS_STATUS_KILL){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_KILL);
		}
		goods.setStatus(status);
		//进行更新数据库
		if(goodsService.save(goods) ==null){
			return Result.error(CodeMsg.ADMIN_GOODS_EDIT_ERROR);
		}
		return Result.success(true);
	}
	
	/**
	 * 物品推荐或取消推荐
	 * @param id
	 * @param recommend
	 * @return
	 */
	@RequestMapping(value="/recommend",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> recommend(@RequestParam(name="id",required=true)Long id ,@RequestParam(name="recommend",required=true)Integer recommend){
		Goods goods = goodsService.findById(id);
		if(goods == null){
			return Result.error(CodeMsg.ADMIN_GOODS_NO_EXIST);
		}
		if(goods.getRecommend() == recommend){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_NO_CHANGE);
		}
		if(recommend != Goods.GOODS_RECOMMEND_OFF && recommend != Goods.GOODS_RECOMMEND_ON){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_ERROR);
		}
		if(goods.getStatus() == Goods.GOODS_STATUS_SOLD){
			return Result.error(CodeMsg.ADMIN_GOODS_STATUS_UNABLE);
		}
		goods.setRecommend(recommend);;
		//进行更新数据库
		if(goodsService.save(goods) ==null){
			return Result.error(CodeMsg.ADMIN_GOODS_EDIT_ERROR);
		}
		return Result.success(true);
	}
	
	
	
	/**
	 * 物品删除操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
		try {
			goodsService.delete(id);
		} catch (Exception e) {
			return Result.error(CodeMsg.ADMIN_GOODS_DELETE_ERROR);
		}
		return Result.success(true);
	}

	/**
	 * 秒杀添加页面
	 * @param
	 * @return
	 */
	@RequestMapping(value="/add_kill",method= RequestMethod.GET)
	public String add_kill(Model model,@RequestParam(name="id",required=true)Long id){
		Goods goods = goodsService.findById(id);

		model.addAttribute("goods",goods);
		return "admin/goods/add_kill";
	}

	/**
	 * 秒杀添加页面
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/add_kill" ,method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> add_kill(KillProduct killProduct) {
		CodeMsg validate = ValidateEntityUtil.validate(killProduct);
		if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
			return Result.error(validate);
		}
		Goods goods = goodsService.findById(killProduct.getGoodsId().getId());

		//商品已出售或已被秒杀
		if (goods.getStatus() == Goods.GOODS_STATUS_SOLD){
			return Result.error(CodeMsg.ADMIN_KILL_ISSELL_ERROR);
		}

		killProduct.setGoodsName(goods.getName());
		killProduct.setStudentName("暂无");
		killProduct.setState(killProductServer.killState(killProduct.getStartTime(), killProduct.getEndTime()));
		killProduct.setGoodsPhoto(goods.getPhoto());
		killProduct.setStartTime(killProduct.getStartTime());
		killProduct.setEndTime(killProduct.getEndTime());
		goods.setStatus(Goods.GOODS_STATUS_KILL);

		if(killProductServer.save(killProduct) == null){
			return Result.error(CodeMsg.ADMIN_KILL_ADD_ERROR);
		}
		return Result.success(true);
	}
}
