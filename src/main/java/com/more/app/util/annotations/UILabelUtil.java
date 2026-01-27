package com.more.app.util.annotations;

import java.lang.reflect.Field;

public class UILabelUtil
{
	public static String getFieldLabel(Field field)
	{
		field.setAccessible(true);;
	    String name = field.getName();
	 
	    if(field.isAnnotationPresent(UILabel.class))
	    {
	    	UILabel uiLabel=field.getAnnotation(UILabel.class);
	        if(!uiLabel.label().isEmpty())
	        {
	            name=uiLabel.label();
	        }
	    }
	 
	    return name;
	}
	public static String getFieldLabel(Object entityObj, String property)
	{
		String label = "";
		
		Class  entityClass = entityObj.getClass();
		Field field;
		try
		{
			field = entityClass.getDeclaredField(property);	
			label = getFieldLabel(field);
		} catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		} catch (SecurityException e)
		{
			e.printStackTrace();
		}
		
	    return label;
	}
}
