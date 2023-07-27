package org.firstPF.controllers;

import org.firstPF.entities.Offer;
import org.firstPF.entities.OfferDelivery;
import org.firstPF.services.OfferDeliveryService;
import org.firstPF.services.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OfferDeliveryControllerTest {

    @Mock
    private OfferDeliveryService offerDeliveryService;

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferDeliveryController offerDeliveryController;

    private Offer offer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        offer = new Offer();
        offer.setId(1L);
        offer.setTitle("Sample Offer");
        offer.setDescription("This is a sample offer.");
        offer.setCost(100);
        offer.setStatus("Accepted");
    }

    @Test
    void testCreateOfferDeliveryWithInvalidOfferId() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.now());
        offerDelivery.setAccepted(false);

        Mockito.when(offerDeliveryService.createOfferDelivery(ArgumentMatchers.any(OfferDelivery.class)))
                .thenThrow(new IllegalArgumentException("OfferId is required."));

        ResponseEntity<OfferDelivery> response = offerDeliveryController.createOfferDelivery(offerDelivery);

        Mockito.verify(offerDeliveryService, Mockito.times(1)).createOfferDelivery(ArgumentMatchers.any(OfferDelivery.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() == null;
    }

    @Test
    void testCreateOfferDeliveryWithNonExistingOffer() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.now());
        offerDelivery.setAccepted(false);
        offerDelivery.setOffer(offer);

        Mockito.when(offerDeliveryService.createOfferDelivery(ArgumentMatchers.any(OfferDelivery.class)))
                .thenThrow(new IllegalArgumentException("Offer with the provided OfferId does not exist."));

        ResponseEntity<OfferDelivery> response = offerDeliveryController.createOfferDelivery(offerDelivery);

        Mockito.verify(offerDeliveryService, Mockito.times(1)).createOfferDelivery(ArgumentMatchers.any(OfferDelivery.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() == null;
    }

    @Test
    void testCreateOfferDeliveryWithValidOfferId() {
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setDeliveryDate(LocalDate.now());
        offerDelivery.setAccepted(true);
        offerDelivery.setOffer(offer);

        when(offerService.getOfferById(offer.getId())).thenReturn(Optional.of(offer));
        when(offerDeliveryService.createOfferDelivery(any(OfferDelivery.class))).thenReturn(offerDelivery);

        ResponseEntity<OfferDelivery> response = offerDeliveryController.createOfferDelivery(offerDelivery);

        verify(offerDeliveryService, times(1)).createOfferDelivery(any(OfferDelivery.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() instanceof OfferDelivery;
        OfferDelivery createdOfferDelivery = response.getBody();
        assert createdOfferDelivery != null;
        assert createdOfferDelivery.getOffer().getTitle().equals(offer.getTitle());
    }

    @Test
    void testGetAllOfferDeliveries() {
        List<OfferDelivery> offerDeliveries = new ArrayList<>();
        OfferDelivery offerDelivery1 = new OfferDelivery();
        offerDelivery1.setDeliveryDate(LocalDate.now());
        offerDelivery1.setAccepted(true);
        offerDelivery1.setOffer(offer);
        offerDeliveries.add(offerDelivery1);

        OfferDelivery offerDelivery2 = new OfferDelivery();
        offerDelivery2.setDeliveryDate(LocalDate.now().plusDays(1));
        offerDelivery2.setAccepted(false);
        offerDelivery2.setOffer(offer);
        offerDeliveries.add(offerDelivery2);

        when(offerDeliveryService.getAllOfferDeliveries()).thenReturn(offerDeliveries);

        ResponseEntity<List<OfferDelivery>> response = offerDeliveryController.getAllOfferDeliveries();

        verify(offerDeliveryService, times(1)).getAllOfferDeliveries();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == offerDeliveries.size();
    }

    @Test
    void testGetOfferDeliveryById() {
        Long offerDeliveryId = 1L;
        OfferDelivery offerDelivery = new OfferDelivery();
        offerDelivery.setId(offerDeliveryId);
        offerDelivery.setDeliveryDate(LocalDate.now());
        offerDelivery.setAccepted(true);
        offerDelivery.setOffer(offer);

        when(offerDeliveryService.getOfferDeliveryById(offerDeliveryId)).thenReturn(Optional.of(offerDelivery));

        ResponseEntity<OfferDelivery> response = offerDeliveryController.getOfferDeliveryById(offerDeliveryId);

        verify(offerDeliveryService, times(1)).getOfferDeliveryById(offerDeliveryId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getId().equals(offerDeliveryId);
    }

    @Test
    void testGetOfferDeliveryByIdNotFound() {
        Long offerDeliveryId = 999L;
        when(offerDeliveryService.getOfferDeliveryById(offerDeliveryId)).thenReturn(Optional.empty());

        ResponseEntity<OfferDelivery> response = offerDeliveryController.getOfferDeliveryById(offerDeliveryId);

        verify(offerDeliveryService, times(1)).getOfferDeliveryById(offerDeliveryId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateOfferDeliveryWithNonExistingOffer() {
        Long offerDeliveryId = 1L;
        OfferDelivery existingOfferDelivery = new OfferDelivery();
        existingOfferDelivery.setId(offerDeliveryId);
        existingOfferDelivery.setDeliveryDate(LocalDate.now());
        existingOfferDelivery.setAccepted(true);
        existingOfferDelivery.setOffer(offer);

        OfferDelivery updatedOfferDelivery = new OfferDelivery();
        updatedOfferDelivery.setDeliveryDate(LocalDate.now());
        updatedOfferDelivery.setAccepted(false);
        updatedOfferDelivery.setOffer(offer);

        when(offerService.getOfferById(offer.getId())).thenReturn(Optional.empty());

        ResponseEntity<OfferDelivery> response = offerDeliveryController.updateOfferDelivery(offerDeliveryId, updatedOfferDelivery);

        verify(offerDeliveryService, never()).createOfferDelivery(any(OfferDelivery.class));
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateOfferDeliveryWithNonExistingOfferDelivery() {
        Long offerDeliveryId = 999L;
        OfferDelivery updatedOfferDelivery = new OfferDelivery();
        updatedOfferDelivery.setDeliveryDate(LocalDate.now());
        updatedOfferDelivery.setAccepted(false);
        updatedOfferDelivery.setOffer(offer);

        when(offerService.getOfferById(offer.getId())).thenReturn(Optional.of(offer));
        when(offerDeliveryService.getOfferDeliveryById(offerDeliveryId)).thenReturn(Optional.empty());

        ResponseEntity<OfferDelivery> response = offerDeliveryController.updateOfferDelivery(offerDeliveryId, updatedOfferDelivery);

        verify(offerDeliveryService, never()).createOfferDelivery(any(OfferDelivery.class));
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateOfferDeliveryWithValidData() {
        Long offerDeliveryId = 1L;
        OfferDelivery existingOfferDelivery = new OfferDelivery();
        existingOfferDelivery.setId(offerDeliveryId);
        existingOfferDelivery.setDeliveryDate(LocalDate.now());
        existingOfferDelivery.setAccepted(false);
        existingOfferDelivery.setOffer(offer);

        OfferDelivery updatedOfferDelivery = new OfferDelivery();
        updatedOfferDelivery.setDeliveryDate(LocalDate.of(2023, 12, 31));
        updatedOfferDelivery.setAccepted(true);
        updatedOfferDelivery.setOffer(offer);

        Mockito.when(offerDeliveryService.updateOfferDelivery(ArgumentMatchers.eq(offerDeliveryId), ArgumentMatchers.any(OfferDelivery.class)))
                .thenReturn(updatedOfferDelivery);

        ResponseEntity<OfferDelivery> response = offerDeliveryController.updateOfferDelivery(offerDeliveryId, updatedOfferDelivery);

        Mockito.verify(offerDeliveryService, Mockito.times(1)).updateOfferDelivery(ArgumentMatchers.eq(offerDeliveryId), ArgumentMatchers.any(OfferDelivery.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getDeliveryDate().equals(LocalDate.of(2023, 12, 31));
        assert response.getBody().isAccepted();
        assert response.getBody().getOffer().equals(offer);
    }

    @Test
    void testDeleteOfferDelivery() {
        Long offerDeliveryId = 1L;

        when(offerDeliveryService.getOfferDeliveryById(offerDeliveryId)).thenReturn(Optional.of(new OfferDelivery()));

        ResponseEntity<Void> response = offerDeliveryController.deleteOfferDelivery(offerDeliveryId);

        verify(offerDeliveryService, times(1)).getOfferDeliveryById(offerDeliveryId);
        verify(offerDeliveryService, times(1)).deleteOfferDelivery(offerDeliveryId);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    void testDeleteOfferDeliveryNotFound() {
        Long offerDeliveryId = 999L;

        when(offerDeliveryService.getOfferDeliveryById(offerDeliveryId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = offerDeliveryController.deleteOfferDelivery(offerDeliveryId);

        verify(offerDeliveryService, times(1)).getOfferDeliveryById(offerDeliveryId);
        verify(offerDeliveryService, never()).deleteOfferDelivery(offerDeliveryId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }
}