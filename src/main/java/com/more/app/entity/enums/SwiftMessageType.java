package com.more.app.entity.enums;

import java.util.Arrays;
import java.util.Collection;

public enum SwiftMessageType
{
	MT101("MT 101", "Request for Transfer"), 
	MT103("MT 103", "Single Customer Credit Transfer"),
	MT200("MT 200", "Financial Institution Transfer for its Own Account"),
	MT202("MT 202", "General Financial Institution Transfer"),
	MT202COV("MT 202 COV", "General Financial Institution Transfer"), 	
	MT400("MT 400", "Advice of Payment"),
	MT410("MT 410", "Acknowledgement"),
	MT412("MT 412", "Advice of Acceptance"),
	MT416("MT 416", "Advice of Non-Payment/Non-Acceptance"),
	MT420("MT 420", "Tracer"),
	MT422("MT 422", "Advice of Fate and Request for Instructions"),
	MT430("MT 430", "Amendment of Instructions"),	
	MT700("MT 700", "Issue of a Documentary Credit"),
	MT701("MT 701", "Issue of a Documentary Credit"), 
	MT705("MT 705", "Pre-Advice of a Documentary Credit"),
	MT707("MT 707", "Amendment to a Documentary Credit"), 
	MT708("MT 708", "Amendment to a Documentary Credit"),
	MT710("MT 710", "Advice of a Third Bank's or a Non-Bank Documentary Credit"),
	MT711("MT 711","Advice of a Third Bank's or a Non-Bank Documentary Credit"),
	MT720("MT 720","Transfer of a Documentary Credit"), 
	MT721("MT 721","Transfer of a Documentary Credit"),
	MT730("MT 730","Acknowledgement"), 
	MT732("MT 732","Advice of Discharge"), 
	MT734("MT 734","Advice of Refusal"),
	MT740("MT 740","Authorisation to Reimburse"), 
	MT742("MT 742","Reimbursement Claim"),
	MT744("MT 744","Notice of Non-Conforming Reimbursement Claim"),
	MT747("MT 747","Amendment to an Authorisation to Reimburse"), 
	MT750("MT 750","Advice of Discrepancy"),
	MT752("MT 752","Authorisation to Pay, Accept or Negotiate"), 
	MT754("MT 754","Advice of Payment/Acceptance/Negotiation"),
	MT756("MT 756","Advice of Reimbursement or Payment"), 
	MT759("MT 759","Ancillary Trade Structured Message"),
	MT199("MT 199", "Free Format Message"),
	MT299("MT 199", "Free Format Message"),
	MT499("MT 199", "Free Format Message"),
	MT799("MT 199", "Free Format Message"),
	MT999("MT 799","Free Format Message");

	private final String messageType;
	private final String description;

	SwiftMessageType(String messageType, String description)
	{
		this.messageType = messageType;
		this.description = description;
	}
	
	public static Collection<SwiftMessageType> lcPreAdviseSwiftMessageType()
	{
		return Arrays.asList(MT705,MT799);
	}
	
	public static Collection<SwiftMessageType> lcIssueSwiftMessageType()
	{
		return Arrays.asList(MT103, MT202, MT202COV, MT700, MT701, MT199, MT299,MT799);
	}
	
	public static Collection<SwiftMessageType> lcAmendmentSwiftMessageType()
	{
		return Arrays.asList(MT103, MT202, MT202COV, MT710, MT711, MT199, MT299,MT799);
	}
	
	public static Collection<SwiftMessageType> inwardCustomerPaySwiftMessageType()
	{
		return Arrays.asList(MT103, MT202, MT202COV,MT199, MT299);
	}
	
	public static Collection<SwiftMessageType> outwardCustomerPaySwiftMessageType()
	{
		return Arrays.asList(MT103, MT202, MT202COV,MT199, MT299);
	}
	
	public static Collection<SwiftMessageType> inwardBankPaySwiftMessageType()
	{
		return Arrays.asList(MT103, MT200, MT202, MT202COV, MT199, MT299);
	}
	
	public static Collection<SwiftMessageType> outwardBankPaySwiftMessageType()
	{
		return Arrays.asList(MT103, MT200, MT202, MT202COV,MT199, MT299);
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	
}
