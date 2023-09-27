package com.genser.demo_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeviceDTO {

    private Long id;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String type;

    @NotNull
    private Integer status;

    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "19.08")
    private BigDecimal energyPrice;

    private Integer batteryChargeLevel;

    private Long user;

    @NotNull
    private Long chargingLevel;

}
