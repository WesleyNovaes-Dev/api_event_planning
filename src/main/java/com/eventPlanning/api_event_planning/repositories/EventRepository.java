package com.eventPlanning.api_event_planning.repositories;

import com.eventPlanning.api_event_planning.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID>{
}
