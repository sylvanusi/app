package com.more.app.service.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.more.app.entity.product.LcMaster;
import com.more.app.entity.product.dto.LcMasterSearchDTO;
import com.more.app.repository.product.LcMasterRepository;
import com.more.app.specification.LcMasterSpecification;

@Service
public class LcMasterService {

	@Autowired
    private final LcMasterRepository repository;
	
	
	public LcMasterService(@Autowired LcMasterRepository repository)
	{
		this.repository = repository;
		
	}

    public Page<LcMaster> search(LcMasterSearchDTO dto, Pageable pageable) {

        return repository.findAll(
                LcMasterSpecification.build(dto),
                pageable
        );
    }
}