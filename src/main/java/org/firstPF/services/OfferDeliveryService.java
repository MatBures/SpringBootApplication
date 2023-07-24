package org.firstPF.services;

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

    @Autowired
    public OfferDeliveryService(OfferDeliveryRepository offerDeliveryRepository) {
        this.offerDeliveryRepository = offerDeliveryRepository;
    }

    public OfferDelivery createOfferDelivery(OfferDelivery offerDelivery) {
        return offerDeliveryRepository.save(offerDelivery);
    }

    public List<OfferDelivery> getAllOfferDeliveries() {
        return offerDeliveryRepository.findAll();
    }

    public Optional<OfferDelivery> getOfferDeliveryById(Long id) {
        return offerDeliveryRepository.findById(id);
    }

    public void deleteOfferDelivery(Long id) {
        offerDeliveryRepository.deleteById(id);
    }
}
