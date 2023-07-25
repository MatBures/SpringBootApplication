package org.firstPF.services;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Customer;
import org.firstPF.entities.Employee;
import org.firstPF.entities.Offer;
import org.firstPF.entities.Provider;
import org.firstPF.repositories.OfferRepository;
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
public class OfferServiceTest extends ApplicationTests {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    private Provider provider;
    private Customer customer;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        offerRepository.deleteAll();

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

        employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setEmail("jane.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1995, 3, 20));
        employee.setContactNumber("5678901234");
        employee.setProvider(provider);
        employee = employeeService.createEmployee(employee);
    }

    @Test
    public void testCreateAndGetOfferById() {
        Offer offer = new Offer();
        offer.setTitle("Sample Offer");
        offer.setDescription("This is a sample offer.");
        offer.setCost(100);
        offer.setStatus("Active");
        offer.setProvider(provider);
        offer.setCustomer(customer);
        offer = offerService.createOffer(offer);
        Long createdOfferId = offer.getId();

        Optional<Offer> result = offerService.getOfferById(createdOfferId);

        Assertions.assertTrue(result.isPresent());

        Offer resultOffer = result.get();

        Assertions.assertEquals(offer.getTitle(), resultOffer.getTitle());
        Assertions.assertEquals(offer.getDescription(), resultOffer.getDescription());
        Assertions.assertEquals(offer.getCost(), resultOffer.getCost());
        Assertions.assertEquals(offer.getStatus(), resultOffer.getStatus());

    }
    @Test
    public void testGetAllOffers() {
        Offer offer1 = new Offer();
        offer1.setTitle("Offer 1");
        offer1.setDescription("This is offer 1 description.");
        offer1.setCost(200L);
        offer1.setStatus("Active");
        offer1.setProvider(provider);
        offer1.setCustomer(customer);
        offer1 = offerService.createOffer(offer1);

        Offer offer2 = new Offer();
        offer2.setTitle("Offer 2");
        offer2.setDescription("This is offer 2 description.");
        offer2.setCost(300L);
        offer2.setStatus("Active");
        offer2.setProvider(provider);
        offer2.setCustomer(customer);
        offer2 = offerService.createOffer(offer2);


        List<Offer> offers = offerService.getAllOffers();

        Assertions.assertEquals(2, offers.size());
        Assertions.assertEquals(offer1.getTitle(), offers.get(0).getTitle());
        Assertions.assertEquals(offer2.getTitle(), offers.get(1).getTitle());
    }

    @Test
    public void testDeleteOffer() {
        Offer offer = new Offer();
        offer.setTitle("Offer 1");
        offer.setDescription("This is offer 1 description.");
        offer.setCost(200L);
        offer.setStatus("Active");
        offer.setProvider(provider);
        offer.setCustomer(customer);
        offer = offerService.createOffer(offer);
        Long offerId = offer.getId();
        offerService.deleteOffer(offerId);

        Optional<Offer> deletedOffer = offerService.getOfferById(offerId);
        Assertions.assertFalse(deletedOffer.isPresent());
    }
}
