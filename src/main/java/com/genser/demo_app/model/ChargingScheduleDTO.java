package com.genser.demo_app.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChargingScheduleDTO {

    private Long id;
    private UUID uniqueId;
    private Long user;

}
