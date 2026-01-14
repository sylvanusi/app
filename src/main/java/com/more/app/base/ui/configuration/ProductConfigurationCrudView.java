package com.more.app.base.ui.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Product;
import com.more.app.entity.ProductModule;
import com.more.app.entity.ProductType;
import com.more.app.repository.CurrencyRepository;
import com.more.app.repository.productsetup.ChargeDefinationRepository;
import com.more.app.repository.productsetup.ProductEventSwiftRepository;
import com.more.app.repository.productsetup.ProductModuleRepository;
import com.more.app.repository.productsetup.ProductReferenceNumberDefinationRepository;
import com.more.app.repository.productsetup.ProductRepository;
import com.more.app.repository.productsetup.ProductTypeEventPolicyRepository;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.more.app.repository.productsetup.ProductTypeRepository;
import com.more.app.repository.productsetup.ProductWorkFlowQueueRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import jakarta.annotation.PostConstruct;

@Route(value = ProductConfigurationCrudView.pageUrlString, layout = CrudPageView.class)
public class ProductConfigurationCrudView extends BaseCrudComponent<Product> implements HasUrlParameter<String> {
	private static final long serialVersionUID = 871239298806282590L;
	public static final String pageUrlString = "productcfg";
	private Binder<Product> binder = new Binder<>(Product.class);

	@Autowired
	private ProductRepository repository;
	@Autowired
	private ProductTypeRepository pTyperepository;
	@Autowired
	private ProductModuleRepository pModulerepository;
	@Autowired
	private CurrencyRepository currencyRepository;	
	@Autowired
	private ProductReferenceNumberDefinationRepository refNoDefRepository;	
	@Autowired
	private ProductWorkFlowQueueRepository wfqRepository;
	@Autowired
	private ProductTypeEventRepository eventRepository;
	@Autowired
	private ProductTypeEventPolicyRepository policyRepo;
	@Autowired
	private ProductTypeEventRepository pteRepository;
	@Autowired
	private CurrencyRepository ccyRepo;
	@Autowired
	private ChargeDefinationRepository chargeDefRepository;
	@Autowired
	private ProductTypeEventRepository productTypeEventRepo;
	@Autowired
	private ProductEventSwiftRepository eventSwiftRepository;
	
	public ProductConfigurationCrudView() {
		super();
	}

	@PostConstruct
	private void init() {
		FormLayout fl = new FormLayout();
		fl.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
		fl.add(productTypeCB, codeTF, nameTF, descriptionTA, refNoFieldLength);

		VerticalLayout vlMain = new VerticalLayout(fl, new Hr(), allowedCcyView, checklistView);
		vlMain.setWidthFull();

		allowedCcyView.setCurrencyRepo(currencyRepository);
		referenceNoView.setRepository(refNoDefRepository);
		workflowView.setRepository(wfqRepository);
		workflowView.setEventRepository(eventRepository);
		workflowView.setPolicyRepo(policyRepo);
		eventChargeView.setProductTypeEventRepo(eventRepository);
		eventChargeView.setChargeDefRepository(chargeDefRepository);
		eventSwiftView.setProductTypeEventRepo(eventRepository);
		eventSwiftView.setRepository(eventSwiftRepository);
				
		tabSheet.add("Main Details", vlMain);
		


		vl.add(tabSheet);
		vl.setHeightFull();
		vl.setWidth("1200px");
	}

	private TextField codeTF, nameTF;
	private TextArea descriptionTA;
	private IntegerField refNoFieldLength;
	private ComboBox<ProductModule> moduleCB;
	private ComboBox<ProductType> productTypeCB;
	private ComboBox<String> statusCB;
	private ProductEventCheckListView checklistView;
	private AllowedCurrencyComponent allowedCcyView;
	private ReferenceNumberDefinationView referenceNoView;
	private WorkflowConfigurationView workflowView;
	private ProductEventChargeConfigurationView eventChargeView;
	private ProductEventSwiftConfigurationView eventSwiftView;

	


	private String oldPageMode = "";

	private TabSheet tabSheet = new TabSheet();


	@Override
	public void initializeComponents() {
		codeTF = new TextField();
		codeTF.setMaxLength(10);
		codeTF.setRequired(true);
		codeTF.setRequiredIndicatorVisible(true);

		nameTF = new TextField();
		nameTF.setMaxLength(35);
		nameTF.setRequired(true);
		nameTF.setRequiredIndicatorVisible(true);

		descriptionTA = new TextArea();
		descriptionTA.setMaxLength(140);
		descriptionTA.setRequired(true);
		descriptionTA.setRequiredIndicatorVisible(true);

		moduleCB = new ComboBox<>();
		moduleCB.setItemLabelGenerator(ProductModule::getName);
		moduleCB.setRequired(true);
		moduleCB.setRequiredIndicatorVisible(true);

		productTypeCB = new ComboBox<>();
		productTypeCB.setItemLabelGenerator(ProductType::getProductType);
		productTypeCB.setRequired(true);
		productTypeCB.setRequiredIndicatorVisible(true);

		refNoFieldLength = new IntegerField();
		refNoFieldLength.setWidth("100px");
		refNoFieldLength.setRequiredIndicatorVisible(true);

		List<String> statusList = new ArrayList<>();
		statusList.add("A");
		statusList.add("I");
		statusCB = new ComboBox<>();
		statusCB.setItems(statusList);
		statusCB.setRequired(true);
		statusCB.setRequiredIndicatorVisible(true);

		checklistView = new ProductEventCheckListView(pteRepository);
		allowedCcyView = new AllowedCurrencyComponent(currencyRepository);		
		workflowView = new WorkflowConfigurationView();
		eventChargeView = new ProductEventChargeConfigurationView();
		eventSwiftView = new ProductEventSwiftConfigurationView();
		referenceNoView = new ReferenceNumberDefinationView(refNoDefRepository);

		productTypeCB.addValueChangeListener(event -> {
			if ("ADD".equals(getPageMode()) || "ADDNEW".equals(getPageMode()))
				checklistView.setProductEventCheckListComponent(event.getValue(), pteRepository);
		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		oldPageMode = getPageMode();
		if (entity != null) {
			if (entity.getTypeId() != null) {
				ProductType type = pTyperepository.findById(entity.getTypeId()).get();
				entity.setType(type);
				productTypeCB.setItems(pTyperepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
			}
			binder.setBean(entity);
			codeTF.setLabel(UIActionUtil.getFieldLabel(entity, "productCode"));
			nameTF.setLabel(UIActionUtil.getFieldLabel(entity, "productName"));
			descriptionTA.setLabel(UIActionUtil.getFieldLabel(entity, "description"));
			// moduleCB.setLabel(UIActionUtil.getFieldLabel(entity, "module"));
			productTypeCB.setLabel(UIActionUtil.getFieldLabel(entity, "type"));
			refNoFieldLength.setLabel(UIActionUtil.getFieldLabel(entity, "referenceLength"));
			binder.forField(codeTF).asRequired().bind("productCode");
			binder.forField(nameTF).asRequired().bind("productName");
			binder.forField(descriptionTA).asRequired().bind("description");
			// binder.forField(moduleCB).asRequired().bind("module");

			binder.forField(productTypeCB).asRequired().bind("type");
			binder.forField(refNoFieldLength)
					.withValidator(r -> Objects.nonNull(r) && r > 0, "Reference Length mus be greater than 0")
					.bind("referenceLength");

			checklistView.setProductEventCheckListComponent(entity);
			allowedCcyView.setAllowedCurrency(entity.getAllowedCurrencies());
			if (entity.getId() != null) {
				updateFieldsOnSave();
			}
		}

		if (getPageMode().equals("ADD"))
			productTypeCB.setItems(pTyperepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		if (getPageMode().equals("ADD") || getPageMode().equals("EDIT")) {
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
			productTypeCB.setEnabled(true);
		}
		if (getPageMode().equals("EDIT")) {
			moduleCB.setEnabled(false);
			productTypeCB.setEnabled(false);
		}
		binder.addStatusChangeListener(evt -> {
			if (!oldPageMode.equals(ui.getPageMode()))
				checklistView.setProductEventCheckListComponent(binder.getBean());
		});

		moduleCB.setItems(pModulerepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo) {
		getBinder().getBean().getProductEventCheckList().clear();
		checklistView.itemsComponentList.forEach(i -> {
			getBinder().getBean().getProductEventCheckList().add(i.binder.getBean());
		});
	}

	@Override
	public void setEventEntityFields(Product pojo) {
		pojo.setAllowedCurrencies(allowedCcyView.getAllowedCurrency());
	}

	@Override
	public void clearSelectedEntity(Product pojo) {

	}

	@Override
	public boolean fullsize() {
		return false;
	}

	@Override
	public String getPageTitle() {
		return "Product Configuration";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<Product> getBinder() {
		return binder;
	}

	@Override
	public Product getEntity() {
		return entity;
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter) {
		if (parameter != null && !parameter.isEmpty()) {
			String params[] = parameter.split(",");
			pageMode = params[0];
			setPageMode(pageMode);
			if ("ADD".equals(pageMode))
				entity = new Product();
			else {
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
				{
					entity = repository.findById(id).orElse(null);
					tabSheet.add("Reference Number", referenceNoView);
					tabSheet.add("WorkFlow Set up", workflowView);
					tabSheet.add("Event Charge Set up", eventChargeView);
					tabSheet.add("Swift Set up", eventSwiftView);
				}
				
				
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<ProductConfigurationView> getCloseNavigationClass() {
		return ProductConfigurationView.class;
	}

	@Override
	public void updateFieldsOnSave() {
		referenceNoView.updateFields(getEntity());
		workflowView.updateFields(getEntity());
		eventChargeView.updateFields(getEntity());
		eventSwiftView.updateFields(getEntity());

	}

	@Override
	public void saveRecord(Product entity) {
		entity.setTypeId(entity.getType().getId());
		setSelectedEntity(entity);
		repository.save(entity);
	}
}
