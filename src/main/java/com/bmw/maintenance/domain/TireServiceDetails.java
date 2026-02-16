package com.bmw.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TireServiceDetails {
    private final TirePosition tirePosition;

    @JsonCreator
    public TireServiceDetails(@JsonProperty("tirePosition")TirePosition tirePosition)
    {
        this.tirePosition=tirePosition;
    }
}
