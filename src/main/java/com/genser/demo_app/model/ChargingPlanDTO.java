package com.genser.demo_app.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChargingPlanDTO {

    private Long id;

    @NotNull
    private LocalDateTime nextCharge;

}
