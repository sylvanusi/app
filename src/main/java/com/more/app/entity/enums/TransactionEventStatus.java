package com.more.app.entity.enums;

import java.util.Arrays;
import java.util.Collection;

public enum TransactionEventStatus {
	A("Active"), C("Completed"), I("Inactive"), P("Processing"), CN("Cancelled"), IP("In Progress"), AP("Approved"),
	REG("Registered"), PAD("Pre Advised"), ISS("Issued"), AMD("Amended"), ADJ("Adjusted"), PD("Paid"),
	DP("Document Presented"), DA("Document Accepted"), CL("Closed");

	private final String status;

	TransactionEventStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static Collection<TransactionEventStatus> getWorkFlowStatusList() {
		return Arrays.asList(TransactionEventStatus.P, TransactionEventStatus.C, TransactionEventStatus.CN);
	}

	public static Collection<TransactionEventStatus> getActiveOrInactiveStatus() {
		return Arrays.asList(TransactionEventStatus.A, TransactionEventStatus.I);
	}

	public static Collection<TransactionEventStatus> getTransactionStatus() {
		return Arrays.asList(TransactionEventStatus.P, TransactionEventStatus.C, TransactionEventStatus.CN);
	}

	public static Collection<TransactionEventStatus> getCurrentQueueStatus() {
		return Arrays.asList(TransactionEventStatus.P, TransactionEventStatus.C, TransactionEventStatus.CN);
	}
}
