package com.more.app.base.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleToIntegerConverter implements Converter<Double, Integer>
{

	private static final long serialVersionUID = 1L;

	@Override
	public Result<Integer> convertToModel(Double presentation, ValueContext valueContext)
	{
		if (null != presentation)
			return Result.ok(presentation.intValue());
		else
			return Result.ok(0);
	}

	@Override
	public Double convertToPresentation(Integer model, ValueContext valueContext)
	{
		if (null != model)
			return model.doubleValue();
		else
			return null;
	}
}
