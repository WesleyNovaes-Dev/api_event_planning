package com.eventPlanning.api_event_planning.repositories;

import com.eventPlanning.api_event_planning.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    public Optional<Address> findByEventId(UUID eventId);

}
