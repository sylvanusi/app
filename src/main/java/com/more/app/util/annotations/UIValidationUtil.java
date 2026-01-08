package com.more.app.util.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

public class UIValidationUtil
{
	public static String getErrorField(Object entityObj, String property)
	{
		String errorLabel = "";
		try
		{
			Class entityClass = entityObj.getClass();
			Field field = entityClass.getDeclaredField(property);
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class))
			{
				String typeString = "get";
				if (field.getType().getName().equals("boolean") || field.getType().getName().equals("Boolean"))
					typeString = "is";
				String methodName = typeString + field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
				Method mthd = entityClass.getDeclaredMethod(methodName, null);
				Object value = mthd.invoke(entityObj, null);
				Column cc = field.getAnnotation(Column.class);
				if (!cc.nullable())
				{

					if (null == value)
						return errorLabel = UIActionUtil.getFieldErrorLabel(field);
				}
				if (null != value)
				{

				}
			}

		} catch (NoSuchFieldException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}

		return errorLabel;
	}

	public static List<String> getErrorFields(Object entityObj)
	{
		List<String> erlist = new ArrayList();
		try
		{
			Class entityClass = entityObj.getClass();
			Field[] fieldArr = entityClass.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++)
			{
				Field field = fieldArr[i];
				field.setAccessible(true);
				if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(JoinColumn.class))
				{
					String typeString = "get";
					if (field.getType().getName().equals("boolean") || field.getType().getName().equals("Boolean"))
						typeString = "is";
					String methodName = typeString + field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					Method mthd = entityClass.getDeclaredMethod(methodName, null);
					Object value = mthd.invoke(entityObj, null);
					if (field.isAnnotationPresent(Column.class))
					{
						Column cc = field.getAnnotation(Column.class);
						if (!cc.nullable())
						{

							if (null == value)
								erlist.add(UIActionUtil.getFieldErrorLabel(field));
							else
							{
								if (field.getType().getSimpleName().equals("String"))
									if (value.toString().trim().isEmpty())
										erlist.add(UIActionUtil.getFieldErrorLabel(field));
							}

						}
					}
					if (field.isAnnotationPresent(JoinColumn.class))
					{
						JoinColumn cc = field.getAnnotation(JoinColumn.class);
						if (!cc.nullable())
						{
							if (null == value)
								erlist.add(UIActionUtil.getFieldErrorLabel(field));

						}
					}
				}
			}
		} catch (NullPointerException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return erlist;
	}
}
