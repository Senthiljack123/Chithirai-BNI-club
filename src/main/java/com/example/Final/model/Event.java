package com.example.Final.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    private Date startTime;
    private Date endTime;
    private LocalDate eventDate;
    private String organizerName;
    private String video;
    private String image;
    private boolean freeOrPaid;
    private double amount;
    private String venue;
    private boolean online;
    private boolean offline;

    @ManyToMany
    @JoinTable(
            name = "event_attendees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees = new ArrayList<>();

    // Constructors
    public Event() {
    }

    public Event(Long id, String name, String description, String location, Date startTime, Date endTime, String organizerName, String video,LocalDate eventDate, String image, boolean freeOrPaid, double amount, String venue, boolean online, boolean offline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizerName = organizerName;
        this.video = video;
        this.image = image;
        this.freeOrPaid = freeOrPaid;
        this.amount = amount;
        this.venue = venue;
        this.online = online;
        this.offline = offline;
        this.eventDate = eventDate;
    }


}
