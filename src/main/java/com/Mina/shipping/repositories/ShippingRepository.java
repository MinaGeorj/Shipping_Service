package com.Mina.shipping.repositories;

import com.Mina.shipping.models.ShippingModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends MongoRepository<ShippingModel, Integer> {
    List<ShippingModel> findAll();
    ShippingModel findById(int id);
}

