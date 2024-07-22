package com.example.Final.controller;

import com.example.Final.Repo.EventRepository;
import com.example.Final.Service.EventService;
import com.example.Final.config.ApiResponse;
import com.example.Final.model.Event;
import com.example.Final.model.EventRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;


    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        Event newEvent = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, "event suceecfully added", null));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // GET event by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT update event
    @PutMapping("/edit/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(id, updatedEvent);
        return event != null ? new ResponseEntity<>(event, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE event by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public List<EventRegistration> getAllEventRegistrations() {
        return eventService.getAllEventRegistrations();
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createEventRegistration(@RequestBody EventRegistration eventRegistration) {

        if (eventService.findByEmail(eventRegistration.getEmailAddress()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "this mail already registerd", null));
        }
        String s = eventRegistration.getHasGuest().toLowerCase();
        if(s.equals("yes")){

            if (eventRegistration.getGuestFirstName() == null || eventRegistration.getGuestFirstName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Please gust a Firstname", null));
            }
            if (eventRegistration.getGuestLastName() == null || eventRegistration.getGuestLastName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Please gust a Lastname", null));
            }
            if (eventRegistration.getGuestEmailAddress() == null || eventRegistration.getGuestEmailAddress().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Please enter an gust Email Address", null));
            }
            if (eventRegistration.getGuestContactNumber() == null || eventRegistration.getGuestContactNumber().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Please enter a gust Contact Number", null));
            }
        }
          eventService.createEventRegistration(eventRegistration);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, "register sucessfull", null));

    }
}

