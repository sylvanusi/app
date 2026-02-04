package com.more.app.service.policy;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.enums.Status;
import com.more.app.entity.product.Register;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;

public class RegisterCancellationPolicy implements Policy
{
	private RegisterRepository repository;	
	private ProductWorkFlowQueueRepository worflowQueueRepo;
	
	@Override
	public <T extends AbstractPojo> Boolean executePolicy(T entity)
	{
		System.out.println("RegisterCancellationPolicy");
		if (entity instanceof Register)
		{
			Register register = (Register) entity;
			
			//Create History for this Queue before changing the queue state.
			// Write to master table also
						
			
			register.setEventStatus(Status.CN);
			register.setTransactionStatus(Status.I);

			
			repository.save(register);
			return  true;
		} else
			return null;
	}

	public RegisterRepository getRepository() {
		return repository;
	}

	public void setRepository(RegisterRepository repository) {
		this.repository = repository;
	}

	public ProductWorkFlowQueueRepository getWorflowQueueRepo() {
		return worflowQueueRepo;
	}

	public void setWorflowQueueRepo(ProductWorkFlowQueueRepository worflowQueueRepo) {
		this.worflowQueueRepo = worflowQueueRepo;
	}
}
