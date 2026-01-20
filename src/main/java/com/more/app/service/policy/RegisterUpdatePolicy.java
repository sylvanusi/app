package com.more.app.service.policy;

import org.springframework.data.repository.query.Param;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.enums.Status;
import com.more.app.entity.product.Register;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;

public class RegisterUpdatePolicy implements Policy
{
	private RegisterRepository repository;	
	private ProductWorkFlowQueueRepository worflowQueueRepo;
	
	@Override
	public <T extends AbstractPojo> Boolean executePolicy(T entity)
	{
		System.out.println("RegisterUpdatePolicy");
		
		if (entity instanceof Register)
		{
			Register register = (Register) entity;
			
			//Create History for this Queue before changing the queue state.
			// Write to master table also
						
			Long productId = register.getProductId();
			Long eventId = register.getNextQueueEventId();
			Integer nextSeq = register.getNextQueueFlowSequence();
			Long nextQueueId = register.getNextQueueId();
			
			Integer maxSeq =  worflowQueueRepo.findMaxFlowSeqByProductIdandEventId(productId, eventId);
			
			System.out.println("Product Id" + productId);
			System.out.println("Event Id" + eventId);
			System.out.println("nextSeq " + nextSeq);
			System.out.println("maxSeq " + maxSeq);
			System.out.println("nextQueueId " + nextQueueId);
			
			if(nextSeq < maxSeq)
				nextSeq = nextSeq + 1;
			
			register.setCurrentQueueId(register.getNextQueue().getId());
			register.setNextQueueId((worflowQueueRepo.findNextProductWorkFlowQueue(productId, eventId,  nextSeq)).getId());
			
			register.setWorkflowStatus("Waiting For " + register.getCurrentQueueName());
			register.setEventStatus(Status.IP);
			
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
