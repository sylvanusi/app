package com.more.app.base.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class ConfirmationInstructionConverter implements Converter<String, String>
{
	private static final long serialVersionUID = 1L;

	// "Confirm", "May Add", "Without"
	// "CONFIRM","MAY ADD","WITHOUT"
	@Override
	public Result<String> convertToModel(String presentation, ValueContext valueContext)
	{
		String value = "";
		if (null == presentation)
			return Result.ok(value);
		if (presentation.equals("Confirm"))
			value = "CONFIRM";
		if (presentation.equals("May Add"))
			value = "MAY ADD";
		if (presentation.equals("Without"))
			value = "WITHOUT";
		return Result.ok(value);
	}

	@Override
	public String convertToPresentation(String model, ValueContext valueContext)
	{
		// "Beneficiary", "Our Customer Charged", "Shared Charges"
		String value = "";
		if (null != model)
		{
			if (model.equals("CONFIRM"))
				value = "Confirm";
			if (model.equals("MAY ADD"))
				value = "May Add";
			if (model.equals("WITHOUT"))
				value = "Without";
		}

		return value;
	}
}
