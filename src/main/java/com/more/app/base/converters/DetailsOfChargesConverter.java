package com.more.app.base.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DetailsOfChargesConverter implements Converter<String, String>
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public Result<String> convertToModel(String presentation, ValueContext valueContext)
	{
		String value = "";
		if (null == presentation)
			return Result.ok(value);
		if (presentation.equals("Our Customer Charged"))
			value = "OUR";
		if (presentation.equals("Beneficiary"))
			value = "BEN";
		if (presentation.equals("Shared Charges"))
			value = "SHA";
		return Result.ok(value);
	}

	@Override
	public String convertToPresentation(String model, ValueContext valueContext)
	{
		// "Beneficiary", "Our Customer Charged", "Shared Charges"
		String value = "";
		if (null != model)
		{
			if (model.equals("OUR"))
				value = "Our Customer Charged";
			if (model.equals("BEN"))
				value = "Beneficiary";
			if (model.equals("SHA"))
				value = "Shared Charges";
		}

		return value;
	}
}
