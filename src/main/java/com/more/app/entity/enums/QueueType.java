package com.more.app.entity.enums;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.compress.utils.Lists;

public enum QueueType
{
	INPUT("Input", 0), UPDATE("Update", 1), REVIEW("Review", 2), APPROVAL("Approval", 3);

	private final String queue;
	private final int order;

	QueueType(String queue, int order)
	{
		this.queue = queue;
		this.order = order;
	}

	public String getQueue()
	{
		return queue;
	}

	public int getOrder()
	{
		return order;
	}

	public static Collection<QueueType> inputQueueType()
	{
		return Arrays.asList(QueueType.INPUT);
	}

	public static Collection<QueueType> intermediateQueueType()
	{
		return Arrays.asList(QueueType.UPDATE, QueueType.REVIEW);
	}

	public static Collection<QueueType> approvalQueueType()
	{
		return Arrays.asList(QueueType.APPROVAL);
	}

	public static Collection<QueueType> nonInputQueueType()
	{
		return Arrays.asList(QueueType.UPDATE, QueueType.REVIEW,QueueType.APPROVAL);
	}
}
