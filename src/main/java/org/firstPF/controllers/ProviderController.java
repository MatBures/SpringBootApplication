package org.firstPF.controllers;


import org.firstPF.entities.Provider;
import org.firstPF.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/providers")
public class ProviderController {
    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<Provider> createProvider(@RequestBody Provider provider) {
        Provider createdProvider = providerService.createProvider(provider);
        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Provider>> getAllProviders() {
        List<Provider> providers = providerService.getAllProviders();
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
        Provider provider = providerService.getProviderById(id).orElse(null);
        if (provider == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provider, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider updatedProvider) {
        Provider provider = providerService.getProviderById(id).orElse(null);
        if (provider == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Update the provider properties with the updatedProvider
        provider.setName(updatedProvider.getName());
        provider.setAddress(updatedProvider.getAddress());
        provider.setEmail(updatedProvider.getEmail());
        provider.setContactNumber(updatedProvider.getContactNumber());

        Provider updatedProviderEntity = providerService.createProvider(provider);
        return new ResponseEntity<>(updatedProviderEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        Optional<Provider> provider = providerService.getProviderById(id);
        if (provider.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}
