package com.genser.demo_app.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChargingLevelDTO {

    private Long id;

    @NotNull
    private Integer percentage;

}
