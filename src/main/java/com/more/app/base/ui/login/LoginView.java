package com.more.app.base.ui.login;

import com.more.app.base.ui.BaseLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "", layout = BaseLayout.class)
public class LoginView extends Div {
	public LoginView() {
		// See login-rich-content.css
		// addClassName("login-rich-content");
		// VerticalLayout vl = new VerticalLayout();
		LoginForm loginForm = new LoginForm();
		loginForm.getElement().getThemeList().add("dark");
		add(loginForm);
		// Prevent the example from stealing focus when browsing the
		// documentation
		loginForm.getElement().setAttribute("no-autofocus", "");
		loginForm.getElement().getStyle().set("background-color", "white");

		loginForm.addLoginListener(e -> {
			VaadinSession.getCurrent().setAttribute("currentUser", "Admin");
			getUI().get().navigate("nav");

		});
	}
}
