package org.firstPF.repositoriesTest;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Provider;
import org.firstPF.repositories.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@DirtiesContext
public class ProviderRepositoryTest extends ApplicationTests {

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    public void setUp() {
        providerRepository.deleteAll();
    }

    @Test
    public void testFindById() {
        Provider provider = new Provider();
        provider.setName("Test Provider");
        provider.setAddress("123 Test Street");
        provider.setEmail("test.provider@example.com");
        provider.setContactNumber("1234567890");
        providerRepository.save(provider);

        Optional<Provider> result = providerRepository.findById(provider.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(provider.getName(), result.get().getName());
        Assertions.assertEquals(provider.getAddress(), result.get().getAddress());
        Assertions.assertEquals(provider.getEmail(), result.get().getEmail());
        Assertions.assertEquals(provider.getContactNumber(), result.get().getContactNumber());
    }

    @Test
    public void testSave() {
        Provider provider = new Provider();
        provider.setName("New Provider");
        provider.setAddress("456 New Street");
        provider.setEmail("new.provider@example.com");
        provider.setContactNumber("9876543210");

        Provider savedProvider = providerRepository.save(provider);

        Assertions.assertNotNull(savedProvider.getId());

        Provider retrievedProvider = providerRepository.findById(savedProvider.getId()).get();
        Assertions.assertEquals(provider.getName(), retrievedProvider.getName());
        Assertions.assertEquals(provider.getAddress(), retrievedProvider.getAddress());
        Assertions.assertEquals(provider.getEmail(), retrievedProvider.getEmail());
        Assertions.assertEquals(provider.getContactNumber(), retrievedProvider.getContactNumber());
    }
}
