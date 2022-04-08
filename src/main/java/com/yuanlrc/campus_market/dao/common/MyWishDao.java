package com.yuanlrc.campus_market.dao.common;

import com.yuanlrc.campus_market.entity.common.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MyWishDao  extends JpaRepository<Wish, Long>, JpaSpecificationExecutor<Wish> {

    /**
     * 根据学生查询
     * @param
     * @return
     */
    @Query(value = "SELECT w from Wish w where loginStudent = :loginStudentId")
    List<Wish> findByLoginStudent(@Param("loginStudentId")Long loginStudentId);
}
