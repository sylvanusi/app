package com.more.app.base.ui.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Parameters;
import com.more.app.repository.ParametersRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextField.TextFieldI18n;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;


@Route(value = ParametersCrudView.pageUrlString, layout = CrudPageView.class)
public class ParametersCrudView extends BaseCrudComponent<Parameters> implements HasUrlParameter<String> {
	public static final String pageUrlString = "parameters";
	private static final long serialVersionUID = -7910699565210806753L;
	private Binder<Parameters> binder = new Binder<>(Parameters.class);

	@Autowired
	private ParametersRepository repo;

	public ParametersCrudView() {
		super();
		vl.add(paramCodeTF, paramValueTF, paramDescriptionTA);
	}

	private TextField paramCodeTF, paramValueTF;
	private TextArea paramDescriptionTA;

	@Override
	public void setSelectedEntity(AbstractPojo pojo) {

	}

	@Override
	public void clearSelectedEntity(Parameters pojo) {

	}

	@Override
	public void initializeComponents() {
		paramCodeTF = new TextField();
		paramCodeTF.setMaxLength(5);
		paramCodeTF.setRequired(true);
		paramCodeTF.setRequiredIndicatorVisible(true);

		paramValueTF = new TextField();
		paramValueTF.setMaxLength(35);
		paramValueTF.setWidth("350px");
		paramValueTF.setRequired(true);
		paramValueTF.setRequiredIndicatorVisible(true);

		paramDescriptionTA = new TextArea();
		paramDescriptionTA.setMaxLength(140);
		paramDescriptionTA.setWidth("350px");
		paramDescriptionTA.setRequired(true);
		paramDescriptionTA.setRequiredIndicatorVisible(true);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		if (entity != null) {
			binder.setBean(entity);
			paramCodeTF.setLabel(UIActionUtil.getFieldLabel(entity, "paramCode"));
			paramValueTF.setLabel(UIActionUtil.getFieldLabel(entity, "paramValue"));
			paramDescriptionTA.setLabel(UIActionUtil.getFieldLabel(entity, "paramDescription"));

			binder.forField(paramCodeTF).asRequired("Param Code is required").bind("paramCode");
			binder.forField(paramValueTF).asRequired("Param Value is required").bind("paramValue");
			binder.forField(paramDescriptionTA).asRequired("Param Description is required").bind("paramDescription");
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT")) {
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	@Override
	public boolean fullsize() {
		return false;
	}

	@Override
	public String getPageTitle() {
		return "Parameters";
	}

	@Override
	public String getPageMode() {
		return pageMode;
	}

	@Override
	public Binder<Parameters> getBinder() {
		return binder;
	}

	@Override
	public Parameters getEntity() {
		return entity;
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String parameter)
	{
		if (parameter != null && !parameter.isEmpty())
		{
			String params[] = parameter.split(",");
			pageMode = params[0];
			setPageMode(pageMode);
			if ("ADD".equals(pageMode))
				entity = new Parameters();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
				entity = repo.findById(id).orElse(null);
			}
		}
		else
			getUI().get().navigate(ParametersView.class);
	}

	@Override
	public Class<ParametersView> getCloseNavigationClass() {
		return ParametersView.class;
	}

	@Override
	public void saveRecord(Parameters entity) {
		repo.save(entity);
		
	}
}
