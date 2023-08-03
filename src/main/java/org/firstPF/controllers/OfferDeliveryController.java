package org.firstPF.controllers;

import org.firstPF.entities.Offer;
import org.firstPF.entities.OfferDelivery;
import org.firstPF.services.OfferDeliveryService;
import org.firstPF.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offer-deliveries")
public class OfferDeliveryController {
    private final OfferDeliveryService offerDeliveryService;
    private final OfferService offerService;

    @Autowired
    public OfferDeliveryController(OfferDeliveryService offerDeliveryService, OfferService offerService) {
        this.offerDeliveryService = offerDeliveryService;
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferDelivery> createOfferDelivery(@RequestBody OfferDelivery offerDelivery) {
            OfferDelivery createdOfferDelivery = offerDeliveryService.createOfferDelivery(offerDelivery);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOfferDelivery);
        }

    @GetMapping
    public ResponseEntity<List<OfferDelivery>> getAllOfferDeliveries() {
        List<OfferDelivery> allOfferDeliveries = offerDeliveryService.getAllOfferDeliveries();
        return ResponseEntity.ok(allOfferDeliveries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDelivery> getOfferDeliveryById(@PathVariable Long id) {
        OfferDelivery offerDelivery = offerDeliveryService.getOfferDeliveryById(id).orElse(null);
        if (offerDelivery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(offerDelivery);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferDelivery> updateOfferDelivery(@PathVariable Long id, @RequestBody OfferDelivery updatedOfferDelivery) {
        OfferDelivery updatedOfferDeliveryEntity = offerDeliveryService.updateOfferDelivery(id, updatedOfferDelivery);
        if (updatedOfferDeliveryEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOfferDeliveryEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferDelivery(@PathVariable Long id) {
        Optional<OfferDelivery> offerDelivery = offerDeliveryService.getOfferDeliveryById(id);
        if (offerDelivery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        offerDeliveryService.deleteOfferDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
