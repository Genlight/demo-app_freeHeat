package com.genser.demo_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VehicleDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @NotNull
    private Long device;

    private Long chargingSchedule;

}
