package org.firstPF.services;

import jakarta.persistence.EntityNotFoundException;
import org.firstPF.entities.Provider;
import org.firstPF.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Optional<Provider> getProviderById(Long id) {
        return providerRepository.findById(id);
    }

    public Provider updateProvider(Long id, Provider updatedProvider) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider with id " + id + " not found"));

        provider.setName(updatedProvider.getName());
        provider.setAddress(updatedProvider.getAddress());
        provider.setEmail(updatedProvider.getEmail());
        provider.setContactNumber(updatedProvider.getContactNumber());

        return providerRepository.save(provider);
    }
    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }
}