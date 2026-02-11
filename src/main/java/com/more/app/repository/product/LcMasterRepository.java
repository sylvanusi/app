package com.more.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.more.app.entity.product.LcMaster;

public interface LcMasterRepository  extends JpaRepository<LcMaster, Long>, JpaSpecificationExecutor<LcMaster> {
	
	

}
