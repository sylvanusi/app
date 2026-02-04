package com.more.app.service.policy;

import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Customer;
import com.more.app.entity.enums.Status;
import com.more.app.entity.product.LcMaster;
import com.more.app.entity.product.Party;
import com.more.app.entity.product.Register;
import com.more.app.repository.CustomerRepository;
import com.more.app.repository.product.LcMasterRepository;
import com.more.app.repository.product.PartyRepository;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductReferenceNumberDefinationRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;
import com.more.app.util.ReferenceNumberItemUtil;

import jakarta.transaction.Transactional;

public class RegisterApprovalPolicy implements Policy {
	private RegisterRepository repository;
	private ProductWorkFlowQueueRepository worflowQueueRepo;

	private ReferenceNumberItemUtil refNoUtil;
	private ProductReferenceNumberDefinationRepository refNoDefRepository;
	private LcMasterRepository lcMasterRepository;
	private PartyRepository partyRepository;
	private CustomerRepository customerRepository;

	@Override
	@Transactional
	public <T extends AbstractPojo> Boolean executePolicy(T entity) {
		if (entity instanceof Register) {
			Register register = (Register) entity;

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("" + register.toString());

			// Create History for this Queue before changing the queue state.
			// Write to master table also

			String referenceNo = refNoUtil.generateTransactionReference(
					refNoDefRepository.findByProductId(register.getProductId()), register.getId());

			register.setWorkflowStatus("Approved");
			register.setEventStatus(Status.C);
			register.setTransactionStatus(Status.AP);

			register.setTransactionReference(referenceNo);
			repository.save(register);

			if ("ILC".equals(register.getProductTypeCode())) {
				// registerId, applicantPartyId, beneficiaryPartyId, lcCcy, lcAmount, lcEvent -
				// Registered, lcStatus - Registered, eventStatus - idle
				LcMaster master = new LcMaster();
				master.setRegisterId(register.getId());
				master.setLcCcy(register.getTransactionCcy());
				master.setLcAmount(register.getTransactionAmount());
				master.setLcEvent("REGISTERED");
				master.setLcStatus("REGISTERED");
				master.setEventStatus("COMPLETED");

				Long applicantId = register.getApplicantId();
				Customer applicant = customerRepository.findById(applicantId).orElse(null);
				if (null != applicant) {
					// name address1 address2 address3
					Party applicantParty = new Party();

					applicantParty.setName(applicant.getFullName());
					applicantParty.setAddress1(applicant.getAddress1());
					applicantParty.setAddress2(applicant.getAddress2());
					applicantParty.setAddress3(applicant.getAddress3());
					applicantParty = partyRepository.save(applicantParty);
					master.setApplicantPartyId(applicantParty.getId());
				}

				Long beneficiaryId = register.getBeneficiaryCustomerId();
				Customer beneficiary = customerRepository.findById(beneficiaryId).orElse(null);
				// account name address1 address2 address3
				if (null != beneficiary) {
					Party beneficiaryParty = new Party();
					// beneficiaryParty.setAccount(beneficiary.get);
					beneficiaryParty.setName(beneficiary.getFullName());
					beneficiaryParty.setAddress1(beneficiary.getAddress1());
					beneficiaryParty.setAddress2(beneficiary.getAddress2());
					beneficiaryParty.setAddress3(beneficiary.getAddress3());
					beneficiaryParty = partyRepository.save(beneficiaryParty);
					
					master.setBeneficiaryPartyId(beneficiaryParty.getId());
				}
				
				

				lcMasterRepository.save(master);
			}

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

	public ReferenceNumberItemUtil getRefNoUtil() {
		return refNoUtil;
	}

	public void setRefNoUtil(ReferenceNumberItemUtil refNoUtil) {
		this.refNoUtil = refNoUtil;
	}

	public ProductReferenceNumberDefinationRepository getRefNoDefRepository() {
		return refNoDefRepository;
	}

	public void setRefNoDefRepository(ProductReferenceNumberDefinationRepository refNoDefRepository) {
		this.refNoDefRepository = refNoDefRepository;
	}

	public void setLcMasterRepository(LcMasterRepository lcMasterRepository) {
		this.lcMasterRepository = lcMasterRepository;
	}

	public void setPartyRepository(PartyRepository partyRepository) {
		this.partyRepository = partyRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

}
