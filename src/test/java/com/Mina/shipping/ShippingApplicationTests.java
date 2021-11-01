package com.Mina.shipping;

import com.Mina.shipping.controllers.ShippingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShippingApplicationTests {

	@Autowired
	private ShippingController shippingController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(shippingController).isNotNull();
	}

}
