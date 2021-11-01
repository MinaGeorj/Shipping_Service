package com.Mina.shipping.controllers;

import com.Mina.shipping.Utils.CompanyId;
import com.Mina.shipping.Utils.ServiceId;
import com.Mina.shipping.Utils.ShipmentDTO;
import com.Mina.shipping.models.ShippingModel;
import com.Mina.shipping.repositories.ShippingRepository;
import com.Mina.shipping.services.ShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class shippingControllerTest {

    @MockBean
    private ShippingService shippingService;

    @MockBean
    private ShippingRepository shippingRepo;

    @Autowired
    private MockMvc mvc;

    @Test
    public void createShipments() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ShipmentDTO shipment = new ShipmentDTO();
        shipment.setCompanyId(CompanyId.FEDEX);
        shipment.setServiceId(ServiceId.FEDEXAIR);
        mvc.perform(post("/api/createShipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(shipment)))
            .andExpect(status().isOk());
    }

    @Test
    public void createInvalidShipment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ShipmentDTO shipment = new ShipmentDTO();

        mvc.perform(post("/api/createShipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(shipment)))
                .andExpect(status().isBadRequest())
                .andReturn();

        shipment.setCompanyId(CompanyId.FEDEX);
        shipment.setServiceId(ServiceId.UPS2DAY);
        mvc.perform(post("/api/createShipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(shipment)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getShipments() throws Exception {
        mvc.perform(get("/api/shipments"))
                .andExpect(status().isOk());
    }

}
