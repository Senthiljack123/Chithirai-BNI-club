package com.example.Final.Repo;


import com.example.Final.model.EventRegistration;
import com.example.Final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    EventRegistration findByEmailAddress(String email);
}