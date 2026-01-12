package com.more.app.entity.enums.converters;

import com.more.app.entity.enums.ReferenceNumberItem;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ReferenceNumberItemsConverter implements AttributeConverter<ReferenceNumberItem, String>
{
	@Override
	public String convertToDatabaseColumn(ReferenceNumberItem item)
	{
		switch (item)
		{
			case MODULE_CODE:
				return "MC";
			case PRODUCT_TYPE_CODE:
				return "PTC";
			case PRODUCT_CODE:
				return "PC";
			case TRANS_CCY:
				return "CCY";
			case YEAR_YY:
				return "YY";
			case YEAR_YYYY:
				return "YYYY";
			case DAY_IN_YR:
				return "DAY";
			case PROCESSING_DT:
				return "PR_DATE";
			case BRANCH_CODE:
				return "BC";
			case BRANCH_NO:
				return "BN";
			//case UNIQUE_CODE:
			//	return "UC";
			case SEQUENCE_NO:
				return "SEQ_NO";
			default:
				throw new IllegalArgumentException("Unknown" + item);
		}
	}

	@Override
	public ReferenceNumberItem convertToEntityAttribute(String dbData)
	{
		switch (dbData)
		{
			case "MC":
				return ReferenceNumberItem.MODULE_CODE;
			case "PTC":
				return ReferenceNumberItem.PRODUCT_TYPE_CODE;
			case "PC":
				return ReferenceNumberItem.PRODUCT_CODE;
			case "CCY":
				return ReferenceNumberItem.TRANS_CCY;
			case "YY":
				return ReferenceNumberItem.YEAR_YY;
			case "YYYY":
				return ReferenceNumberItem.YEAR_YYYY;
			case "DAY":
				return ReferenceNumberItem.DAY_IN_YR;
			case "PR_DATE":
				return ReferenceNumberItem.PROCESSING_DT;
			case "BC":
				return ReferenceNumberItem.BRANCH_CODE;
			case "BN":
				return ReferenceNumberItem.BRANCH_NO;
			//case "UC":
			//	return ReferenceNumberItem.UNIQUE_CODE;
			case "SEQ_NO":
				return ReferenceNumberItem.SEQUENCE_NO;
	
			default:
				throw new IllegalArgumentException("Unknown" + dbData);
		}
	}
}
