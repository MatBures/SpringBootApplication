package org.firstPF.controllers;

import org.firstPF.services.OfferDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferDeliveryController {
    private final OfferDeliveryService offerDeliveryService;

    @Autowired
    public OfferDeliveryController(OfferDeliveryService offerDeliveryService) {
        this.offerDeliveryService = offerDeliveryService;
    }
}