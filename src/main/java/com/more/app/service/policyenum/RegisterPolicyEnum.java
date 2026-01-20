package com.more.app.service.policyenum;

public enum RegisterPolicyEnum {
	REGISTER_CREATE_POLICY("REGISTER CREATE POLICY"), REGISTER_UPDATE_POLICY("REGISTER UPDATE POLICY"),
	REGISTER_APPROVAL_POLICY("REGISTER APPROVAL POLICY"), REGISTER_CANCELLATION_POLICY("REGISTER CANCELLATION POLICY");

	private final String policyName;

	RegisterPolicyEnum(String policyName) {
		this.policyName = policyName;
	}

	public String getPolicyName() {
		return policyName;
	}

}
