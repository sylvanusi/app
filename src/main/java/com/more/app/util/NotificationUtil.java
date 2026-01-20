package com.more.app.util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NotificationUtil {
	public static Notification createSubmitSuccess(String message) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

		Icon icon = VaadinIcon.CHECK_CIRCLE.create();

		Button viewBtn = new Button("View");
		viewBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

		var layout = new HorizontalLayout(icon, new Text(message));
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);

		return notification;
	}

	public static Notification createReportError(String message) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

		Icon icon = VaadinIcon.WARNING.create();
		Button retryBtn = new Button("Retry");
		retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

		var layout = new HorizontalLayout(icon, new Text(message), retryBtn, createCloseBtn());
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);

		return notification;
	}


	public static Notification createUploadSuccess() {
		Notification notification = new Notification();

		Icon icon = VaadinIcon.CHECK_CIRCLE.create();
		icon.setColor("var(--lumo-success-color)");

		Div uploadSuccessful = new Div(new Text("Upload successful"));
		uploadSuccessful.getStyle().set("font-weight", "600").setColor("var(--lumo-success-text-color)");

		Span fileName = new Span("Financials.xlsx");
		fileName.getStyle().set("font-size", "0.875rem").set("font-weight", "600");

		Div info = new Div(uploadSuccessful,
				new Div(fileName, new Text(" is now available in "), new Anchor("#", "Documents")));

		info.getStyle().set("font-size", "0.875rem").setColor("var(--lumo-secondary-text-color)");

		var layout = new HorizontalLayout(icon, info, createCloseBtn());
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);

		return notification;
	}
	
	public static void createSavedNotification(String message)
	{
		Notification notification = new Notification();

		Div text = new Div(new Text(message));

		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.setAriaLabel("Close");
		closeButton.addClickListener(event -> {
		    notification.close();
		});

		HorizontalLayout layout = new HorizontalLayout(text, closeButton);
		layout.setAlignItems(Alignment.CENTER);
		
		notification.setPosition(Position.TOP_CENTER);
	    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);


		notification.add(layout);
		notification.open();
	}

	public static Button createCloseBtn() {
		Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create());
		closeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

		return closeBtn;
	}
}
