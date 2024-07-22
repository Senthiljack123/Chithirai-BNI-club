package com.example.Final.Service;
import com.example.Final.Repo.EventRegistrationRepository;
import com.example.Final.Repo.EventRepository;
import com.example.Final.model.Event;
import com.example.Final.model.EventRegistration;
import com.example.Final.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Method to add an event
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    // Method to retrieve all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Method to retrieve a single event by its ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event getEventByIds(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    // Method to update an existing event
    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> existingEventOptional = eventRepository.findById(id);
        if (existingEventOptional.isPresent()) {
            Event existingEvent = existingEventOptional.get();
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setLocation(updatedEvent.getLocation());
            existingEvent.setStartTime(updatedEvent.getStartTime());
            existingEvent.setEndTime(updatedEvent.getEndTime());
            existingEvent.setOrganizerName(updatedEvent.getOrganizerName());
            // Update other fields as needed
            return eventRepository.save(existingEvent);
        } else {
            // Handle error - Event not found
            return null;
        }
    }

    // Method to delete an event by its ID
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    public List<EventRegistration> getAllEventRegistrations() {
        return eventRegistrationRepository.findAll();
    }

    public EventRegistration createEventRegistration(EventRegistration eventRegistration) {
        return eventRegistrationRepository.save(eventRegistration);
    }

    public EventRegistration findByEmail(String emailAddress) {
       return eventRegistrationRepository.findByEmailAddress(emailAddress);
    }
}
