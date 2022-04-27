package com.yuanlrc.campus_market.entity.common;

import com.yuanlrc.campus_market.annotion.ValidateEntity;
import com.yuanlrc.campus_market.entity.common.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * 后台用户实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="ylrc_kill_product")
@EntityListeners(AuditingEntityListener.class)
public class KillProduct extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="goods_id")
    private Goods goodsId;


    @ValidateEntity(required=false)
    @Column(name="student_name",nullable=false)
    private String  studentName;

    @ValidateEntity(required=false)
    @Column(name="goods_name",nullable=false)
    private String goodsName;

    @ValidateEntity(required=false)
    @Column(name="state")
    private String state;

    @ValidateEntity(required=false)
    @Column(name="start_time",nullable=false)
    private String startTime;//创建时间

    @ValidateEntity(required=false)
    @Column(name="end_time",nullable=false)
    private String endTime;//创建时间

    @ValidateEntity(required=false)
    @Column(name="goods_photo",nullable=false)
    private String goodsPhoto;//创建时间

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public Goods getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Goods goodsId) {
        this.goodsId = goodsId;
    }
}
