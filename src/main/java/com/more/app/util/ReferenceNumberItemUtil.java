package com.more.app.util;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.more.app.entity.Product;
import com.more.app.entity.ProductReferenceNumberDefination;
import com.more.app.entity.enums.ReferenceNumberItem;
import com.more.app.entity.product.Register;
import com.more.app.repository.product.RegisterRepository;
import com.more.app.repository.productsetup.ProductRepository;

@Service
public class ReferenceNumberItemUtil {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RegisterRepository registerRepository;

	public String getReferenceNumberItemValue(ReferenceNumberItem item, Product product1) {
		Product product = productRepository.findById(product1.getId()).get();
		
		switch (item) {
		case MODULE_CODE:
			return product.getModuleCode();
		case PRODUCT_TYPE_CODE:
			return product.getTypeCode();
		case PRODUCT_CODE:
			return product.getProductCode();
		case TRANS_CCY:
			return generateRandomReferenceNo(item.getLength());
		case YEAR_YY:
			return String.valueOf(LocalDate.now().getYear()).substring(2);
		case YEAR_YYYY:
			return String.valueOf(LocalDate.now().getYear());
		case DAY_IN_YR:
			return String.valueOf(LocalDate.now().getDayOfYear());
		case PROCESSING_DT:
			return String.valueOf(LocalDate.now().toString()).replaceAll("-", "");
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
	
	public String getReferenceNumberItemValueByRegister(ReferenceNumberItem item, Long registerId) {
		Register register = registerRepository.findById(registerId).get();
		Product product = productRepository.findById(register.getProductId()).get();
		
		switch (item) {
		case MODULE_CODE:
			return product.getModuleCode();
		case PRODUCT_TYPE_CODE:
			return product.getTypeCode();
		case PRODUCT_CODE:
			return product.getProductCode();
		case TRANS_CCY:
			return register.getTransactionCcy();
		case YEAR_YY:
			return String.valueOf(LocalDate.now().getYear()).substring(2);
		case YEAR_YYYY:
			return String.valueOf(LocalDate.now().getYear());
		case DAY_IN_YR:
			return String.valueOf(LocalDate.now().getDayOfYear());
		case PROCESSING_DT:
			return String.valueOf(LocalDate.now().toString()).replaceAll("-", "");
		case BRANCH_CODE:
			return register.getTransactionBranchCode();
		case BRANCH_NO:
			return register.getTransactionBranchNo();
		// case UNIQUE_CODE:
		// return generateRandomReferenceNo(item.getLength());
		case SEQUENCE_NO:
			return "";
		default:
			return "";
		}
	}

	public String getReferenceNumberItemValue(ReferenceNumberItem item, Register register) {
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

	public String generateRandomReferenceNo(int n) {
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

	public synchronized String generateTransactionReference(List<ProductReferenceNumberDefination> refDefList,
			Product product) {
		int referenceLength = product.getReferenceLength();
		Long lastSequenceNumber = -1L;
		String referenceNumber = "";
		refDefList.sort((c1, c2) -> c1.getPosition() - c2.getPosition());
		int n = 0;
		for (ProductReferenceNumberDefination refDef : refDefList) {
			System.out.println(n++);
			String itemValue = getReferenceNumberItemValue(refDef.getItem(), product);
			System.out.println("itemValue " + itemValue);
			if (ReferenceNumberItem.SEQUENCE_NO.equals(refDef.getItem())) {
				// lastSequenceNumber = Long.parseLong(itemValue);

				lastSequenceNumber = product.getLastSequenceNumber();
				if (null == lastSequenceNumber)
					lastSequenceNumber = 0L;

				lastSequenceNumber = lastSequenceNumber + 1;
				product.setLastSequenceNumber(lastSequenceNumber);
				productRepository.save(product);

				int nextStopIndex = 0;
				if (0 == refDef.getRefNoStopIndex())
					nextStopIndex = product.getReferenceLength();
				else
					nextStopIndex = refDef.getRefNoStopIndex();
				int itemLength = nextStopIndex - refDef.getRefNoStartIndex();

				// if (itemValue.length() < itemLength) {
				int diff = itemLength - itemValue.length();
				String toAppend = "";
				for (int i = 0; i < diff; i++) {
					toAppend = toAppend + "0";
				}
				System.out.println(toAppend);
				itemValue = toAppend + lastSequenceNumber;
				// }
			}
			referenceNumber = referenceNumber + itemValue;
			System.out.println("referenceNumber " + referenceNumber);
		}

		return referenceNumber;
	}
	
	
	public synchronized String generateTransactionReference(List<ProductReferenceNumberDefination> refDefList,
			Long registerId) {

		Register register = registerRepository.findById(registerId).get();
		Product product = productRepository.findById(register.getProductId()).get();
		int referenceLength = product.getReferenceLength();
		Long lastSequenceNumber = -1L;
		String referenceNumber = "";
		refDefList.sort((c1, c2) -> c1.getPosition() - c2.getPosition());
		int n = 0;
		for (ProductReferenceNumberDefination refDef : refDefList) {
			System.out.println(n++);
			String itemValue = getReferenceNumberItemValueByRegister(refDef.getItem(), registerId);
			System.out.println("itemValue " + itemValue);
			if (ReferenceNumberItem.SEQUENCE_NO.equals(refDef.getItem())) {
				// lastSequenceNumber = Long.parseLong(itemValue);

				lastSequenceNumber = product.getLastSequenceNumber();
				if (null == lastSequenceNumber)
					lastSequenceNumber = 0L;

				lastSequenceNumber = lastSequenceNumber + 1;
				product.setLastSequenceNumber(lastSequenceNumber);
				productRepository.save(product);

				int nextStopIndex = 0;
				if (0 == refDef.getRefNoStopIndex())
					nextStopIndex = product.getReferenceLength();
				else
					nextStopIndex = refDef.getRefNoStopIndex();
				int itemLength = nextStopIndex - refDef.getRefNoStartIndex();

				// if (itemValue.length() < itemLength) {
				int diff = itemLength - itemValue.length();
				String toAppend = "";
				for (int i = 0; i < diff; i++) {
					toAppend = toAppend + "0";
				}
				System.out.println(toAppend);
				itemValue = toAppend + lastSequenceNumber;
				// }
			}
			referenceNumber = referenceNumber + itemValue;
			System.out.println("referenceNumber " + referenceNumber);
		}
		
		if(referenceNumber.length() > referenceLength)
			referenceNumber = "INVALID";

		return referenceNumber;
	}

	public synchronized String generateTransactionReferenceByProductId(List<ProductReferenceNumberDefination> refDefList,
			Long productId) {
		Product product = productRepository.findById(productId).get();
		return generateTransactionReference(refDefList, product);
	}

}
