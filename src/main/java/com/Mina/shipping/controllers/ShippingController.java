package com.Mina.shipping.controllers;

import com.Mina.shipping.Utils.CompanyId;
import com.Mina.shipping.Utils.ServiceId;
import com.Mina.shipping.Utils.ShipmentDTO;
import com.Mina.shipping.Utils.ShipmentStatus;
import com.Mina.shipping.models.ShippingModel;
import com.Mina.shipping.repositories.ShippingRepository;
import com.Mina.shipping.services.ShippingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ShippingController {

    @Autowired
    private ShippingRepository shippingRepo;

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/shipments")
    public ResponseEntity<?> getShipments() {
        return shippingService.shipments();
    }

    @GetMapping("/shipment/{id}")
    public ResponseEntity<?> getShipmentDetails(@PathVariable String id) {
        return shippingService.getShipmentDetails(id);
    }

    @PostMapping("/createShipment")
    public ResponseEntity<?> createShipment(@RequestBody ShipmentDTO newShipment) {
//        return shippingService.createShipment(newShipment);
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
            shippingRepo.save(shipment);
            return ResponseEntity.ok("Created Successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
