package com.more.app.base.ui;

import com.more.app.base.ui.configuration.ChargeDefinationView;
import com.more.app.base.ui.configuration.InitialDataView;
import com.more.app.base.ui.configuration.ProductConfigurationView;
import com.more.app.base.ui.configuration.ProductModuleView;
import com.more.app.base.ui.configuration.ProductTypeEventView;
import com.more.app.base.ui.configuration.ProductTypeView;
import com.more.app.base.ui.product.LcIssueView;
import com.more.app.base.ui.product.LcMasterView;
import com.more.app.base.ui.product.MessagesView;
import com.more.app.base.ui.product.OcpView;
import com.more.app.base.ui.product.PartyView;
import com.more.app.base.ui.product.RegisterView;
import com.more.app.base.ui.product.ReportView;
import com.more.app.base.ui.security.AppRoleView;
import com.more.app.base.ui.security.PermissionEntityView;
import com.more.app.base.ui.security.UsersView;
import com.more.app.base.ui.setup.AccountOfficerView;
import com.more.app.base.ui.setup.AccountTypeView;
import com.more.app.base.ui.setup.AccountView;
import com.more.app.base.ui.setup.BankView;
import com.more.app.base.ui.setup.BranchView;
import com.more.app.base.ui.setup.CountryView;
import com.more.app.base.ui.setup.CurrencyView;
import com.more.app.base.ui.setup.CustomerView;
import com.more.app.base.ui.setup.HolidayView;
import com.more.app.base.ui.setup.ParametersView;
import com.more.app.base.ui.setup.SwiftBicView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;

@Route(value = "nav", layout = BaseLayout.class)
public class NavigationPlatform extends VerticalLayout {

	private static final long serialVersionUID = -6366180042506456913L;

	public NavigationPlatform() {

		Button logoutBtn = new Button("logout");
		logoutBtn.addClickListener(e -> {
			getUI().get().navigate("");

		});

		H4 hMenu = new H4();
		hMenu.setText("Application Menu");
		hMenu.getElement().getStyle().set("color", "blue");

		Hr hr = new Hr();
		hr.setWidthFull();
		hr.getElement().getStyle().set("border-top", "1.0px solid #000000");

		SideNavItem setUpSection = new SideNavItem("Set Up", MessagesView.class);
		setUpSection.addItem(new SideNavItem("Parameter", ParametersView.class));
		setUpSection.addItem(new SideNavItem("Country", CountryView.class));
		setUpSection.addItem(new SideNavItem("Currency", CurrencyView.class));
		setUpSection.addItem(new SideNavItem("Bank", BankView.class));
		setUpSection.addItem(new SideNavItem("Branch", BranchView.class));
		setUpSection.addItem(new SideNavItem("Holiday", HolidayView.class));
		setUpSection.addItem(new SideNavItem("Swift Bic", SwiftBicView.class));
		setUpSection.addItem(new SideNavItem("Account Officer", AccountOfficerView.class));
		setUpSection.addItem(new SideNavItem("Customer", CustomerView.class));
		setUpSection.addItem(new SideNavItem("Account Type", AccountTypeView.class));
		setUpSection.addItem(new SideNavItem("Account", AccountView.class));
		setUpSection.setExpanded(true);

		SideNavItem securitySection = new SideNavItem("Security");
		securitySection.addItem(new SideNavItem("Users", UsersView.class));
		securitySection.addItem(new SideNavItem("User Roles", AppRoleView.class));
		securitySection.addItem(new SideNavItem("Permissions", PermissionEntityView.class));
		securitySection.setExpanded(true);

		SideNavItem productConfigSection = new SideNavItem("Configuration");
		productConfigSection.addItem(new SideNavItem("Load Initial Data", InitialDataView.class));
		productConfigSection.addItem(new SideNavItem("Module", ProductModuleView.class));
		productConfigSection.addItem(new SideNavItem("Product Type", ProductTypeView.class));
		productConfigSection.addItem(new SideNavItem("Event", ProductTypeEventView.class));
		productConfigSection.addItem(new SideNavItem("Charge SetUp", ChargeDefinationView.class));
		productConfigSection.addItem(new SideNavItem("Product SetUp", ProductConfigurationView.class));
		productConfigSection.setExpanded(true);

		SideNavItem productModuleSection = new SideNavItem("Product");
		productModuleSection.addItem(new SideNavItem("Party", PartyView.class));
		productModuleSection.addItem(new SideNavItem("Product Register", RegisterView.class));
		productModuleSection.addItem(new SideNavItem("Letter of Credit", LcMasterView.class));
		productModuleSection.addItem(new SideNavItem("Outward Payment", OcpView.class));
		productModuleSection.addItem(new SideNavItem("Letter Issue", LcIssueView.class));
		productModuleSection.setExpanded(true);

		SideNavItem reportSection = new SideNavItem("Report");
		reportSection.addItem(new SideNavItem("Outward Payment Report", ReportView.class));
		reportSection.addItem(new SideNavItem("LC Reportt", ReportView.class));
		reportSection.setExpanded(true);

		VerticalLayout v1 = new VerticalLayout();
		v1.setSpacing(false);
		v1.setMargin(false);
		v1.add(setUpSection);

		VerticalLayout v2 = new VerticalLayout();
		v2.setSpacing(false);
		v2.setMargin(false);
		v2.add(securitySection);

		VerticalLayout v3 = new VerticalLayout();
		v3.setSpacing(false);
		v3.setMargin(false);
		v3.add(productConfigSection);

		VerticalLayout v4 = new VerticalLayout();
		v4.setSpacing(false);
		v4.setMargin(false);
		v4.add(productModuleSection);

		VerticalLayout v5 = new VerticalLayout();
		v5.setSpacing(false);
		v5.setMargin(false);
		v5.add(reportSection);

		HorizontalLayout hor1 = new HorizontalLayout();
		hor1.setSpacing(false);
		hor1.setMargin(false);
		hor1.setSizeFull();
		hor1.add(v1, v2, v3, v4, v5);

		v1.getElement().getStyle().set("background-color", "white");
		v2.getElement().getStyle().set("background-color", "white");
		v3.getElement().getStyle().set("background-color", "white");
		v4.getElement().getStyle().set("background-color", "white");
		v5.getElement().getStyle().set("background-color", "white");

		HorizontalLayout hmenubar = new HorizontalLayout();
		hmenubar.setPadding(false);
		hmenubar.setMargin(false);
		hmenubar.setSpacing(true);
		hmenubar.setSizeFull();
		hmenubar.add(hMenu, logoutBtn);
		hmenubar.setFlexGrow(4, hMenu);

		setSpacing(false);
		setMargin(false);
		add(hmenubar, hr, hor1);
	}
}
