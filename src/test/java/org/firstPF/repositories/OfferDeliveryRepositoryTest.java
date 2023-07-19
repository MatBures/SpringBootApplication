package org.firstPF.repositories;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.OfferDelivery;
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
    public void testSaveAndFindById() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.of(2023, 1, 1));
        offerDelivery.setAccepted(true);
        offerDeliveryRepository.save(offerDelivery);

        Optional<OfferDelivery> result = offerDeliveryRepository.findById(offerDelivery.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(offerDelivery.getDeliveryDate(), result.get().getDeliveryDate());
        Assertions.assertEquals(offerDelivery.isAccepted(), result.get().isAccepted());
    }
}
