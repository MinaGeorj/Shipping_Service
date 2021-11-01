package com.Mina.shipping.services;

import com.Mina.shipping.Utils.CompanyId;
import com.Mina.shipping.Utils.ServiceId;
import com.Mina.shipping.Utils.ShipmentDTO;
import com.Mina.shipping.Utils.ShipmentStatus;
import com.Mina.shipping.models.ShippingModel;
import com.Mina.shipping.repositories.ShippingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shipmentRepo;

    public ResponseEntity<?> getShipmentDetails(String id) {
        Optional<ShippingModel> shipmentDetails = shipmentRepo.findById(id);
        return ResponseEntity.ok().body(shipmentDetails);
    }

    public ResponseEntity<?> createShipment(ShipmentDTO newShipment) {
        try {
            if(newShipment.getCompanyId()==null || newShipment.getServiceId()==null)
                return ResponseEntity.badRequest().body("Invalid shipment details!");

            ServiceId serviceId = newShipment.getServiceId();

            if(newShipment.getCompanyId()==CompanyId.UPS) {
                if (serviceId != ServiceId.UPSEXPRESS && serviceId != ServiceId.UPS2DAY)
                    return ResponseEntity.badRequest().body("Invalid service type");
            }
            else {
                if (serviceId != ServiceId.FEDEXAIR && serviceId != ServiceId.FEDEXGROUND)
                    return ResponseEntity.badRequest().body("Invalid service type");
            }
            ShippingModel shipment = new ShippingModel();
            BeanUtils.copyProperties(newShipment,shipment);
            shipment.setCreatedAt(LocalDateTime.now());
            shipment.setStatus(ShipmentStatus.Created);
            shipmentRepo.save(shipment);
            return ResponseEntity.ok("Created Successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> shipments() {
        return ResponseEntity.ok().body(shipmentRepo.findAll());
    }
}
