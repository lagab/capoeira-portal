package com.lagab.capoeira.capoeiraportal.service;

import com.lagab.capoeira.capoeiraportal.domain.Address;
import com.lagab.capoeira.capoeiraportal.domain.School;
import com.lagab.capoeira.capoeiraportal.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address address) {
        return this.addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return addressRepository.existsById(id);
    }

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public Address update(Address address) {
        return addressRepository.save(address);
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    public Optional<Address> findById(Long id){
        return this.addressRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public List<Address> findAll() {
        return this.addressRepository.findAll();
    }

}
