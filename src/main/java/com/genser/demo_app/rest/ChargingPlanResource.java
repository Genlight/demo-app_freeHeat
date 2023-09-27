package com.genser.demo_app.rest;

import com.genser.demo_app.model.ChargingPlanDTO;
import com.genser.demo_app.service.ChargingPlanService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/chargingPlans", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargingPlanResource {

    private final ChargingPlanService chargingPlanService;

    public ChargingPlanResource(final ChargingPlanService chargingPlanService) {
        this.chargingPlanService = chargingPlanService;
    }

    @GetMapping
    public ResponseEntity<List<ChargingPlanDTO>> getAllChargingPlans() {
        return ResponseEntity.ok(chargingPlanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingPlanDTO> getChargingPlan(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chargingPlanService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createChargingPlan(
            @RequestBody @Valid final ChargingPlanDTO chargingPlanDTO) {
        final Long createdId = chargingPlanService.create(chargingPlanDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateChargingPlan(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ChargingPlanDTO chargingPlanDTO) {
        chargingPlanService.update(id, chargingPlanDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChargingPlan(@PathVariable(name = "id") final Long id) {
        chargingPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
