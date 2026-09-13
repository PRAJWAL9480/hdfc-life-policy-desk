package com.hdfclife.desk.web;

import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.service.ClaimService;
import com.hdfclife.desk.service.PolicyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policies")
public class PolicyController {

    private final PolicyService policyService;
    private final ClaimService claimService;

    public PolicyController(PolicyService policyService,
                            ClaimService claimService) {
        this.policyService = policyService;
        this.claimService = claimService;
    }

    @GetMapping
    @Operation(summary = "Get all policies or filter by status/type")
    @ApiResponse(responseCode = "200", description = "Policies returned")
    public ResponseEntity<List<Policy>> getPolicies(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type) {

        if (status != null && type != null) {
            return ResponseEntity.ok(
                    policyService.getPoliciesByStatus(status)
                            .stream()
                            .filter(policy -> policy.getType().equals(type))
                            .toList()
            );
        }

        if (status != null) {
            return ResponseEntity.ok(
                    policyService.getPoliciesByStatus(status)
            );
        }

        if (type != null) {
            return ResponseEntity.ok(
                    policyService.getPoliciesByType(type)
            );
        }

        return ResponseEntity.ok(
                policyService.getAllPolicies()
        );
    }

    @GetMapping("/{policyNo}")
    @Operation(summary = "Get one policy")
    @ApiResponse(responseCode = "200", description = "Policy found")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Policy> getPolicy(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                policyService.getPolicy(policyNo)
        );
    }

    @PostMapping
    @Operation(summary = "Create a policy")
    @ApiResponse(responseCode = "201", description = "Policy created")
    @ApiResponse(responseCode = "409", description = "Duplicate policy")
    public ResponseEntity<Policy> createPolicy(
            @RequestBody Policy policy) {

        Policy created = policyService.addPolicy(policy);

        URI location = URI.create(
                "/api/policies/" + created.getPolicyNo()
        );

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{policyNo}")
    @Operation(summary = "Replace a policy")
    @ApiResponse(responseCode = "200", description = "Policy updated")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Policy> updatePolicy(
            @PathVariable String policyNo,
            @RequestBody Policy policy) {

        return ResponseEntity.ok(
                policyService.updatePolicy(policyNo, policy)
        );
    }

    @DeleteMapping("/{policyNo}")
    @Operation(summary = "Delete a policy")
    @ApiResponse(responseCode = "204", description = "Policy deleted")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Void> deletePolicy(
            @PathVariable String policyNo) {

        policyService.deletePolicy(policyNo);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{policyNo}/claims")
    @Operation(summary = "Get claims for a policy")
    @ApiResponse(responseCode = "200", description = "Claims returned")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<?> getClaims(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                claimService.getClaimsForPolicy(policyNo)
        );
    }
}