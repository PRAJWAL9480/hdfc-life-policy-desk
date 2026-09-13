package com.hdfclife.desk.web;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Urgency;
import com.hdfclife.desk.service.ClaimService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/claims")
@Tag(name = "Claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    @Operation(summary = "File a claim")
    @ApiResponse(responseCode = "201", description = "Claim created")
    @ApiResponse(responseCode = "400", description = "Invalid claim")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Claim> createClaim(
            @RequestBody ClaimRequest request) {

        Claim claim = claimService.createClaim(
                request.getPolicyNo(),
                request.getClaimAmount(),
                request.getUrgency()
        );

        URI location = URI.create(
                "/api/claims/" + claim.getClaimNo()
        );

        return ResponseEntity
                .created(location)
                .body(claim);
    }

    @GetMapping("/{claimNo}")
    @Operation(summary = "Get one claim")
    @ApiResponse(responseCode = "200", description = "Claim found")
    @ApiResponse(responseCode = "404", description = "Claim not found")
    public ResponseEntity<Claim> getClaim(
            @PathVariable String claimNo) {

        return ResponseEntity.ok(
                claimService.getClaim(claimNo)
        );
    }

    public static class ClaimRequest {

        private String policyNo;
        private int claimAmount;
        private Urgency urgency;

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }

        public int getClaimAmount() {
            return claimAmount;
        }

        public void setClaimAmount(int claimAmount) {
            this.claimAmount = claimAmount;
        }

        public Urgency getUrgency() {
            return urgency;
        }

        public void setUrgency(Urgency urgency) {
            this.urgency = urgency;
        }
    }
}