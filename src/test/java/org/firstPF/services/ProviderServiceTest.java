package org.firstPF.services;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Provider;
import org.firstPF.repositories.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext
public class ProviderServiceTest extends ApplicationTests {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    public void setUp() {
        providerRepository.deleteAll();
    }

    @Test
    public void testCreateProvider() {
        Provider provider = new Provider();
        provider.setName("ABC Electronics");
        provider.setAddress("London 583/5");
        provider.setEmail("ABCElectronics@example.com");
        provider.setContactNumber("1234567890");

        Provider createdProvider = providerService.createProvider(provider);

        Assertions.assertNotNull(createdProvider.getId());
        Assertions.assertTrue(createdProvider.getId() > 0);

        Assertions.assertEquals(provider.getName(), createdProvider.getName());
        Assertions.assertEquals(provider.getAddress(), createdProvider.getAddress());
        Assertions.assertEquals(provider.getEmail(), createdProvider.getEmail());
        Assertions.assertEquals(provider.getContactNumber(), createdProvider.getContactNumber());
    }

    @Test
    public void testGetAllProviders() {
        List<Provider> providers = providerService.getAllProviders();

        Assertions.assertTrue(providers.isEmpty());

        Provider provider1 = new Provider();
        provider1.setName("ABC Electronics");
        provider1.setAddress("London 583/5");
        provider1.setEmail("ABCElectronics@example.com");
        provider1.setContactNumber("1234567890");
        providerService.createProvider(provider1);

        Provider provider2 = new Provider();
        provider2.setName("XYZ Technologies");
        provider2.setAddress("New York 123");
        provider2.setEmail("xyz@example.com");
        provider2.setContactNumber("9876543210");
        providerService.createProvider(provider2);

        providers = providerService.getAllProviders();

        Assertions.assertEquals(2, providers.size());
    }

    @Test
    public void testGetProviderById() {
        Provider provider = new Provider();
        provider.setName("ABC Electronics");
        provider.setAddress("London 583/5");
        provider.setEmail("ABCElectronics@example.com");
        provider.setContactNumber("1234567890");
        provider = providerService.createProvider(provider);
        Long createdProviderId = provider.getId();

        Optional<Provider> result = providerService.getProviderById(createdProviderId);

        Assertions.assertTrue(result.isPresent());

        Assertions.assertEquals(provider.getName(), result.get().getName());
        Assertions.assertEquals(provider.getAddress(), result.get().getAddress());
        Assertions.assertEquals(provider.getEmail(), result.get().getEmail());
        Assertions.assertEquals(provider.getContactNumber(), result.get().getContactNumber());
    }

    @Test
    public void testDeleteProvider() {
        Provider provider = new Provider();
        provider.setName("ABC Electronics");
        provider.setAddress("London 583/5");
        provider.setEmail("ABCElectronics@example.com");
        provider.setContactNumber("1234567890");
        provider = providerService.createProvider(provider);
        Long providerId = provider.getId();

        providerService.deleteProvider(providerId);

        Optional<Provider> deletedProvider = providerService.getProviderById(providerId);

        Assertions.assertFalse(deletedProvider.isPresent());
    }
}
