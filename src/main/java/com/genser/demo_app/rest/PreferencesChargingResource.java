package com.genser.demo_app.rest;

import com.genser.demo_app.model.PreferencesChargingDTO;
import com.genser.demo_app.service.PreferencesChargingService;
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
@RequestMapping(value = "/api/preferencesChargings", produces = MediaType.APPLICATION_JSON_VALUE)
public class PreferencesChargingResource {

    private final PreferencesChargingService preferencesChargingService;

    public PreferencesChargingResource(
            final PreferencesChargingService preferencesChargingService) {
        this.preferencesChargingService = preferencesChargingService;
    }

    @GetMapping
    public ResponseEntity<List<PreferencesChargingDTO>> getAllPreferencesChargings() {
        return ResponseEntity.ok(preferencesChargingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferencesChargingDTO> getPreferencesCharging(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(preferencesChargingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPreferencesCharging(
            @RequestBody @Valid final PreferencesChargingDTO preferencesChargingDTO) {
        final Long createdId = preferencesChargingService.create(preferencesChargingDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePreferencesCharging(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PreferencesChargingDTO preferencesChargingDTO) {
        preferencesChargingService.update(id, preferencesChargingDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePreferencesCharging(
            @PathVariable(name = "id") final Long id) {
        preferencesChargingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
