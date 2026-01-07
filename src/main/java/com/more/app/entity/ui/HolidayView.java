package com.more.app.entity.ui;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.annotations.UIActionUtil;
import com.more.app.base.ui.BaseView;
import com.more.app.base.ui.DialogSelectEntity;
import com.more.app.base.ui.LeftAlignedLayout;
import com.more.app.entity.Holiday;
import com.more.app.repository.HolidayRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = HolidayView.pageUrlString, layout = LeftAlignedLayout.class)
public class HolidayView extends BaseView<Holiday>
{
	private static final long serialVersionUID = -4232596535128360059L;
	public static final String pageUrlString = "holiday-view";
	public static final Long resourceAdd = Long.valueOf(25L);
	public static final Long resourceEdit = Long.valueOf(26L);
	public static final Long resourceView = Long.valueOf(27L);

	private TextField titleTF;
	private DatePicker holidayDateDF;

	private HolidayView view;
	private HolidayCrudView cdg;
	
	@Autowired
	private HolidayRepository repository;

	public HolidayView()
	{
		super();
		view = this;
	}

	public HolidayView(DialogSelectEntity dialogSelectEntity, Dialog dg)
	{
		super(dialogSelectEntity, dg);
	}
	public <T> HolidayView(DialogSelectEntity dialog, Dialog dg, JpaRepository repository)
	{
		super(dialog, dg, repository);
		this.repository = (HolidayRepository) repository;
	}


	public void loadComponents()
	{
		titleTF = new TextField(UIActionUtil.getFieldLabel(Holiday.class, "title"));
		holidayDateDF = new DatePicker(UIActionUtil.getFieldLabel(Holiday.class, "holidayDateString"));

		grid.addColumn(Holiday::getTitle).setHeader(UIActionUtil.getFieldLabel(Holiday.class, "title"));
		grid.addColumn(Holiday::getHolidayDateString)
				.setHeader(UIActionUtil.getFieldLabel(Holiday.class, "holidayDate"));
		grid.addColumn(Holiday::isRecurring).setHeader(UIActionUtil.getFieldLabel(Holiday.class, "recurring"));
		grid.addColumn(Holiday::isBranchHoliday).setHeader(UIActionUtil.getFieldLabel(Holiday.class, "branchHoliday"));
		grid.addColumn(Holiday::getBranchName).setHeader(UIActionUtil.getFieldLabel(Holiday.class, "branchName"));

		hlsearch.add(titleTF, holidayDateDF, searchb);
		hlsearch.setVerticalComponentAlignment(Alignment.BASELINE, titleTF, holidayDateDF, searchb);

		addBaseComponentsandStyle();

		searchb.addClickListener(event ->
		{
			grid.setItems(new ArrayList<Holiday>());
			String title = titleTF.getValue();
			LocalDate holidayDate = holidayDateDF.getValue();
			grid.setItems(repository.findByTitleStartsWithAndHolidayDateStartsWith(title, holidayDate));
		});
	}

	public void reloadView()
	{
		grid.setItems(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent)
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
		getUI().ifPresent(ui -> ui.navigate(HolidayCrudView.class, param));
	}

	@Override
	public String getDialogTitle()
	{
		return "Search Holiday";
	}
}
