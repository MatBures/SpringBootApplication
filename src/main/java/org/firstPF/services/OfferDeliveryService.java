package org.firstPF.services;

import jakarta.persistence.EntityNotFoundException;
import org.firstPF.entities.Offer;
import org.firstPF.entities.OfferDelivery;
import org.firstPF.repositories.OfferDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferDeliveryService {
    private final OfferDeliveryRepository offerDeliveryRepository;

    private final OfferService offerService;

    @Autowired
    public OfferDeliveryService(OfferDeliveryRepository offerDeliveryRepository,OfferService offerService) {
        this.offerDeliveryRepository = offerDeliveryRepository;
        this.offerService = offerService;
    }

    public OfferDelivery createOfferDelivery(OfferDelivery offerDelivery) {
        Long offerId = offerDelivery.getOffer().getId();
        if (offerId == null) {
            throw new IllegalArgumentException("OfferId is required.");
        }

        Optional<Offer> existingOffer = offerService.getOfferById(offerId);
        if (existingOffer.isEmpty()) {
            throw new IllegalArgumentException("Offer with the provided OfferId does not exist.");
        }

        return offerDeliveryRepository.save(offerDelivery);
    }

    public List<OfferDelivery> getAllOfferDeliveries() {
        return offerDeliveryRepository.findAll();
    }

    public Optional<OfferDelivery> getOfferDeliveryById(Long id) {
        return offerDeliveryRepository.findById(id);
    }

    public OfferDelivery updateOfferDelivery(Long id, OfferDelivery updatedOfferDelivery) {
        OfferDelivery existingOfferDelivery = offerDeliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OfferDelivery with id " + id + " not found"));

        Long offerId = updatedOfferDelivery.getOffer().getId();
        if (offerId == null) {
            throw new IllegalArgumentException("OfferId is required.");
        }

        Offer existingOffer = offerService.getOfferById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with the provided OfferId does not exist."));

        existingOfferDelivery.setDeliveryDate(updatedOfferDelivery.getDeliveryDate());
        existingOfferDelivery.setAccepted(updatedOfferDelivery.isAccepted());
        existingOfferDelivery.setOffer(existingOffer);

        return offerDeliveryRepository.save(existingOfferDelivery);
    }

    public void deleteOfferDelivery(Long id) {
        offerDeliveryRepository.deleteById(id);
    }
}
