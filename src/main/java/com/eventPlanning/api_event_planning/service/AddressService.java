package com.eventPlanning.api_event_planning.service;

import com.eventPlanning.api_event_planning.domain.event.Event;
import com.eventPlanning.api_event_planning.domain.event.EventRequestDTO;
import com.eventPlanning.api_event_planning.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventPlanning.api_event_planning.domain.address.Address;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AddressService
{
    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event)
    {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.state());
        address.setEvent(event);
        return addressRepository.save(address);
    }

    public Optional<Address> findByEventId(UUID eventId) {
        return addressRepository.findByEventId(eventId);
    }
}
