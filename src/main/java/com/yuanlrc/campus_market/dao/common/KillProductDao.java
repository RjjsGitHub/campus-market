package com.yuanlrc.campus_market.dao.common;

import com.yuanlrc.campus_market.entity.common.KillProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KillProductDao  extends JpaRepository<KillProduct, Long>, JpaSpecificationExecutor<KillProduct> {
}
