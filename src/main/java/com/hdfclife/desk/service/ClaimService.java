package com.hdfclife.desk.service;

import com.hdfclife.desk.config.HdfcProperties;
import com.hdfclife.desk.exception.ClaimNotFoundException;
import com.hdfclife.desk.exception.InvalidClaimException;
import com.hdfclife.desk.exception.PolicyNotFoundException;
import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Urgency;
import com.hdfclife.desk.store.PolicyStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    private final PolicyStore policyStore;
    private final HdfcProperties hdfcProperties;

    public ClaimService(PolicyStore policyStore,
                        HdfcProperties hdfcProperties) {
        this.policyStore = policyStore;
        this.hdfcProperties = hdfcProperties;
    }

    public Claim createClaim(String policyNo,
                             int claimAmount,
                             Urgency urgency) {

        // Policy must exist
        if (!policyStore.existsByPolicyNo(policyNo)) {
            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNo
            );
        }

        // Claim amount validation
        if (claimAmount <= 0
                || claimAmount > hdfcProperties.getMaxClaimAmount()) {

            throw new InvalidClaimException(
                    "Invalid claim amount: " + claimAmount
            );
        }

        String claimNo = String.format(
                "CLM-%02d",
                policyStore.countClaims() + 1
        );

        Claim claim = new Claim(
                claimNo,
                policyNo,
                claimAmount,
                urgency,
                "SUBMITTED"
        );

        policyStore.addClaim(claim);

        return claim;
    }

    public Claim getClaim(String claimNo) {

        return policyStore.findClaimByClaimNo(claimNo)
                .orElseThrow(() ->
                        new ClaimNotFoundException(
                                "Claim not found: " + claimNo
                        ));
    }

    public List<Claim> getClaimsForPolicy(String policyNo) {

        if (!policyStore.existsByPolicyNo(policyNo)) {
            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNo
            );
        }

        return policyStore.findClaimsByPolicyNo(policyNo);
    }
}