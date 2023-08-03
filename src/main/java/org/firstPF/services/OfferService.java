package org.firstPF.services;

import jakarta.persistence.EntityNotFoundException;
import org.firstPF.entities.Employee;
import org.firstPF.entities.Offer;
import org.firstPF.repositories.EmployeeRepository;
import org.firstPF.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository, EmployeeRepository employeeRepository) {
        this.offerRepository = offerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Offer createOffer(Offer offer) {
        if (offer.getProvider() == null || offer.getProvider().getId() == null) {
            throw new IllegalArgumentException("ProviderId is required.");
        }

        if (offer.getCustomer() == null || offer.getCustomer().getId() == null) {
            throw new IllegalArgumentException("CustomerId is required.");
        }

        return offerRepository.save(offer);
    }

    public void assignEmployeeToOffer(Long offerId, Long employeeId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalOffer.isEmpty()) {
            throw new IllegalArgumentException("Offer doesn't exist.");
        }

        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee doesn't exist.");
        }

        Offer offer = optionalOffer.get();
        Employee employee = optionalEmployee.get();

        offer.addEmployee(employee);
        offerRepository.save(offer);

        employee.addOffer(offer);
        employeeRepository.save(employee);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    public Offer updateOffer(Long id, Offer updatedOffer) {
        Offer offer = getOfferById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer with id " + id + " not found"));

        if (updatedOffer.getProvider() == null || updatedOffer.getProvider().getId() == null) {
            throw new IllegalArgumentException("Provider data is required.");
        }

        if (updatedOffer.getCustomer() == null || updatedOffer.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Customer data is required.");
        }

        offer.setTitle(updatedOffer.getTitle());
        offer.setDescription(updatedOffer.getDescription());
        offer.setCost(updatedOffer.getCost());
        offer.setStatus(updatedOffer.getStatus());
        offer.setProvider(updatedOffer.getProvider());
        offer.setCustomer(updatedOffer.getCustomer());

        return offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
