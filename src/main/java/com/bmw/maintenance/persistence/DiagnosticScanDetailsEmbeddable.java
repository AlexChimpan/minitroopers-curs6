package com.bmw.maintenance.persistence;


import com.bmw.maintenance.domain.ScannerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticScanDetailsEmbeddable {
    @Enumerated(EnumType.STRING)
    private ScannerType scannerType;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name = "diagnostic_error_codes",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "error_code")
    private List<String> errorCodes;
}
