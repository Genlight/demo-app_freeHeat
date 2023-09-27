package com.genser.demo_app.rest;

import com.genser.demo_app.model.ChargingScheduleDTO;
import com.genser.demo_app.service.ChargingScheduleService;
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
@RequestMapping(value = "/api/chargingSchedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargingScheduleResource {

    private final ChargingScheduleService chargingScheduleService;

    public ChargingScheduleResource(final ChargingScheduleService chargingScheduleService) {
        this.chargingScheduleService = chargingScheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ChargingScheduleDTO>> getAllChargingSchedules() {
        return ResponseEntity.ok(chargingScheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingScheduleDTO> getChargingSchedule(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chargingScheduleService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createChargingSchedule(
            @RequestBody @Valid final ChargingScheduleDTO chargingScheduleDTO) {
        final Long createdId = chargingScheduleService.create(chargingScheduleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateChargingSchedule(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ChargingScheduleDTO chargingScheduleDTO) {
        chargingScheduleService.update(id, chargingScheduleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChargingSchedule(@PathVariable(name = "id") final Long id) {
        chargingScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
