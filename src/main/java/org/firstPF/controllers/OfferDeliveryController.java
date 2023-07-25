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
        Long offerId = offerDelivery.getOffer().getId();
        if (offerId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Offer> existingOffer = offerService.getOfferById(offerId);
        if (existingOffer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OfferDelivery createdOfferDelivery = offerDeliveryService.createOfferDelivery(offerDelivery);
        return new ResponseEntity<>(createdOfferDelivery, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OfferDelivery>> getAllOffers() {
        List<OfferDelivery> allOfferDeliveries = offerDeliveryService.getAllOfferDeliveries();
        return new ResponseEntity<>(allOfferDeliveries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDelivery> getOfferDeliveryById(@PathVariable Long id) {
        OfferDelivery offerDelivery = offerDeliveryService.getOfferDeliveryById(id).orElse(null);
        if (offerDelivery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(offerDelivery, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferDelivery> updateOfferDelivery(@PathVariable Long id, @RequestBody OfferDelivery updatedOfferDelivery) {
        Long offerId = updatedOfferDelivery.getOffer().getId();
        if (offerId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Offer> existingOffer = offerService.getOfferById(offerId);
        if (existingOffer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OfferDelivery existingOfferDelivery = offerDeliveryService.getOfferDeliveryById(id).orElse(null);
        if (existingOfferDelivery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the offerDelivery properties with the updatedOfferDelivery
        existingOfferDelivery.setDeliveryDate(updatedOfferDelivery.getDeliveryDate());
        existingOfferDelivery.setAccepted(updatedOfferDelivery.isAccepted());
        existingOfferDelivery.setOffer(existingOffer.get());

        OfferDelivery updatedOfferDeliveryEntity = offerDeliveryService.createOfferDelivery(existingOfferDelivery);
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