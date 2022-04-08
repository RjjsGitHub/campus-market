package com.yuanlrc.campus_market.entity.common;

import com.yuanlrc.campus_market.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.SQLOutput;

@Entity
@Table(name="ylrc_mywish")
@EntityListeners(AuditingEntityListener.class)
public class Wish extends BaseEntity{

    public static final int GOODS_STATUS_UP = 1;//上架
    public static final int GOODS_STATUS_DOWN = 2;//下架
    public static final int GOODS_STATUS_SOLD = 3;//已售出

    private static final long serialVersionUID = 23131231L;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;//所属学生

    @ManyToOne
    @JoinColumn(name="goods_id")
    private Goods goods;

    @ValidateEntity(required=true)
    @Column(name="login_student")
    private Long loginStudent;

    public Long getLoginStudent() {
        return loginStudent;
    }

    public void setLoginStudent(Long loginStudent) {
        this.loginStudent = loginStudent;
    }

    @ValidateEntity(required=true,requiredLeng=true,minLength=1,maxLength=30,errorRequiredMsg="物品名称不能为空!",errorMinLengthMsg="物品名称长度需大于1!",errorMaxLengthMsg="物品名称长度不能大于30!")
    @Column(name="name",nullable=false,length=32)
    private String name;//物品名称

    @ValidateEntity(required=true,errorRequiredMsg="请填写出售价格",requiredMinValue=true,errorMinValueMsg="出售价格不能小于0")
    @Column(name="sell_price",nullable=false,length=8)
    private Float sellPrice;//出售价格

    @ValidateEntity(required=true,errorRequiredMsg="请上传图片")
    @Column(name="photo",nullable=false,length=128)
    private String photo;//物品图片

    @Column(name="status",nullable=false,length=1)
    private int status = GOODS_STATUS_UP;//物品状态，默认上架

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Float sellPrice) {
        this.sellPrice = sellPrice;
    }
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
