package com.more.app.base.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal>
{

	private static final long serialVersionUID = 1L;

	@Override
	public Result<BigDecimal> convertToModel(Double presentation, ValueContext valueContext)
	{
		if (null != presentation)
			return Result.ok(BigDecimal.valueOf(presentation).setScale(2, RoundingMode.HALF_DOWN));
		else
			return Result.ok(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN));
	}

	@Override
	public Double convertToPresentation(BigDecimal model, ValueContext valueContext)
	{
		if (null != model)
			return model.doubleValue();
		else
			return null;
	}
}
