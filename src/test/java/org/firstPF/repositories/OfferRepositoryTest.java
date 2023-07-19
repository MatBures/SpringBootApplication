package org.firstPF.repositories;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Offer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@SpringBootTest
@DirtiesContext
public class OfferRepositoryTest extends ApplicationTests {

    @Autowired
    private OfferRepository offerRepository;

    @BeforeEach
    public void setUp() {
        offerRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindById() {
        Offer offer = new Offer();
        offer.setTitle("Test Offer");
        offer.setDescription("This is a test offer");
        offer.setCost(100);
        offer.setStatus("Active");
        offerRepository.save(offer);

        Optional<Offer> result = offerRepository.findById(offer.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(offer.getTitle(), result.get().getTitle());
        Assertions.assertEquals(offer.getDescription(), result.get().getDescription());
        Assertions.assertEquals(offer.getCost(), result.get().getCost());
        Assertions.assertEquals(offer.getStatus(), result.get().getStatus());
    }
}
