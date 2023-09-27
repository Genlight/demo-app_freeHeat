package com.genser.demo_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PreferencesChargingDTO {

    private Long id;

    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "41.08")
    private BigDecimal maxCost;

    private Integer maxTime;

    private Integer maxCharge;

    private Integer minCharge;

}
