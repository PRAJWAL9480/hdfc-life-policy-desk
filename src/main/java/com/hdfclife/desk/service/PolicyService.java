package com.hdfclife.desk.service;

import com.hdfclife.desk.exception.DuplicatePolicyException;
import com.hdfclife.desk.exception.PolicyNotFoundException;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.store.PolicyStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    private final PolicyStore policyStore;

    public PolicyService(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    public List<Policy> getAllPolicies() {
        return policyStore.findAll();
    }

    public Policy getPolicy(String policyNo) {
        return policyStore.findByPolicyNo(policyNo)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found: " + policyNo
                        ));
    }

    public List<Policy> getPoliciesByStatus(String status) {
        return policyStore.findByStatus(status);
    }

    public List<Policy> getPoliciesByType(String type) {
        return policyStore.findByType(type);
    }

    public Policy addPolicy(Policy policy) {

        if (policyStore.existsByPolicyNo(policy.getPolicyNo())) {
            throw new DuplicatePolicyException(
                    "Policy already exists: " + policy.getPolicyNo()
            );
        }

        policyStore.add(policy);
        return policy;
    }

    public Policy updatePolicy(String policyNo, Policy policy) {

        getPolicy(policyNo);

        Policy updatedPolicy = new Policy(
                policyNo,
                policy.getCustomer(),
                policy.getType(),
                policy.getBasePremium(),
                policy.getStatus()
        );

        policyStore.update(updatedPolicy);

        return updatedPolicy;
    }

    public void deletePolicy(String policyNo) {

        getPolicy(policyNo);

        policyStore.delete(policyNo);
    }

    public long count() {
        return policyStore.count();
    }

    public long countActivePolicies() {
        return policyStore.countByStatus("Active");
    }

    public long countTermPolicies() {
        return policyStore.countByType("TERM");
    }

    public long countUniqueCustomers() {
        return policyStore.countUniqueCustomers();
    }
}