package com.more.app.base.ui.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.base.ui.configuration.ProductConfigurationView;
import com.more.app.base.ui.setup.BranchView;
import com.more.app.base.ui.setup.CustomerView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Branch;
import com.more.app.entity.Customer;
import com.more.app.entity.Product;
import com.more.app.entity.ProductReferenceNumberDefination;
import com.more.app.entity.ProductTypeEventPolicy;
import com.more.app.entity.enums.ReferenceNumberItem;
import com.more.app.entity.enums.Status;
import com.more.app.entity.product.ProductWorkFlowQueue;
import com.more.app.entity.product.Register;
import com.more.app.repository.BranchRepository;
import com.more.app.repository.CustomerRepository;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductReferenceNumberDefinationRepository;
import com.more.app.repository.productsetup.ProductRepository;
import com.more.app.repository.productsetup.ProductTypeEventPolicyRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;
import com.more.app.service.policy.RegisterApprovalPolicy;
import com.more.app.service.policy.RegisterCancellationPolicy;
import com.more.app.service.policy.RegisterCreatePolicy;
import com.more.app.service.policy.RegisterUpdatePolicy;
import com.more.app.service.policyenum.RegisterPolicyEnum;
import com.more.app.util.ReferenceNumberItemUtil;
import com.more.app.util.WorkFlowProductItemUtil;
import com.more.app.util.annotations.UIActionUtil;
import com.more.app.util.annotations.WorkflowUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import jakarta.annotation.PostConstruct;

@Route(value = RegisterCrudView.pageUrlString, layout = CrudPageView.class)
public class RegisterCrudView extends BaseCrudComponent<Register> implements HasUrlParameter<String> {
	public static final String pageUrlString = "Register";
	private Register entity;
	private String pageMode = "";
	private String pageTitle = "Register";
	private TextField productTF;
	private Button searchproduct;
	private HorizontalLayout productHl, amountHl, applicantHl, beneficiaryHl, branchHl;
	private Checkbox transactionStatusBox;
	private Product product;
	private Binder<Register> binder = new Binder();

	private TextField moduleTF, productTypeTF, transactionReferenceTF, internalReferenceTF, eventTf, currentQueueTf,
			nextQueueTF;
	private BigDecimalField amountTF;
	private Select<String> ccySF;
	private TextField applicantTF, beneficiaryTF, branchTf;
	private Button searchApplicant, searchBeneficiary, searchBranch;
	private FormLayout formLayout = new FormLayout();
	private FormLayout productformLayout = new FormLayout();
	private CancellationReasonComponent reasonComponent;
	private Button closeButton, saveButton, submitButton, declineButton;
	private Div bodyVl = new Div();

	private TabSheet tabSheet = new TabSheet();

	private ProductTypeEventPolicy policyEntity;
	private ProductWorkFlowQueue currentQueue;
	private ProductWorkFlowQueue nextQueue;

	private Long currentQueuePolicyId = -1L;

	private H5 title = new H5();
	private RadioButtonGroup<String> enableApproveorDecline;
	@Autowired
	private ProductRepository prodRepository;
	@Autowired
	private RegisterRepository repository;
	@Autowired
	private ProductReferenceNumberDefinationRepository refNoRepository;
	@Autowired
	private ProductWorkFlowQueueRepository pwfqRepository;
	@Autowired
	private CustomerRepository custRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private ProductTypeEventPolicyRepository productTypeEventPolicyRepository;

	// private RegisterCreatePolicy policy;

	@Autowired
	private ProductWorkFlowQueueRepository workflowQueueRepo;

	public RegisterCrudView() {
		super();
	}

	private void saveAction() {
		Register register = binder.getBean();
		// register.setAuditUser(CurrentUser.get());
		register.getCancelReasons().clear();
		if (!reasonComponent.getCancellationReasions().isEmpty())
			register.setCancelReasons(reasonComponent.getCancellationReasions());
		repository.save(register);
		Notification.show("Register Saved for Processing", 5000, Position.TOP_CENTER);
	}

	@PostConstruct
	private void init() {

		tabSheet.addThemeVariants(TabSheetVariant.AURA_TABS_FILLED, TabSheetVariant.AURA_TABS_ACCENT);
		title.setText(pageTitle);
		title.getElement().getStyle().set("font-weight", "bold");
		HorizontalLayout hlheader = new HorizontalLayout();
		hlheader.setPadding(false);
		hlheader.setMargin(false);
		hlheader.setSpacing(true);
		hlheader.add(title);
		hlheader.setVerticalComponentAlignment(Alignment.CENTER, title);
		hlheader.setFlexGrow(4, title);
		Hr hr = new Hr();

		closeButton = new Button("Close", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
		closeButton.addClickListener(event -> getUI().get().navigate(getCloseNavigationClass()));
		closeButton.getElement().getStyle().set("color", "black");
		closeButton.getElement().getStyle().set("border", "0.2px solid #000000");
		closeButton.getElement().getStyle().set("background-color", "white");

		searchproduct = new Button(new Icon(VaadinIcon.SEARCH));
		searchApplicant = new Button(new Icon(VaadinIcon.SEARCH));
		searchBeneficiary = new Button(new Icon(VaadinIcon.SEARCH));
		searchBranch = new Button(new Icon(VaadinIcon.SEARCH));

		productTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "product"));
		productTF.setMaxLength(35);
		productTF.setReadOnly(true);
		productTF.setWidthFull();

		productTypeTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "productType"));
		productTypeTF.setMaxLength(35);
		productTypeTF.setReadOnly(true);

		moduleTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "module"));
		moduleTF.setMaxLength(35);
		moduleTF.setReadOnly(true);

		eventTf = new TextField(UIActionUtil.getFieldLabel(Register.class, "eventName"));
		eventTf.setMaxLength(35);
		eventTf.setReadOnly(true);

		currentQueueTf = new TextField(UIActionUtil.getFieldLabel(Register.class, "currentQueueName"));
		currentQueueTf.setMaxLength(35);
		currentQueueTf.setReadOnly(true);

		nextQueueTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "nextQueueName"));
		nextQueueTF.setMaxLength(35);
		nextQueueTF.setReadOnly(true);

		// eventTf, currentQueueTf, nextQueueTF

		transactionReferenceTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "transactionReference"));
		transactionReferenceTF.setMaxLength(16);
		transactionReferenceTF.setEnabled(false);
		binder.forField(transactionReferenceTF).bind(Register::getTransactionReference,
				Register::setTransactionReference);

		internalReferenceTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "internalReference"));
		internalReferenceTF.setMaxLength(16);
		binder.forField(internalReferenceTF).bind(Register::getInternalReference, Register::setInternalReference);

		ccySF = new Select();
		ccySF.setLabel(UIActionUtil.getFieldLabel(Register.class, "transactionCcy"));
		ccySF.setRequiredIndicatorVisible(true);
		// ccySF.setItems("", "USD", "GBP", "NGN");
		ccySF.setWidth("25%");
		binder.forField(ccySF).bind(Register::getTransactionCcy, Register::setTransactionCcy);

		amountTF = new BigDecimalField(UIActionUtil.getFieldLabel(Register.class, "transactionAmount"));
		amountTF.setWidthFull();
		binder.forField(amountTF).bind(Register::getTransactionAmount, Register::setTransactionAmount);

		applicantTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "applicant"));
		applicantTF.setMaxLength(35);
		applicantTF.setWidthFull();

		beneficiaryTF = new TextField(UIActionUtil.getFieldLabel(Register.class, "beneficiaryCustomer"));
		beneficiaryTF.setMaxLength(35);
		beneficiaryTF.setWidthFull();

		branchTf = new TextField(UIActionUtil.getFieldLabel(Register.class, "transactionBranch"));
		branchTf.setMaxLength(35);
		branchTf.setWidthFull();

		transactionStatusBox = new Checkbox("Cancel Transaction");
		transactionStatusBox.setVisible(false);

		productHl = new HorizontalLayout();
		productHl.add(productTF, searchproduct);
		productHl.setVerticalComponentAlignment(Alignment.BASELINE, productTF, searchproduct);

		amountHl = new HorizontalLayout();
		amountHl.add(ccySF, amountTF);
		amountHl.setVerticalComponentAlignment(Alignment.BASELINE, ccySF, amountTF);

		// ,applicantHl, beneficiaryHl, branchHl
		applicantHl = new HorizontalLayout();
		applicantHl.add(applicantTF, searchApplicant);
		applicantHl.setVerticalComponentAlignment(Alignment.BASELINE, applicantTF, searchApplicant);

		beneficiaryHl = new HorizontalLayout();
		beneficiaryHl.add(beneficiaryTF, searchBeneficiary);
		beneficiaryHl.setVerticalComponentAlignment(Alignment.BASELINE, beneficiaryTF, searchBeneficiary);

		branchHl = new HorizontalLayout();
		branchHl.add(branchTf, searchBranch);
		branchHl.setVerticalComponentAlignment(Alignment.BASELINE, branchTf, searchBranch);

		reasonComponent = new CancellationReasonComponent("Cancellation Reason", true);
		reasonComponent.setVisible(false);

		enableApproveorDecline = new RadioButtonGroup<>();
		enableApproveorDecline.setItems("Enable Decline", "Enable Submit");
		enableApproveorDecline.addThemeVariants(RadioGroupVariant.AURA_HORIZONTAL);

		productformLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));
		productformLayout.add(productHl, moduleTF, productTypeTF);

		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
		formLayout.add(applicantHl, beneficiaryHl, amountHl, branchHl, transactionReferenceTF, internalReferenceTF,
				transactionStatusBox, reasonComponent);
		formLayout.setVisible(false);

		saveButton = new Button("Save to Current Queue", new Icon(VaadinIcon.EDIT));
		saveButton.getElement().getStyle().set("color", "#0aa8f7");
		saveButton.getElement().getStyle().set("border", "0.2px solid #0aa8f7");
		saveButton.getElement().getStyle().set("background-color", "white");

		submitButton = new Button("Submit", new Icon(VaadinIcon.CHECK_SQUARE_O));
		submitButton.addClickListener(event -> getUI().get().navigate(getCloseNavigationClass()));
		submitButton.getElement().getStyle().set("color", "#004e06");
		submitButton.getElement().getStyle().set("border", "0.2px solid #004e06");
		submitButton.getElement().getStyle().set("background-color", "white");
		submitButton.setEnabled(false);

		declineButton = new Button("Decline", new Icon(VaadinIcon.ARROW_CIRCLE_DOWN));
		declineButton.addClickListener(event -> getUI().get().navigate(getCloseNavigationClass()));
		declineButton.getElement().getStyle().set("color", "#e82315");
		declineButton.getElement().getStyle().set("border", "0.2px solid #e82315");
		declineButton.getElement().getStyle().set("background-color", "white");
		declineButton.setEnabled(false);

		HorizontalLayout hlFooter = new HorizontalLayout();
		hlFooter.setPadding(false);
		hlFooter.setMargin(false);
		hlFooter.setSpacing(true);
		hlFooter.add(saveButton, declineButton, submitButton, closeButton);

		VerticalLayout headerVl = new VerticalLayout();
		headerVl.add(productformLayout);

		formLayout.getElement().getStyle().set("overflow", "auto");
		formLayout.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");

		tabSheet.add("Main Details", new VerticalLayout(formLayout, enableApproveorDecline));
		tabSheet.add("Other", new VerticalLayout(new H5("Use the Page to set uo Check List Items")));

		bodyVl.getElement().getStyle().set("overflow", "auto");
		bodyVl.getElement().getStyle().set("padding-right", "var(--lumo-space-m)");
		bodyVl.getElement().getStyle().set("border", "2px solid #e6f5ff");
		// bodyVl.setVisible(false);
		tabSheet.setVisible(true);

		add(headerVl, hlheader, tabSheet, bodyVl, hlFooter);

		setSpacing(true);
		setMargin(true);
		// setWidth("650px");
		setHeightFull();

		searchproduct.addClickListener(event -> {
			Dialog dg = new Dialog();
			ProductConfigurationView view = new ProductConfigurationView(ui, dg, prodRepository);
			view.getHl().setVisible(false);
			view.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!view.grid.getSelectedItems().isEmpty()) {

					Object[] arr = view.grid.getSelectedItems().toArray();
					Product product1 = (Product) arr[0];
					Product product = prodRepository.findById(product1.getId()).orElse(null);
					if (null != product && !product.getStatus().equals("A"))
						return;

					List<ProductReferenceNumberDefination> refNoDefList = refNoRepository
							.findByProductId(product.getId());
					boolean useccy = refNoDefList.stream()
							.anyMatch(refNoItem -> ReferenceNumberItem.TRANS_CCY.equals(refNoItem.getItem()));
					boolean usebranch = refNoDefList.stream()
							.anyMatch(refNoItem -> ReferenceNumberItem.BRANCH_CODE.equals(refNoItem.getItem())
									|| ReferenceNumberItem.BRANCH_NO.equals(refNoItem.getItem()));

					productTF.setValue(product.getProductName());
					moduleTF.setValue(product.getModuleCode());
					productTypeTF.setValue(product.getTypeCode());
					ccySF.setItems(product.getAllowedCurrencies());

					binder.getBean().setProduct(product);
					binder.getBean().setProductId(product.getId());

					WorkFlowProductItemUtil wpItemUtil = new WorkFlowProductItemUtil();
					wpItemUtil.setRepository(pwfqRepository);
					Register register = wpItemUtil.generateInputterWorkFlowProductItemDetails(binder.getBean(),
							product);

					binder.getBean().setCurrentQueue(register.getCurrentQueue());
					binder.getBean().setCurrentQueueId(register.getCurrentQueue().getId());
					binder.getBean().setNextQueue(register.getNextQueue());
					binder.getBean().setNextQueueId(register.getNextQueue().getId());
					binder.getBean().setWorkflowStatus(register.getWorkflowStatus());
					binder.getBean().setWorkflowReference(register.getWorkflowReference());
					binder.getBean().setWorkflowReferenceNo(register.getWorkflowReferenceNo());
					binder.getBean().setNextQueueFlowSequence(register.getNextQueue().getFlowSequence());
					binder.getBean().setTransactionStatus(Status.IP);
					binder.getBean().setNextQueueEventId(register.getNextQueue().getEventId());
					binder.getBean().setCurrentQueueEventId(register.getCurrentQueue().getEventId());

					if (!useccy && !usebranch) {
						String referenceNo = ReferenceNumberItemUtil.generateTransactionReference(refNoDefList,
								binder.getBean());
						binder.getBean().setTransactionReference(referenceNo);
						transactionReferenceTF.setValue(referenceNo);
						saveAction();
					}
					register = repository.save(binder.getBean());

					register = repository.findById(register.getId()).get();

					binder.readBean(register);

					formLayout.setVisible(true);
					saveButton.setVisible(true);
					// submitButton.setVisible(true);
					title.setText(register.getProductType() + " -> " + register.getEventDescription() + " Event -> "
							+ register.getCurrentQueueName() + " Queue");
					submitButton.setText("Submit To " + register.getNextQueueName() + " Queue");

					tabSheet.setVisible(true);
					enableApproveorDecline.setVisible(true);
					if (register.getCurrentQueueFlowSequence() == 1) {
						declineButton.setVisible(false);
						enableApproveorDecline.setItems("Enable Submit");
					}
				}
			});
			view.add(selectItem);

			dg.add(view);
			dg.open();
		});

		searchApplicant.addClickListener(event -> {
			Dialog dg = new Dialog();
			CustomerView view = new CustomerView(ui, dg, custRepository);
			view.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!view.grid.getSelectedItems().isEmpty()) {
					Object[] arr = view.grid.getSelectedItems().toArray();
					Customer customer = (Customer) arr[0];
					applicantTF.setValue(customer.getFullName());
					binder.getBean().setApplicant(customer);
					binder.getBean().setApplicantId(customer.getId());
					binder.getBean().setApplicantName(customer.getFullName());
				}
			});
			view.add(selectItem);
			dg.add(view);
			dg.open();
		});

		searchBeneficiary.addClickListener(event -> {
			Dialog dg = new Dialog();
			CustomerView view = new CustomerView(ui, dg, custRepository);
			view.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!view.grid.getSelectedItems().isEmpty()) {
					Object[] arr = view.grid.getSelectedItems().toArray();
					Customer entity = (Customer) arr[0];
					beneficiaryTF.setValue(entity.getFullName());
					binder.getBean().setBeneficiaryCustomer(entity);
					binder.getBean().setBeneficiaryCustomerId(entity.getId());
					binder.getBean().setBeneficiaryCustomerName(entity.getFullName());
				}
			});
			view.add(selectItem);
			dg.add(view);
			dg.open();
		});

		searchBranch.addClickListener(event -> {

			Dialog dg = new Dialog();
			BranchView view = new BranchView(ui, dg, branchRepository);
			view.select.setVisible(false);
			Button selectItem = new Button("Select Item");
			selectItem.setSizeFull();
			selectItem.addClickListener(evt -> {
				if (!view.grid.getSelectedItems().isEmpty()) {
					Object[] arr = view.grid.getSelectedItems().toArray();
					Branch entity = (Branch) arr[0];
					branchTf.setValue(entity.getName());
					binder.getBean().setTransactionBranch(entity);
					binder.getBean().setTransactionBranchId(entity.getId());
					binder.getBean().setTransactionBranchName(entity.getName());
				}
			});
			view.add(selectItem);
			dg.add(view);
			dg.open();
		});

		transactionStatusBox.addValueChangeListener(event -> {
			if (event.getValue()) {
				binder.getBean().setTransactionStatus(Status.CN);
				reasonComponent.setVisible(true);
			} else {
				binder.getBean().setTransactionStatus(Status.IP);
				if (reasonComponent.getCancellationReasions().isEmpty())
					reasonComponent.setVisible(false);
				else
					reasonComponent.setVisible(true);
			}
		});

		saveButton.addClickListener(event -> saveAction());

		declineButton.addClickListener(event -> {
			Register register = binder.getBean();
			// register.setAuditUser(CurrentUser.get());
			register.getCancelReasons().clear();
			if (!reasonComponent.getCancellationReasions().isEmpty()) {
				register.setCancelReasons(reasonComponent.getCancellationReasions());
				repository.save(register);
				Notification.show("Register Saved for Processing", 5000, Position.TOP_CENTER);
			} else
				Notification.show("Enter decline reason before saving record", 5000, Position.TOP_CENTER);
		});

		submitButton.addClickListener(event -> {
			try {
				binder.writeBean(entity);

				Register register = binder.getBean();
				// register.setAuditUser(CurrentUser.get());
				register.getCancelReasons().clear();
				if (!reasonComponent.getCancellationReasions().isEmpty())
					register.setCancelReasons(reasonComponent.getCancellationReasions());
				WorkflowUtil util = new WorkflowUtil();
				// currentQueuePolicyId = util.getCurrentPolicyId(register);
				// policyEntity =
				// productTypeEventPolicyRepository.getById(currentQueuePolicyId);
				util.setProductTypeEventPolicyRepository(productTypeEventPolicyRepository);
				String policyName = util.getCurrentPolicyName(register);
				// To do call the policy attach to the workflow queue

				if (policyName.equals(RegisterPolicyEnum.REGISTER_CREATE_POLICY.getPolicyName())) {
					RegisterCreatePolicy policy = new RegisterCreatePolicy();
					policy.setRepository(repository);
					policy.setWorflowQueueRepo(workflowQueueRepo);
					policy.executePolicy(register);
				} else if (policyName.equals(RegisterPolicyEnum.REGISTER_UPDATE_POLICY.getPolicyName())) {
					RegisterUpdatePolicy policy = new RegisterUpdatePolicy();
					policy.setRepository(repository);
					policy.setWorflowQueueRepo(workflowQueueRepo);
					policy.executePolicy(register);
				} else if (policyName.equals(RegisterPolicyEnum.REGISTER_APPROVAL_POLICY.getPolicyName())) {
					RegisterApprovalPolicy policy = new RegisterApprovalPolicy();
					policy.setRepository(repository);
					policy.setWorflowQueueRepo(workflowQueueRepo);
					policy.executePolicy(register);
				} else if (policyName.equals(RegisterPolicyEnum.REGISTER_CANCELLATION_POLICY.getPolicyName())) {
					RegisterCancellationPolicy policy = new RegisterCancellationPolicy();
					policy.setRepository(repository);
					policy.setWorflowQueueRepo(workflowQueueRepo);
					policy.executePolicy(register);
				} else {
				}

				if (register.getNextQueue() != null)
					Notification.show("SuccessFully Submitted to " + register.getNextQueueName(), 5000,
							Position.TOP_CENTER);
				else
					Notification.show("SuccessFully Submitted");

			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		enableApproveorDecline.addValueChangeListener(event -> {
			declineButton.setVisible(event.getValue().equals("Enable Decline"));
			declineButton.setEnabled(event.getValue().equals("Enable Decline"));
			submitButton.setVisible(event.getValue().equals("Enable Submit"));
			submitButton.setEnabled(event.getValue().equals("Enable Submit"));
		});

		hr1.setVisible(false);
		super.hl.setVisible(false);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		if (entity != null && entity.getProductId() != null) {

			Product product = prodRepository.findById(entity.getProductId()).orElse(null);
			entity.setProduct(product);
			if (null != entity.getTransactionBranchId()) {
				Customer applicant = custRepository.findById(entity.getApplicantId()).orElse(null);
				entity.setApplicant(applicant);
			}
			if (null != entity.getTransactionBranchId()) {
				Customer beneficiary = custRepository.findById(entity.getBeneficiaryCustomerId()).orElse(null);
				entity.setBeneficiaryCustomer(beneficiary);
			}
			if (null != entity.getTransactionBranchId()) {
				Branch transactingBranch = branchRepository.findById(entity.getTransactionBranchId()).orElse(null);
				entity.setTransactionBranch(transactingBranch);
			}

			currentQueue = pwfqRepository.getById(entity.getCurrentQueueId());
			nextQueue = pwfqRepository.getById(entity.getNextQueueId());

			entity.setCurrentQueue(currentQueue);
			entity.setNextQueue(nextQueue);
			// entity.setNextQueueEventId(nextQueue.getEventId());
			// entity.setCurrentQueueEventId(currentQueue.getEventId());

			binder.forField(transactionReferenceTF).bind(Register::getTransactionReference,
					Register::setTransactionReference);
			binder.forField(internalReferenceTF).bind(Register::getInternalReference, Register::setInternalReference);
			binder.forField(amountTF).bind(Register::getTransactionAmount, Register::setTransactionAmount);
			binder.forField(beneficiaryTF).bind(Register::getBeneficiaryCustomerName,
					Register::setBeneficiaryCustomerName);

			binder.forField(eventTf).bind(Register::getEventDescription, Register::setEventDescription);
			binder.forField(currentQueueTf).bind(Register::getCurrentQueueName, Register::setCurrentQueueName);
			binder.forField(nextQueueTF).bind(Register::getNextQueueName, Register::setNextQueueName);

			transactionStatusBox.setVisible(entity.getId() != null);
			transactionStatusBox.setValue(Status.CN.equals(entity.getTransactionStatus()));

			boolean prodIdIsNotNull = (entity.getProductId() != null);
			if (prodIdIsNotNull) {
				ccySF.setItems(entity.getProduct().getAllowedCurrencies());
				productTF.setValue(entity.getProductName());
				moduleTF.setValue(entity.getModule());
				productTypeTF.setValue(entity.getProductType());
				ccySF.setValue(entity.getTransactionCcy());

				if (null != entity.getApplicant())
					applicantTF.setValue(entity.getApplicant().getFullName());

				if (null != entity.getBeneficiaryCustomer())
					beneficiaryTF.setValue(entity.getBeneficiaryCustomer().getFullName());

				if (null != entity.getTransactionBranch())
					branchTf.setValue(entity.getTransactionBranch().getName());
			}
			binder.forField(ccySF).bind(Register::getTransactionCcy, Register::setTransactionCcy);

			formLayout.setVisible(entity.getProduct() != null);

			if (entity != null && !entity.getCancelReasons().isEmpty()) {
				reasonComponent.setVisible(true);
				reasonComponent.setCancellationReasons(entity.getCancelReasons());
				transactionStatusBox.setValue(true);
			}

			if (entity.getId() != null)
				title.setText(entity.getProductType() + " -> " + entity.getEventDescription() + " Event -> "
						+ entity.getCurrentQueueName() + " Queue");
			if (entity.getNextQueue() != null) {
				if (entity.isFinalQueue()) {
					submitButton.setText("Approve Transaction");
				} else
					submitButton.setText("Submit To " + entity.getNextQueueName() + " Queue");
			} else {
				if (Status.CN.equals(entity.getTransactionStatus()))
					submitButton.setText("Approve Cancellation");
				else
					submitButton.setText("Approve Transaction");
			}
			saveButton.setVisible(prodIdIsNotNull);
			enableApproveorDecline.setVisible(prodIdIsNotNull);
			tabSheet.setVisible(prodIdIsNotNull);
			binder.setBean(entity);
			binder.readBean(entity);

		}
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter) {
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			pageMode = params[0];

			if ("ADD".equals(pageMode))
				entity = new Register();
			else {
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else {
					entity = repository.findById(id).orElse(null);
					System.out.println("----------------------------------------------------------------");
					System.out.println(entity.toString());
				}
			}
			binder.setBean(entity);
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<RegisterView> getCloseNavigationClass() {
		return RegisterView.class;
	}

	@Override
	public void initializeComponents() {
	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clearSelectedEntity(Register pojo) {

	}

	@Override
	public boolean fullsize() {
		return true;
	}

	@Override
	public String getPageTitle() {
		return "Register";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<Register> getBinder() {
		return binder;
	}

	@Override
	public Register getEntity() {
		return entity;
	}

	@Override
	public void saveRecord(Register entity) {
		repository.save(entity);
	}

}
