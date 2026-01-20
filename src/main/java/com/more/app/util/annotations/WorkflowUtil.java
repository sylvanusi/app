package com.more.app.util.annotations;

import com.more.app.entity.product.Register;
import com.more.app.repository.productsetup.ProductTypeEventPolicyRepository;

public class WorkflowUtil {
	
	private ProductTypeEventPolicyRepository productTypeEventPolicyRepository;
	public long getCurrentPolicyId(Register entity)
	{
		
		return entity.getCurrentQueueId();
	}
		
	public String getCurrentPolicyClass(Register entity)
	{
		String policyClass = productTypeEventPolicyRepository.getPolicyClass(entity.getCurrentQueueId());
		return policyClass;
	}

	public String getCurrentPolicyName(Register entity)
	{
		String policyName = productTypeEventPolicyRepository.getPolicyName(entity.getCurrentQueueId());
		return policyName;
	}

	public ProductTypeEventPolicyRepository getProductTypeEventPolicyRepository() {
		return productTypeEventPolicyRepository;
	}


	public void setProductTypeEventPolicyRepository(ProductTypeEventPolicyRepository productTypeEventPolicyRepository) {
		this.productTypeEventPolicyRepository = productTypeEventPolicyRepository;
	}
	
	

}
