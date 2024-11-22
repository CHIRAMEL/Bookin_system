package com.example.demo.controller;

import com.example.demo.entity.Waitlist;
import com.example.demo.service.WaitlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waitlist")
public class WaitlistController {

    @Autowired
    private WaitlistService waitlistService;

    @PostMapping("/add")
    public Waitlist addToWaitlist(@RequestParam Long userId, @RequestParam Long classId) {
        return waitlistService.addToWaitlist(userId, classId);
    }

    @GetMapping("/class/{classId}")
    public List<Waitlist> getWaitlist(@PathVariable Long classId) {
        return waitlistService.getWaitlistForClass(classId);
    }

}

