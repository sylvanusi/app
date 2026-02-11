package com.more.app.specification;

import org.springframework.data.jpa.domain.Specification;

import com.more.app.entity.product.LcMaster;
import com.more.app.entity.product.dto.LcMasterSearchDTO;

public class LcMasterSpecification {

    public static Specification<LcMaster> build(LcMasterSearchDTO dto) {

        return new GenericSpecificationBuilder<LcMaster>()

                .equals("registerId", dto.getRegisterId())
                .equals("applicantPartyId", dto.getApplicantPartyId())
                .equals("beneficiaryPartyId", dto.getBeneficiaryPartyId())

                .like("lcReferenceNo", dto.getLcReferenceNo())
                .like("internalReferenceNo", dto.getInternalReferenceNo())
                .like("applicantName", dto.getApplicantName())
                .like("beneficiaryName", dto.getBeneficiaryName())

                .equals("lcCcy", dto.getLcCcy())

                .range("lcAmount",
                        dto.getLcAmountFrom(),
                        dto.getLcAmountTo())

                .range("issueDate",
                        dto.getIssueDateFrom(),
                        dto.getIssueDateTo())
                
                .range("systemCreateDate",
                        dto.getFromCreateDate(),
                        dto.getToCreateDate())

                .range("expiryDate",
                        dto.getExpiryDateFrom(),
                        dto.getExpiryDateTo())

                .equals("eventStatus", dto.getEventStatus())
                .equals("lcEvent", dto.getLcEvent())
                .equals("lcStatus", dto.getLcStatus())

                .build();
    }
}
