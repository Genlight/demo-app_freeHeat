package com.genser.demo_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @JsonProperty("isAdmin")
    private Boolean isAdmin;

    private Long preferencesCharging;

}
