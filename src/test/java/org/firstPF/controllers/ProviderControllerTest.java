package org.firstPF.controllers;

import org.firstPF.entities.Provider;
import org.firstPF.services.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProviderControllerTest {

    @Mock
    private ProviderService providerService;

    @InjectMocks
    private ProviderController providerController;

    private Provider provider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        provider = new Provider();
        provider.setId(1L);
        provider.setName("Sample Provider");
        provider.setAddress("123 Main Street");
        provider.setEmail("provider@example.com");
        provider.setContactNumber("123-456-7890");
    }

    @Test
    void testCreateProvider() {
        when(providerService.createProvider(any(Provider.class))).thenReturn(provider);

        ResponseEntity<Provider> response = providerController.createProvider(provider);

        verify(providerService, times(1)).createProvider(any(Provider.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() != null;
        assert response.getBody().getId().equals(provider.getId());
    }

    @Test
    void testGetAllProviders() {
        List<Provider> providers = new ArrayList<>();
        providers.add(provider);

        when(providerService.getAllProviders()).thenReturn(providers);

        ResponseEntity<List<Provider>> response = providerController.getAllProviders();

        verify(providerService, times(1)).getAllProviders();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == providers.size();
    }

    @Test
    void testGetProviderById() {
        Long providerId = 1L;

        when(providerService.getProviderById(providerId)).thenReturn(Optional.of(provider));

        ResponseEntity<Provider> response = providerController.getProviderById(providerId);

        verify(providerService, times(1)).getProviderById(providerId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getId().equals(providerId);
    }

    @Test
    void testGetProviderByIdNotFound() {
        Long providerId = 999L;

        when(providerService.getProviderById(providerId)).thenReturn(Optional.empty());

        ResponseEntity<Provider> response = providerController.getProviderById(providerId);

        verify(providerService, times(1)).getProviderById(providerId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateProvider() {
        Long providerId = 1L;
        Provider existingProvider = new Provider();
        existingProvider.setId(providerId);
        existingProvider.setName("Existing Provider");
        existingProvider.setAddress("Old Address");
        existingProvider.setEmail("existing@example.com");
        existingProvider.setContactNumber("123456789");

        Provider updatedProvider = new Provider();
        updatedProvider.setName("Updated Provider");
        updatedProvider.setAddress("New Address");
        updatedProvider.setEmail("updated@example.com");
        updatedProvider.setContactNumber("987654321");

        Mockito.when(providerService.updateProvider(ArgumentMatchers.eq(providerId), ArgumentMatchers.any(Provider.class)))
                .thenReturn(updatedProvider);

        ResponseEntity<Provider> response = providerController.updateProvider(providerId, updatedProvider);

        Mockito.verify(providerService, Mockito.times(1)).updateProvider(ArgumentMatchers.eq(providerId), ArgumentMatchers.any(Provider.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getName().equals(updatedProvider.getName());
    }

    @Test
    void testUpdateProviderNotFound() {
        Long invalidProviderId = 999L;
        Provider updatedProvider = new Provider();
        updatedProvider.setName("Updated Provider");
        updatedProvider.setAddress("New Address");
        updatedProvider.setEmail("updated@example.com");
        updatedProvider.setContactNumber("987654321");

        Mockito.when(providerService.updateProvider(ArgumentMatchers.eq(invalidProviderId), ArgumentMatchers.any(Provider.class)))
                .thenReturn(null);

        ResponseEntity<Provider> response = providerController.updateProvider(invalidProviderId, updatedProvider);

        Mockito.verify(providerService, Mockito.times(1)).updateProvider(ArgumentMatchers.eq(invalidProviderId), ArgumentMatchers.any(Provider.class));
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert response.getBody() == null;
    }

    @Test
    void testDeleteProvider() {
        Long providerId = 1L;

        when(providerService.getProviderById(providerId)).thenReturn(Optional.of(provider));

        ResponseEntity<Void> response = providerController.deleteProvider(providerId);

        verify(providerService, times(1)).getProviderById(providerId);
        verify(providerService, times(1)).deleteProvider(providerId);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    void testDeleteProviderNotFound() {
        Long providerId = 999L;

        when(providerService.getProviderById(providerId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = providerController.deleteProvider(providerId);

        verify(providerService, times(1)).getProviderById(providerId);
        verify(providerService, never()).deleteProvider(providerId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }
}
