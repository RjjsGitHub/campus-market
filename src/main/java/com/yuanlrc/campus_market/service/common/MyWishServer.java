package com.yuanlrc.campus_market.service.common;

import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.constant.SessionConstant;
import com.yuanlrc.campus_market.dao.common.GoodsDao;
import com.yuanlrc.campus_market.dao.common.MyWishDao;
import com.yuanlrc.campus_market.dao.common.StudentDao;
import com.yuanlrc.campus_market.entity.common.Goods;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.entity.common.Wish;
import com.yuanlrc.campus_market.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
public class MyWishServer {


    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private MyWishDao myWishDao;

    /**
     * 愿望清单添加/编辑，当id不为空时，则编辑
     * @param
     * @return
     */
    public Wish save(Wish wish){
        return myWishDao.save(wish);
    }



    /**
     * 获取所有的物品
     * @return
     */
    public List<Wish> findAll(){
        return myWishDao.findAll();
    }


    /**
     * 获取指定状态的物品总数
     * @param status
     * @return
     */
    public Long getTotalCount(Integer status){
        return goodsDao.getTotalCount(status);
    }

    /**
     * 获取已出售的商品总数
     * @return
     */
    public Long getSoldTotalCount(){
        return getTotalCount(Goods.GOODS_STATUS_SOLD);
    }


    /**
     * 创建对应的wish对象 用户 添加至 数据库
     * @param goods
     * @param student
     * @return
     */
    public Wish saveToWish(Goods goods ,Student student){
        Wish wish = new Wish();
        wish.setLoginStudent(student.getId());
        wish.setPhoto(goods.getPhoto());
        wish.setGoods(goods);
        wish.setStudent(goods.getStudent());
        wish.setSellPrice(goods.getSellPrice());
        wish.setCreateTime(goods.getCreateTime());
        wish.setUpdateTime(goods.getUpdateTime());
        wish.setStatus(goods.getStatus());
        wish.setName(goods.getName());
        return wish;
    }



    /**
     * 愿望清单删除
     * @param id
     */
    public void delete(Long id){
        myWishDao.deleteById(id);
    }

    public Long isExit(String name,Long id){
       return myWishDao.existsByGoodsName(name,id);
    }

    /**
     * 获取愿望总数
     * @return
     */
    public long total(){
        return myWishDao.count();
    }

    /**
     * 搜索清单列表
     * @param pageBean
     * @param
     * @return
     */
    public PageBean<Wish> findlist(PageBean<Wish> pageBean,Wish wish){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("login_student", ExampleMatcher.GenericPropertyMatchers.contains())
                                       .withIgnorePaths("status");
        Example<Wish> example = Example.of(wish, exampleMatcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(), sort);
        Page<Wish> findAll = myWishDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }



}
