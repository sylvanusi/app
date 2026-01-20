package com.more.app.service.policy;

import com.more.app.entity.AbstractPojo;

public interface Policy
{
	public <T extends AbstractPojo> Boolean executePolicy(T entity);
}
