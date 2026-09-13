package com.hdfclife.desk.store;

import com.hdfclife.desk.model.Policy;

import java.util.List;
import java.util.Optional;
import com.hdfclife.desk.model.Claim;

public interface PolicyStore {

    List<Policy> findAll();

    Optional<Policy> findByPolicyNo(String policyNo);

    List<Policy> findByStatus(String status);

    List<Policy> findByType(String type);

    void add(Policy policy);

    void update(Policy policy);

    void delete(String policyNo);

    boolean existsByPolicyNo(String policyNo);

    long count();

    long countByStatus(String status);

    long countByType(String type);

    long countUniqueCustomers();
    List<Claim> findClaimsByPolicyNo(String policyNo);

    Optional<Claim> findClaimByClaimNo(String claimNo);

    void addClaim(Claim claim);

    long countClaims();
}