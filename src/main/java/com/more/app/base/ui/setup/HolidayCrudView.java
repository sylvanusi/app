package com.more.app.base.ui.setup;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.base.ui.BaseCrudComponent;
import com.more.app.base.ui.CrudPageView;
import com.more.app.entity.AbstractPojo;
import com.more.app.entity.Branch;
import com.more.app.entity.Holiday;
import com.more.app.repository.BranchRepository;
import com.more.app.repository.HolidayRepository;
import com.more.app.util.annotations.UIActionUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = HolidayCrudView.pageUrlString, layout = CrudPageView.class)
public class HolidayCrudView extends BaseCrudComponent<Holiday> implements HasUrlParameter<String>
{
	private static final long serialVersionUID = -5511035586185573430L;
	public static final String pageUrlString = "holiday";	
	private Branch branch;
	private Binder<Holiday> binder = new Binder<>(Holiday.class);
	

	@Autowired
	private HolidayRepository repository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	public HolidayCrudView()
	{
		super();
		Button getBranch = new Button("Get Branch", new Icon(VaadinIcon.SEARCH), event ->
		{
			Dialog dg = new Dialog();
			BranchView v = new BranchView(ui, dg,branchRepository);
			dg.add(v);
			dg.open();
		});

		HorizontalLayout branchl = new HorizontalLayout();
		branchl.add(branchNameTF, getBranch);
		branchl.setVerticalComponentAlignment(Alignment.BASELINE, branchNameTF, getBranch);

		vl.add(titleTF, recurringCB, holidayDateDP, branchHolidayCB, branchl);
	}

	private TextField titleTF, branchNameTF;
	private Checkbox recurringCB, branchHolidayCB;
	private DatePicker holidayDateDP;

	@Override
	public void initializeComponents()
	{
		titleTF = new TextField();
		titleTF.setMaxLength(50);
		titleTF.setMinWidth("300px");
		titleTF.setRequired(true);
		titleTF.setRequiredIndicatorVisible(true);

		holidayDateDP = new DatePicker();
		holidayDateDP.setRequired(true);
		holidayDateDP.setRequiredIndicatorVisible(true);

		recurringCB = new Checkbox();

		branchHolidayCB = new Checkbox();

		branchNameTF = new TextField();
		branchNameTF.setMaxLength(35);
		branchNameTF.setMinWidth("300px");
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
	{
		if (entity != null)
		{
			binder.setBean(entity);
			titleTF.setLabel(UIActionUtil.getFieldLabel(entity, "title"));
			holidayDateDP.setLabel(UIActionUtil.getFieldLabel(entity, "holidayDate"));
			recurringCB.setLabel(UIActionUtil.getFieldLabel(entity, "recurring"));
			branchNameTF.setLabel(UIActionUtil.getFieldLabel(entity, "branchName"));
			branchHolidayCB.setLabel(UIActionUtil.getFieldLabel(entity, "branchHoliday"));
			
			binder.forField(titleTF).asRequired().bind("title");
			binder.forField(holidayDateDP).asRequired().bind("holidayDate");
			binder.forField(recurringCB).bind("recurring");
			binder.forField(branchHolidayCB).bind("branchHoliday");
			binder.forField(branchNameTF).bind("branchName");
		}
		if (getPageMode().equals("ADD") || getPageMode().equals("ADDNEW") || getPageMode().equals("EDIT"))
		{
			confirmButton.setVisible(true);
			addewButton.setVisible(true);
		}
	}

	@Override
	public void setSelectedEntity(AbstractPojo pojo)
	{
		if (pojo instanceof Branch)
		{
			((HolidayCrudView) ui).setBranch((Branch) pojo);
			branchNameTF.setValue(getBranch().getName());
			branchNameTF.setReadOnly(true);
			entity.setBranch(getBranch());
		}
	}
	/**
	 * @return the bank
	 */
	public Branch getBranch()
	{
		return branch;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBranch(Branch branch)
	{
		this.branch = branch;
	}

	@Override
	public void clearSelectedEntity(Holiday pojo)
	{
		pojo.setBranch(null);
		
	}
	@Override
	public boolean fullsize()
	{
		return false;
	}

	@Override
	public String getPageTitle()
	{
		return "Holiday";
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
				entity = new Holiday();
			else
			{
				Long id = Long.valueOf(params[1]);
				if (id == -1L)
					beforeEvent.rerouteTo(getCloseNavigationClass());
				else
					entity = repository.findById(id).orElse(null);
			}
		} else
			getUI().get().navigate(getCloseNavigationClass());
	}

	@Override
	public Class<HolidayView> getCloseNavigationClass()
	{
		return HolidayView.class;
	}

	@Override
	public String getPageMode()
	{
		return pageMode;
	}

	@Override
	public Binder<Holiday> getBinder()
	{
		return binder;
	}

	@Override
	public Holiday getEntity()
	{
		return entity;
	}

	@Override
	public void saveRecord(Holiday entity) {
		repository.save(entity);
	}
}
