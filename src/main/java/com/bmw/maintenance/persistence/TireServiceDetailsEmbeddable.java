package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TirePosition;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TireServiceDetailsEmbeddable {

    @Enumerated(EnumType.STRING)
    public TirePosition tirePosition;

}
