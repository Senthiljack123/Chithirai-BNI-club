package com.example.Final.controller;

import com.example.Final.Service.ClubService;
import com.example.Final.model.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club")
public class ClubController {
    @Autowired
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }
    @PostMapping("/addclubs")
    public ResponseEntity<String> addClub(@RequestBody Club club) {
        Club addedClub = clubService.addClub(club);
        if (addedClub != null) {
            return new ResponseEntity<>("Club added successfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add club", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClub(@PathVariable Long id, @RequestBody Club clubDetails) {
        Club updateClub = clubService.updateClub(id, clubDetails);
        if (updateClub != null) {
            return new ResponseEntity<>("Club update successfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to update club", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
    }
}
