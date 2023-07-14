package org.firstPF.repositories;

import org.firstPF.entities.OfferDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferDeliveryRepository extends JpaRepository<OfferDelivery, Long> {

    Optional<OfferDelivery> findById(Long id);

    OfferDelivery save(OfferDelivery offerDelivery);
}
