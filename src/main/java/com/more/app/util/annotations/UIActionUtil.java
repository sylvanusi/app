package com.more.app.util.annotations;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public static List<String> getFieldLabels(Class entityClass, List<String> propertys)
	{
		List<String> labelList = new ArrayList<>();
		propertys.forEach(p ->{
			labelList.add(getFieldLabel(entityClass, p));
		});
				
	    return labelList;
	}
	
	public static List<String> getBasisAmountFieldLabel(String entityClassString)
	{
		List<String> labelList = new ArrayList<>();		
		try
		{
			Class entityClass = Class.forName(entityClassString);
			Field[] fields= entityClass.getDeclaredFields();	
			List<String> propertyList = Arrays.asList(fields).stream().filter(field -> field.getType().equals(BigDecimal.class)).map(f -> f.getName()).collect(Collectors.toList());
			labelList.addAll(UIActionUtil.getFieldLabels(entityClass, propertyList));
		} catch (SecurityException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}		
	    return labelList;
	}
}
