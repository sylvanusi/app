package com.more.app.util;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.more.app.entity.Product;
import com.more.app.entity.ProductReferenceNumberDefination;
import com.more.app.entity.enums.ReferenceNumberItem;
import com.more.app.entity.product.Register;
import com.more.app.repository.productsetup.ProductRepository;

public class ReferenceNumberItemUtil {
	@Autowired
	private ProductRepository productRepository;

	public static String getReferenceNumberItemValue(ReferenceNumberItem item, Product product) {
		switch (item) {
		case MODULE_CODE:
			return product.getModule().getCode();
		case PRODUCT_TYPE_CODE:
			return product.getType().getCode();
		case PRODUCT_CODE:
			return product.getProductCode();
		case TRANS_CCY:
			return generateRandomReferenceNo(item.getLength());
		case YEAR_YY:
			return String.valueOf(LocalDate.now().getYear());
		case YEAR_YYYY:
			return String.valueOf(LocalDate.now().getYear());
		case DAY_IN_YR:
			return String.valueOf(LocalDate.now().getDayOfYear());
		case PROCESSING_DT:
			return String.valueOf(LocalDate.now().toString());
		case BRANCH_CODE:
			return generateRandomReferenceNo(item.getLength());
		case BRANCH_NO:
			return generateRandomReferenceNo(item.getLength());
		// case UNIQUE_CODE:
		// return generateRandomReferenceNo(item.getLength());
		case SEQUENCE_NO:
			return "";
		default:
			return "";
		}
	}

	public static String getReferenceNumberItemValue(ReferenceNumberItem item, Register register) {
		Product product = register.getProduct();
		// FacadeFactory.getFacade().refresh(product);
		switch (item) {
		case MODULE_CODE:
			return product.getModule().getCode();
		case PRODUCT_TYPE_CODE:
			return product.getType().getCode();
		case PRODUCT_CODE:
			return product.getProductCode();
		case TRANS_CCY:
			return register.getTransactionCcy();
		case YEAR_YY:
			return String.valueOf(LocalDate.now().getYear());
		case YEAR_YYYY:
			return String.valueOf(LocalDate.now().getYear());
		case DAY_IN_YR:
			return String.valueOf(LocalDate.now().getDayOfYear());
		case PROCESSING_DT:
			return String.valueOf(LocalDate.now().toString());
		case BRANCH_CODE:
			return String.valueOf(register.getTransactionBranch().getBranchNo());
		case BRANCH_NO:
			return register.getTransactionBranch().getCode();
		// case UNIQUE_CODE:
		// return "";
		case SEQUENCE_NO:
			return String.valueOf(product.getLastSequenceNumber() + 1);
		default:
			return "";
		}
	}

	public static String generateRandomReferenceNo(int n) {
		byte[] array = new byte[256];
		new Random().nextBytes(array);

		String randomString = new String(array, Charset.forName("UTF-8"));
		StringBuffer r = new StringBuffer();

		String AlphaNumericString = randomString.replaceAll("[^A-Za-z0-9]", "");

		for (int k = 0; k < AlphaNumericString.length(); k++) {
			if (Character.isLetter(AlphaNumericString.charAt(k)) && (n > 0)
					|| Character.isDigit(AlphaNumericString.charAt(k)) && (n > 0)) {
				r.append(AlphaNumericString.charAt(k));
				n--;
			}
		}
		return r.toString().toUpperCase();
	}

	public synchronized static String generateTransactionReference(List<ProductReferenceNumberDefination> refDefList,
			Register register) {
		int referenceLength = register.getProduct().getReferenceLength();
		Long lastSequenceNumber = -1L;
		String referenceNumber = "";
		refDefList.sort((c1, c2) -> c1.getPosition() - c2.getPosition());
		for (ProductReferenceNumberDefination refDef : refDefList) {
			String itemValue = getReferenceNumberItemValue(refDef.getItem(), register);
			if (ReferenceNumberItem.SEQUENCE_NO.equals(refDef.getItem())) {
				lastSequenceNumber = Long.parseLong(itemValue);
				Product product = register.getProduct();
				product.setLastSequenceNumber(lastSequenceNumber);
				// FacadeFactory.getFacade().store(product);

				int itemLength = refDef.getRefNoStopIndex() - refDef.getRefNoStartIndex();
				if (itemValue.length() < itemLength) {
					int diff = itemLength - itemValue.length();
					String toAppend = "";
					for (int i = 0; i < diff; i++) {
						toAppend = toAppend + "0";
					}
					System.out.println(toAppend);
					itemValue = toAppend + itemValue;
				}
			}
			referenceNumber = referenceNumber + itemValue;
		}

		return referenceNumber;
	}

}
