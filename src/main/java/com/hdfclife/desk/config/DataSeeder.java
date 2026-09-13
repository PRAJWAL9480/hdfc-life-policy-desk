package com.hdfclife.desk.config;

import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.service.PolicyService;
import com.hdfclife.desk.store.PolicyStore;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PolicyStore policyStore;
    private final PolicyService policyService;
    private final HdfcProperties hdfcProperties;

    public DataSeeder(PolicyStore policyStore,
                      PolicyService policyService,
                      HdfcProperties hdfcProperties) {
        this.policyStore = policyStore;
        this.policyService = policyService;
        this.hdfcProperties = hdfcProperties;
    }

    @Override
    public void run(String... args) {

        policyStore.add(new Policy(
                "HDFC-LIFE-1001", "Anita Sharma", "TERM", 18500, "Active"));

        policyStore.add(new Policy(
                "HDFC-LIFE-1002", "Rahul Mehta", "ULIP", 42000, "Active"));

        policyStore.add(new Policy(
                "HDFC-LIFE-1003", "Priya Nair", "ENDOWMENT", 27000, "Lapsed"));

        policyStore.add(new Policy(
                "HDFC-LIFE-1004", "Vikram Singh", "TERM", 15200, "Active"));

        policyStore.add(new Policy(
                "HDFC-LIFE-1005", "Sneha Patel", "ULIP", 36000, "Active"));

        policyStore.add(new Policy(
                "HDFC-LIFE-1006", "Anita Sharma", "ENDOWMENT", 22000, "Pending"));

        System.out.println("Active profile → dev");

        System.out.println("Company name from HdfcProperties → "
                + hdfcProperties.getCompanyName());

        System.out.println("Max claim amount → "
                + hdfcProperties.getMaxClaimAmount());

        System.out.println("Seeded policy count → "
                + policyStore.count());

        policyStore.findByPolicyNo("HDFC-LIFE-1004")
                .ifPresent(policy ->
                        System.out.println(
                                "Lookup HDFC-LIFE-1004 customer → "
                                + policy.getCustomer()));

        System.out.println("Active policy count via PolicyService → "
                + policyService.countActivePolicies());

        System.out.println("TERM policy count via PolicyService → "
                + policyService.countTermPolicies());

        System.out.println("Unique customer count → "
                + policyService.countUniqueCustomers());

        System.out.println("Simple class name of injected PolicyStore → "
                + AopProxyUtils
                    .ultimateTargetClass(policyStore)
                    .getSimpleName());
    }
}