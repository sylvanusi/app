package com.more.app.base.ui.security;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.User;
import com.more.app.repository.UserRepository;
import com.more.app.util.PasswordGeneratorUtil;
import com.more.app.util.PasswordUtil;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;

@Route(value = UsersView.pageUrlString, layout = LeftAlignedLayout.class)
public class UsersView extends BaseView<User>
{
	private static final long serialVersionUID = 4156014215920148324L;
	public static final String pageUrlString = "users-view";
	public static final Long resourceAdd = Long.valueOf(1L);
	public static final Long resourceEdit = Long.valueOf(2L);
	public static final Long resourceView = Long.valueOf(3L);

	private TextField usernameTF;
	private TextField nameTF;
	private TextField staffNoTF;
	private TextField emailTF;

	@Autowired
	private UserRepository repository;
	public UsersView()
	{
		super();
		view = this;
	}

	public UsersView(DialogSelectEntity dialogEntity, Dialog dg)
	{
		super(dialogEntity, dg);
	}

	public void loadComponents()
	{

		usernameTF = new TextField(UIActionUtil.getFieldLabel(User.class, "username"));
		usernameTF.setMaxLength(35);

		nameTF = new TextField(UIActionUtil.getFieldLabel(User.class, "name"));
		nameTF.setMaxLength(35);

		staffNoTF = new TextField(UIActionUtil.getFieldLabel(User.class, "staffNo"));
		staffNoTF.setMaxLength(35);

		emailTF = new TextField(UIActionUtil.getFieldLabel(User.class, "email"));
		emailTF.setMaxLength(35);

		grid.addColumn(User::getUsername).setHeader(UIActionUtil.getFieldLabel(User.class, "username"));
		grid.addColumn(User::getName).setHeader(UIActionUtil.getFieldLabel(User.class, "name"));
		grid.addColumn(User::getStaffNo).setHeader(UIActionUtil.getFieldLabel(User.class, "staffNo"));
		grid.addColumn(User::getEmail).setHeader(UIActionUtil.getFieldLabel(User.class, "email"));
		grid.addColumn(User::isAccountLocked).setHeader(UIActionUtil.getFieldLabel(User.class, "accountLocked"));

		grid.addColumn(new NativeButtonRenderer<>("Generate", clickedItem ->
		{

			String password = PasswordGeneratorUtil.generateRandomPassword(8);

			Object[] arr = grid.getSelectedItems().toArray();
			User user = (User) arr[0];

			user.setAccountLocked(false);
			user.clearFailedLoginAttempts();
			user.setReasonForLockedAccount(null);
			user.setPassword(PasswordUtil.generateHashedPassword(password));
			repository.save(user);

			CompletableFuture<Void> a = CompletableFuture.runAsync(() ->
			{
				//JavaMailService.sendPasswordMail(user.getEmail(), password);

			});

			Notification.show("Password Generated and Mail sent to " + user.getEmail(), 7000, Position.TOP_STRETCH);

		})).setHeader("Generate Password");
		grid.addColumn(new NativeButtonRenderer<>("Unlock", clickedItem ->
		{

			

		})).setHeader("Unlock User");

		hlsearch.add(usernameTF, nameTF, staffNoTF, emailTF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, usernameTF, nameTF, staffNoTF, emailTF, searchb);

		addBaseComponentsandStyle();

		// hl.add(unlockUserBtn);

		searchb.addClickListener(event ->
			{
				grid.setItems(new ArrayList<User>());
				String username = usernameTF.getValue();
				String name = nameTF.getValue();
				String staffNo = staffNoTF.getValue();
				String email = emailTF.getValue();
				grid.setItems(repository.findByUsernameStartsWithAndNameStartsWithAndStaffNoStartsWithAndEmailStartsWith(username, name, staffNo, email));
			});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public void reloadView()
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	public boolean getAddPermission()
	{
		return true;
	}

	@Override
	public boolean getEditPermission()
	{
		return true;
	}

	@Override
	public boolean getViewPermission()
	{
		return true;
	}

	@Override
	public void navigationAction(String param)
	{
		getUI().ifPresent(ui -> ui.navigate(UsersCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Users";
	}
}
