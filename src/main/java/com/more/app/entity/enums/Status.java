package com.more.app.entity.enums;

import java.util.Arrays;
import java.util.Collection;

public enum Status
{
	A("Active"), C("Completed"), I("Inactive"),P("Processing"),CN("Cancelled"),IP("In Progress"),AP("Approved");
	private final String status;

	Status(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}
	
	public static Collection<Status> getWorkFlowStatusList()
	{
		return Arrays.asList(Status.P, Status.C,Status.CN);
	}

	public static Collection<Status> getActiveOrInactiveStatus()
	{
		return Arrays.asList(Status.A, Status.I);
	}
	
	public static Collection<Status> getTransactionStatus()
	{
		return Arrays.asList(Status.P, Status.C,Status.CN);
	}
	
	public static Collection<Status> getCurrentQueueStatus()
	{
		return Arrays.asList(Status.P, Status.C,Status.CN);
	}
}
