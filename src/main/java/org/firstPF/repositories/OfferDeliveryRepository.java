package org.firstPF.repositories;

import org.firstPF.entities.OfferDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OfferDeliveryRepository extends JpaRepository<OfferDelivery, Long> {
}
