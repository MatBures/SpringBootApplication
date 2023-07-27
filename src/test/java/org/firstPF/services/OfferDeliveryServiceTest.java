package org.firstPF.services;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Customer;
import org.firstPF.entities.Offer;
import org.firstPF.entities.OfferDelivery;
import org.firstPF.entities.Provider;
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

    @Autowired
    private OfferService offerService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CustomerService customerService;


    private Provider provider;
    private Customer customer;
    private Offer offer;

    @BeforeEach
    public void setUp() {
        offerDeliveryRepository.deleteAll();

        provider = new Provider();
        provider.setName("ABC Electronics");
        provider.setAddress("London 583/5");
        provider.setEmail("ABCElectronics@example.com");
        provider.setContactNumber("1234567890");
        provider = providerService.createProvider(provider);

        customer = new Customer();
        customer.setName("John Smith");
        customer.setEmail("john.smith@example.com");
        customer.setContactNumber("9876543210");
        customer = customerService.createCustomer(customer);

        offer = new Offer();
        offer.setTitle("Sample Offer");
        offer.setDescription("This is a sample offer.");
        offer.setCost(100);
        offer.setStatus("Accepted");
        offer.setProvider(provider);
        offer.setCustomer(customer);

        offer = offerService.createOffer(offer);
    }

    @Test
    public void testCreateAndGetOfferDeliveryById() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.of(2023, 7, 15));
        offerDelivery.setAccepted(true);
        offerDelivery.setOffer(offer);

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
        offerDelivery1.setOffer(offer);
        offerDeliveryService.createOfferDelivery(offerDelivery1);

        OfferDelivery offerDelivery2 = new OfferDelivery();
        offerDelivery2.setDeliveryDate(LocalDate.of(2023, 7, 20));
        offerDelivery2.setAccepted(false);
        offerDelivery2.setOffer(offer);
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
        offerDelivery.setOffer(offer);
        offerDeliveryService.createOfferDelivery(offerDelivery);

        Long offerDeliveryId = offerDelivery.getId();
        offerDeliveryService.deleteOfferDelivery(offerDeliveryId);

        Optional<OfferDelivery> deletedOfferDelivery = offerDeliveryService.getOfferDeliveryById(offerDeliveryId);
        Assertions.assertFalse(deletedOfferDelivery.isPresent());
    }
}
