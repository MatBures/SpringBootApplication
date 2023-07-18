package org.firstPF.repositoriesTest;

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
import java.util.Optional;

@SpringBootTest
@DirtiesContext
public class OfferDeliveryRepositoryTest extends ApplicationTests {

    @Autowired
    private OfferDeliveryRepository offerDeliveryRepository;

    @BeforeEach
    public void setUp() {
        offerDeliveryRepository.deleteAll();
    }

    @Test
    public void testFindById() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.of(2023, 1, 1));
        offerDelivery.setAccepted(true);
        offerDeliveryRepository.save(offerDelivery);

        Optional<OfferDelivery> result = offerDeliveryRepository.findById(offerDelivery.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(offerDelivery.getDeliveryDate(), result.get().getDeliveryDate());
        Assertions.assertEquals(offerDelivery.isAccepted(), result.get().isAccepted());
    }

    @Test
    public void testSave() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.of(2023, 1, 1));
        offerDelivery.setAccepted(true);

        OfferDelivery savedOfferDelivery = offerDeliveryRepository.save(offerDelivery);

        Assertions.assertNotNull(savedOfferDelivery.getId());

        OfferDelivery retrievedOfferDelivery = offerDeliveryRepository.findById(savedOfferDelivery.getId()).get();
        Assertions.assertEquals(offerDelivery.getDeliveryDate(), retrievedOfferDelivery.getDeliveryDate());
        Assertions.assertEquals(offerDelivery.isAccepted(), retrievedOfferDelivery.isAccepted());
    }
}
