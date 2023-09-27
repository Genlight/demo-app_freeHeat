package com.genser.demo_app.rest;

import com.genser.demo_app.model.ChargingLevelDTO;
import com.genser.demo_app.service.ChargingLevelService;
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
@RequestMapping(value = "/api/chargingLevels", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargingLevelResource {

    private final ChargingLevelService chargingLevelService;

    public ChargingLevelResource(final ChargingLevelService chargingLevelService) {
        this.chargingLevelService = chargingLevelService;
    }

    @GetMapping
    public ResponseEntity<List<ChargingLevelDTO>> getAllChargingLevels() {
        return ResponseEntity.ok(chargingLevelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingLevelDTO> getChargingLevel(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chargingLevelService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createChargingLevel(
            @RequestBody @Valid final ChargingLevelDTO chargingLevelDTO) {
        final Long createdId = chargingLevelService.create(chargingLevelDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateChargingLevel(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ChargingLevelDTO chargingLevelDTO) {
        chargingLevelService.update(id, chargingLevelDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChargingLevel(@PathVariable(name = "id") final Long id) {
        chargingLevelService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
