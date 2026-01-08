package com.more.app.util;

import java.time.LocalDate;

public class NullCheckUtil
{
	public static boolean checkNullAndEmptyString(String value)
	{
		return null != value && !value.trim().isEmpty();
	}
	
	public static boolean checkNullAndEmptyLocalDate(LocalDate value)
	{
		return null != value;
	}
	
	public static boolean checkNullBooleanValue(Boolean value)
	{
		return !(value.equals(Boolean.TRUE) ||value.equals(Boolean.FALSE));
	}

	public static boolean checkNullObjectValue(Object value)
	{
		return null != value;
	}
	
}
