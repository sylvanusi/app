package com.more.app.base.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class FormOfDocumentaryCreditConverter implements Converter<String, String>
{
	private static final long serialVersionUID = 1L;

	//"IRREVOCABLE", "IRREVOCABLE TRANSFERABLE", "IRREVOCABLE STANDBY", "IRREVOC TRANS STANDBY"
	//"Irrevocable", "Irrevocable and Transferable", "Irrevocable Standby LC", "Irrevocable Transferable Standby LC"
	@Override
	public Result<String> convertToModel(String presentation, ValueContext valueContext)
	{
		String value = "";
		if (null == presentation)
			return Result.ok(value);
		if (presentation.equals("Irrevocable"))
			value = "IRREVOCABLE";
		if (presentation.equals("Irrevocable and Transferable"))
			value = "IRREVOCABLE TRANSFERABLE";
		if (presentation.equals("Irrevocable Standby LC"))
			value = "IRREVOCABLE STANDBY";
		if (presentation.equals("Irrevocable Transferable Standby LC"))
			value = "IRREVOC TRANS STANDBY";
		return Result.ok(value);
	}

	@Override
	public String convertToPresentation(String model, ValueContext valueContext)
	{
		// "Beneficiary", "Our Customer Charged", "Shared Charges"
		String value = "";
		if (null != model)
		{
			if (model.equals("IRREVOCABLE"))
				value = "Irrevocable";
			if (model.equals("IRREVOCABLE TRANSFERABLE"))
				value = "Irrevocable and Transferable";
			if (model.equals("IRREVOCABLE STANDBY"))
				value = "Irrevocable Standby LC";
			if (model.equals("IRREVOC TRANS STANDBY"))
				value = "Irrevocable Transferable Standby LC";
		}

		return value;
	}
}
