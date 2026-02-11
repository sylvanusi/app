package com.more.app.base.ui.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.authorization.PermissionEntity;
import com.more.app.authorization.PermissionType;
import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.AppResource;
import com.more.app.entity.AppRole;
import com.more.app.entity.Country;
import com.more.app.entity.Currency;
import com.more.app.entity.ProductModule;
import com.more.app.entity.ProductType;
import com.more.app.entity.ProductTypeEvent;
import com.more.app.entity.ProductTypeEventPolicy;
import com.more.app.entity.User;
import com.more.app.repository.AppResourceRepository;
import com.more.app.repository.AppRoleRepository;
import com.more.app.repository.CountryRepository;
import com.more.app.repository.CurrencyRepository;
import com.more.app.repository.PermissionEntityRepository;
import com.more.app.repository.UserRepository;
import com.more.app.repository.productsetup.ProductModuleRepository;
import com.more.app.repository.productsetup.ProductTypeEventPolicyRepository;
import com.more.app.repository.productsetup.ProductTypeEventRepository;
import com.more.app.repository.productsetup.ProductTypeRepository;
import com.more.app.util.PasswordUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "initialData", layout = LeftAlignedLayout.class)
public class InitialDataView extends BaseView<User> {
	@Autowired
	private CurrencyRepository ccyRepo;
	@Autowired
	private CountryRepository ctryRepo;
	@Autowired
	private AppRoleRepository appRoleRepo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppResourceRepository appResourceRepository;
	@Autowired
	private ProductModuleRepository productModuleRepo;
	@Autowired
	private ProductTypeRepository productTypeRepo;
	@Autowired
	private ProductTypeEventRepository productTypeEventRepo;
	@Autowired
	private PermissionEntityRepository peRepo;
	@Autowired 
	private ProductTypeEventPolicyRepository policyRepo;
	
	
	public InitialDataView() {
		super();
		view = this;
		
		

		Button loadConfigData = new Button("Load Config Data");

		Button loadProductModule = new Button("Load Product Module");

		Button loadProductType = new Button("Load Product Type");

		Button loadProductTypeEvent = new Button("Load Product Type Event");

		Button loadPolicy = new Button("Load Policy");

		Button loadCSV = new Button("Load CSV");

		loadConfigData.addClickListener(event -> {
			
			User adminUser = new User();
			adminUser.setUsername("admin");
			adminUser.setPassword(PasswordUtil.generateHashedPassword("password"));
			adminUser.setName("System");
			adminUser.setEmail("admin@admin.com");
			
			userRepository.save(adminUser);
			
			AppRole adminRole = new AppRole();
			adminRole.setName("ADMIN");
			adminRole.setDescription("ADMIN");
			adminRole.setStatus("Active");
			appRoleRepo.save(adminRole);

			String currentUser = (String) VaadinSession.getCurrent().getAttribute("currentUser");
			User user = userRepository.findByUsername("admin");
			user.setRole(adminRole);
			user.setRoleId(adminRole.getId());
			userRepository.save(user);

			List<AppResource> resourceList = appResourceRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<PermissionEntity> pmelist = new ArrayList<PermissionEntity>();
			for (AppResource ar : resourceList) {
				PermissionEntity permission = new PermissionEntity(PermissionType.ALLOW);
				permission.setRole(user.getRoleId().toString());
				permission.setAction(ar.getAction());
				permission.setResource(ar.getIdentifier());
				permission.setAppresource(ar);
				pmelist.add(permission);
			}
			peRepo.saveAll(pmelist);
		});

		loadProductModule.addClickListener(event -> {
			List<ProductModule> productModuleList = new ArrayList<>();
			ProductModule lcProductModule = new ProductModule();
			lcProductModule.setCode("LC");
			lcProductModule.setName("Letter of Credit");
			productModuleList.add(lcProductModule);
			ProductModule ftProductModule = new ProductModule();
			ftProductModule.setCode("FT");
			ftProductModule.setName("Funds Transfer");
			productModuleList.add(ftProductModule);
			if (productModuleRepo.count() == 0)
				productModuleRepo.saveAll(productModuleList);
			Notification.show("Product Module Saved");
		});

		loadProductType.addClickListener(event -> {
			List<ProductModule> productModuleList = productModuleRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<ProductType> productTypeList = new ArrayList<>();
			ProductType ilcProductType = new ProductType();
			ilcProductType.setCode("ILC");
			ilcProductType.setProductType("Import Letter of Credit");
			ilcProductType
					.setModule(productModuleList.stream().filter(i -> i.getCode().endsWith("LC")).findFirst().get());
			productTypeList.add(ilcProductType);

			ProductType elcProductType = new ProductType();
			elcProductType.setCode("ELC");
			elcProductType.setProductType("Export Letter of Credit");
			elcProductType
					.setModule(productModuleList.stream().filter(i -> i.getCode().equals("LC")).findFirst().get());
			productTypeList.add(elcProductType);

			ProductType icProductType = new ProductType();
			icProductType.setCode("ICP");
			icProductType.setProductType("Inward Customer Payment");
			icProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(icProductType);

			ProductType ocProductType = new ProductType();
			ocProductType.setCode("OCP");
			ocProductType.setProductType("Outward Customer Payment");
			ocProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(ocProductType);

			ProductType ibProductType = new ProductType();
			ibProductType.setCode("IBP");
			ibProductType.setProductType("Inward Bank Payment");
			ibProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(ibProductType);

			ProductType obProductType = new ProductType();
			obProductType.setCode("OBP");
			obProductType.setProductType("Outward Bank Payment");
			obProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(obProductType);

			if (productTypeRepo.count() == 0)
				productTypeRepo.saveAll(productTypeList);
			Notification.show("Product Types Saved");
		});

		loadProductTypeEvent.addClickListener(event -> {
			List<ProductType> productTypeList = productTypeRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));;
			List<ProductTypeEvent> productTypeEventList = new ArrayList<>();
			ProductTypeEvent ilcRegisterEvent = new ProductTypeEvent();
			ilcRegisterEvent.setEventCode("REG");
			ilcRegisterEvent.setEventDescription("Register");
			ilcRegisterEvent
					.setProductType(productTypeList.stream().filter(i -> i.getCode().equals("ILC")).findFirst().get());
			ilcRegisterEvent.setOrder(1);
			ilcRegisterEvent.setEntityClass("com.princeps.app.backend.entity.productmodule.Register");
			productTypeEventList.add(ilcRegisterEvent);

			ProductTypeEvent ilcPreAdviceEvent = new ProductTypeEvent();
			ilcPreAdviceEvent.setEventCode("PRE");
			ilcPreAdviceEvent.setEventDescription("Pre Advise");
			ilcPreAdviceEvent
					.setProductType(productTypeList.stream().filter(i -> i.getCode().equals("ILC")).findFirst().get());
			ilcPreAdviceEvent.setOrder(2);
			productTypeEventList.add(ilcPreAdviceEvent);

			ProductTypeEvent ilcIssueEvent = new ProductTypeEvent();
			ilcIssueEvent.setEventCode("ISS");
			ilcIssueEvent.setEventDescription("Issue");
			ilcIssueEvent
					.setProductType(productTypeList.stream().filter(i -> i.getCode().equals("ILC")).findFirst().get());
			ilcIssueEvent.setOrder(3);
			productTypeEventList.add(ilcIssueEvent);

			ProductTypeEvent ocpRegisterEvent = new ProductTypeEvent();
			ocpRegisterEvent.setEventCode("REG");
			ocpRegisterEvent.setEventDescription("Register");
			ocpRegisterEvent
					.setProductType(productTypeList.stream().filter(i -> i.getCode().equals("OCP")).findFirst().get());
			ocpRegisterEvent.setOrder(1);
			ocpRegisterEvent.setEntityClass("com.princeps.app.backend.entity.productmodule.Register");
			productTypeEventList.add(ocpRegisterEvent);

			ProductTypeEvent ocpCreatEvent = new ProductTypeEvent();
			ocpCreatEvent.setEventCode("CRE");
			ocpCreatEvent.setEventDescription("Create");
			ocpCreatEvent
					.setProductType(productTypeList.stream().filter(i -> i.getCode().equals("OCP")).findFirst().get());
			ocpCreatEvent.setOrder(2);
			productTypeEventList.add(ocpCreatEvent);

			ProductTypeEvent ocpCancelEvent = new ProductTypeEvent();
			ocpCancelEvent.setEventCode("CAN");
			ocpCancelEvent.setEventDescription("Cancel");
			ocpCancelEvent
					.setProductType(productTypeList.stream().filter(i -> i.getCode().equals("OCP")).findFirst().get());
			ocpCancelEvent.setOrder(3);
			productTypeEventList.add(ocpCancelEvent);

			if (productTypeEventRepo.count() == 0)
				productTypeEventRepo.saveAll(productTypeEventList);

			ProductTypeEvent ocpRegEvt = productTypeEventRepo.findById(1L).get();
			ProductTypeEvent ilcRegEvt = productTypeEventRepo.findById(4L).get();

			Set<ProductTypeEventPolicy> registerPolicyList = new HashSet();
			ProductTypeEventPolicy ocpRegisterCreatePolicy = new ProductTypeEventPolicy();
			ocpRegisterCreatePolicy.setEvent(ocpRegEvt);
			ocpRegisterCreatePolicy.setPolicyName("REGISTER CREATE POLICY");
			ocpRegisterCreatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCreatePolicy");
			ocpRegisterCreatePolicy.setInputQueueType(true);
			ocpRegisterCreatePolicy.setUpdateQueueType(false);
			ocpRegisterCreatePolicy.setApprovalQueueType(false);
			ocpRegisterCreatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ocpRegisterUpdatePolicy = new ProductTypeEventPolicy();
			ocpRegisterUpdatePolicy.setEvent(ocpRegEvt);
			ocpRegisterUpdatePolicy.setPolicyName("REGISTER UPDATE POLICY");
			ocpRegisterUpdatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterUpdatePolicy");
			ocpRegisterUpdatePolicy.setInputQueueType(false);
			ocpRegisterUpdatePolicy.setUpdateQueueType(true);
			ocpRegisterUpdatePolicy.setApprovalQueueType(false);
			ocpRegisterUpdatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ocpRegisterApprovalPolicy = new ProductTypeEventPolicy();
			ocpRegisterApprovalPolicy.setEvent(ocpRegEvt);
			ocpRegisterApprovalPolicy.setPolicyName("REGISTER APPROVAL POLICY");
			ocpRegisterApprovalPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterApprovalPolicy");
			ocpRegisterApprovalPolicy.setInputQueueType(true);
			ocpRegisterApprovalPolicy.setUpdateQueueType(true);
			ocpRegisterApprovalPolicy.setApprovalQueueType(true);
			ocpRegisterApprovalPolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ocpRegisterCancelPolicy = new ProductTypeEventPolicy();
			ocpRegisterCancelPolicy.setEvent(ocpRegEvt);
			ocpRegisterCancelPolicy.setPolicyName("REGISTER CANCELLATION POLICY");
			ocpRegisterCancelPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCancellationPolicy");
			ocpRegisterCancelPolicy.setInputQueueType(false);
			ocpRegisterCancelPolicy.setUpdateQueueType(false);
			ocpRegisterCancelPolicy.setApprovalQueueType(false);
			ocpRegisterCancelPolicy.setCancelQueueType(true);

			ProductTypeEventPolicy ilcRegisterCreatePolicy = new ProductTypeEventPolicy();
			ilcRegisterCreatePolicy.setEvent(ilcRegEvt);
			ilcRegisterCreatePolicy.setPolicyName("REGISTER CREATE POLICY");
			ilcRegisterCreatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCreatePolicy");
			ilcRegisterCreatePolicy.setInputQueueType(true);
			ilcRegisterCreatePolicy.setUpdateQueueType(false);
			ilcRegisterCreatePolicy.setApprovalQueueType(false);
			ilcRegisterCreatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ilcRegisterUpdatePolicy = new ProductTypeEventPolicy();
			ilcRegisterUpdatePolicy.setEvent(ilcRegEvt);
			ilcRegisterUpdatePolicy.setPolicyName("REGISTER UPDATE POLICY");
			ilcRegisterUpdatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterUpdatePolicy");
			ilcRegisterUpdatePolicy.setInputQueueType(false);
			ilcRegisterUpdatePolicy.setUpdateQueueType(true);
			ilcRegisterUpdatePolicy.setApprovalQueueType(false);
			ilcRegisterUpdatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ilcRegisterApprovalPolicy = new ProductTypeEventPolicy();
			ilcRegisterApprovalPolicy.setEvent(ilcRegEvt);
			ilcRegisterApprovalPolicy.setPolicyName("REGISTER APPROVAL POLICY");
			ilcRegisterApprovalPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterApprovalPolicy");
			ilcRegisterApprovalPolicy.setInputQueueType(true);
			ilcRegisterApprovalPolicy.setUpdateQueueType(true);
			ilcRegisterApprovalPolicy.setApprovalQueueType(true);
			ilcRegisterApprovalPolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ilcRegisterCancelPolicy = new ProductTypeEventPolicy();
			ilcRegisterCancelPolicy.setEvent(ilcRegEvt);
			ilcRegisterCancelPolicy.setPolicyName("REGISTER CANCELLATION POLICY");
			ilcRegisterCancelPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCancellationPolicy");
			ilcRegisterCancelPolicy.setInputQueueType(false);
			ilcRegisterCancelPolicy.setUpdateQueueType(false);
			ilcRegisterCancelPolicy.setApprovalQueueType(false);
			ilcRegisterCancelPolicy.setCancelQueueType(true);

			registerPolicyList.add(ocpRegisterCreatePolicy);
			registerPolicyList.add(ocpRegisterUpdatePolicy);
			registerPolicyList.add(ocpRegisterApprovalPolicy);
			registerPolicyList.add(ocpRegisterCancelPolicy);
			registerPolicyList.add(ilcRegisterCreatePolicy);
			registerPolicyList.add(ilcRegisterUpdatePolicy);
			registerPolicyList.add(ilcRegisterApprovalPolicy);
			registerPolicyList.add(ilcRegisterCancelPolicy);

			if (policyRepo.count() == 0)
				policyRepo.saveAll(registerPolicyList);
			Notification.show("Product Type Event Saved");
		});
		
		loadProductType.addClickListener(event -> {
			List<ProductModule> productModuleList = productModuleRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<ProductType> productTypeList = new ArrayList<>();
			ProductType ilcProductType = new ProductType();
			ilcProductType.setCode("ILC");
			ilcProductType.setProductType("Import Letter of Credit");
			ilcProductType
					.setModule(productModuleList.stream().filter(i -> i.getCode().endsWith("LC")).findFirst().get());
			productTypeList.add(ilcProductType);

			ProductType elcProductType = new ProductType();
			elcProductType.setCode("ELC");
			elcProductType.setProductType("Export Letter of Credit");
			elcProductType
					.setModule(productModuleList.stream().filter(i -> i.getCode().equals("LC")).findFirst().get());
			productTypeList.add(elcProductType);

			ProductType icProductType = new ProductType();
			icProductType.setCode("ICP");
			icProductType.setProductType("Inward Customer Payment");
			icProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(icProductType);

			ProductType ocProductType = new ProductType();
			ocProductType.setCode("OCP");
			ocProductType.setProductType("Outward Customer Payment");
			ocProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(ocProductType);

			ProductType ibProductType = new ProductType();
			ibProductType.setCode("IBP");
			ibProductType.setProductType("Inward Bank Payment");
			ibProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(ibProductType);

			ProductType obProductType = new ProductType();
			obProductType.setCode("OBP");
			obProductType.setProductType("Outward Bank Payment");
			obProductType.setModule(productModuleList.stream().filter(i -> i.getCode().equals("FT")).findFirst().get());
			productTypeList.add(obProductType);

			if (productTypeRepo.count() == 0)
				productTypeRepo.saveAll(productTypeList);
			Notification.show("Product Types Saved");
		});

		loadPolicy.addClickListener(event -> {
			ProductTypeEvent ocpRegEvt = productTypeEventRepo.findById(1L).get();
			ProductTypeEvent ilcRegEvt = productTypeEventRepo.findById(4L).get();

			Set<ProductTypeEventPolicy> registerPolicyList = new HashSet();
			ProductTypeEventPolicy ocpRegisterCreatePolicy = new ProductTypeEventPolicy();
			ocpRegisterCreatePolicy.setEvent(ocpRegEvt);
			ocpRegisterCreatePolicy.setEventId(ocpRegEvt.getId());
			ocpRegisterCreatePolicy.setPolicyName("REGISTER CREATE POLICY");
			ocpRegisterCreatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCreatePolicy");
			ocpRegisterCreatePolicy.setInputQueueType(true);
			ocpRegisterCreatePolicy.setUpdateQueueType(false);
			ocpRegisterCreatePolicy.setApprovalQueueType(false);
			ocpRegisterCreatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ocpRegisterUpdatePolicy = new ProductTypeEventPolicy();
			ocpRegisterUpdatePolicy.setEvent(ocpRegEvt);
			ocpRegisterUpdatePolicy.setEventId(ocpRegEvt.getId());
			ocpRegisterUpdatePolicy.setPolicyName("REGISTER UPDATE POLICY");
			ocpRegisterUpdatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterUpdatePolicy");
			ocpRegisterUpdatePolicy.setInputQueueType(false);
			ocpRegisterUpdatePolicy.setUpdateQueueType(true);
			ocpRegisterUpdatePolicy.setApprovalQueueType(false);
			ocpRegisterUpdatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ocpRegisterApprovalPolicy = new ProductTypeEventPolicy();
			ocpRegisterApprovalPolicy.setEvent(ocpRegEvt);
			ocpRegisterApprovalPolicy.setEventId(ocpRegEvt.getId());
			ocpRegisterApprovalPolicy.setPolicyName("REGISTER APPROVAL POLICY");
			ocpRegisterApprovalPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterApprovalPolicy");
			ocpRegisterApprovalPolicy.setInputQueueType(false);
			ocpRegisterApprovalPolicy.setUpdateQueueType(true);
			ocpRegisterApprovalPolicy.setApprovalQueueType(true);
			ocpRegisterApprovalPolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ocpRegisterCancelPolicy = new ProductTypeEventPolicy();
			ocpRegisterCancelPolicy.setEvent(ocpRegEvt);
			ocpRegisterCancelPolicy.setEventId(ocpRegEvt.getId());
			ocpRegisterCancelPolicy.setPolicyName("REGISTER CANCELLATION POLICY");
			ocpRegisterCancelPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCancellationPolicy");
			ocpRegisterCancelPolicy.setInputQueueType(false);
			ocpRegisterCancelPolicy.setUpdateQueueType(false);
			ocpRegisterCancelPolicy.setApprovalQueueType(false);
			ocpRegisterCancelPolicy.setCancelQueueType(true);

			ProductTypeEventPolicy ilcRegisterCreatePolicy = new ProductTypeEventPolicy();
			ilcRegisterCreatePolicy.setEvent(ilcRegEvt);
			ilcRegisterCreatePolicy.setEventId(ilcRegEvt.getId());
			ilcRegisterCreatePolicy.setPolicyName("REGISTER CREATE POLICY");
			ilcRegisterCreatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCreatePolicy");
			ilcRegisterCreatePolicy.setInputQueueType(true);
			ilcRegisterCreatePolicy.setUpdateQueueType(false);
			ilcRegisterCreatePolicy.setApprovalQueueType(false);
			ilcRegisterCreatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ilcRegisterUpdatePolicy = new ProductTypeEventPolicy();
			ilcRegisterUpdatePolicy.setEvent(ilcRegEvt);
			ilcRegisterUpdatePolicy.setEventId(ilcRegEvt.getId());
			ilcRegisterUpdatePolicy.setPolicyName("REGISTER UPDATE POLICY");
			ilcRegisterUpdatePolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterUpdatePolicy");
			ilcRegisterUpdatePolicy.setInputQueueType(false);
			ilcRegisterUpdatePolicy.setUpdateQueueType(true);
			ilcRegisterUpdatePolicy.setApprovalQueueType(false);
			ilcRegisterUpdatePolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ilcRegisterApprovalPolicy = new ProductTypeEventPolicy();
			ilcRegisterApprovalPolicy.setEvent(ilcRegEvt);
			ilcRegisterApprovalPolicy.setEventId(ilcRegEvt.getId());
			ilcRegisterApprovalPolicy.setPolicyName("REGISTER APPROVAL POLICY");
			ilcRegisterApprovalPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterApprovalPolicy");
			ilcRegisterApprovalPolicy.setInputQueueType(false);
			ilcRegisterApprovalPolicy.setUpdateQueueType(true);
			ilcRegisterApprovalPolicy.setApprovalQueueType(true);
			ilcRegisterApprovalPolicy.setCancelQueueType(false);

			ProductTypeEventPolicy ilcRegisterCancelPolicy = new ProductTypeEventPolicy();
			ilcRegisterCancelPolicy.setEvent(ilcRegEvt);
			ilcRegisterCancelPolicy.setEventId(ilcRegEvt.getId());
			ilcRegisterCancelPolicy.setPolicyName("REGISTER CANCELLATION POLICY");
			ilcRegisterCancelPolicy.setPolicyClass("com.princeps.app.backend.policy.RegisterCancellationPolicy");
			ilcRegisterCancelPolicy.setInputQueueType(false);
			ilcRegisterCancelPolicy.setUpdateQueueType(false);
			ilcRegisterCancelPolicy.setApprovalQueueType(false);
			ilcRegisterCancelPolicy.setCancelQueueType(true);

			registerPolicyList.add(ocpRegisterCreatePolicy);
			registerPolicyList.add(ocpRegisterUpdatePolicy);
			registerPolicyList.add(ocpRegisterApprovalPolicy);
			registerPolicyList.add(ocpRegisterCancelPolicy);
			registerPolicyList.add(ilcRegisterCreatePolicy);
			registerPolicyList.add(ilcRegisterUpdatePolicy);
			registerPolicyList.add(ilcRegisterApprovalPolicy);
			registerPolicyList.add(ilcRegisterCancelPolicy);

			if (policyRepo.count() == 0)
				policyRepo.saveAll(registerPolicyList);
			Notification.show("Product Type Event Saved");
		});

		loadCSV.addClickListener(event -> {
			List<Country> countryList = getCountryListFromCsv();
			List<Currency> currencyList = getCurrencyListFromCsv();

			if (!countryList.isEmpty()) {
				ctryRepo.saveAll(countryList);
				Notification.show(countryList.size() + " Country records saved");
			}
			if (!currencyList.isEmpty()) {
				ccyRepo.saveAll(currencyList);
				Notification.show(currencyList.size() + " Currency records saved");
			}
		});

		add(loadConfigData, loadProductModule, loadProductType, loadProductTypeEvent,loadPolicy, loadCSV);
	}

	@Override
	public void loadComponents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reloadView() {

	}

	@Override
	public boolean getAddPermission() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean getEditPermission() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean getViewPermission() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void navigationAction(String param) {
		getUI().ifPresent(ui -> ui.navigate("nav"));

	}

	@Override
	public String getDialogTitle() {
		// TODO Auto-generated method stub
		return "";
	}

	public List<Country> getCountryListFromCsv() {
		List<Country> list = new ArrayList<>();
		if (!(ctryRepo.count() > 0))
			return list;
		InputStream stream = getClass().getClassLoader().getResourceAsStream("setup/country.csv");
		
		if (stream == null) {
            Notification.show("File not found: " + "setup/country.csv");
            //return records;
        }
	
		CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
		try {
			String[] lineInArray;
			while ((lineInArray = reader.readNext()) != null) {
				Country c = new Country();
				c.setCode(lineInArray[1]);
				c.setCountryName(lineInArray[0].trim());
				list.add(c);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	public List<Currency> getCurrencyListFromCsv() {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Currency> list = new ArrayList<>();
		System.out.println(ccyRepo.count());
		if (ccyRepo.count() > 0)
			return list;
		InputStream stream = getClass().getClassLoader().getResourceAsStream("setup/currency.csv");
		CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
		List<Country> countryList = ctryRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		try {
			String[] lineInArray;
			while ((lineInArray = reader.readNext()) != null) {
				Currency c = new Currency();
				c.setCode(lineInArray[3]);
				c.setCurrency(lineInArray[2].trim());
				String countryCode = lineInArray[1].trim();
				Country country = countryList.stream().filter(ctry -> ctry.getCode().trim().equals(countryCode.trim())).findFirst()
						.orElse(null);
				if (null != country) {
					c.setCountryId(country.getId());
					list.add(c);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stream.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
