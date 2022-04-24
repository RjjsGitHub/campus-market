package com.yuanlrc.campus_market.entity.common;

import com.yuanlrc.campus_market.annotion.ValidateEntity;
import com.yuanlrc.campus_market.entity.common.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    @JoinColumn(name = "student_id")
    private Student student;

    @ValidateEntity(required=false)
    @Column(name="goods_name",nullable=false)
    private String goodsName;

    @ValidateEntity(required=false)
    @Column(name="state")
    private int state;

    @ValidateEntity(required=false)
    @Column(name="start_time",nullable=false)
    private Date startTime;//创建时间

    @ValidateEntity(required=false)
    @Column(name="end_time",nullable=false)
    private Date endTime;//创建时间

    @ValidateEntity(required=false)
    @Column(name="goods_photo",nullable=false)
    private String goodsPhoto;//创建时间

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
