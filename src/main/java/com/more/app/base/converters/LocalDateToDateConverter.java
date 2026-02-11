package com.more.app.base.converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class LocalDateToDateConverter implements Converter<LocalDate, Date>
{

	private static final long serialVersionUID = 1L;

	@Override
	public Result<Date> convertToModel(LocalDate presentation, ValueContext valueContext)
	{
		return Result.ok(java.util.Date.from(presentation.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
	}

	@Override
	public LocalDate convertToPresentation(Date model, ValueContext valueContext)
	{
		return model.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}