package com.hdfclife.desk.store;

import com.hdfclife.desk.model.Policy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.hdfclife.desk.model.Claim;

@Repository
public class InMemoryPolicyStore implements PolicyStore {

    private final List<Policy> policies = new ArrayList<>();
    private final List<Claim> claims = new ArrayList<>();

    @Override
    public List<Policy> findAll() {
        return new ArrayList<>(policies);
    }

    @Override
    public Optional<Policy> findByPolicyNo(String policyNo) {
        return policies.stream()
                .filter(policy -> policy.getPolicyNo().equals(policyNo))
                .findFirst();
    }

    @Override
    public List<Policy> findByStatus(String status) {
        return policies.stream()
                .filter(policy -> policy.getStatus().equals(status))
                .toList();
    }

    @Override
    public List<Policy> findByType(String type) {
        return policies.stream()
                .filter(policy -> policy.getType().equals(type))
                .toList();
    }

    @Override
    public void add(Policy policy) {
        policies.add(policy);
    }

    @Override
    public void update(Policy policy) {

        for (int i = 0; i < policies.size(); i++) {

            if (policies.get(i).getPolicyNo().equals(policy.getPolicyNo())) {
                policies.set(i, policy);
                return;
            }
        }
    }

    @Override
    public void delete(String policyNo) {

        policies.removeIf(
                policy -> policy.getPolicyNo().equals(policyNo)
        );
    }

    @Override
    public boolean existsByPolicyNo(String policyNo) {
        return policies.stream()
                .anyMatch(policy -> policy.getPolicyNo().equals(policyNo));
    }

    @Override
    public long count() {
        return policies.size();
    }

    @Override
    public long countByStatus(String status) {
        return policies.stream()
                .filter(policy -> policy.getStatus().equals(status))
                .count();
    }

    @Override
    public long countByType(String type) {
        return policies.stream()
                .filter(policy -> policy.getType().equals(type))
                .count();
    }

    @Override
    public long countUniqueCustomers() {
        return policies.stream()
                .map(Policy::getCustomer)
                .distinct()
                .count();
    }
    @Override
    public List<Claim> findClaimsByPolicyNo(String policyNo) {
        return claims.stream()
                .filter(claim -> claim.getPolicyNo().equals(policyNo))
                .toList();
    }

    @Override
    public Optional<Claim> findClaimByClaimNo(String claimNo) {
        return claims.stream()
                .filter(claim -> claim.getClaimNo().equals(claimNo))
                .findFirst();
    }

    @Override
    public void addClaim(Claim claim) {
        claims.add(claim);
    }

    @Override
    public long countClaims() {
        return claims.size();
    }
}