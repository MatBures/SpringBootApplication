package org.firstPF.controllers;

import org.firstPF.entities.Customer;
import org.firstPF.entities.Employee;
import org.firstPF.entities.Offer;
import org.firstPF.entities.Provider;
import org.firstPF.repositories.EmployeeRepository;
import org.firstPF.services.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OfferControllerTest {

    @Mock
    private OfferService offerService;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private OfferController offerController;

    private Provider provider;
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        provider = new Provider();
        provider.setId(1L);
        provider.setName("ABC Electronics");
        provider.setAddress("London 583/5");
        provider.setEmail("ABCElectronics@example.com");
        provider.setContactNumber("1234567890");

        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Smith");
        customer.setEmail("john.smith@example.com");
        customer.setContactNumber("9876543210");
    }

    @Test
    void testCreateOfferWithInvalidProviderId() {
        Offer offer = new Offer();
        offer.setTitle("Invalid Provider Offer");
        offer.setDescription("This is an offer with an invalid providerId.");
        offer.setCost(200);
        offer.setStatus("Unaccepted");
        offer.setCustomer(customer);

        when(offerService.createOffer(any(Offer.class)))
                .thenThrow(new IllegalArgumentException("ProviderId is required."));

        ResponseEntity<?> response = offerController.createOffer(offer);

        verify(offerService, times(1)).createOffer(any(Offer.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() instanceof String;
        assert response.getBody().equals("ProviderId is required.");
    }

    @Test
    void testCreateOfferWithInvalidCustomerId() {
        Offer offer = new Offer();
        offer.setTitle("Invalid Customer Offer");
        offer.setDescription("This is an offer with an invalid customerId.");
        offer.setCost(150);
        offer.setStatus("Unaccepted");
        offer.setProvider(provider);

        when(offerService.createOffer(any(Offer.class)))
                .thenThrow(new IllegalArgumentException("CustomerId is required."));

        ResponseEntity<?> response = offerController.createOffer(offer);

        verify(offerService, times(1)).createOffer(any(Offer.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() instanceof String;
        assert response.getBody().equals("CustomerId is required.");
    }

    @Test
    void testCreateOfferWithValidProviderAndCustomerIds() {
        Offer offer = new Offer();
        offer.setTitle("Sample Offer");
        offer.setDescription("This is a sample offer.");
        offer.setCost(100);
        offer.setStatus("Unaccepted");
        offer.setProvider(provider);
        offer.setCustomer(customer);

        when(offerService.createOffer(any(Offer.class))).thenReturn(offer);

        ResponseEntity<?> response = offerController.createOffer(offer);

        verify(offerService, times(1)).createOffer(any(Offer.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() instanceof Offer;
        Offer createdOffer = (Offer) response.getBody();
        assert createdOffer != null;
        assert createdOffer.getTitle().equals(offer.getTitle());
    }

    @Test
    void testGetAllOffers() {
        List<Offer> offers = new ArrayList<>();
        Offer offer1 = new Offer();
        offer1.setTitle("Offer 1");
        offer1.setDescription("This is offer 1 description.");
        offer1.setCost(200L);
        offer1.setStatus("Unaccepted");
        offer1.setProvider(provider);
        offer1.setCustomer(customer);
        offers.add(offer1);

        Offer offer2 = new Offer();
        offer2.setTitle("Offer 2");
        offer2.setDescription("This is offer 2 description.");
        offer2.setCost(300L);
        offer2.setStatus("Accepted");
        offer2.setProvider(provider);
        offer2.setCustomer(customer);
        offers.add(offer2);

        when(offerService.getAllOffers()).thenReturn(offers);

        ResponseEntity<List<Offer>> response = offerController.getAllOffers();

        verify(offerService, times(1)).getAllOffers();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == offers.size();
    }

    @Test
    void testGetOfferById() {
        Long offerId = 1L;
        Offer offer = new Offer();
        offer.setTitle("Sample Offer");
        offer.setDescription("This is a sample offer.");
        offer.setCost(100);
        offer.setStatus("Accepted");
        offer.setProvider(provider);
        offer.setCustomer(customer);

        when(offerService.getOfferById(offerId)).thenReturn(Optional.of(offer));

        ResponseEntity<Offer> response = offerController.getOfferById(offerId);

        verify(offerService, times(1)).getOfferById(offerId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getTitle().equals(offer.getTitle());
    }

    @Test
    void testGetOfferByIdNotFound() {
        Long offerId = 999L;
        when(offerService.getOfferById(offerId)).thenReturn(Optional.empty());

        ResponseEntity<Offer> response = offerController.getOfferById(offerId);

        verify(offerService, times(1)).getOfferById(offerId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateOfferWithInvalidCustomerId() {
        Long offerId = 1L;
        Offer existingOffer = new Offer();
        existingOffer.setTitle("Sample Offer");
        existingOffer.setDescription("This is a sample offer.");
        existingOffer.setCost(100);
        existingOffer.setStatus("Accepted");
        existingOffer.setProvider(provider);
        existingOffer.setCustomer(customer);

        Offer updatedOffer = new Offer();
        updatedOffer.setTitle("Updated Offer");
        updatedOffer.setDescription("This is an updated offer.");
        updatedOffer.setCost(200);
        updatedOffer.setStatus("Unaccepted");
        updatedOffer.setProvider(provider);

        when(offerService.getOfferById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerService.updateOffer(eq(offerId), any(Offer.class))).thenReturn(null);

        ResponseEntity<?> response = offerController.updateOffer(offerId, updatedOffer);

        verify(offerService, times(1)).updateOffer(eq(offerId), eq(updatedOffer));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateOfferWithValidProviderAndCustomerIds() {
        Long offerId = 1L;
        Offer existingOffer = new Offer();
        existingOffer.setTitle("Sample Offer");
        existingOffer.setDescription("This is a sample offer.");
        existingOffer.setCost(100);
        existingOffer.setStatus("Accepted");
        existingOffer.setProvider(provider);
        existingOffer.setCustomer(customer);

        Offer updatedOffer = new Offer();
        updatedOffer.setTitle("Updated Offer");
        updatedOffer.setDescription("This is an updated offer.");
        updatedOffer.setCost(200);
        updatedOffer.setStatus("Unaccepted");
        updatedOffer.setProvider(provider);
        updatedOffer.setCustomer(customer);

        when(offerService.getOfferById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerService.updateOffer(eq(offerId), any(Offer.class))).thenReturn(updatedOffer);

        ResponseEntity<?> response = offerController.updateOffer(offerId, updatedOffer);

        verify(offerService, times(1)).updateOffer(eq(offerId), any(Offer.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() instanceof Offer;
        Offer updatedOfferEntity = (Offer) response.getBody();
        assert updatedOfferEntity != null;
        assert updatedOfferEntity.getTitle().equals(updatedOffer.getTitle());
    }

    @Test
    void testUpdateOfferNotFound() {
        Long offerId = 999L;
        Offer updatedOffer = new Offer();
        updatedOffer.setTitle("Updated Offer");
        updatedOffer.setDescription("This is an updated offer.");
        updatedOffer.setCost(200);
        updatedOffer.setStatus("Unaccepted");
        updatedOffer.setProvider(provider);
        updatedOffer.setCustomer(customer);

        when(offerService.getOfferById(offerId)).thenReturn(Optional.empty());
        when(offerService.updateOffer(anyLong(), any(Offer.class))).thenReturn(null);

        ResponseEntity<?> response = offerController.updateOffer(offerId, updatedOffer);

        verify(offerService, times(1)).updateOffer(eq(offerId), eq(updatedOffer));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    void testDeleteOffer() {
        Long offerId = 1L;

        when(offerService.getOfferById(offerId)).thenReturn(Optional.of(new Offer()));

        ResponseEntity<Void> response = offerController.deleteOffer(offerId);

        verify(offerService, times(1)).getOfferById(offerId);
        verify(offerService, times(1)).deleteOffer(offerId);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    void testDeleteOfferNotFound() {
        Long offerId = 999L;

        when(offerService.getOfferById(offerId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = offerController.deleteOffer(offerId);

        verify(offerService, times(1)).getOfferById(offerId);
        verify(offerService, never()).deleteOffer(offerId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testAssignEmployeeToOffer() {
        Long offerId = 1L;
        Long employeeId = 2L;

        doNothing().when(offerService).assignEmployeeToOffer(offerId, employeeId);

        ResponseEntity<String> response = offerController.assignEmployeeToOffer(offerId, employeeId);

        verify(offerService, times(1)).assignEmployeeToOffer(offerId, employeeId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().equals("Employee assigned to offer successfully");
    }

    @Test
    void testAssignEmployeeToOfferWithInvalidOfferId() {
        Long invalidOfferId = 999L;
        Long employeeId = 2L;

        Mockito.doThrow(new IllegalArgumentException("Offer doesn't exist."))
                .when(offerService).assignEmployeeToOffer(invalidOfferId, employeeId);

        ResponseEntity<String> response = offerController.assignEmployeeToOffer(invalidOfferId, employeeId);

        verify(offerService, times(1)).assignEmployeeToOffer(invalidOfferId, employeeId);
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() instanceof String;
        assert response.getBody().equals("Offer doesn't exist.");
    }

    @Test
    void testAssignEmployeeToOfferWithInvalidEmployeeId() {
        Long offerId = 1L;
        Long invalidEmployeeId = 999L;

        Mockito.doThrow(new IllegalArgumentException("Employee doesn't exist."))
                .when(offerService).assignEmployeeToOffer(offerId, invalidEmployeeId);

        ResponseEntity<String> response = offerController.assignEmployeeToOffer(offerId, invalidEmployeeId);

        verify(offerService, times(1)).assignEmployeeToOffer(offerId, invalidEmployeeId);
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() instanceof String;
        assert response.getBody().equals("Employee doesn't exist.");
    }
}