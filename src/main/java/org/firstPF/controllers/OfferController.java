package org.firstPF.controllers;

import org.firstPF.entities.Offer;
import org.firstPF.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody Offer offer) {
        // Check if providerId and customerId are provided
        if (offer.getProvider() == null || offer.getProvider().getId() == null) {
            return ResponseEntity.badRequest().body("ProviderId is required.");
        }

        if (offer.getCustomer() == null || offer.getCustomer().getId() == null) {
            return ResponseEntity.badRequest().body("CustomerId is required.");
        }

        Offer createdOffer = offerService.createOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> allOffers = offerService.getAllOffers();
        return new ResponseEntity<>(allOffers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Long id, @RequestBody Offer updatedOffer) {
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if providerId and customerId are provided in the updatedOffer
        if (updatedOffer.getProvider() == null || updatedOffer.getProvider().getId() == null) {
            return ResponseEntity.badRequest().body("ProviderId is required for updating an offer.");
        }

        if (updatedOffer.getCustomer() == null || updatedOffer.getCustomer().getId() == null) {
            return ResponseEntity.badRequest().body("CustomerId is required for updating an offer.");
        }

        // Update the offer properties with the updatedOffer
        offer.setTitle(updatedOffer.getTitle());
        offer.setDescription(updatedOffer.getDescription());
        offer.setCost(updatedOffer.getCost());
        offer.setStatus(updatedOffer.getStatus());
        offer.setProvider(updatedOffer.getProvider());
        offer.setCustomer(updatedOffer.getCustomer());

        Offer updatedOfferEntity = offerService.createOffer(offer);
        return ResponseEntity.ok(updatedOfferEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        Optional<Offer> offer = offerService.getOfferById(id);
        if (offer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }
}