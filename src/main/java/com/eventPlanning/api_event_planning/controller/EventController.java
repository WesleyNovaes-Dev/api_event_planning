package com.eventPlanning.api_event_planning.controller;


import com.eventPlanning.api_event_planning.domain.event.Event;
import com.eventPlanning.api_event_planning.domain.event.EventRequestDTO;
import com.eventPlanning.api_event_planning.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@RequestParam("title") String title,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image", required = false) MultipartFile image)
    {
        EventRequestDTO eventRequestDTO = new EventRequestDTO(title, description, date, city, state, remote, eventUrl, image);
        Event event = eventService.createEvent(eventRequestDTO);
        return ResponseEntity.ok(event);
    }

}
