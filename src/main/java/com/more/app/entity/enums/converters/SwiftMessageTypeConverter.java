package com.more.app.entity.enums.converters;

import com.more.app.entity.enums.SwiftMessageType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SwiftMessageTypeConverter implements AttributeConverter<SwiftMessageType, String>
{

	@Override
	public String convertToDatabaseColumn(SwiftMessageType messageType)
	{
		return messageType.getMessageType();
	}

	@Override
	public SwiftMessageType convertToEntityAttribute(String messageType)
	{
		switch (messageType)
		{
		case "MT 101":
			return SwiftMessageType.MT101;
		case "MT 103":
			return SwiftMessageType.MT103;
		case "MT 200":
			return SwiftMessageType.MT200;
		case "MT 202":
			return SwiftMessageType.MT202;
		case "MT 202 COV":
			return SwiftMessageType.MT202COV;
		case "MT 400":
			return SwiftMessageType.MT400;
		case "MT 410":
			return SwiftMessageType.MT410;
		case "MT 412":
			return SwiftMessageType.MT412;
		case "MT 416":
			return SwiftMessageType.MT416;
		case "MT 420":
			return SwiftMessageType.MT420;
		case "MT 422":
			return SwiftMessageType.MT422;
		case "MT 430":
			return SwiftMessageType.MT430;
		case "MT 700":
			return SwiftMessageType.MT700;
		case "MT 701":
			return SwiftMessageType.MT701;
		case "MT 705":
			return SwiftMessageType.MT705;
		case "MT 707":
			return SwiftMessageType.MT707;
		case "MT 708":
			return SwiftMessageType.MT708;
		case "MT 710":
			return SwiftMessageType.MT710;
		case "MT 711":
			return SwiftMessageType.MT711;
		case "MT 720":
			return SwiftMessageType.MT720;
		case "MT 721":
			return SwiftMessageType.MT721;
		case "MT 730":
			return SwiftMessageType.MT730;
		case "MT 732":
			return SwiftMessageType.MT732;
		case "MT 734":
			return SwiftMessageType.MT734;
		case "MT 740":
			return SwiftMessageType.MT740;
		case "MT 742":
			return SwiftMessageType.MT742;
		case "MT 744":
			return SwiftMessageType.MT744;
		case "MT 747":
			return SwiftMessageType.MT747;
		case "MT 750":
			return SwiftMessageType.MT750;
		case "MT 752":
			return SwiftMessageType.MT752;
		case "MT 754":
			return SwiftMessageType.MT754;
		case "MT 756":
			return SwiftMessageType.MT756;
		case "MT 759":
			return SwiftMessageType.MT759;
		case "MT 199":
			return SwiftMessageType.MT199;
		case "MT 299":
			return SwiftMessageType.MT299;
		case "MT 499":
			return SwiftMessageType.MT499;
		case "MT 799":
			return SwiftMessageType.MT799;
		case "MT 999":
			return SwiftMessageType.MT999;
		default:
			throw new IllegalArgumentException("Unknown" + messageType);
		}
	}
}
