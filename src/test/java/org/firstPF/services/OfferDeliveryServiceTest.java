package org.firstPF.services;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.OfferDelivery;
import org.firstPF.repositories.OfferDeliveryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext
public class OfferDeliveryServiceTest extends ApplicationTests {

    @Autowired
    private OfferDeliveryService offerDeliveryService;

    @Autowired
    private OfferDeliveryRepository offerDeliveryRepository;

    @BeforeEach
    public void setUp() {
        offerDeliveryRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetOfferDeliveryById() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.of(2023, 7, 15));
        offerDelivery.setAccepted(true);

        OfferDelivery createdOfferDelivery = offerDeliveryService.createOfferDelivery(offerDelivery);
        Long createdOfferDeliveryId = createdOfferDelivery.getId();

        Optional<OfferDelivery> result = offerDeliveryService.getOfferDeliveryById(createdOfferDeliveryId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(offerDelivery.getDeliveryDate(), result.get().getDeliveryDate());
        Assertions.assertEquals(offerDelivery.isAccepted(), result.get().isAccepted());
    }

    @Test
    public void testGetAllOfferDeliveries() {
        OfferDelivery offerDelivery1 = new OfferDelivery();
        offerDelivery1.setDeliveryDate(LocalDate.of(2023, 7, 15));
        offerDelivery1.setAccepted(true);
        offerDeliveryService.createOfferDelivery(offerDelivery1);

        OfferDelivery offerDelivery2 = new OfferDelivery();
        offerDelivery2.setDeliveryDate(LocalDate.of(2023, 7, 20));
        offerDelivery2.setAccepted(false);
        offerDeliveryService.createOfferDelivery(offerDelivery2);

        List<OfferDelivery> offerDeliveries = offerDeliveryService.getAllOfferDeliveries();

        Assertions.assertEquals(2, offerDeliveries.size());
        Assertions.assertEquals(offerDelivery1.getDeliveryDate(), offerDeliveries.get(0).getDeliveryDate());
        Assertions.assertEquals(offerDelivery2.getDeliveryDate(), offerDeliveries.get(1).getDeliveryDate());
        Assertions.assertEquals(offerDelivery1.isAccepted(), offerDeliveries.get(0).isAccepted());
        Assertions.assertEquals(offerDelivery2.isAccepted(), offerDeliveries.get(1).isAccepted());

    }

    @Test
    public void testDeleteOfferDelivery() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.of(2023, 7, 15));
        offerDelivery.setAccepted(true);
        offerDeliveryService.createOfferDelivery(offerDelivery);

        Long offerDeliveryId = offerDelivery.getId();
        offerDeliveryService.deleteOfferDelivery(offerDeliveryId);

        Optional<OfferDelivery> deletedOfferDelivery = offerDeliveryService.getOfferDeliveryById(offerDeliveryId);
        Assertions.assertFalse(deletedOfferDelivery.isPresent());
    }
}
