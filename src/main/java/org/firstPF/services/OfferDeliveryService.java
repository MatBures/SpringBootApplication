package org.firstPF.services;

import org.firstPF.repositories.OfferDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferDeliveryService {
    private final OfferDeliveryRepository offerDeliveryRepository;

    @Autowired
    public OfferDeliveryService(OfferDeliveryRepository offerDeliveryRepository) {
        this.offerDeliveryRepository = offerDeliveryRepository;
    }
}
