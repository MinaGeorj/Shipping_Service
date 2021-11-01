package com.Mina.shipping.models;

import com.Mina.shipping.Utils.CompanyId;
import com.Mina.shipping.Utils.ServiceId;
import com.Mina.shipping.Utils.ShipmentStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class ShippingModel {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @NonNull
    private CompanyId companyId;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ServiceId serviceId;

    private float width;
    private float height;
    private float length;
    private float weight;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDateTime createdAt;

}
