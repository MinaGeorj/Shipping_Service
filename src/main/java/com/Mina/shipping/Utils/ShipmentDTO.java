package com.Mina.shipping.Utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDTO {
    @Enumerated(EnumType.STRING)
    private CompanyId companyId;

    @Enumerated(EnumType.STRING)
    private ServiceId serviceId;

    private float width;
    private float height;
    private float length;
    private float weight;
}
