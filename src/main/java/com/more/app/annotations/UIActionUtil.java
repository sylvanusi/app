package com.more.app.annotations;

import java.lang.reflect.Field;

public class UIActionUtil
{
	public static String getFieldLabel(Field field)
	{
		field.setAccessible(true);;
	    String name = "";
	    if(field.isAnnotationPresent(UIAction.class))
	    {
	    	UIAction uiLabel=field.getAnnotation(UIAction.class);
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
		} catch (NoSuchFieldException | SecurityException e)
		{
			e.printStackTrace();
		}	
	    return label;
	}
	
	public static String getFieldLabel(Class entityClass, String property)
	{
		String label = "";
		Field field;
		try
		{
			field = entityClass.getDeclaredField(property);	
			label = getFieldLabel(field);
		} catch (NoSuchFieldException | SecurityException e)
		{
			e.printStackTrace();
		}		
	    return label;
	}
	
	public static String getFieldErrorLabel(Field field)
	{
		field.setAccessible(true);
	    String name = ""; 
	    if(field.isAnnotationPresent(UIAction.class))
	    {
	    	UIAction uiLabel=field.getAnnotation(UIAction.class);
	        if(!uiLabel.errorlabel().isEmpty())
	        {
	            name=uiLabel.errorlabel();
	        }
	    }	 
	    return name;
	}
}
