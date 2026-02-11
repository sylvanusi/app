package com.more.app.service.policy;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.enums.Status;
import com.more.app.entity.product.Register;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;

public class RegisterCreatePolicy implements Policy {
	private RegisterRepository repository;
	private ProductWorkFlowQueueRepository worflowQueueRepo;

	@Override
	public <T extends AbstractPojo> Boolean executePolicy(T entity) {
		System.out.println("RegisterCreatePolicy");
		if (entity instanceof Register) {
			Register register = (Register) entity;

			// System.out.println(register.toString());

			// Create History for this Queue before changing the queue state.
			// Write to master table also

			Long productId = register.getProductId();
			Long eventId = register.getNextQueueEventId();
			int nextSeq = register.getNextQueueFlowSequence();

			System.out.println("productId " + productId);
			System.out.println("eventId " + eventId);
			System.out.println("nextSeq " + nextSeq);

			register.setCurrentQueueId(register.getNextQueue().getId());
			register.setNextQueueId(
					(worflowQueueRepo.findNextProductWorkFlowQueue(productId, eventId, nextSeq + 1)).getId());

			register.setWorkflowStatus("Waiting For " + register.getCurrentQueueName());
			register.setEventStatus(Status.IP);
			register.setTransactionStatus(Status.I);

			repository.save(register);
			return true;
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
