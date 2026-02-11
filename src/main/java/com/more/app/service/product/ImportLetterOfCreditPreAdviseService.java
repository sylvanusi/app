package com.more.app.service.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.more.app.entity.product.ImportLetterOfCreditPreAdvise;
import com.more.app.entity.product.LcMaster;
import com.more.app.repository.product.ImportLetterOfCreditPreAdviseRepository;
import com.more.app.repository.product.LcMasterRepository;

import jakarta.transaction.Transactional;

@Service
public class ImportLetterOfCreditPreAdviseService {

	
    private final LcMasterRepository masterRepository;
	
	
    private final ImportLetterOfCreditPreAdviseRepository ilcPreAdviseRepo;
	
	
	public ImportLetterOfCreditPreAdviseService(@Autowired LcMasterRepository masterRepository, ImportLetterOfCreditPreAdviseRepository ilcPreAdviseRepo)
	{
		this.masterRepository = masterRepository;
		this.ilcPreAdviseRepo = ilcPreAdviseRepo;
	}

	@Transactional
	public ImportLetterOfCreditPreAdvise savePreAdvise(ImportLetterOfCreditPreAdvise entity)
	{
		entity = ilcPreAdviseRepo.save(entity);
		
		LcMaster lcmaster = masterRepository.findById(entity.getLcMasterId()).orElse(null);
		lcmaster.setLcEvent("PREADVISED");		
		lcmaster.setEventStatus("INPROGRESS");
		lcmaster.setLcStatus("PREADVISED");
		masterRepository.save(lcmaster);
		return entity;
	}
    
}