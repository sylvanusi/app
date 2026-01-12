package com.more.app.entity.enums;

public enum ReferenceNumberItem
{
	//UNIQUE_CODE(4,"Unique Code"),
	MODULE_CODE(2,"Module Code"),
	PRODUCT_TYPE_CODE(3,"Product Type Code"),
	PRODUCT_CODE(4,"Product Code"),
	TRANS_CCY(3,"Transaction Currency"),
	YEAR_YY(2,"Year (YY)"),
	YEAR_YYYY(4,"Year (YYYY)"),
	DAY_IN_YR(3,"Day in Year"),
	PROCESSING_DT(6,"Processing Date"),
	BRANCH_CODE(4,"Branch Code"),
	BRANCH_NO(4,"Branch No"),
	SEQUENCE_NO(16,"Sequence No");
	
	private final int length;
	private final String description;
	
	ReferenceNumberItem(int length, String description)
	{
		this.length = length;
		this.description = description;
	}
	
	public int getLength()
	{
		return length;
	}

	public String getDescription()
	{
		return description;
	}
	
}
