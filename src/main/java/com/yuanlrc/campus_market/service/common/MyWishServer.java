package com.yuanlrc.campus_market.service.common;

import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.dao.common.GoodsDao;
import com.yuanlrc.campus_market.dao.common.MyWishDao;
import com.yuanlrc.campus_market.dao.common.StudentDao;
import com.yuanlrc.campus_market.entity.common.Goods;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.entity.common.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
public class MyWishServer {

    @Autowired
    private StudentDao studentDao;

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
     * 根据id查询
     * @param
     * @return
     */
    public List<Goods> findByStuId(Student student){
        return goodsDao.findByStudent(student);
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
     * 根据物品名称模糊搜索
     * @param name
     * @return
     */
    public List<Goods> findListByName(String name){
        return goodsDao.findListByName(name);
    }

    /**
     * 根据学生查找物品
     * @param student
     * @return
     */
    public List<Goods> findByStudent(Student student){
        return goodsDao.findByStudent(student);
    }

    /**
     * 愿望清单删除
     * @param id
     */
    public void delete(Long id){
        myWishDao.deleteById(id);
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
     * @param wish
     * @return
     */
    public PageBean<Wish> findlist(PageBean<Wish> pageBean, Wish wish){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("login_student", ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher = exampleMatcher.withIgnorePaths("status");
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
