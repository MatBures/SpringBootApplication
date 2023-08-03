package org.firstPF.controllers;

import org.firstPF.entities.Employee;
import org.firstPF.entities.Offer;
import org.firstPF.repositories.EmployeeRepository;
import org.firstPF.services.EmployeeService;
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
    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public OfferController(OfferService offerService,
                           EmployeeService employeeService,
                           EmployeeRepository employeeRepository) {
        this.offerService = offerService;
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody Offer offer) {
            Offer createdOffer = offerService.createOffer(offer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @PostMapping("/{offerId}/employees")
    public ResponseEntity<String> assignEmployeeToOffer(@PathVariable Long offerId, @RequestBody Long employeeId) {
            offerService.assignEmployeeToOffer(offerId, employeeId);
            return ResponseEntity.ok("Employee assigned to offer successfully");
        }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> allOffers = offerService.getAllOffers();
        return ResponseEntity.ok(allOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(offer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Long id, @RequestBody Offer updatedOffer) {
        Offer updatedOfferEntity = offerService.updateOffer(id, updatedOffer);
        if (updatedOfferEntity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
