package com.example.Final.Service;

import com.example.Final.Dto.ApplyEventRequest;
import com.example.Final.Dto.ApplyEventResponse;
import com.example.Final.Repo.EventRepository;
import com.example.Final.Repo.UserRepository;
import com.example.Final.config.ApplyEventService;
import com.example.Final.model.Event;
import com.example.Final.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplyEventServiceImpl implements ApplyEventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApplyEventResponse applyForEvent(ApplyEventRequest request) {
        Optional<Event> optionalEvent = eventRepository.findById(request.getEventId());
        Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if (optionalEvent.isPresent() && optionalUser.isPresent()) {
            Event event = optionalEvent.get();
            User user = optionalUser.get();
            // Add logic to handle event application, such as adding user to event attendees
            // For example:
            event.getAttendees().add(user);
            eventRepository.save(event);

            return new ApplyEventResponse(true, "User applied for event successfully.");
        } else {
            return new ApplyEventResponse(false, "Event or user not found.");
        }
    }
}
