package com.bmw.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class DiagnosticScanDetails {
    private final ScannerType scannerType;
    private final List<String> errorCodes;

    @JsonCreator
    public DiagnosticScanDetails(@JsonProperty("scanner type")ScannerType scannerType, @JsonProperty("errorCodes")List<String> errorCodes)
    {
        this.scannerType=scannerType;
        this.errorCodes=errorCodes;
    }
}
