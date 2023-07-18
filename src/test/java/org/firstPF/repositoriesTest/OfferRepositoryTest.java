package org.firstPF.repositoriesTest;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Offer;
import org.firstPF.repositories.OfferRepository;
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
    public void testFindById() {
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

    @Test
    public void testSave() {
        Offer offer = new Offer();
        offer.setTitle("New Offer");
        offer.setDescription("This is a new offer");
        offer.setCost(200);
        offer.setStatus("Inactive");

        Offer savedOffer = offerRepository.save(offer);

        Assertions.assertNotNull(savedOffer.getId());

        Offer retrievedOffer = offerRepository.findById(savedOffer.getId()).get();
        Assertions.assertEquals(offer.getTitle(), retrievedOffer.getTitle());
        Assertions.assertEquals(offer.getDescription(), retrievedOffer.getDescription());
        Assertions.assertEquals(offer.getCost(), retrievedOffer.getCost());
        Assertions.assertEquals(offer.getStatus(), retrievedOffer.getStatus());
    }
}
